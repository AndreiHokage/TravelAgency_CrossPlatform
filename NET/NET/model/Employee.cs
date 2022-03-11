namespace NET.model
{
    public class Employee : IIdentifiable<int>
    {
        public string Username { set; get; }

        public string Password { set; get; }

        public Employee(int ID, string username, string password)
        {
            base.ID = ID;
            Username = username;
            Password = password;
        }
        
        public Employee(string username, string password)
        {
            base.ID = ID;
            Username = username;
            Password = password;
        }

        public override string ToString()
        {
            return ID + ";" + Username + ";" + Password;
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