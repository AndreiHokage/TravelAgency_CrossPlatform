package travel.client;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import travel.model.Flight;
import travel.model.Ticket;
import travel.model.notification.Notification;
import travel.services.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;


public class TravelController implements INotificationSubscriber {

    private ITravelASMServices server;
    private INotificationReceiver receiver;
    private String destinationField = null;
    private LocalDate departureField = null;

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

    public TravelController(){
        System.out.println("Constructor TravelController");
    }

    public TravelController(ITravelASMServices server){
        this.server = server;
        System.out.println("constructor TravelController cu server param");
        initModelAllFlights();
    }

    public void setServer(ITravelASMServices server){
        this.server = server;
        initModelAllFlights();
        receiver.start(this);
    }

    public void setReceiver(INotificationReceiver receiver) {
        this.receiver = receiver;
    }


    public void logout(){
        receiver.stop();
    }

    @FXML
    public void initialize(){
        System.out.println("INIT models and tabel for Travel Controllers: .......");
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
        Collection<Flight> flights = null;
        try {
            flights = server.filterFlightByAvailableSeats();
        } catch (TravelException e) {
            e.printStackTrace();
        }
        modelAllFlights.setAll(flights);
    }

    @FXML
    public void handleSearchFlights(){
        destinationField = textFieldDestination.getText();
        departureField = datePickerDeparture.getValue();
        if(destinationField == null || departureField == null)
            return;
        Collection<Flight> flights = null;
        try {
            flights = server.filterFlightsByDestinationAndDeparture(destinationField,departureField);
            modelAllFlightsTicket.setAll(flights);
        } catch (TravelException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Agency");
            alert.setHeaderText("Searched failure by destination and departure");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
        addFlightController.setServer(server);
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
        ticketController.setServer(server);
        ticketController.setFlightForTicket(flightForTicket);
        stageTicket.show();
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("INIT : am in lista zboruri " + modelAllFlights.size());
//        System.out.println("INIT models and tabel for Travel Controllers: .......");
//        tableColumnDestination.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
//        tableColumnDeparture.setCellValueFactory(new PropertyValueFactory<Flight,LocalDateTime>("departure"));
//        tableColumnAirport.setCellValueFactory(new PropertyValueFactory<Flight,String>("airport"));
//        tableColumnAvailableSeats.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("availableSeats"));
//        tableViewFlights.setItems(modelAllFlights);
//
//        tableColumnDestinationTicket.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
//        tableColumnDepartureTicket.setCellValueFactory(new PropertyValueFactory<Flight,LocalDateTime>("departure"));
//        tableColumnAirportTicket.setCellValueFactory(new PropertyValueFactory<Flight,String>("airport"));
//        tableColumnAvailableSeatsTicket.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("availableSeats"));
//        tableViewFlightsTicket.setItems(modelAllFlightsTicket);
//        initModelAllFlights();
//        System.out.println("END INIT!!!!!!!!!");
//    }

//    @Override
//    public void soldTicket(Ticket ticket) throws TravelException {
//        System.out.println("sold ticket method Observer controller....");
//        Platform.runLater(() -> {
//            initModelAllFlights();
//            LocalDate convertDate = ticket.getFlight().getDeparture().toLocalDate();
//            if(destinationField.equals(ticket.getFlight().getDestination()) && departureField.equals(convertDate)){
//                Collection<Flight> flights = null;
//                try {
//                    flights = server.filterFlightsByDestinationAndDeparture(destinationField,departureField);
//                    modelAllFlightsTicket.setAll(flights);
//                } catch (TravelException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void saveFlight(Flight flight) throws TravelException {
//        Platform.runLater(() -> {
//            initModelAllFlights();
//            LocalDate convertDate = flight.getDeparture().toLocalDate();
//            if(destinationField.equals(flight.getDestination()) && departureField.equals(convertDate)){
//                Collection<Flight> flights = null;
//                try {
//                    flights = server.filterFlightsByDestinationAndDeparture(destinationField,departureField);
//                    modelAllFlightsTicket.setAll(flights);
//                } catch (TravelException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    public void notificationReceived(Notification notif) {
        try{
            System.out.println("Ctrl notificationReceived ..." + notif.getType());
            SwingUtilities.invokeLater(()->{
                switch (notif.getType()){
                    case SAVE_FLIGHT:{
                        Flight flight = notif.getFlight();
                        initModelAllFlights();
                        LocalDate convertDate = flight.getDeparture().toLocalDate();
                        if(destinationField.equals(flight.getDestination()) && departureField.equals(convertDate)){
                            Collection<Flight> flights = null;
                            try {
                                flights = server.filterFlightsByDestinationAndDeparture(destinationField,departureField);
                                modelAllFlightsTicket.setAll(flights);
                            } catch (TravelException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case BUY_TICKET:{
                        Ticket ticket = notif.getTicket();
                        System.out.println("BALANICI ENTER BUY_TICKE:  " + ticket);
                        initModelAllFlights();
                        LocalDate convertDate = ticket.getFlight().getDeparture().toLocalDate();
                        if(destinationField.equals(ticket.getFlight().getDestination()) && departureField.equals(convertDate)){
                            Collection<Flight> flights = null;
                            try {
                                flights = server.filterFlightsByDestinationAndDeparture(destinationField,departureField);
                                modelAllFlightsTicket.setAll(flights);
                            } catch (TravelException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
