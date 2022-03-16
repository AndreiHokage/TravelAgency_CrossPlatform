import model.Flight;
import model.Ticket;
import repository.FlightDBRepository;
import repository.FlightRepository;
import repository.TicketDBRepository;
import repository.TicketRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class MainTravel {
    public static void main(String[] args) {
        Properties serverProps = new Properties(System.getProperties());
        try {
            serverProps.load(MainTravel.class.getClassLoader().getResourceAsStream("bd.properties"));
            System.setProperties(serverProps);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FlightRepository flightRepository = new FlightDBRepository();
        TicketRepository ticketRepository = new TicketDBRepository(flightRepository);
        Flight flight = new Flight("Trucia",LocalDateTime.now(),"Suceava airport",18);
       // flightRepository.add(flight);


        flightRepository.findAll().forEach(System.out::println);
        System.out.println("------------ticket-------------");
        ticketRepository.findAll().forEach(System.out::println);
    }
}
