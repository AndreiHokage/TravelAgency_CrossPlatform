package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Flight;
import services.FlightServices;
import services.TicketServices;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;


public class TravelController {
    private Stage mainStage;
    private FlightServices flightServices;
    private TicketServices ticketServices;
    ObservableList<Flight> modelAllFlights = FXCollections.observableArrayList();
    ObservableList<Flight> modelAllFlightsTicket = FXCollections.observableArrayList();

    @FXML
    TableView<Flight> tableViewFlights;
    @FXML
    TableColumn<Flight,String> tableColumnDestination;
    @FXML
    TableColumn<Flight, LocalDateTime> tableColumnDeparture;
    @FXML
    TableColumn<Flight,String> tableColumnAirport;
    @FXML
    TableColumn<Flight,Integer> tableColumnAvailableSeats;
    @FXML
    TextField textFieldDestination;
    @FXML
    DatePicker datePickerDeparture;

    @FXML
    TableView<Flight> tableViewFlightsTicket;
    @FXML
    TableColumn<Flight,String> tableColumnDestinationTicket;
    @FXML
    TableColumn<Flight, LocalDateTime> tableColumnDepartureTicket;
    @FXML
    TableColumn<Flight,String> tableColumnAirportTicket;
    @FXML
    TableColumn<Flight,Integer> tableColumnAvailableSeatsTicket;

    public void setTravelController(Stage mainStage,FlightServices flightServices,TicketServices ticketServices){
        this.mainStage = mainStage;
        this.flightServices = flightServices;
        this.ticketServices = ticketServices;
        initModelAllFlights();
    }

    @FXML
    public void initialize(){
        tableColumnDestination.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        tableColumnDeparture.setCellValueFactory(new PropertyValueFactory<Flight,LocalDateTime>("departure"));
        tableColumnAirport.setCellValueFactory(new PropertyValueFactory<Flight,String>("airport"));
        tableColumnAvailableSeats.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("availableSeats"));
        tableViewFlights.setItems(modelAllFlights);

        tableColumnDestinationTicket.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        tableColumnDepartureTicket.setCellValueFactory(new PropertyValueFactory<Flight,LocalDateTime>("departure"));
        tableColumnAirportTicket.setCellValueFactory(new PropertyValueFactory<Flight,String>("airport"));
        tableColumnAvailableSeatsTicket.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("availableSeats"));
        tableViewFlightsTicket.setItems(modelAllFlightsTicket);
    }

    private void initModelAllFlights(){
        Collection<Flight> flights = flightServices.filterFlightByAvailableSeats();
        modelAllFlights.setAll(flights);
    }

    @FXML
    public void handleSearchFlights(){
        String destination = textFieldDestination.getText();
        LocalDate departureTime = datePickerDeparture.getValue();
        Collection<Flight> flights = flightServices.filterFlightsByDestinationAndDeparture(destination,departureTime);
        modelAllFlightsTicket.setAll(flights);
    }

    @FXML
    public void handleAddFlight() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/addFlight.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage stageAddFlight = new Stage();
        stageAddFlight.setScene(newScene);
        AddFlightController addFlightController = loader.getController();
        addFlightController.setAddFlightController(stageAddFlight,flightServices);
        stageAddFlight.show();
    }

    @FXML
    public void handleBuyTicket() throws IOException {
        Flight flightForTicket = tableViewFlightsTicket.getSelectionModel().getSelectedItem();
        if(flightForTicket == null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ticket.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage stageTicket = new Stage();
        stageTicket.setScene(newScene);
        TicketController ticketController = loader.getController();
        ticketController.setTicketController(stageTicket ,ticketServices,flightForTicket);
        stageTicket.show();
    }
}
