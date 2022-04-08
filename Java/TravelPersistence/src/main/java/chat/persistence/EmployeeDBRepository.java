package chat.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import travel.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class EmployeeDBRepository implements EmployeeRepository{

    private final static Logger logger = LogManager.getLogger(EmployeeDBRepository.class);
    private JdbcUtils dbUtils;

    public EmployeeDBRepository(Properties properties){
        logger.info("creating EmployeeDBRepository");
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public void add(Employee elem) {
        logger.traceEntry("add an employee {}", elem);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement insertStmt = connection
                    .prepareStatement("insert into Employee(username,password) values (?,?)")) {
            insertStmt.setString(1,elem.getUsername());
            insertStmt.setString(2,elem.getPassword());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Employee elem) {

    }

    @Override
    public void update(Employee elem, Integer id) {
        logger.traceEntry("update an employee {} with id {}", elem ,id);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement updateStmt = connection
                    .prepareStatement("update Employee set username = ?,password =? where id =?")) {
            updateStmt.setString(1,elem.getUsername());
            updateStmt.setString(2, elem.getPassword());
            updateStmt.setInt(3,id);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public Employee findByID(Integer id) {
        return null;
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Collection<Employee> getAll() {
        return null;
    }

    @Override
    public boolean filterByUsernameAndPassword(String username, String password) {
        logger.traceEntry("filter by username and password");
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement filterStmt = connection
                    .prepareStatement("select * from Employee where username = ? and password = ?")) {
            filterStmt.setString(1,username);
            filterStmt.setString(2,password);
            ResultSet resultSet = filterStmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.traceExit();
        return false;
    }
}
