using System;
using System.Collections.Generic;
using travelModel;

namespace travelServices
{

    public interface ITravelServices
    {
        void login(Employee employee, ITravelObserver client);
        void logout(Employee employee, ITravelObserver client);
        IEnumerable<Flight> filterFlightsByDestinationAndDeparture(String destination, DateTime departure);
        IEnumerable<Flight> filterFlightByAvailableSeats();
        void addTicket(Ticket ticket);
    }
}