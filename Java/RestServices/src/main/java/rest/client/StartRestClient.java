package rest.client;

import rest.ServiceException;
import travel.model.Flight;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class StartRestClient {

    private final static UserClient userClient = new UserClient();

    public static void main(String[] args) {
        Flight createdFlight = new Flight("Warsaw", LocalDateTime.now(), "Bucharest", 30);

        System.out.println("Create flight: ---------------------------");
        final Flight newFlight = userClient.createFlight(createdFlight);
        System.out.println(newFlight);

        show(() -> {
            System.out.println("FindAll flights: -------------------------");
            Flight[] flights = userClient.findAll();
            for(Flight flight: flights)
                System.out.println(flight);
        });

        show(() -> {
            System.out.println("Update flight: ---------------------------");
            Flight updatedFlight = newFlight;
            updatedFlight.setAirport("Otopeni Airport");
            userClient.update(updatedFlight);
        });

        show(() -> {
            System.out.println("FindById flight: -------------------------");
            System.out.println(userClient.flightById(newFlight.getID()));
        });

        show(() -> {
            System.out.println("Delete flight: -------------------------");
            userClient.deleteFlight(newFlight.getID());
            System.out.println(userClient.flightById(newFlight.getID()));
        });
    }

    private static void show(Runnable task){
        try{
            task.run();
        }catch (ServiceException e){
            System.out.println("Service exception " + e);
        }
    }
}
