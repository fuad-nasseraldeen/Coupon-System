package com.devtech.jdbc.connection.pooling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;


/**
 * Singleton instance to store connection pool so that it can be reused
 * in between threads.
 */
public class ConnectionPool{
    
	private ArrayList<Connection>availableConnections = new ArrayList<Connection>();
	private Set<Connection>usedConnections = new HashSet<Connection>();
	private final int MAX_CONNECTIONS = 20;
	private static ConnectionPool instance = null; 
	private  String dbUrl ;
	private  String JDBC_DRIVER ;
    private  String userName ;
    private  String password ;
	
	
	/** Initialize all MAX_CONNECTIONS Connections and put them in the Pool **/
	private ConnectionPool() throws SQLException {
		
		dbUrl = "jdbc:mysql://localhost:3306/devtech_project?useSSL=false";
		JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	    userName = "root";
	    password = "root";
	
		for (int count = 0; count <MAX_CONNECTIONS; count++) {
			availableConnections.add(this.createConnection());
		}
}
	
    public synchronized static ConnectionPool getInstance()  { 
        if (instance == null) { 
        	try {
				instance = new ConnectionPool();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        } 
        return instance; 
    } 
    
	/** Private function, 
	used by the Pool to create new connection internally 
	 * @throws SQLException **/
	private Connection createConnection() throws SQLException {
		// TODO Auto-generated method stub
		try {
			Class.forName(JDBC_DRIVER);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(dbUrl, userName, password);
	}
	
	/** get connection from Pool **/
	public Connection getConnection() {
		if (availableConnections.size() == 0) {
			System.out.println("All connections are Used !!");
			return null;
		} else {
			//System.out.println("connection to MySql DB ....");
			Connection con = availableConnections.remove(availableConnections.size() - 1);
			usedConnections.add(con);
			return con;
		}
	}
	
	/** return connection back to the Pool **/
	public boolean releaseConnection(Connection con) {
		if (null != con) {
			usedConnections.remove(con);
			availableConnections.add(con);
			return true;
		}
		return false;
	}
	/** check the Connections number in used **/
	public int getUsedConnectionCount() {
		if(usedConnections.size() == 0 ) {
			System.out.println("there is no connection in use ");
		}
		return usedConnections.size();
	}

	/** check the number of Available Connections **/
	public int getFreeConnectionCount() {
		if(availableConnections.size() == 0 ) {
			System.out.println("there is no connection available right know ");
		}
		return availableConnections.size();
	}
	
	public void closeAllConnections()  {
		
		
		for(Connection connection : availableConnections) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("all the connection are closed");
	}
	

}



	