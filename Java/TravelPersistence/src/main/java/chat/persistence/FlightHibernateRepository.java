package chat.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import travel.model.Flight;
import travel.model.Ticket;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class FlightHibernateRepository implements FlightRepository{

    private SessionFactory sessionFactory;


    @Autowired
    public FlightHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Flight elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(elem);
                String findStmt = "from Flight ";
                Collection<Flight> flights = session.createQuery(findStmt, Flight.class).list();
                Integer maxi = -1;
                for(Flight flight: flights)
                    if(flight.getID() > maxi)
                        maxi = flight.getID();
                elem.setID(maxi);
                tx.commit();
            }
            catch (RuntimeException ex){
                System.err.println("Error occurred to addFlight method: " + ex.getMessage());
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Flight elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            tx = session.beginTransaction();
            try {
                String selectStmt = "from Flight where ID = :idFlight";
                Flight deleteFlight = session.createQuery(selectStmt, Flight.class).setParameter("idFlight", elem.getID())
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(deleteFlight);
                tx.commit();
            }
            catch (RuntimeException ex){
                System.err.println("Error occurred to deleteFLight: "+ ex.getMessage());
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Flight elem, Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx= null;
            try{
                tx = session.beginTransaction();
                Flight updateFlight = session.load(Flight.class, id);
                updateFlight.setDestination(elem.getDestination());
                updateFlight.setDeparture(elem.getDeparture());
                updateFlight.setAirport(elem.getAirport());
                updateFlight.setAvailableSeats(elem.getAvailableSeats());
                session.update(updateFlight);
                tx.commit();
            }catch (RuntimeException ex){
                System.err.println("Error occurred to updateFlight method: "+ ex.getMessage());
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Flight findByID(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            try{
                String findStmt = "from Flight where ID = :idFlight";
                Flight findFlight = session.createQuery(findStmt, Flight.class).setParameter("idFlight", id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return findFlight;
            }
            catch (RuntimeException ex){
                System.err.println("Error occurred to findByIdFlight : " + ex.getMessage());
                if(tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public Iterable<Flight> findAll() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            try {
                String findStmt = "from Flight ";
                Iterable<Flight> flights = session.createQuery(findStmt, Flight.class).list();
                tx.commit();
                return flights;
            }catch (RuntimeException ex){
                System.err.println("Error occurred to findAllTicket " + ex.getMessage());
                if(tx != null)
                    tx.rollback();
                return new ArrayList<>();
            }
        }
    }

    @Override
    public Collection<Flight> getAll() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            try {
                String findStmt = "from Flight ";
                Collection<Flight> flights = session.createQuery(findStmt, Flight.class).list();
                tx.commit();
                return flights;
            }catch (RuntimeException ex){
                System.err.println("Error occurred to findAllTicket "+ ex.getMessage());
                if(tx != null)
                    tx.rollback();
                return new ArrayList<>();
            }
        }
    }

    @Override
    public Collection<Flight> filterFlightByDestinationAndDeparture(String destination, LocalDate departure) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            try{
                String findStmt = "from Flight flg where flg.availableSeats > 0 and "+
                        " flg.destination = :destination and year(flg.departure) = :year and " +
                        "month(flg.departure) = :month and day(flg.departure) = :day";
                Collection<Flight> flights = session.createQuery(findStmt, Flight.class)
                        .setParameter("destination", destination)
                        .setParameter("year", departure.getYear())
                        .setParameter("month", departure.getMonthValue())
                        .setParameter("day", departure.getDayOfMonth()).list();
                tx.commit();
                return flights;
            }
            catch (RuntimeException ex){
                System.err.println("Error occurred to filterFlightsByDestinationAndDeparture method " + ex.getMessage());
                if(tx != null)
                    tx.rollback();
                return new ArrayList<>();
            }
        }
    }

    @Override
    public Collection<Flight> filterFlightByAvailableSeats() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            try{
                String findStmt = "from Flight as flg where flg.availableSeats > 0";
                Collection<Flight> flights = session.createQuery(findStmt, Flight.class).list();
                tx.commit();
                return flights;
            }
            catch (RuntimeException ex){
                System.err.println("Error occurred to filterFlightByAvailableSeats method " + ex.getMessage());
                ex.printStackTrace();
                if(tx != null)
                    tx.rollback();
                return new ArrayList<>();
            }
        }
    }
}
