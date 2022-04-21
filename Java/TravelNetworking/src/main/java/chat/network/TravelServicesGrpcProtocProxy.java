//package chat.network;
//
//import chat.network.utils.ProtoUtils;
//import generatedcode.ITravelServicesProtoGrpc;
//import generatedcode.TravelRequest;
//import generatedcode.TravelResponse;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.stub.StreamObserver;
//import travel.model.Employee;
//import travel.model.Flight;
//import travel.model.Ticket;
//import travel.services.ITravelObserver;
//import travel.services.TravelException;
//
//import javax.sound.sampled.Port;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.concurrent.TimeUnit;
//
//public class TravelServicesGrpcProtocProxy {
//
//    private ITravelObserver clientCtrl;
//    private final ManagedChannel channel;
//    private final ITravelServicesProtoGrpc.ITravelServicesProtoBlockingStub blockingStub;
//    private final ITravelServicesProtoGrpc.ITravelServicesProtoStub versionStub;
//    private final StreamObserver<generatedcode.Ticket> mainObserver;
//
//    public TravelServicesGrpcProtocProxy(String host, int port){
//        this(ManagedChannelBuilder.forAddress(host, port)
//                .usePlaintext()
//                .build());
//    }
//
//    TravelServicesGrpcProtocProxy(ManagedChannel channel){
//        this.channel = channel;
//        blockingStub = ITravelServicesProtoGrpc.newBlockingStub(channel);
//        versionStub = ITravelServicesProtoGrpc.newStub(channel);
//        mainObserver = versionStub.addTicket(new StreamObserver<generatedcode.Ticket>() {
//            @Override
//            public void onNext(generatedcode.Ticket value) {
//                try {
//                    clientCtrl.soldTicket(ProtoUtils.getTicket(value));
//                } catch (TravelException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//        });
//    }
//
//    public void shutdown() throws InterruptedException {
//        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
//    }
//
//    public void login(Employee employee, ITravelObserver client) throws TravelException, InterruptedException {
//        System.out.println("Invoked login method in Stub.....");
//        generatedcode.Employee request = ProtoUtils.createEmployeeMessage(employee);
//        TravelResponse response;
//        response = blockingStub.login(request);
//
//        if( ProtoUtils.getTypeResponse(response).equals("OK") ){
//            this.clientCtrl = client;
//            System.out.println("Client connected ...");
//            return;
//        }
//        if( ProtoUtils.getTypeResponse(response).equals("ERROR") ){
//            System.out.println("Error client connection ...");
//            String err = ProtoUtils.getErrorResponse(response);
//            throw new TravelException(err);
//        }
//    }
//
//
//    public void logout(Employee employee, ITravelObserver client) throws TravelException, InterruptedException {
//        System.out.println("Invoked logout method in Stub.....");
//        generatedcode.Employee request = ProtoUtils.createEmployeeMessage(employee);
//        TravelResponse response;
//        response = blockingStub.logout(request);
//        shutdown();
//        if( ProtoUtils.getTypeResponse(response).equals("ERROR")){
//            System.out.println("Error logout stub...");
//            String err = ProtoUtils.getErrorResponse(response);
//            throw new TravelException(err);
//        }
//    }
//
//    public Collection<Flight> filterFlightsByDestinationAndDeparture(String destination, LocalDate departure) throws TravelException {
//        System.out.println("Invoked filterFlightsByDestinationAndDeparture method in Stub.....");
//        FlightDestDepDTO flightDestDepDTO = new FlightDestDepDTO(destination,departure);
//        generatedcode.FlightDestDepDTO request = ProtoUtils.createFlightDestDTOMessage(flightDestDepDTO);
//        Collection<Flight> flights = new ArrayList<>();
//        Iterator<generatedcode.Flight> response;
//        response = blockingStub.filterFlightsByDestinationAndDeparture(request);
//        while(response.hasNext()){
//            generatedcode.Flight currentObject = response.next();
//            Flight flight = ProtoUtils.getFlight(currentObject);
//            System.out.println("Flight filtered: " + flight);
//            flights.add(flight);
//        }
//        return flights;
//    }
//
//    public Collection<Flight> filterFlightByAvailableSeats() throws TravelException {
//        System.out.println("Invoked filterFlightByAvailableSeats method in Stub.....");
//        generatedcode.TravelRequest request = ProtoUtils.createEmptyRequest();
//        Collection<Flight> flights = new ArrayList<>();
//        Iterator<generatedcode.Flight> response;
//        response = blockingStub.filterFlightByAvailableSeats(request);
//        while(response.hasNext()){
//            generatedcode.Flight currentObject = response.next();
//            Flight flight = ProtoUtils.getFlight(currentObject);
//            System.out.println("Available flight: " + flight);
//            flights.add(flight);
//        }
//        return flights;
//    }
//
//    public void addTicket(Ticket ticket) throws TravelException, InterruptedException {
//        System.out.println("Invoked addTicket method in Stub.....");
//        generatedcode.Ticket request = ProtoUtils.createTicketMessage(ticket);
//        mainObserver.onNext(request);
//    }
//
//    public void addFlight(Flight flight) throws TravelException {
//    }
//}
