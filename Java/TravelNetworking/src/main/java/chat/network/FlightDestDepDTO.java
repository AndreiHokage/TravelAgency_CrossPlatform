package chat.network;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlightDestDepDTO implements Serializable {
    private String destination;
    private LocalDate departure;

    public FlightDestDepDTO(String destination, LocalDate departure) {
        this.destination = destination;
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return "FlightDestDepDTO{" +
                "destination='" + destination + '\'' +
                ", departure=" + departure +
                '}';
    }
}
