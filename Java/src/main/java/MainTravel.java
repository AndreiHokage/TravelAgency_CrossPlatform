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
        Flight flight = new Flight("Dublin", LocalDateTime.now(),"Arad International Airport",48);
        Flight flight2 = new Flight("London", LocalDateTime.now(),"Bacau International Airport",60);
        //flightRepository.add(flight);
        //flightRepository.add(flight2);
        flight2.setID(2);
        Ticket ticket = new Ticket("Andrei","Maria;Alin","Barrow St",3,flight2);
        //ticketRepository.add(ticket);
        //ticket.setID(1);
        //ticketRepository.delete(ticket);
        Ticket ticket2 = new Ticket("Andrei","Maria;Angela","Barrow St",3,flight2);
       //ticketRepository.update(ticket2,1);
        //ticket2.setID(3);
       // ticketRepository.delete(ticket2);
       flightRepository.delete(flight2);


        flightRepository.findAll().forEach(System.out::println);
        System.out.println("------------ticket-------------");
        ticketRepository.findAll().forEach(System.out::println);
    }
}
