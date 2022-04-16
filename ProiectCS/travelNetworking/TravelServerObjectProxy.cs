using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using travelModel;
using travelModel.validators;
using travelServices;

namespace travelNetworking{

    public class TravelServerObjectProxy : ITravelServices
    {
        private string host;
        private int port;

        private ITravelObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public TravelServerObjectProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public void login(Employee employee, ITravelObserver client)
        {
            initializeConnection();
            Request loginRequest = new LoginRequest(employee);
            sendRequest(loginRequest);
            Response response = readResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return;
            }

            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse) response;
                closeConnection();
                throw new TravelException(err.getData());
            }
        }

        public void logout(Employee employee, ITravelObserver client)
        {
            Request logoutRequest = new LogoutRequest(employee);
            sendRequest(logoutRequest);
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse) response;
                throw new TravelException(err.getData());
            }
        }

        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            FlightDestDepDTO flightDestDepDto = new FlightDestDepDTO(destination, departure);
            Request request = new FightsDestinationDepartureRequest(flightDestDepDto);
            sendRequest(request);
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse) response;
                throw new TravelException(err.getData());
            }

            FlightDestinationDepartureResponse flightResponse = (FlightDestinationDepartureResponse) response;
            IEnumerable<Flight> flights = flightResponse.getData();
            return flights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            Request request = new FlightAvailableSeatsRequest();
            sendRequest(request);
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse) response;
                Console.WriteLine(err.getData());
            }

            FlightAvailableSeatsResponse flightResponse = (FlightAvailableSeatsResponse) response;
            IEnumerable<Flight> flights = flightResponse.getData();
            return flights;
        }

        public void addTicket(Ticket ticket)
        {
            Request request = new BuyTicketRequest(ticket);
            sendRequest(request);
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse) response;
                Console.WriteLine(err.getData());
            }

            if (response is ErrorUnavailableSeatsResponse)
            {
                ErrorUnavailableSeatsResponse err = (ErrorUnavailableSeatsResponse) response;
                throw new ValidationException(err.getData());
            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new TravelException("Error sending object");
            }
        }

        private Response readResponse()
        {
            Response response = null;
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
                formatter = new BinaryFormatter();
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

        private void handleUpdate(UpdateResponse response)
        {
            if (response is AddTicketResponse)
            {
                try
                {
                    AddTicketResponse addTicketResponse = (AddTicketResponse) response;
                    Ticket ticket = addTicketResponse.getData();
                    client.soldTicket(ticket);
                }
                catch (TravelException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse) response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response) response);
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