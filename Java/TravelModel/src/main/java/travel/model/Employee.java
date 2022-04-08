package travel.model;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Identifiable<Integer>, Serializable {
    private Integer ID;
    private String username;
    private String password;

    public Employee(Integer ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public Employee(String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(ID, employee.ID) && Objects.equals(username, employee.username) && Objects.equals(password, employee.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, username, password);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
