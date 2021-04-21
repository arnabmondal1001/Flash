package businesscomponents;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.util.frameworkutils.Status;
import com.util.frameworkutils.Util;

import supportlibraries.DriverScript;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/**
 * Generic Functions class
 * @author Ravi
 */

public class GenericFunctions extends ReusableLibrary
{
	
	/**
	 * Constructor to initialize the component group library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */ 
	
	public GenericFunctions(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	

	String strPathToDownloads = new File(System.getProperty("user.dir")).getAbsolutePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "Downloads";
	String strPathToJava = new File(System.getProperty("user.dir")).getAbsolutePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test" + Util.getFileSeparator() + "java";
	String strPathToResources = new File(System.getProperty("user.dir")).getAbsolutePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources";
		
	String strEnvMaxWaitTime = System.getenv("MAX_WAIT_TIME");	//From Env variables
	String strPropMaxWaitTime = properties.getProperty("MaxWaitTime");	//From Properties file
	String strActMaxWaitTime;
	
	//WebDriverWait wait = new WebDriverWait(driver, 10);
	
	public boolean isElementPresent(By by)
	{		
		try {
			driver.findElement(by);				
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

		
	public void readAllAttributes(WebElement element,String objectName) 
	{
		System.out.println("---------------------------"+objectName+"-----------------------------");
        @SuppressWarnings("rawtypes")
		ArrayList parentAttributes = (ArrayList)  
                     ((JavascriptExecutor)driver).executeScript(
                     		"var s = []; var attrs = arguments[0].attributes; for (var  i= 0; i<attrs.length;i++) { var a = attrs[i]; s.push(a.name + ':' + a.value); } ; return s;", element);
			  for (Object o : parentAttributes) {
			    System.out.println(o);
			  }

			  System.out.println("---------------------------"+objectName+"-----------------------------");
		
	}

	public WebElement findObject(WebDriver driver,By by,String elementName) throws Exception{
        WebElement wElement = null; int timeOut = 0;
        do{
               if(timeOut > 0){
                    Thread.sleep(1000);
               }
               try{
                          wElement = driver.findElement(by);
               }catch (NoSuchElementException e) {
            	   timeOut++;
            	   }
        }while(wElement == null && timeOut < 30);
        try{
                    wElement = driver.findElement(by);
        }catch (NoSuchElementException e) {
             //report.updateTestLog("Find "+elementName, "Exception occured while trying to find the "+elementName+". Exception ["+e+"]",Status.FAIL);
        }      
        if(wElement == null){
               report.updateTestLog("Verifying "+elementName, elementName+" does not available", Status.FAIL);
               //throw new FrameworkException("Find "+elementName, elementName+" not found");
        }else{
               try {
                          ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", wElement);
                    } catch (Exception e) {} 
        }
      return wElement;
   }
	
	public void selectDropdown(WebDriver driver, By by, String strValue, String strDDName) throws Exception
	{		
		Select selectDD = new Select(findObject(driver, by, strDDName));		
		selectDD.selectByVisibleText(strValue);			

	}
	
	public void clickTableCell(WebDriver driver, By by, String strValue, String strTableName) throws Exception
	{
		
		WebElement table_element = findObject(driver, by, strTableName);
        List<WebElement> tr_collection = table_element.findElements(By.xpath("tbody/tr"));
        boolean boolFound = false;
        
        for(int i = 0 ; i < tr_collection.size() ; i++){
        	List<WebElement> td_collection = tr_collection.get(i).findElements(By.xpath("td"));
        	//int j = intColumnNum;
        	for(int j = 0 ; j < td_collection.size() ; j++){
        		String cellValue = td_collection.get(j).getText();
        		if(cellValue.trim().equalsIgnoreCase(strValue) == true){
        			td_collection.get(j).click();
        			report.updateTestLog("Verify Cell value", "'" + strValue + "' is found in the table '" + strTableName + "'", Status.PASS);
        			boolFound = true;        			
        			break;        			
        		}
        	}
		}	
        if(boolFound == false){
        	report.updateTestLog("Verify Cell value", "'" + strValue + "' is NOT found in the table '" + strTableName + "'", Status.FAIL);        
        }
	}
	

	
	 /**
     * Function to check maximum length of each element
    * @param driver
    * @param by
    * @param elementName
    * @param maxLength
    * @throws Exception
    */
	public void ObjectFieldValidation(WebDriver driver,By by,String elementName,Integer maxLength) throws Exception
	{
       Thread.sleep(2000);
       if(driver.findElement(by).getText().length()<=maxLength){
       		report.updateTestLog(elementName," maximum field length Characters is " +maxLength+ ", user is allowed to enter "+ maxLength+ " characters only",Status.PASS);
		}else{
			report.updateTestLog(elementName, " maximum field length Characters is " +maxLength+  ", user entered more than "+ maxLength+" characters ",Status.FAIL);
		}
    }
	
	/* Function to return Visible elements.
	 * 
	  */
	public void visibleElement(SearchContext s, By by)
	{
		List<WebElement> lstElements = s.findElements(by);
		for (WebElement we : lstElements){
			if (we.isDisplayed()){
				report.updateTestLog("Visible elements are", "'" + we.getText() + "' in the table \n \n", Status.PASS);
			}
		}
		
	}
   /* Component to get text from the webpage
  
   */ 
	public boolean verifyObjectPresenceNegation(By by, String textToBeReported)
	{		 
		WebDriverWait wait = new WebDriverWait(driver, 10);
		boolean isObjectPresent;
		boolean isFunctionPassed = false;
		try{
	    	    	
			try{						
				currentTime();
				WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
				isObjectPresent = element.isDisplayed();
				currentTime();				
			}
			catch(Exception e){				
				currentTime();
				isObjectPresent = false;
			}
			
			if (! isObjectPresent)
			{
				isFunctionPassed = true;
				report.updateTestLog("Verify that <b> " + textToBeReported + "</b> is not present within the page", "The object / control <b> " + textToBeReported + "</b> is not present within the page", Status.PASS);
			}
			else
			{
				isFunctionPassed = false;
				report.updateTestLog("Verify that <b> " + textToBeReported + "</b> is not present within the page", "The text <b> " + textToBeReported + "</b> is present within the page", Status.FAIL);
			}
		}
		catch(Exception e)
		{
			isFunctionPassed = false;
			report.updateTestLog("Verify that <b> " + textToBeReported + "</b> is not present within the page", "An Exception occured while locating the object / control <b> " + textToBeReported + "</b>", Status.FAIL);
		}
		
		return (isFunctionPassed);
	}

	/* Component to Synchronize an object
	
	*/  
	public boolean syncObject(By by)
	{		
		boolean IsDisplayed = false;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try{						
			currentTime();
			WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			
			IsDisplayed = element1.isDisplayed();
			
			currentTime();				
		}
		catch(Exception e){				
			currentTime();								
		}
		
		if ((IsDisplayed)  )
			System.out.println(by + " :Object is Displayed :)");
		else						
			report.updateTestLog("Verify that the <b>" + by + "</b> object is displayed","<b>" + by + "</b> object is not displayed", Status.FAIL);
		
		return (IsDisplayed);
	}
	/**
	* Component to get the current Time in the format HHMMSS
	
	*/   
	public void currentTime() 
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	}
	
	 /* Function to click Visible element.
		 * @FunctionName clickVisible
		 * @InputParameters None
		 * @Author Cognizant
		 * @param driver
		 * @param by
		 */
		public void visibleElements(WebDriver driver, By by) {
			List<WebElement> lstElements = driver.findElements(by);
			String strLinkText = null;
			for (WebElement we : lstElements) {
				if (we.isDisplayed()) {
					 strLinkText = we.getText();
					 report.updateTestLog("Visible elements are", strLinkText , Status.PASS);
					//we.click();
					//break;
				}
			}
			
			//return strLinkText;
		}
		
		 /* Function to click Visible element.
		 * @FunctionName clickVisible
		 * @InputParameters None
		 * @Author Cognizant
		 * @param driver
		 * @param by
		 */
		public String visibleElements1(WebDriver driver, By by) {
			List<WebElement> lstElements = driver.findElements(by);
			String strLinkText = null;
			for (WebElement we : lstElements) {
				if (we.isDisplayed()) {
					 strLinkText = we.getText();
					 report.updateTestLog("Visible elements are", strLinkText , Status.PASS);
					//we.click();
					//break;
				}
			}
			
			return strLinkText;
		}
		
	    /**
	       * Component to get text from the webpage and check whether each text contains given input as sub String
	       * 
	       * @since 
	       * @author 
	       * @throws InterruptedException
	       */ 
	    @SuppressWarnings("unused")
		public void fetchTextToCompare(WebDriver driver, By by, String inputValue)
	    {		
	    	String textFetched = null;
	    	boolean IstextFetchedEqual = false;
	    	List<WebElement> lstElements = driver.findElements(by);
			String strLinkText = null;
			for (WebElement we : lstElements) 
			{
				if (we.isDisplayed()) 
				{
					textFetched = we.getText();
					textFetched = textFetched.toString().trim().toUpperCase();
					if (textFetched.contains(inputValue.trim().toUpperCase()))
					{
					IstextFetchedEqual = true;
					report.updateTestLog( textFetched + " consists of " + inputValue.trim().toUpperCase() + " as sub string ","\n"+ "   Hence search result is correct"+"\n", Status.PASS);
					}
					else{
					IstextFetchedEqual = false;
					report.updateTestLog(textFetched + " does not consist of " + inputValue.trim().toUpperCase() + "as sub string" , "  Hence search result is incorrect", Status.FAIL);
					}
				}
				
				else
					report.updateTestLog("No search result found"," ", Status.FAIL);
	    	}
			
		}

	   /* Component to get attribute value of the XML tag
	       * 
	       * @since Sep 9, 2014
	       * @author 
	       * @throws InterruptedException
	       */ 
	    public void fetchAttributeToCompare(WebDriver driver, By by, String textToBeCompared)
	    {	
	    	String textFetchedAttribute = null;
	    	textFetchedAttribute=driver.findElement(by).getAttribute("value");
	    	if(textFetchedAttribute==null)
	    		textFetchedAttribute=driver.findElement(by).getText();
			
			if (textFetchedAttribute.equalsIgnoreCase(textToBeCompared.toString().trim()))
			{
				report.updateTestLog("  ",textFetchedAttribute + " is matching with the modified value "+ textToBeCompared.toString() ,Status.DONE);
			}
			else
			{
				report.updateTestLog("  ",textFetchedAttribute + " is not matching with the modified value " + textToBeCompared.toString() ,Status.FAIL);
			}
		}
	    

	    public void dynamicWaitForObject(WebDriver driver, int intTimeToWait, By by) throws Exception {
	    	
			WebDriverWait wait = new WebDriverWait(driver,intTimeToWait);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));			
		}


	    
	    public void highlightElement(WebDriver driver, WebElement element) throws Exception {
		       
		            JavascriptExecutor js = (JavascriptExecutor) driver;
		            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		                    element, "color: blue; border: 2px solid blue;");
		            Thread.sleep(200);
		         
		    }
		    
		    public void removehighlight(WebDriver driver, WebElement element) throws Exception {
		      //  for (int i = 0; i < 2; i++) {
		            JavascriptExecutor js = (JavascriptExecutor) driver;
		           // js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		             //       element, "color: blue; border: 2px solid blue;");
		            Thread.sleep(200);
		            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		                    element, "");
		       // }
		    }
			
	    
	    
	    /**
	     * VerifyElementPresent - To verify whether the given element is present
	     * @param by
	     * @param strElementName
	     * @throws Exception 
	     */
	    public void verifyElementPresent(By by, String strElementName) throws Exception
		{			    	
	    	driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
	    	if(isElementPresent(by) == true){
	    		report.updateTestLog(strElementName, strElementName + " is displayed as expected", Status.DONE);
	    		//highlightElement(driver, element);
	    	}else{
	    		report.updateTestLog(strElementName, strElementName + " is NOT displayed", Status.FAIL);
	    	}
		}
	    
	    /** waitForObject - To Wait for the element presence
	     * @param by
	     * @param strElementName
	     * @throws Exception
	     */
	    public void waitForObject(By by, String strElementName) throws Exception
	    {
	    	
	    	if(strEnvMaxWaitTime != null){
	    		strActMaxWaitTime = strEnvMaxWaitTime;
     		}else if(strPropMaxWaitTime != null){
     			strActMaxWaitTime = strPropMaxWaitTime;
     		}
	    	
	    	
	    	try{
	    		new WebDriverWait(driver, (Integer.parseInt(strActMaxWaitTime))).until(ExpectedConditions.presenceOfElementLocated(by));
	    	}catch(Exception e){
	    		report.updateTestLog("Dynamic Wait", strElementName + " is NOT displayed", Status.FAIL);
	    		exitTest();
	    	}
	    	
	    }
	    
	    public void exitTest(){
			throw new NullPointerException("Exiting Test");
		}
		
	
	    public boolean verifyElement(WebDriver driver, String txnstatusfield) throws Exception
        {
            List<WebElement> td_txnstatusvalue = driver.findElements(By.tagName("td"));
            //String txnactstlmentstatus = "";
            boolean txnstatusfoundflag = false;
            for(int i=0 ; i<td_txnstatusvalue.size() ; i++) {
//                if(td_txnstatusvalue.get(i).getText().toUpperCase().contains(txnstatusfield.toUpperCase())) {
                if(td_txnstatusvalue.get(i).getText().replace(",","").toUpperCase().contains(txnstatusfield.toUpperCase().replace(",",""))) {
                                //txnactstlmentstatus = td_txnstatusvalue.get(i+1).getText().trim();
                 txnstatusfoundflag = true;
                 return txnstatusfoundflag;
                     
                }
            }
            
            return txnstatusfoundflag;             
        }
	    
	    public void verifyElementValue(WebDriver driver, String strField, String strFieldValue, int intColInc) throws Exception
		{
					
			List<WebElement> tds = driver.findElements(By.xpath("//tr[@class='text']/td"));
			String strActVal = "";
			boolean eleFound = false;
			for(int i = 0 ; i < tds.size() ; i++){
				String appValue = tds.get(i).getText().trim();
				if(appValue.toUpperCase().equalsIgnoreCase(strField)){
					strActVal = tds.get(i+intColInc).getText().trim();
					eleFound = true;
					if(strActVal.trim().toUpperCase().contains(strFieldValue.toUpperCase().trim())){
						report.updateTestLog("Verify " + strField + " value", "Actual : " + strActVal + " Expected : " + strFieldValue , Status.DONE);
					}else{
						report.updateTestLog("Verify " + strField + " value", "Actual : " + strActVal + " Expected : " + strFieldValue , Status.FAIL);
					}
					break;
				}							
			}	
			if(eleFound == false){
				report.updateTestLog("Verify " + strField, strField + " is NOT displayed", Status.FAIL);
			}
		}
	    
	    public String fetchElementValue(WebDriver driver, String strField, int intColInc) throws Exception
        {
                            
               List<WebElement> tds = driver.findElements(By.xpath("//tr[@class='text']/td"));
               String strActVal = "";
               boolean eleFound = false;
               for(int i = 0 ; i < tds.size() ; i++){
                     String appValue = tds.get(i).getText().trim();
                     if(appValue.toUpperCase().equalsIgnoreCase(strField)){
                            strActVal = tds.get(i+intColInc).getText().trim();
                            eleFound = true;
                            break;
               }                                               
               }      
               if(eleFound == false){
                     report.updateTestLog("Verify " + strField, strField + " is NOT displayed", Status.FAIL);
               }
               return strActVal;
        }

	  
	    
	    public String fetchElementValueContains(WebDriver driver, String strField, int intColInc) throws Exception
        {
                            
               List<WebElement> tds = driver.findElements(By.tagName("td"));
               String strActVal = "";
               boolean eleFound = false;
               for(int i = 0 ; i < tds.size() ; i++){
                     String appValue = tds.get(i).getText().trim();
                     if(appValue.toUpperCase().contains(strField)){
                            strActVal = tds.get(i+intColInc).getText().trim();
                            eleFound = true;
                            break;
               }                                               
               }      
               if(eleFound == false){
                     report.updateTestLog("Verify " + strField, strField + " is NOT displayed", Status.FAIL);
               }
               return strActVal;
        }
	    
	    
	    
	
	
	public String getUniqueString(String strString) { 
		DateFormat df = new SimpleDateFormat("dd:MM:YYYYHH:mm:ss"); 
		Date dateobj = new Date(); 
		String newDT = df.format(dateobj).toString(); 
		newDT = newDT.replace(":", ""); 
		return strString+newDT; 
	} 

	
	public WebElement findTable(String verifytableheading){
		WebElement blockcommentstable = null;
		boolean boolFound = false;
		List<WebElement> alltablelist = driver.findElements(By.tagName("table"));
		for(int i=0; i<alltablelist.size();i++){
			
			if(alltablelist.get(i).getText().equalsIgnoreCase(verifytableheading)){
				blockcommentstable = alltablelist.get(i+1); 
				boolFound = true;
				break;
			}
		}
		if(boolFound == false){
			report.updateTestLog("Verify specified table heading name", "Specified table heading "+verifytableheading+" is NOT found", Status.FAIL);
		}
		return blockcommentstable;	 
	}

	public void verifyTable(String verifytableheading){
		boolean boolFound = false;
		List<WebElement> alltablelist = driver.findElements(By.tagName("table"));
		for(int i=0; i<alltablelist.size();i++){
			
			if(alltablelist.get(i).getText().equalsIgnoreCase(verifytableheading)){
				boolFound = true;
				report.updateTestLog("Verify table heading name", "Specified table  "+verifytableheading+" is found", Status.PASS);
				break;
			}
		}
		
		if(boolFound == false){
			report.updateTestLog("Verify table heading name", "Specified table  "+verifytableheading+" is NOT found", Status.FAIL);
			exitTest();
		}
		
	}
	
	
	public void browseFileUpload() throws Exception {
		// Now browse the saved Excel - CSV_New OC_New Payee
		// The file browse should be successful
		findObject(driver, By.name("FileName"), "file_input_button").click();// click on browse 
		Thread.sleep(5000);
		String fileName = dataTable.getData("General_Data", "FileUpload");
		
		// StringSelection is a class that can be used for copy and paste operations		
		StringSelection stringSelection = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
		// native key strokes for CTRL, V and ENTER keys
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}
	

     public void verifyElementValueInEle(WebDriver driver, WebElement ele,String strField, String strFieldValue, int intColInc) throws Exception
		{
					
			List<WebElement> tds = ele.findElements(By.tagName("td"));
			String strActVal = "";
			boolean eleFound = false;
			for(int i = 0 ; i < tds.size() ; i++){
				String appValue = tds.get(i).getText().trim();
				if(appValue.toUpperCase().equalsIgnoreCase(strField)){
					strActVal = tds.get(i+intColInc).getText().trim();
					eleFound = true;
					if(strActVal.trim().equalsIgnoreCase(strFieldValue.trim())){
						report.updateTestLog("Verify " + strField + " value", "Actual : " + strActVal + " Expected : " + strFieldValue , Status.DONE);
					}else{
						report.updateTestLog("Verify " + strField + " value", "Actual : " + strActVal + " Expected : " + strFieldValue , Status.FAIL);
					}
					break;
				}							
			}	
			if(eleFound == false){
				report.updateTestLog("Verify " + strField, strField + " is NOT displayed", Status.FAIL);
			}
		}

     
     public String fetchElementValueInEle(WebDriver driver,WebElement ele, String strField, int intColInc) throws Exception
     {
                         
            List<WebElement> tds = ele.findElements(By.tagName("td"));
            String strActVal = "";
            boolean eleFound = false;
            for(int i = 0 ; i < tds.size() ; i++){
                  String appValue = tds.get(i).getText().trim();
                  if(appValue.toUpperCase().equalsIgnoreCase(strField)){
                         strActVal = tds.get(i+intColInc).getText().trim();
                         eleFound = true;
                         break;
            }                                               
            }      
            if(eleFound == false){
                  report.updateTestLog("Verify " + strField, strField + " is NOT displayed", Status.FAIL);
            }
            return strActVal;
     }
   
     
   
    public void DelExistingExcelFromFolder(String ReprotName) throws Exception{
 		
    	String strRelFilePath = dataTable.getData("General_Data", "FilePath");
    	
    	String[] arrRelFilePath = strRelFilePath.split("\\\\");		
		String strFilePath = strPathToDownloads;
		for(int i = 1 ; i < arrRelFilePath.length ; i++){			
			strFilePath = strFilePath + Util.getFileSeparator() + arrRelFilePath[i];
		}
		    	 
 		try{
 			File file = new File(strFilePath);
 			if (!file.exists()) {
 				report.updateTestLog("Existing File", "Existing File is not Found", Status.DONE);
 			}
 			else
 			{
 				if(file.delete())
 				{
 					report.updateTestLog("Existing File", "Existing File is deleted", Status.DONE);
 				}
 				else
 				{
 					report.updateTestLog("Existing File", "Delete Operation is failed", Status.DONE);
 				}
 			}
 		}catch(NullPointerException e){
 			  
 		}
 		
 	 }     
     
     public String[] getReqExcelValue(String strRelFilePath, String colValue1, int colvalue1, String colValue2, int colvalue2) throws IOException{
    	    	  		 
 		String[] arrRelFilePath = strRelFilePath.split("\\\\");		
		String strFilePath = strPathToDownloads;
		for(int i = 1 ; i < arrRelFilePath.length ; i++){			
			strFilePath = strFilePath + Util.getFileSeparator() + arrRelFilePath[i];
		}
 		
//     	 String strPath1 = new File(System.getProperty("user.dir")).getAbsolutePath();
// 		 strPath1 = strPath1 + "\\src\\test\\resources\\Downloads";
// 		 filePath = strPath1 + filePath;
 		 
    	 FileInputStream input = new FileInputStream(strFilePath);
    	 HSSFWorkbook wb = new HSSFWorkbook(input);
    	 HSSFSheet sh = wb.getSheetAt(0);
    	 String actColValue1 = "";
    	 String Details[] = new String[40];
    	 boolean flag = false;
    	 
    	 for(int i=0; i<=sh.getLastRowNum();i++){
    		 HSSFRow row = sh.getRow(i);
    		 
    		 try{
    			 actColValue1 = row.getCell(colvalue1).toString();
    		 }catch(NullPointerException e){
    			 actColValue1 = "";
    		 }
    		 
    		 if(colValue1.equalsIgnoreCase(actColValue1)){
    			 String actColValue2 = row.getCell(colvalue2).toString();
    			 if(colValue2.equalsIgnoreCase(actColValue2)){
    				for(int j=0;j<=row.getLastCellNum();j++){
    					try{
    						Details[j] = row.getCell(j).toString();
    					}catch(NullPointerException e){
    						Details[j] ="";
    					}
    				}
    				flag = true;
    				break;
    			 }
    		 }
    	 }
    	 
    	 if(flag==false){
    		 report.updateTestLog("Verify "+colValue2+" in reports", "Entry: "+colValue2+" is not found for the "+colValue1+" in the Report", Status.FAIL);
    	 }
    	 
    	 return Details;
     }

     public void compareActualandExpected(String actValue, String expValue, String colName){
    	 if(actValue.trim().equalsIgnoreCase(expValue.trim())){
			report.updateTestLog(colName, colName+" is displayed as expected, Expected: "+expValue+" Actual: "+actValue,Status.PASS);
		 }else{
			report.updateTestLog(colName, colName+" is NOT displayed as expected, Expected: "+expValue+" Actual: "+actValue,Status.FAIL);
    	 }
     }
     
     public boolean compareActualandExpectedandReturn(String actValue, String expValue, String colName){
    	 
    	 Boolean flag = false;
    	 
    	 if(actValue.equalsIgnoreCase(expValue)){
    		 flag = true;
    		}	
    	 return flag;
    }
     
     public void comparedoubleActualandExpectedvalue(double actValue, double expValue, String colName){
    	 
    	 if(actValue == expValue){
 			report.updateTestLog(colName, colName+" is displayed as expected, Expected: "+expValue+" Actual: "+actValue,Status.DONE);
			}else{
				report.updateTestLog(colName, colName+" is NOT displayed as expected, Expected: "+expValue+" Actual: "+actValue,Status.FAIL);
			}
    }


	public String fetchElementvaluewithtr(WebDriver driver, String findTable, int IntTableIndex, String strField, int intColInc){
		String strActVal = "";
        boolean eleFound = false;
		List<WebElement> a = driver.findElements(By.xpath("//td[@class='sectionon']"));
		
		fetchValue:{
			for(int b=0; b<a.size();b++){
				if(a.get(b).getText().contains(findTable)){
					List<WebElement> c = a.get(b).findElements(By.tagName("table"));
					List<WebElement> d = c.get(IntTableIndex).findElements(By.tagName("tr"));
					for(int e=0; e<d.size();e++){
						if(d.get(e).getText().contains(strField)){
							List<WebElement> f = d.get(e).findElements(By.tagName("td"));
							for(int g=0; g<f.size();g++){
								if(f.get(g).getText().equalsIgnoreCase(strField)){
									strActVal = f.get(g+intColInc).getText().trim();
		                            eleFound = true;
		                            break fetchValue;
								}
							}
						}
					}
				}
			}
		}
		
		
		 if(eleFound == false){
             report.updateTestLog("Verify " + strField, strField + " is NOT displayed", Status.FAIL);
       }
       return strActVal;

	}

     public String getMyIPAddress() throws Exception
     {
    	 String strIPAddress=Inet4Address.getLocalHost().getHostAddress();
    	 return strIPAddress;
     }     
     
       public String getUniqueString8digits(String strString) { 
        DateFormat df = new SimpleDateFormat("dd:MM:HH:mm"); 
        Date dateobj = new Date(); 
        String newDT = df.format(dateobj).toString(); 
        newDT = newDT.replace(":", ""); 
        return strString+newDT; 
   }

	
	public String nullValueHandler(String assignvalue){
		String text = "";
		try{
			text = assignvalue;
		}catch(Exception e){
			text = "";
		}
		return text;
	}

	public double nullValueHandlerDouble(double Value){
		double assignedvalue = 0.00;
		try{
			assignedvalue = Value;
		}catch(Exception e){
			assignedvalue = 0.00;
		}
		return assignedvalue;
	}
	
	public String nullValueChangetoEmpty(String DBValue){
		
		if(DBValue == null){
			DBValue = "";
		}
		
		return DBValue;
	}
	
	 

	@SuppressWarnings("unlikely-arg-type")
	public Set<String> findUniqueValuesInArray(String[] transactionsArray){
		
		ArrayList<String> list = new ArrayList<String>();
		for(String s : transactionsArray)
			if(!s.equals(""))
				list.add(s);
			
		Set<String> uniqueValues = new HashSet<String>();
		Set<String> duplicateValues = new HashSet<String>();
		for(String a : list){
			if(!uniqueValues.add(a)){
				duplicateValues.add(a);
			}
		}
		uniqueValues.remove(duplicateValues);
		
		return uniqueValues;
		
	}
	

	public String setRunAgainstEnv(){
		
		String strEnvRunAgainstEnv = System.getenv("RUN_AGAINST_ENV");	//From Env variables
		String strPropRunAgainstEnv = properties.getProperty("RunAgainstEnv");	//From Properties file
		String strActRunAgainstEnv = null;
		
		if(strEnvRunAgainstEnv != null){
 			strActRunAgainstEnv = strEnvRunAgainstEnv;
 		}else if(strPropRunAgainstEnv != null){
 			strActRunAgainstEnv = strPropRunAgainstEnv;
 		}
    	
    	if(strActRunAgainstEnv.equalsIgnoreCase("ALL")){
    		String strTCID = dataTable.getDataSubIter("General_Data", "TC_ID", 1).toUpperCase();
    		if(strTCID.contains("RR")){
    			strActRunAgainstEnv = "RR";
    		}    		
    	}
		return strActRunAgainstEnv;
	}
	
	public String[] setDBConnection(String strActRunAgainstEnv){
		
		String connectionHost = "", connectionUser = "", connectionPassword = "";
 	 if(strActRunAgainstEnv.equalsIgnoreCase("RR")){
 			connectionHost = properties.getProperty("DB_ConnectionHost_RR");
 			connectionUser = properties.getProperty("DB_ConnectionUser_RR");
 			connectionPassword = properties.getProperty("DB_ConnectionPassword_RR");
 		}
 		
 		String[] arrDBConnection = {connectionHost, connectionUser, connectionPassword};
		return arrDBConnection;
	}
	
		
	public  String getUniqueAlphaString(){
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
		   char c = chars[random.nextInt(chars.length)];
		   sb.append(c);
		}
		String output = sb.toString();		
		return output;

	}
	
    public void clickElementJavaScript(WebElement ele, String strEleName){
    	
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", ele, strEleName);	 
		
	}
    
    public void scrollDown(int y){
    	
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0," + y + ")", "");	 
		
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


    
    public void reportStatusGenerationDb(String attrName,String uiAttr, String dbAttr)
    {
       if(dbAttr==null)
       {
              dbAttr="";
       }
    
       
       if(uiAttr==null)
       {
              uiAttr="";
       }
       
       if (uiAttr.equalsIgnoreCase(dbAttr))
        {
              
 
              report.updateTestLog("CACM IDD", "DB Validation for " +attrName+ " Before match and merge value is :"+ uiAttr +" = "+" After match and merge value is : "+dbAttr, Status.PASS);
        }
        
        else 
        {
              report.updateTestLog("CACM IDD", "DB Validation for  " +attrName+ "  before match and merge  value is :"+ uiAttr +" = "+" After match and merge value is : "+dbAttr, Status.FAIL);
              
        }
    }

}
