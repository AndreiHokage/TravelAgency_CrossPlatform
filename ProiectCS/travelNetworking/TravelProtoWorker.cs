using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using generatedcode;
using Google.Protobuf;
using travelModel.validators;
using travelNetworking.gRPC;
using travelServices;
using Employee = travelModel.Employee;
using Flight = travelModel.Flight;
using Ticket = travelModel.Ticket;

namespace travelNetworking
{
    public class TravelProtoWorker : ITravelObserver
    {
        private ITravelServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public TravelProtoWorker(ITravelServices server, TcpClient connection)
        {
            Console.WriteLine("Creating TravelProtoWorker ..");
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    TravelMainRequest request = TravelMainRequest.Parser.ParseDelimitedFrom(stream);
                    TravelMainResponse response = handleRequest( request);
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }

            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        public void soldTicket(Ticket ticket)
        {
            Console.WriteLine("Update ticket to notify: " + ticket);
            try
            {
                TravelMainResponse resp = ProtoUtils.createTicketResponse(ticket);
                sendResponse(resp);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        private TravelMainResponse handleRequest(TravelMainRequest request)
        {
            TravelMainResponse response = null;
            Console.WriteLine("BAABAABABABAB " + ProtoUtils.getTypeRequest(request) );
            if(ProtoUtils.getTypeRequest(request) == "Login")
            {
                Console.WriteLine("Login Request ...");
                Employee employee = ProtoUtils.getEmployeeRequest(request);

                try
                {
                    lock (server)
                    {
                        server.login(employee, this);
                    }

                    return ProtoUtils.createOkResponse();
                }
                catch (TravelException ex)
                {
                    connected = false;
                    return ProtoUtils.createErrorResponse(ex.Message);
                }
            }

            //if (request is LogoutRequest)
            if(ProtoUtils.getTypeRequest(request) == "Logout")
            {
                Console.WriteLine("Logout Request ...");
                Employee employee = ProtoUtils.getEmployeeRequest(request);
                try
                {
                    lock (server)
                    {
                        server.logout(employee, this);
                    }

                    connected = false;
                    return ProtoUtils.createOkResponse();
                }
                catch (TravelException ex)
                {
                    return ProtoUtils.createErrorResponse(ex.Message);
                }
            }

            //if (request is BuyTicketRequest)
            if(ProtoUtils.getTypeRequest(request) == "BuyTicketTravel")
            {
                Console.WriteLine("Buy ticket request ...");
                Ticket ticket = ProtoUtils.getTicketRequest(request);
                try
                {
                    lock (server)
                    {
                        server.addTicket(ticket);
                    }

                    return ProtoUtils.createOkResponse();
                }
                catch (TravelException ex)
                {
                    return ProtoUtils.createErrorResponse(ex.Message);
                }
                catch (ValidationException ex)
                {
                    return ProtoUtils.createErrorNoSeatsResponse(ex.Message);
                }
            }

            //if (request is FightsDestinationDepartureRequest)
            if(ProtoUtils.getTypeRequest(request) == "FlightsByDestinationAndDeparture")
            {
                Console.WriteLine("FightsDestinationDepartureRequest request ...");
                FlightDestDepDTO flightDestDepDto = ProtoUtils.getFlightDestDepRequest(request);
                string destination = flightDestDepDto.Destination;
                DateTime departure = flightDestDepDto.Departure;
                try
                {
                    IEnumerable<Flight> flights;
                    lock (server)
                    {
                        flights = server.filterFlightsByDestinationAndDeparture(destination, departure);
                    }
                    return ProtoUtils.createDestinationDepartureResponse(flights);
                }
                catch (TravelException ex)
                {
                    return ProtoUtils.createErrorResponse(ex.Message);
                }
            }

            //if (request is FlightAvailableSeatsRequest)
            if(ProtoUtils.getTypeRequest(request) == "FlightByAvailableSeats")
            {
                Console.WriteLine("flight available seats request ...");
                try
                {
                    IEnumerable<Flight> flights;
                    lock (server)
                    {
                        flights = server.filterFlightByAvailableSeats();
                    }

                    return ProtoUtils.createAvailableSeatsResponse(flights);
                }
                catch (TravelException ex)
                {
                    return ProtoUtils.createErrorResponse(ex.Message);
                }
            }

            return response;
        }

        private void sendResponse(TravelMainResponse response)
        {
            Console.WriteLine("sending response "+response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }
    }
}