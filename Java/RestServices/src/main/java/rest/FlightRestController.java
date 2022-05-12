package rest;

import chat.persistence.FlightDBRepository;
import chat.persistence.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.model.Flight;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Properties;

@RestController
@RequestMapping("/travel/flights")
public class FlightRestController {

    private FlightRepository flightRepository;

    @Autowired
    public FlightRestController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createFlight(@RequestBody Flight flight){
        System.out.println("Rest - Create a new flight");
        flightRepository.add(flight);
        return new ResponseEntity<Integer>(flight.getID(), HttpStatus.OK);
    }

    @RequestMapping(value="/{flightId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFlight(@PathVariable Integer flightId){
        System.out.println("Rest - Delete a flight");
        Flight thereIsFlight = flightRepository.findByID(flightId);
        if(thereIsFlight == null)
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        flightRepository.delete(thereIsFlight);
        return new ResponseEntity<String>("The delete operation has been made successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/{flightId}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer flightId, @RequestBody Flight flight){
        System.out.println("Rest - Update a flight");
        flightRepository.update(flight, flightId);
    }

    @RequestMapping(value="/{flightId}", method = RequestMethod.GET)
    public ResponseEntity<?> findFlightById(@PathVariable Integer flightId){
        System.out.println("Rest - Find flight by id");
        Flight flight = flightRepository.findByID(flightId);
        if(flight != null)
            return new ResponseEntity<Flight>(flight, HttpStatus.OK);
        else
            return new ResponseEntity<String>("Flight not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Flight> getAll(){
        System.out.println("Rest - Find all flights");
        return flightRepository.getAll();
    }

//    @RequestMapping(value="?destination&departure", method = RequestMethod.GET)
//    public Collection<Flight> filterFlightByDestinationAndDeparture
//            (@RequestParam(value = "destination", defaultValue = "")String destination,
//             @RequestParam(value = "departure", defaultValue = "1970/1/1") LocalDate departure){
//        Collection<Flight> flights = flightRepository.filterFlightByDestinationAndDeparture(destination, departure);
//        return flights;
//    }

//    @RequestMapping(method = RequestMethod.GET)
//    public Collection<Flight> filterFlightByAvailableSeats
//            (@RequestParam(value = "available", defaultValue = "true") String required){
//        Collection<Flight> flights = new ArrayList<>();
//        if(required.equals("true"))
//            flights = flightRepository.filterFlightByAvailableSeats();
//        else
//            flights = flightRepository.getAll();
//        return flights;
//    }

}
