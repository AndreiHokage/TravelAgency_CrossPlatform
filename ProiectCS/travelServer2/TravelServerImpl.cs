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
        private readonly EmployeeServices employeeServices;
        private readonly FlightServices flightServices;
        private readonly IDictionary<string, ITravelObserver> loggedClients;
        private readonly TicketServices ticketServices;
        private EmployeeRepository employeeRepository;
        private FlightRepository flightRepository;
        private FlightValidator flightValidator;
        private TicketRepository ticketRepository;
        private TicketValidator ticketValidator;

        public TravelServerImpl(EmployeeRepository employeeRepository, FlightRepository flightRepository,
            TicketRepository ticketRepository, FlightValidator flightValidator, TicketValidator ticketValidator)
        {
            this.employeeRepository = employeeRepository;
            this.flightRepository = flightRepository;
            this.ticketRepository = ticketRepository;
            this.flightValidator = flightValidator;
            this.ticketValidator = ticketValidator;

            employeeServices = new EmployeeServices(employeeRepository);
            flightServices = new FlightServices(flightRepository, flightValidator);
            ticketServices = new TicketServices(ticketRepository, ticketValidator, flightServices);

            loggedClients = new Dictionary<string, ITravelObserver>();
        }

        public void login(Employee employee, ITravelObserver client)
        {
            Console.WriteLine("LogIn service method ...");
            var employeeOK = getEmployeeService().logIn(employee.Username, employee.Password);
            if (employeeOK)
            {
                if (loggedClients.ContainsKey(employee.Username))
                    throw new TravelException("User already logged in.");
                loggedClients[employee.Username] = client;
            }
            else
            {
                throw new TravelException("Authentication failed.");
            }
        }

        public void logout(Employee employee, ITravelObserver client)
        {
            Console.WriteLine("Logout service method ...");
            var observer = loggedClients[employee.Username];
            if (observer == null)
                throw new TravelException("Employee " + employee.Username + " not logged in");
            loggedClients.Remove(employee.Username);
        }

        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            var flights =
                getFlightService().filterFlightsByDestinationAndDeparture(destination, departure);
            return flights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            var flights = getFlightService().filterFlightByAvailableSeats();
            return flights;
        }

        public void addTicket(Ticket ticket)
        {
            getTicketServices().addTicket(ticket.CustomerName, ticket.TouristName, ticket.CustomerAddress, ticket.Seats,
                ticket.FlightForTicket);
            notifyEmployeeBuyTicket(ticket);
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

        private void notifyEmployeeBuyTicket(Ticket ticket)
        {
            foreach (var entry in loggedClients)
            {
                var username = entry.Key;
                var client = entry.Value;
                Task.Run(() => { client.soldTicket(ticket); });
            }
        }
    }
}