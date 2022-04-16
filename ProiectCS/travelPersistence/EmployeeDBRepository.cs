using System.Collections.Generic;
using log4net;
using NET.repository;
using Npgsql;
using travelModel;

namespace travelPersistence
{
    public class EmployeeDBRepository : EmployeeRepository
    {
        private static readonly ILog log = LogManager.GetLogger("EmployeeDBRepository");
        private IDictionary<string, string> props;

        public EmployeeDBRepository(IDictionary<string, string> props)
        {
            log.Info("Creating EmployeeDBRepository");
            this.props = props;
        }

        public void Save(Employee elem)
        {
            log.InfoFormat("saving employee {0}", elem);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var saveStmt = new NpgsqlCommand("insert into Employee(username,password) values (@username,@password)", connection))
                {
                    saveStmt.Parameters.AddWithValue("@username", elem.Username);
                    saveStmt.Parameters.AddWithValue("@password", elem.Password);
                    var result = saveStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No employee saved!");
                    }
                }
            }
            log.InfoFormat("employee has been saved {0}", elem);
        }

        public void Delete(Employee elem)
        {
            throw new System.NotImplementedException();
        }

        public void Update(Employee elem, int ID)
        {
            log.InfoFormat("updating employee {0} with id {1}", elem, ID);
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var updateStmt = new NpgsqlCommand("update Employee set username = @username, password = @password" +
                                                          "where id = @id", connection))
                {
                    updateStmt.Parameters.AddWithValue("@username", elem.Username);
                    updateStmt.Parameters.AddWithValue("@password", elem.Password);
                    updateStmt.Parameters.AddWithValue("@id", ID);
                    var result = updateStmt.ExecuteNonQuery();
                    if (result == 0)
                    {
                        log.Error("No employee updated!");
                        //throw new Exception("No employee updated!");
                    }
                }
            }
            log.InfoFormat("employee has been updating {0} with id {1}", elem, ID);
        }

        public Employee FindById(int ID)
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<Employee> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public bool filterByUsernameAndPassword(string username, string password)
        {
            log.Info("filter by username and password");
            using (var connection = new NpgsqlConnection(props["ConnectionString"]))
            {
                connection.Open();
                using (var filterStmt = new NpgsqlCommand(
                           "select * from Employee where username = @us and password = @ps", connection))
                {
                    filterStmt.Parameters.AddWithValue("@us", username);
                    filterStmt.Parameters.AddWithValue("@ps", password);
                    using (var dataR = filterStmt.ExecuteReader())
                    {
                        log.Info("Exit");
                        return dataR.Read();
                    }
                }
            }
        }
    }
}