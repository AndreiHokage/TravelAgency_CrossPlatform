package repository;

import model.Employee;

public interface EmployeeRepository extends Repository<Employee,Integer> {
    boolean filterByUsernameAndPassword(String username,String password);
}
