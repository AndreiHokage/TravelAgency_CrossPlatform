package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.EmployeeServices;
import services.FlightServices;
import services.TicketServices;

import java.io.IOException;


public class LogInController {
    private FlightServices flightServices;
    private TicketServices ticketServices;
    private EmployeeServices employeeServices;
    private Stage mainStage;

    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField textFieldPassword;

    public void setLogInController(Stage mainStage, FlightServices flightServices,TicketServices ticketServices,
                                   EmployeeServices employeeServices){
        this.mainStage = mainStage;
        this.flightServices = flightServices;
        this.ticketServices = ticketServices;
        this.employeeServices = employeeServices;
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void handleLogIn() throws IOException {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        boolean isLog = employeeServices.logIn(username,password);
        if(isLog == false){
            textFieldPassword.clear();
            textFieldUsername.clear();
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/travel.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        Stage stageTravel = new Stage();
        stageTravel.setScene(newScene);
        TravelController travelController = fxmlLoader.getController();
        travelController.setTravelController(stageTravel,flightServices,ticketServices);
        stageTravel.show();
        mainStage.close();
    }
}
