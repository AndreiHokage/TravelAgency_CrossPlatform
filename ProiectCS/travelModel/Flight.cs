using System;

namespace travelModel
{
    [Serializable]
    public class Flight : IIdentifiable<int>
    {
        public int ID { get; set;  }
        public string Destination { set; get; }

        public DateTime Departure { set; get; }

        public string Airport { set; get; }

        public int AvailableSeats { set; get; }

        public Flight()
        {
            
        }
        
        public Flight(int ID, string destination, DateTime departure, string airport, int availableSeats)
        {
            this.ID = ID;
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
            Flight flight = obj as Flight;
            if (obj == null)
                return false;
            return ID == flight.ID;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}