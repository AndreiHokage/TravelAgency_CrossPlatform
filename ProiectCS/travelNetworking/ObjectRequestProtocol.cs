using System;
using travelModel;


namespace travelNetworking
{

    public interface Request
    {

    }

    [Serializable]
    public class LoginRequest : Request
    {
        private Employee employee;

        public LoginRequest(Employee employee)
        {
            this.employee = employee;
        }

        public virtual Employee getData()
        {
            return employee;
        }

        public override string ToString()
        {
            return "LoginRequest: " + employee;
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        private Employee employee;

        public LogoutRequest(Employee employee)
        {
            this.employee = employee;
        }

        public virtual Employee getData()
        {
            return employee;
        }
        
        public override string ToString()
        {
            return "LogoutRequest: " + employee;
        }
    }

    [Serializable]
    public class BuyTicketRequest : Request
    {
        private Ticket ticket;

        public BuyTicketRequest(Ticket ticket)
        {
            this.ticket = ticket;
        }

        public virtual Ticket getData()
        {
            return ticket;
        }
        
        public override string ToString()
        {
            return "BuyTicketRequest: " + ticket;
        }
    }

    [Serializable]
    public class FightsDestinationDepartureRequest : Request
    {
        private FlightDestDepDTO flightDestDepDTO;

        public FightsDestinationDepartureRequest(FlightDestDepDTO flightDestDepDto)
        {
            flightDestDepDTO = flightDestDepDto;
        }

        public virtual FlightDestDepDTO getData()
        {
            return flightDestDepDTO;
        }
        
        public override string ToString()
        {
            return "FightsDestinationDepartureRequest: " + flightDestDepDTO;
        }
    }

    [Serializable]
    public class FlightAvailableSeatsRequest : Request
    {
        public FlightAvailableSeatsRequest()
        {
        }
        
        public override string ToString()
        {
            return "FlightAvailableSeatsRequest ...";
        }
    }

}