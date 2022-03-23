package services;

import model.Employee;
import repository.EmployeeRepository;

public class EmployeeServices {
    private EmployeeRepository employeeRepository;

    public EmployeeServices(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(String username,String password){
        Employee saveEmployee = new Employee(username,password);
        employeeRepository.add(saveEmployee);
    }

    public void updateEmployee(String username,String password,Integer Id){
        Employee upEmployee = new Employee(username,password);
        upEmployee.setID(Id);
        employeeRepository.update(upEmployee,Id);
    }

    public boolean logIn(String username,String password){
        boolean isLog = employeeRepository.filterByUsernameAndPassword(username,password);
        return isLog;
    }
}
