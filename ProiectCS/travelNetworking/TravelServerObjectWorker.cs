using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using travelModel;
using travelModel.validators;
using travelServices;

namespace travelNetworking
{

    public class TravelServerObjectWorker : ITravelObserver
    {
        private ITravelServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public TravelServerObjectWorker(ITravelServices server, TcpClient connection)
        {
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
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request) request);
                    if (response != null)
                    {
                        sendResponse((Response) response);
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
                sendResponse(new AddTicketResponse(ticket));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            if (request is LoginRequest)
            {
                Console.WriteLine("Login Request ...");
                LoginRequest logReq = (LoginRequest) request;
                Employee employee = logReq.getData();

                try
                {
                    lock (server)
                    {
                        server.login(employee, this);
                    }

                    return new OkResponse();
                }
                catch (TravelException ex)
                {
                    connected = false;
                    return
                        new ErrorResponse(ex
                            .Message); //I return something,I don t raise an exception
                }
            }

            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout Request ...");
                LogoutRequest logoutRequest = (LogoutRequest) request;
                Employee employee = logoutRequest.getData();
                try
                {
                    lock (server)
                    {
                        server.logout(employee, this);
                    }

                    connected = false;
                    return new OkResponse();
                }
                catch (TravelException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if (request is BuyTicketRequest)
            {
                Console.WriteLine("Buy ticket request ...");
                BuyTicketRequest ticketRequest = (BuyTicketRequest) request;
                Ticket ticket = ticketRequest.getData();
                try
                {
                    lock (server)
                    {
                        server.addTicket(ticket);
                    }

                    return new OkResponse();
                }
                catch (TravelException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
                catch (ValidationException ex)
                {
                    return new ErrorUnavailableSeatsResponse(ex.Message);
                }
            }

            if (request is FightsDestinationDepartureRequest)
            {
                Console.WriteLine("FightsDestinationDepartureRequest request ...");
                FightsDestinationDepartureRequest flightDestReq = (FightsDestinationDepartureRequest) request;
                FlightDestDepDTO flightDestDepDto = flightDestReq.getData();
                string destination = flightDestDepDto.Destination;
                DateTime departure = flightDestDepDto.Departure;
                try
                {
                    IEnumerable<Flight> flights;
                    lock (server)
                    {
                        flights = server.filterFlightsByDestinationAndDeparture(destination, departure);
                    }

                    return new FlightDestinationDepartureResponse(flights);
                }
                catch (TravelException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if (request is FlightAvailableSeatsRequest)
            {
                Console.WriteLine("flight available seats request ...");
                FlightAvailableSeatsRequest availableSeatsRequest = (FlightAvailableSeatsRequest) request;
                try
                {
                    IEnumerable<Flight> flights;
                    lock (server)
                    {
                        flights = server.filterFlightByAvailableSeats();
                    }

                    return new FlightAvailableSeatsResponse(flights);
                }
                catch (TravelException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response.ToString());
            lock (stream)
            {
                formatter.Serialize(stream, response);
                stream.Flush();
            }
        }
    }
}