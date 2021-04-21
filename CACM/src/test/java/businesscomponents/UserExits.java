package businesscomponents;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.frameworkutils.Status;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
//This class contains all user exit methods
public class UserExits extends ReusableLibrary {

	public UserExits(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	IDD_Customer_Search obj1= new IDD_Customer_Search (scriptHelper);
	//Method to loig in in CACM
	public void loginCACM() throws Exception 
	{
		
		obj1.loginCACM();
		
	}
	//Clicks on OpenMenu from Menubar
	public void openMenu() throws Exception
	{
		obj1.openMenu();
	}
	//Open saveQueries 
	public void savedQueries() throws Exception
	{
		obj1.savedQueries();
		
	}
	//Method to search customer by Auto and Manual Mode
	public void searchCustomer() throws Exception 
	{
	String Mode = dataTable.getData("General_Data", "Execution_Mode");
	System.out.println("The Execution Mode is: " +Mode);
	if (Mode.equalsIgnoreCase("Manual"))
	{	
	obj1.searchCustomer();
	}
	else
	{	
	  String CA_ID=TestDataforAcct_userExit();
	  try
	   {

	  driver.findElement(By.xpath(obj1.customerqueryXpath("CA ID"))).sendKeys(CA_ID);
	  System.out.println("CA_ID:"+CA_ID);
	  report.updateTestLog("CACM IDD", "Customer Search complete", Status.PASS);
	   }
	   catch(Exception e){
		  e.printStackTrace(); 
		 report.updateTestLog("CACM IDD", "Customer Search Not complete", Status.FAIL);
	   }
	}
	}
	//Method that clicks iddRunsearch
	public void iddUIRunSearch() throws Exception 
	{
		obj1.iddUIRunSearch();
	}
	//Meyhod to click open ecord
	public void openRecordFromSearch() throws Exception
	{
		
		obj1.openRecordFromSearch();
		
	}
	//The below method adds classification on top of existing classification
	public void addClassification() throws Exception
	{
	
		obj1. xpathSubjArea("Client Classification").click();
		Thread.sleep(5000);
		obj1.switchtoTableView("Client Classification");
		Thread.sleep(5000);
	    System.out.println(" Inside in Subject Area : "+obj1.xpathSubjArea("Client Classification").getText());
	    //clicking plus sign
	    driver.findElement(By.xpath("(//span[text()='Client Classification']//following::table//input)[3]")).click();
	    Thread.sleep(5000);
	    String classificationType = dataTable.getData("General_Data", "Classification_Type");
	    driver.findElement(By.xpath("//*[text()='CUSTOMER CLASSIFICATION TYPE']//following-sibling::td//tr/td/div/div/div[2]/input[2]")).sendKeys(classificationType);
	    Thread.sleep(5000);
	    
		
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
		public void saveitem() throws Exception
		{
			WebElement save=driver.findElement(By.xpath("//*[text()='Save']"));
			String color =save.getCssValue("color");
			if (color.equalsIgnoreCase( "rgba(255, 255, 255, 1)"))
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
		
		//This Keyword compares UI error with datasheet error
				public void verifyError() throws Exception
				{
					String Error_UI=driver.findElement(By.xpath("//*[@class='modalNotificationHeader modalHeaderOf_ERROR']")).getText();
					String Erorr_Standard =dataTable.getData("General_Data", "Standard_Error");
					if (Error_UI.equals( Erorr_Standard))
					{
						
						System.out.println("The Error message :"+Error_UI);
						report.updateTestLog("IDD", "Error message :"+Error_UI, Status.PASS);
						
					}
					else{
						
						System.out.println("Save Button not Clicked");
						report.updateTestLog("IDD", "Issue in testdata", Status.FAIL);
					}
				}
				//Method to logout from 
				public void logoutCACMIDD() throws Exception {
					obj1.logoutCACMIDD();
					
				}
	
				//This method executes account query and Identifies test data - the account Id 
		    	 public String  TestDataforAcct_userExit()
		    	 
		    	 
		    	 {
		    		 
		    		 String	 CA_ID="";
		    		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
		    		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
		    		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
		    		 
		    		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		    		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("userexit");
		    		 
		    		 System.out.println("Query from parameter file is: "+sqlPassed);
		    		 
		    		 
		    		 try {
		    			 
		     			
		 				ResultSet dataSet=stmt.executeQuery(sqlPassed);
		 				 System.out.println("Waiting for executing of the query");
		 				Thread.sleep(20000);
		 				while (dataSet.next()) { 
		 				CA_ID=dataSet.getString("CA_ID");
		 				System.out.println("ID is: "+CA_ID);
		     			}
		 			} catch (SQLException | InterruptedException e) {
		 				
		 			
		 				System.out.println("Test Data for account not found");
		                report.updateTestLog("CACM IDD", "Test Data for userexit not found", Status.FAIL);
		            	e.printStackTrace();
		 			} 
		    		 
		    		return CA_ID;
		    	 }
		    	 
		    	 
}
