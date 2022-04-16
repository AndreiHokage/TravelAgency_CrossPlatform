using System;

namespace travelModel
{
    [Serializable]
    public class Employee : IIdentifiable<int>
    {
        public int ID { set; get; }
        public string Username { set; get; }

        public string Password { set; get; }

        public Employee(int ID, string username, string password)
        {
            this.ID = ID;
            Username = username;
            Password = password;
        }
        
        public Employee(string username, string password)
        {
            Username = username;
            Password = password;
        }

        public override string ToString()
        {
            return ID + ";" + Username + ";" + Password;
        }

        public override bool Equals(object obj)
        {
            Employee employee = obj as Employee;
            if (employee == null)
                return false;
            return ID == employee.ID;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}