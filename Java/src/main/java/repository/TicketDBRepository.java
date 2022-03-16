package repository;

import model.Flight;
import model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TicketDBRepository implements TicketRepository{

    private final static Logger logger = LogManager.getLogger(TicketDBRepository.class);
    private JdbcUtils dbUtils;
    private FlightRepository flightRepository;

    public TicketDBRepository(FlightRepository flightRepository){
        logger.info("creating TicketDBRepository");
        this.dbUtils = new JdbcUtils();
        this.flightRepository = flightRepository;
    }

    @Override
    public void add(Ticket elem) {
        logger.traceEntry("save ticket {} ", elem);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement insertStmt = connection
                    .prepareStatement("insert into Tickets(customerName,touristName,customerAddress,seats,FlightID) " +
                            "values (?,?,?,?,?)")) {
            insertStmt.setString(1, elem.getCustomerName());
            insertStmt.setString(2,elem.getTouristName());
            insertStmt.setString(3,elem.getCustomerAddress());
            insertStmt.setInt(4,elem.getSeats());
            insertStmt.setInt(5,elem.getFlight().getID());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Ticket elem) {
        logger.traceEntry("delete ticket {} ", elem);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement deleteStmt = connection
                    .prepareStatement("delete from Tickets where id = ?")) {
            deleteStmt.setInt(1,elem.getID());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Ticket elem, Integer id) {
        logger.traceEntry("update ticket {} with id {}", elem, id);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement updateStmt = connection
                    .prepareStatement("update Tickets set customerName = ? ,touristName = ? " +
                            ",customerAddress = ? ,seats = ?, FlightID = ? where id = ?")) {
            updateStmt.setString(1,elem.getCustomerName());
            updateStmt.setString(2,elem.getTouristName());
            updateStmt.setString(3,elem.getCustomerAddress());
            updateStmt.setInt(4,elem.getSeats());
            updateStmt.setInt(5,elem.getFlight().getID());
            updateStmt.setInt(6,id);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public Ticket findByID(Integer id) {
        logger.traceEntry("finding ticket with id {}", id);
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement findStmt = connection
                    .prepareStatement("select * from Tickets T inner join Flights F " +
                            "on T.FlightID = F.id where id = ?")) {
            findStmt.setInt(1,id);
            ResultSet resultSet = findStmt.executeQuery();
            if(resultSet.next()){
                Ticket ticket = buildTicket(resultSet);;
                return ticket;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit("No ticket found with id {}", id);
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        logger.traceEntry("find all tickets");
        List<Ticket> ticketList = new ArrayList<>();
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement selectStmt = connection
                    .prepareStatement("select * from Tickets T inner join Flights F on T.FlightID = F.id")) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()){
                Ticket ticket = buildTicket(resultSet);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.traceExit();
        return ticketList;
    }

    @Override
    public Collection<Ticket> getAll() {
        logger.traceEntry("get all tickets");
        List<Ticket> ticketList = new ArrayList<>();
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement selectStmt = connection
                    .prepareStatement("select * from Tickets T inner join Flights F on T.FlightID = F.id")) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()){
                Ticket ticket = buildTicket(resultSet);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return ticketList;
    }

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String customerName = resultSet.getString("customerName");
        String touristName = resultSet.getString("touristName");
        String customerAddress = resultSet.getString("customerAddress");
        Integer seats = resultSet.getInt("seats");
        Integer flightID = resultSet.getInt("FlightID");

        Flight flight = flightRepository.findByID(flightID);
        Ticket ticket = new Ticket(id,customerName,touristName,customerAddress,seats,flight);
        return ticket;
    }
}
