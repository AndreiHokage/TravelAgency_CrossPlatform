package chat.network;

import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;
import travel.model.validators.ValidationException;
import travel.services.ITravelObserver;
import travel.services.ITravelServices;
import travel.services.TravelException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Collection;

public class TravelClientRpcReflectionWorker implements Runnable, ITravelObserver {
    private ITravelServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public TravelClientRpcReflectionWorker(ITravelServices server, Socket connection){
        System.out.println("Create ClientRpcReflectionWorker");
        this.server = server;
        this.connection = connection;

        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(connected){
            try{
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if(response != null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void soldTicket(Ticket ticket) throws TravelException {
        Response resp = new Response.Builder().type(ResponseType.ADD_TICKET).data(ticket).build();
        System.out.println("Ticket added " + ticket);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFlight(Flight flight) throws TravelException {
        Response resp = new Response.Builder().type(ResponseType.ADD_FLIGHT).data(flight).build();
        System.out.println("Flight added " + flight);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);

        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..." + request.type());
        Employee employee = (Employee)request.data();
        try{
            server.login(employee, this);
            return okResponse;
        } catch (TravelException e) {
            connected = false;
            return  new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("LogOUT request ...");
        Employee employee = (Employee) request.data();
        try {
            server.logout(employee, this);
            connected = false;
            return okResponse;
        } catch (TravelException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFLIGHTS_BY_DESTINATION_AND_DEPARTURE(Request request){
        System.out.println("Filter flights by destination and departure ...");
        FlightDestDepDTO flightDestDepDTO = (FlightDestDepDTO) request.data();
        String destination = flightDestDepDTO.getDestination();
        LocalDate departure = flightDestDepDTO.getDeparture();
        try{
            Collection<Flight> flights = server.filterFlightsByDestinationAndDeparture(destination, departure);
            return new Response.Builder().type(ResponseType.FLIGHT_DESTINATION_DEPARTURE).data(flights).build();
        } catch (TravelException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFLIGHT_BY_AVAILABLE_SEATS(Request request){
        System.out.println("Filter flights by available seats ...");
        try{
            Collection<Flight> flights = server.filterFlightByAvailableSeats();
            return new Response.Builder().type(ResponseType.AVAILABLE_SEATS).data(flights).build();
        } catch (TravelException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleBUY_TICKET_TRAVEL(Request request){
        System.out.println("savind ticket ...");
        Ticket ticket = (Ticket) request.data();
        System.out.println("Balancii ai primit " + ticket);
        try{
            server.addTicket(ticket);
            return okResponse;
        } catch (TravelException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }catch (ValidationException e) {
            return new Response.Builder().type(ResponseType.ERROR_UNVAILABLE_SEATS).data(e.getMessage()).build();
        }
    }

    private Response handleFLIGHT_ADD_COMPANY(Request request){
        System.out.println("saving flight ...");
        Flight flight = (Flight) request.data();
        try{
            server.addFlight(flight);
            return okResponse;
        } catch (TravelException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
}
