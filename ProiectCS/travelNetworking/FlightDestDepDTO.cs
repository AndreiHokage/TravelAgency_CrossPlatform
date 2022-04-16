using System;

namespace travelNetworking
{

    [Serializable]
    public class FlightDestDepDTO
    {
        public string Destination { get; set; }
        public DateTime Departure { get; set; }

        public FlightDestDepDTO(string destination, DateTime departure)
        {
            Destination = destination;
            Departure = departure;
        }

        public override string ToString()
        {
            return Destination + ";" + Departure;
        }
    }
}