

using travelModel;

namespace travelPersistence
{
    public interface EmployeeRepository :IRepository<Employee,int>
    {
        bool filterByUsernameAndPassword(string username, string password);
    }
}