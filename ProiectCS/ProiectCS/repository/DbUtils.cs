using System.Collections.Generic;
using System.Data;
using Npgsql;


namespace NET.repository
{
    public static class DbUtils
    {
        private static IDbConnection instance = null;

        public static IDbConnection getConnection(IDictionary<string, string> props)
        {
            if(instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = getNewConnection(props);
                instance.Open();
            }
            return instance;
        }

        private static IDbConnection getNewConnection(IDictionary<string, string> props)
        {
            string connectionString = props["ConnectionString"];
            return new NpgsqlConnection(connectionString);
        }
    
    }
}