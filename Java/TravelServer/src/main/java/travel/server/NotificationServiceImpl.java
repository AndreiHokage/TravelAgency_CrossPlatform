package travel.server;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsOperations;
import travel.model.Flight;
import travel.model.Ticket;
import travel.model.notification.Notification;
import travel.model.notification.NotificationType;
import travel.services.ITravelNotificationService;
import travel.services.TravelException;

public class NotificationServiceImpl implements ITravelNotificationService {

    private JmsOperations jmsOperations;

    public NotificationServiceImpl(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    @Override
    public void soldTicket(Ticket ticket) throws TravelException {
        System.out.println("buy ticket notification");
        Notification notif = new Notification(NotificationType.BUY_TICKET, ticket);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(notif);
            System.out.println("Convert JSON: " + jsonInString);
            jmsOperations.convertAndSend(notif);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Sent message to ActiveMQ..." + notif);
    }

    @Override
    public void saveFlight(Flight flight) throws TravelException {
        System.out.println("save flight notification");
        Notification notif = new Notification(NotificationType.SAVE_FLIGHT, flight);
        jmsOperations.convertAndSend(notif);
        System.out.println("Sent message to ActiveMQ..." + notif);
    }
}
