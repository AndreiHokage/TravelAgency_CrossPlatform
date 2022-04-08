package travel.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Identifiable<Integer>, Serializable {
    private Integer ID;
    private String customerName;
    private String touristName;
    private String customerAddress;
    private Integer seats;
    private Flight flight;

    public Ticket(Integer ID, String customerName, String touristName, String customerAddress, Integer seats , Flight flight) {
        this.ID = ID;
        this.customerName = customerName;
        this.touristName = touristName;
        this.customerAddress = customerAddress;
        this.seats = seats;
        this.flight = flight;
    }

    public Ticket(String customerName, String touristName, String customerAddress, Integer seats , Flight flight) {
        this.customerName = customerName;
        this.touristName = touristName;
        this.customerAddress = customerAddress;
        this.seats = seats;
        this.flight = flight;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ID, ticket.ID) && Objects.equals(customerName, ticket.customerName) && Objects.equals(touristName, ticket.touristName) && Objects.equals(customerAddress, ticket.customerAddress) && Objects.equals(seats, ticket.seats) && Objects.equals(flight, ticket.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, customerName, touristName, customerAddress, seats, flight);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ID=" + ID +
                ", customerName='" + customerName + '\'' +
                ", touristName='" + touristName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", seats=" + seats +
                ", flight=" + flight +
                '}';
    }
}
