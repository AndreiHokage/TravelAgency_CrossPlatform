import chat.network.utils.AbstractServer;
import chat.network.utils.ChatProtobuffConcurrentServer;
import chat.network.utils.ServerException;
import chat.persistence.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import travel.model.Flight;
import travel.model.validators.FlightValidator;
import travel.model.validators.TicketValidator;
import travel.server.TravelServicesImpl;
import travel.services.ITravelServices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 55555;

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            System.out.println("It has been created in StartRpcServer");
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

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
        //FlightRepository flightRepository = new FlightDBRepository(serverProps);
        initialize();
        FlightRepository flightRepository = new FlightHibernateRepository(sessionFactory);
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
        //AbstractServer server = new TravelRpcConcurrentServer(travelServerPort, travelServerImpl);
        AbstractServer server = new ChatProtobuffConcurrentServer(travelServerPort, travelServerImpl);
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
