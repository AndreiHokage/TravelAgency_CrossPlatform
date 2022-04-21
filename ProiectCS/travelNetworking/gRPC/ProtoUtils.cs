using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using generatedcode;
using Google.Protobuf.WellKnownTypes;
using Employee = travelModel.Employee;
using Flight = travelModel.Flight;
using Ticket = travelModel.Ticket;


namespace travelNetworking.gRPC
{
    public class ProtoUtils
    {
        public static Employee getUser(generatedcode.Employee employee)
        {
            Employee client = new Employee(employee.Id, employee.Username, employee.Password);
            return client;
        }

        public static Flight getFlight(generatedcode.Flight flight)
        {
            DateTime dt = flight.Departure.ToDateTime();
            Flight client = new Flight(flight.Id, flight.Destination, dt, 
                flight.Airport, flight.AvailableSeats);
            return client;
        }

        public static FlightDestDepDTO getFlightDestDTO(generatedcode.FlightDestDepDTO flightDestDepDto)
        {
            DateTime dt = flightDestDepDto.Departure.ToDateTime();
            FlightDestDepDTO client = new FlightDestDepDTO(flightDestDepDto.Destination, dt);
            return client;
        }

        public static Ticket getTicket(generatedcode.Ticket ticket)
        {
            Ticket clientTicket = new Ticket(ticket.Id, ticket.CustomerName, ticket.TouristName,
                ticket.CustomerAddress, ticket.Seats, ProtoUtils.getFlight(ticket.Flight));
            return clientTicket;
        }
        
        public static TravelMainResponse createOkResponse()
        {
            TravelMainResponse response = new TravelMainResponse(){
                Type = TravelMainResponse.Types.Type.Ok
            };
            return response;
        }
        
        public static TravelMainResponse createErrorResponse(string text)
        {
            TravelMainResponse response = new TravelMainResponse(){
                Type = TravelMainResponse.Types.Type.Error,
                Error = text
            };
            return response;
        }
        
        public static TravelMainResponse createErrorNoSeatsResponse(string text)
        {
            TravelMainResponse response = new TravelMainResponse(){
                Type = TravelMainResponse.Types.Type.ErrorUnvailableSeats,
                Error = text
            };
            return response;
        }

        public static generatedcode.Flight createFlightMessage(Flight flight)
        { 
            var ts = Timestamp.FromDateTimeOffset(flight.Departure);
            generatedcode.Flight worker= new generatedcode.Flight
            {
                Id = flight.ID,
                Destination = flight.Destination,
                Departure = ts,
                Airport = flight.Airport,
                AvailableSeats = flight.AvailableSeats
            };
            return worker;
        }

        public static generatedcode.Ticket createTicketMessage(Ticket ticket)
        {
            generatedcode.Ticket worker = new generatedcode.Ticket
            {
                Id = ticket.ID,
                CustomerName = ticket.CustomerName,
                TouristName = ticket.TouristName,
                CustomerAddress = ticket.CustomerAddress,
                Seats = ticket.Seats,
                Flight = ProtoUtils.createFlightMessage(ticket.FlightForTicket)
            };
            return worker;
        }

        public static generatedcode.FlightDestDepDTO createFlightDestDTOMessage(FlightDestDepDTO flightDestDepDto)
        {
            Timestamp ts = Timestamp.FromDateTimeOffset(flightDestDepDto.Departure);
            generatedcode.FlightDestDepDTO worker = new generatedcode.FlightDestDepDTO
            {
                Destination = flightDestDepDto.Destination,
                Departure = ts
            };
            return worker;
        }

        public static generatedcode.Employee createEmployeeMessage(Employee employee)
        {
            generatedcode.Employee worker = new generatedcode.Employee
            {
                Id = employee.ID,
                Username = employee.Username,
                Password = employee.Password
            };
            return worker;
        }

        public static generatedcode.TravelMainRequest createLoginRequest(Employee employee)
        {
            generatedcode.TravelMainRequest request = new generatedcode.TravelMainRequest
            {
                Type = TravelMainRequest.Types.Type.Login,
                User = ProtoUtils.createEmployeeMessage(employee)
            };
            return request;
        }
        
        public static generatedcode.TravelMainRequest createLogoutRequest(Employee employee)
        {
            generatedcode.TravelMainRequest request = new generatedcode.TravelMainRequest
            {
                Type = TravelMainRequest.Types.Type.Logout,
                User = ProtoUtils.createEmployeeMessage(employee)
            };
            return request;
        }
        
        public static generatedcode.TravelMainRequest createFilterFlightsByDestinationAndDepartureRequest(FlightDestDepDTO flightDestDepDto)
        {
            generatedcode.TravelMainRequest request = new generatedcode.TravelMainRequest
            {
                Type = TravelMainRequest.Types.Type.FlightsByDestinationAndDeparture,
                FlightDestDepDTO = ProtoUtils.createFlightDestDTOMessage(flightDestDepDto)
            };
            return request;
        }
        
        public static generatedcode.TravelMainRequest createFilterFlightByAvailableSeatsRequest()
        {
            generatedcode.TravelMainRequest request = new generatedcode.TravelMainRequest
            {
                Type = TravelMainRequest.Types.Type.FlightByAvailableSeats,
            };
            return request;
        }
        
        public static generatedcode.TravelMainRequest createAddTicketRequest(Ticket ticket)
        {
            generatedcode.TravelMainRequest request = new generatedcode.TravelMainRequest
            {
                Type = TravelMainRequest.Types.Type.BuyTicketTravel,
                Ticket = ProtoUtils.createTicketMessage(ticket)
            };
            return request;
        }
        
        public static generatedcode.TravelMainRequest createAddFlightRequest(Flight flight)
        {
            generatedcode.TravelMainRequest request = new generatedcode.TravelMainRequest
            {
                Type = TravelMainRequest.Types.Type.BuyTicketTravel,
                Flight = ProtoUtils.createFlightMessage(flight)
            };
            return request;
        }
        
        public static String getTypeResponse(TravelMainResponse response)
        {
            String responseType = response.Type.ToString();
            return responseType;
        }
        
        public static String getTypeRequest(TravelMainRequest request)
        {
            String requestType = request.Type.ToString();
            return requestType;
        }

        public static Flight getFlightResponse(TravelMainResponse response)
        {
            return ProtoUtils.getFlight(response.Flight);
        }

        public static Ticket getTicketResponse(TravelMainResponse response)
        {
            return ProtoUtils.getTicket(response.Ticket);
        }

        public static Employee getEmployeeRequest(TravelMainRequest request)
        {
            return ProtoUtils.getUser(request.User);
        }
        
        public static Flight getFlightRequest(TravelMainRequest request)
        {
            return ProtoUtils.getFlight(request.Flight);
        }

        public static Ticket getTicketRequest(TravelMainRequest request)
        {
            return ProtoUtils.getTicket(request.Ticket);
        }
        
        public static FlightDestDepDTO getFlightDestDepRequest(TravelMainRequest request){
            return ProtoUtils.getFlightDestDTO(request.FlightDestDepDTO);
        }
        
        public static Collection<Flight> getFlightsCollectionResponse(TravelMainResponse response){
            Collection<Flight> flights = new Collection<Flight>();
            for(int i=0;i<response.Flights.Count;i++){
                generatedcode.Flight flight = response.Flights[i];
                Flight flight1 = ProtoUtils.getFlight(flight);
                flights.Add(flight1);
            }
            return flights;
        }

        public static String getErrorResponse(TravelMainResponse response)
        {
            return response.Error;
        }
        
        public static generatedcode.TravelMainResponse createAvailableSeatsResponse(IEnumerable<Flight> flights){
            TravelMainResponse response = new generatedcode.TravelMainResponse
            {
                Type = TravelMainResponse.Types.Type.AvailableSeats
            };
            foreach (var flight in flights)
            {
                response.Flights.Add(ProtoUtils.createFlightMessage(flight));
            }
            return response;
        }

        public static generatedcode.TravelMainResponse createDestinationDepartureResponse(IEnumerable<Flight> flights)
        {
            TravelMainResponse response = new generatedcode.TravelMainResponse
            {
                Type = TravelMainResponse.Types.Type.FlightDestinationDeparture
            };
            foreach (var flight in flights)
            {
                response.Flights.Add(ProtoUtils.createFlightMessage(flight));
            }
            return response;
        }
        
        public static generatedcode.TravelMainResponse createTicketResponse(Ticket ticket)
        {
            generatedcode.TravelMainResponse response = new generatedcode.TravelMainResponse
            {
                Type = TravelMainResponse.Types.Type.AddTicket,
                Ticket = ProtoUtils.createTicketMessage(ticket)
            };
            return response;
        }

        
  /*      public static Employee getUser(generatedcode.Employee employee)
        {
            Employee client = new Employee(employee.Id, employee.Username, employee.Password);
            return client;
        }

        public static FlightDestDepDTO getFlightDestDepDTO(generatedcode.FlightDestDepDTO flightDestDepDto)
        {
            DateTime dt = ProtoUtils.convertTimeStampToDateTime(flightDestDepDto.Departure);
            FlightDestDepDTO client = new FlightDestDepDTO(flightDestDepDto.Destination, dt);
            return client;
        }

        public static Flight getFlight(generatedcode.Flight flight)
        {
            DateTime dt = ProtoUtils.convertTimeStampToDateTime(flight.Departure);
            Flight client = new Flight(flight.Id, flight.Destination, dt, flight.Airport, flight.AvailableSeats);
            return client;
        }

        public static Ticket getTicket(generatedcode.Ticket ticket)
        {
            Ticket clientTicket = new Ticket(ticket.Id, ticket.CustomerName, ticket.TouristName,
                ticket.CustomerAddress, ticket.Seats, ProtoUtils.getFlight(ticket.Flight));
            return clientTicket;
        }

        public static String getErrorResponse(TravelResponse response)
        {
            String error = response.Error;
            return error;
        }

        public static String getTypeResponse(TravelResponse response)
        {
            String responseType = response.Type.ToString();
            return responseType;
        }

        public static TravelRequest createEmptyRequest()
        {
            return new TravelRequest();
        }

        

        private static DateTime convertTimeStampToDateTime(Timestamp ts)
        {
            return ts.ToDateTime();
        }
        
        
        */
    }
}