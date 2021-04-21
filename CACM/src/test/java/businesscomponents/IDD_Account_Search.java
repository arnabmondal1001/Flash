package businesscomponents;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.util.frameworkutils.Status;
import objectrepositories.CACMIDDRepo;
import objectrepositories.Customer_Search;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class IDD_Account_Search extends ReusableLibrary{
	
	Random rand = new Random(); 
	WebDriverWait wait = new WebDriverWait(driver, 100);
	static String accountId="";
	public IDD_Account_Search(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	
	GenericFunctions GF = new GenericFunctions(scriptHelper);
	WebElement element;
	static String dealerId="";

	//  Keyword will open CACM IDD applications  site with valid credentials. The key word will Generates reports where ever applicable.
	public void loginCACM() throws Exception 
	{

		String env = dataTable.getData("General_Data", "Environment");
		driver.manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);	
		ArrayList<String> al =url_list(env) ;
		String strAppURL=al.get(0);
		String uid=al.get(1);
		String pwd=al.get(2);
		driver.get(strAppURL);
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 500);
		try
		{
			
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Log In']")));
		        driver.findElement(By.xpath(Customer_Search.IDD_UI_TXT_LOGIN_USER_ID)).sendKeys(uid);
		        driver.findElement(By.xpath(Customer_Search.IDD_UI_TXT_LOGIN_PWD)).sendKeys(pwd);
		        report.updateTestLog("CACM IDD", "Application is launched successfully ", Status.SCREENSHOT);
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
    			System.out.println(" ************************Opened Menu Item ************************************ ");
    			
    			
    			String Menuitem = dataTable.getData("General_Data", "Menuitem");
    			 driver.findElement(By.xpath("//*[text()='"+Menuitem+"']")).click();
    		     report.updateTestLog("CACM IDD", "Opened Menu Item"+Menuitem, Status.PASS);
    		     Thread.sleep(5000);
    		}
    	 //This Keyword identifies the “AccountQuery” and click on it in saved Queries.
    		
    		public void savedQueries() throws Exception {
    	           
    			
    			System.out.println(" ************************Saved Queries  ************************************ ");
    			
    			String savedQueries = dataTable.getData("General_Data", "savedQueries");
    	           try {
    	                  
    	                  driver.switchTo().frame("data");
    	                  Thread.sleep(5000);
    	    
    	                  if (savedQueries.equalsIgnoreCase("Account Query")) {
    	                        driver.findElement(By.xpath (CACMIDDRepo.accountQuerySearchtxt)).click(); 
    	                        
    	                  }
    	                //  CACM IDD Customer Query
    	                  else 
    	                  {
    	                  driver.findElement(By.xpath ("//*[text()='"+savedQueries+"']")).click();
    	                  }
    	                  Thread.sleep(5000);
    	                 //Click on Open Queries
    	                       driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_OpenQueries)).click();
    	                 Thread.sleep(10000);
    	                 System.out.println("Saved Query Opened"+savedQueries);
    	                 report.updateTestLog("CACM IDD", "Saved Query opened"+savedQueries, Status.PASS);
    	           }
    	           catch (Exception e) {
    	                 
    	                  System.out.println("Saved Query not Opened"+savedQueries);
    	                  report.updateTestLog("CACM IDD", "Saved Query not opened"+savedQueries, Status.FAIL);
    	                  e.printStackTrace();
    	           }
    	    }
    	   
    	// Select the mode to identify the test data by using manually/Automation
         public void modeOfExecTestDataforAcct()
         {
        	
        	 System.out.println(" ************************ Mode of Test  ************************************ ");
        	 
        	 String excelMode = dataTable.getData("General_Data", "Test Data Mode"); 
        	 
        	 if(excelMode.equalsIgnoreCase("Manual"))
        	 {
        		  accountId = dataTable.getData("General_Data", "ACCOUNT ID"); 
        		  
        		  searchAccountforManualAcct(accountId);
        	 }
        	 
        	 else
        	 {
        		 searchAccount();
        	 }
         }

    	//This method executes account query and Identifies test data - the account Id 
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
 				 System.out.println("Waiting for executing of the query");
 				Thread.sleep(20000);
 				while (dataSet.next()) { 
 			    ACCT_ID=dataSet.getString("ACCT_ID");
 				System.out.println("Account ID is: "+ACCT_ID);
     			}
 			} catch (SQLException | InterruptedException e) {
 				
 			
 				System.out.println("Test Data for account not found");
                report.updateTestLog("CACM IDD", "Test Data for account not found", Status.FAIL);
            	e.printStackTrace();
 			} 
    		 
    		return ACCT_ID;
    	 }
    	 
    	 
    	 
    	 
    	 // Identify the webElements to insert values in Account Search
    	     public WebElement xpathforAcctSearchOthherAttributes(int i)
    	     {
    	    	 
    	    WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT ID')]/following-sibling::div["+i+"]//input"));
    	    	 
    	    	 return searchElmt;
    	     }
    	     
    	     // To identify test data by executing the query for Account with other attributes
	    	 public void  identfTDforAcctWithOtherAttrs()
	    	 
	    	 
	    	 {
	    	 System.out.println(" ************************ Identification Test data for Account with Others Attributes ************************************ ");
	    	 
	    	 
	    		 
	    		 String	 dbAcctId="";
	    		 String	 dbAcctNm="";
	    		 String	 dbAcctType="";
	    		 String dbAcctSubType="";
	    		 String dbCountryCdDesc="";
	    		
	    		 DbEnvConnection test=new DbEnvConnection(scriptHelper);
	    		 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
	    		 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
	    		 
	    		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	    		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("Ident2_TestData_acct");
	    		 
	    		 System.out.println("Query from parameter file is: "+sqlPassed);
	    		 
	    		 
	    		 try {
	    			 
	    			 
	 				ResultSet dataSet=stmt.executeQuery(sqlPassed);
	 				Thread.sleep(13000);
	 				
	 				
	 				
	 				while (dataSet.next()) { 
	 					
	 					
	 					
	 				String dbfield1 = dataTable.getData("General_Data", "ACCOUNT ID"); 
	 				if(!dbfield1.equals(""))
	 				{
	 					 dbAcctId=dataSet.getString(dbfield1);
	 	 				 System.out.println("Account ID is: "+dbAcctId);
	 	 				 
	 	 				 
	 	 				 
	 	 				xpathforAcctSearchOthherAttributes(1).sendKeys(dbAcctId);
	 	 				report.updateTestLog("CACM IDD", "Search Account ID value is "+ dbAcctId, Status.DONE); 
	 	 				 
	 				}
	 				
	 				
	 				String dbfield2 = dataTable.getData("General_Data", "ACCOUNT NAME"); 
	 				if(!dbfield2.equals(""))
	 				{
	 					 dbAcctNm=dataSet.getString(dbfield2);
	 	 				 System.out.println("Account Name is: "+dbAcctNm);
	 	 				 
	 	 				driver.findElement(By.xpath(CACMIDDRepo.searchAcctNm)).sendKeys(dbAcctNm);
	 	 				report.updateTestLog("CACM IDD", "Search Account Name value is "+ dbAcctNm, Status.DONE); 
	 	 				 
	 				}
	 				
	 				String dbfield3 = dataTable.getData("General_Data", "CLEANSED ADDRESS LINE 1"); 
	 				if(!dbfield3.equals(""))
	 				{
	 				String	 dbCleansedAddrLine1=dataSet.getString(dbfield3);
	 	 				 System.out.println("CLEANSED ADDRESS LINE 1 is: "+dbCleansedAddrLine1);
	 	 				 
	 	 				xpathforAcctSearchOthherAttributes(3).sendKeys(dbCleansedAddrLine1);;
	 	 				report.updateTestLog("CACM IDD", "Search CLEANSED ADDRESS LINE 1 value is "+ dbCleansedAddrLine1, Status.DONE); 
	 				}
	 				
	 				String dbfield4 = dataTable.getData("General_Data", "CLEANSED COUNTRY"); 
	 				if(!dbfield4.equals(""))
	 				{
	 					 dbCountryCdDesc=dataSet.getString(dbfield4);
	 	 				 System.out.println("Country Code Desc is: "+dbCountryCdDesc);

	 	 				driver.findElement(By.xpath(CACMIDDRepo.searchCountryCdDesc)).sendKeys(dbCountryCdDesc);
	 	 				report.updateTestLog("CACM IDD", "Search Country Code Desc value is "+ dbCountryCdDesc, Status.DONE); 
	 	 				 
	 				}
	 				
	 				String dbfield5 = dataTable.getData("General_Data", "CLEANSED PROVINCE"); 
	 				
	 				if(!dbfield5.equals(""))
	 				{
	 				String	dbProvinceCdDesc=dataSet.getString(dbfield5);
	 	 				 System.out.println("Province Code is: "+dbProvinceCdDesc);
	 	 				 
	 	 				driver.findElement(By.xpath(CACMIDDRepo.searchProvinceCdDesc)).sendKeys(dbProvinceCdDesc);
	 	 				report.updateTestLog("CACM IDD", "Search Province Code value is "+ dbProvinceCdDesc, Status.DONE); 
	 				}
	 				
	 				String dbfield6 = dataTable.getData("General_Data", "CLEANSED CITY"); 
	 				
	 				if(!dbfield6.equals(""))
	 				{
	 					String dbCleansedCity=dataSet.getString(dbfield6);
	 	 				 System.out.println("Cleansed City is: "+dbCleansedCity);
	 	 				 
	 	 				xpathforAcctSearchOthherAttributes(6).sendKeys(dbCleansedCity);;
	 	 				report.updateTestLog("CACM IDD", "Search Cleansed City value is "+ dbCleansedCity, Status.DONE);  
	 				}
	 				
	 				String dbfield7 = dataTable.getData("General_Data", "ACCOUNT TYPE"); 
	 				if(!dbfield7.equals(""))
	 				{
	 				 dbAcctType=dataSet.getString(dbfield7);
	 	 			System.out.println("Account Type is: "+dbAcctType);
	 	 			driver.findElement(By.xpath(CACMIDDRepo.searchAcctType1)).click();
	 	 			driver.findElement(By.xpath(CACMIDDRepo.searchAcctType1)).sendKeys(dbAcctType);
	 	 			report.updateTestLog("CACM IDD", "Search Account Type value is "+ dbAcctType, Status.DONE);  
	 	 			
	 				}

	 	 			String dbfield8 = dataTable.getData("General_Data", "ACCOUNT SUBTYPE"); 
	 	 			Thread.sleep(3000);
	 				if(!dbfield8.equals(""))
	 				{
	 				 dbAcctSubType=dataSet.getString(dbfield8);
	 	 			System.out.println("Account Sub Type is: "+dbAcctSubType);
	 	 			
	 	 	
	 	 			Thread.sleep(2000);
	 	 			driver.findElement(By.xpath(CACMIDDRepo.searchAcctSubType)).sendKeys(dbAcctSubType.trim());
	 	 			
	 	 			report.updateTestLog("CACM IDD", "Search Account Sub Type value is "+ dbAcctSubType, Status.DONE); 
	 	 			Thread.sleep(2000);	 
	 				}
	 				 
	 				
	 				String dbfield9 = dataTable.getData("General_Data", "ACCOUNT ROLE"); 
	 				if(!dbfield9.equals(""))
	 				{
	 				String dbAcctRole=dataSet.getString(dbfield9);
	 	 			System.out.println("Account Role is: "+dbAcctRole);
	 	 			
	 	 			if(dbAcctRole !=null)
	 	 			{
	 	 				
	 	 				xpathforAcctSearchOthherAttributes(9).sendKeys(dbAcctRole);
	 	 				report.updateTestLog("CACM IDD", "Search Account Role value is "+ dbAcctRole, Status.DONE); 
	 	 			}
	 	 			
	 	 		


	 				}
	 				
	 				String dbfield = dataTable.getData("General_Data", "ACCOUNT STATUS"); 
	 				if(!dbfield.equals(""))
	 				{
	 				String dbAcctStatusDesc=dataSet.getString(dbfield);
	 	 			System.out.println("Account Status Desc is: "+dbAcctStatusDesc);
	 	 			
                     driver.findElement(By.xpath(CACMIDDRepo.searchAcctStatusDesc)).sendKeys(dbAcctStatusDesc);
                     report.updateTestLog("CACM IDD", "Search Account Status Desc value is "+ dbAcctStatusDesc, Status.DONE);  
	 				}
	 				
	 			
	 				
	 				 Thread.sleep(2000);
	 				String dbfield11 = dataTable.getData("General_Data", "ACCOUNT FUNCTION TYPE"); 
	 				if(!dbfield11.equals(""))
	 				{
	 				String dbAcctFuncType=dataSet.getString(dbfield11);
	 	 			System.out.println("Account Func Type is: "+dbAcctFuncType);
	 	 			
                     driver.findElement(By.xpath(CACMIDDRepo.searchAcctFuncType)).sendKeys(dbAcctFuncType);
                     report.updateTestLog("CACM IDD", "Search Account Func Type value is "+ dbAcctFuncType, Status.DONE);  
	 				}
	 				
	 			
			           
	     			}
	 				
	    		 }
	 			 catch (SQLException | InterruptedException e) {
	 				
	 				
	 				 report.updateTestLog("CACM IDD", "Search value is not inserted in to field ", Status.FAIL);
	 				 e.printStackTrace();
	 			} 
	    		 
	    		
	    	 }
	    	 
	    	 
    	 
    	 // This method calls identificationTestDataforAcct() and passes the identified account Id in account query search text ( for manual Mode).
    	 
	    	 
	    	 public void searchAccountforManualAcct(String AcctID)
	    	 {
	    		 try {
	 				
					 driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(AcctID);
					 System.out.println("Account ID is taken from excel " + AcctID);
					 Thread.sleep(3000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
					System.out.println("Account values is not provided in text field"+AcctID);
					report.updateTestLog("CACM IDD", "Account values is not provided"+AcctID, Status.FAIL);
				} 
	    		
	    		 report.updateTestLog("CACM IDD", "Search Account value is "+ AcctID, Status.DONE); 
	    	 }
	    	 
	    	 
	    	 // This method calls identificationTestDataforAcct() and passes the identified account Id in account query search text ( For Automation Mode Of Test)
	    	 public void searchAccount()
    	 {   
    		 accountId=identificationTestDataforAcct();
    
    		 try {
				
    			 
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CACMIDDRepo.accountSearchtxt)));
				 driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(accountId);
				 System.out.println("Account ID is taken from identificationTestDataforAcct() ");
				 Thread.sleep(3000);
				// driver.findElement(By.xpath(CACMIDDRepo.accountSearchtxt)).sendKeys(Keys.ENTER);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				System.out.println("Account values is not provided in text field"+accountId);
				report.updateTestLog("CACM IDD", "Account values is not provided"+accountId, Status.FAIL);
			} 
    		
    		 report.updateTestLog("CACM IDD", "Search Account value is "+ accountId, Status.DONE); 
    	 }
    	 
    	 // This method clicks search button
    	 public void iddUIRunSearch() throws Exception {
   		  
    		 System.out.println(" ************************ IDD UI Run Search ************************************ ");
    		 
    		 try {
   		  //Click On RUN SEARCH button
   	        driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_RunSearch)).click();
   	        Thread.sleep(10000);
   	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)));
    	        if(driver.findElements(By.xpath("//*[@class='extdt-table-layout rich-table ' and contains(@id, 'search')]//table")).size()==0){
   	        	report.updateTestLog("CACM IDD", "No Results to Show", Status.FAIL);  
   	        }
   	        System.out.println("IDD UI RunSearch Finished"); 
   	        report.updateTestLog("CACM IDD", " UI RunSearch Finished", Status.PASS);
   		  }
   		  catch(Exception e) {
   			  report.updateTestLog("CACM IDD", " UI RunSearch not Finished", Status.FAIL);  
   			  e.printStackTrace();
   		  }
   	  }

    	 
    	 // This method clicks on open tab
    	 public void openRecordFromSearch() throws Exception
   	  {
   		  
    		 System.out.println(" ************************ open Record From Search ************************************ ");
    		 
    		 try {
   		
   	        Thread.sleep(5000);
   	        driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)).click();
   	        System.out.println("Opened record");
   	        report.updateTestLog("CACM IDD", "Opened Record", Status.PASS);
   	     report.updateTestLog("CACM IDD", "Searched record is found successfully ", Status.SCREENSHOT);
   	        Thread.sleep(8000);
   	      
   		  }
   		  
   		  catch(Exception e)
   		  {
   			  System.out.println("Not Able to open the record");
   		        report.updateTestLog("CACM IDD", "Not Able to open the  Record", Status.FAIL);  
   		  }
   	  } 
    	 
    	 // This method identifies web element of attributes for subject Area - Account
    	 public WebElement xpathSubjAreaAccount(String attr)
    	 {
    		 WebElement attrValue=null;
    		
	    	try{
	    		 attrValue= driver.findElement(By.xpath("//td[text()='"+attr+"']/following-sibling::td/span"));
	    	   }
	    	catch(Exception e)
	    	{
	    		System.out.println("WebElement not found for xpathSubjAreaAccount "+attr);
	    		report.updateTestLog("CACM IDD", "WebElement not found for xpathSubjAreaAccount"+attr, Status.FAIL);
	    	}
	    	
	    	return attrValue;
    	 }
    	 
    	 /*
    	  * 
    	  * This method generates dynamic xpath for Account Postal Address by passing corresponding attributes value
    	  
    	  */
    	 
    	 String attrValue="";
    	 
        	 
    	 // To identify the web element for different subject Area
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
    	 
   
    	
    	 public static String uiAcctId="";
    	// compares between IDD and database for Account 
    	 public void verifySubjAreaAcct()
    	 {
	 
    			System.out.println(" ************************ Account Attributes ************************************ ");
    			
    			
    		 
		 try {
			 
			    ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT ID"));
			     uiAcctId=xpathSubjAreaAccount("ACCOUNT ID").getText();
		    	System.out.println(" Acct ID value from Subject Area Account : "+uiAcctId);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT NAME"));
		    	
		    	String uiAcctName=xpathSubjAreaAccount("ACCOUNT NAME").getText();
		    	System.out.println(" Acct Name value from Subject Area Account : "+uiAcctName);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT TYPE"));
		    	String uiAcctType=xpathSubjAreaAccount("ACCOUNT TYPE").getText();
		    	System.out.println(" Acct Type value from Subject Area Account : "+uiAcctType);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT SUBTYPE"));
		    	String uiAcctSubType=xpathSubjAreaAccount("ACCOUNT SUBTYPE").getText();
		    	System.out.println(" Acct Sub Type value from Subject Area Account : "+uiAcctSubType);
		  
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT ROLE"));
		    	String uiAcctRole=driver.findElement(By.xpath("//td[text()='ACCOUNT ROLE']/following-sibling::td[1]/div")).getText();
		    	System.out.println(" Acct Role value from Subject Area Account : "+uiAcctRole);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT STATUS"));
		    	String uiAcctStatus=xpathSubjAreaAccount("ACCOUNT STATUS").getText();
		    	System.out.println(" Acct Status value from Subject Area Account : "+uiAcctStatus);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("ACCOUNT FUNCTION TYPE"));
		    	String uiAcctFunctType=xpathSubjAreaAccount("ACCOUNT FUNCTION TYPE").getText();
		    	System.out.println(" Acct Function Type value from Subject Area Account : "+uiAcctFunctType);
		    	
		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", xpathSubjAreaAccount("SOURCE DELETE FLAG"));
		    	String uiAcctSrcDelFlag=xpathSubjAreaAccount("SOURCE DELETE FLAG").getText();
		    	System.out.println(" Source Delete Flag value from Subject Area Account : "+uiAcctSrcDelFlag);
		    	
		    	
		    	 
			   	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
				 
				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaAccount");
				 sqlPassed=sqlPassed.replace("account_id", uiAcctId);
				 sqlPassed=sqlPassed.replace("acct_type1", uiAcctType);
				 
				 System.out.println("Query from parameter file is: "+sqlPassed);
				 Thread.sleep(3000);
				 ResultSet dataSet1=stmt.executeQuery(sqlPassed);
				 Thread.sleep(6000);
				 while (dataSet1.next()) { 
				
				 
				String   dbAcctID=dataSet1.getString("ACCT_ID");
			    System.out.println("Account ID is: "+dbAcctID);
				String	dbAcctName=dataSet1.getString("ACCT_NAME");
				System.out.println("Account Name is: "+dbAcctName);
				String dbAcctType=dataSet1.getString("ACCT_TYPE");
				System.out.println("Account Type is: "+dbAcctType);
				String dbAcctSubType=dataSet1.getString("ACCT_SUBTYPE_DESC");
				System.out.println("Account Sub Type is: "+dbAcctSubType);
				String dbAcctRole=dataSet1.getString("ACCT_ROLE");
				if(dbAcctRole==null)
				{
					dbAcctRole="";
				}
				
				System.out.println("Account Role is: "+dbAcctRole);
				String dbAcctStatus=dataSet1.getString("ACCT_STATUS");
				
				
				if(dbAcctStatus==null)
				{
					dbAcctStatus="";
				}
				
				if (dbAcctStatus.equalsIgnoreCase("A"))
				{
					dbAcctStatus="Active";
				}
				
				if (dbAcctStatus.equalsIgnoreCase("I"))
				{
					dbAcctStatus="Inactive";
				}
				System.out.println("Account Status is: "+dbAcctStatus);
				String dbAcctFunctType=dataSet1.getString("ACCT_FUNCTION_TYPE");
				System.out.println("Account Function Type is: "+dbAcctFunctType);
				String dbSrcDelFlag=dataSet1.getString("SOURCE_DELETE_FLAG");
				System.out.println("Account Source Delete Flag is: "+dbSrcDelFlag);
	
				
				
				GF.reportStatusGeneration("Account ID",uiAcctId,dbAcctID);
				GF.reportStatusGeneration("Account Name",uiAcctName,dbAcctName);
				GF.reportStatusGeneration("Account Type",uiAcctType,dbAcctType);
				GF.reportStatusGeneration("Account Sub Type",uiAcctSubType,dbAcctSubType);
				GF.reportStatusGeneration("Account Role",uiAcctRole,dbAcctRole);
				GF.reportStatusGeneration("Account Status",uiAcctStatus,dbAcctStatus);
				GF.reportStatusGeneration("Account Function Type",uiAcctFunctType,dbAcctFunctType);
				GF.reportStatusGeneration("Account Source Delete Flag",uiAcctSrcDelFlag,dbSrcDelFlag);
				
				System.out.println(" Completed steps in verifySubjAreaAcct  - Account  ");
		   }
		   
			} catch (SQLException | InterruptedException e) {
				
				e.printStackTrace();
				
				System.out.println("DB validation failed for Subject Area Account ");
	    		report.updateTestLog("CACM IDD", "DB validation failed for Subject Area Account ", Status.FAIL);	
			} 
    	 }
		 
		  
    		
        // This method validates the value of attributes between IDD and database for  Account Postal Address
    	 public void verifySubjAreaAcctPostAddr()
    	 {
    		 
    		 
    		System.out.println(" *******************************Account Postal Address  ***************************** ");
 			
 			
    		 
    		 
    		 String uiAddressType="";
    		 String uiPrimaryFlag="";
    		 String uiAttentionTo="";
    		  
    		 String uiUseCleansedAddr="";
    		 String uiCleansedAddrLine1="";
    		  
    	
    		 try {
    			 
    			 
 			    xpathSubjArea("Account Postal Address").click();
 		    	System.out.println(" Inside in Subject Area : "+xpathSubjArea("Account Postal Address").getText());
 		    	
 		    	
 		    
 		    	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
 		    	
 		    	 List<WebElement> attrValueRow=driver.findElements(By.xpath("//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td[2]/div"));
	    		 int rowCount=attrValueRow.size();
	    		 System.out.println(" Total Row count vaue  "+rowCount);
	    		 
 		    	
 		    	 for(int i=1;i<=rowCount;i++)	
 		    	 {
 		    		 
 		    	System.out.println(" Value fetched for loop   : "+i);
 		    	
 		    	System.out.println(" Data retrive from IDD UI for Subject Area : Account Postal Address ");
 		    	
 		    	System.out.println("-------------------------------------------------------- ");
 		    	
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
 		    	uiUseCleansedAddr=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")).getText();
 		    	System.out.println("Use Cleansed Address value is : "+uiUseCleansedAddr);
 		    	 
 		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")) );
 		    	uiCleansedAddrLine1=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
 		    	System.out.println("Cleansed Address Line value is : "+uiCleansedAddrLine1);
 		    	

 			   	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
 				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
 				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
 				 
 				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaAcctPostAddr");
 				 sqlPassed=sqlPassed.replace("account_id", uiAcctId);
 				 sqlPassed=sqlPassed.replace("paddress_type1", uiAddressType);
 				 sqlPassed=sqlPassed.replace("primary_flag1", uiPrimaryFlag);
 				 sqlPassed=sqlPassed.replace("use_cleansed_flag1", uiUseCleansedAddr);
 				 sqlPassed=sqlPassed.replace("cleansed_addr_1", uiCleansedAddrLine1);
 				 
 				 System.out.println("Query from parameter file is: "+sqlPassed);
 				 
 				System.out.println(" Data retrived from Database for Subject Area : Account Postal Address ");
 				
 				System.out.println(" ######################################################## ");
 				
 				Thread.sleep(3000);
 				
 				ResultSet dataSet2=stmt.executeQuery(sqlPassed);
 				Thread.sleep(6000);
 				while (dataSet2.next()) { 
 				
 				 
 				String dbaddress_Type=dataSet2.getString("PADDRESS_TYPE");
 			    System.out.println("PADDRESS_TYPE is: "+dbaddress_Type);
 				String	dbprimary_flag=dataSet2.getString("PRIMARY_FLAG");
 				System.out.println("PRIMARY_FLAG is: "+dbprimary_flag);
 				
 				if(dbprimary_flag == null)
 				{
 					dbprimary_flag="";
 				}
 				
 				String dbattention_To=dataSet2.getString("ATTENTION_TO");
 				
 				if(dbattention_To == null)
 				{
 					dbattention_To="";
 				}
 				
 				System.out.println("ATTENTION_TO is: "+dbattention_To);
 				String dbuseCleansedAddr=dataSet2.getString("USE_CLEANSED_FLAG");
 				
 				if(dbuseCleansedAddr == null)
 				{
 					dbuseCleansedAddr="";
 				}
 				
 				System.out.println("USE_CLEANSED_FLAG is: "+dbuseCleansedAddr);
 				String dbcleansedAddr=dataSet2.getString("C_ADDR_LINE_1");
 				
 				if(dbcleansedAddr == null)
 				{
 					dbcleansedAddr="";
 				}
 				
 				
 				System.out.println("C_ADDR_LINE_1 is: "+dbcleansedAddr);

 				
 				GF.reportStatusGeneration("ADDRESS_TYPE",uiAddressType,dbaddress_Type);
 				GF.reportStatusGeneration("PRIMARY_FLAG",uiPrimaryFlag,dbprimary_flag);
 				GF.reportStatusGeneration("ATTENTION_TO",uiAttentionTo,dbattention_To);
 				GF.reportStatusGeneration("USE_CLEANSED_FLAG",uiUseCleansedAddr,dbuseCleansedAddr);
 				GF.reportStatusGeneration("C_ADDR_LINE_1",uiCleansedAddrLine1,dbcleansedAddr);
 				
 				System.out.println(" Completed steps in verifySubjAreaAcct  - Account  ");
 				
 		   }
 		 }
 		   
 			} catch (SQLException | InterruptedException e) {
 				
 				
 				
 				System.out.println("DB validation failed for Subject Area Account Post Address");
	    		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);	
	    		e.printStackTrace();
 			} 
 		 
    	 }
    	 
    	 
    	 // Validates the values of External Id Source System between Idd and database
    	 public void verifyExternalIdSrc()
    	 {
    		 
 
  			 		
    		 try 
    		 {
    			 
    		 xpathSubjArea("External ID (Source Systems)").click();
    		 System.out.println(" ****************************** External ID (Source Systems)   ****************************** ");
    		 
    		 Thread.sleep(3000);
    		 
    		 switchtoTableView("External ID (Source Systems)");
    		 noRecords("External ID (Source Systems)");
    		 
    		 List<WebElement> extId=driver.findElements(By.xpath("(//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[2]"));
    		 int rowCount=extId.size();
    		 System.out.println("Row count of External ID "+rowCount);

    		 
    		 
    	
    		 for(int i=1;i<=rowCount;i++)
    		 {
    			 
    			
    			 
    		     System.out.println("Data retrived from UI for row count : "+i);
    		     
    		     System.out.println("--------------------------- ");
    		     
    		     ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("((//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[2])["+i+"]")));
    			 String uiexternalIdType=driver.findElement(By.xpath("((//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[2])["+i+"]")).getText();
    			 System.out.println("EXTERNAL_ID_TYPE is: "+uiexternalIdType);
    			 
    			 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("((//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[3])["+i+"]")));
    			 String uiexternalId=driver.findElement(By.xpath("((//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[3])["+i+"]")).getText();
    			 System.out.println("EXTERNAL_ID is: "+uiexternalId);
    			 
    			 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("((//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[4])["+i+"]")) );
    			 String uisourceDelFlag=driver.findElement(By.xpath("((//*[text()='External ID (Source Systems)'])[1]//following::tr[5]//td[4])["+i+"]")).getText();
    			 System.out.println("SOURCE_DELETE_FLAG is: "+uisourceDelFlag);
    			
    				 
    			
 			   	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
 				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
 				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
 				 
 				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaExternalId");
 				 sqlPassed=sqlPassed.replace("account_id", uiAcctId);
 				 sqlPassed=sqlPassed.replace("externalIdType", uiexternalIdType);
 				 
 				 System.out.println("Query from parameter file is: "+sqlPassed);
 				 
 				System.out.println(" Data retrived from Database for Subject Area : External Id (Source System) for externalIdType "+uiexternalIdType);
 				
 				Thread.sleep(3000);
 				ResultSet dataSet3=stmt.executeQuery(sqlPassed);
 				Thread.sleep(5000);
 				while (dataSet3.next()) { 
 				
 				 
 				String dbexternalIdType=dataSet3.getString("EXTERNAL_ID_TYPE");
 			    System.out.println("EXTERNAL_ID_TYPE is: "+dbexternalIdType);
 				String	dbexternalId=dataSet3.getString("EXTERNAL_ID");
 				System.out.println("EXTERNAL_ID is: "+dbexternalId);
 				
 				if(dbexternalId == null)
 				{
 					dbexternalId="";
 				}
 				
 				String dbSourceDelFlag=dataSet3.getString("SOURCE_DELETE_FLAG");
 				
 				if(dbSourceDelFlag == null)
 				{
 					dbSourceDelFlag="";
 				} 
 				System.out.println("SOURCE_DELETE_FLAG is: "+dbSourceDelFlag);
 				
 				
 				
 				GF.reportStatusGeneration("EXTERNAL_ID_TYPE",uiexternalIdType,dbexternalIdType);
 				GF.reportStatusGeneration("EXTERNAL_ID",uiexternalId,dbexternalId);
 				GF.reportStatusGeneration("SOURCE_DELETE_FLAG",uisourceDelFlag,dbSourceDelFlag);
    		 
 				System.out.println(" Completed steps verifyExternalIdSrc  - External ID (Source Systems)  ");
    		 
    			}
 				}
    		 }
    		 catch (SQLException | InterruptedException e) 
    		    {
     				
     				
     				
     				System.out.println("DB validation failed for Subject Area External ID (Source Systems)");
    	    		report.updateTestLog("CACM IDD", "DB validation failed for Subject Area External ID (Source Systems)", Status.FAIL);	
    	    		e.printStackTrace();
     			} 
    		 
    	 }
    	 
    	 // Validates values of attributes for Related Customer between Idd and database
    	 public void relatedCustomer(){
    		 
    		 
    		 
    		System.out.println(" *************************  Related Customer   *********************************** ");
   			
   			
    		 
    		 try
    		 {
  			    xpathSubjArea("Related Customer").click();
  			    Thread.sleep(3000);
  			    
  			    
  		    	System.out.println(" Inside in Subject Area : "+xpathSubjArea("Related Customer").getText());
  		    	
  		    	switchtoTableView("Related Customer");
  		    	noRecords("Related Customer");
  		    	
  		    	
  		    	
  		    	System.out.println(" Data retrive from UI for Subject Area : Related Customer ");
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[2]")) );
  		    	String ca_Id=driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[2]")).getText();
  		    	System.out.println("CA_ID value is "+ca_Id);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[3]")));
  		    	String cust_name=driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[3]")).getText();
  		    	System.out.println("Customer Name value is "+cust_name);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[4]")) );
  		    	String cust_legal_name=driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[4]")).getText();
  		    	System.out.println("Customer Legal Name value is "+cust_legal_name);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[5]")) );
  		    	String cust_type=driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[5]")).getText();
  		    	System.out.println("Customer type value is "+cust_type);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[6]")));
  		    	String cust_subtype=driver.findElement(By.xpath("(//*[text()='Related Customer']/following::tr[6])[1]//td[6]")).getText();
  		    	System.out.println("Customer sub type value is "+cust_subtype);

  		    	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
 				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
 				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
 				 
 				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaReltedCust");
 				 sqlPassed=sqlPassed.replace("account_id", uiAcctId);
 				 sqlPassed=sqlPassed.replace("customerNm", cust_name);
 				 
 				 System.out.println("Query from parameter file is: "+sqlPassed);
 				 
 				System.out.println(" Data retrived from Database for Subject Area : Related Customer for Customer Name "+cust_name);
 				Thread.sleep(3000);
 				ResultSet dataSet4=stmt.executeQuery(sqlPassed);
 				Thread.sleep(5000);
 				
 				
 				
 				while (dataSet4.next()) { 
 	 				
 	 				 
 	 				String dbca_Id=dataSet4.getString("CA_ID");
 	 			    System.out.println("CA_ID is: "+ca_Id);
 	 				String	dbcust_name=dataSet4.getString("CUST_NAME");
 	 				System.out.println("CUST_NAME is: "+cust_name);
 	 				String	dbcust_legal_name=dataSet4.getString("CUST_LEGAL_NAME");
 	 				System.out.println("CUST_LEGAL_NAME is: "+dbcust_legal_name);
 	 				
 	 				if(dbcust_legal_name == null)
 	 				{
 	 					dbcust_legal_name="";
 	 				} 
 	 				
 	 				String	dbcust_type=dataSet4.getString("CUST_TYPE");
 	 				System.out.println("CUST_TYPE is: "+dbcust_type);
 	 				String	dbcust_subtype=dataSet4.getString("CUST_SUBTYPE");
 	 				System.out.println("CUST_SUBTYPE is: "+dbcust_subtype);
 	 				
 	 				GF.reportStatusGeneration("CA_ID",ca_Id,dbca_Id);
 	 				GF.reportStatusGeneration("CUST_NAME",cust_name,dbcust_name);
 	 				GF.reportStatusGeneration("CUST_LEGAL_NAME",cust_legal_name,dbcust_legal_name);
 	 				GF.reportStatusGeneration("CUST_TYPE",cust_type,dbcust_type);
 	 				GF.reportStatusGeneration("CUST_SUBTYPE",cust_subtype,dbcust_subtype);
 	 				
 	 				System.out.println(" Completed steps in relatedCustomer  - Related Customer   "); 
    		 }
    		 }
    		 catch(Exception e)
    		 {
    			 e.printStackTrace();
    			 
    			 System.out.println("DB validation failed for Subject Area Related Customer");
 	    		report.updateTestLog("CACM IDD", "DB validation failed for Related Customer", Status.FAIL);	
    		 }


    	 
    	 }
    	 
    	 
    	 //Validates values of attributes for Nameplate Relationship between IDD and database
    	 public void nameplateRelationship()
    	 {
    		 
    		    System.out.println(" ***********************************  Nameplate Relationship  ************************* ");
    			
    		
    		 
    		 try
    		 {
  			    xpathSubjArea("Nameplate Relationship").click();
  			    Thread.sleep(3000);
  		    	System.out.println(" Inside in Subject Area : "+xpathSubjArea("Nameplate Relationship").getText());
  		    	Thread.sleep(3000);
  		    	switchtoTableView("Nameplate Relationship");
  		    	noRecords("Nameplate Relationship");
  		    	
  		    	System.out.println(" Data retrive from UI for Subject Area : Nameplate Relationship ");
  		    	
  		    	 List<WebElement> count=driver.findElements(By.xpath("(//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[2]"));
  	    		 int rowCount=count.size();
  	    		 System.out.println("Row count of Nameplate Relationship "+rowCount);
  	    		 
  	    		 for (int i=1;i<=rowCount;i++)
  	    		 {		 
  	    			 
  	    		     System.out.println("Data retrived from UI for row count : "+i);
  	    		     
  	    		     System.out.println("--------------------------- ");
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[2])["+i+"]")));
  		    	String uinamepltCd=driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[2])["+i+"]")).getText();
  		    	System.out.println("NAMEPLATE_CD value is "+uinamepltCd);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[3])["+i+"]")));
  		    	String uiSrcDelFlg=driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[3])["+i+"]")).getText();
  		    	System.out.println("SOURCE_DELETE_FLAG value is "+uiSrcDelFlg);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[4])["+i+"]")));
  		    	String uiOemId=driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[4])["+i+"]")).getText();
  		    	System.out.println("OEM_ID value is "+uiOemId);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[5])["+i+"]")));
  		    	String uiOemLocId=driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[5])["+i+"]")).getText();
  		    	System.out.println("Oem Location ID value is "+uiOemLocId);
  		    	
  		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[6])["+i+"]")) );
  		    	String uiStartDt=driver.findElement(By.xpath("((//*[text()='Nameplate Relationship'])[1]//following::tr[5]//td[6])["+i+"]")).getText();
  		    	System.out.println("Start date  is "+uiStartDt);
  		    	
  		    	

  		    	 DbEnvConnection test=new DbEnvConnection(scriptHelper);
 				 String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
 				 System.out.println("Env input from xlsx "+dbEnvInputXlsx);
 				 
 				 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
 				 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaNameplateRel");
 				 sqlPassed=sqlPassed.replace("account_id", uiAcctId);
 				 sqlPassed=sqlPassed.replace("nmpltcd1", uinamepltCd);
 				 
 				 System.out.println("Query from parameter file is: "+sqlPassed);
 				 
 				 System.out.println(" Data retrived from Database for Subject Area : Nameplate Relationship for Customer Name "+uinamepltCd);
 				
 				 
 				Thread.sleep(3000); 
 				ResultSet dataSet4=stmt.executeQuery(sqlPassed);
 				Thread.sleep(5000);
 				
 				
 				
 				while (dataSet4.next()) { 
 	 				
 					
 	 				String dbnamepltCd=dataSet4.getString("NAMEPLATE_DESC");
 	 				
 	 			    System.out.println("NAMEPLATE_CD is: "+dbnamepltCd);
 	 				String	dbsrcDelFlg=dataSet4.getString("SOURCE_DELETE_FLAG");
 	 				
 	 				if(dbsrcDelFlg == null)
 	 				{
 	 					dbsrcDelFlg="";
 	 				}
 	 				
 	 				System.out.println("SOURCE_DELETE_FLAG is: "+dbsrcDelFlg);
 	 				String	dbOemId=dataSet4.getString("OEM_ID");
 	 				
 	 				
 	 				if(dbOemId == null)
 	 				{
 	 					dbOemId="";
 	 				} 
 	 				
 	 				System.out.println("OEM_ID value is: "+dbOemId);
 	 				
 	 				String	dbstartDt=dataSet4.getString("START_DATE");
 	 				
 	 				if(dbstartDt !=null)
 	 				{
 	 				
 	 				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
 	 			    SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");
 	 			    Date date = format1.parse(dbstartDt);
 	 			    

 	 			    dbstartDt=format2.format(date);
 	 			    if(dbstartDt.startsWith("0"))
 	 			    {
 	 			    	dbstartDt=dbstartDt.substring(1);
 	 			    }
 	 			    }
 	 			    
 	 			    else 
 	 			    {
 	 			    	dbstartDt="";
 	 			    }
 	 			    
 	 			    System.out.println("START_DATE is: "+dbstartDt);
 	 				
 	 				GF.reportStatusGeneration("NAMEPLATE_CD",uinamepltCd,dbnamepltCd);
 	 				GF.reportStatusGeneration("SOURCE_DELETE_FLAG",uiSrcDelFlg,dbsrcDelFlg);
 	 				GF.reportStatusGeneration("OEM_ID",uiOemId,dbOemId);
 	 				GF.reportStatusGeneration("START_DATE",uiStartDt,dbstartDt);
 	 			
 	 				System.out.println(" Completed steps in nameplateRelationship  - Nameplate Relationship  "); 
    		 }
    		 }
    		 }
    		 catch(Exception e)
    		 {
    			
		 
   			 System.out.println("DB validation failed for Subject Area Nameplate Relationship");
  	    		 report.updateTestLog("CACM IDD", "DB validation failed for Nameplate Relationship", Status.FAIL);	
  	    		 e.printStackTrace();
    		 }


    	
    		 }
    	 
    	 //Function to logout from IDD
   	  public void logoutIDD() throws Exception
   	  {
   		System.out.println("********************************  logoutIDD() *******************************************"); 
   		  
        try
        {
                        
        	            Thread.sleep(5000);
        	            driver.switchTo().defaultContent();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Dropdown)));
                       
                   
                       
                        driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Dropdown)).click();
                        driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Dropdown)).click();
                     //Thread.sleep(5000);
                        
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Button)));
                                      JavascriptExecutor executor = (JavascriptExecutor)driver;
                                      executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_LogOut_Button)));
                      
                        Thread.sleep(10000);
                        report.updateTestLog("CACM IDD", "Log Out Successful ", Status.PASS);
                        System.out.println("CACM IDD Log Out Successful");
              
        }
        catch(Exception e )
        {
                        
              report.updateTestLog("CACM IDD", "Log Out not Successful ", Status.FAIL);
             System.out.println("CACM IDD Log Out not Successful"); 
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
     //method that verifies no records in the classification area 
      public void noRecords()
      {
    	  try
    	  {
    		  
    		  Thread.sleep(3000);
    	  if(driver.findElements(By.xpath("//*[text()='No Records ']")).size()==1)
		   {
			 System.out.println("No Records found for client Classification ");
			
				  report.updateTestLog("CACM IDD", "No Records ", Status.PASS);	
			
		   } }
    	  catch(Exception e)
    	  {
    		  
    	  }
      }
      //method returns triangular buttom in subject areas
      public String triangularButton(String subjectarea)
	     {
	    	 
	   
	    	 String subjectareaXpath= "//*[text()='"+subjectarea+"']//..//child::img";
	    	 System.out.println("Expanded"+subjectarea);
	    	 return subjectareaXpath;
	     }
      //method to verify no records
      public void noRecords(String subjectarea)
      {
         try
         {
              WebElement e11 = driver.findElement(By.xpath("(//span[text()='"+subjectarea+"']//..//..//..//following-sibling::td//input)[1]"));
              WebElement e12 = driver.findElement(By.xpath("(//span[text()='"+subjectarea+"']//..//..//..//following-sibling::td//input)[2]"));
              if (!e11.isEnabled() && !e12.isEnabled())
              {
                     
                    driver.findElement(By.xpath(triangularButton(subjectarea))).click();
                     System.out.println("No records found");
                     report.updateTestLog("CACM IDD", "No Records ", Status.PASS);	
                     Thread.sleep(5000);
                     
                 } }
         catch(Exception e)
         {
                
         }
      }

}



