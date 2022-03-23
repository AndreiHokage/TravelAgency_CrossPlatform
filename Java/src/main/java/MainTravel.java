import controllers.LogInController;
import controllers.TravelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Flight;
import model.Ticket;
import model.validators.FlightValidator;
import model.validators.TicketValidator;
import repository.*;
import services.EmployeeServices;
import services.FlightServices;
import services.TicketServices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class MainTravel extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties serverProps = new Properties(System.getProperties());
        try {
            serverProps.load(MainTravel.class.getClassLoader().getResourceAsStream("bd.properties"));
            System.setProperties(serverProps);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FlightValidator flightValidator = new FlightValidator();
        TicketValidator ticketValidator = new TicketValidator();
        FlightRepository flightRepository = new FlightDBRepository();
        TicketRepository ticketRepository = new TicketDBRepository(flightRepository);
        EmployeeRepository employeeRepository = new EmployeeDBRepository();
        Flight flight = new Flight("Trucia",LocalDateTime.now(),"Suceava airport",18);
        FlightServices flightServices = new FlightServices(flightRepository,flightValidator);
        TicketServices ticketServices = new TicketServices(ticketRepository,ticketValidator,flightServices);
        EmployeeServices employeeServices = new EmployeeServices(employeeRepository);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        LogInController logInController = fxmlLoader.getController();
        logInController.setLogInController(primaryStage,flightServices,ticketServices,employeeServices);
        primaryStage.show();
    }
}
