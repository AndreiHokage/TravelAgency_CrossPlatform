using System;
using System.Collections.Generic;
using log4net;
using NET.model;
using Npgsql;


namespace NET.repository
{
    public class TicketDBRepository : TicketRepository
    {
        private static readonly ILog log = LogManager.GetLogger("TicketDBRepository");
        
        private IDictionary<string, string> props;

        public TicketDBRepository(IDictionary<string, string> props)
        {
            log.Info("Creating TiketDBRepository");
            this.props = props;
        }

        public void Save(Ticket elem)
        {
            log.InfoFormat("saving ticket {0}", elem);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var saveStmt = new NpgsqlCommand("insert into Tickets(customerName,touristName,customerAddress,seats,FlightID)" +
                                                        "values (@cN,@tN,@cA,@seats,@FlightID)", connection))
                {
                    saveStmt.Parameters.AddWithValue("@cN", elem.CustomerName);
                    saveStmt.Parameters.AddWithValue("@tN", elem.TouristName);
                    saveStmt.Parameters.AddWithValue("@cA", elem.CustomerAddress);
                    saveStmt.Parameters.AddWithValue("@seats", elem.Seats);
                    saveStmt.Parameters.AddWithValue("@FlightID", elem.FlightForTicket.ID);
                    var result = saveStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No ticket added !");
                        //throw new Exception("No ticket added !");
                    }
                }
            }
            log.InfoFormat("ticket has been added {0}", elem);
        }

        public void Delete(Ticket elem)
        {
            log.InfoFormat("deleting ticket {0}", elem);
            using (var connection =  new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var deleteStmt = new NpgsqlCommand("delete from Tickets where id = @id", connection))
                {
                    deleteStmt.Parameters.AddWithValue("@id", elem.ID);
                    var result = deleteStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No ticket deleted");
                        //throw new Exception("No ticket deleted !");
                    }
                }
            }
            log.InfoFormat("ticket has been deleted {0}", elem);
        }

        public void Update(Ticket elem, int ID)
        {
            log.InfoFormat("updating ticket {0} with ID {1}", elem, ID);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var updateStmt = new NpgsqlCommand("update Tickets set customerName = @cN , touristName = @tN ," +
                                                          "customerAddress = @cA,seats = @seats,FlightID = @FlightID) where id = @id", connection))
                {

                    updateStmt.Parameters.AddWithValue("@cN", elem.CustomerName);
                    updateStmt.Parameters.AddWithValue("@tN", elem.TouristName);
                    updateStmt.Parameters.AddWithValue("@cA", elem.CustomerAddress);
                    updateStmt.Parameters.AddWithValue("@seats", elem.Seats);
                    updateStmt.Parameters.AddWithValue("@FlightID", elem.FlightForTicket.ID);
                    updateStmt.Parameters.AddWithValue("@id", ID);
                    var result = updateStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No ticket updated!");
                        //throw new Exception("No ticket updated!");
                    }
                }
            }
            log.InfoFormat("ticket has been updated {0} with ID {1}", elem, ID);
        }

        public Ticket FindById(int ID)
        {
            log.InfoFormat("finding ticket with ID {0}", ID);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var findStmt = new NpgsqlCommand("select * from Tickets  T inner join Flights  F on T.Flightid = F.id where T.id = @id", connection))
                {
                    findStmt.Parameters.AddWithValue("@id", ID);
                    using (var dataR = findStmt.ExecuteReader())
                    {
                        if (dataR.Read())
                        {
                            string customerName = dataR.GetString(1);
                            string touristName = dataR.GetString(2);
                            string customerAddress = dataR.GetString(3);
                            int seats = dataR.GetInt32(4);
                            int idTicket = dataR.GetInt32(6);
                            string destination = dataR.GetString(7);
                            DateTime departure = dataR.GetDateTime(8);
                            string airport = dataR.GetString(9);
                            int availableSeats = dataR.GetInt32(10);

                            Flight flight = new Flight(idTicket, destination, departure, airport, availableSeats);
                            Ticket ticket = new Ticket(ID, customerName, touristName, customerAddress, seats, flight);
                            log.InfoFormat("ticket with id {0} has been found {1}", ID, ticket);
                            return ticket;
                        }
                    }
                }
                log.InfoFormat("no ticket with id {0}", ID);
            }

            return null;
        }

        public IEnumerable<Ticket> FindAll()
        {
            log.Info("finding all tickets");
            IList<Ticket> tickets = new List<Ticket>();
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var findStmt = new NpgsqlCommand("select * from Tickets AS T inner join Flights AS F on T.FlightID = F.id", connection))
                {
                    using (var dataR = findStmt.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int ID = dataR.GetInt32(0);
                            string customerName = dataR.GetString(1);
                            string touristName = dataR.GetString(2);
                            string customerAddress = dataR.GetString(3);
                            int seats = dataR.GetInt32(4);
                            int idTicket = dataR.GetInt32(6);
                            string destination = dataR.GetString(7);
                            DateTime departure = dataR.GetDateTime(8);
                            string airport = dataR.GetString(9);
                            int availableSeats = dataR.GetInt32(10);

                            Flight flight = new Flight(idTicket, destination, departure, airport, availableSeats);
                            Ticket ticket = new Ticket(ID, customerName, touristName, customerAddress, seats, flight);
                            tickets.Add(ticket);
                        }
                    }
                    log.Info("return all found tickets");
                    return tickets;
                }
            }
        }
    }
}