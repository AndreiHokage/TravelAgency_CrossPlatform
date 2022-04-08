package chat.network;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {
    private String username;
    private String password;

    public EmployeeDTO(String username){
        this(username, "");
    }

    public EmployeeDTO(String username, String password) {
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
    public String toString() {
        return "EmployeeDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
