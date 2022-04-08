package travel.server;

import chat.persistence.EmployeeRepository;
import chat.persistence.FlightRepository;
import chat.persistence.TicketRepository;
import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;
import travel.model.validators.FlightValidator;
import travel.model.validators.TicketValidator;
import travel.services.ITravelObserver;
import travel.services.ITravelServices;
import travel.services.TravelException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TravelServicesImpl implements ITravelServices {
    private EmployeeRepository employeeRepository;
    private FlightRepository flightRepository;
    private FlightValidator flightValidator;
    private TicketRepository ticketRepository;
    private TicketValidator ticketValidator;
    private Map<String, ITravelObserver> loggedEmployee;

    public TravelServicesImpl(EmployeeRepository employeeRepository, FlightRepository flightRepository, FlightValidator flightValidator,
                              TicketRepository ticketRepository, TicketValidator ticketValidator) {
        this.employeeRepository = employeeRepository;
        this.flightRepository = flightRepository;
        this.flightValidator = flightValidator;
        this.ticketRepository = ticketRepository;
        this.ticketValidator = ticketValidator;
        loggedEmployee = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Employee employee, ITravelObserver client) throws TravelException {
        boolean isLogged = employeeRepository.filterByUsernameAndPassword(employee.getUsername(), employee.getPassword());
        if(isLogged){
            if(loggedEmployee.get(employee.getUsername()) != null)
                throw new TravelException("Employee already logged in");
            loggedEmployee.put(employee.getUsername(), client);
        }else
            throw  new TravelException("Authentication failed");
    }

    @Override
    public synchronized void logout(Employee employee, ITravelObserver client) throws TravelException {
        ITravelObserver localClient = loggedEmployee.remove(employee.getUsername());
        if( localClient == null )
            throw new TravelException("Employee " + employee.getUsername() + " is not logged in.");

    }

    @Override
    public synchronized Collection<Flight> filterFlightsByDestinationAndDeparture(String destination, LocalDate departure) throws TravelException {
        Collection<Flight> flights = flightRepository.filterFlightByDestinationAndDeparture(destination, departure);
        return flights;
    }

    @Override
    public synchronized Collection<Flight> filterFlightByAvailableSeats() throws TravelException {
        Collection<Flight> flights = flightRepository.filterFlightByAvailableSeats();
        return flights;
    }

    @Override
    public synchronized void addTicket(Ticket ticket) throws TravelException {
        Flight flight = ticket.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() - ticket.getSeats());
        flightValidator.validate(flight);
        ticketValidator.validate(ticket);
        flightRepository.update(flight, flight.getID());

        ticket.setFlight(flightRepository.findByID(flight.getID()));
        ticketRepository.add(ticket);
        notifyEmployeeBuyTicket(ticket);
    }

    @Override
    public synchronized void addFlight(Flight flight) throws TravelException {
        flightRepository.add(flight);
        flightValidator.validate(flight);
        notifyEmployeeFlightSave(flight);
    }

    private final int defaultThreadsNo=5;
    private void notifyEmployeeFlightSave(Flight flight){
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for(Map.Entry<String, ITravelObserver> elem : loggedEmployee.entrySet()){
            String username = elem.getKey();
            ITravelObserver client = elem.getValue();
            executor.execute(() -> {
                try {
                    System.out.println("Notifying [" + username + "] that a flight was added");
                    client.saveFlight(flight);
                } catch (TravelException e) {
                    System.err.println("Error notifying friend " + e);
                }
            });
        }
        executor.shutdown();
    }

    private void notifyEmployeeBuyTicket(Ticket ticket){
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for(Map.Entry<String, ITravelObserver> elem : loggedEmployee.entrySet()){
            String username = elem.getKey();
            ITravelObserver client = elem.getValue();
            executor.execute(() -> {
                try {
                    System.out.println("Notifying [" + username + "] that a flight was added");
                    client.soldTicket(ticket);
                } catch (TravelException e) {
                    System.err.println("Error notifying friend " + e);
                }
            });
        }
        executor.shutdown();
    }
}
