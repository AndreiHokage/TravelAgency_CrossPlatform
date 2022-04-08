import chat.network.utils.AbstractServer;
import chat.network.utils.ServerException;
import chat.network.utils.TravelRpcConcurrentServer;
import chat.persistence.*;
import travel.model.Employee;
import travel.model.validators.FlightValidator;
import travel.model.validators.TicketValidator;
import travel.server.TravelServicesImpl;
import travel.services.ITravelServices;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {

        Properties serverProps = new Properties();
        try{
            serverProps.load(StartRpcServer.class.getResourceAsStream("/travelServer.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find travelserver.properties" + e);
            return;
        }

        FlightValidator flightValidator = new FlightValidator();
        TicketValidator ticketValidator = new TicketValidator();
        EmployeeRepository employeeRepository = new EmployeeDBRepository(serverProps);
        FlightRepository flightRepository = new FlightDBRepository(serverProps);
        TicketRepository ticketRepository = new TicketDBRepository(flightRepository, serverProps);
        ITravelServices travelServerImpl = new TravelServicesImpl(employeeRepository,flightRepository,flightValidator,ticketRepository,ticketValidator);

        int travelServerPort = defaultPort;
        try{
            travelServerPort = Integer.parseInt(serverProps.getProperty("travel.server.port"));
        }catch(NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }

        System.out.println("Starting server on port: " + travelServerPort);
        AbstractServer server = new TravelRpcConcurrentServer(travelServerPort, travelServerImpl);
        try{
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server " + e.getMessage());
        }finally {
            try{
                server.stop();
            } catch (ServerException e) {
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
