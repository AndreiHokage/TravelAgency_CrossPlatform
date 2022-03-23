package controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Flight;
import model.validators.ValidationException;
import repository.FlightRepository;
import services.FlightServices;
import tornadofx.control.DateTimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddFlightController {
    Stage mainStage;
    FlightServices flightServices;

    @FXML
    TextField textFieldDestination;
    @FXML
    DateTimePicker dateTimePickerDep;
    @FXML
    TextField textFieldAirport;
    @FXML
    TextField textFieldAvailableSeats;

    public void setAddFlightController(Stage mainStage,FlightServices flightServices){
        this.mainStage = mainStage;
        this.flightServices = flightServices;
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void handleSaveFlight(){
        String destination = textFieldDestination.getText();
        LocalDateTime departure = dateTimePickerDep.getDateTimeValue();
        String airport = textFieldAirport.getText();
        try{
            Integer availableSeats = Integer.valueOf(textFieldAvailableSeats.getText());
            flightServices.addFlight(destination, departure,airport,availableSeats);
            MessageAlert.showMessage(mainStage, Alert.AlertType.INFORMATION,"Save","The flight has been saved succesfully !");
            mainStage.close();
        }
        catch (ValidationException validationException){
            MessageAlert.showErrorMessage(mainStage,validationException.getMessage());
            textFieldAvailableSeats.clear();
            textFieldAirport.clear();
            textFieldDestination.clear();
        }catch (Exception validationException){
            MessageAlert.showErrorMessage(mainStage,validationException.getMessage());
            textFieldAvailableSeats.clear();
            textFieldAirport.clear();
            textFieldDestination.clear();
        }
    }
}
