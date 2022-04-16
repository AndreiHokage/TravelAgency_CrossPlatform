using System;
using System.Collections.Generic;
using travelModel;

namespace travelPersistence
{
    public interface FlightRepository : IRepository<Flight,int>
    {
        IEnumerable<Flight> filterFlightByDestinationAndDeparture(string destination, DateTime departure);
        IEnumerable<Flight> filterFlightByAvailableSeats();
    }
}