package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Flight;
import model.validators.ValidationException;

import org.apache.logging.log4j.message.Message;
import services.TicketServices;

public class TicketController {
    private Flight flightForTicket;
    private TicketServices ticketServices;
    private Stage mainStage;

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

    public void setTicketController(Stage mainStage ,TicketServices ticketServices, Flight flightForTicket){
        this.mainStage = mainStage;
        this.ticketServices = ticketServices;
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
    public void handleBuyTicket(){
        String customerName = textFieldCustomerName.getText();
        String touristName = textFieldTouristName.getText();
        String customerAddress = textFieldCustomerAddress.getText();

        try {
            Integer seats = Integer.valueOf(textFieldSeats.getText());
            ticketServices.addTicket(customerName, touristName, customerAddress, seats, flightForTicket);
            MessageAlert.showMessage(mainStage, Alert.AlertType.INFORMATION,"Sold","The ticket has been bought succesfully !");
            mainStage.close();
        }
        catch(ValidationException validationException){
           MessageAlert.showErrorMessage(mainStage, validationException.getMessage());
        }
        catch(Exception ex){
            MessageAlert.showErrorMessage(mainStage, ex.getMessage());
        }
    }
}
