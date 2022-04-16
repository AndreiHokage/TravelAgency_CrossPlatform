using System;
using System.Collections.Generic;
using travelModel;

namespace travelNetworking{
    public interface Response
    {

    }

    [Serializable]
    public class OkResponse : Response
    {
        public override string ToString()
        {
            return "OkResponse";
        }
    }

    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;

        public ErrorResponse(string message)
        {
            this.message = message;
        }

        public virtual string getData()
        {
            return message;
        }

        public override string ToString()
        {
            return "Error Response: " + message;
        }
    }

    [Serializable]
    public class FlightDestinationDepartureResponse : Response
    {
        private IEnumerable<Flight> flights;

        public FlightDestinationDepartureResponse(IEnumerable<Flight> flights)
        {
            this.flights = flights;
        }

        public virtual IEnumerable<Flight> getData()
        {
            return flights;
        }
        
        public override string ToString()
        {
            string msg = "FlightDestinationDepartureResponse Response: ";
            foreach (var flight in flights)
            {
                msg += flight.ToString() + '\n';
            }
            return msg;
        }
    }

    [Serializable]
    public class FlightAvailableSeatsResponse : Response
    {
        private IEnumerable<Flight> flights;

        public FlightAvailableSeatsResponse(IEnumerable<Flight> flights)
        {
            this.flights = flights;
        }

        public virtual IEnumerable<Flight> getData()
        {
            return flights;
        }
        
        public override string ToString()
        {
            string msg = "FlightAvailableSeatsResponse Response: ";
            foreach (var flight in flights)
            {
                msg += flight.ToString() +'\n';
            }
            return msg;
        }
    }

    [Serializable]
    public class ErrorUnavailableSeatsResponse : Response
    {
        private string message;

        public ErrorUnavailableSeatsResponse(string message)
        {
            this.message = message;
        }

        public virtual string getData()
        {
            return message;
        }
        
        public override string ToString()
        {
            return "ErrorUnavailableSeatsResponse Response: " + message;
        }
    }

    public interface UpdateResponse : Response
    {

    }

    [Serializable]
    public class AddTicketResponse : UpdateResponse
    {
        private Ticket ticket;

        public AddTicketResponse(Ticket ticket)
        {
            this.ticket = ticket;
        }

        public virtual Ticket getData()
        {
            return ticket;
        }
        
        public override string ToString()
        {
            return "AddTicketResponse Response: " + ticket;
        }
        
    }

}