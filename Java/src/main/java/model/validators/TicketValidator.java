package model.validators;

import model.Ticket;

public class TicketValidator implements Validator<Ticket> {
    @Override
    public void validate(Ticket entity) throws ValidationException {
        String msg = "";
        if(entity.getCustomerName().equals(""))
            msg += "The customer's name cannot be empty !";
        if(entity.getTouristName().equals(""))
            msg += "The tourist's name cannot be empty !";
        if(entity.getCustomerAddress().equals(""))
            msg += "The customer's address cannot be empty !";
        if(entity.getSeats() <= 0)
            msg += "Invalid number of seats !";
        if(!msg.equals(""))
            throw  new ValidationException(msg);
    }
}
