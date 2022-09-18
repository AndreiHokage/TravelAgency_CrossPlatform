package travel.server;

import chat.persistence.EmployeeRepository;
import chat.persistence.FlightRepository;
import chat.persistence.TicketRepository;
import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;
import travel.model.validators.FlightValidator;
import travel.model.validators.TicketValidator;
import travel.services.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TravelServicesASMImpl implements ITravelASMServices {

    private EmployeeRepository employeeRepository;
    private FlightRepository flightRepository;
    private FlightValidator flightValidator;
    private TicketRepository ticketRepository;
    private TicketValidator ticketValidator;
    private Map<String, Employee> loggedEmployee;
    private ITravelNotificationService notificationService;

    public TravelServicesASMImpl(EmployeeRepository employeeRepository, FlightRepository flightRepository, FlightValidator flightValidator,
                              TicketRepository ticketRepository, TicketValidator ticketValidator, ITravelNotificationService notificationService) {
        this.employeeRepository = employeeRepository;
        this.flightRepository = flightRepository;
        this.flightValidator = flightValidator;
        this.ticketRepository = ticketRepository;
        this.ticketValidator = ticketValidator;
        this.notificationService = notificationService;
        loggedEmployee = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Employee employee) throws TravelException {
        boolean isLogged = employeeRepository.filterByUsernameAndPassword(employee.getUsername(), employee.getPassword());
        if(isLogged){
            if(loggedEmployee.get(employee.getUsername()) != null)
                throw new TravelException("Employee already logged in");
            loggedEmployee.put(employee.getUsername(), employee);
        }else
            throw  new TravelException("Authentication failed");

    }

    @Override
    public synchronized void logout(Employee employee) throws TravelException {
        Employee localClient = loggedEmployee.remove(employee.getUsername());
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
        notificationService.soldTicket(ticket);
    }

    @Override
    public synchronized void addFlight(Flight flight) throws TravelException {
        flightValidator.validate(flight);
        flightRepository.add(flight);
        notificationService.saveFlight(flight);

    }
}
