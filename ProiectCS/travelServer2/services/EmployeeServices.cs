using travelModel;
using travelPersistence;

namespace travelServer.services
{
    public class EmployeeServices
    {
        private readonly EmployeeRepository employeeRepository;

        public EmployeeServices(EmployeeRepository employeeRepository)
        {
            this.employeeRepository = employeeRepository;
        }

        public void addEmployee(string username, string password)
        {
            var saveEmployee = new Employee(username, password);
            employeeRepository.Save(saveEmployee);
        }

        public void updateEmployee(string username, string password, int Id)
        {
            var upEmployee = new Employee(Id, username, password);
            employeeRepository.Update(upEmployee, Id);
        }

        public bool logIn(string username, string password)
        {
            var isLogin = employeeRepository.filterByUsernameAndPassword(username, password);
            return isLogin;
        }
    }
}