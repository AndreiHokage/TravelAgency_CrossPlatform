using System;
using System.Collections;
using System.Collections.Generic;
using travelModel;
using travelServices;

namespace ProiectCS
{
    public class ClientCtrl: ITravelObserver
    {
        public event EventHandler<TravelUserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly ITravelServices server;
        private Employee _employee;

        public ClientCtrl(ITravelServices server)
        {
            this.server = server;
            this._employee = null;
        }

        public void login(String username, String password)
        {
            Employee employee = new Employee(username, password);
            server.login(employee, this);
            Console.WriteLine("Login succeeded ...");
            this._employee = employee;
            Console.WriteLine("Current employee {0} ", this._employee);
        }

        public void logout()
        {
            Console.WriteLine("Ctrl logout ...");
            server.logout(_employee, this);
            this._employee = null;
        }

        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            Console.WriteLine("Ctrl filter flights bt destination and departure ...");
            IEnumerable<Flight> flights = server.filterFlightsByDestinationAndDeparture(destination, departure);
            return flights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            Console.WriteLine("Ctrl filter flights by available seats ...");
            IEnumerable<Flight> flights = server.filterFlightByAvailableSeats();
            return flights;
        }

        public void addTicket(Ticket ticket)
        {
            Console.WriteLine("Ctrl buy ticket " + ticket);
            server.addTicket(ticket);
        }

        public void soldTicket(Ticket ticket)
        {
            Console.WriteLine("Ctrl notify sold ticket " + ticket);
            TravelUserEventArgs userArgs = new TravelUserEventArgs(TravelUserEvent.BuyTicket, ticket);
            OnUserEvent(userArgs);
        }

        protected virtual void OnUserEvent(TravelUserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event Called");
        }
    }
}