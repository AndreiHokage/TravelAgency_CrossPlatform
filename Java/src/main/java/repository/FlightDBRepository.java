package repository;

import model.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FlightDBRepository implements FlightRepository{

    private final static Logger logger =  LogManager.getLogger(FlightDBRepository.class);
    private JdbcUtils dbUtils;

    public FlightDBRepository(){
        logger.info("creating FlightDBRepository");
        dbUtils = new JdbcUtils();
    }

    @Override
    public void add(Flight elem) {
        logger.traceEntry("saving Flight {} ", elem);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement insertStmt = connection.
                    prepareStatement("insert into Flights(destination,departure,airport,availableSeats)" +
                            "values (?,?,?,?)")) {
            insertStmt.setString(1,elem.getDestination());
            insertStmt.setTimestamp(2, Timestamp.valueOf(elem.getDeparture()) );
            insertStmt.setString(3, elem.getAirport());
            insertStmt.setInt(4,elem.getAvailableSeats());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Flight elem) {
        logger.traceEntry("delete flight {} " ,elem);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement deleteStmt = connection
                    .prepareStatement("delete from Flights where id = ?")) {
            deleteStmt.setInt(1,elem.getID());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Flight elem, Integer id) {
        logger.traceEntry("update flight {} with id {}", elem, id);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement updateStmt = connection
                    .prepareStatement("update Flights set destination = ? , departure = ? " +
                            ",airport = ? , availableSeats = ? where id = ?")) {
            updateStmt.setString(1,elem.getDestination());
            updateStmt.setTimestamp(2,Timestamp.valueOf(elem.getDeparture()));
            updateStmt.setString(3,elem.getAirport());
            updateStmt.setInt(4,elem.getAvailableSeats());
            updateStmt.setInt(5,id);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public Flight findByID(Integer id) {
        logger.traceEntry("finding Flight with id {}",id);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement findStmt = connection
                    .prepareStatement("select * from Flights where id = ?")) {
            findStmt.setInt(1,id);
            ResultSet resultSet = findStmt.executeQuery();
            if( resultSet.next()  ) {
                String destination = resultSet.getString("destination");
                LocalDateTime departure = resultSet.getTimestamp("departure").toLocalDateTime();
                String airport = resultSet.getString("airport");
                Integer availableSeats = resultSet.getInt("availableSeats");

                Flight flight = new Flight(id, destination, departure, airport, availableSeats);
                return flight;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit("No Flight found with id {}",id);
        return null;
    }

    @Override
    public Iterable<Flight> findAll() {
        logger.traceEntry("find all flight");
        List<Flight> flightList = new ArrayList<>();
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement findAllStmt = connection.prepareStatement("select * from Flights")) {
            ResultSet resultSet = findAllStmt.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt("id");
                String destination = resultSet.getString("destination");
                LocalDateTime departure = resultSet.getTimestamp("departure").toLocalDateTime();
                String airport = resultSet.getString("airport");
                Integer availableSeats = resultSet.getInt("availableSeats");

                Flight flight = new Flight(id, destination, departure, airport, availableSeats);
                flightList.add(flight);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return flightList;
    }

    @Override
    public Collection<Flight> getAll() {
        logger.traceEntry("get all flights");
        List<Flight> flightList = new ArrayList<>();
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement findAllStmt = connection.prepareStatement("select * from Flights")) {
            ResultSet resultSet = findAllStmt.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt("id");
                String destination = resultSet.getString("destination");
                LocalDateTime departure = resultSet.getTimestamp("departure").toLocalDateTime();
                String airport = resultSet.getString("airport");
                Integer availableSeats = resultSet.getInt("availableSeats");

                Flight flight = new Flight(id, destination, departure, airport, availableSeats);
                flightList.add(flight);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return flightList;
    }
}
