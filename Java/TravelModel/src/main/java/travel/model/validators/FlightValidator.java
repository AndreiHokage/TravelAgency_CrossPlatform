package travel.model.validators;


import travel.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightValidator implements Validator<Flight> {
    @Override
    public void validate(Flight entity) throws ValidationException {
        String msg = "";
        if(entity.getDestination().equals(""))
            msg += "Destination cannot be empty !";
        if(entity.getDeparture() == null)
            msg += "Departure cannot be empty !";
        if(entity.getAirport().equals(""))
            msg += "The name of airport cannot be empty !";
        if(entity.getAvailableSeats() == null || entity.getAvailableSeats() <= 0)
            msg += "There are not available seats !";
        if(!msg.equals(""))
            throw new ValidationException(msg);
    }
}
