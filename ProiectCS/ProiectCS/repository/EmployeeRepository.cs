using NET.model;

namespace NET.repository
{
    public interface EmployeeRepository :IRepository<Employee,int>
    {
        bool filterByUsernameAndPassword(string username, string password);
    }
}