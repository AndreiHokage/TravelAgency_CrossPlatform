namespace NET.model
{
    public class Ticket : IIdentifiable<int>
    {
        public string CustomerName { set; get; }

        public string TouristName { set; get; }

        public string CustomerAddress { set; get; }

        public int Seats { set; get; }
        
        public Flight FlightForTicket { set; get; }

        public Ticket(int ID ,string customerName, string touristName, string customerAddress, int seats, Flight flightForTicket)
        {
            base.ID = ID;
            CustomerName = customerName;
            TouristName = touristName;
            CustomerAddress = customerAddress;
            Seats = seats;
            FlightForTicket = flightForTicket;
        }
        
        public Ticket(string customerName, string touristName, string customerAddress, int seats, Flight flightForTicket)
        {
            CustomerName = customerName;
            TouristName = touristName;
            CustomerAddress = customerAddress;
            Seats = seats;
            FlightForTicket = flightForTicket;
        }

        public override string ToString()
        {
            return ID + ";" + CustomerName + ";" + TouristName + ";" + CustomerName + ";" + Seats + ";" +
                   FlightForTicket;
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