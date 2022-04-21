using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Threading.Tasks;
using generatedcode;
using Grpc.Core;
using log4net.Config;
using travelModel.validators;
using travelNetworking.gRPC;
using travelPersistence;
using travelServer.services;

namespace travelServer
{
    public class StartServerGrpc
    {
        /*private const int Port = 50000;

        private static string GetConnectionStringByName(string name)
        {
            string returnValue = null;

            var settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }

        public static void Main(string[] args)
        {
            XmlConfigurator.Configure(new FileInfo(args[0]));
            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString", GetConnectionStringByName("travelDB"));

            var flightValidator = new FlightValidator();
            var ticketValidator = new TicketValidator();
            EmployeeRepository employeeRepository = new EmployeeDBRepository(props);
            FlightRepository flightRepository = new FlightDBRepository(props);
            var ticketRepository = new TicketDBRepository(props, flightRepository);

            var server = new Server
            {
                Services =
                {
                    ITravelServicesProto.BindService(new TravelServiceImplGRPC(employeeRepository, flightRepository,
                        ticketRepository, flightValidator, ticketValidator))
                },
                Ports = {new ServerPort("localhost", Port, ServerCredentials.Insecure)}
            };
            server.Start();
            Console.WriteLine("StartServerGrpc server listening on port " + Port);
            Console.WriteLine("Press any key to stop the server ...");
            Console.ReadKey();
            server.ShutdownAsync().Wait();
        }
    }

    internal class TravelServiceImplGRPC : ITravelServicesProto.ITravelServicesProtoBase
    {
        private readonly EmployeeServices employeeServices;
        private readonly FlightServices flightServices;
        private readonly IDictionary<string, bool> loggedClients;
        private readonly TicketServices ticketServices;

        private EmployeeRepository employeeRepository;
        private FlightRepository flightRepository;
        private FlightValidator flightValidator;
        private TicketRepository ticketRepository;
        private TicketValidator ticketValidator;

        public TravelServiceImplGRPC(EmployeeRepository employeeRepository, FlightRepository flightRepository,
            TicketRepository ticketRepository, FlightValidator flightValidator, TicketValidator ticketValidator)
        {
            Console.WriteLine("Creating TravelServiceImplGRPC...");
            this.employeeRepository = employeeRepository;
            this.flightRepository = flightRepository;
            this.ticketRepository = ticketRepository;
            this.flightValidator = flightValidator;
            this.ticketValidator = ticketValidator;

            employeeServices = new EmployeeServices(employeeRepository);
            flightServices = new FlightServices(flightRepository, flightValidator);
            ticketServices = new TicketServices(ticketRepository, ticketValidator, flightServices);

            //this.loggedClients = new System.Collections.Generic.Dictionary<String, ITravelObserver>();
            loggedClients = new Dictionary<string, bool>();
        }

        private EmployeeServices getEmployeeService()
        {
            return employeeServices;
        }

        private FlightServices getFlightService()
        {
            return flightServices;
        }

        private TicketServices getTicketServices()
        {
            return ticketServices;
        }

        public override Task<TravelResponse> Login(Employee request, ServerCallContext context)
        {
            Console.WriteLine("LogIn service method ...");
            var employee = ProtoUtils.getUser(request);
            var employeeOK = getEmployeeService().logIn(employee.Username, employee.Password);
            if (employeeOK)
            {
                if (loggedClients.ContainsKey(employee.Username))
                    return Task.FromResult(ProtoUtils.createErrorMessage("Employee already logged in!"));

                loggedClients[employee.Username] = true;
                return Task.FromResult(ProtoUtils.createOkResponse());
            }

            return Task.FromResult(ProtoUtils.createErrorMessage("Authentificaton failed !"));
        }

        public override Task<TravelResponse> Logout(Employee request, ServerCallContext context)
        {
            Console.WriteLine("Logout service method ...");
            var employee = ProtoUtils.getUser(request);
            if (loggedClients.ContainsKey(employee.Username))
                return Task.FromResult(
                    ProtoUtils.createErrorMessage("Employee " + employee.Username + " not logged in"));
            loggedClients.Remove(employee.Username);
            return Task.FromResult(ProtoUtils.createOkResponse());
        }

        public override async Task FilterFlightsByDestinationAndDeparture(FlightDestDepDTO request,
            IServerStreamWriter<Flight> responseStream, ServerCallContext context)
        {
            var flightDestDepDto = ProtoUtils.getFlightDestDepDTO(request);
            var destination = flightDestDepDto.Destination;
            var departure = flightDestDepDto.Departure;
            var flights =
                getFlightService().filterFlightsByDestinationAndDeparture(destination, departure);
            foreach (var flight in flights) await responseStream.WriteAsync(ProtoUtils.createFlightMessage(flight));
        }

        public override async Task FilterFlightByAvailableSeats(TravelRequest request,
            IServerStreamWriter<Flight> responseStream, ServerCallContext context)
        {
            var flights = getFlightService().filterFlightByAvailableSeats();
            foreach (var flight in flights) await responseStream.WriteAsync(ProtoUtils.createFlightMessage(flight));
        }

        public override Task AddTicket(IAsyncStreamReader<Ticket> requestStream,
            IServerStreamWriter<Ticket> responseStream, ServerCallContext context)
        {
            return Task.FromResult(ProtoUtils.createOkResponse());
        }

        public override Task<TravelResponse> AddFlight(Flight request, ServerCallContext context)
        {
            return Task.FromResult(ProtoUtils.createOkResponse());
        }*/
    }
}