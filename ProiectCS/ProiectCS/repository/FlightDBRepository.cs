using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.WebControls;
using log4net;
using NET.model;
using Npgsql;


namespace NET.repository
{
    public class FlightDBRepository : FlightRepository
    {
        private static readonly ILog log = LogManager.GetLogger("FlightDBRepository");
        private IDictionary<string, string> props;

        public FlightDBRepository(IDictionary<string, string> props)
        {
            log.Info("Creating FlightDBRepository");
            this.props = props;
        }
        
        public void Save(Flight elem)
        {
            log.InfoFormat("saving flight {0}", elem);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var insertStmt = new NpgsqlCommand("insert into Flights(destination, departure, airport, availableSeats) values " +
                                                          "(@destination, @departure, @airport, @availableSeats)", connection))
                {
                    insertStmt.Parameters.AddWithValue("@destination", elem.Destination);
                    insertStmt.Parameters.AddWithValue("@departure", elem.Departure);
                    insertStmt.Parameters.AddWithValue("@airport", elem.Airport);
                    insertStmt.Parameters.AddWithValue("@availableSeats", elem.AvailableSeats);
                    var result = insertStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No flight was added!");
                        //throw new Exception("No Flight added !");
                    }
                }
            }
            log.InfoFormat("flight has been saved {0}", elem);
        }

        public void Delete(Flight elem)
        {
            log.InfoFormat("deleting flight {0}", elem);
            using (var connection =  new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var deleteStmt = new NpgsqlCommand("delete from Flights where id = @id, connection", connection))
                {
                    deleteStmt.Parameters.AddWithValue("@id", elem.ID);
                    var result = deleteStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No flight was deleted!");
                        //throw new Exception("No Flight deleted");
                    }
                }
            }
            log.InfoFormat("flight has been deleted {0}", elem);
        }

        public void Update(Flight elem, int ID)
        {
            log.InfoFormat("updating flight {0} with ID {1}", elem, ID);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var updateStmt = new NpgsqlCommand("update Flights set destination = @destination,departure=@departure," +
                                                          "airport=@airport,availableSeats=@availableSeats where id = @id", connection))
                {
                    updateStmt.Parameters.AddWithValue("@destination", elem.Destination);
                    updateStmt.Parameters.AddWithValue("@departure", elem.Departure);
                    updateStmt.Parameters.AddWithValue("@airport", elem.Airport);
                    updateStmt.Parameters.AddWithValue("@availableSeats", elem.AvailableSeats);
                    updateStmt.Parameters.AddWithValue("@id", ID);
                    var result = updateStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No flight updated!");
                        //throw new Exception("No update flight");
                    }
                }
            }
            log.InfoFormat("flight has been updating {0} with ID {1}", elem, ID);
        }

        public Flight FindById(int ID)
        {
            log.InfoFormat("finding flight with id {0}", ID);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var findStmt = new NpgsqlCommand("select * from Flights where id = @id",connection))
                {
                    findStmt.Parameters.AddWithValue("@id", ID);
                    using (var dataR = findStmt.ExecuteReader())
                    {
                        if (dataR.Read())
                        {
                            string destination = dataR.GetString(1);
                            DateTime departure = dataR.GetDateTime(2);
                            string airport = dataR.GetString(3);
                            int availableSeats = dataR.GetInt32(4);
                            Flight flight = new Flight(ID, destination, departure, airport, availableSeats);
                            log.InfoFormat("finding flight {0} with id {1}", flight, ID);
                            return flight;
                        }
                    }
                }
            }
            log.InfoFormat("no flight has been found with id {0}", ID);
            return null;
        }

        public IEnumerable<Flight> FindAll()
        {
            log.Info("finding all flights");
            IList<Flight> flights = new List<Flight>();
            using (var connection =  new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var findAllStmt = new NpgsqlCommand("select * from Flights",connection))
                {
                    using (var dataR = findAllStmt.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int id = dataR.GetInt32(0);
                            string destination = dataR.GetString(1);
                            DateTime departure = dataR.GetDateTime(2);
                            string airport = dataR.GetString(3);
                            int availableSeats = dataR.GetInt32(4);
                            Flight flight = new Flight(id, destination, departure, airport, availableSeats);
                            flights.Add(flight);
                        }
                    }
                }
            }
            log.Info("return all found flights");
            return flights;
        }

        public IEnumerable<Flight> filterFlightByDestinationAndDeparture(string destination, DateTime departure)
        {
            log.Info("findall filtered flights by destination and departure");
            IList<Flight> filteredFlights = new List<Flight>();
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var filterStmt = new NpgsqlCommand(
                           "select * from Flights where availableSeats > 0 and destination = @dt and " +
                           "(SELECT EXTRACT(YEAR FROM departure))= @year and" +
                           "(SELECT EXTRACT(MONTH FROM departure))= @month and" +
                           "(SELECT EXTRACT(DAY FROM departure))= @day ", connection))
                {
                    filterStmt.Parameters.AddWithValue("@dt", destination);
                    filterStmt.Parameters.AddWithValue("@year", departure.Year);
                    filterStmt.Parameters.AddWithValue("@month", departure.Month);
                    filterStmt.Parameters.AddWithValue("@day", departure.Day);
                    using (var dataR = filterStmt.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int id = dataR.GetInt32(0);
                            string destinationR = dataR.GetString(1);
                            DateTime departureR = dataR.GetDateTime(2);
                            string airport = dataR.GetString(3);
                            int availableSeats = dataR.GetInt32(4);
                            Flight flight = new Flight(id, destinationR, departureR, airport, availableSeats);
                            filteredFlights.Add(flight);
                        }
                    }
                 
                }
            }
            log.Info("getting all filtered flights by destination and departure");
            return filteredFlights;
        }

        public IEnumerable<Flight> filterFlightByAvailableSeats()
        {
            log.Info("find all flights with available seats");
            IList<Flight> filteredFlights = new List<Flight>();
            using (var connecton = new NpgsqlConnection(props["ConnectionString"]))
            {
                connecton.Open();
                using (var filteredStmt = new NpgsqlCommand("select * from Flights where availableSeats > 0", connecton))
                {
                    using (var dataR = filteredStmt.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int id = dataR.GetInt32(0);
                            string destination = dataR.GetString(1);
                            DateTime departure = dataR.GetDateTime(2);
                            string airport = dataR.GetString(3);
                            int availableSeats = dataR.GetInt32(4);
                            Flight flight = new Flight(id, destination, departure, airport, availableSeats);
                            filteredFlights.Add(flight);
                        }
                    }
                }
            }
            log.Info("getting all filtered flights by available Seats");
            return filteredFlights;
        }
    }
}