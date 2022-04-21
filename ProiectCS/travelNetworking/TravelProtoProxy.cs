using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
    public class TravelProtoProxy  : ITravelServices
    {
        private string host;
        private int port;

        private ITravelObserver client;

        private NetworkStream stream;
        
        private TcpClient connection;

        private Queue<TravelMainResponse> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public TravelProtoProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<TravelMainResponse>();
        }

        public void login(Employee employee, ITravelObserver client)
        {
            initializeConnection();
            generatedcode.TravelMainRequest request = ProtoUtils.createLoginRequest(employee);
            sendRequest(request);
            TravelMainResponse response = readResponse();
            if( ProtoUtils.getTypeResponse(response) == "Ok" ){
                this.client = client;
                Console.WriteLine("Client connected ...");
                return;
            }
            if( ProtoUtils.getTypeResponse(response) == "Error" ){
                Console.WriteLine("Error client connection ...");
                String err = ProtoUtils.getErrorResponse(response);
                closeConnection();
                throw new TravelException(err);
            }
        }

        public void logout(Employee employee, ITravelObserver client)
        {
            TravelMainRequest request = ProtoUtils.createLogoutRequest(employee);
            sendRequest(request);
            TravelMainResponse response = readResponse();
            closeConnection();
            if(ProtoUtils.getTypeResponse(response) == "Error" ){
                String err = ProtoUtils.getErrorResponse(response);
                throw new TravelException(err);
            }
        }

        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            FlightDestDepDTO flightDestDepDTO = new FlightDestDepDTO(destination,departure);
            TravelMainRequest request = ProtoUtils.createFilterFlightsByDestinationAndDepartureRequest(flightDestDepDTO);
            sendRequest(request);
            TravelMainResponse response = readResponse();
            if(ProtoUtils.getTypeResponse(response) == "Error" ){
                String err = ProtoUtils.getErrorResponse(response);
                throw new TravelException(err);
            }
            Collection<Flight> flights = ProtoUtils.getFlightsCollectionResponse(response);
            return flights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            TravelMainRequest request = ProtoUtils.createFilterFlightByAvailableSeatsRequest();
            sendRequest(request);
            TravelMainResponse response = readResponse();
            if(ProtoUtils.getTypeResponse(response) == "Error"){
                throw new TravelException("Something went wrong");
            }
            Collection<Flight> flights = ProtoUtils.getFlightsCollectionResponse(response);
            return flights;
        }

        public void addTicket(Ticket ticket)
        {
            Console.WriteLine("balanciic " + ticket);
            TravelMainRequest request = ProtoUtils.createAddTicketRequest(ticket);
            sendRequest(request);
            TravelMainResponse response = readResponse();
            if(ProtoUtils.getTypeResponse(response) == "Error"){
                String err = ProtoUtils.getErrorResponse(response);
                closeConnection();
                throw new TravelException(err);
            }
            if(ProtoUtils.getTypeResponse(response) == "ErrorUnvaiableSeats"){
                String err = ProtoUtils.getErrorResponse(response);
                throw new TravelException(err);
            }
        }

        private void sendRequest(TravelMainRequest request)
        {
            try
            {
                Console.WriteLine("Sending request ..."+request);
                request.WriteDelimitedTo(stream);
                stream.Flush();
                Console.WriteLine("Request sent.");
            }
            catch (Exception e)
            {
                throw new TravelException("Error sending object");
            }
        }

        private TravelMainResponse readResponse()
        {
            TravelMainResponse response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

            return response;
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();

                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void startReader()
        {
            Thread thread = new Thread(new ThreadStart(run));
            thread.Start();
        }

        private void handleUpdate(TravelMainResponse response)
        {
            if (ProtoUtils.getTypeResponse(response) == "AddTicket")
            {
                try
                {
                    Ticket ticket = ProtoUtils.getTicketResponse(response);
                    client.soldTicket(ticket);
                }
                catch (TravelException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        private bool isUpdate(TravelMainResponse response){
            return ProtoUtils.getTypeResponse(response) == "AddFlight" || ProtoUtils.getTypeResponse(response) == "AddTicket";
        }
        
        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    TravelMainResponse response = TravelMainResponse.Parser.ParseDelimitedFrom(stream);
                    Console.WriteLine("response received " + response);
                    if (isUpdate(response))
                    {
                        handleUpdate(response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue(response);
                        }

                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error: " + e);
                }
            }
        }
    }
}