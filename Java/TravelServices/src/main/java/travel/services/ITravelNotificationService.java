package travel.services;

import travel.model.Flight;
import travel.model.Ticket;

public interface ITravelNotificationService {
    void soldTicket(Ticket ticket) throws TravelException;
    void saveFlight(Flight flight) throws TravelException;
}
