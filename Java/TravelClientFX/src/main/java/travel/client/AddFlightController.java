package travel.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;
import travel.model.Flight;
import travel.model.validators.ValidationException;
import travel.services.ITravelASMServices;
import travel.services.ITravelServices;

import java.time.LocalDateTime;

public class AddFlightController {
    ITravelASMServices server;

    @FXML
    TextField textFieldDestination;
    @FXML
    DateTimePicker dateTimePickerDep;
    @FXML
    TextField textFieldAirport;
    @FXML
    TextField textFieldAvailableSeats;

    public void setServer(ITravelASMServices server){
        this.server = server;
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void handleSaveFlight(ActionEvent actionEvent){
        String destination = textFieldDestination.getText();
        LocalDateTime departure = dateTimePickerDep.getDateTimeValue();
        String airport = textFieldAirport.getText();
        try{
            Integer availableSeats = Integer.valueOf(textFieldAvailableSeats.getText());
            Flight flight = new Flight(destination, departure,airport,availableSeats);
            server.addFlight(flight);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Flight");
            alert.setContentText("The flight has been saved succesfully !");
            alert.showAndWait();

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (ValidationException validationException){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Save Flight");
            alert.setContentText(validationException.getMessage());
            alert.showAndWait();
        }catch (Exception validationException){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Save Flight");
            alert.setContentText(validationException.getMessage());
            alert.showAndWait();
        }
    }
}
