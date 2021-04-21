package businesscomponents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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

public class IDD_Match_Merge extends ReusableLibrary {
	Random rand = new Random(); 
	WebDriverWait wait = new WebDriverWait(driver, 100);
	GenericFunctions GF = new GenericFunctions(scriptHelper);
	IDD_Customer_Search custkeyword=new IDD_Customer_Search(scriptHelper);
	WebElement element;
	List<CustAddress> custAddrlist=new ArrayList<CustAddress>();
	List<CustAddress> custAddrAfterMergelist=new ArrayList<CustAddress>();
    static	List<CustAddress> custPostafter;
    static	List<CustAddress> cuPost;
    static  List<CustRelatedAccount> custRelAccts=new ArrayList<CustRelatedAccount>();
    static  List<CustRelatedAccount> custRelAcctsAfter=new ArrayList<CustRelatedAccount>();
	public static String CA_ID ="";
	public static  String CA_ID2 ="";
	public static String dbEnvInputXlsx ="";
	public static String cust_id="";
	
	public IDD_Match_Merge(ScriptHelper scriptHelper)
	{
		super(scriptHelper);

	}

	
	public void loginIDDCACM()
	{
	
			
	try {
		
		custkeyword.loginCACM();
		
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	public void logOutIDDCACM()
	{
	
			
	try {
		
		custkeyword.logoutCACMIDD();
		
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	public void clickOnQueriesMenu()
	{
	
		System.out.println("*******************clickOnQueriesMenu*****************************");
	try {
		
		custkeyword.openMenu();
		
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	// Identify the xpath for Searching  fields
	
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

	public void clickOnsavedQueries()
	{
		System.out.println("*******************clickOnsavedQueries*****************************");
			
	try {
		
		custkeyword.savedQueries();
		
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	public void readDatafromExcel()
	{
		System.out.println("*******************readDatafromExcel*****************************");
		
		  CA_ID = dataTable.getData("General_Data","CA_ID");
		  System.out.println(" CA_ID value is "+CA_ID);
		  CA_ID2 = dataTable.getData("General_Data","CA_ID2");
		  System.out.println(" CAI_ID2 value is "+CA_ID2);
		  dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
		  System.out.println("Env input from xlsx "+dbEnvInputXlsx);
		  
		report.updateTestLog("CACM IDD", " CUSTOMER ID value is "+ CA_ID, Status.DONE);
		report.updateTestLog("CACM IDD", "CUSTOMER ID value is "+ CA_ID2, Status.DONE);
		report.updateTestLog("CACM IDD", "Environment is "+ dbEnvInputXlsx, Status.DONE);
		
	}
	
	public List<CustAddress> dbCustAddrDetailBeforeMerge()
	{
		
		try
		{
		DbEnvConnection test=new DbEnvConnection(scriptHelper);
		  
		
		 
		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 
		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CustAddressBeforeMerge");
		
		 
		
			 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
			 sqlPassed=sqlPassed.replace("ca_id2",CA_ID2);
			 
			 System.out.println("Query is  "+sqlPassed); 
			 
			 ResultSet dataSet2=stmt.executeQuery(sqlPassed);
			// Thread.sleep(10000);
			 
			 System.out.println("***************************  records store before match    *******************************");

				while (dataSet2.next()) { 
				
					CustAddress custaddr=new CustAddress();
				 
					String dbaddress_Type=dataSet2.getString("PADDRESS_TYPE");
				    System.out.println("PADDRESS_TYPE is: "+dbaddress_Type);
				    custaddr.setAddressType(dbaddress_Type);
				    
					String	DBav_code1=dataSet2.getString("AV_CODE");
					System.out.println("AV_CODE is: "+DBav_code1);
					custaddr.setAddrVerfyCd(DBav_code1);
					
					String	DBav_message1=dataSet2.getString("AV_MESSAGE");
					custaddr.setAddrVerfyMessg(DBav_message1);
					System.out.println("Addr verfy message is: "+DBav_message1);
					
					String	DBuse_cleansed_flag1=dataSet2.getString("USE_CLEANSED_FLAG");
					System.out.println("Use claeansed flag is: "+DBuse_cleansed_flag1);
					custaddr.setUseCleansedAddress(DBuse_cleansed_flag1);
					
					String	DBc1_addr_line1=dataSet2.getString("C_ADDR_LINE_1");
					System.out.println("Address line 1 is: "+DBc1_addr_line1);
					custaddr.setCleanAddressLine1(DBc1_addr_line1);
					
					custAddrlist.add(custaddr);
					
					
					
					
			
				}
				
				System.out.println("Before match & merge a total primary customer address "+custAddrlist.size());
				
			
		} catch (Exception e) {
		
		e.printStackTrace();
		
		System.out.println("DB validation failed for Subject Area Account Post Address");
		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);	
	} 
		
		return custAddrlist;
	}

	public void searchCustomer() throws Exception {
		   
		System.out.println("*******************searchCustomer*****************************");
		try
		   {
		  
		  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(CA_ID);
		  System.out.println("CA_ID:"+CA_ID);
		  report.updateTestLog("CACM IDD", " Put Customer ID in Customer Text box  "+ CA_ID, Status.PASS);
		   }
		   catch(Exception e){
			  e.printStackTrace(); 
			 report.updateTestLog("CACM IDD", "Customer ID value Not populated in Customer ID text box", Status.FAIL);
		   }
	   }
	
	 //Click On RUN SEARCH button
	
	public void clickOnIddUIRunSearch()
	{
		System.out.println("*******************clickOnIddUIRunSearch*****************************");
			
	try {
		Thread.sleep(5000);
		custkeyword.iddUIRunSearch();
		
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	// Click on open searched record
	public void openReord()
	{
		System.out.println("*******************openReord*****************************");
			
	try {
		
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)));
		custkeyword.openRecordFromSearch();
		
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	
	// Click on matches to add merge candidate
	public void clickMatch() throws Exception
	{
		System.out.println("*******************clickMatch*****************************");
		
		try {driver.findElement(By.xpath("(//*[@title='alt+ctrl+m']//div//following::div)[1]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@id,'candidatesAmountLabel')]")));
		report.updateTestLog("CACM IDD", "Match  opened",Status.PASS);
		System.out.println("Match opened");
		}
		catch (Exception e ){
			
			report.updateTestLog("CACM IDD", "Match  not opened",Status.PASS);
			System.out.println("Match  not  opened");
		}
	}
	
	
	public void addMergeCandidates() throws Exception
	{
		System.out.println("*******************addMergeCandidates*****************************");
		
		try {
			
		driver.findElement(By.xpath("//*[@class='middle' and text()='Add Merge Candidates']")).click();
		Thread.sleep(2000);

		
		 driver.findElement(By.xpath(CACMIDDRepo.SearchCAID2)).sendKeys(CA_ID2);
		 Thread.sleep(3000); 
		//iddUIRunSearch();
		 driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_RunSearch)).click();
	     Thread.sleep(5000);
		 driver.findElement(By.xpath(CACMIDDRepo.CACMIDD_UI_Button_OK)).click();
		 Thread.sleep(5000);
			report.updateTestLog("CACM IDD", "Search merge candidates "+CA_ID2,Status.PASS);
			System.out.println("Search merge candidates "+CA_ID2);

		}
		catch (Exception e ){
			
			report.updateTestLog("CACM IDD", "Search merge candidates not found"+CA_ID2,Status.FAIL);
			System.out.println("Search merge candidates not found"+CA_ID2);
		}
	}
	
	
	// click on checkbox to merge the candidate
	public void mergeRecords() throws Exception 

	{
		System.out.println("*******************mergeRecords*****************************");

	try 
	
	{
	//Enter Customer id 
	driver.findElement(By.xpath("//input[@type='checkbox' and contains(@name,'match_merge_table')]")).click();
	Thread.sleep(10000);
	driver.findElement(By.xpath("//*[text()='Actions']")).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath("//*[text()='Merge']")).click();
	//Thread.sleep(5000);
	
	WebDriverWait wait2 = new WebDriverWait(driver, 30);
	
	
	wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Yes']")));
	
	if(GF.isElementPresent(By.xpath("//*[text()='Yes']")))
	{
		
		driver.findElement(By.xpath("//*[text()='Yes']")).click();
	}

	System.out.println("Merge process done successfully");
	report.updateTestLog("CACM IDD", "Merge process done successfully", Status.PASS);	
	
	}
	catch (Exception e)
	{
		System.out.println("Merge process failed ");
	e.printStackTrace();
	report.updateTestLog("CACM IDD", "Merge process failed", Status.FAIL);	
	}
	}
	
	
	public void potentialMergeRecords()
	{
		System.out.println("*******************potentialMergeRecords*****************************");

		try 
		
		{
		//Enter Customer id 
		driver.findElement(By.xpath("//input[@type='checkbox' and contains(@name,'match_merge_table')]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[text()='Actions']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[text()='Merge']")).click();
		System.out.println("Merge process done successfully");
		report.updateTestLog("CACM IDD", "Merge process done successfully", Status.PASS);	
		
		}
		catch (Exception e)
		{
			System.out.println("Merge process failed ");
		e.printStackTrace();
		report.updateTestLog("CACM IDD", "Merge process failed", Status.FAIL);	
		}
	}
	
	public void mergSearchCustomer() throws Exception {
		  
		System.out.println("*******************mergSearchCustomer*****************************");
		try
		   {

		  System.out.println("Inside in mergSearchCustomer ");	
		  

		  
		  Thread.sleep(10000);
		  WebElement CustomerX=driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_Search_CustomerX));
		  CustomerX.click();
		  System.out.println("Clicked on CustomerX ");	
		  
		  Thread.sleep(3000);
		  
		  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).click();
		  
		  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).clear();
		  System.out.println("Clean the text box ");	
		
		  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(cust_id);
		  System.out.println(" Surviving CA_ID entered in text box :"+cust_id);
		 
		  Thread.sleep(3000);

		  
		  driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_RunSearch)).click();
		  
		  Thread.sleep(3000);
		 
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)));
		    
		  Thread.sleep(5000);
		  
		  driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)).click();
		  

		  
		  Thread.sleep(10000);
		  
		  report.updateTestLog("CACM IDD", " Surviving Customer Searched successfully for  "+cust_id, Status.PASS);
		  report.updateTestLog("CACM IDD", "Customer Search done successfully", Status.SCREENSHOT);
		  
		  System.out.println("Opened the first record");
		   }
		   catch(Exception e){
			  e.printStackTrace(); 
			 report.updateTestLog("CACM IDD", "Customer Search Not complete", Status.FAIL);
		   }
	}
	
	public void returnBeforeMergePostAddr()
	{
		
		System.out.println("*******************returnBeforeMergePostAddr*****************************");
		
		
		try
		{
		cuPost=dbCustAddrDetailBeforeMerge();
	
		 for (int i = 0; i < cuPost.size(); i++) {

		        System.out.print(cuPost.get(i).getAddressType());
		        System.out.print(" ");
		        System.out.print(cuPost.get(i).getAddrVerfyCd());
		        System.out.print(" ");
		        System.out.print(cuPost.get(i).getAddrVerfyMessg());
		        System.out.print(" ");
		        System.out.print(cuPost.get(i).getUseCleansedAddress());
		        System.out.print(" ");
		        System.out.print(cuPost.get(i).getCleanAddressLine1());
		        System.out.println();
		    }
		 report.updateTestLog("CACM IDD", "Primary Address informations are noted successfully before merge", Status.PASS);
		}
		catch(Exception e)
		{
			report.updateTestLog("CACM IDD", "Primary Address informations failed to note before merge", Status.FAIL);
		}
		
	}
	
	public void returnAfterMergePostAddr()
	{
		System.out.println(" Inside in returnAfterMergePostAddr ");
		
		try
		{
		custPostafter= afterMergePostAddr(cust_id );

		
		 for (int i = 0; i < custPostafter.size(); i++) {

		        System.out.print(custPostafter.get(i).getAddressType());
		        System.out.print(" ");
		        System.out.print(custPostafter.get(i).getAddrVerfyCd());
		        System.out.print(" ");
		        System.out.print(custPostafter.get(i).getAddrVerfyMessg());
		        System.out.print(" ");
		        System.out.print(custPostafter.get(i).getUseCleansedAddress());
		        System.out.print(" ");
		        System.out.print(custPostafter.get(i).getCleanAddressLine1());
		        System.out.println();
		    }
		 
		

		 
		}catch(Exception e)
		{
			report.updateTestLog("CACM IDD", "Primary Address informations failed to note after merge", Status.FAIL);
		}
	}
	
	public void afterIDDMergeSurvivingCAID()
	{
		System.out.println("*******************afterIDDMergeSurvivingCAID*****************************");
		 DbEnvConnection test=new DbEnvConnection(scriptHelper);

		 
		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("Surviving_CA_ID");
		
		 
		 sqlPassed=sqlPassed.replace("caId1", CA_ID);
		 sqlPassed=sqlPassed.replace("caId2", CA_ID2);
		 
		 
		 System.out.println("Query from parameter file is: "+sqlPassed);
		 
		 
		 try {
			 
			    Thread.sleep(10000);
				ResultSet dataSet=stmt.executeQuery(sqlPassed);
				Thread.sleep(5000);
				while (dataSet.next()) { 
			    cust_id=dataSet.getString("CA_ID");
			
				
				Thread.sleep(2000);
				
				driver.findElement(By.xpath("(//*[text()='Customer Query'])[1]")).click();
				
				
				returnAfterMergePostAddr();
				report.updateTestLog("CACM IDD", "Primary Address informations are noted successfully after merge", Status.PASS);
			
				}
				
				 	
				
			} catch (Exception e) {
				
			
			System.out.println("Surviving record not found");
			 report.updateTestLog("CACM IDD", "Surviving record not found", Status.FAIL);
	    	e.printStackTrace();
			} 
		 
	}

	
   public List<CustAddress> afterMergePostAddr(String cust_id )
   {
	   try
		{
		DbEnvConnection test=new DbEnvConnection(scriptHelper);
	    Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 
		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CustAddressAfterMerge");
		 sqlPassed=sqlPassed.replace("caid_merge",cust_id);
						 
			 System.out.println("Query is  "+sqlPassed); 
			 
			 ResultSet dataSet3=stmt.executeQuery(sqlPassed);
			 Thread.sleep(15000);
			 
			 System.out.println("***************************  records store in list after match & merge   *******************************");

				while (dataSet3.next()) { 
				
					CustAddress custaddrafterMM=new CustAddress();
				 
					String dbaddress_Type=dataSet3.getString("PADDRESS_TYPE");
				    System.out.println("PADDRESS_TYPE is: "+dbaddress_Type);
				    custaddrafterMM.setAddressType(dbaddress_Type);
				    
					String	DBav_code1=dataSet3.getString("AV_CODE");
					System.out.println("AV_CODE is: "+DBav_code1);
					custaddrafterMM.setAddrVerfyCd(DBav_code1);
					
					String	DBav_message1=dataSet3.getString("AV_MESSAGE");
					custaddrafterMM.setAddrVerfyMessg(DBav_message1);
					System.out.println("Addr verfy message is: "+DBav_message1);
					
					String	DBuse_cleansed_flag1=dataSet3.getString("USE_CLEANSED_FLAG");
					System.out.println("Use claeansed flag is: "+DBuse_cleansed_flag1);
					custaddrafterMM.setUseCleansedAddress(DBuse_cleansed_flag1);
					
					String	DBc1_addr_line1=dataSet3.getString("C_ADDR_LINE_1");
					System.out.println("Address line 1 is: "+DBc1_addr_line1);
					custaddrafterMM.setCleanAddressLine1(DBc1_addr_line1);
					
					custAddrAfterMergelist.add(custaddrafterMM);
					
			
				}
				
				
				
				System.out.println("After match & merge a total primary customer address "+custAddrAfterMergelist.size());
		} catch (Exception e) {
		
		e.printStackTrace();
		
		System.out.println("DB validation failed for Subject Area Account Post Address");
		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Cust Postal Address", Status.FAIL);	
	} 
	   
	   return custAddrAfterMergelist;
		
   }
   

   
   public void verifyCustPostalAddr()
   {
	   System.out.println("*******************verifyCustPostalAddr*****************************");
	   int ii =custPostafter.size();
	   System.out.println("Total count of Customer Postal address after merge  "+ii);
	   int jj=cuPost.size();
	   System.out.println("Total count of Customer Postal address before merge  :"+jj);
	   
	   System.out.println("************************After Merge****************************");
	   
	   for (int i = 0; i < custPostafter.size(); i++) {

	        System.out.print(custPostafter.get(i).getAddressType());
	        System.out.print(" ");
	        System.out.print(custPostafter.get(i).getAddrVerfyCd());
	        System.out.print(" ");
	        System.out.print(custPostafter.get(i).getAddrVerfyMessg());
	        System.out.print(" ");
	        System.out.print(custPostafter.get(i).getUseCleansedAddress());
	        System.out.print(" ");
	        System.out.print(custPostafter.get(i).getCleanAddressLine1());
	        System.out.println();
	    }
	   
	   System.out.println("**********************Before  Merge******************************");
	   for (int i = 0; i < cuPost.size(); i++) {

	        System.out.print(cuPost.get(i).getAddressType());
	        System.out.print(" ");
	        System.out.print(cuPost.get(i).getAddrVerfyCd());
	        System.out.print(" ");
	        System.out.print(cuPost.get(i).getAddrVerfyMessg());
	        System.out.print(" ");
	        System.out.print(cuPost.get(i).getUseCleansedAddress());
	        System.out.print(" ");
	        System.out.print(cuPost.get(i).getCleanAddressLine1());
	        System.out.println();
	    }
	   
	   
	  
	 if(ii==jj)
	 {
		 System.out.println(" Primary address count are matching between before and after match & merge  "+ii);
		 report.updateTestLog("CACM IDD", "Total count of primary customer addresses Before Merge  are "+jj +" = Total count of primary customer addresses after merge  are " +ii, Status.PASS);	
	 
		 
		 for (int i = 0; i < custPostafter.size(); i++)
		   {
		   GF.reportStatusGenerationDb("Address Type",cuPost.get(i).getAddressType(),custPostafter.get(i).getAddressType());
		   GF.reportStatusGenerationDb("Addrress Verify Code",cuPost.get(i).getAddrVerfyCd(),custPostafter.get(i).getAddrVerfyCd());
		   GF.reportStatusGenerationDb("Address Verify Message",cuPost.get(i).getAddrVerfyMessg(),custPostafter.get(i).getAddrVerfyMessg());
		   GF.reportStatusGenerationDb("Use Cleansed Address",cuPost.get(i).getUseCleansedAddress(),custPostafter.get(i).getUseCleansedAddress());
		   GF.reportStatusGenerationDb("Clean Address Line 1",cuPost.get(i).getCleanAddressLine1(),custPostafter.get(i).getCleanAddressLine1());
		   }
	 
	 
	 }
	 else 
	 {
		 report.updateTestLog("CACM IDD", "Total  primary customer addresses Before Merge "+jj +" is not equal to Total  primary customer addresses after merge " +ii, Status.FAIL);
	 }
   
	 
   
   }
   
   
   public void dbCustRelAcctsDetailBeforeMerge()
	{
	   System.out.println("*******************dbCustRelAcctsDetailBeforeMerge*****************************");
	   
		try
		{
		DbEnvConnection test=new DbEnvConnection(scriptHelper);
		  
		
		 
		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 
		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("custRelatedAccount");
		
		 
		
			 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
			 sqlPassed=sqlPassed.replace("ca_id2",CA_ID2);
			 
			 System.out.println("Query is  "+sqlPassed); 
			 
			 ResultSet dataSet4=stmt.executeQuery(sqlPassed);
			// Thread.sleep(10000);
			 
			 System.out.println("***************************  records store before match  for Related Account  *******************************");

				while (dataSet4.next()) { 
				
					CustRelatedAccount custRelAcct=new CustRelatedAccount();
				 
					String acctId=dataSet4.getString("ACCT_ID");
				    System.out.println("ACCT ID is: "+acctId);
				    custRelAcct.setAcctId(acctId);
				    
					String	acctName=dataSet4.getString("ACCT_NAME");
					System.out.println("ACCT NAME is: "+acctName);
					custRelAcct.setAcctName(acctName);
					
					String	acctType=dataSet4.getString("ACCT_TYPE");
					custRelAcct.setAcctType(acctType);
					System.out.println("ACCT TYPE message is: "+acctType);
					
					String	acctSubType=dataSet4.getString("ACCT_SUBTYPE");
					System.out.println("ACCT SUBTYPE is: "+acctSubType);
					custRelAcct.setAcctSubType(acctSubType);
					
					String	acctRole=dataSet4.getString("ACCT_ROLE");
					System.out.println("ACCT ROLE is: "+acctRole);
					custRelAcct.setAcctRole(acctRole);
					
					custRelAccts.add(custRelAcct);

				}
				
				System.out.println("Before match & merge a total related accounts for a customer  "+custRelAccts.size());
				 report.updateTestLog("CACM IDD", "Related Accounts information are noted successfully before merge", Status.PASS);
				
			
		} catch (Exception e) {
		
		e.printStackTrace();
		
		System.out.println("DB validation failed for Subject Area Related Account ");
		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Related Account", Status.FAIL);
		 report.updateTestLog("CACM IDD", "Related Accounts information failed to note before merge", Status.FAIL);
	} 
		
	//	return custRelAccts;
	}
  
				
   public void dbCustRelAcctsDetailAfterMerge()
  	{
	   System.out.println("*******************dbCustRelAcctsDetailAfterMerge*****************************");
  		try
  		{
  		DbEnvConnection test=new DbEnvConnection(scriptHelper);
  		  
  		
  		 
  		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
  		 
  		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("custRelatedAccountAfterMerge");
  		
  		 
  		
  		 sqlPassed=sqlPassed.replace("caid_merge",cust_id);
  		
  			 
  			 System.out.println("Query is  "+sqlPassed); 
  			 
  			 ResultSet dataSet4=stmt.executeQuery(sqlPassed);
  			// Thread.sleep(10000);
  			 
  			 System.out.println("***************************  records store After match  for Related Account  *******************************");

  				while (dataSet4.next()) { 
  				
  					CustRelatedAccount custRelAcctAfter=new CustRelatedAccount();
  				 
  					String acctId=dataSet4.getString("ACCT_ID");
  				    System.out.println("ACCT ID is: "+acctId);
  				    custRelAcctAfter.setAcctId(acctId);
  				    
  					String	acctName=dataSet4.getString("ACCT_NAME");
  					System.out.println("ACCT NAME is: "+acctName);
  					custRelAcctAfter.setAcctName(acctName);
  					
  					String	acctType=dataSet4.getString("ACCT_TYPE");
  					custRelAcctAfter.setAcctType(acctType);
  					System.out.println("ACCT TYPE message is: "+acctType);
  					
  					String	acctSubType=dataSet4.getString("ACCT_SUBTYPE");
  					System.out.println("ACCT SUBTYPE is: "+acctSubType);
  					custRelAcctAfter.setAcctSubType(acctSubType);
  					
  					String	acctRole=dataSet4.getString("ACCT_ROLE");
  					System.out.println("ACCT ROLE is: "+acctRole);
  					custRelAcctAfter.setAcctRole(acctRole);
  					
  					custRelAcctsAfter.add(custRelAcctAfter);

  				}
  				
  				System.out.println("After match & merge a total related accounts for a customer After match "+custRelAcctsAfter.size());
  				 report.updateTestLog("CACM IDD", "Related Accounts information are noted successfully After merge", Status.PASS);
  			
  		} catch (Exception e) {
  		
  		e.printStackTrace();
  		
  		System.out.println("DB validation failed for Subject Area Related Account ");
  		report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Related Account", Status.FAIL);	
  	} 
  		
  		// return custRelAcctsAfter;
  	}
   
   public void verifyCustRelAccts()
   {
	   System.out.println("*******************verifyCustRelAccts*****************************");
	   
	   
	   
	   int acctCountBefore=custRelAccts.size();
	   int acctCountAfter=custRelAcctsAfter.size();
	   
  System.out.println("************************After Merge Related Accounts ****************************");
	   
	   for (int i = 0; i < custRelAcctsAfter.size(); i++) {

	        System.out.print(custRelAcctsAfter.get(i).getAcctId());
	        System.out.print(" ");
	        System.out.print(custRelAcctsAfter.get(i).getAcctName());
	        System.out.print(" ");
	        System.out.print(custRelAcctsAfter.get(i).getAcctType());
	        System.out.print(" ");
	        System.out.print(custRelAcctsAfter.get(i).getAcctSubType());
	        System.out.print(" ");
	        System.out.print(custRelAcctsAfter.get(i).getAcctRole());
	        System.out.println();
	    }
	   
	   System.out.println("**********************Before Merge Related Accounts******************************");
	   for (int i = 0; i < custRelAccts.size(); i++) {

	        System.out.print(custRelAccts.get(i).getAcctId());
	        System.out.print(" ");
	        System.out.print(custRelAccts.get(i).getAcctName());
	        System.out.print(" ");
	        System.out.print(custRelAccts.get(i).getAcctType());
	        System.out.print(" ");
	        System.out.print(custRelAccts.get(i).getAcctSubType());
	        System.out.print(" ");
	        System.out.print(custRelAccts.get(i).getAcctRole());
	        System.out.println();
	    }
	   
	   
	   
	   if(acctCountBefore==acctCountAfter)
		 {
			 System.out.println(" Related Accounts count are matching between before and after match & merge  ");
			 report.updateTestLog("CACM IDD", "Total count of related accounts for a customer Before Merge  are "+acctCountBefore +" = Total count of related accounts for a customer after merge  are  " +acctCountAfter, Status.PASS);	
		 
			 
			 for (int i = 0; i < custRelAccts.size(); i++)
			   {
			   GF.reportStatusGenerationDb("Account ID",custRelAccts.get(i).getAcctId(),custRelAcctsAfter.get(i).getAcctId());
			   GF.reportStatusGenerationDb("Account Name",custRelAccts.get(i).getAcctName(),custRelAcctsAfter.get(i).getAcctName());
			   GF.reportStatusGenerationDb("Account Type",custRelAccts.get(i).getAcctType(),custRelAcctsAfter.get(i).getAcctType());
			   GF.reportStatusGenerationDb("Account SubType",custRelAccts.get(i).getAcctSubType(),custRelAcctsAfter.get(i).getAcctSubType());
			   GF.reportStatusGenerationDb("Account Role",custRelAccts.get(i).getAcctRole(),custRelAcctsAfter.get(i).getAcctRole());
			   }
		 
		 
		 }
		 else 
		 {
			 report.updateTestLog("CACM IDD", "Related Accounts count Before Merge "+acctCountBefore +" are not equal to Related Accounts count after merge " +acctCountAfter, Status.FAIL);
		 }
	   
	   driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_ConnectCust)).click();
   }
      public void verfyCustPostalAddrIddDb()
      {
    	  System.out.println("*******************verfyCustPostalAddrIddDb*****************************");
    	  
    	  
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
 		        System.out.println(" Data retrive from UI for Subject Area : Customer Postal Address ");	    
 		    	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
 		    	
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
 		    	System.out.println("Verification Code value is : "+uiVerificationCode);
 		    	
 		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")) );
 		    	uiAddressVerification=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(4)+"]/div)["+i+"]")).getText();
 		    	System.out.println("Address Verification value is : "+uiAddressVerification);
 		    	
 		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")) );
 		    	uiUseCleansedAddr=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(5)+"]/div)["+i+"]")).getText();
 		    	System.out.println("Use Cleansed Address value is : "+uiUseCleansedAddr);
 		    	 
 		    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'",driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")) );
 		    	uiCleansedAddrLine1=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(6)+"]/div)["+i+"]")).getText();
 		    	System.out.println("Cleansed Address Line 1 value is : "+uiCleansedAddrLine1);
		    	DbEnvConnection test=new DbEnvConnection(scriptHelper);
	  		    Statement stmt=test.dbConnectionString( dbEnvInputXlsx );

 		  	    String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_ADDRESS");
 	  		    sqlPassed=sqlPassed.replace("ca_id1",cust_id);
 		  		 System.out.println("Query is  "+sqlPassed); 
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
 		 		GF.reportStatusGeneration("Address Type",uiAddressType,dbaddress_Type); 
 		 		GF.reportStatusGeneration("Verification Code",uiVerificationCode,DBav_code1); 
 		 		GF.reportStatusGeneration("Address Verification",uiAddressVerification,DBav_message1); 
 		 		GF.reportStatusGeneration("Use Cleansed Address",uiUseCleansedAddr,DBuse_cleansed_flag1); 
 		 		GF.reportStatusGeneration("Cleansed Address Line1",uiCleansedAddrLine1,DBc1_addr_line1);
 		 	 	
 				
 		  
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
      
      public String triangularButton(String subjectarea)
	     {
	    	 
	    	// WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT NAME')]/../div["+i+"]//input"));
	    	 String subjectareaXpath= "//*[text()='"+subjectarea+"']//..//child::img";
	    	 System.out.println("Expanded"+subjectarea);
	    	 return subjectareaXpath;
	     }
      
      public void verifyRelatedAcctsIddDb()
      {
    	  System.out.println("*******************verifyRelatedAcctsIddDb*****************************");
    	 		 
    	 		 try
    	 		 {
    	 			//Contracting the Subject Area
    		 		 	driver.findElement(By.xpath(triangularButton("Related Account(s)"))).click();
    				    Thread.sleep(3000);
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
    					
    					 
    					 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
    					 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_RELATED_ACCOUNT");
    					
    					 sqlPassed=sqlPassed.replace("ca_id1",cust_id);
    			 		
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
    			 		 	GF.reportStatusGeneration("acct_Id",acct_Id,dbca_Id); 
    			 		 	GF.reportStatusGeneration("acct_name",acct_name,dbacct_name); 
    			 		 	GF.reportStatusGeneration("acct_type",acct_type,dbacct_type); 
    			 		 	GF.reportStatusGeneration("acct_subtype",acct_subtype,dbacct_subtype); 
    			 		 	GF.reportStatusGeneration("acct_role",acct_role,dbacct_role); 
    						 
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
	   
      
      public void verifyCustHmrg()
      {
    	  
    	  System.out.println("*******************verifyCustHmrg*****************************");
    	  
    	  String caIdHmrg="";
    	  try
    		{
    		DbEnvConnection test=new DbEnvConnection(scriptHelper);
		    Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
    		String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("custHmrgVal");
            sqlPassed=sqlPassed.replace("caid_hmrg",cust_id);
            System.out.println("Query is  "+sqlPassed); 
            ResultSet dataSet5=stmt.executeQuery(sqlPassed);	 
            System.out.println("***************************  Customer HMRG  *******************************");	 
            while (dataSet5.next()) { 

				 caIdHmrg=dataSet5.getString("CA_ID");	
				 
            }
            
               if(cust_id.equalsIgnoreCase(caIdHmrg))
               {
            	   report.updateTestLog("CACM IDD", "Surviving Customer ID populated in Customer HMRG table "+caIdHmrg , Status.PASS);
               }
            }catch(Exception e)
            {
            	report.updateTestLog("CACM IDD", "Surviving Customer ID not populated in Customer HMRG table "+caIdHmrg , Status.FAIL);
            }
    	  
    		
}
      public void captureAttrsVal()
      {
    	  System.out.println("*******************captureAttrsVal*****************************");
    	  
    	  String caId="";
    	  String updatedBy="";
    	  String dlReason="";
    	  String hubStateInd="";
    	  String sourceDeleteFlag="";
    	  
    	  
    	  try
  		{
  		DbEnvConnection test=new DbEnvConnection(scriptHelper);
		    Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
  		String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("custAttrsVal");
          sqlPassed=sqlPassed.replace("caIdAttr",cust_id);
          System.out.println("Query is  "+sqlPassed); 
          ResultSet dataSet6=stmt.executeQuery(sqlPassed);	 
          System.out.println("*************************** Caputre Customer Fields Value  *******************************");	 
          while (dataSet6.next()) { 

        	  caId=dataSet6.getString("CA_ID");
        	  updatedBy=dataSet6.getString("UPDATED_BY");
        	  dlReason=dataSet6.getString("DL_REASON");
        	  hubStateInd=dataSet6.getString("HUB_STATE_IND");
        	  sourceDeleteFlag=dataSet6.getString("SOURCE_DELETE_FLAG");
		 
          }
          
            
          	   report.updateTestLog("CACM IDD", "CA_ID, UPDATED_BY, DL_REASON, HUB_STATE_IND, SOURCE_DELETE_FLAG " , Status.DONE);
          	   report.updateTestLog("CACM IDD", caId +","+ updatedBy +","+ dlReason +","+ hubStateInd +","+sourceDeleteFlag , Status.PASS);
         
          }catch(Exception e)
          {
        	  report.updateTestLog("CACM IDD", "CA_ID, UPDATED_BY, DL_REASON, HUB_STATE_IND, SOURCE_DELETE_FLAG  not populated " , Status.FAIL);
          }
      }
      
      
      public String uiIDDval(String uiVal )
 	 {
 		 
 		 String UiVal=driver.findElement(By.xpath("//*[text()='"+uiVal+"']"+"//following-sibling::td[1]")).getText() ;
 		 return UiVal ;
 	 }
      
      public String uiIDxPath(String uiVal )
 	 {
 		 
 		 String UiVal="//*[text()='"+uiVal+"']"+"//following-sibling::td[1]";
 		 return UiVal ;
 	 }
      //db validation for search by CA ID
 	 public void verifyCustIddDb() throws Exception
     	 
 	
     	 {
 		 System.out.println("*******************verifyCustIddDb*****************************");
     		 
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
     		
     		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
     		 System.out.println("Env input from xlsx "+stmt);
     		
     		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CA_ID"); 
     		
     		
     		 sqlPassed=sqlPassed.replace("ca_id1",cust_id);
     	
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
     		 GF.reportStatusGeneration("CA_ID",CA_ID_UI,CA_ID_DB); 
     		 
     		 // Comparing Customer Name with DB vis UI
     		GF.reportStatusGeneration("CUST_NAME",CUST_NAME_UI,CUST_NAME_DB); 
     		 
     		 //Comparing Customer legal name
     		GF.reportStatusGeneration("CUST_LEGAL_NAME",CUST_LEGAL_NAME_UI,CUST_LEGAL_NAME_DB); 
     		 
     		 //CUST TYPE
     		GF.reportStatusGeneration("CUST TYPE",CUST_TYPE_UI,CUST_TYPE_DB); 
     		 //	AUTO FRANCHISE INDICATOR
     		GF.reportStatusGeneration("FRANCHISE FLAG",FRANCHISE_FLAG_UI,FRANCHISE_FLAG_DB); 
     		 //CUST_STATUS_UI
     		GF. reportStatusGeneration("CUST STATUS",CUST_STATUS_UI,CUST_STATUS_DB); 
     		 //SOURCE DELETE FLAG
     		GF. reportStatusGeneration("SOURCE DELETE FLAG",SOURCE_DELETE_FLAG_UI,SOURCE_DELETE_FLAG_DB);
     		 //SEGMENT_DESC_DB
     		GF.reportStatusGeneration("SEGMENT_DESC",SEGMENT_DESC_UI,SEGMENT_DESC_DB);
     		 //CUSTOMER SUBTYPE"
     		GF.reportStatusGeneration("CUSTOMER SUBTYPE",CUSTOMER_SUBTYPE_UI,CUST_SUBTYPE_DB);
     	 }
 	 
 public void verifyProductCategory() throws Exception
 	 

 	 {
	 
	 IDD_Account_Search iddAcctSearch=new IDD_Account_Search(scriptHelper);
	 
	 System.out.println("*******************verifyProductCategory*****************************");
		  //Expanding the Subject Area
		  driver.findElement(By.xpath(triangularButton("Product Category"))).click();
		  
		  DbEnvConnection test=new DbEnvConnection(scriptHelper);
	 		
	 		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
	 		 System.out.println("Env input from xlsx "+stmt);
	 		
	 		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_PRODUCT"); 
	 		
	 	
	 		 sqlPassed=sqlPassed.replace("ca_id1",cust_id);
	 		 
	 		 System.out.println("Validation SQL query :"+sqlPassed);
	 		 //Initializing DB values
	 		 String PRODUCT_CATEGORY_DB ="";
	 		 String SOURCE_DELETE_DB ="";
	 		 
	 		 try {
	 			 
	 			 Thread.sleep(10000);
					ResultSet dataSet=stmt.executeQuery(sqlPassed);
					Thread.sleep(10000);
		  
					if(dataSet.next()==false)
					{
						
						  
						 if(driver.findElements(By.xpath("//*[text()='No Records ']")).size()==1)
						   {
							 System.out.println("No Records found for client Classification ");
							
								  report.updateTestLog("CACM IDD", "No Records ", Status.PASS);	
							
							  }
						 
						 driver.findElement(By.xpath(triangularButton("Product Category"))).click();
						 
					}
					
					else
					{
							     
						 do  { 

									PRODUCT_CATEGORY_DB=dataSet.getString("CUST_PROD_CAT_TYPE");
									System.out.println("PRODUCT_CATEGORY is: "+PRODUCT_CATEGORY_DB);
									SOURCE_DELETE_DB=dataSet.getString("SOURCE_DELETE_FLAG");
									System.out.println("SOURCE_DELETE_DB is: "+SOURCE_DELETE_DB);

					 			
						   } while (dataSet.next());
						 
						 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='Switch to Table View']"))); 
						 
						 if(driver.findElement(By.xpath("//*[@title='Switch to Table View']")).isEnabled()){
							 driver.findElement(By.xpath("//*[@title='Switch to Table View']")).click();
							 System.out.println("Switch to Table View");
						 }
					
	 		 
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


 		 //Comparing CA_ID with DB and UI
 		 GF.reportStatusGeneration("PRODUCT_CATEGORY",PRODUCT_CATEGORY_UI,PRODUCT_CATEGORY_DB); 
 		 
 		 // Comparing Customer Name with DB vis UI
 		 GF.reportStatusGeneration("SOURCE_DELETE",SOURCE_DELETE_FLAG,SOURCE_DELETE_DB); 
 		 
 		 //Contracting the Subject Area
		  driver.findElement(By.xpath(triangularButton("Product Category"))).click();
		  
					}} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
 	 } }
 public void verifyClassification() throws Exception

	 {
	 
	 System.out.println("*******************verifyClassification*****************************");
	  //Expanding the Subject Area
	  
	 try {
	 
	 driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
	
	 // Highlighting the UI value 
	  //Client_Classification

	 
	     DbEnvConnection test=new DbEnvConnection(scriptHelper);
		 Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
		 System.out.println("Env input from xlsx "+stmt);
		
		 String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CLASSIFICATION"); 
		
	//	 sqlPassed=sqlPassed.replace("ca_id1",CA_ID);
		 
		 sqlPassed=sqlPassed.replace("ca_id1",cust_id);
		
		 System.out.println("Validation SQL query :"+sqlPassed);
		 //Initializing DB values
		 String Client_Classification_DB ="";
	 
			
			ResultSet dataSet=stmt.executeQuery(sqlPassed);
			Thread.sleep(10000);
			if(dataSet.next()==false)
			{
				
				  
				 if(driver.findElements(By.xpath("//*[text()='No Records ']")).size()==1)
				   {
					 System.out.println("No Records found for client Classification ");
					
						  report.updateTestLog("CACM IDD", "No Records ", Status.PASS);	
					
					  }
				 
				 driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
				 
			}
			
			else
			{
					     
				 do  { 
						
						 
						Client_Classification_DB=dataSet.getString("CUST_CLSF_TYPE");
						System.out.println("Client_Classification is: "+Client_Classification_DB);

						} while (dataSet.next());
				 
			//	 wait.until(ExpectedConditions.visibilityOfElementLocated(locator)(By.xpath("//*[@title='Switch to Table View']")));
				 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='Switch to Table View']"))); 
				 
				 if(driver.findElement(By.xpath("//*[@title='Switch to Table View']")).isEnabled()){
					 driver.findElement(By.xpath("//*[@title='Switch to Table View']")).click();
					 System.out.println("Switch to Table View");
				 }
					
				
				 Thread.sleep(2000);
				 ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")));
				   Thread.sleep(3000);
				   String Client_Classification=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")).getText();	
				
					 //Comparing CA_ID with DB and UI
					 	GF.reportStatusGeneration("Client_Classification",Client_Classification,Client_Classification_DB); 

					 
					 //Contracting the Subject Area
					 	driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
			}
		  
		  
		
		
		  
		 
	 
			} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				report.updateTestLog("CACM IDD", "Valiation for Client Classification failed ", Status.FAIL);
				e.printStackTrace();
			} 
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
	 System.out.println("*******************verifySubjAreaAcctPostAddr*****************************");
	 
		 String uiAddressType="";
		 String uiPrimaryFlag="";
		 String uiAttentionTo="";
		 String uiUseCleansedAddrFlag ="";
		 String uiUseCleansedAddr="";
		 String uiCleansedAddrLine1="";
		 String orgCleansedAddrLine1="";
		  
	
		 try {
			 
			 	
			    //driver.findElement(By.xpath(triangularButton("Related Account(s)"))).click();
			   Thread.sleep(5000);
		    xpathSubjArea("Account Postal Address").click();		    
		    Thread.sleep(5000);
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
 		 	GF.reportStatusGeneration("Address Type",uiAddressType,dbaddress_Type); 
 		 	GF.reportStatusGeneration("Primary Flag",uiPrimaryFlag,dbprimary_flag); 
 		 	GF.reportStatusGeneration("AttentionTo",uiAttentionTo,dbAttentionTo); 
 		 	GF.reportStatusGeneration("Use Cleansed Address Flag",uiUseCleansedAddrFlag,dbUseCleansedAddrFlag); 
 		 	GF.reportStatusGeneration("Cleansed Address Line1",uiCleansedAddrLine1,dbuiCleansedAddrLine1);
 		 	GF.reportStatusGeneration("OrgCleansedAddrLine1",orgCleansedAddrLine1,dborgCleansedAddrLine1);
 		 	
			
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
      }

