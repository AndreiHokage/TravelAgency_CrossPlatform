using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Grpc.Core;
using travelModel;
using travelNetworking.gRPC;
using travelServices;


namespace travelNetworking
{
    public class TravelServerGRPCProxy
    {
       /* private Channel channel;
        private ITravelObserver clientCtrl;
        private generatedcode.ITravelServicesProto.ITravelServicesProtoClient client;
        
        public TravelServerGRPCProxy(string host, int port, 
            generatedcode.ITravelServicesProto.ITravelServicesProtoClient client)
        {
            Console.WriteLine("Creating TravelServerGRPCProxy ...");
            string addr = host + ":" + port.ToString();
            this.channel = new Channel(addr, ChannelCredentials.Insecure);
            this.client = client;
        }
        
        public void shutdown()
        {
            this.channel.ShutdownAsync();
        }
        
        public void login(Employee employee, ITravelObserver clientCall)
        {
            Console.WriteLine("Invoked login method in Stub.....");
            generatedcode.Employee request = ProtoUtils.createEmployeeMessage(employee);
            generatedcode.TravelResponse response = client.Login(request);

            if (ProtoUtils.getTypeResponse(response).Equals("OK"))
            {
                this.clientCtrl = clientCall;
                Console.WriteLine("Client connected ...");
                return;
            }

            if (ProtoUtils.getTypeResponse(response).Equals("ERROR"))
            {
                Console.WriteLine("Error client connection ...");
                string error = ProtoUtils.getErrorResponse(response);
                throw new TravelException(error);
            }
        }
        
        public void logout(Employee employee, ITravelObserver clientCall)
        {
            Console.WriteLine("Invoked logout method in Stub.....");
            generatedcode.Employee request = ProtoUtils.createEmployeeMessage(employee);
            generatedcode.TravelResponse response = client.Logout(request);
            shutdown();
            if (ProtoUtils.getTypeResponse(response).Equals("ERROR"))
            {
                Console.WriteLine("Error logout stub ...");
                string error = ProtoUtils.getErrorResponse(response);
                throw new TravelException(error);
            }
        }
        
        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            FlightDestDepDTO flightDestDepDto = new FlightDestDepDTO(destination, departure);
            generatedcode.FlightDestDepDTO request = ProtoUtils.createFlightDestDTOMessage(flightDestDepDto);
            IEnumerable<Flight> flights = new List<Flight>();
            using (var call = client.FilterFlightsByDestinationAndDeparture(request))
            {
                while (call.ResponseStream.MoveNext() != null)
                {
                    Flight flight = ProtoUtils.getFlight(call.ResponseStream.Current);
                    flights.Append(flight);
                }
            }
            return flights;
        }

        private async Task<IEnumerable<Flight>> readInputStream(generatedcode.TravelRequest request)
        {
            IEnumerable<Flight> flights = new List<Flight>();
            using (var call = client.FilterFlightByAvailableSeats(request))
            {
                while (await call.ResponseStream.MoveNext() != null)
                {
                    Flight flight = ProtoUtils.getFlight(call.ResponseStream.Current);
                    flights.Append(flight);
                }
            }
            return await Task.FromResult(flights);
        }
        
        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            generatedcode.TravelRequest request = ProtoUtils.createEmptyRequest();
            IEnumerable<Flight> flights = new List<Flight>();
            /*using (var call = client.FilterFlightByAvailableSeats(request))
            {
                while (call.ResponseStream.MoveNext() != null)
                {
                    try
                    {
                        Flight flight = ProtoUtils.getFlight(call.ResponseStream.Current);
                        flights.Append(flight);
                    }
                    catch (InvalidOperationException ex)
                    {
                        Console.WriteLine("VALEUUU");
                    }
                }
            }

            return flights;
            return readInputStream(request).Result;
        }

        public void addTicket(Ticket ticket)
        {
            
        }*/
    }
}