package travel.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import travel.model.Employee;
import travel.services.ITravelServices;
import travel.services.TravelException;

import java.io.IOException;


public class LogInController {
    private ITravelServices server;
    private TravelController travelController;
    private Parent mainTravelParent;

    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField textFieldPassword;

    public void setServer(ITravelServices server){
        this.server = server;
    }

    public void setTravelController(TravelController travelController){
        this.travelController = travelController;
    }

    public void setMainTravelParent(Parent mainTravelParent){
        this.mainTravelParent = mainTravelParent;
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void handleLogIn(ActionEvent actionEvent)  {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        Employee employee = new Employee(0, username, password);
        try {
            server.login(employee, travelController);

        } catch (TravelException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }

        System.out.println("After the controller was loaded by FXMLLoader, I can load the data");
        travelController.setServer(server);
        Stage stage = new Stage();
        stage.setScene(new Scene(mainTravelParent));

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    server.logout(employee, travelController);
                } catch (TravelException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        stage.show();


        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


    }
}
