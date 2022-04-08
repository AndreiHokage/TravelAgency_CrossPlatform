package chat.persistence;


import travel.model.Flight;

import java.time.LocalDate;
import java.util.Collection;

public interface FlightRepository extends Repository<Flight , Integer>{
    Collection<Flight> filterFlightByDestinationAndDeparture(String destination, LocalDate departure);
    Collection<Flight> filterFlightByAvailableSeats();
}
