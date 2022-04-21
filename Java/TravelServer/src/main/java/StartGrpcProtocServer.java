//import chat.network.FlightDestDepDTO;
//import chat.persistence.*;
//import generatedcode.ITravelServicesProtoGrpc;
//import chat.network.utils.ProtoUtils;
//import generatedcode.TravelResponse;
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import io.grpc.stub.StreamObserver;
//import travel.model.Employee;
//import travel.model.Flight;
//import travel.model.Ticket;
//import travel.model.validators.FlightValidator;
//import travel.model.validators.TicketValidator;
//import travel.model.validators.ValidationException;
//import travel.services.ITravelObserver;
//import travel.services.TravelException;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class StartGrpcProtocServer {
//
//    private static int defaultPort = 55555;
//    private Server server;
//
//    private void start() throws IOException {
//        Properties serverProps = new Properties();
//        try{
//            serverProps.load(StartRpcServer.class.getResourceAsStream("/travelServer.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find travelserver.properties" + e);
//            return;
//        }
//
//        FlightValidator flightValidator = new FlightValidator();
//        TicketValidator ticketValidator = new TicketValidator();
//        EmployeeRepository employeeRepository = new EmployeeDBRepository(serverProps);
//        FlightRepository flightRepository = new FlightDBRepository(serverProps);
//        TicketRepository ticketRepository = new TicketDBRepository(flightRepository, serverProps);
//
//        int travelServerPort = defaultPort;
//        try{
//            travelServerPort = Integer.parseInt(serverProps.getProperty("travel.server.port"));
//        }catch(NumberFormatException nef){
//            System.err.println("Wrong  Port Number"+nef.getMessage());
//            System.err.println("Using default port "+defaultPort);
//        }
//
//        System.out.println("Starting server on port: " + travelServerPort);
//
//        server = ServerBuilder.forPort(travelServerPort)
//                .addService(new TravelServicesGrpcImpl(employeeRepository, flightRepository,
//                        flightValidator, ticketRepository, ticketValidator))
//                .build()
//                .start();
//        System.out.println("Server gRPC Protoc started, listening on " + travelServerPort);
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
//                System.err.println("*** shutting down gRPC server since JVM is shutting down");
//                try {
//                    StartGrpcProtocServer.this.stop();
//                } catch (InterruptedException e) {
//                    e.printStackTrace(System.err);
//                }
//                System.err.println("*** server shut down");
//            }
//        });
//    }
//
//    private void stop() throws InterruptedException {
//        if(server != null){
//            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
//        }
//    }
//
//    private void blockUntilShutdown() throws InterruptedException {
//        if(server != null){
//            server.awaitTermination();
//        }
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        final StartGrpcProtocServer server = new StartGrpcProtocServer();
//        server.start();
//        server.blockUntilShutdown();
//    }
//
//    static class TravelServicesGrpcImpl extends ITravelServicesProtoGrpc.ITravelServicesProtoImplBase{
//
//        private EmployeeRepository employeeRepository;
//        private FlightRepository flightRepository;
//        private FlightValidator flightValidator;
//        private TicketRepository ticketRepository;
//        private TicketValidator ticketValidator;
//        private Map<String, StreamObserver<generatedcode.TravelResponse>> loggedEmployee;
//        private static LinkedHashSet<StreamObserver<generatedcode.Ticket>> observers = new LinkedHashSet<>();
//
//
//        public TravelServicesGrpcImpl(EmployeeRepository employeeRepository, FlightRepository flightRepository, FlightValidator flightValidator,
//                                  TicketRepository ticketRepository, TicketValidator ticketValidator) {
//            System.out.println("Implement services in server-part .......");
//            this.employeeRepository = employeeRepository;
//            this.flightRepository = flightRepository;
//            this.flightValidator = flightValidator;
//            this.ticketRepository = ticketRepository;
//            this.ticketValidator = ticketValidator;
////            loggedEmployee = new ConcurrentHashMap<>();
//            loggedEmployee = new HashMap<>();
//        }
//
//        @Override
//        public void login(generatedcode.Employee request,
//                          StreamObserver<generatedcode.TravelResponse> responseObserver){
//            System.out.println("login method called in grpc server...");
//            Employee employee = ProtoUtils.getUser(request);
//            boolean isLogged = employeeRepository.filterByUsernameAndPassword(employee.getUsername(), employee.getPassword());
//            if(isLogged){
//                if(loggedEmployee.get(employee.getUsername()) != null) {
//                    //throw new TravelException("Employee already logged in");
//                    responseObserver.onNext(ProtoUtils.createErrorResponse("Employee already logged in"));
//                    responseObserver.onCompleted();
//                    return;
//                }
//                loggedEmployee.put(employee.getUsername(), responseObserver);
//                responseObserver.onNext(ProtoUtils.createOkResponse());
//                responseObserver.onCompleted();
//            }else {
//                responseObserver.onNext(ProtoUtils.createErrorResponse("Authentication failed"));
//                responseObserver.onCompleted();
//            }
//        }
//
//        @Override
//        public void logout(generatedcode.Employee request,
//                           StreamObserver<generatedcode.TravelResponse> responseObserver){
//            System.out.println("logout method called in grpc server...");
//            Employee employee = ProtoUtils.getUser(request);
//            StreamObserver<generatedcode.TravelResponse> localClient = loggedEmployee.remove(employee.getUsername());
//            if( localClient == null ) {
//                //throw new TravelException("Employee " + employee.getUsername() + " is not logged in.");
//                responseObserver.onNext(ProtoUtils.createErrorResponse("Employee " + employee.getUsername() + " is not logged in."));
//                responseObserver.onCompleted();
//                return;
//            }
//            responseObserver.onNext(ProtoUtils.createOkResponse());
//            responseObserver.onCompleted();
//        }
//
//        @Override
//        public void filterFlightsByDestinationAndDeparture(generatedcode.FlightDestDepDTO request,
//                                                           StreamObserver<generatedcode.Flight> responseObserver){
//            System.out.println("filterFlightsByDestinationAndDeparture method called in grpc server...");
//            FlightDestDepDTO flightDestDepDTO = ProtoUtils.getFlightDestDTO(request);
//            String destination = flightDestDepDTO.getDestination();
//            LocalDate departure = flightDestDepDTO.getDeparture();
//            Collection<Flight> flights = flightRepository.filterFlightByDestinationAndDeparture(destination, departure);
//            for(Flight flight: flights)
//                responseObserver.onNext(ProtoUtils.createFlightMessage(flight));
//            responseObserver.onCompleted();
//        }
//
//        @Override
//        public void filterFlightByAvailableSeats(generatedcode.TravelRequest request,
//                                                 StreamObserver<generatedcode.Flight> responseObserver){
//            System.out.println("filterFlightByAvailableSeats method called in grpc server...");
//            Collection<Flight> flights = flightRepository.filterFlightByAvailableSeats();
//            for(Flight flight: flights)
//                responseObserver.onNext(ProtoUtils.createFlightMessage(flight));
//            responseObserver.onCompleted();
//        }
//
//        @Override
//        public StreamObserver<generatedcode.Ticket> addTicket(
//                StreamObserver<generatedcode.Ticket> responseObserver){
//            System.out.println("addTicket method called in grpc server...");
//            observers.add(responseObserver);
//
//            return new StreamObserver<generatedcode.Ticket>() {
//                @Override
//                public void onNext(generatedcode.Ticket value) {
//                    Ticket ticket = ProtoUtils.getTicket(value);
//                    try {
//                        Flight flight = ticket.getFlight();
//                        flight.setAvailableSeats(flight.getAvailableSeats() - ticket.getSeats());
//                        flightValidator.validate(flight);
//                        ticketValidator.validate(ticket);
//                        flightRepository.update(flight, flight.getID());
//
//                        ticket.setFlight(flightRepository.findByID(flight.getID()));
//                        ticketRepository.add(ticket);
//                    }catch (ValidationException ex){
//                        System.out.println(ex.getMessage());
//                    }
//                    for(StreamObserver<generatedcode.Ticket> observer: observers){
//                        observer.onNext(ProtoUtils.createTicketMessage(ticket));
//                    }
//                }
//
//                @Override
//                public void onError(Throwable t) {
//
//                }
//
//                @Override
//                public void onCompleted() {
//
//                }
//            };
//        }
//
//        @Override
//        public void addFlight(generatedcode.Flight request,
//                              StreamObserver<generatedcode.TravelResponse> responseObserver){
//
//        }
//
//
//    }
//}
