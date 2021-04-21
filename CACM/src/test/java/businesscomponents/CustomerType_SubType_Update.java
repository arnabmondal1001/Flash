package businesscomponents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.util.frameworkutils.Status;

import objectrepositories.CustomerType_Subtype;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class CustomerType_SubType_Update extends ReusableLibrary{

	public CustomerType_SubType_Update(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
		
				
		
	}
	//Below Method is combined activity starting from login,Clicking Menu,Save Queries,Search a customer by CA id , Clicking IDD Run Search,
		//Open record from search
	public void loginToRecordOpen() throws Exception
	{
		try{
		IDD_Customer_Search obj1= new IDD_Customer_Search (scriptHelper);
		obj1.loginCACM();
		obj1.openMenu();
		obj1.savedQueries();
		obj1.searchCustomer();
		obj1.iddUIRunSearch();
		obj1.openRecordFromSearch();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//Below MEthod is for customer type update
	public void custTypeUpdate() throws Exception
	
	{
		try{
			 String CUST_TYPE = dataTable.getData("Update", "CUST_TYPE"); 
			driver.findElement(By.xpath(CustomerType_Subtype.Edit_Icon)).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[text()='CUSTOMER TYPE']//following-sibling::td//input[2]")).clear();
			driver.findElement(By.xpath("//*[text()='CUSTOMER TYPE']//following-sibling::td//input[2]")).sendKeys(CUST_TYPE);
			Thread.sleep(10000);
			System.out.println("Selected Customer type");
			 report.updateTestLog("IDD", "Customer type selected", Status.PASS);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			 report.updateTestLog("IDD", "Customer type not selected", Status.FAIL);
		}
		
		
	}
	
	//Apply the changes 
	public void apply() throws Exception
	{
		try{
			driver.findElement(By.xpath("//*[text()='Apply']")).click();
			Thread.sleep(5000);
			System.out.println("Apply Button Clicked");
			 report.updateTestLog("IDD", "Apply Button Clicked", Status.PASS);
		}
		catch(Exception e){
		e.printStackTrace();
		 report.updateTestLog("IDD", "Apply BUtton Not Clicked", Status.FAIL);
		}
	}
	//This Keyword Save the changes when apply button is hit.
	public void saveitem(String item) throws Exception
	{
		WebElement save=driver.findElement(By.xpath("//*[text()='"+item+"']"));
		String color =save.getCssValue("color");
		if (color.equalsIgnoreCase( "rgba(255, 117, 25, 1)"))
		{
			driver.findElement(By.xpath("//*[text()='Save']")).click();
			Thread.sleep(5000);
			System.out.println("Save Button Clicked");
			report.updateTestLog("IDD", "Save Button Clicked", Status.PASS);
			
		}
		else{
			
			System.out.println("Save Button not Clicked");
			report.updateTestLog("IDD", "Issue in testdata", Status.FAIL);
		}
	}
	//Save customer type after making changes in UI
	public void save_type() throws Exception
	{
		saveitem("CUSTOMER TYPE");
		
	}
	
	//Below MEthod is for customer type update
		public void custSubTypeUpdate() throws Exception
		
		{
			try{
				 String CUST_SUBTYPE = dataTable.getData("Update", "CUST_SUBTYPE"); 
				driver.findElement(By.xpath("//*[text()='CUSTOMER SUBTYPE']")).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath(CustomerType_Subtype.Edit_Icon)).click();
				
				//driver.findElement(By.xpath("//*[text()='CUSTOMER SUBTYPE']//following-sibling::td//input[2]")).clear();
				driver.findElement(By.xpath("//*[text()='CUSTOMER SUBTYPE']//following-sibling::td//input[2]")).sendKeys(CUST_SUBTYPE);
				Thread.sleep(3000);
				System.out.println("Selected Customer subtype");
				report.updateTestLog("IDD", "Customer type selected", Status.PASS);
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
				 report.updateTestLog("IDD", "Customer type not selected", Status.FAIL);
			}
			
			
		}
		//Method to update customer type 
		public void save_Subtype() throws Exception
		{
			saveitem("CUSTOMER SUBTYPE");
			
		}
		//This keyword will verify updated UI data from database and generates pass fail report based on matching
		public void verifyTypeSubtype() throws Exception
	 	 
	 	 
	 	 {
		    
		     //CUST_TYPE_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER TYPE"))));
		     Thread.sleep(3000);
		     String CUST_TYPE_UI=uiIDDval("CUSTOMER TYPE");
		     //CUST_SUBTYPE_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER SUBTYPE"))));
		     Thread.sleep(3000);
		     String CUSTOMER_SUBTYPE_UI=uiIDDval("CUSTOMER SUBTYPE");
			 
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_TYPE_SUBTYPE"); 
	 		
	 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		 String CUST_TYPE_DB ="";
	 		String CUSTOMER_SUBTYPE_DB ="";
	 		 
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					
					 
					CUST_TYPE_DB=dataSet.getString("CUST_TYPE");
					System.out.println("CUST_TYPE is: "+CUST_TYPE_DB);
					CUSTOMER_SUBTYPE_DB=dataSet.getString("CUST_SUBTYPE");
					System.out.println("CUST_SUBTYPE is: "+CUST_TYPE_DB);


	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	 		 //Comparing CA_ID with DB and UI
	 		 	reportStatusGeneration("CUST_TYPE_UI",CUST_TYPE_UI,CUST_TYPE_DB); 

	 		 	reportStatusGeneration("CUSTOMER_SUBTYPE_UI",CUSTOMER_SUBTYPE_UI,CUSTOMER_SUBTYPE_DB);

	 	 } 
	  //utility method to get path
		 public String uiIDDval(String uiVal )
		 {
			 
			 String UiVal=driver.findElement(By.xpath("//*[text()='"+uiVal+"']"+"//following-sibling::td[1]")).getText() ;
			 return UiVal ;
		 }
		//utility method to get path
		 public String uiIDxPath(String uiVal )
		 {
			 
			 String UiVal="//*[text()='"+uiVal+"']"+"//following-sibling::td[1]";
			 return UiVal ;
		 }
		 //Common method for report description and status
		  public void reportStatusGeneration(String attrName,String uiAttr, String dbAttr)
		    {
		       if(dbAttr==null)
		              {
		              dbAttr="";
		              }
		       
		       if (uiAttr.equalsIgnoreCase(dbAttr))
		        {
		              
		 
		              report.updateTestLog("CACM IDD", "DB Validation for " +attrName+ " UI value is :"+ uiAttr +" = "+" DB value is : "+dbAttr, Status.PASS);
		        }
		        
		        else 
		        {
		              report.updateTestLog("CACM IDD", "DB Validation for  " +attrName+ "  UI value is :"+ uiAttr +" = "+" DB value is : "+dbAttr, Status.FAIL);
		              
		        }
		    }
		  
		  //This keyword takes various information from database like updated by, CA_ID,DL_REASON etc. and then prints on the report
		  
		  public void verifyUserInfo() throws Exception
		 	 
		 	 
		 	 {
			    
			  			 
		 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
		 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
		 		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
		 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
		 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 		 System.out.println("Env input from xlsx "+stmt);
		 		
		 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_INFO"); 
		 		
		 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
		 		 System.out.println("Validation SQL query :"+sqlPassed);
		 		 //Initializing DB values
		 		String CA_ID_DB ="";
		 		String UPDATED_BY_DB ="";
		 		String DL_REASON_DB ="";
		 		String HUB_STATE_IND ="";
		 		String SOURCE_DELETE_FLAG =""; 
		 		 
		 		 try {
		 			 
		 			 Thread.sleep(10000);
						ResultSet dataSet=stmt.executeQuery(sqlPassed);
						Thread.sleep(10000);
						while (dataSet.next()) { 
						
						 
						CA_ID_DB=dataSet.getString("CA_ID");
						System.out.println("CUST_TYPE is: "+CA_ID_DB);
						UPDATED_BY_DB=dataSet.getString("UPDATED_BY");
						System.out.println("UPDATED_BY is: "+UPDATED_BY_DB);
						DL_REASON_DB=dataSet.getString("DL_REASON");
						System.out.println("DL_REASON is: "+DL_REASON_DB);
						HUB_STATE_IND=dataSet.getString("HUB_STATE_IND");
						System.out.println("HUB_STATE_IND is: "+HUB_STATE_IND);
						SOURCE_DELETE_FLAG=dataSet.getString("SOURCE_DELETE_FLAG");
						System.out.println("SOURCE_DELETE_FLAG is: "+SOURCE_DELETE_FLAG);


		 			}
					} catch (SQLException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		 		 //Reporting User info
		 		report.updateTestLog("IDD", "CA_ID: "+CA_ID_DB, Status.DONE);; 
		 		report.updateTestLog("IDD", "LUPDATED_BY: "+UPDATED_BY_DB, Status.DONE);
		 		report.updateTestLog("IDD", "DL_REASON: "+DL_REASON_DB, Status.DONE);
		 		report.updateTestLog("IDD", "HUB_STATE_IND: "+HUB_STATE_IND, Status.DONE);
		 		report.updateTestLog("IDD", "SOURCE_DELETE_FLAG: "+SOURCE_DELETE_FLAG, Status.DONE);
		 		
		 	 } 
		  //Method to logout
		 public void logout() throws Exception{
			 
			 IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper);
			 obj1.logoutCACMIDD();
		 }
}


