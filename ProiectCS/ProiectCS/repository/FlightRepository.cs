using System;
using System.Collections;
using System.Collections.Generic;
using NET.model;

namespace NET.repository
{
    public interface FlightRepository : IRepository<Flight,int>
    {
        IEnumerable<Flight> filterFlightByDestinationAndDeparture(string destination, DateTime departure);
        IEnumerable<Flight> filterFlightByAvailableSeats();
    }
}