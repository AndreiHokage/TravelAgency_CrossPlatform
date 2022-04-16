using System;
using System.Collections.Generic;
using travelModel;
using travelModel.validators;
using travelPersistence;

namespace travelServer.services
{
    public class FlightServices
    {
        private FlightRepository flightRepository;
        private Validator<Flight> flightValidator;

        public FlightServices(FlightRepository flightRepository, Validator<Flight> flightValidator)
        {
            this.flightRepository = flightRepository;
            this.flightValidator = flightValidator;
        }

        public void addFlight(string destination, DateTime departure, string airport, int availableSeats)
        {
            Flight saveFlight = new Flight(destination, departure, airport, availableSeats);
            flightValidator.validate(saveFlight);
            flightRepository.Save(saveFlight);
        }

        public void deleteFlight(string destination, DateTime departure, string airport, int availableSeats, int Id)
        {
            Flight removeFlight = new Flight(Id, destination, departure, airport, availableSeats);
            flightRepository.Delete(removeFlight);
        }
        
        public void updateFlight(string destination, DateTime departure, string airport, int availableSeats, int Id)
        {
            Flight upFlight = new Flight(Id, destination, departure, airport, availableSeats);
            flightValidator.validate(upFlight);
            flightRepository.Update(upFlight, Id);
        }

        public Flight findFlightByID(int Id)
        {
            return flightRepository.FindById(Id);
        }

        public IEnumerable<Flight> findALL()
        {
            return flightRepository.FindAll();
        }

        public IEnumerable<Flight> filterFlightsByDestinationAndDeparture(string destination, DateTime departure)
        {
            IEnumerable<Flight> filteredFlights =
                flightRepository.filterFlightByDestinationAndDeparture(destination, departure);
            return filteredFlights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            IEnumerable<Flight> filteredFlights = flightRepository.filterFlightByAvailableSeats();
            return filteredFlights;
        }
    }
}