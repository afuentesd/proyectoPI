// Descomentar si se se va a usar un Listener para iniciar la conexión:
//package listener;
//Comentar si se va a usar un Listner para iniciar la conexión:
package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;



public class DBConn {
	
	private static final Logger logger = Logger.getLogger(DBConn.class.getName());
	
	
	public Connection create(){
		
		logger.info("Connecting DB");
		
		
		Connection conn = null;
		
			
		try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:file:"+System.getProperty("user.home")+"/sqlite_dbs/notes.sqlite";
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		logger.info("DB created");
		
		return conn;
	}
	
	 
	public void destroy(Connection conn){
		
		logger.info("Destroying DB");
		try {
			logger.info("DB shutdown start");
	
			conn.close();
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				logger.info("DB deregistering drivers");
				Driver driver = drivers.nextElement();
				try {
					DriverManager.deregisterDriver(driver);
					logger.info(String.format("deregistering jdbc driver: %s", driver));
				} catch (SQLException e) {
					logger.severe(String.format("Error deregistering driver %s %s", driver, e));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("DB destroyed");
	}

	
   
	
	

}
