package services;

import model.Flight;
import model.validators.FlightValidator;
import repository.FlightRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class FlightServices {
    private FlightRepository flightRepository;
    private FlightValidator flightValidator;

    public FlightServices(FlightRepository flightRepository,FlightValidator flightValidator) {
        this.flightRepository = flightRepository;
        this.flightValidator = flightValidator;
    }

    public void addFlight(String destination, LocalDateTime departure, String airport, Integer availableSeats){
        Flight saveFlight = new Flight(destination,departure,airport,availableSeats);
        flightValidator.validate(saveFlight);
        flightRepository.add(saveFlight);
    }

    public void deleteFlight(String destination, LocalDateTime departure, String airport, Integer availableSeats,Integer Id){
        Flight removeFlight = new Flight(destination,departure,airport,availableSeats);
        removeFlight.setID(Id);
        flightRepository.delete(removeFlight);
    }

    public void updateFlight(String destination, LocalDateTime departure, String airport, Integer availableSeats,Integer Id){
        Flight upFlight = new Flight(Id,destination,departure,airport,availableSeats);
        flightValidator.validate(upFlight);
        flightRepository.update(upFlight, Id);
    }

    public Flight findFlightByID(Integer Id){
        return flightRepository.findByID(Id);
    }

    public Iterable<Flight> findAllFlights(){
        return flightRepository.findAll();
    }

    public Collection<Flight> getAllFlights(){
        return flightRepository.getAll();
    }

    public Collection<Flight> filterFlightsByDestinationAndDeparture(String destination, LocalDate departure){
        Collection<Flight> filterFlights = flightRepository.filterFlightByDestinationAndDeparture(destination, departure);
        return filterFlights;
    }

    public Collection<Flight> filterFlightByAvailableSeats(){
        Collection<Flight> filterFlights = flightRepository.filterFlightByAvailableSeats();
        return filterFlights;
    }
}
