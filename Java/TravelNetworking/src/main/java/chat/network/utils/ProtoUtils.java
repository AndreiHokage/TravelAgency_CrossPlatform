package chat.network.utils;


import chat.network.FlightDestDepDTO;
import chat.network.ResponseType;
import com.google.protobuf.Timestamp;
import generatedcode.TravelMainRequest;
import generatedcode.TravelMainResponse;
import generatedcode.TravelRequest;
import travel.model.Flight;
import generatedcode.TravelResponse;
import travel.model.Employee;
import travel.model.Ticket;

import java.time.*;
import java.util.ArrayList;
import java.util.Collection;

public class ProtoUtils {

    private static Timestamp convertLocalDateTimeToTimeStampProto(LocalDateTime ldt) {
        LocalDateTime localDateTime = ldt;

        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();

        return timestamp;
    }

    public static Employee getUser(generatedcode.Employee employee){
        Employee client = new Employee(employee.getId(), employee.getUsername(), employee.getPassword());
        return client;
    }

    public static Flight getFlight(generatedcode.Flight flight){
        Timestamp ts = flight.getDeparture();
        LocalDateTime date =  Instant
                .ofEpochSecond( ts.getSeconds() , ts.getNanos() )
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        Flight clientFlight = new Flight(flight.getId(), flight.getDestination(), date,
                flight.getAirport(), flight.getAvailableSeats());
        return clientFlight;
    }

    public static FlightDestDepDTO getFlightDestDTO(generatedcode.FlightDestDepDTO flightDestDepDTO){
        Timestamp ts = flightDestDepDTO.getDeparture();
        LocalDate date =  Instant
                .ofEpochSecond( ts.getSeconds() , ts.getNanos() )
                .atZone(ZoneOffset.UTC)
                .toLocalDate();
        FlightDestDepDTO client = new FlightDestDepDTO(flightDestDepDTO.getDestination(), date);
        return client;
    }

    public static Ticket getTicket(generatedcode.Ticket ticket){
        Ticket clientTicket = new Ticket(ticket.getId(), ticket.getCustomerName(), ticket.getTouristName(),
                ticket.getCustomerAddress(), ticket.getSeats(), ProtoUtils.getFlight(ticket.getFlight()));
        return clientTicket;
    }

    private static generatedcode.Ticket createTicketMessage(Ticket ticket){
        generatedcode.Ticket worker = generatedcode.Ticket.newBuilder().setId(ticket.getID())
                .setCustomerName(ticket.getCustomerName()).setTouristName(ticket.getTouristName())
                .setCustomerAddress(ticket.getCustomerAddress()).setSeats(ticket.getSeats())
                .setFlight(ProtoUtils.createFlightMessage(ticket.getFlight())).build();
        return worker;
    }

    public static generatedcode.Flight createFlightMessage(Flight flight){
        Timestamp timestamp = ProtoUtils.convertLocalDateTimeToTimeStampProto(flight.getDeparture());

        generatedcode.Flight generateFlight = generatedcode.Flight.newBuilder().setId(flight.getID())
                .setDestination(flight.getDestination()).setDeparture(timestamp).setAirport(flight.getAirport())
                .setAvailableSeats(flight.getAvailableSeats()).build();
        return generateFlight;
    }


    public static generatedcode.FlightDestDepDTO createFlightDestDTOMessage(FlightDestDepDTO flightDestDepDTO){
        LocalDateTime fakeLtd = LocalDateTime.of(flightDestDepDTO.getDeparture(), LocalTime.now());
        Timestamp timestamp = ProtoUtils.convertLocalDateTimeToTimeStampProto( fakeLtd );
        generatedcode.FlightDestDepDTO worker = generatedcode.FlightDestDepDTO.newBuilder()
                .setDestination(flightDestDepDTO.getDestination()).setDeparture(timestamp).build();
        return worker;
    }

    private static generatedcode.Employee createEmployeeMessage(Employee employee){
        generatedcode.Employee worker = generatedcode.Employee.newBuilder().setId(employee.getID())
                .setUsername(employee.getUsername()).setPassword(employee.getPassword()).build();
        return worker;
    }

    public static generatedcode.TravelMainRequest createLoginRequest(Employee employee){
        generatedcode.TravelMainRequest request = generatedcode.TravelMainRequest.newBuilder()
                .setType(TravelMainRequest.Type.LOGIN).setUser(ProtoUtils.createEmployeeMessage(employee)).build();
        return request;
    }

    public static generatedcode.TravelMainRequest createLogoutRequest(Employee employee){
        generatedcode.TravelMainRequest request = generatedcode.TravelMainRequest.newBuilder()
                .setType(TravelMainRequest.Type.LOGOUT).setUser(ProtoUtils.createEmployeeMessage(employee)).build();
        return request;
    }

    public static generatedcode.TravelMainRequest createFilterFlightsByDestinationAndDepartureRequest(FlightDestDepDTO flightDestDepDTO){
        generatedcode.TravelMainRequest request = generatedcode.TravelMainRequest.newBuilder()
                .setType(TravelMainRequest.Type.FLIGHTS_BY_DESTINATION_AND_DEPARTURE).
                setFlightDestDepDTO(ProtoUtils.createFlightDestDTOMessage(flightDestDepDTO)).build();
        return request;
    }

    public static generatedcode.TravelMainRequest createFilterFlightByAvailableSeatsRequest(){
        generatedcode.TravelMainRequest request = generatedcode.TravelMainRequest.newBuilder()
                .setType(TravelMainRequest.Type.FLIGHT_BY_AVAILABLE_SEATS).build();
        return request;
    }

    public static generatedcode.TravelMainRequest createAddTicketRequest(Ticket ticket){
        generatedcode.TravelMainRequest request = generatedcode.TravelMainRequest.newBuilder()
                .setType(TravelMainRequest.Type.BUY_TICKET_TRAVEL)
                .setTicket(ProtoUtils.createTicketMessage(ticket)).build();
        return request;
    }

    public static generatedcode.TravelMainRequest createAddFlightRequest(Flight flight){
        generatedcode.TravelMainRequest request = generatedcode.TravelMainRequest.newBuilder()
                .setType(TravelMainRequest.Type.FLIGHT_ADD_COMPANY)
                .setFlight(ProtoUtils.createFlightMessage(flight)).build();
        return request;
    }

    public static generatedcode.TravelMainResponse createOkResponse(){
        generatedcode.TravelMainResponse response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.OK).build();
        return response;
    }

    public static generatedcode.TravelMainResponse createErrorResponse(String text){
        generatedcode.TravelMainResponse response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.ERROR).setError(text).build();
        return response;
    }

    public static generatedcode.TravelMainResponse createErrorNoSeatsResponse(String text){
        generatedcode.TravelMainResponse response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.ERROR_UNVAILABLE_SEATS).setError(text).build();
        return response;
    }

    public static generatedcode.TravelMainResponse createFlightResponse(Flight flight){
        generatedcode.TravelMainResponse response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.ADD_FLIGHT)
                .setFlight(ProtoUtils.createFlightMessage(flight)).build();
        return response;
    }

    public static generatedcode.TravelMainResponse createTicketResponse(Ticket ticket){
        generatedcode.TravelMainResponse response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.ADD_TICKET)
                .setTicket(ProtoUtils.createTicketMessage(ticket)).build();
        return response;
    }

    public static generatedcode.TravelMainResponse createAvailableSeatsResponse(Collection<Flight> flights){
        TravelMainResponse.Builder response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.AVAILABLE_SEATS);
        for(Flight flight: flights){
            response.addFlights(ProtoUtils.createFlightMessage(flight));
        }
        return response.build();
    }

    public static generatedcode.TravelMainResponse createDestinationDepartureResponse(Collection<Flight> flights){
        TravelMainResponse.Builder response = generatedcode.TravelMainResponse.newBuilder()
                .setType(TravelMainResponse.Type.FLIGHT_DESTINATION_DEPARTURE);
        for(Flight flight: flights){
            response.addFlights(ProtoUtils.createFlightMessage(flight));
        }
        return response.build();
    }

    public static String getErrorResponse(TravelMainResponse response){
        String error = response.getError();;
        return error;
    }

    public static String getTypeResponse(TravelMainResponse response){
        String responseType = response.getType().toString();
        return responseType;
    }

    public static String getTypeRequest(TravelMainRequest request){
        String requestType = request.getType().toString();
        return requestType;
    }

    public static Collection<Flight> getFlightsCollectionResponse(TravelMainResponse response){
        Collection<Flight> flights = new ArrayList<>();
        for(int i=0;i<response.getFlightsCount();i++){
            generatedcode.Flight flight = response.getFlights(i);
            Flight flight1 = ProtoUtils.getFlight(flight);
            flights.add(flight1);
        }
        return flights;
    }

    public static Flight getFlightResponse(TravelMainResponse response){
        return ProtoUtils.getFlight(response.getFlight());
    }

    public static Ticket getTicketResponse(TravelMainResponse response){
        return ProtoUtils.getTicket(response.getTicket());
    }

    public static Employee getEmployeeRequest(TravelMainRequest request){
        return ProtoUtils.getUser(request.getUser());
    }

    public static FlightDestDepDTO getFlightDestDepRequest(TravelMainRequest request){
        return ProtoUtils.getFlightDestDTO(request.getFlightDestDepDTO());
    }

    public static Flight getFlightRequest(TravelMainRequest request){
        return ProtoUtils.getFlight(request.getFlight());
    }

    public static Ticket getTicketRequest(TravelMainRequest request){
        return ProtoUtils.getTicket(request.getTicket());
    }

//
//    public static Employee getUser(generatedcode.Employee employee){
//        Employee client = new Employee(employee.getId(), employee.getUsername(), employee.getPassword());
//        return client;
//    }
//
//    public static FlightDestDepDTO getFlightDestDTO(generatedcode.FlightDestDepDTO flightDestDepDTO){
//        Timestamp ts = flightDestDepDTO.getDeparture();
//        LocalDate date =  Instant
//                .ofEpochSecond( ts.getSeconds() , ts.getNanos() )
//                .atZone(ZoneOffset.UTC)
//                .toLocalDate();
//        FlightDestDepDTO client = new FlightDestDepDTO(flightDestDepDTO.getDestination(), date);
//        return client;
//    }
//
//    public static Flight getFlight(generatedcode.Flight flight){
//        Timestamp ts = flight.getDeparture();
//        LocalDateTime date =  Instant
//                .ofEpochSecond( ts.getSeconds() , ts.getNanos() )
//                .atZone(ZoneOffset.UTC)
//                .toLocalDateTime();
//        Flight clientFlight = new Flight(flight.getId(), flight.getDestination(), date,
//                flight.getAirport(), flight.getAvailableSeats());
//        return clientFlight;
//    }
//
//    public static Ticket getTicket(generatedcode.Ticket ticket){
//        Ticket clientTicket = new Ticket(ticket.getId(), ticket.getCustomerName(), ticket.getTouristName(),
//                ticket.getCustomerAddress(), ticket.getSeats(), ProtoUtils.getFlight(ticket.getFlight()));
//        return clientTicket;
//    }
//
//    public static String getErrorResponse(TravelResponse response){
//        String error = response.getError();;
//        return error;
//    }
//
//    public static String getTypeResponse(TravelResponse response){
//        String responseType = response.getType().toString();
//        return responseType;
//    }
//
//    public static generatedcode.TravelRequest createEmptyRequest(){
//        return generatedcode.TravelRequest.newBuilder().setType(TravelRequest.Type.FLIGHT_BY_AVAILABLE_SEATS).build();
//    }
//
//    public static TravelResponse createOkResponse(){
//        TravelResponse response = TravelResponse.newBuilder().setType(TravelResponse.Type.OK).build();
//        return response;
//    }
//
//    public static TravelResponse createErrorResponse(String text){
//        TravelResponse response = TravelResponse.newBuilder().setType(TravelResponse.Type.ERROR)
//                .setError(text).build();
//        return response;
//    }
//
//    public static generatedcode.Flight createFlightMessage(Flight flight){
//        Timestamp timestamp = ProtoUtils.convertLocalDateTimeToTimeStampProto(flight.getDeparture());
//
//        generatedcode.Flight generateFlight = generatedcode.Flight.newBuilder().setId(flight.getID())
//                .setDestination(flight.getDestination()).setDeparture(timestamp).setAirport(flight.getAirport())
//                .setAvailableSeats(flight.getAvailableSeats()).build();
//        return generateFlight;
//    }
//
//    public static generatedcode.Ticket createTicketMessage(Ticket ticket){
//        generatedcode.Ticket worker = generatedcode.Ticket.newBuilder().setId(ticket.getID())
//                .setCustomerName(ticket.getCustomerName()).setTouristName(ticket.getTouristName())
//                .setCustomerAddress(ticket.getCustomerAddress()).setSeats(ticket.getSeats())
//                .setFlight(ProtoUtils.createFlightMessage(ticket.getFlight())).build();
//        return worker;
//    }
//
//    public static generatedcode.FlightDestDepDTO createFlightDestDTOMessage(FlightDestDepDTO flightDestDepDTO){
//        LocalDateTime fakeLtd = LocalDateTime.of(flightDestDepDTO.getDeparture(), LocalTime.now());
//        Timestamp timestamp = ProtoUtils.convertLocalDateTimeToTimeStampProto( fakeLtd );
//        generatedcode.FlightDestDepDTO worker = generatedcode.FlightDestDepDTO.newBuilder()
//                .setDestination(flightDestDepDTO.getDestination()).setDeparture(timestamp).build();
//        return worker;
//    }
//
//    public static generatedcode.Employee createEmployeeMessage(Employee employee){
//        generatedcode.Employee worker = generatedcode.Employee.newBuilder().setId(employee.getID())
//                .setUsername(employee.getUsername()).setPassword(employee.getPassword()).build();
//        return worker;
//    }

}
