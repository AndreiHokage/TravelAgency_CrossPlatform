using System;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Sockets;
using System.Threading;
using log4net.Config;
using travelModel.validators;
using travelNetworking;
using travelPersistence;
using travelServices;


namespace travelServer
{

    public class StartServer
    {
        static string GetConnectionStringByName(String name)
        {
            string returnValue = null;

            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }

        public static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString", GetConnectionStringByName("travelDB"));

            FlightValidator flightValidator = new FlightValidator();
            TicketValidator ticketValidator = new TicketValidator();
            EmployeeRepository employeeRepository = new EmployeeDBRepository(props);
            FlightRepository flightRepository = new FlightDBRepository(props);
            TicketDBRepository ticketRepository = new TicketDBRepository(props, flightRepository);

            TravelServerImpl serviceImpl = new TravelServerImpl(employeeRepository, flightRepository, ticketRepository,
                flightValidator, ticketValidator);

            ServerUtils.AbstractServer server = new SerialTravelServer(serviceImpl, "127.0.0.1", 55556);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();

        }

        public class SerialTravelServer : ServerUtils.ConcurrentServer
        {
            private ITravelServices server;
            private TravelServerObjectWorker worker;

            public SerialTravelServer(ITravelServices server, string host, int port) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("SerialTravelServer ...");
            }

            protected override Thread createWorker(TcpClient client)
            {
                worker = new TravelServerObjectWorker(server, client);
                Thread thread = new Thread(new ThreadStart(worker.run));
                return thread;
            }
        }
    }
}