package chat.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private final static Logger logger = LogManager.getLogger(JdbcUtils.class);
    private Properties properties;

    private Connection instance = null;

    public JdbcUtils(Properties properties){
        this.properties = properties;
    }

    private Connection getNewConnection(){
        logger.traceEntry();
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.pass");
        logger.info("trying to connect to database ... {}",url);
        logger.info("user: {}", username);
        logger.info("pass: {}", password);

        Connection connection = null;
        try {
            if (username != null && password != null)
                connection = DriverManager.getConnection(url, username, password);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return connection;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if(instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
