package businesscomponents;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import businesscomponents.DbEnvConnection;
import objectrepositories.CACMIDDRepo;
import objectrepositories.Customer_Search;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import java.util.Date;
//Class that holds all the keywords present in the AuctionAccess.xls
public class IDD_Customer_Search extends ReusableLibrary {
	Random rand = new Random(); 
	WebDriverWait wait = new WebDriverWait(driver, 100);
	static String accountId="";
	static String CA_ID="";
	public IDD_Customer_Search(ScriptHelper scriptHelper)
	{
		super(scriptHelper);

	}
	GenericFunctions GF = new GenericFunctions(scriptHelper);
	WebElement element;
	static String dealerId="";

	//  Keyword will Open https://v4externaltest.auctionaccess.com site with valid credentials, Role and Company. The key word will Generates reports where ever applicable.
	public void loginCACM() throws Exception 
	{

		String env = dataTable.getData("General_Data", "Environment");
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);	
		ArrayList<String> al =url_list(env) ;
		String strAppURL=al.get(0);
		String uid=al.get(1);
		String pwd=al.get(2);
		driver.get(strAppURL);
		Thread.sleep(25000);
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try
		{
			try {
			
						String text1=driver.findElement(By.xpath("(//*[@class='icon icon-generic'])[1]//..//following::div[2]/h1/span")).getText();
						if (text1.equalsIgnoreCase("This site can’t be reached"))
						{
							
							report.updateTestLog("CACM IDD", "Application is failed to open ", Status.FAIL);
							System.out.println("IDD site was not able to open");
							
						}
			}
			 catch(Exception e)
			{
				 
				 e.printStackTrace();	 
				 
			}
			
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Log In']")));
		        driver.findElement(By.xpath(Customer_Search.IDD_UI_TXT_LOGIN_USER_ID)).sendKeys(uid);
		        driver.findElement(By.xpath(Customer_Search.IDD_UI_TXT_LOGIN_PWD)).sendKeys(pwd);
		        report.updateTestLog("CACM IDD", "Application is opened ", Status.SCREENSHOT);
		        driver.findElement(By.xpath(Customer_Search.IDD_UI_BTN_LOGIN)).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@name='dashboard']"))); 
		        report.updateTestLog("CACM IDD", "Login Successful ", Status.PASS);
		        System.out.println("IDD Login Successful");
			  
			}
								
	
			catch(Exception e)
		{
			report.updateTestLog("CACM IDD", "Login Failed due to Login Screen Load Delay", Status.FAIL);
		}

		
	}
	
		//Method to add Application url and credential depending upon different environments 
    	 public ArrayList<String>url_list(String Env) throws Exception
  	   {
  			
  		   ArrayList<String> listValue = new ArrayList<String>();
  			// Intializing  the variables
  		   
  		   String ApplicationUrl=null;
  	  		String userNameAA=null;
  	  		String passwordAA=null;
  			
  			
  	  	
  	  		if (Env.equalsIgnoreCase("QA1"))
  	  		{
  	  		ApplicationUrl=properties.getProperty("CACMQA1IDDUrl");	
  			userNameAA =properties.getProperty("UserIDQA1");
  			passwordAA =properties.getProperty("PasswordQA1");
  	  		

		  	  		listValue.add(ApplicationUrl);
		  	  		listValue.add(userNameAA);
		  	  		listValue.add(passwordAA);
		  	  		
  			 		
  	   		}
    	 
    		if (Env.equalsIgnoreCase("QA2"))
  	  		{
  	  		 ApplicationUrl=properties.getProperty("CACMQA2IDDUrl");	
  	  		 userNameAA =properties.getProperty("UserIDQA2");
  	  		 passwordAA =properties.getProperty("PasswordQA2");
  	  		


	  	  		listValue.add(ApplicationUrl);
	  	  		listValue.add(userNameAA);
	  	  		listValue.add(passwordAA);
		  	  		
  			 		
  	   		}
    		if (Env.equalsIgnoreCase("PreProd"))
  	  		{
  	  		 ApplicationUrl=properties.getProperty("CACMPreProdIDDUrl");	
  	  		 userNameAA =properties.getProperty("UserIDPreProd");
  	  		 passwordAA =properties.getProperty("PasswordPreProd");
  	  		

		  	  		listValue.add(ApplicationUrl);
		  	  		listValue.add(userNameAA);
		  	  		listValue.add(passwordAA);
		  	  		
  			 		
  	   		}
    		return listValue  ;
}
    	 
    	//Opens Menu from IDD header
 		public void openMenu() throws Exception
 		{
 			String Menuitem = dataTable.getData("General_Data", "Menuitem");
 			 driver.findElement(By.xpath("//*[text()='"+Menuitem+"']")).click();
 		     report.updateTestLog("CACM IDD", "Opened Menu Item"+Menuitem, Status.PASS);
 		     Thread.sleep(5000);
 		}
 	 
 		
 		public void savedQueries() throws Exception {
 	           String savedQueries = dataTable.getData("General_Data", "savedQueries");
 	           try {
 	                  
 	                  driver.switchTo().frame("data");
 	                  Thread.sleep(10000);
 	    
 	                  if (savedQueries.equalsIgnoreCase("Account Query")) {
 	                        driver.findElement(By.xpath (CACMIDDRepo.accountQuerySearchtxt)).click(); 
 	                        
 	                  }
 	                //  CACM IDD Customer Query
 	                  else 
 	                  {
 	                  driver.findElement(By.xpath ("//*[text()='"+savedQueries+"']")).click();
 	                  }
 	                  Thread.sleep(15000);
 	                // wait.until(ExpectedConditions.elementToBeSelected(By.xpath(IDDRepo1.IDD_UI_OpenQueries))); 
 	                 //Click on Open Queries
 	                       driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_OpenQueries)).click();
 	                 Thread.sleep(20000);
 	                 System.out.println("Saved Query Opened"+savedQueries);
 	                 report.updateTestLog("CACM IDD", "Saved Query opened"+savedQueries, Status.PASS);
 	           }
 	           catch (Exception e) {
 	                  e.printStackTrace();
 	                  System.out.println("Saved Query not Opened"+savedQueries);
 	                  report.updateTestLog("CACM IDD", "Saved Query not opened"+savedQueries, Status.FAIL);
 	           }
 	    }
 		
 		 // get the account Id from account table using method identificationTestDataforAcct() and put it account query search text
   	 public void searchAccount()
   	 {   
   		 accountId=identificationTestDataforAcct();
   
   		 try {
				
				 driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(accountId);
				 System.out.println("Account ID is taken from identificationTestDataforAcct() ");
				 Thread.sleep(3000);
				// driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(Keys.ENTER);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} 
   		
   	 }
   	 
   	 public void iddUIRunSearch() throws Exception {
  		  try {
  			  
  			Thread.sleep(5000);
  		  //Click On RUN SEARCH button
  	        driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_RunSearch)).click();
  	        Thread.sleep(10000);
  	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)));
  	      //*[@class='extdt-table-layout rich-table ' and contains(@id, 'search')]//table
  	        if(driver.findElements(By.xpath("//*[@class='extdt-table-layout rich-table ' and contains(@id, 'search')]//table")).size()==0){
  	       // if(driver.findElements(By.xpath("//*[text()='ROWID_OBJECT']")).size()==0){
  	        	report.updateTestLog("CACM IDD", "No Results to Show", Status.FAIL);  
  	        }
  	       // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'ACCOUNT NUMBER:')]")));  
  	        System.out.println("CACM IDD UI RunSearch Finished"); 
  	        report.updateTestLog("CACM IDD", " UI RunSearch Finished", Status.PASS);
  		  }
  		  catch(Exception e) {
  			  report.updateTestLog("CACM IDD", " UI RunSearch not Finished", Status.FAIL);  
  			  e.printStackTrace();
  		  }
  	  }

   	 
   	 public void openRecordFromSearch() throws Exception
  	  {
  		  try {
  		
  	        Thread.sleep(5000);
  	        driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)).click();
  	        System.out.println("Opened record");
  	        report.updateTestLog("CACM IDD", "Opened Record", Status.PASS);
  	        Thread.sleep(8000);
  	      
  		  }
  		  
  		  catch(Exception e)
  		  {
  			  System.out.println("Not Able to open the record");
  		        report.updateTestLog("CACM IDD", "Not Able to open the  Record", Status.FAIL);  
  		  }
  	  } 
   	 
	 public String  identificationTestDataforAcct()
	 
	 
	 {
		 
		 String	 ACCT_ID="";
		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
		 
		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("Ident_TestData_acct");
		 
		 System.out.println("Query from parameter file is: "+sqlPassed);
		 
		 
		 try {
			 
 			
				ResultSet dataSet=stmt.executeQuery(sqlPassed);
				Thread.sleep(10000);
				while (dataSet.next()) { 
				
				 
			    ACCT_ID=dataSet.getString("ACCT_ID");
				System.out.println("Account ID is: "+ACCT_ID);
 			}
			} catch (SQLException | InterruptedException e) {
				
				e.printStackTrace();
			} 
		 
		return ACCT_ID;
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
	 
	
	  //Utility function to find XPATH in idd for customer other fileds 
		 public String OthercustomerqueryXpath(String fileld) throws Exception
	 	   {
	 		  String QueryXpath="";
	 		   try{
	 			  QueryXpath="(//*[contains(text(),'"+fileld+"')]//following-sibling::div//div//child::div[2]//child::input[2])[1]";
	 		   }
	 		   
	 		   
	 		   catch(Exception e)
	 		   {
	 			  e.printStackTrace(); 
	 		   }
	 		   return QueryXpath;
	 	   }
	//Function to find customer with different attributes in IDD 
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
 	 //db validation for search by CA ID
	 public void dbval_Customer_CAID() throws Exception
    	 
    	 
    	 {
    		 
		 // Highlighting the UI value 
		     //CA_ID
		 	 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CA ID"))));
		     Thread.sleep(3000);
		     String CA_ID_UI=uiIDDval("CA ID");
		     //CUST_NAME
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER NAME"))));
		     Thread.sleep(3000);
		     String CUST_NAME_UI=uiIDDval("CUSTOMER NAME");
		     //CUST_LEGAL_NAME_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER LEGAL NAME"))));
		     Thread.sleep(3000);
		     String CUST_LEGAL_NAME_UI=uiIDDval("CUSTOMER LEGAL NAME");
		     //CUST_TYPE_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER TYPE"))));
		     Thread.sleep(3000);
		     String CUST_TYPE_UI=uiIDDval("CUSTOMER TYPE");
		     //FRANCHISE_FLAG
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("AUTO FRANCHISE INDICATOR"))));
		     Thread.sleep(3000);
		     String FRANCHISE_FLAG_UI=uiIDDval("AUTO FRANCHISE INDICATOR");
		     //CUST_STATUS
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER STATUS"))));
		     Thread.sleep(3000);
		     String CUST_STATUS_UI=uiIDDval("CUSTOMER STATUS");
		     //SOURCE_DELETE_FLAG_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("SOURCE DELETE FLAG"))));
		     Thread.sleep(3000);
		     String SOURCE_DELETE_FLAG_UI=uiIDDval("SOURCE DELETE FLAG");
		     //SEGMENT_DESC_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("SEGMENTATION"))));
		     Thread.sleep(3000);
		     String SEGMENT_DESC_UI=uiIDDval("SEGMENTATION");
		     //CUST_SUBTYPE_DB
		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER SUBTYPE"))));
		     Thread.sleep(3000);
		     String CUSTOMER_SUBTYPE_UI=uiIDDval("CUSTOMER SUBTYPE");
		     
    		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
    		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
    		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
    		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
    		 String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
    		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
    		 System.out.println("Env input from xlsx "+stmt);
    		
    		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CA_ID"); 
    		 if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
    		 {
    		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
    		 }
    		 if (dbdatamode.equalsIgnoreCase("Auto"))
    		 {
    		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
    		 }
    		 System.out.println("Validation SQL query :"+sqlPassed);
    		 //Initializing DB values
    		 String CA_ID_DB ="";
    		 String CUST_NAME_DB ="";
    		 String CUST_LEGAL_NAME_DB="";
    		 String  CUST_TYPE_DB="";
    		 String CUST_SUBTYPE_DB="";
    		 String FRANCHISE_FLAG_DB="";
    		 String CUST_STATUS_DB="";
    		 String SOURCE_DELETE_FLAG_DB="";
    		 String SEGMENT_DESC_DB="";
    		 
    		 try {
    			 
    			 Thread.sleep(10000);
				ResultSet dataSet=stmt.executeQuery(sqlPassed);
				Thread.sleep(10000);
				while (dataSet.next()) { 
				
				 
				 CA_ID_DB=dataSet.getString("CA_ID");
				System.out.println("CA_ID_DB is: "+CA_ID_DB);
				CUST_NAME_DB=dataSet.getString("CUST_NAME");
				System.out.println("CUST_NAME_DB is: "+CUST_NAME_DB);
				CUST_LEGAL_NAME_DB=dataSet.getString("CUST_LEGAL_NAME");
				System.out.println("CUST_LEGAL_NAME_DB is: "+CUST_LEGAL_NAME_DB);
				CUST_TYPE_DB=dataSet.getString("CUST_TYPE");
				System.out.println("CUST_TYPE_DB is: "+CUST_TYPE_DB);
				CUST_SUBTYPE_DB =dataSet.getString("CUST_SUBTYPE");
				System.out.println("CUST_SUBTYPE_DB is: "+CUST_SUBTYPE_DB);
				FRANCHISE_FLAG_DB=dataSet.getString("FRANCHISE_FLAG");
				System.out.println("FRANCHISE_FLAG_DB is: "+FRANCHISE_FLAG_DB);
				CUST_STATUS_DB =dataSet.getString("CUST_STATUS");
				System.out.println("CUST_STATUS_DB is: "+CUST_STATUS_DB);
				SOURCE_DELETE_FLAG_DB=dataSet.getString("SOURCE_DELETE_FLAG");
				System.out.println("SOURCE_DELETE_FLAG_DB is: "+SOURCE_DELETE_FLAG_DB);
				SEGMENT_DESC_DB=dataSet.getString("SEGMENT_DESC");
				System.out.println("SEGMENT_DESC_DB is: "+SEGMENT_DESC_DB);
    			}
			} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		 //Comparing CA_ID with DB and UI
    		 reportStatusGeneration("CA_ID",CA_ID_UI,CA_ID_DB); 
    		 
    		 // Comparing Customer Name with DB vis UI
    		 reportStatusGeneration("CUST_NAME",CUST_NAME_UI,CUST_NAME_DB); 
    		 
    		 //Comparing Customer legal name
    		 reportStatusGeneration("CUST_LEGAL_NAME",CUST_LEGAL_NAME_UI,CUST_LEGAL_NAME_DB); 
    		 
    		 //CUST TYPE
    		 reportStatusGeneration("CUST TYPE",CUST_TYPE_UI,CUST_TYPE_DB); 
    		 //	AUTO FRANCHISE INDICATOR
    		 reportStatusGeneration("FRANCHISE FLAG",FRANCHISE_FLAG_UI,FRANCHISE_FLAG_DB); 
    		 //CUST_STATUS_UI
    		 reportStatusGeneration("CUST STATUS",CUST_STATUS_UI,CUST_STATUS_DB); 
    		 //SOURCE DELETE FLAG
    		 reportStatusGeneration("SOURCE DELETE FLAG",SOURCE_DELETE_FLAG_UI,SOURCE_DELETE_FLAG_DB);
    		 //SEGMENT_DESC_DB
    		 reportStatusGeneration("SEGMENT_DESC",SEGMENT_DESC_UI,SEGMENT_DESC_DB);
    		 //CUSTOMER SUBTYPE"
    		 reportStatusGeneration("CUSTOMER SUBTYPE",CUSTOMER_SUBTYPE_UI,CUST_SUBTYPE_DB);
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
	  //The keyword takes values from SQL and provide it in UI as an Input
	  public void  testDataforCustWithOtherAttributes() throws Exception
 	 
 	 
 	 {
 		 
 		// String	 dbAcctId="";
 		 String	 dbAcctNm="";
 		 String	 dbAcctType="";
 		 String  dbAcctSubType="";
 		 String  dbCountryCdDesc="";
 		
 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);		 
 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_OTHER_ATTRIBUTES");
 		 
 		 System.out.println("Query from parameter file is: "+sqlPassed);
 		 
 		 
 		 try {
 			 
 			 
				ResultSet dataSet=stmt.executeQuery(sqlPassed);
				Thread.sleep(13000);
				
				
				
				while (dataSet.next()) { 
					
					
				CA_ID=dataSet.getString("CA_ID");	
				System.out.println("CA ID is: "+CA_ID);
				String dbfield1 = dataTable.getData("General_Data", "ID"); 
				if(!dbfield1.equals("No"))
				{
					// CA_ID=dataSet.getString("CA_ID");
	 				 System.out.println("CA ID is: "+CA_ID);
	 				driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(CA_ID);
	 				report.updateTestLog("CACM IDD", "CA ID value is "+ CA_ID, Status.DONE); 
	 				 
				}
				
				
				String dbfield2 = dataTable.getData("General_Data", "CUSTOMER NAME"); 
				if(!dbfield2.equals("No"))
				{
					 dbAcctNm=dataSet.getString("CUST_NAME");
	 				 System.out.println("CUSTOMER  Name is: "+dbAcctNm);
	 				 
	 				driver.findElement(By.xpath(customerqueryXpath("CUSTOMER NAME"))).sendKeys(dbAcctNm);
	 				report.updateTestLog("CACM IDD", "CUSTOMER  Name  value is "+ dbAcctNm, Status.DONE); 
	 				 
				}
				
				String dbfield3 = dataTable.getData("General_Data", "CLEANSED ADDRESS LINE 1"); 
				if(!dbfield3.equals("No"))
				{
				String	 dbCleansedAddrLine1=dataSet.getString("C_ADDR_LINE_1");
	 				 System.out.println("CLEANSED ADDRESS LINE 1 is: "+dbCleansedAddrLine1);
	 				 
	 				driver.findElement(By.xpath(customerqueryXpath("CLEANSED ADDRESS LINE 1"))).sendKeys(dbCleansedAddrLine1);
	 				report.updateTestLog("CACM IDD", "Search CLEANSED ADDRESS LINE 1 value is "+ dbCleansedAddrLine1, Status.DONE); 
				}
				
				String dbfield4 = dataTable.getData("General_Data", "CLEANSED COUNTRY"); 
				if(!dbfield4.equals("No"))
				{
					 dbCountryCdDesc=dataSet.getString("COUNTRY_CD_DESC");
	 				 System.out.println("Country Code Desc is: "+dbCountryCdDesc);
	 		        driver.findElement(By.xpath(OthercustomerqueryXpath("CLEANSED COUNTRY"))).sendKeys(dbCountryCdDesc);
	 				report.updateTestLog("CACM IDD", "Search Country Code Desc value is "+ dbCountryCdDesc, Status.DONE); 
	 				 
				}
				
				String dbfield5 = dataTable.getData("General_Data", "CLEANSED PROVINCE"); 
				
				if(!dbfield5.equals("No"))
				{
				String	dbProvinceCdDesc=dataSet.getString("PROVINCE_CD_DESC");
	 		    System.out.println("Province Code is: "+dbProvinceCdDesc);
	 				 
	 			driver.findElement(By.xpath(OthercustomerqueryXpath("CLEANSED PROVINCE"))).sendKeys(dbProvinceCdDesc);
	 			report.updateTestLog("CACM IDD", "Search Province Code value is "+ dbProvinceCdDesc, Status.DONE); 
				}
				
				String dbfield6 = dataTable.getData("General_Data", "CLEANSED CITY"); 
				
				if(!dbfield6.equals("No"))
				{
					String dbCleansedCity=dataSet.getString("C_CITY");
	 				 System.out.println("Cleansed City is: "+dbCleansedCity);
	 				 
	 				driver.findElement(By.xpath(customerqueryXpath("CLEANSED CITY"))).sendKeys(dbCleansedCity);
	 				report.updateTestLog("CACM IDD", "Search Cleansed City value is "+ dbCleansedCity, Status.DONE);  
				}
				
				String dbfield7 = dataTable.getData("General_Data", "CUSTOMER TYPE"); 
				if(!dbfield7.equals("No"))
				{
				 dbAcctType=dataSet.getString("CUST_TYPE");
	 			System.out.println("Account Type is: "+dbAcctType);
	 			driver.findElement(By.xpath(OthercustomerqueryXpath("CUSTOMER TYPE"))).sendKeys(dbAcctType);
	 			report.updateTestLog("CACM IDD", "Search Cust Type value is "+ dbAcctType, Status.DONE);  
	 			
				}

	 			String dbfield8 = dataTable.getData("General_Data", "CUSTOMER SUBTYPE"); 
	 			Thread.sleep(3000);
				if(!dbfield8.equals("No"))
				{
				 dbAcctSubType=dataSet.getString("CUST_SUBTYPE");
	 			System.out.println("CUSTOMER Sub Type is: "+dbAcctSubType);
	 			driver.findElement(By.xpath(OthercustomerqueryXpath("CUSTOMER SUBTYPE"))).click();
	 			driver.findElement(By.xpath(OthercustomerqueryXpath("CUSTOMER SUBTYPE"))).sendKeys(dbAcctSubType);
	 			report.updateTestLog("CACM IDD", "Search CUSTOMER Sub Type value is "+ dbAcctSubType, Status.DONE); 
	 				 
				}
				 
				
				String dbfield9 = dataTable.getData("General_Data", "CLEANSED POSTAL CODE"); 
				if(!dbfield9.equals("No"))
				{
				String dbAcctRole=dataSet.getString("C_POSTAL_CD");
				
	 			System.out.println("CUSTOMER STATUS: "+dbAcctRole);
	 			driver.findElement(By.xpath(customerqueryXpath("CLEANSED POSTAL CODE"))).sendKeys(dbAcctRole);
	 			report.updateTestLog("CACM IDD", "Search CUSTOMER Status Type value is "+ dbAcctRole, Status.DONE); 
	 			
				}
				
				String dbfield = dataTable.getData("General_Data", "CUSTOMER STATUS"); 
				if(!dbfield.equals(""))
				{
				String dbAcctStatusDesc=dataSet.getString("CUST_STATUS");
	 			System.out.println("CUSTOMER Status Desc is: "+dbAcctStatusDesc);
	 			
	 		     driver.findElement(By.xpath(OthercustomerqueryXpath("CUSTOMER STATUS"))).sendKeys(dbAcctStatusDesc);
                 report.updateTestLog("CACM IDD", "Search CUSTOMER Status Desc value is "+ dbAcctStatusDesc, Status.DONE);  
				}
									
				 Thread.sleep(2000);
				String dbfield11 = dataTable.getData("General_Data", "SEGMENTATION"); 
				if(!dbfield11.equals("No"))
				{
				String dbAcctFuncType=dataSet.getString("SEGMENT_DESC");
	 			System.out.println("SEGMENTATION is: "+dbAcctFuncType);
	 			
	 			driver.findElement(By.xpath(OthercustomerqueryXpath("SEGMENTATION"))).sendKeys(dbAcctFuncType);
                report.updateTestLog("CACM IDD", "SEGMENTATION value is "+ dbAcctFuncType, Status.DONE);  
				}
				

		           
  			}
				
 		 }
			 catch (SQLException | InterruptedException e) {
				
				e.printStackTrace();
			} 
 		 
 		
 	 }
 	 
	  public String xpathforAcctSearchOthherAttributes(int i)
	     {
	    	 
	    	// WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT NAME')]/../div["+i+"]//input"));
	    	 String searchElmt= "//label[contains(text(),'ACCOUNT ID')]/following-sibling::div["+i+"]//input";
	    	 
	    	 return searchElmt;
	     }
	  
	  public void testdatainsertCust()throws Exception
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
	    			
	    			testDataforCustWithOtherAttributes();
	    		}
	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	 
	     } 
	  
	  
	  public String triangularButton(String subjectarea)
	     {
	    	 
	    	// WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT NAME')]/../div["+i+"]//input"));
	    	 String subjectareaXpath= "//*[text()='"+subjectarea+"']//..//child::img";
	    	 System.out.println("Expanded"+subjectarea);
	    	 return subjectareaXpath;
	     }
	  
	  public void verifyProductCategory() throws Exception
 	 
 	 
 	 {
		  //Expanding the Subject Area
		  driver.findElement(By.xpath(triangularButton("Product Category"))).click();
		  Thread.sleep(5000);
		  switchtoTableView("Product Category");
		 // Highlighting the UI value 
		     //CATEGORY_TYPE
		 	 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")));
		     Thread.sleep(3000);
		     String PRODUCT_CATEGORY_UI=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")).getText();
		     //SOURCE DELETE FLAG
		 	 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[3]//div")));
		     Thread.sleep(3000);
		     String SOURCE_DELETE_FLAG=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[3]//div")).getText();

		     
 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
 		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
 		 String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 		 System.out.println("Env input from xlsx "+stmt);
 		
 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_PRODUCT"); 
 		 if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
 		 {
 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
 		 }
 		 if (dbdatamode.equalsIgnoreCase("Auto"))
 		 {
 		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
 		 }
 		 System.out.println("Validation SQL query :"+sqlPassed);
 		 //Initializing DB values
 		 String PRODUCT_CATEGORY_DB ="";
 		 String SOURCE_DELETE_DB ="";
 		 
 		 try {
 			 
 			 Thread.sleep(10000);
				ResultSet dataSet=stmt.executeQuery(sqlPassed);
				Thread.sleep(10000);
				while (dataSet.next()) { 
				
				 
				PRODUCT_CATEGORY_DB=dataSet.getString("CUST_PROD_CAT_TYPE");
				System.out.println("PRODUCT_CATEGORY is: "+PRODUCT_CATEGORY_DB);
				SOURCE_DELETE_DB=dataSet.getString("SOURCE_DELETE_FLAG");
				System.out.println("SOURCE_DELETE_DB is: "+SOURCE_DELETE_DB);

 			}
			} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
 		 //Comparing CA_ID with DB and UI
 		 reportStatusGeneration("PRODUCT_CATEGORY",PRODUCT_CATEGORY_UI,PRODUCT_CATEGORY_DB); 
 		 
 		 // Comparing Customer Name with DB vis UI
 		 reportStatusGeneration("SOURCE_DELETE",SOURCE_DELETE_FLAG,SOURCE_DELETE_DB); 
 		 
 		 //Contracting the Subject Area
		  driver.findElement(By.xpath(triangularButton("Product Category"))).click();
 	 } 
	  
	  public void postalAddress() throws Exception
	 	 
	 	 
	 	 {
			  //Expanding the Subject Area
			  driver.findElement(By.xpath(triangularButton("Product Category"))).click();
			  Thread.sleep(5000);
			 // Highlighting the UI value 
			     //CATEGORY_TYPE
			 	 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")));
			     Thread.sleep(3000);
			     String PRODUCT_CATEGORY_UI=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")).getText();
			     //SOURCE DELETE FLAG
			 	 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[3]//div")));
			     Thread.sleep(3000);
			     String SOURCE_DELETE_FLAG=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[3]//div")).getText();

			     
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_PRODUCT"); 
	 		 if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
	 		 {
	 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
	 		 }
	 		 if (dbdatamode.equalsIgnoreCase("Auto"))
	 		 {
	 		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
	 		 }
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		 String PRODUCT_CATEGORY_DB ="";
	 		 String SOURCE_DELETE_DB ="";
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					
					 
					PRODUCT_CATEGORY_DB=dataSet.getString("CUST_PROD_CAT_TYPE");
					System.out.println("PRODUCT_CATEGORY is: "+PRODUCT_CATEGORY_DB);
					SOURCE_DELETE_DB=dataSet.getString("SOURCE_DELETE_FLAG");
					System.out.println("SOURCE_DELETE_DB is: "+SOURCE_DELETE_DB);

	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	 		 //Comparing CA_ID with DB and UI
	 		 reportStatusGeneration("PRODUCT_CATEGORY",PRODUCT_CATEGORY_UI,PRODUCT_CATEGORY_DB); 
	 		 
	 		 // Comparing Customer Name with DB vis UI
	 		 reportStatusGeneration("SOURCE_DELETE",SOURCE_DELETE_FLAG,SOURCE_DELETE_DB); 
	 		 
	 		 //Contracting the Subject Area
			  driver.findElement(By.xpath(triangularButton("Product Category"))).click();
	 	 } 
	  	 public WebElement xpathSubjArea(String attr)
    	 {
    		 WebElement subjElemt=null;
    		
	    	try{
	    		 subjElemt= driver.findElement(By.xpath("(//*[text()='"+attr+"'])[1]"));
	    		 
	    	   }
	    	catch(Exception e)
	    	{
	    		System.out.println("WebElement not found for xpathSubjArea "+attr);
	    		report.updateTestLog("CACM IDD", "WebElement not found for xpathSubjArea"+attr, Status.FAIL);	    	
	    	
	    	}
	    	
	    	return subjElemt;
    	 }
	  public void verifySubjAreaAcctPostAddr()
 	 {
 		 String uiAddressType="";
 		 String uiVerificationCode="";
 		 String uiAddressVerification="";
 		  
 		 String uiUseCleansedAddr="";
 		 String uiCleansedAddrLine1="";
 		  
 	
 		 try 
 		 {
 			 
 			 	//Expanding Customer Postal Code 
 			    driver.findElement(By.xpath(triangularButton("Customer Postal Address"))).click();
 			    Thread.sleep(10000);
		        System.out.println(" Data retrive from UI for Subject Area : Account Postal Address ");	    
		    	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		    	 switchtoTableView("Customer Postal Address");
		    	 List<WebElement> attrValueRow=driver.findElements(By.xpath("//*[text()='ADDRESS TYPE']/following::tr[1]/td/div/div/div[2]//tbody/tr"));
	    		 int rowCount=attrValueRow.size();
	    		 System.out.println(" Total Row count vaue  "+rowCount);
	    		 
		    	
		    	 for(int i=1;i<=rowCount;i++)	
		    	 {
		    		 
		    	System.out.println(" Value for row count  : "+i);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")));
		    		 
		    	uiAddressType=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")).getText();
		    	System.out.println("Address Type value is : "+uiAddressType);
		    	 
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")));
		    	uiVerificationCode=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")).getText();
		    	System.out.println("Primary Flag value is : "+uiVerificationCode);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")) );
		    	uiAddressVerification=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")).getText();
		    	System.out.println("Attention To value is : "+uiAddressVerification);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")) );
		    	uiUseCleansedAddr=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")).getText();
		    	System.out.println("Use Cleansed Address value is : "+uiUseCleansedAddr);
		    	 
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")) );
		    	uiCleansedAddrLine1=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
		    	System.out.println("Cleansed Address Line value is : "+uiCleansedAddrLine1);
		    	

			   	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
				 
				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
				 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_ADDRESS");
				 String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
				 if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
		 		 {
		 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
		 		 }
		 		 if (dbdatamode.equalsIgnoreCase("Auto"))
		 		 {
		 		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
		 		 }
				 sqlPassed=sqlPassed.replace("paddress_type1", uiAddressType);
				 sqlPassed=sqlPassed.replace("av_code1", uiVerificationCode);
				 sqlPassed=sqlPassed.replace("av_message1", uiAddressVerification);
				 sqlPassed=sqlPassed.replace("use_cleansed_flag1",uiUseCleansedAddr);
				 sqlPassed=sqlPassed.replace("c1_addr_line1",uiCleansedAddrLine1);
				 System.out.println("Query from parameter file is: "+sqlPassed);
				Thread.sleep(3000);
				
				ResultSet dataSet2=stmt.executeQuery(sqlPassed);
				Thread.sleep(6000);
				while (dataSet2.next()) { 
				
				 
				String dbaddress_Type=dataSet2.getString("PADDRESS_TYPE");
			    System.out.println("PADDRESS_TYPE is: "+dbaddress_Type);
				String	DBav_code1=dataSet2.getString("AV_CODE");
				System.out.println("AV_CODE is: "+DBav_code1);
				String	DBav_message1=dataSet2.getString("AV_MESSAGE");
				System.out.println("AV_CODE is: "+DBav_message1);
				String	DBuse_cleansed_flag1=dataSet2.getString("USE_CLEANSED_FLAG");
				System.out.println("AV_CODE is: "+DBuse_cleansed_flag1);
				String	DBc1_addr_line1=dataSet2.getString("C_ADDR_LINE_1");
				System.out.println("AV_CODE is: "+DBc1_addr_line1);
				//Comparing  DB and UI
		 		reportStatusGeneration("uiAddressType",uiAddressType,dbaddress_Type); 
		 		reportStatusGeneration("uiVerificationCode",uiVerificationCode,DBav_code1); 
		 		reportStatusGeneration("uiAddressVerification",uiAddressVerification,DBav_message1); 
		 		reportStatusGeneration("uiUseCleansedAddr",uiUseCleansedAddr,DBuse_cleansed_flag1); 
		 		reportStatusGeneration("uiCleansedAddrLine1",uiCleansedAddrLine1,DBc1_addr_line1);
		 	 	
				
		  
				}
		 }
		   //Contracting Customer Postal Code 
		    driver.findElement(By.xpath(triangularButton("Customer Postal Address"))).click();
			} 
 		 
 		 catch (SQLException | InterruptedException e)
 		 {
				
				e.printStackTrace();
				
				System.out.println("DB validation failed for Subject Area Account Post Address");
	    		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);	
			} 
		 
 	 }
	  public void verifyClassification() throws Exception
	 	 
	 	 
	 	 {
			  //Expanding the Subject Area
			  driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
			  Thread.sleep(5000);
			  switchtoTableView("Client Classification"); 
			 // Highlighting the UI value 
			  //Client_Classification
			 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")));
			  Thread.sleep(3000);
			 String Client_Classification=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")).getText();		     
	 		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
	 		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
	 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 		 String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CLASSIFICATION"); 
	 		 if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
	 		 {
	 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
	 		 }
	 		 if (dbdatamode.equalsIgnoreCase("Auto"))
	 		 {
	 		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
	 		 }
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		 String Client_Classification_DB ="";

	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
					while (dataSet.next()) { 
					
					 
					Client_Classification_DB=dataSet.getString("CUST_CLSF_TYPE");
					System.out.println("PRODUCT_CATEGORY is: "+Client_Classification_DB);


	 			}
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	 		 //Comparing CA_ID with DB and UI
	 		 	reportStatusGeneration("Client_Classification",Client_Classification,Client_Classification_DB); 

	 		 
	 		 //Contracting the Subject Area
	 		 	driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
	 	 } 
	  
	  // Validates values of attributes for Related Customer
 	 public void relatedAccount(){
 		 
 		 try
 		 {
 			//Contracting the Subject Area
	 		 	driver.findElement(By.xpath(triangularButton("Related Account(s)"))).click();
			    Thread.sleep(3000);
				switchtoTableView("Related Account(s)");
			    List<WebElement> attrValueRow=driver.findElements(By.xpath("(//*[text()='Related Account(s)']/following::table[6])[1]//tr"));
	    		 int rowCount=attrValueRow.size();
	    		 System.out.println(" Row Count: "+rowCount);
			    for (int i=0;i<rowCount;i++)
			    {
		    	//System.out.println(" Inside in Subject Area : "+xpathSubjArea("Related Customer").getText());    	
		    	System.out.println(" Data retrive from UI for Subject Area : Related Account ");
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath(getxpath(6+i,2))));
		    	String acct_Id=driver.findElement(By.xpath(getxpath(6+i,2))).getText();
		    	System.out.println("CA_ID value is "+acct_Id);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(getxpath(6+i,3))));
		    	String acct_name=driver.findElement(By.xpath(getxpath(6+i,3))).getText();
		    	System.out.println("Customer Name value is "+acct_name);
		    	

		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath(getxpath(6+i,4))) );
		    	String acct_type=driver.findElement(By.xpath(getxpath(6+i,4))).getText();
		    	System.out.println("Customer type value is "+acct_type);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(getxpath(6+i,5))));
		    	String acct_subtype=driver.findElement(By.xpath(getxpath(6+i,5))).getText();
		    	System.out.println("Customer sub type value is "+acct_subtype);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath(getxpath(6+i,6))) );
		    	String acct_role=driver.findElement(By.xpath(getxpath(6+i,6))).getText();
		    	System.out.println("Customer Legal Name value is "+acct_role);

		    	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
				 
				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_RELATED_ACCOUNT");
				// sqlPassed=sqlPassed.replace("account_id", uiAcctId);
		 		 String ca_id2 = dataTable.getData("General_Data", "CA_ID");
		 		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
		 		 String dbdatamode = dataTable.getData("General_Data", "Test Data Mode" );
		 		 if (dbdatamode.equalsIgnoreCase("Manual")||dbdatamode.equalsIgnoreCase(""))
		 		 {
		 		 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
		 		 }
		 		 if (dbdatamode.equalsIgnoreCase("Auto"))
		 		 {
		 		 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
		 		 }
		 		 sqlPassed=sqlPassed.replace("acct_id1",acct_Id);
				 System.out.println("Query from parameter file is: "+sqlPassed);
				 
				System.out.println(" Data retrived from Database for Subject Area : Related Customer for Account Name "+acct_name);
				Thread.sleep(3000);
				ResultSet dataSet4=stmt.executeQuery(sqlPassed);
				Thread.sleep(5000);
				
				
				
				while (dataSet4.next()) { 
	 				
	 				 
	 				String dbca_Id=dataSet4.getString("ACCT_ID");
	 			    System.out.println("acct_Id is: "+dbca_Id);
	 				String	dbacct_name=dataSet4.getString("ACCT_NAME");
	 				System.out.println("CUST_NAME is: "+dbacct_name);
	 				String	dbacct_type=dataSet4.getString("ACCT_TYPE");
	 				System.out.println("CUST_LEGAL_NAME is: "+dbacct_type);
	 				String dbacct_subtype=dataSet4.getString("ACCT_SUBTYPE");
	 			    System.out.println("acct_Id is: "+dbacct_subtype);
	 				String dbacct_role=dataSet4.getString("ACCT_ROLE");
	 			    System.out.println("acct_Id is: "+dbacct_role);
	 				
	 			 //Comparing CA_ID with DB and UI
		 		 	reportStatusGeneration("acct_Id",acct_Id,dbca_Id); 
		 		 	reportStatusGeneration("acct_name",acct_name,dbacct_name); 
		 		 	reportStatusGeneration("acct_type",acct_type,dbacct_type); 
		 		 	reportStatusGeneration("acct_subtype",acct_subtype,dbacct_subtype); 
		 		 	reportStatusGeneration("acct_role",acct_role,dbacct_role); 
					 
 		 }								
 		 }
 		 }
 		 catch(Exception e)
 		 {
 			 e.printStackTrace();
 			 
 			 System.out.println("DB validation failed for Subject Area Related Account");
	    		report.updateTestLog("CACM IDD", "DB validation failed for Related Account", Status.FAIL);	
 		 }


 	 
 	 }
 	   //Common Method to get XPATH based on arguments
 	   public String getxpath(int num1,int num2 ){
 		   String vatrxpath="";
 		   try{
 		   vatrxpath =  "(//*[text()='Related Account(s)']/following::tr"+"["+num1+"]"+")[1]//td"+"["+num2+"]" ;
 			   
 		    //vatrxpath =  "(//*[@class='x-trigger-cell'])"+"["+num+"]" ;
 		   }
 		   catch(Exception e){
 			   
 		   }
 		   return vatrxpath; 
 	   }
 	   
 	  public void verifySubjAreaCustPostAddr()
 	 {
 		 String uiAddressType="";
 		 String uiPrimaryFlag="";
 		 String uiAttentionTo="";
 		 String uiUseCleansedAddrFlag ="";
 		 String uiUseCleansedAddr="";
 		 String uiCleansedAddrLine1="";
 		 String orgCleansedAddrLine1="";
 		  
 	
 		 try {
 			 
 			 	
 			    //driver.findElement(By.xpath(triangularButton("Related Account(s)"))).click();
 			  // Thread.sleep(5000);
			    xpathSubjArea("Account Postal Address").click();		    
			    Thread.sleep(5000);
			    switchtoTableView("Account Postal Address"); 
			    List<WebElement> attrValueRow1=driver.findElements(By.xpath("(//*[text()='Related Account(s)']/following::table[6])[1]//tr"));
	    		 int rowCount1=attrValueRow1.size();
	    		 System.out.println(" Row Count: "+rowCount1);
			    for (int j=0;j<rowCount1;j++)
			    {
		    	//System.out.println(" Inside in Subject Area : "+xpathSubjArea("Related Customer").getText());    	
		    	System.out.println(" Data retrive from UI for Subject Area : Related Account ");
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath(getxpath(6+j,2))));
		    	String acct_Id=driver.findElement(By.xpath(getxpath(6+j,2))).getText();
		    	System.out.println("CA_ID value is "+acct_Id);
		    	driver.findElement(By.xpath(getxpath(6+j,2))).click();
		    	Thread.sleep(5000);
		    	 List<WebElement> attrValueRow=driver.findElements(By.xpath("//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td[2]/div"));
	    		 int rowCount=attrValueRow.size();
	    		 System.out.println(" Total Row count vaue  "+rowCount);
	    		 
		    	
		    	 for(int i=1;i<=rowCount;i++)	
		    	 {
		    		 
		    	System.out.println(" Value for row count  : "+i);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")));
		    		 
		    	uiAddressType=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")).getText();
		    	System.out.println("Address Type value is : "+uiAddressType);
		    	 
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")));
		    	uiPrimaryFlag=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")).getText();
		    	System.out.println("Primary Flag value is : "+uiPrimaryFlag);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")) );
		    	 uiAttentionTo=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")).getText();
		    	System.out.println("Attention To value is : "+uiAttentionTo);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")) );
		    	uiUseCleansedAddrFlag=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")).getText();
		    	System.out.println("Use Cleansed Address value is : "+uiUseCleansedAddrFlag);
		    	 
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")) );
		    	uiCleansedAddrLine1=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
		    	System.out.println("Cleansed Address Line value is : "+uiCleansedAddrLine1);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(7)+"]/div)["+i+"]")) );
		    	orgCleansedAddrLine1=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
		    	System.out.println("Original Address Line value is : "+orgCleansedAddrLine1);
		    	

			   	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
				 
				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaCustPostAddr");
				 sqlPassed=sqlPassed.replace("acct_id1", acct_Id);
				 sqlPassed=sqlPassed.replace("paddress_type1", uiAddressType);
				 sqlPassed=sqlPassed.replace("primary_flag1",uiPrimaryFlag);
				 sqlPassed=sqlPassed.replace("use_cleansed_flag1",uiUseCleansedAddrFlag);
				 sqlPassed=sqlPassed.replace("cleansed_addr_1",uiCleansedAddrLine1);
				 System.out.println("Query from parameter file is: "+sqlPassed);
				 
				System.out.println(" Data retrive from Database for Subject Area : Account Postal Address ");
				
				Thread.sleep(3000);
				
				ResultSet dataSet2=stmt.executeQuery(sqlPassed);
				Thread.sleep(6000);
				while (dataSet2.next()) { 
				
				 
				String dbaddress_Type=dataSet2.getString("PADDRESS_TYPE");
			    System.out.println("dbaddress is: "+dbaddress_Type);
				String	dbprimary_flag=dataSet2.getString("PRIMARY_FLAG");
				System.out.println("PRIMARY_FLAG is: "+dbprimary_flag);
				String dbAttentionTo=dataSet2.getString("ATTENTION_TO");
			    System.out.println("dbAttentionTo is: "+dbAttentionTo);
				String	dbUseCleansedAddrFlag=dataSet2.getString("USE_CLEANSED_FLAG");
				System.out.println("USE_CLEANSED_FLAG is: "+dbUseCleansedAddrFlag);
				String dbuiCleansedAddrLine1=dataSet2.getString("C_ADDR_LINE_1");
			    System.out.println("C_ADDR_LINE_1 is: "+dbuiCleansedAddrLine1);
				String	dborgCleansedAddrLine1=dataSet2.getString("ADDR_LINE_1");
				System.out.println("PRIMARY_FLAG ADDR_LINE_1: "+dborgCleansedAddrLine1);
				
				 //Comparing CA_ID with DB and UI
	 		 	reportStatusGeneration("uiAddressType",uiAddressType,dbaddress_Type); 
	 		 	reportStatusGeneration("uiPrimaryFlag",uiPrimaryFlag,dbprimary_flag); 
	 		 	reportStatusGeneration("uiAttentionTo",uiAttentionTo,dbAttentionTo); 
	 		 	reportStatusGeneration("uiUseCleansedAddrFlag",uiUseCleansedAddrFlag,dbUseCleansedAddrFlag); 
	 		 	reportStatusGeneration("uiCleansedAddrLine1",uiCleansedAddrLine1,dbuiCleansedAddrLine1);
	 		 	reportStatusGeneration("orgCleansedAddrLine1",orgCleansedAddrLine1,dborgCleansedAddrLine1);
	 		 	
				
				 System.out.println(" Execution of all the steps successfully done  in verifySubjAreaCustPostAddr ");
				 
				 Thread.sleep(3000);
				 
				
		   }
		 }
			    }
			} catch (SQLException | InterruptedException e) {
				
				e.printStackTrace();
				
				System.out.println("DB validation failed for Subject Area Account Post Address");
	    		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);	
			} 
 		driver.findElement(By.xpath(triangularButton("Related Account(s)"))).click();
 	 }
      public void logoutCACMIDD() throws Exception
      {
                      try
                      {
                                      driver.switchTo().defaultContent();
                                    //  driver.switchTo().frame("dashboard");
                                      Thread.sleep(5000);
                                      driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Dropdown)).click();
                                      driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Dropdown)).click();
                                      Thread.sleep(5000);
                                                    JavascriptExecutor executor = (JavascriptExecutor)driver;
                                                    executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Button)));
                                    //  driver.findElement(By.xpath(IDDRepo1.IDD_LogOut_Button)).click();
                                                    Thread.sleep(10000);
                                    // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Log In']")));
                                      report.updateTestLog("CACM IDD", "Log Out Successful ", Status.PASS);
                                      System.out.println("CACM IDD Log Out Successful");
                            
                      }
                      catch(Exception e )
                      {
                                      
                            report.updateTestLog("CACM IDD", "Log Out not Successful ", Status.PASS);
                           System.out.println("CACM IDD Log Out not Successful"); 
                           e.printStackTrace();
                      }
      	}
      
      public void fillCustomer_Segmentation() throws Exception 
      {
                     
      	String Customer_Segmentation = dataTable.getData("General_Data","Customer_Segmentation");
        
      	try {

      		driver.findElement(By.xpath(CACMIDDRepo.Customer_Segmentation)).click();
      		Thread.sleep(5000);
      		driver.findElement(By.xpath(CACMIDDRepo.Customer_Segmentation)).sendKeys(Customer_Segmentation);
      		report.updateTestLog("CACM IDD", "Customer Segmentation is entered", Status.PASS);
      		System.out.println("CACM Customer Run Search Data Entered");
      		}
      	catch (Exception e)
      		{
      		e.printStackTrace();
      		report.updateTestLog("CACM IDD", "Customer_Segmentation is not entered", Status.FAIL);          
      		}
      }

      public void dbValidationCMCMIDDCustomerSegmentation() throws InterruptedException
      {
      	String ui_Count="";
      	ui_Count=totalRecordCount();
      	String CUSTOMER_SEGMENTATION = dataTable.getData("General_Data", "Customer_Segmentation");
      	DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
      	String fieldval = CUSTOMER_SEGMENTATION;
      	System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	System.out.println("Env input from xlsx "+stmt);
      	
      	String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_SEGMENT"); 
      	sqlPassed=sqlPassed.replace("segval",fieldval);
      	System.out.println("Validation SQL query :"+sqlPassed);

      	String COUNT_DB="";

      	try {
      		Thread.sleep(10000);
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(10000);
      		while (dataSet.next()) { 
      		COUNT_DB =dataSet.getString("COUNT_DB");
      		System.out.println("COUNT is: "+ COUNT_DB);
      		}
      	} catch (SQLException | InterruptedException e) {
      		
      		e.printStackTrace();
      	} 
      	 //Comparing Count Values
      	 reportStatusGeneration("COUNT", ui_Count, COUNT_DB); 
      	 	 
      }

      public void fillCustomer_Status() throws Exception 
      {
                     
      	String Customer_Status = dataTable.getData("General_Data","Customer_Status");
        
      	try {

      		driver.findElement(By.xpath(CACMIDDRepo.Customer_Status)).click();
      		Thread.sleep(5000);
      		driver.findElement(By.xpath(CACMIDDRepo.Customer_Status)).sendKeys(Customer_Status);
      		report.updateTestLog("CACM IDD", "Customer Status is entered", Status.PASS);
      		System.out.println("CACM Customer Run Search Data Entered");
      		}
      	catch (Exception e)
      		{
      		e.printStackTrace();
      		report.updateTestLog("CACM IDD", "Customer_Status is not entered", Status.FAIL);          
      		}
      }

      public void dbValidationCMCMIDDCustomerStatus() throws InterruptedException
      {
      	String ui_Count="";
      	ui_Count=totalRecordCount();
      	String CUSTOMER_STATUS = dataTable.getData("General_Data", "Customer_Status");
      	DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
      	String fieldval = CUSTOMER_STATUS;
      	System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	System.out.println("Env input from xlsx "+stmt);
      	
      	String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_STATUS"); 
      	sqlPassed=sqlPassed.replace("statusval",fieldval);
      	System.out.println("Validation SQL query :"+sqlPassed);

      	String COUNT_DB="";

      	try {
      		Thread.sleep(10000);
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(10000);
      		while (dataSet.next()) { 
      		COUNT_DB =dataSet.getString("COUNT_DB");
      		System.out.println("COUNT is: "+ COUNT_DB);
      		}
      	} catch (SQLException | InterruptedException e) {
      		
      		e.printStackTrace();
      	} 
      	 //Comparing Count Values
      	 reportStatusGeneration("COUNT", ui_Count, COUNT_DB); 
      	 	 
      }

      public void fillCustomer_SubType() throws Exception 
      {
                     
      	String Customer_SubType = dataTable.getData("General_Data","Customer_SubType");
        
      	try {

      		driver.findElement(By.xpath(CACMIDDRepo.Customer_SubType)).click();
      		Thread.sleep(5000);
      		driver.findElement(By.xpath(CACMIDDRepo.Customer_SubType)).sendKeys(Customer_SubType);
      		report.updateTestLog("CACM IDD", "Customer SubType is entered", Status.PASS);
      		System.out.println("CACM Customer Run Search Data Entered");
      		}
      	catch (Exception e)
      		{
      		e.printStackTrace();
      		report.updateTestLog("CACM IDD", "Customer_SubType is not entered", Status.FAIL);          
      		}
      }

      public void dbValidationCMCMIDDCustomerSubType() throws InterruptedException
      {
      	String ui_Count="";
      	ui_Count=totalRecordCount();
      	String CUSTOMER_SUBTYPE = dataTable.getData("General_Data", "Customer_SubType");
      	DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
      	String fieldval = CUSTOMER_SUBTYPE;
      	System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	System.out.println("Env input from xlsx "+stmt);
      	
      	String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_SUBTYPE"); 
      	sqlPassed=sqlPassed.replace("subtypeval",fieldval);
      	System.out.println("Validation SQL query :"+sqlPassed);

      	String COUNT_DB="";

      	try {
      		Thread.sleep(10000);
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(10000);
      		while (dataSet.next()) { 
      		COUNT_DB =dataSet.getString("COUNT_DB");
      		System.out.println("COUNT is: "+ COUNT_DB);
      		}
      	} catch (SQLException | InterruptedException e) {
      		
      		e.printStackTrace();
      	} 
      	 //Comparing Count Values
      	 reportStatusGeneration("COUNT", ui_Count, COUNT_DB); 
      	 	 
      }

      public void fillCustomer_Type() throws Exception 
      {
                     
      	String Customer_Type = dataTable.getData("General_Data","Customer_Type");
        
      	try {

      		driver.findElement(By.xpath(CACMIDDRepo.Customer_Type)).click();
      		Thread.sleep(5000);
      		driver.findElement(By.xpath(CACMIDDRepo.Customer_Type)).sendKeys(Customer_Type);
      		report.updateTestLog("CACM IDD", "Customer Type is entered", Status.PASS);
      		System.out.println("CACM Customer Run Search Data Entered");
      		}
      	catch (Exception e)
      		{
      		e.printStackTrace();
      		report.updateTestLog("CACM IDD", "Customer_Type is not entered", Status.FAIL);          
      		}
      }

      public void dbValidationCMCMIDDCustomerType() throws InterruptedException
      {
      	String ui_Count="";
      	ui_Count=totalRecordCount();
      	String CUSTOMER_TYPE = dataTable.getData("General_Data", "Customer_Type");
      	DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
      	String fieldval = CUSTOMER_TYPE;
      	System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	System.out.println("Env input from xlsx "+stmt);
      	
      	String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_TYPE"); 
      	sqlPassed=sqlPassed.replace("typeval",fieldval);
      	System.out.println("Validation SQL query :"+sqlPassed);

      	String COUNT_DB="";

      	try {
      		Thread.sleep(10000);
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(10000);
      		while (dataSet.next()) { 
      		COUNT_DB =dataSet.getString("COUNT_DB");
      		System.out.println("COUNT is: "+ COUNT_DB);
      		}
      	} catch (SQLException | InterruptedException e) {
      		
      		e.printStackTrace();
      	} 
      	 //Comparing Count Values
      	 reportStatusGeneration("COUNT", ui_Count, COUNT_DB); 
      	 	 
      }


      public void fillCustomer_PostalCode() throws Exception 
      {
                     
      	String Customer_PostalCode = dataTable.getData("General_Data","Customer_PostalCode");
        
      	try {

      		driver.findElement(By.xpath(CACMIDDRepo.Customer_PostalCode)).click();
      		Thread.sleep(5000);
      		driver.findElement(By.xpath(CACMIDDRepo.Customer_PostalCode)).sendKeys(Customer_PostalCode);
      		report.updateTestLog("CACM IDD", "Customer Postal Code is entered", Status.PASS);
      		System.out.println("CACM Customer Run Search Data Entered");
      		}
      	catch (Exception e)
      		{
      		e.printStackTrace();
      		report.updateTestLog("CACM IDD", "Customer_Postal Code is not entered", Status.FAIL);          
      		}
      }

      public void dbValidationCMCMIDDCustomerPostalCode() throws InterruptedException
      {
      	String ui_Count="";
      	ui_Count=totalRecordCount();
      	String CUSTOMER_POSTALCODE = dataTable.getData("General_Data", "Customer_PostalCode");
      	DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
      	String fieldval = CUSTOMER_POSTALCODE;
      	System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	System.out.println("Env input from xlsx "+stmt);
      	
      	String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_POSTALCODE"); 
      	sqlPassed=sqlPassed.replace("postalcodeval",fieldval);
      	System.out.println("Validation SQL query :"+sqlPassed);

      	String COUNT_DB="";

      	try {
      		Thread.sleep(20000);
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(20000);
      		while (dataSet.next()) { 
      		COUNT_DB =dataSet.getString("COUNT_DB");
      		System.out.println("COUNT is: "+ COUNT_DB);
      		}
      	} catch (SQLException | InterruptedException e) {
      		
      		e.printStackTrace();
      	} 
      	 //Comparing Count Values
      	 reportStatusGeneration("COUNT", ui_Count, COUNT_DB); 
      	 	 
      }
      
      public String  identificationTestDataforCust()


      {
      	 
      	 String	 CA_ID="";
      	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
      	 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	 
      	 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("ident_TestData_Cust_Rel");
      	 
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

      //This method validates the Customer Relationships
      public void verifySubjAreaRelationships() 
      {
      	 String uiHierarchyType="";
      	 String uiRelatedEntity1="";
      	 String uiRelationshipType="";
      	 String uiRelatedEntity2="";
      	 String uiRelationshipStartDate="";
      	 String uiRelationshipEndDate="";
      	 String CA_ID_UI=uiIDDval("CA ID");
      	  

      	 try {
      		 
      		 
      	    xpathSubjArea("Relationships").click();
          	System.out.println(" Inside in Subject Area : "+xpathSubjArea("Relationships").getText());
          	
          	System.out.println(" Data retrive from UI for Subject Area : Relationships ");
          
          	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
          	switchtoTableView("Relationships");  
          	 List<WebElement> attrValueRow=driver.findElements(By.xpath("//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td[1]/div"));
      		 int rowCount=attrValueRow.size();
      		 System.out.println(" Total Row count vaue  "+rowCount);
      		 
          	
          	 for(int i=1;i<=rowCount;i++)	
          	 {
          		 
          	System.out.println(" Row count vaue  "+i);
          	
          	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(1)+"]/div)["+i+"]")));
          	uiHierarchyType=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(1)+"]/div)["+i+"]")).getText();
          	System.out.println("Hierarchy Type value is : "+uiHierarchyType);
          	
          	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")));
          	uiRelatedEntity1=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")).getText();
          	System.out.println("Related Entity1 value is : "+uiRelatedEntity1);
          	
          	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")));
          	uiRelationshipType=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")).getText();
          	System.out.println("Relationship Type value is : "+uiRelationshipType);
          	
          	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")));
          	uiRelatedEntity2=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")).getText();
          	System.out.println("Related Entity2 value is : "+uiRelatedEntity2);
          	
          	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")));
          	uiRelationshipStartDate=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")).getText();
          	System.out.println("Relationship StartDate value is : "+uiRelationshipStartDate);
          	
          	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")));
          	uiRelationshipEndDate=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
          	System.out.println("Relationship EndDate value is : "+uiRelationshipEndDate);
          	

      	   	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
      		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
      		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      		 
      		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_RELATIONSHIPS");
      		 sqlPassed=sqlPassed.replace("ca_idr", CA_ID_UI);
      		 //sqlPassed=sqlPassed.replace("paddress_type1", uiAddressType);
      		 
      		 System.out.println("Query from parameter file is: "+sqlPassed);
      		 
      		System.out.println(" Data retrive from Database for Subject Area : Relationships ");
      		
      		Thread.sleep(3000);
      		
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(6000);
      		while (dataSet.next()) { 
      		
      		 
      		String dbhierarchy_cd=dataSet.getString("HIERARCHY_CD");
      	    System.out.println("HIERARCHY_CD is: "+dbhierarchy_cd);
      		String	dbcust_name1=dataSet.getString("CUST_NAME1");
      		System.out.println("CUST_NAME1 is: "+dbcust_name1);
      		
      		if(dbcust_name1 == null)
      		{
      			dbcust_name1="";
      		}
      		
      		String dbcust_name2=dataSet.getString("CUST_NAME2");
      		
      		if(dbcust_name2 == null)
      		{
      			dbcust_name2="";
      		}
      		
      		System.out.println("CUST_NAME2 is: "+dbcust_name2);
      		String dbperiod_StartDate=dataSet.getString("PERIOD_START_DATE");
      		
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

      		String dbperiod_EndDate=dataSet.getString("PERIOD_END_DATE");
      		
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

      		
      		GF.reportStatusGeneration("HIERARCHY_CD",uiHierarchyType,dbhierarchy_cd);
      		GF.reportStatusGeneration("CUST_NAME1",uiRelatedEntity1,dbcust_name1);
      		GF.reportStatusGeneration("CUST_NAME2",uiRelatedEntity2,dbcust_name2);
      		GF.reportStatusGeneration("PERIOD_START_DATE",uiRelationshipStartDate,dbperiod_StartDate);
      		GF.reportStatusGeneration("PERIOD_END_DATE",uiRelationshipEndDate,dbperiod_EndDate);
      		
      		 System.out.println(" Execution of all the steps successfully done  in verifySubjAreaRelationships ");
      		 
      		 Thread.sleep(3000);
      		
         }
       }
         
      	} catch (Exception e) {
      		
      		e.printStackTrace();
      	} 
       
      	 
      }
      public String totalRecordCount() throws InterruptedException
      {
         String Recodcount = "";
         try { 
                Thread.sleep(5000);
        
                String pagecount = driver.findElement(By.xpath(CACMIDDRepo.pagecount)).getText();
          if (!pagecount.contains("+")) {
               String pagecount1 =  driver.findElement(By.xpath(CACMIDDRepo.Total_Records_Field1)).getText();
               Recodcount =  pagecount1.substring(14, pagecount1.length());
               System.out.println(Recodcount); 
          }
          else {
               Thread.sleep(5000);
               driver.findElement(By.xpath(CACMIDDRepo.Total_Records_Field2)).click();
               Thread.sleep(30000);
               String pagecount2 =  driver.findElement(By.xpath(CACMIDDRepo.Total_Records_Field3)).getText();
               System.out.println(pagecount2);
               Thread.sleep(5000);
               Recodcount =  pagecount2.substring(14, pagecount2.length());
               Thread.sleep(5000);
               System.out.println(Recodcount); 
          }
          
          
          }
         
         catch(Exception e)
         {
         System.out.println(e);    
         }

          return Recodcount;
          
      } 
      public void dbval_Customer_Generic() throws Exception


      {
      	 
      // Highlighting the UI value 
          //CA_ID
      	 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CA ID"))));
          Thread.sleep(3000);
          String CA_ID_UI=uiIDDval("CA ID");
          //CUST_NAME
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER NAME"))));
          Thread.sleep(3000);
          String CUST_NAME_UI=uiIDDval("CUSTOMER NAME");
          //CUST_LEGAL_NAME_DB
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER LEGAL NAME"))));
          Thread.sleep(3000);
          String CUST_LEGAL_NAME_UI=uiIDDval("CUSTOMER LEGAL NAME");
          //CUST_TYPE_DB
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER TYPE"))));
          Thread.sleep(3000);
          String CUST_TYPE_UI=uiIDDval("CUSTOMER TYPE");
          //FRANCHISE_FLAG
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("AUTO FRANCHISE INDICATOR"))));
          Thread.sleep(3000);
          String FRANCHISE_FLAG_UI=uiIDDval("AUTO FRANCHISE INDICATOR");
          //CUST_STATUS
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER STATUS"))));
          Thread.sleep(3000);
          String CUST_STATUS_UI=uiIDDval("CUSTOMER STATUS");
          //SOURCE_DELETE_FLAG_DB
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("SOURCE DELETE FLAG"))));
          Thread.sleep(3000);
          String SOURCE_DELETE_FLAG_UI=uiIDDval("SOURCE DELETE FLAG");
          //SEGMENT_DESC_DB
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("SEGMENTATION"))));
          Thread.sleep(3000);
          String SEGMENT_DESC_UI=uiIDDval("SEGMENTATION");
          //CUST_SUBTYPE_DB
          ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath(uiIDxPath("CUSTOMER SUBTYPE"))));
          Thread.sleep(3000);
          String CUSTOMER_SUBTYPE_UI=uiIDDval("CUSTOMER SUBTYPE");
          
      	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
      	 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
      	 String ca_id2 = CA_ID_UI;
      	 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
      	 
      	 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
      	 System.out.println("Env input from xlsx "+stmt);
      	
      	 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CA_ID"); 
      	 sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
      	 System.out.println("Validation SQL query :"+sqlPassed);
      	 //Initializing DB values
      	 String CA_ID_DB ="";
      	 String CUST_NAME_DB ="";
      	 String CUST_LEGAL_NAME_DB="";
      	 String  CUST_TYPE_DB="";
      	 String CUST_SUBTYPE_DB="";
      	 String FRANCHISE_FLAG_DB="";
      	 String CUST_STATUS_DB="";
      	 String SOURCE_DELETE_FLAG_DB="";
      	 String SEGMENT_DESC_DB="";
      	 
      	 try {
      		 
      		 Thread.sleep(10000);
      		ResultSet dataSet=stmt.executeQuery(sqlPassed);
      		Thread.sleep(10000);
      		while (dataSet.next()) { 
      		
      		 
      		 CA_ID_DB=dataSet.getString("CA_ID");
      		System.out.println("ROWID_OBJECT is: "+CA_ID_DB);
      		CUST_NAME_DB=dataSet.getString("CUST_NAME");
      		System.out.println("ROWID_OBJECT is: "+CUST_NAME_DB);
      		CUST_LEGAL_NAME_DB=dataSet.getString("CUST_LEGAL_NAME");
      		System.out.println("ROWID_OBJECT is: "+CUST_LEGAL_NAME_DB);
      		CUST_TYPE_DB=dataSet.getString("CUST_TYPE");
      		System.out.println("ROWID_OBJECT is: "+CUST_TYPE_DB);
      		CUST_SUBTYPE_DB =dataSet.getString("CUST_SUBTYPE");
      		System.out.println("ROWID_OBJECT is: "+CUST_SUBTYPE_DB);
      		FRANCHISE_FLAG_DB=dataSet.getString("FRANCHISE_FLAG");
      		System.out.println("ROWID_OBJECT is: "+FRANCHISE_FLAG_DB);
      		CUST_STATUS_DB =dataSet.getString("CUST_STATUS");
      		System.out.println("ROWID_OBJECT is: "+CUST_STATUS_DB);
      		SOURCE_DELETE_FLAG_DB=dataSet.getString("SOURCE_DELETE_FLAG");
      		System.out.println("ROWID_OBJECT is: "+SOURCE_DELETE_FLAG_DB);
      		SEGMENT_DESC_DB=dataSet.getString("SEGMENT_DESC");
      		System.out.println("ROWID_OBJECT is: "+SEGMENT_DESC_DB);
      		}
      	} catch (SQLException | InterruptedException e) {
      		// TODO Auto-generated catch block
      		e.printStackTrace();
      	} 
      	 //Comparing CA_ID with DB and UI
      	 reportStatusGeneration("CA_ID",CA_ID_UI,CA_ID_DB); 
      	 
      	 // Comparing Customer Name with DB vis UI
      	 reportStatusGeneration("CUST_NAME",CUST_NAME_UI,CUST_NAME_DB); 
      	 
      	 //Comparing Customer legal name
      	 reportStatusGeneration("CUST_LEGAL_NAME",CUST_LEGAL_NAME_UI,CUST_LEGAL_NAME_DB); 
      	 
      	 //CUST TYPE
      	 reportStatusGeneration("CUST TYPE",CUST_TYPE_UI,CUST_TYPE_DB); 
      	 //	AUTO FRANCHISE INDICATOR
      	 reportStatusGeneration("FRANCHISE FLAG",FRANCHISE_FLAG_UI,FRANCHISE_FLAG_DB); 
      	 //CUST_STATUS_UI
      	 reportStatusGeneration("CUST STATUS",CUST_STATUS_UI,CUST_STATUS_DB); 
      	 //SOURCE DELETE FLAG
      	 reportStatusGeneration("SOURCE DELETE FLAG",SOURCE_DELETE_FLAG_UI,SOURCE_DELETE_FLAG_DB);
      	 //SEGMENT_DESC_DB
      	 reportStatusGeneration("SEGMENT_DESC",SEGMENT_DESC_UI,SEGMENT_DESC_DB);
      	 //CUSTOMER SUBTYPE"
      	 reportStatusGeneration("CUSTOMER SUBTYPE",CUSTOMER_SUBTYPE_UI,CUST_SUBTYPE_DB);
      }

      public void testdatainsertCustRel()throws Exception
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
      public void searchRandomCustomer()
      {   
     	 CA_ID=identificationTestDataforCustRel();

     	 try {
     		
     		 driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_CA_ID_Under_Search)).sendKeys(CA_ID);
     		 System.out.println("CA ID is taken from identificationTestDataforCust() ");
     		 Thread.sleep(3000);
     		// driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(Keys.ENTER);
     	} catch (InterruptedException e) {
     		
     		e.printStackTrace();
     	} 
     	
     	 report.updateTestLog("CACM IDD", "Search Account value is "+ CA_ID, Status.PASS); 
      }

public String  identificationTestDataforCustRel()


{
	 
	 String	 CA_ID="";
	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
	 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	 
	 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("ident_TestData_Cust_Rel");
	 
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
public void verifyRelationships() 
 {
        String uiHierarchyType="";
        String uiRelatedEntity1="";
        String uiRelationshipType="";
        String uiRelatedEntity2="";
        String uiRelationshipStartDate="";
        String uiRelationshipEndDate="";
        String CA_ID_UI=uiIDDval("CA ID");
          

        try {
               
        	     
        	driver.findElement(By.xpath(triangularButton("Relationships"))).click();
       // System.out.println(" Inside in Subject Area : "+xpathSubjArea("Relationships").getText());
        
       // System.out.println(" Data retrive from UI for Subject Area : Relationships ");
     
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        
         List<WebElement> attrValueRow=driver.findElements(By.xpath("//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td[1]/div"));
               int rowCount=attrValueRow.size();
               System.out.println(" Total Row count vaue  "+rowCount);
               switchtoTableView("Relationships");  
        
         for(int i=1;i<=rowCount;i++)    
         {
                
        System.out.println(" Row count vaue  "+i);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(1)+"]/div)["+i+"]")));
        uiHierarchyType=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(1)+"]/div)["+i+"]")).getText();
        System.out.println("Hierarchy Type value is : "+uiHierarchyType);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")));
        uiRelatedEntity1=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(2)+"]/div)["+i+"]")).getText();
        System.out.println("Related Entity1 value is : "+uiRelatedEntity1);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")));
        uiRelationshipType=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(3)+"]/div)["+i+"]")).getText();
        System.out.println("Relationship Type value is : "+uiRelationshipType);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")));
        uiRelatedEntity2=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")).getText();
        System.out.println("Related Entity2 value is : "+uiRelatedEntity2);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")));
        uiRelationshipStartDate=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")).getText();
        System.out.println("Relationship StartDate value is : "+uiRelationshipStartDate);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")));
        uiRelationshipEndDate=driver.findElement(By.xpath("(//*[text()='Hierarchy Type']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
        System.out.println("Relationship EndDate value is : "+uiRelationshipEndDate);
        

                DbEnvConnection test=new DbEnvConnection(scriptHelper);
               String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
                System.out.println("Env input from xlsx "+dbEnvInputXlsx);
               
                Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
               String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUST_RELATIONSHIPS_SUBJECT");
               
               sqlPassed=sqlPassed.replace("ca_idr", CA_ID_UI);
               sqlPassed=sqlPassed.replace("CUST_NAME_FIRST", uiRelatedEntity1);
               sqlPassed=sqlPassed.replace("CUST_NAME_SECOND", uiRelatedEntity2);
               
               //sqlPassed=sqlPassed.replace("paddress_type1", uiAddressType);
               
                System.out.println("Query from parameter file is: "+sqlPassed);
               
               System.out.println(" Data retrive from Database for Subject Area : Relationships ");
               
               Thread.sleep(3000);
               
               ResultSet dataSet=stmt.executeQuery(sqlPassed);
               Thread.sleep(6000);
               while (dataSet.next()) { 
               
                
               String dbhierarchy_cd=dataSet.getString("HIERARCHY_CD");
               System.out.println("HIERARCHY_CD is: "+dbhierarchy_cd);
               String dbcust_name1=dataSet.getString("CUST_NAME1");
               System.out.println("CUST_NAME1 is: "+dbcust_name1);
               
               if(dbcust_name1 == null)
               {
                      dbcust_name1="";
               }
               
               String dbcust_name2=dataSet.getString("CUST_NAME2");
               
               if(dbcust_name2 == null)
               {
                      dbcust_name2="";
               }
               
               System.out.println("CUST_NAME2 is: "+dbcust_name2);
               String dbperiod_StartDate=dataSet.getString("PERIOD_START_DATE");
               
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

               String dbperiod_EndDate=dataSet.getString("PERIOD_END_DATE");
               
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

               
               GF.reportStatusGeneration("HIERARCHY_CD",uiHierarchyType,dbhierarchy_cd);
               GF.reportStatusGeneration("CUST_NAME1",uiRelatedEntity1,dbcust_name1);
               GF.reportStatusGeneration("CUST_NAME2",uiRelatedEntity2,dbcust_name2);
               GF.reportStatusGeneration("PERIOD_START_DATE",uiRelationshipStartDate,dbperiod_StartDate);
               GF.reportStatusGeneration("PERIOD_END_DATE",uiRelationshipEndDate,dbperiod_EndDate);
               
               System.out.println(" Execution of all the steps successfully done  in verifySubjAreaRelationships ");
               
                Thread.sleep(3000);
               
    }
 }
         //Contracting Relationships
         driver.findElement(By.xpath(triangularButton("Relationships"))).click();
        } 
        
        catch (Exception e) {
               
               e.printStackTrace();
        } 
  
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
}