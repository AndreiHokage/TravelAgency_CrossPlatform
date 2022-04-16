using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using travelModel;
using travelModel.validators;
using travelPersistence;
using travelServer.services;
using travelServices;


namespace travelServer
{

    public class TravelServerImpl : ITravelServices
    {
        private EmployeeRepository employeeRepository;
        private FlightRepository flightRepository;
        private TicketRepository ticketRepository;
        private FlightValidator flightValidator;
        private TicketValidator ticketValidator;

        private EmployeeServices employeeServices;
        private FlightServices flightServices;
        private TicketServices ticketServices;

        private readonly IDictionary<String, ITravelObserver> loggedClients;

        public TravelServerImpl(EmployeeRepository employeeRepository, FlightRepository flightRepository,
            TicketRepository ticketRepository, FlightValidator flightValidator, TicketValidator ticketValidator)
        {
            this.employeeRepository = employeeRepository;
            this.flightRepository = flightRepository;
            this.ticketRepository = ticketRepository;
            this.flightValidator = flightValidator;
            this.ticketValidator = ticketValidator;

            this.employeeServices = new EmployeeServices(employeeRepository);
            this.flightServices = new FlightServices(flightRepository, flightValidator);
            this.ticketServices = new TicketServices(ticketRepository, ticketValidator, this.flightServices);

            this.loggedClients = new System.Collections.Generic.Dictionary<String, ITravelObserver>();
        }

        private EmployeeServices getEmployeeService()
        {
            return this.employeeServices;
        }

        private FlightServices getFlightService()
        {
            return this.flightServices;
        }

        private TicketServices getTicketServices()
        {
            return this.ticketServices;
        }

        public void login(Employee employee, ITravelObserver client)
        {
            Console.WriteLine("LogIn service method ...");
            bool employeeOK = getEmployeeService().logIn(employee.Username, employee.Password);
            if (employeeOK)
            {
                if (loggedClients.ContainsKey(employee.Username))
                    throw new TravelException("User already logged in.");
                loggedClients[employee.Username] = client;
            }
            else
                throw new TravelException("Authentication failed.");
        }

        public void logout(Employee employee, ITravelObserver client)
        {
            Console.WriteLine("Logout service method ...");
            ITravelObserver observer = loggedClients[employee.Username];
            if (observer == null)
                throw new TravelException("Employee " + employee.Username + " not logged in");
            loggedClients.Remove(employee.Username);
        }

        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            IEnumerable<Flight> flights =
                getFlightService().filterFlightsByDestinationAndDeparture(destination, departure);
            return flights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            IEnumerable<Flight> flights = getFlightService().filterFlightByAvailableSeats();
            return flights;
        }

        public void addTicket(Ticket ticket)
        {
            getTicketServices().addTicket(ticket.CustomerName, ticket.TouristName, ticket.CustomerAddress, ticket.Seats,
                ticket.FlightForTicket);
            notifyEmployeeBuyTicket(ticket);
        }

        private void notifyEmployeeBuyTicket(Ticket ticket)
        {
            foreach (System.Collections.Generic.KeyValuePair<String, ITravelObserver> entry in loggedClients)
            {
                String username = entry.Key;
                ITravelObserver client = entry.Value;
                Task.Run(() => { client.soldTicket(ticket); });
            }
        }
    }
}