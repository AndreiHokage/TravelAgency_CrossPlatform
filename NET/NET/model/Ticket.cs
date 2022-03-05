namespace NET.model
{
    public class Ticket : IIdentifiable<int>
    {
        private int ID; 

        public string CustomerName { set; get; }

        public string TouristName { set; get; }

        public string CustomerAddress { set; get; }

        public int Seats { set; get; }

        public Ticket(int ID, string customerName, string touristName, string customerAddress, int seats)
        {
            this.ID = ID;
            CustomerName = customerName;
            TouristName = touristName;
            CustomerAddress = customerAddress;
            Seats = seats;
        }

        public int getID()
        {
            return ID;
        }

        public void setID(int ID)
        {
            this.ID = ID;
        }

        public override string ToString()
        {
            return ID + ";" + CustomerAddress + ";" + TouristName + ";" + CustomerAddress + ";" + Seats;
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