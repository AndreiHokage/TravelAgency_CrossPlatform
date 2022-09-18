package travel.model.notification;

import travel.model.Flight;
import travel.model.Ticket;

public class Notification {

    private NotificationType type;
    private Flight flight;
    private Ticket ticket;

    public Notification(){

    }

    public Notification(NotificationType type) {
        this.type = type;
    }

    public Notification(NotificationType type, Ticket ticket) {
        this.type = type;
        this.ticket = ticket;
    }

    public Notification(NotificationType type, Flight flight) {
        this.type = type;
        this.flight = flight;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "type=" + type +
                ", flight=" + flight +
                ", ticket=" + ticket +
                '}';
    }
}
