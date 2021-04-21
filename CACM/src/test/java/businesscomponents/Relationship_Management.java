package businesscomponents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.util.frameworkutils.Status;

import objectrepositories.Relationship;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Relationship_Management extends ReusableLibrary {
	 static String CA_ID1="";
	 static String PARENT_ID="";
	 static String CHILD_ID="";
	 //test
	public Relationship_Management(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	//below method log in to CACM and CLICKS relationship management
	WebDriverWait wait = new WebDriverWait(driver, 50);
	GenericFunctions GF = new GenericFunctions(scriptHelper);
	public void loginRelationShip() throws Exception
	{
		try{
		IDD_Customer_Search obj1= new IDD_Customer_Search (scriptHelper);
		obj1.loginCACM();
		//Clicking on Relationship-Management
		menuItems("Relationship Management");
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//Common function to select Menuitems
	public void menuItems(String ops) throws Exception
	
	{
		
	try
	{
		driver.findElement(By.xpath("//*[text()='"+ops+"']")).click();
		// The script is waiting for the Relationship Management to load
		driver.switchTo().frame("rel_mgmt_import_tab");
		Thread.sleep(5000);
    	report.updateTestLog("CACM IDD", "Clicked"+ops, Status.PASS);
    	System.out.println("Clicked on :"+ops);		
	}	
	catch (Exception e)
	{
		e.printStackTrace();
		
	}	
	}
	//Below method uploads XLSX file
	public void fileupload() throws Exception
	
	{
		
	try
	{
		  String Path =System.getProperty("user.dir");
		  Path=Path+"\\src\\test\\resources\\Utilities\\OWN_NetNew_QA.xlsx";
		  WebElement elem = driver.findElement(By.xpath(Relationship.Upload_file));
		  elem.sendKeys(Path);
		  Thread.sleep(10000);
		  System.out.println("The file path is"+Path);
		  report.updateTestLog("CACM IDD", "Uploaded file", Status.PASS);
	      System.out.println("Uploaded file");

		
	}	
	catch (Exception e)
	{
		report.updateTestLog("CACM IDD", "Not Uploaded the file", Status.FAIL);
	    System.out.println("Not Uploaded file");
		e.printStackTrace();
		
	}	
	}
	//Below method uploads XLSX file
		public void fileuploadEndDate() throws Exception
		
		{
			
		try
		{

			  String Path =System.getProperty("user.dir");
			  Path=Path+"\\src\\test\\resources\\Utilities\\OWN_EndDate_QA.xlsx";
			  WebElement elem = driver.findElement(By.xpath(Relationship.Upload_file));
			  elem.sendKeys(Path);
			  Thread.sleep(10000);
			  report.updateTestLog("CACM IDD", "Uploaded file", Status.PASS);
		      System.out.println("Uploaded file");

			
		}	
		catch (Exception e)
		{
			report.updateTestLog("IDD", "Not Uploaded the file", Status.FAIL);
		    System.out.println("Not Uploaded file");
			e.printStackTrace();
			
		}	
	}
	//This Keyword validates if the records from xlsx file is properly validated or not.	
	public void validate() throws Exception
	
	{
		
	try
	{

		  driver.findElement(By.xpath(Relationship.Validate)).click();
		  Thread.sleep(5000);
		  try{
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Validated Records']")));
		  PARENT_ID=driver.findElement(By.xpath("//*[text()='CA ID (Parent)']//..//..//following-sibling::tbody/tr/td[1]")).getText();
		  CHILD_ID=driver.findElement(By.xpath("//*[text()='CA ID (Parent)']//..//..//following-sibling::tbody/tr/td[2]")).getText();
		  
		  }
			catch (Exception e)
			{
				System.out.println(" Validated Records Not present");
				
			}
		    if(GF.isElementPresent(By.xpath("//*[text()='Validated Records']")))
		    {
		    	report.updateTestLog("CACM IDD", "Records Validated", Status.PASS);
		    	System.out.println("Records Validated");
		    }
		    else{
		    	
		    	report.updateTestLog("CACM IDD", "Records Not Validated", Status.FAIL);
		    	System.out.println("Records Not Validated");
		    }
		
	}	
	catch (Exception e)
	{
		e.printStackTrace();
		
	}	
	}
	
	//Back to Previous Screen
	public void backtoPreviousScreen() throws Exception
	
	{
		
	try
	{

		  driver.findElement(By.xpath(Relationship.PreviousScreen)).click();		  
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Relationship.Upload_file)));
			
		    if(GF.isElementPresent(By.xpath(Relationship.Upload_file)))
		    {
		    	report.updateTestLog("CACM IDD", "Landed in Previous Screen", Status.PASS);
		    	System.out.println("Landed in Previous Screen");
		    }
		    else{
		    	
		    	report.updateTestLog("CACM IDD", " Not Landed in Previous Screen", Status.PASS);
		    	System.out.println("Not Landed in Previous Screen");
		    }
		
	}	
	catch (Exception e)
	{
		e.printStackTrace();
		
	}	
	}
//This Keyword imports the Relationship build in IDD.	
public void importOps() throws Exception
	
	{
		
	try
	{

		  driver.findElement(By.xpath(Relationship.Button_Import)).click();		  
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Process Executed by: ']")));
			
		    if(GF.isElementPresent(By.xpath("//*[text()='Inserted Records']")))
		    {
		    	report.updateTestLog("CACM IDD", "Exported Properly", Status.PASS);
		    	System.out.println("Exported Properly");
		    	CA_ID1=driver.findElement(By.xpath("//*[text()='Inserted Records']//parent::b//following::tbody[1]/tr/td[2]")).getText();
		    	System.out.println(CA_ID1);
		    }
		    else if(GF.isElementPresent(By.xpath("//*[text()='End Dated Records']")))
		    {
		    	
		    	report.updateTestLog("CACM IDD", "Exported Properly", Status.PASS);
		    	System.out.println("Exported Properly");
		    	CA_ID1=driver.findElement(By.xpath("//*[text()='End Dated Records']//parent::b//following::tbody[1]/tr/td[2]")).getText();
		    	System.out.println(CA_ID1);
		    }
		    else
		    {
		    	report.updateTestLog("CACM IDD", " Not Exported Properly", Status.PASS);
		    	System.out.println("Not Exported Properly");
		    }
	}	
	catch (Exception e)
	{
		e.printStackTrace();
		
	}	
	}
//This keyword do the following the steps:
//1)openMenu 2)savedQueries 3)searchCustomerRelationship 4)iddUIRunSearch 5)openRecordFromSearch

public void menuToSearch() throws Exception

{
	
try
{
   driver.switchTo().defaultContent();
   Thread.sleep(5000);
	IDD_Customer_Search obj2 = new IDD_Customer_Search(scriptHelper);
	obj2.openMenu();
	obj2.savedQueries();
	searchCustomerRelationship();
	obj2.iddUIRunSearch();
	obj2.openRecordFromSearch();
}	
catch (Exception e)
{
	e.printStackTrace();
	
}	
}
//searches  CA_ID in IDD
public void searchCustomerRelationship() throws Exception {
	   try
	   {
	  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(CA_ID1);
	  System.out.println("CA_ID:"+CA_ID1);
	  report.updateTestLog("CACM IDD", "Customer Search complete", Status.PASS);
	   }
	   catch(Exception e){
		  e.printStackTrace(); 
		 report.updateTestLog("CACM IDD", "Customer Search Not complete", Status.FAIL);
	   }
 }
//Utility function to find XPATH in idd for customer 
	 public String customerqueryXpath(String fileld) throws Exception
	   {
		  String QueryXpath="";
		   try{
			   
			   QueryXpath="//*[contains(text(),'"+fileld+"')]//following::div[1]/input";
		   }
		   
		   catch(Exception e)
		   {
			  e.printStackTrace(); 
		   }
		   return QueryXpath;
	   }
	 //This keyword takes below information from database and validates against database:
	// uiHierarchyType ,uiEntity1, uiEntity2,uiRelationStartDate,uiRelationEndDate

	  public void verifyRelationshipUpload() throws ParseException
	 	 {
	 		 String uiHierarchyType="";
	 		 String uiEntity1="";
	 		 String uiEntity2="";
	 		 String uiRelationStartDate="";
	 		 String uiRelationEndDate="";
	 		  
	 	
	 		 try
	 		 {
	 			 	
	 			    
	 			 	//Expanding Relationship Code 
	 			    driver.findElement(By.xpath(triangularButton("Relationships"))).click();	    
			    	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			    	switchtoTableView("Relationships");
			    	 List<WebElement> attrValueRow=driver.findElements(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr"));
		    		 int rowCount=attrValueRow.size();
		    		 System.out.println(" Total Row count vaue  "+rowCount);
		    	  	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
					 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
					 System.out.println("Env input from xlsx "+dbEnvInputXlsx);				 
					 Statement stmt=test.dbConnectionString( dbEnvInputXlsx ); 
					 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("RELATIONSHIP_UPLOAD");
					 sqlPassed=sqlPassed.replace("parent_CA_ID", PARENT_ID);
					 sqlPassed=sqlPassed.replace("child_CA_ID", CHILD_ID);
					 System.out.println("The SQL QUERY is:"+sqlPassed);
					Thread.sleep(3000);					
					ResultSet dataSet2=stmt.executeQuery(sqlPassed);
					Thread.sleep(6000);
					while (dataSet2.next())
					{ 
					
					 
					String dbHIERARCHY_CD=dataSet2.getString("HIERARCHY_CD");
				    System.out.println("PADDRESS_TYPE is: "+dbHIERARCHY_CD);
					String	DBCUST_NAME1=dataSet2.getString("CUST_NAME1");
					System.out.println("AV_CODE is: "+DBCUST_NAME1);
					String	DBCUST_NAME2=dataSet2.getString("CUST_NAME2");
					System.out.println("AV_CODE is: "+DBCUST_NAME2);
					
//**********************The below code is to change the date format***********************************************	   
					String dbperiod_StartDate=dataSet2.getString("PERIOD_START_DATE");
					          
		             if(dbperiod_StartDate !=null)
		                    {
		                    
		                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		                 SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");
		                 Date date = format1.parse(dbperiod_StartDate);
		                 

		                 dbperiod_StartDate=format2.format(date);
		                 if(dbperiod_StartDate.startsWith("0"))
		                 {
		                    dbperiod_StartDate=dbperiod_StartDate.substring(1);
		                 }
		                 }
		                 
		                 else 
		                 {
		                    dbperiod_StartDate="";
		                 }
		                 
		             System.out.println("PERIOD_START_DATE is: "+dbperiod_StartDate);

		             String dbperiod_EndDate=dataSet2.getString("PERIOD_END_DATE");
		             
		             if(dbperiod_EndDate !=null)
		             {
		             
		             SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		          SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");
		          Date date = format1.parse(dbperiod_EndDate);
		          

		          dbperiod_EndDate=format2.format(date);
		          if(dbperiod_EndDate.startsWith("0"))
		          {
		             dbperiod_EndDate=dbperiod_EndDate.substring(1);
		          }
		          }
		          
		           else 
		           {
		             dbperiod_EndDate="";
		          }
		          
		           System.out.println("PERIOD_END_DATE is: "+dbperiod_EndDate);
//******************* Date conversion ends here********************************************************
		           
					for(int i=1;i<=rowCount;i++)	
			    	 {
			    		 
			    	System.out.println(" Value for row count  : "+i);
			    	
			    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[2]")));
			    	uiEntity1=driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[2]")).getText();	 
			    	System.out.println("uiEntity1 Type value is : "+uiEntity1);
			    	
			    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[4]")));
			    	uiEntity2=driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[4]")).getText();	 
			    	System.out.println("uiEntity2 Type value is : "+uiEntity2);
			    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[1]")));
			    	uiHierarchyType=driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[1]")).getText();	 
			    	System.out.println("uiHierarchyType Type value is : "+uiHierarchyType);
					 
			    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[5]")));
			    	uiRelationStartDate=driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[5]")).getText();	 
			    	System.out.println("uiRelationStartDate Type value is : "+uiRelationStartDate);
			    	
			    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[6]")));
			    	uiRelationEndDate=driver.findElement(By.xpath("(//*[@class='extdt-table-layout rich-table '])[2]/tbody/tr["+i+"]/td[6]")).getText();	 
			    	System.out.println("uiRelationEndDate Type value is : "+uiRelationEndDate);
			    	
					 if (uiEntity1.equalsIgnoreCase(DBCUST_NAME1)& uiEntity2.equalsIgnoreCase(DBCUST_NAME2))
		 		   {
							//Comparing  DB and UI
					 		GF.reportStatusGeneration("uiHierarchyType",uiHierarchyType,dbHIERARCHY_CD); 
					 		GF.reportStatusGeneration("uiEntity1",uiEntity1,DBCUST_NAME1); 
					 		GF.reportStatusGeneration("uiEntity2",uiEntity2,DBCUST_NAME2); 
					 		GF.reportStatusGeneration("uiRelationStartDate",uiRelationStartDate,dbperiod_StartDate); 
					 		GF.reportStatusGeneration("uiRelationEndDate",uiRelationEndDate,dbperiod_EndDate); 
					 		break;
		 		   }
			   
			    	 }
					}
	 		 }
	 		 
	 		 catch (SQLException | InterruptedException e) {
					
					e.printStackTrace();
					
					System.out.println("DB validation failed for Subject Area Account Post Address");
		    		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);	
				} 
			 
	 	 }
	  //method returns triangular button xpath
	  public String triangularButton(String subjectarea)
	     {
	    	 
	    	// WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT NAME')]/../div["+i+"]//input"));
	    	 String subjectareaXpath= "//*[text()='"+subjectarea+"']//..//child::img";
	    	 System.out.println("Expanded"+subjectarea);
	    	 return subjectareaXpath;
	     }
	//Method to switch to table view 
		public void switchtoTableView(String subjectarea)
		{
			try{
			WebElement e1 = driver.findElement(By.xpath("(//span[text()='"+subjectarea+"']//..//..//..//following-sibling::td//input)[2]"));
			String view=e1.getAttribute("title");
			if (view.equalsIgnoreCase("Switch to Table View"))
			{
				
				e1.click();
				Thread.sleep(5000);
				System.out.println(view);
			}
			}
			catch(Exception e) 
			{
				
			}
			
		}
		  //Method to extract all Parent information from database
		 public void userInfoParent() throws Exception
	 	 
	 	 
	 	 {
		    
		  			 
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("USER_INFO_PARENT"); 
	 		
	 		 sqlPassed=sqlPassed.replace("ca_id1",PARENT_ID);
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		String CA_ID_DB ="";
	 		String UPDATED_BY_CUST ="";
	 		String DL_REASON_CUST="";
	 		String HUB_STATE_CUST ="";
	 		String SOURCE_DELETE_CUST =""; 
	 		String UPDATED_BY_REL ="";
	 		String DL_REASON_REL="";
	 		String HUB_STATE_REL ="";
	 		String SOURCE_DELETE_REL =""; 
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					
					 
					CA_ID_DB=dataSet.getString("CA_ID");
					System.out.println("CA_ID is: "+CA_ID_DB);
					UPDATED_BY_CUST=dataSet.getString("UPDATED_BY_CUST");
					System.out.println("UPDATED_BY is: "+UPDATED_BY_CUST);
					DL_REASON_CUST=dataSet.getString("DL_REASON_CUST");
					System.out.println("DL_REASON is: "+DL_REASON_CUST);
					HUB_STATE_CUST=dataSet.getString("HUB_STATE_IND_CUST");
					System.out.println("HUB_STATE_IND is: "+HUB_STATE_CUST);
					SOURCE_DELETE_CUST=dataSet.getString("SOURCE_DELETE_FLAG_CUST");
					System.out.println("SOURCE_DELETE_FLAG is: "+SOURCE_DELETE_CUST);
					UPDATED_BY_REL=dataSet.getString("UPDATED_BY_REL");
					System.out.println("UPDATED_BY is: "+UPDATED_BY_REL);
					DL_REASON_REL=dataSet.getString("DL_REASON_REL");
					System.out.println("DL_REASON is: "+DL_REASON_REL);
					HUB_STATE_REL=dataSet.getString("HUB_STATE_IND_REL");
					System.out.println("HUB_STATE_IND is: "+HUB_STATE_REL);
					SOURCE_DELETE_REL=dataSet.getString("SOURCE_DELETE_FLAG_REL");
					System.out.println("SOURCE_DELETE_FLAG is: "+SOURCE_DELETE_REL);


	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	 		 //Reporting User info
	 		report.updateTestLog("CACM IDD", "CA_ID: "+CA_ID_DB, Status.DONE);; 
	 		report.updateTestLog("CACM IDD", "UPDATED_BY_CUST: "+UPDATED_BY_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "DL_REASON_CUST: "+DL_REASON_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "HUB_STATE_IND_CUST: "+HUB_STATE_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "SOURCE_DELETE_FLAG_CUST: "+SOURCE_DELETE_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "UPDATED_BY_REL: "+UPDATED_BY_REL, Status.DONE);
	 		report.updateTestLog("CACM IDD", "DL_REASON_REL: "+DL_REASON_REL, Status.DONE);
	 		report.updateTestLog("CACM IDD", "HUB_STATE_REL: "+HUB_STATE_REL, Status.DONE);
	 		report.updateTestLog("CACM IDD", "SOURCE_DELETE_REL: "+SOURCE_DELETE_REL, Status.DONE);
	 		
	 	 } 
	     //Method to extract all child information from database
		 public void userInfoChild() throws Exception
	 	 
	 	 
	 	 {
		    
		  			 
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("USER_INFO_CHILD"); 
	 		
	 		 sqlPassed=sqlPassed.replace("ca_id1",CHILD_ID);
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		String CA_ID_DB ="";
	 		String UPDATED_BY_CUST ="";
	 		String DL_REASON_CUST="";
	 		String HUB_STATE_CUST ="";
	 		String SOURCE_DELETE_CUST =""; 
	 		String UPDATED_BY_REL ="";
	 		String DL_REASON_REL="";
	 		String HUB_STATE_REL ="";
	 		String SOURCE_DELETE_REL =""; 
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					
					 
					CA_ID_DB=dataSet.getString("CA_ID");
					System.out.println("CA_ID is: "+CA_ID_DB);
					UPDATED_BY_CUST=dataSet.getString("UPDATED_BY_CUST");
					System.out.println("UPDATED_BY is: "+UPDATED_BY_CUST);
					DL_REASON_CUST=dataSet.getString("DL_REASON_CUST");
					System.out.println("DL_REASON is: "+DL_REASON_CUST);
					HUB_STATE_CUST=dataSet.getString("HUB_STATE_IND_CUST");
					System.out.println("HUB_STATE_IND is: "+HUB_STATE_CUST);
					SOURCE_DELETE_CUST=dataSet.getString("SOURCE_DELETE_FLAG_CUST");
					System.out.println("SOURCE_DELETE_FLAG is: "+SOURCE_DELETE_CUST);
					UPDATED_BY_REL=dataSet.getString("UPDATED_BY_REL");
					System.out.println("UPDATED_BY is: "+UPDATED_BY_REL);
					DL_REASON_REL=dataSet.getString("DL_REASON_REL");
					System.out.println("DL_REASON is: "+DL_REASON_REL);
					HUB_STATE_REL=dataSet.getString("HUB_STATE_IND_REL");
					System.out.println("HUB_STATE_IND is: "+HUB_STATE_REL);
					SOURCE_DELETE_REL=dataSet.getString("SOURCE_DELETE_FLAG_REL");
					System.out.println("SOURCE_DELETE_FLAG is: "+SOURCE_DELETE_REL);


	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	 		 //Reporting User info
	 		report.updateTestLog("CACM IDD", "CA_ID: "+CA_ID_DB, Status.DONE);; 
	 		report.updateTestLog("CACM IDD", "UPDATED_BY_CUST: "+UPDATED_BY_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "DL_REASON_CUST: "+DL_REASON_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "HUB_STATE_IND_CUST: "+HUB_STATE_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "SOURCE_DELETE_FLAG_CUST: "+SOURCE_DELETE_CUST, Status.DONE);
	 		report.updateTestLog("CACM IDD", "UPDATED_BY_REL: "+UPDATED_BY_REL, Status.DONE);
	 		report.updateTestLog("CACM IDD", "DL_REASON_REL: "+DL_REASON_REL, Status.DONE);
	 		report.updateTestLog("CACM IDD", "HUB_STATE_REL: "+HUB_STATE_REL, Status.DONE);
	 		report.updateTestLog("CACM IDD", "SOURCE_DELETE_REL: "+SOURCE_DELETE_REL, Status.DONE);
	 		
	 	 } 
		 //Logout function
		 public void logout() throws Exception{
			 
			 IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper);
			 obj1.logoutCACMIDD();
		 }
		 //Method to fetch value of source delete indicator before record deletion
		 public void sourceDeleteBefore() throws Exception
	 	 
	 	 
	 	 {
		    
		  			 
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("SOURCE_DELETE_FLAG_REMOVE"); 
	 		
	 		 sqlPassed=sqlPassed.replace("ca_id1",PARENT_ID);
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		String SOURCE_DELETE =""; 
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					SOURCE_DELETE=dataSet.getString("SOURCE_DELETE_FLAG");
					System.out.println("SOURCE_DELETE_FLAG is: "+SOURCE_DELETE);


	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

	 		if (SOURCE_DELETE.equalsIgnoreCase("N"))
	 		{
	 			report.updateTestLog("CACM IDD", "SOURCE_DELETE: "+SOURCE_DELETE, Status.PASS);
	 			
	 		}
	 		else{
	 			
	 			report.updateTestLog("CACM IDD", "SOURCE_DELETE: Before Remove "+SOURCE_DELETE, Status.FAIL);
	 		}
	 	 } 
////Method to fetch value of source delete indicator after record deletion		 
public void sourceDeleteAfter() throws Exception
	 	 
	 	 
	 	 {
		    
		  			 
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("SOURCE_DELETE_FLAG_REMOVE"); 
	 		
	 		 sqlPassed=sqlPassed.replace("ca_id1",PARENT_ID);
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		String SOURCE_DELETE =""; 
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					SOURCE_DELETE=dataSet.getString("SOURCE_DELETE_FLAG");
					System.out.println("SOURCE_DELETE_FLAG is After Remove: "+SOURCE_DELETE);


	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

	 		if (SOURCE_DELETE.equalsIgnoreCase("Y"))
	 		{
	 			report.updateTestLog("CACM IDD", "SOURCE_DELETE: "+SOURCE_DELETE, Status.PASS);
	 			
	 		}
	 		else{
	 			
	 			report.updateTestLog("CACM IDD", "SOURCE_DELETE: "+SOURCE_DELETE, Status.FAIL);
	 		}
	 	 } 
//This keyword verifies that no record is present when end date is imported from file
public void verifyRelNoRecords() throws Exception
 {
	
	  

	 try
	 {
		 	
		    
		 	//Expanding Relationship Code 
		    driver.findElement(By.xpath(triangularButton("Relationships"))).click();	    
	    	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	    	switchtoTableView("Relationships");
	    	 if(driver.findElements(By.xpath("//*[text()='No Records ']")).size()==1)
			   {
				 System.out.println("No Records found for Relationship ");
				
					  report.updateTestLog("CACM IDD", "No Records ", Status.PASS);	
				
				  }
	    	 else{
	    		 System.out.println("Records found for Relationship ");
					
				  report.updateTestLog("CACM IDD", "Records present", Status.FAIL);	
	    		 
	    	 }

	 }
	 
	 catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("DB validation failed for Subject Area Account Post Address");
   		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);	
		} 
	 
 }

}
