package travel.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Flight implements Identifiable<Integer>, Serializable {
    private Integer ID;
    private String destination;
    private LocalDateTime departure;
    private String airport;
    private Integer availableSeats;

    public Flight(){}

    public Flight(Integer ID,String destination, LocalDateTime departure, String airport, Integer availableSeats) {
        this.ID = ID;
        this.destination = destination;
        this.departure = departure;
        this.airport = airport;
        this.availableSeats = availableSeats;
    }

    public Flight(String destination, LocalDateTime departure, String airport, Integer availableSeats) {
        this.destination = destination;
        this.departure = departure;
        this.airport = airport;
        this.availableSeats = availableSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(ID, flight.ID) && Objects.equals(destination, flight.destination) && Objects.equals(departure, flight.departure) && Objects.equals(airport, flight.airport) && Objects.equals(availableSeats, flight.availableSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, destination, departure, airport, availableSeats);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "ID=" + ID +
                ", destination='" + destination + '\'' +
                ", departure=" + departure +
                ", airport='" + airport + '\'' +
                ", availableSeats='" + availableSeats + '\'' +
                '}';
    }
}
