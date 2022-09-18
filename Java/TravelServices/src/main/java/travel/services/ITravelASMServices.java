package travel.services;

import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;

import java.time.LocalDate;
import java.util.Collection;

public interface ITravelASMServices {
    void login(Employee employee) throws TravelException;
    void logout(Employee employee) throws TravelException;
    Collection<Flight> filterFlightsByDestinationAndDeparture(String destination, LocalDate departure) throws TravelException;
    Collection<Flight> filterFlightByAvailableSeats() throws TravelException;
    void addTicket(Ticket ticket) throws TravelException;
    void addFlight(Flight flight) throws TravelException;
}
