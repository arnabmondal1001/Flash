package businesscomponents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.util.frameworkutils.Status;
import org.openqa.selenium.WebElement;
import objectrepositories.CACMIDDRepo;
import objectrepositories.Customer_Search;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import java.util.Date;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


public class IDD_Customer_Add_Classification extends ReusableLibrary {


	Random rand = new Random(); 
	WebDriverWait wait = new WebDriverWait(driver, 100);
	static String CA_ID="";
    public IDD_Customer_Add_Classification(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}


    public void loginCACM_ClsfnAdd() throws Exception {
    	IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
    	obj1.loginCACM();
    		
    }
    
    public void openMenu_ClsfnAdd() throws Exception {
		IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
		
		obj1.openMenu();
				
	}
    
    public void savedQueries_ClsfnAdd() throws Exception {
		IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
		obj1.savedQueries();
		
	}
    
    public void testdatainsertCustClassification_ClsfnAdd()throws Exception
    {
       
      try 
      {
             String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
             if (dbdatamode.equalsIgnoreCase("Manual"))
             {
                   
                   searchCustomer();
             }
             
             if (dbdatamode.equalsIgnoreCase("Auto"))
             {
                   
             	searchRandomCustomer();
             }
             
      }
      catch(Exception e){
             e.printStackTrace();
      }
       
    }
    
    public void searchCustomer() throws Exception {
		   try
		   {
		  String CA_ID = dataTable.getData("General_Data", "CA_ID");
		  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(CA_ID);
		  System.out.println("CA_ID:"+CA_ID);
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
  	 
  	public void searchRandomCustomer()
    {   
   	 CA_ID=identificationTestDataforCustClassificationAdd();

   	 try {
   		
   		 driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_CA_ID_Under_Search)).sendKeys(CA_ID);
   		 System.out.println("CA ID is taken from identificationTestDataforCustClassificationAdd() ");
   		 Thread.sleep(3000);
   		// driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(Keys.ENTER);
   	} catch (InterruptedException e) {
   		
   		e.printStackTrace();
   	} 
   	
   	 report.updateTestLog("CACM IDD", "Searched Customer value is "+ CA_ID, Status.PASS); 
    }
        
  	public String  identificationTestDataforCustClassificationAdd()


  	{
  		 
  		 String	 CA_ID="";
  		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
  		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
  		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
  		 
  		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
  		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("ident_TestData_Cust_Classification_Add");
  		 
  		 System.out.println("Query from parameter file is: "+sqlPassed);
  		 
  		 
  		 try {
  			 
  			
  			ResultSet dataSet=stmt.executeQuery(sqlPassed);
  			Thread.sleep(10000);
  			while (dataSet.next()) { 
  		    CA_ID=dataSet.getString("CA_ID");
  			System.out.println("CA ID is: "+CA_ID);
  			}
  		} catch (SQLException | InterruptedException e) {
  			
  			e.printStackTrace();
  		} 
  		 
  		return CA_ID;
  	}
  	
  	public void iddUIRunSearch_ClsfnAdd() throws Exception {
		IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
		obj1.iddUIRunSearch();
				
		}
  	
  	public void openRecordFromSearch_ClsfnAdd() throws Exception
	{ String CA_ID = dataTable.getData("General_Data","CA_ID");
	     try {
	     //Open  record from Search Result
	    Thread.sleep(5000);
	    driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)).click();
	    System.out.println("Opened the first record");
	    report.updateTestLog("CACM IDD", "Opened the first Record", Status.PASS);
	    Thread.sleep(8000);
	    //Implement new wait 
	  //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='ACCT CLOSED IND']")));
	    //Open any record from Search Result
	    //((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(ObjectRepositories.IDD_UI_AccountNumber_From_DataTab)));
	    String CA_ID_UI=cACMAttributeVAl("CA ID");
	  if (CA_ID_UI.equalsIgnoreCase(CA_ID.trim())) {
	    
	    report.updateTestLog("CACM IDD", "CA ID MAtched and the Value:" +CA_ID_UI, Status.PASS);
	  }
	     }
	     
	     catch(Exception e)
	     {
	            System.out.println("Not Able to open the first record");
	           report.updateTestLog("CACM IDD", "Not able to open the first Record", Status.FAIL);  
	     }
	}
  	
  	public String cACMAttributeVAl(String text1) throws Exception {
		   ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[text()='"+text1+"']//following-sibling::td//span"))); 
		   String val1= driver.findElement(By.xpath("//*[text()='"+text1+"']//following-sibling::td//span")).getText() ;
		   
		   return val1;
		}
  	
  	public void clickNew(String Type) throws Exception{
    	try {
    	String Xpath="//*[@class='topTogglePanelHeaderText' and text()='"+Type+"']//following::input[3]";
    	driver.findElement(By.xpath(Xpath)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='This entity has been created but it is not saved!']")));
    	System.out.println("Button Clicked New");
    	}
    	catch (Exception e) {	
    	System.out.println("No Button Clicked ");
    	}
    }
  	
  //Utility function to get IDD text values 
  	 public String uiIDDval(String uiVal )
  	 {
  		 
  		 String UiVal=driver.findElement(By.xpath("//*[text()='"+uiVal+"']"+"//following-sibling::td[1]")).getText() ;
  		 return UiVal ;
  	 }
  	 //Utility function to get IDD xPath
  	 public String uiIDxPath(String uiVal )
  	 {
  		 
  		 String UiVal="//*[text()='"+uiVal+"']"+"//following-sibling::td[1]";
  		 return UiVal ;
  	 }
  	
  	public void addClassification() 
  	 {
  	        

  			String ClassificationValue="EDP +ONE";
  			ClassificationValue = ClassificationValue.trim(); 
  	          

  	        try {
  	               
  	                
  	        	driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
  	        	Thread.sleep(10000);
  	            clickNew("Client Classification");
  		    	Thread.sleep(5000);
  		    	driver.findElement(By.xpath("//*[text()='CUSTOMER CLASSIFICATION TYPE']//following-sibling::td//input[2]")).clear();
  	      		Thread.sleep(5000);
  	      		driver.findElement(By.xpath("//*[text()='CUSTOMER CLASSIFICATION TYPE']//following-sibling::td//input[2]")).sendKeys(ClassificationValue);
  	      	    Thread.sleep(5000);
  	      	    report.updateTestLog("CACM IDD", "Classification added successfully ", Status.PASS);

  	        }

  	        catch (Exception e) {
  	               
  	               e.printStackTrace();
  	               report.updateTestLog("CACM IDD", "Classification is not added", Status.FAIL);
  	        } 
  	        
  	  
  	 }
  	
  	public String triangularButton(String subjectarea)
    {
   	 
   	// WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT NAME')]/../div["+i+"]//input"));
   	 String subjectareaXpath= "//*[text()='"+subjectarea+"']//..//child::img";
   	 System.out.println("Expanded"+subjectarea);
   	 return subjectareaXpath;
    }
  	
  	public void apply_ClsfnAdd() throws Exception
	{
		try{
			driver.findElement(By.xpath("//*[text()='Apply']")).click();
			Thread.sleep(5000);
			System.out.println("Apply Button Clicked");
			 report.updateTestLog("CACM IDD", "Apply Button Clicked", Status.PASS);
		}
		catch(Exception e){
		e.printStackTrace();
		 report.updateTestLog("CACM IDD", "Apply Button Not Clicked", Status.FAIL);
		}
	}
  	
  	public void saveitem(String item) throws Exception
	{
		WebElement save=driver.findElement(By.xpath("//*[text()='"+item+"']"));
		String color =save.getCssValue("color");
		if (color.equalsIgnoreCase( "rgba(255, 117, 25, 1)"))
		{
			driver.findElement(By.xpath("//*[text()='Save']")).click();
			Thread.sleep(5000);
			System.out.println("Save Button Clicked");
			report.updateTestLog("CACM IDD", "Save Button Clicked", Status.PASS);
			
		}
		else{
			
			System.out.println("Save Button not Clicked");
			report.updateTestLog("CACM IDD", "Issue in testdata", Status.FAIL);
		}
	}
  	
  	public void save_ClsfnAdd() throws Exception
	{
		saveitem("CUSTOMER CLASSIFICATION TYPE");
		
	}
  	
  	public void verifyaddedClassification() 
 	 {
 	        
 	        String CA_ID_UI=uiIDDval("CA ID");
 	        String ca_id2 = dataTable.getData("General_Data", "CA_ID");
 	        String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
 	        String HUB_STATE_IND = "";
 	        String CA_ID1 = "";
 	        
 	       try
 			{
 				DbEnvConnection test=new DbEnvConnection(scriptHelper);
 		  		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
 		  		 
 		  		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
 		  		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 		  		 System.out.println("Env input from xlsx "+stmt);

 		  		 
 				 
 		  		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("VerifyAddedClassification"); 
 		  		if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
 	    		 {
 	    		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
 	    		 }
 	    		 if (dbdatamode.equalsIgnoreCase("Auto"))
 	    		 {
 	    		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
 	    		 }
 		  		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID_UI);
 		  		 System.out.println("Validation SQL query :"+sqlPassed);
 		  		 ResultSet dataSet2=stmt.executeQuery(sqlPassed);
 		  		while (dataSet2.next()) { 

 					 
 		  			HUB_STATE_IND=dataSet2.getString("HUB_STATE_IND");
 		  			CA_ID1=dataSet2.getString("CA_ID");
 				    System.out.println("HUB_STATE_IND is : "+HUB_STATE_IND);

 				 }
 		  		
 		  		if (HUB_STATE_IND.equals("1"))
 		  		{
 		  			System.out.println("Record present in DB with HUB_STATE_IND as 1");
 		  			report.updateTestLog("CACM IDD", "Classification added successfully and reflected in BO with HUB_STATE_IND as 1 for  "+CA_ID1, Status.PASS);
 		  		}

 	        }

 	        catch (Exception e) {
 	               
 	               e.printStackTrace();
 	               report.updateTestLog("CACM IDD", "Classification not added successfully ", Status.FAIL);
 	        } 
 	        
 	  
 	 }
  	
  	public void captureAttrsVal_ClsfnAdd()
  	{
  		  String caId=uiIDDval("CA ID");
  		  String updatedBy="";
  		  String dlReason="";
  		  String hubStateInd="";
  		  String sourceDeleteFlag="";
  		  
  		  
  		  try
  		{
  	    
  		
  		DbEnvConnection test=new DbEnvConnection(scriptHelper);
  		String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
  		
  		System.out.println("Env input from xlsx "+dbEnvInputXlsx);
  		Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
  	    System.out.println("Env input from xlsx "+stmt);
  		String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("custAttrsValClsfAdd");
  	    sqlPassed=sqlPassed.replace("ca_id11",caId);
  	    System.out.println("Query is  "+sqlPassed); 
  	    ResultSet dataSet=stmt.executeQuery(sqlPassed);	 
  	    System.out.println("*************************** Caputre Customer Fields Value  *******************************");	 
  	    while (dataSet.next()) { 

  	  	  caId=dataSet.getString("CA_ID");
  	  	  updatedBy=dataSet.getString("UPDATED_BY");
  	  	  dlReason=dataSet.getString("DL_REASON");
  	  	  hubStateInd=dataSet.getString("HUB_STATE_IND");
  	  	  sourceDeleteFlag=dataSet.getString("SOURCE_DELETE_FLAG");
  		 
  	    }
  	    
  	      
  	    	   report.updateTestLog("CACM IDD", "CA_ID, UPDATED_BY, DL_REASON, HUB_STATE_IND, SOURCE_DELETE_FLAG " , Status.DONE);
  	    	   report.updateTestLog("CACM IDD", caId +","+ updatedBy +","+ dlReason +","+ hubStateInd +","+sourceDeleteFlag , Status.PASS);
  	   
  	    }catch(Exception e)
  	    {
  	  	  report.updateTestLog("CACM IDD", "CA_ID, UPDATED_BY, DL_REASON, HUB_STATE_IND, SOURCE_DELETE_FLAG  not populated " , Status.FAIL);
  	    }
  	}
  	
  	public void logoutCACMIDD_ClsfnAdd() throws Exception {
		IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
		obj1.logoutCACMIDD();
				
		}

}
