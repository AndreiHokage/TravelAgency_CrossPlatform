using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
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

            var serviceImpl = new TravelServerImpl(employeeRepository, flightRepository, ticketRepository,
                flightValidator, ticketValidator);

            ServerUtils.AbstractServer server = new ProtoV3ChatServer(serviceImpl, "127.0.0.1", 50000);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();
        }

        public class SerialTravelServer : ServerUtils.ConcurrentServer
        {
            private readonly ITravelServices server;
            private TravelServerObjectWorker worker;

            public SerialTravelServer(ITravelServices server, string host, int port) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("SerialTravelServer ...");
            }

            protected override Thread createWorker(TcpClient client)
            {
                worker = new TravelServerObjectWorker(server, client);
                var thread = new Thread(worker.run);
                return thread;
            }
        }
        
        public class ProtoV3ChatServer : ServerUtils.ConcurrentServer
        {
            private ITravelServices server;
            private TravelProtoWorker worker;
            public ProtoV3ChatServer(ITravelServices server, string host, int port)
                : base(host, port)
            {
                this.server = server;
                Console.WriteLine("ProtoChatServer...");
            }
            protected override Thread createWorker(TcpClient client)
            {
                worker = new TravelProtoWorker(server, client);
                return new Thread(new ThreadStart(worker.run));
            }
        }
    }
}