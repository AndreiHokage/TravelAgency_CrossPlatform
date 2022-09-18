package travel.client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import travel.model.Flight;
import travel.model.Ticket;
import travel.model.validators.ValidationException;
import travel.services.ITravelASMServices;
import travel.services.ITravelServices;

public class TicketController {
    private Flight flightForTicket;
    private ITravelASMServices server;

    @FXML
    TextField textFieldCustomerName;
    @FXML
    TextField textFieldTouristName;
    @FXML
    TextField textFieldCustomerAddress;
    @FXML
    TextField textFieldSeats;
    @FXML
    Label labelDestination;
    @FXML
    Label labelDeparture;
    @FXML
    Label labelAirport;

    public TicketController(){
        System.out.println("Constructor TicketController");
    }

    public TicketController(ITravelASMServices server, Flight flightForTicket){
        this.server = server;
        this.flightForTicket = flightForTicket;
        System.out.println("constructor TicketController with server param");
        initTicket();
    }

    public void setServer(ITravelASMServices server){
        this.server = server;
    }

    public void setFlightForTicket(Flight flightForTicket){
        this.flightForTicket = flightForTicket;
        initTicket();
    }

    @FXML
    public void initialize(){

    }

    private void initTicket(){
        labelDestination.setText(flightForTicket.getDestination());
        labelDeparture.setText(flightForTicket.getDeparture().toString());
        labelAirport.setText(flightForTicket.getAirport());
    }

    @FXML
    public void handleBuyTicket(ActionEvent actionEvent){
        String customerName = textFieldCustomerName.getText();
        String touristName = textFieldTouristName.getText();
        String customerAddress = textFieldCustomerAddress.getText();

        try {
            Integer seats = Integer.valueOf(textFieldSeats.getText());
            Flight flight = new Flight(flightForTicket.getID(),flightForTicket.getDestination(),flightForTicket.getDeparture(), flightForTicket.getAirport(), flightForTicket.getAvailableSeats());
            Ticket ticket = new Ticket(0,customerName, touristName, customerAddress, seats, flight);
            server.addTicket(ticket);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Sold");
            alert.setContentText("The ticket has been bought succesfully !");
            alert.showAndWait();

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch(ValidationException validationException){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Sold");
            alert.setContentText(validationException.getMessage());
            alert.showAndWait();
        }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Sold");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
