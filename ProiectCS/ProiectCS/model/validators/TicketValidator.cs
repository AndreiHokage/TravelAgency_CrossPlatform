namespace NET.model.validators
{
    public class TicketValidator : Validator<Ticket>
    {
        public void validate(Ticket item)
        {
            string msg = "";
            if(item.CustomerName == "")
                msg += "The customer's name cannot be empty !";
            if(item.TouristName == "")
                msg += "The tourist's name cannot be empty !";
            if(item.CustomerAddress == "")
                msg += "The customer's address cannot be empty !"; 
            if(item.Seats <= 0)
                msg += "Invalid number of seats !";
            if (msg != "")
                throw new ValidationException(msg);
        }
    }
}