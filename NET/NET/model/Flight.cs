using System;

namespace NET.model
{
    public class Flight : IIdentifiable<int>
    {
        public string Destination { set; get; }

        public DateTime Departure { set; get; }

        public string Airport { set; get; }

        public int AvailableSeats { set; get; }

        public Flight(int ID, string destination, DateTime departure, string airport, int availableSeats)
        {
            base.ID = ID;
            Destination = destination;
            Departure = departure;
            Airport = airport;
            AvailableSeats = availableSeats;
        }
        
        public Flight(string destination, DateTime departure, string airport, int availableSeats)
        {
            Destination = destination;
            Departure = departure;
            Airport = airport;
            AvailableSeats = availableSeats;
        }
        
        public override string ToString()
        {
            return ID + ";" + Destination + ";" + Departure + ";" + Airport + ";" + AvailableSeats;
        }

        public override bool Equals(object obj)
        {
            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}