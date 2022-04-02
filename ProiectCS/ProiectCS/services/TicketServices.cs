using System.Collections;
using System.Collections.Generic;
using NET.model;
using NET.model.validators;
using NET.repository;

namespace ProiectCS.services
{
    public class TicketServices
    {
        private TicketRepository ticketRepository;
        private Validator<Ticket> ticketValidator;
        private FlightServices flightServices;

        public TicketServices(TicketRepository ticketRepository, Validator<Ticket> ticketValidator, FlightServices flightServices)
        {
            this.ticketRepository = ticketRepository;
            this.ticketValidator = ticketValidator;
            this.flightServices = flightServices;
        }

        public void addTicket(string customerName, string touristName, string customerAddress, int seats, Flight flight)
        {
            Flight flightForTicket = flightServices.findFlightByID(flight.ID);
            Ticket saveTicket = new Ticket(customerName, touristName, customerAddress, seats, flightForTicket);
            ticketValidator.validate(saveTicket);
            
            flightServices.updateFlight(flightForTicket.Destination, flightForTicket.Departure, 
                flightForTicket.Airport,flightForTicket.AvailableSeats - seats,flightForTicket.ID);
            ticketRepository.Save(saveTicket);
        }

        public void deleteTicket(string customerName, string touristName, string customerAddress, 
            int seats, Flight flight, int Id)
        {
            Ticket removeTicket = new Ticket(Id, customerName, touristName, customerAddress, seats, flight);
            ticketRepository.Delete(removeTicket);
        }

        public void updateTicket(string customerName, string touristName, string customerAddress,
            int seats, Flight flight, int Id)
        {
            Ticket upTicket = new Ticket(Id,customerName,touristName,customerAddress,seats,flight);
            ticketValidator.validate(upTicket);
            ticketRepository.Update(upTicket, Id);
        }

        public Ticket findTicketById(int Id)
        {
            return ticketRepository.FindById(Id);
        }

        public IEnumerable<Ticket> findAllTickets()
        {
            return ticketRepository.FindAll();
        } 
        
    }
}