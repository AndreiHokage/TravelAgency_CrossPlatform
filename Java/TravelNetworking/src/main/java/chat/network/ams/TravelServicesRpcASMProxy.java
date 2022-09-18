package chat.network.ams;

import chat.network.FlightDestDepDTO;
import chat.network.TravelServicesRpcProxy;
import chat.network.utils.ProtoUtils;
import generatedcode.TravelMainRequest;
import generatedcode.TravelMainResponse;
import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;
import travel.services.INotificationReceiver;
import travel.services.ITravelASMServices;
import travel.services.ITravelObserver;
import travel.services.TravelException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TravelServicesRpcASMProxy implements ITravelASMServices {

    private String host;
    private int port;

    private ITravelObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<TravelMainResponse> qresponses;
    private volatile boolean finished;

    public TravelServicesRpcASMProxy(String host,int port){
        System.out.println("Creating TravelServicesRpcASMProxy ...");
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingDeque<generatedcode.TravelMainResponse>();
    }

    @Override
    public void login(Employee employee) throws TravelException {
        initializeConnection();
        System.out.println("login method stub ...");
        generatedcode.TravelMainRequest request = ProtoUtils.createLoginRequest(employee);
        sendRequest(request);
        TravelMainResponse response = readResponse();
        if( ProtoUtils.getTypeResponse(response).equals("OK") ){
            this.client = client;
            System.out.println("Client connected ...");
            return;
        }
        if( ProtoUtils.getTypeResponse(response).equals("ERROR") ){
            System.out.println("Error client connection ...");
            String err = ProtoUtils.getErrorResponse(response);
            closeConnection();
            throw new TravelException(err);
        }
    }

    @Override
    public void logout(Employee employee) throws TravelException {
        TravelMainRequest request = ProtoUtils.createLogoutRequest(employee);
        sendRequest(request);
        TravelMainResponse response = readResponse();
        closeConnection();
        if(ProtoUtils.getTypeResponse(response).equals("ERROR")){
            String err = ProtoUtils.getErrorResponse(response);
            throw new TravelException(err);
        }
    }

    @Override
    public Collection<Flight> filterFlightsByDestinationAndDeparture(String destination, LocalDate departure) throws TravelException {
        FlightDestDepDTO flightDestDepDTO = new FlightDestDepDTO(destination,departure);
        TravelMainRequest request = ProtoUtils.createFilterFlightsByDestinationAndDepartureRequest(flightDestDepDTO);
        sendRequest(request);
        TravelMainResponse response = readResponse();
        if(ProtoUtils.getTypeResponse(response).equals("ERROR")){
            String err = ProtoUtils.getErrorResponse(response);
            throw new TravelException(err);
        }
        Collection<Flight> flights = ProtoUtils.getFlightsCollectionResponse(response);
        return flights;
    }

    @Override
    public Collection<Flight> filterFlightByAvailableSeats() throws TravelException {
        TravelMainRequest request = ProtoUtils.createFilterFlightByAvailableSeatsRequest();
        sendRequest(request);
        TravelMainResponse response = readResponse();
        if(ProtoUtils.getTypeResponse(response).equals("ERROR")){
            throw new TravelException("Something went wrong");
        }
        Collection<Flight> flights = ProtoUtils.getFlightsCollectionResponse(response);
        return flights;
    }

    @Override
    public void addTicket(Ticket ticket) throws TravelException {
        System.out.println("balanciic " + ticket);
        TravelMainRequest request = ProtoUtils.createAddTicketRequest(ticket);
        sendRequest(request);
        TravelMainResponse response = readResponse();
        if(ProtoUtils.getTypeResponse(response).equals("ERROR")){
            String err = ProtoUtils.getErrorResponse(response);
            closeConnection();
            throw new TravelException(err);
        }
        if(ProtoUtils.getTypeResponse(response).equals("ERROR_UNVAILABLE_SEATS")){
            String err = ProtoUtils.getErrorResponse(response);
            throw new TravelException(err);
        }
    }

    @Override
    public void addFlight(Flight flight) throws TravelException {
        TravelMainRequest request = ProtoUtils.createAddFlightRequest(flight);
        sendRequest(request);
        TravelMainResponse response = readResponse();
        if(ProtoUtils.getTypeResponse(response).equals("ERROR")){
            String err = ProtoUtils.getErrorResponse(response);
            closeConnection();
            throw new TravelException(err);
        }
    }

    private void closeConnection(){
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(generatedcode.TravelMainRequest request) throws TravelException{
        try{
            System.out.println("Sending request ..."+request);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new TravelException("Error sending object " + e);
        }
    }

    private generatedcode.TravelMainResponse readResponse() throws TravelException{
        generatedcode.TravelMainResponse response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws TravelException{
        try{
            connection = new Socket(host, port);
            output = connection.getOutputStream();//new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = connection.getInputStream();//new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new TravelServicesRpcASMProxy.ReaderThread());
        tw.start();
    }

    private boolean isUpdate(TravelMainResponse response){
        return ProtoUtils.getTypeResponse(response).equals("ADD_FLIGHT") || ProtoUtils.getTypeResponse(response).equals("ADD_TICKET");
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while (!finished){
                try {
                    TravelMainResponse response = TravelMainResponse.parseDelimitedFrom(input);
                    System.out.println("response received " + response);
                    if(isUpdate(response)){

                    }else{

                        try{
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
