using System;
using System.Collections.Generic;
using travelModel;
using travelModel.validators;
using travelPersistence;

namespace travelServer.services
{
    public class FlightServices
    {
        private readonly FlightRepository flightRepository;
        private readonly Validator<Flight> flightValidator;

        public FlightServices(FlightRepository flightRepository, Validator<Flight> flightValidator)
        {
            this.flightRepository = flightRepository;
            this.flightValidator = flightValidator;
        }

        public void addFlight(string destination, DateTime departure, string airport, int availableSeats)
        {
            var saveFlight = new Flight(destination, departure, airport, availableSeats);
            flightValidator.validate(saveFlight);
            flightRepository.Save(saveFlight);
        }

        public void deleteFlight(string destination, DateTime departure, string airport, int availableSeats, int Id)
        {
            var removeFlight = new Flight(Id, destination, departure, airport, availableSeats);
            flightRepository.Delete(removeFlight);
        }

        public void updateFlight(string destination, DateTime departure, string airport, int availableSeats, int Id)
        {
            var upFlight = new Flight(Id, destination, departure, airport, availableSeats);
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
            var filteredFlights =
                flightRepository.filterFlightByDestinationAndDeparture(destination, departure);
            return filteredFlights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            var filteredFlights = flightRepository.filterFlightByAvailableSeats();
            return filteredFlights;
        }
    }
}