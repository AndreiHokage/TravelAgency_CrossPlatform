package chat.network.ams;

import chat.network.FlightDestDepDTO;
import chat.network.utils.ProtoUtils;
import generatedcode.TravelMainRequest;
import generatedcode.TravelMainResponse;
import travel.model.Employee;
import travel.model.Flight;
import travel.model.Ticket;
import travel.model.validators.ValidationException;
import travel.services.ITravelASMServices;
import travel.services.ITravelServices;
import travel.services.TravelException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Collection;

public class TravelClientRpcASMReflectionWorker implements Runnable{

    private ITravelASMServices server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;

    public TravelClientRpcASMReflectionWorker(ITravelASMServices server, Socket connection){
        System.out.println("Create ClientRpcReflectionWorker");
        this.server = server;
        this.connection = connection;

        try {
            output = connection.getOutputStream(); //new ObjectOutputStream(connection.getOutputStream());
            //output.flush();
            input = connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());//new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(connected){
            try{
                TravelMainRequest request = TravelMainRequest.parseDelimitedFrom(input);
                TravelMainResponse response = handleRequest(request);
                if(response != null){
                    sendResponse(response);
                }
            } catch (IOException e) {
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


    //private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private TravelMainResponse handleRequest(TravelMainRequest request){
        TravelMainResponse response = null;
        String handlerName = "handle" + ProtoUtils.getTypeRequest(request);
        System.out.println("HandlerName " + handlerName);

        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, TravelMainRequest.class);
            response = (TravelMainResponse) method.invoke(this, request);
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

    private void sendResponse(TravelMainResponse response) throws IOException {
        System.out.println("sending response " + response);
        response.writeDelimitedTo(output);
        output.flush();
    }

    private TravelMainResponse handleLOGIN(TravelMainRequest request){
        System.out.println("Login request ..." + ProtoUtils.getTypeRequest(request));
        Employee employee = ProtoUtils.getEmployeeRequest(request);
        try{
            server.login(employee);
            return ProtoUtils.createOkResponse();
        } catch (TravelException e) {
            connected = false;
            return ProtoUtils.createErrorResponse(e.getMessage());
        }
    }

    private TravelMainResponse handleLOGOUT(TravelMainRequest request){
        System.out.println("LogOUT request ...");
        Employee employee = ProtoUtils.getEmployeeRequest(request);
        try {
            server.logout(employee);
            connected = false;
            return ProtoUtils.createOkResponse();
        } catch (TravelException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }
    }

    private TravelMainResponse handleFLIGHTS_BY_DESTINATION_AND_DEPARTURE(TravelMainRequest request){
        System.out.println("Filter flights by destination and departure ...");
        FlightDestDepDTO flightDestDepDTO = ProtoUtils.getFlightDestDepRequest(request);
        String destination = flightDestDepDTO.getDestination();
        LocalDate departure = flightDestDepDTO.getDeparture();
        try{
            Collection<Flight> flights = server.filterFlightsByDestinationAndDeparture(destination, departure);
            return ProtoUtils.createDestinationDepartureResponse(flights);
        } catch (TravelException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }
    }

    private TravelMainResponse handleFLIGHT_BY_AVAILABLE_SEATS(TravelMainRequest request){
        System.out.println("Filter flights by available seats ...");
        try{
            Collection<Flight> flights = server.filterFlightByAvailableSeats();
            return ProtoUtils.createAvailableSeatsResponse(flights);
        } catch (TravelException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }
    }

    private TravelMainResponse handleBUY_TICKET_TRAVEL(TravelMainRequest request){
        System.out.println("savind ticket ...");
        Ticket ticket = ProtoUtils.getTicketRequest(request);
        System.out.println("Balancii ai primit " + ticket);
        try{
            server.addTicket(ticket);
            return ProtoUtils.createOkResponse();
        } catch (TravelException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }catch (ValidationException e) {
            return ProtoUtils.createErrorNoSeatsResponse(e.getMessage());
        }
    }

    private TravelMainResponse handleFLIGHT_ADD_COMPANY(TravelMainRequest request){
        System.out.println("saving flight ...");
        Flight flight = ProtoUtils.getFlightRequest(request);
        try{
            server.addFlight(flight);
            return ProtoUtils.createOkResponse();
        } catch (TravelException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }
    }
}
