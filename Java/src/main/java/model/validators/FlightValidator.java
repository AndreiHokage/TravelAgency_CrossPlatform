package model.validators;

import model.Flight;

public class FlightValidator implements Validator<Flight> {
    @Override
    public void validate(Flight entity) throws ValidationException {
        String msg = "";
        if(entity.getDestination().equals(""))
            msg += "Destination cannot be empty !";
        if(entity.getAirport().equals(""))
            msg += "The name of airport cannot be empty !";
        if(entity.getAvailableSeats() <= 0)
            msg += "There are not available seats !";
        if(!msg.equals(""))
            throw new ValidationException(msg);
    }
}
