package services;

import model.Flight;
import model.Ticket;
import model.validators.TicketValidator;
import repository.TicketRepository;

import java.util.Collection;

public class TicketServices {
    private TicketRepository ticketRepository;
    private TicketValidator ticketValidator;
    private FlightServices flightServices;

    public TicketServices(TicketRepository ticketRepository,TicketValidator ticketValidator
            ,FlightServices flightServices) {
        this.ticketRepository = ticketRepository;
        this.ticketValidator = ticketValidator;
        this.flightServices = flightServices;
    }

    public void addTicket(String customerName, String touristName, String customerAddress, Integer seats, Flight flight){
        Flight flightForTicket = flightServices.findFlightByID(flight.getID());
        Ticket saveTicket = new Ticket(customerName,touristName,customerAddress,seats,flightForTicket);
        ticketValidator.validate(saveTicket);

        flightServices.updateFlight(flight.getDestination(), flight.getDeparture(),
                flight.getAirport(), flight.getAvailableSeats() - seats, flight.getID());
        ticketRepository.add(saveTicket);
    }

    public void deleteTicket(String customerName, String touristName, String customerAddress, Integer seats, Flight flight, Integer Id){
        Ticket removeTicket = new Ticket(Id,customerName,touristName,customerAddress,seats,flight);
        ticketRepository.delete(removeTicket);
    }

    public void updateTicket(String customerName, String touristName, String customerAddress, Integer seats, Flight flight, Integer Id){
        Ticket upTicket = new Ticket(Id,customerName,touristName,customerAddress,seats,flight);
        ticketValidator.validate(upTicket);
        ticketRepository.update(upTicket,Id);
    }

    public Ticket findTicketByID(Integer Id){
        return ticketRepository.findByID(Id);
    }

    public Iterable<Ticket> findAllTickets(){
        return ticketRepository.findAll();
    }

    public Collection<Ticket> getAllTickets(){
        return ticketRepository.getAll();
    }
}
