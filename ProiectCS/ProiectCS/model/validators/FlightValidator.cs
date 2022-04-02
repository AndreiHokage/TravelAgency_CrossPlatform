namespace NET.model.validators
{
    public class FlightValidator : Validator<Flight>
    {
        public void validate(Flight item)
        {
            string msg = "";
            if (item.Destination == "")
                msg += "Destination cannot be empty !";
            if (item.Airport == "")
                msg += "The name of airport cannot be empty !";
            if (item.AvailableSeats <= 0)
                msg += "There are not available seats !";
            if (msg != "")
                throw new ValidationException(msg);
        }
    }
}