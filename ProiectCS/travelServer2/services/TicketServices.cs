using System.Collections.Generic;
using travelModel;
using travelModel.validators;
using travelPersistence;

namespace travelServer.services
{
    public class TicketServices
    {
        private readonly FlightServices flightServices;
        private readonly TicketRepository ticketRepository;
        private readonly Validator<Ticket> ticketValidator;

        public TicketServices(TicketRepository ticketRepository, Validator<Ticket> ticketValidator,
            FlightServices flightServices)
        {
            this.ticketRepository = ticketRepository;
            this.ticketValidator = ticketValidator;
            this.flightServices = flightServices;
        }

        public void addTicket(string customerName, string touristName, string customerAddress, int seats, Flight flight)
        {
            var flightForTicket = flightServices.findFlightByID(flight.ID);
            var saveTicket = new Ticket(customerName, touristName, customerAddress, seats, flightForTicket);
            ticketValidator.validate(saveTicket);

            flightServices.updateFlight(flightForTicket.Destination, flightForTicket.Departure,
                flightForTicket.Airport, flightForTicket.AvailableSeats - seats, flightForTicket.ID);
            ticketRepository.Save(saveTicket);
        }

        public void deleteTicket(string customerName, string touristName, string customerAddress,
            int seats, Flight flight, int Id)
        {
            var removeTicket = new Ticket(Id, customerName, touristName, customerAddress, seats, flight);
            ticketRepository.Delete(removeTicket);
        }

        public void updateTicket(string customerName, string touristName, string customerAddress,
            int seats, Flight flight, int Id)
        {
            var upTicket = new Ticket(Id, customerName, touristName, customerAddress, seats, flight);
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