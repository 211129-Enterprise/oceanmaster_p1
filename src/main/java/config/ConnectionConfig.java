package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionConfig {
	//private static Logger logger = Logger.getLogger(ConnectionConfig.class);
private static Connection conn = null;
	
	private ConnectionConfig() {
		super();
	}
public static Connection getConnection() {
		
		try {
			if (conn != null && !conn.isClosed()) {
				//logger.info("returned re-used connection object");
				return conn;
			}
		} catch (SQLException e) {
			//logger.error("we failed to re-use a connection");
			e.printStackTrace();
			return null;
		}
		
		Properties prop = new Properties(); 
		
		String url = "";
		String username = "";
		String password = "";
		
		try {
			prop.load(new FileReader(PropertyFile.getFilePath()));
			
			url = prop.getProperty("url"); 
			System.out.println(url);
			username =  prop.getProperty("username");
			System.out.println(username);
			password = prop.getProperty("password");
			System.out.println(password);
			conn = DriverManager.getConnection(url, username, password);
			//logger.info("Database Connection Established");
			
		} catch (SQLException e) {
			//logger.error("SQL Exception thrown - Cannot establish DB connection");

		} catch (FileNotFoundException e) {
			//logger.error("Cannot locate application.properties file");
			e.printStackTrace();
		} catch (IOException e) {
			//logger.error("Something wrong with app.props file");
			e.printStackTrace();
		}
		
		return conn; 
	}
}
