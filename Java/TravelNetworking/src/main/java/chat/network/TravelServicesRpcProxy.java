package chat.network;

import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;
import travel.services.ITravelObserver;
import travel.services.ITravelServices;
import travel.services.TravelException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.SocketHandler;

public class TravelServicesRpcProxy implements ITravelServices {
    private String host;
    private int port;

    private ITravelObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public TravelServicesRpcProxy(String host,int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingDeque<Response>();
    }


    @Override
    public void login(Employee employee, ITravelObserver client) throws TravelException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOGIN).data(employee).build();
        sendRequest(req);
        Response response = readResponse();
        if( response.type() == ResponseType.OK ){
            this.client = client;
            return;
        }
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
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

    @Override
    public void logout(Employee employee, ITravelObserver client) throws TravelException {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(employee).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new TravelException(err);
        }
    }

    @Override
    public Collection<Flight> filterFlightsByDestinationAndDeparture(String destination, LocalDate departure) throws TravelException {
        FlightDestDepDTO flightDestDepDTO = new FlightDestDepDTO(destination,departure);
        Request req = new Request.Builder().type(RequestType.FLIGHTS_BY_DESTINATION_AND_DEPARTURE).data(flightDestDepDTO).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new TravelException(err);
        }
        Collection<Flight> flights = (Collection<Flight>) response.data();
        return flights;
    }

    @Override
    public Collection<Flight> filterFlightByAvailableSeats() throws TravelException {
        Request req = new Request.Builder().type(RequestType.FLIGHT_BY_AVAILABLE_SEATS).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            throw new TravelException("Something went wrong");
        }
        Collection<Flight> flights = (Collection<Flight>) response.data();
        return flights;
    }

    @Override
    public void addTicket(Ticket ticket) throws TravelException {
        Request req = new Request.Builder().type(RequestType.BUY_TICKET_TRAVEL).data(ticket).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            closeConnection();
            throw new TravelException(err);
        }
    }

    @Override
    public void addFlight(Flight flight) throws TravelException {
        Request req = new Request.Builder().type(RequestType.FLIGHT_ADD_COMPANY).data(flight).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            closeConnection();
            throw new TravelException(err);
        }
    }


    private void sendRequest(Request request) throws TravelException{
        try{
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TravelException("Error sending object " + e);
        }
    }

    private Response readResponse() throws TravelException{
        Response response = null;
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
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if(response.type() == ResponseType.ADD_TICKET){
            Ticket ticket = (Ticket) response.data();
            System.out.println("Ticket update for observer");
            try {
                client.soldTicket(ticket);
            } catch (TravelException e) {
                e.printStackTrace();
            }
        }

        if(response.type() == ResponseType.ADD_FLIGHT){
            Flight flight = (Flight) response.data();
            System.out.println("Flight update for observer");
            try {
                client.saveFlight(flight);
            } catch (TravelException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.ADD_TICKET || response.type() == ResponseType.ADD_FLIGHT;
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while (!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if(isUpdate((Response)response)){
                        handleUpdate((Response) response);
                    }else{

                        try{
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
