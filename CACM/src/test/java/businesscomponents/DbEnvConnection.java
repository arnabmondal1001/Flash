package businesscomponents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


// Class uses to take DB connection details from global setting properties file and connect with database  
public class DbEnvConnection extends ReusableLibrary	 {
	
	public DbEnvConnection(ScriptHelper scriptHelper) {
		super(scriptHelper);
		
	}

	

	static  Connection conn = null;
	static 	Statement stmt = null;
	
// Method uses to connect database of QA1, QA2 and Prepord environment 	
	public  Statement dbConnectionString( String dbEnv ) {
		
	//This will return connection string for QA1	           
		
		
		 if(dbEnv.equals("QA1"))
		 {
			 String userName=properties.getProperty("usernameQA1");
			 String passwd=properties.getProperty("passwordQA1");
			 String driverName=properties.getProperty("drivername");
			 String connectionUrlQA1=properties.getProperty("connectionUrl_QA1");
			 
			
			 
			 try {
					Class.forName(driverName);
					conn=DriverManager.getConnection(connectionUrlQA1,userName,passwd);
					if(conn!=null){
						stmt = conn.createStatement();
				
					
					
					}
					} 
			 catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
			 
		 }
				
		//This will return connection string for QA2
		 if(dbEnv.equals("QA2"))
		 {
			 String userName=properties.getProperty("usernameQA2");
			 String passwd=properties.getProperty("passwordQA2");
			 String driverName=properties.getProperty("drivername");
			 String  connectionUrlQA2=properties.getProperty("connectionUrl_QA2");
			 
			 try {
					Class.forName(driverName);
					conn=DriverManager.getConnection(connectionUrlQA2,userName,passwd);
					if(conn!=null){
						stmt = conn.createStatement();
					
					
					
					}
					} 
			 catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
			 
		 }	
		 
		//This will return connection string for PREPROD
		 if(dbEnv.equals("PreProd"))
		 {
			 String userName=properties.getProperty("usernamePrepord");
			 String passwd=properties.getProperty("passwdPrepord");
			 String driverName=properties.getProperty("drivername");
			 String connectionUrl_PREPROD=properties.getProperty("connectionUrl_PREPROD");
			 
			 try {
					Class.forName(driverName);
					conn=DriverManager.getConnection(connectionUrl_PREPROD,userName,passwd);
					if(conn!=null){
						stmt = conn.createStatement();
				
					
					
					}
					} 
			 catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
			 
		 }	
		 
		 return stmt;
		
		}
	}
	





