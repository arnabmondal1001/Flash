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

public class IDD_Customer_Unmerge extends ReusableLibrary {
              
              Random rand = new Random(); 
              WebDriverWait wait = new WebDriverWait(driver, 100);

              static List<String> listXrefCAID=new ArrayList<String>();

              public IDD_Customer_Unmerge(ScriptHelper scriptHelper) {
                             super(scriptHelper);
                             // TODO Auto-generated constructor stub
              }
              static String actualunmergedcaidgolden = "";
              static String actualunmergedcaidnongolden = "";
              public void loginCACM_Unmerge() throws Exception {
              IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
              obj1.loginCACM();
                             
              }
              
              public void openMenu_Unmerge() throws Exception {
                             IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
                             
                             obj1.openMenu();
                                                          
                             }
              
              public void savedQueries_Unmerge() throws Exception {
                             IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
                             obj1.savedQueries();
                             
                             
                             }
              
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
              
              public void searchCustomer_Unmerge() throws Exception {
                             try
                   {
                  String CA_ID = dataTable.getData("General_Data", "WINING_CA_ID");
                  driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(CA_ID);
                  System.out.println("CA_ID:"+CA_ID);
                  report.updateTestLog("CACM IDD", "Customer Search complete", Status.PASS);
                   }
                   catch(Exception e){
                         e.printStackTrace(); 
                         report.updateTestLog("CACM IDD", "Customer Search Not complete", Status.FAIL);
                   }
              }
              
              public void iddUIRunSearch_Unmerge() throws Exception {
                             IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
                             obj1.iddUIRunSearch();
                                                          
                             }
              
              public void logoutCACMIDD_Unmerge() throws Exception {
                             IDD_Customer_Search obj1= new IDD_Customer_Search(scriptHelper) ;
                             obj1.logoutCACMIDD();
                                                          
                             }
              
              public void openRecordFromSearch_Unmerge() throws Exception
              { String CA_ID = dataTable.getData("General_Data","WINING_CA_ID");
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
              
              //Click XREF in IDD
              public void clickXREF() throws Exception{

                             
                             try {
                             driver.findElement(By.xpath("(//*[@title='alt+ctrl+x']//div//following::div)[1]")).click();
                                           Thread.sleep(5000);
                                           System.out.println("XREF clicked");
                                           report.updateTestLog("CACM IDD", "XREF clicked", Status.PASS);     
                                           
                             }
                             catch(Exception e)
                             {
                                           e.printStackTrace();
                                           report.updateTestLog("CACM IDD", "XREF not Successfully clicked", Status.FAIL);           
                                           
                             }
              }
              
              public WebElement xpathSubjArea(String sourcesystem)
              {
                             WebElement subjElemt=null;
                             String sourcesystem1 = dataTable.getData("General_Data","SOURCE_SYSTEM_CA_ID_TO_BE_UNMERGED");
                             sourcesystem1 = sourcesystem1.trim();
                             
                             try{
                                           subjElemt= driver.findElement(By.xpath("(//*[text()='Source System : '"+sourcesystem1+"'])[1]"));
                                           
                                }
                             catch(Exception e)
                             {
                                                                        }
                             
                             return subjElemt;
              }
              
              
              
public void unMergeCRO() throws Exception{
                             
              String areaName="";      
              String uiCAIDToBeUnmerged="";
              try {
                             
                                           driver.findElement(By.xpath("//*[text()='Collapse All']")).click();
                                           Thread.sleep(4000);
                                           String srcsys = dataTable.getData("General_Data","SOURCE_SYSTEM_CA_ID_TO_BE_UNMERGED");
                                           List<WebElement> areaRow=driver.findElements(By.xpath("//*[@src='/bdd/images/icons/collapsed.png']//parent::span//..//following-sibling::td//parent::div/span"));
                                           int rowCount=areaRow.size();
                                           Thread.sleep(4000);
                                           System.out.println(rowCount);
                                           Thread.sleep(4000);
                                           for(int i=1;i<=rowCount;i++)       
                             {
                                            
                            System.out.println(" Row count vaue  "+i);
                  areaName=driver.findElement(By.xpath("(//*[@src='/bdd/images/icons/collapsed.png']//parent::span//..//following-sibling::td//parent::div/span)["+i+"]")).getText();
                            Thread.sleep(4000);
                            if (areaName.contains(srcsys.trim())) {
                                           System.out.println(areaName);
                                           System.out.println(srcsys);
                            driver.findElement(By.xpath("(//*[@src='/bdd/images/icons/collapsed.png'])["+i+"]")).click();
                                           Thread.sleep(5000);
                                           break;
                            }
                            
                            }
                                           
                                           Thread.sleep(5000);
                                           String CA_ID_TO_BE_UNMERGED = dataTable.getData("General_Data", "CA_ID_TO_BE_UNMERGED");
                             System.out.println("CA_ID_TO_BE_UNMERGED:"+CA_ID_TO_BE_UNMERGED);
                                           
                            
                             List<WebElement> caidValue=driver.findElements(By.xpath("//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[2]/div"));
                                           int caidCount=caidValue.size();
                                           System.out.println(" Total Row count value  "+caidCount);
                                           
                            
                             for(int i=1;i<=caidCount;i++)      
                             {
                                            
                            System.out.println(" Row count vaue  "+i);
                            
                            
                            
                            
                      uiCAIDToBeUnmerged=driver.findElement(By.xpath("(//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[7]/div)["+i+"]")).getText();
                      uiCAIDToBeUnmerged = uiCAIDToBeUnmerged.trim();
                            System.out.println("CA ID to be unmerged : "+uiCAIDToBeUnmerged);
                            
                            if (uiCAIDToBeUnmerged.equalsIgnoreCase(CA_ID_TO_BE_UNMERGED.trim()))
                            {
                            driver.findElement(By.xpath("(//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[7]/div)["+i+"]")).click();
                                           Thread.sleep(5000);
                                           Actions actions = new Actions(driver);
                                           WebElement btnElement = driver.findElement(By.xpath("(//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[7]/div)["+i+"]"));
                                           Thread.sleep(5000);
                                           actions.contextClick(btnElement).perform();
                                           Thread.sleep(5000);
                                           System.out.println("Right clicked");
                                          driver.findElement(By.xpath("//*[text()='Unmerge']")).click();
                                           Thread.sleep(5000);
                                           driver.findElement(By.xpath("//*[text()='Cross Reference Only']")).click();
                                           Thread.sleep(6000);
                                           break;
                            }
                             }
                            
                                           report.updateTestLog("CACM IDD", "Unmerge done successfully", Status.PASS);
                                           
                                           
              }
                             catch(Exception e) {
                                           e.printStackTrace();
                                           report.updateTestLog("CACM IDD", "Unmerge not done successfully", Status.FAIL); 
                             }
              
              try {
                               errormsgRemove();
    } catch (Exception ee) {
                             
                             ee.printStackTrace();
    }
              }

public void unMergeCRWL() throws Exception{
              
              String areaName="";      
              String uiCAIDToBeUnmerged="";
              try {
                             
                                           driver.findElement(By.xpath("//*[text()='Collapse All']")).click();
                                           Thread.sleep(4000);
                                           String srcsys = dataTable.getData("General_Data","SOURCE_SYSTEM_CA_ID_TO_BE_UNMERGED");
                                           List<WebElement> areaRow=driver.findElements(By.xpath("//*[@src='/bdd/images/icons/collapsed.png']//parent::span//..//following-sibling::td//parent::div/span"));
                                           int rowCount=areaRow.size();
                                           Thread.sleep(4000);
                                           System.out.println(rowCount);
                                           Thread.sleep(4000);
                                           for(int i=1;i<=rowCount;i++)       
                             {
                                            
                            System.out.println(" Row count vaue  "+i);
                  areaName=driver.findElement(By.xpath("(//*[@src='/bdd/images/icons/collapsed.png']//parent::span//..//following-sibling::td//parent::div/span)["+i+"]")).getText();
                            Thread.sleep(4000);
                            if (areaName.contains(srcsys.trim())) {
                                           System.out.println(areaName);
                                           System.out.println(srcsys);
                            driver.findElement(By.xpath("(//*[@src='/bdd/images/icons/collapsed.png'])["+i+"]")).click();
                                           Thread.sleep(5000);
                                           break;
                            }
                            
                            }
                                           
                                           Thread.sleep(5000);
                                           String CA_ID_TO_BE_UNMERGED = dataTable.getData("General_Data", "CA_ID_TO_BE_UNMERGED");
                             System.out.println("CA_ID_TO_BE_UNMERGED:"+CA_ID_TO_BE_UNMERGED);
                                           
                            
                             List<WebElement> caidValue=driver.findElements(By.xpath("//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[2]/div"));
                                           int caidCount=caidValue.size();
                                           System.out.println(" Total Row count value  "+caidCount);
                                           
                            
                             for(int i=1;i<=caidCount;i++)      
                             {
                                            
                            System.out.println(" Row count vaue  "+i);
                            
                            
                            
                            
                      uiCAIDToBeUnmerged=driver.findElement(By.xpath("(//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[7]/div)["+i+"]")).getText();
                      uiCAIDToBeUnmerged = uiCAIDToBeUnmerged.trim();
                            System.out.println("CA ID to be unmerged : "+uiCAIDToBeUnmerged);
                            
                            if (uiCAIDToBeUnmerged.equalsIgnoreCase(CA_ID_TO_BE_UNMERGED.trim()))
                            {
                            driver.findElement(By.xpath("(//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[7]/div)["+i+"]")).click();
                                           Thread.sleep(5000);
                                           Actions actions = new Actions(driver);
                                           WebElement btnElement = driver.findElement(By.xpath("(//*[text()='PKEY_SRC_OBJECT']/following::tr[1]/td//div/following-sibling::div//td[7]/div)["+i+"]"));
                                           Thread.sleep(5000);
                                           actions.contextClick(btnElement).perform();
                                           Thread.sleep(5000);
                                           System.out.println("Right clicked");
                                          driver.findElement(By.xpath("//*[text()='Unmerge']")).click();
                                           Thread.sleep(5000);
                                           driver.findElement(By.xpath("//*[text()='Cross Reference With Lineage']")).click();
                                           Thread.sleep(6000);
                                           break;
                            }
                             }
                            
                                           report.updateTestLog("CACM IDD", "Unmerge done successfully", Status.PASS);
                                           
                                           
              }
                             catch(Exception e) {
                                           e.printStackTrace();
                                           report.updateTestLog("CACM IDD", "Unmerge not done successfully", Status.FAIL); 
                             }
              
              try {
                               errormsgRemove();
    } catch (Exception ee) {
                             
                             ee.printStackTrace();
    }
              }


              
public void unmergedCustomerSearch() throws Exception {
              
              try {
              closeCustomerX();
              }
              catch(Exception e){
        e.printStackTrace(); 
  }
                 
              try
                 {

                System.out.println("Inside in mergSearchCustomer ");  
                
                Thread.sleep(6000);
                
                driver.findElement(By.xpath(customerqueryXpath("CA ID"))).click();
                
                driver.findElement(By.xpath(customerqueryXpath("CA ID"))).clear();
                System.out.println("Clean the text box ");          
                
                String unmergedca_id = "";
                String CA_ID_TO_BE_UNMERGED = actualunmergedcaidgolden;
                unmergedca_id = CA_ID_TO_BE_UNMERGED.trim();
              
                driver.findElement(By.xpath(customerqueryXpath("CA ID"))).sendKeys(unmergedca_id);
                System.out.println(" Unmerged CA_ID entered in text box :"+unmergedca_id);
              
                Thread.sleep(6000);

                
                driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_RunSearch)).click();
                
                Thread.sleep(3000);
              
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)));
                  
                Thread.sleep(5000);
                
                driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_UI_Open)).click();
                

                
                Thread.sleep(10000);
                
                report.updateTestLog("CACM IDD", " Unmerged Customer Searched successfully for  "+unmergedca_id, Status.PASS);
                report.updateTestLog("CACM IDD", "Customer Search done successfully", Status.SCREENSHOT);
                
                System.out.println("Opened the first record");
                 }
                 catch(Exception e){
                               e.printStackTrace(); 
                              report.updateTestLog("CACM IDD", "Customer Search Not complete", Status.FAIL);
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
              
               public String triangularButton(String subjectarea)
     {
               
              // WebElement searchElmt= driver.findElement(By.xpath("//label[contains(text(),'ACCOUNT NAME')]/../div["+i+"]//input"));
               String subjectareaXpath= "//*[text()='"+subjectarea+"']//..//child::img";
               System.out.println("Expanded"+subjectarea);
               return subjectareaXpath;
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

//db validation for search by CA ID
              public void dbval_Unmerged_CAID() throws Exception
               
               
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
                              //String ca_id2 = dataTable.getData("General_Data", "CA_ID_TO_BE_UNMERGED");
                              String ca_id2 =  actualunmergedcaidgolden;
                              ca_id2 = ca_id2.trim();
                              System.out.println("Env input from xlsx "+dbEnvInputXlsx);
                              Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
                              System.out.println("Env input from xlsx "+stmt);
                             
                              String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CA_ID1"); 
                              sqlPassed=sqlPassed.replace("ca_id11",ca_id2);
                              
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
                              //          AUTO FRANCHISE INDICATOR
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
              
public void dbval_Unmerged_ProductCategory() throws Exception
              
               
               {
                               //Expanding the Subject Area
                               driver.findElement(By.xpath(triangularButton("Product Category"))).click();
                               Thread.sleep(5000);
          noRecords("Product Category");
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
                             //String ca_id2 = dataTable.getData("General_Data", "CA_ID_TO_BE_UNMERGED");
                             String ca_id2 =  actualunmergedcaidgolden;
                             ca_id2 = ca_id2.trim();
                             System.out.println("Env input from xlsx "+dbEnvInputXlsx);
                             Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
                             System.out.println("Env input from xlsx "+stmt);
                            
                              String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_PRODUCT1"); 
                              
                              sqlPassed=sqlPassed.replace("ca_id11",ca_id2);
                             
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
                               //driver.findElement(By.xpath(triangularButton("Product Category"))).click();
              } 

public void dbval_Unmerged_CustAddr()
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
            noRecords("Customer Postal Address");
            Thread.sleep(5000);
            switchtoTableView("Customer Postal Address");
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
                             
                            Thread.sleep(10000);
                            
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
                                           //String ca_id2 = dataTable.getData("General_Data", "CA_ID_TO_BE_UNMERGED"); 
                                            String ca_id2 =  actualunmergedcaidgolden;
                                           ca_id2 = ca_id2.trim();
                                           String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_ADDRESS1");
                                           sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
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
                                           String              DBav_code1=dataSet2.getString("AV_CODE");
                                           System.out.println("AV_CODE is: "+DBav_code1);
                                           String              DBav_message1=dataSet2.getString("AV_MESSAGE");
                                           System.out.println("AV_CODE is: "+DBav_message1);
                                           String             DBuse_cleansed_flag1=dataSet2.getString("USE_CLEANSED_FLAG");
                                           System.out.println("AV_CODE is: "+DBuse_cleansed_flag1);
                                           String              DBc1_addr_line1=dataSet2.getString("C_ADDR_LINE_1");
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
                  Thread.sleep(5000);
                             } 
               
               catch (SQLException | InterruptedException e)
              {
                                           
                                           e.printStackTrace();
                                           
                                           System.out.println("DB validation failed for Subject Area Account Post Address");
                             report.updateTestLog("CACM IDD", "DB validation failed  for Subject Area Account Postal Address", Status.FAIL);  
                             } 
               
 }

public void dbval_Unmerged_Classification() throws Exception

 
 {
                Thread.sleep(10000);  
                //Expanding the Subject Area
                driver.findElement(By.xpath(triangularButton("Client Classification"))).click();
                Thread.sleep(5000);
                System.out.println("Client Classification clicked ");
      noRecords("Client Classification");
      Thread.sleep(5000);
      switchtoTableView("Client Classification");
              // Highlighting the UI value 
                //Client_Classification
              ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid blue'", driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")));
                Thread.sleep(3000);
              String Client_Classification=driver.findElement(By.xpath("//*[@class='extdt-table-layout rich-table ']//tr//td//div[2]//tbody//tr//td[2]//div")).getText();                        
               DbEnvConnection test=new DbEnvConnection(scriptHelper);
              String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
              //String ca_id2 = dataTable.getData("General_Data", "CA_ID_TO_BE_UNMERGED");
              String ca_id2 =  actualunmergedcaidgolden;
              ca_id2 = ca_id2.trim();
              System.out.println("Env input from xlsx "+dbEnvInputXlsx);
              Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
              System.out.println("Env input from xlsx "+stmt);
              String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_CLASSIFICATION1"); 
               sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
              System.out.println("Validation SQL query :"+sqlPassed);
              //Initializing DB values
              String Client_Classification_DB ="";

              
               try {
                             
                              Thread.sleep(10000);
                                           ResultSet dataSet=stmt.executeQuery(sqlPassed);
                                           Thread.sleep(10000);
                                           while (dataSet.next()) { 
                                           
                                            
                                           Client_Classification_DB=dataSet.getString("CUST_CLSF_TYPE");
                                           System.out.println("CUST_CLSF_TYPE is: "+Client_Classification_DB);


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
              

// Validates values of attributes for Related Account
public void dbval_Unmerged_relatedAccount(){
              
               try
              {
                             //Contracting the Subject Area
                                           driver.findElement(By.xpath(triangularButton("Related Account(s)"))).click();
                                 Thread.sleep(5000);
            noRecords("Related Account(s)");
            Thread.sleep(5000);
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
                                           String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("CUSTOMER_RELATED_ACCOUNT1");
                                           String ca_id2 =  actualunmergedcaidgolden;
                                           ca_id2 = ca_id2.trim();
                                           System.out.println("Env input from xlsx "+dbEnvInputXlsx);
                                           sqlPassed=sqlPassed.replace("ca_id1",ca_id2);
                                           sqlPassed=sqlPassed.replace("acct_id1",acct_Id);
                                           System.out.println("Query from parameter file is: "+sqlPassed);
                                           
                                           System.out.println(" Data retrived from Database for Subject Area : Related Customer for Account Name "+acct_name);
                                           Thread.sleep(3000);
                                           ResultSet dataSet4=stmt.executeQuery(sqlPassed);
                                           Thread.sleep(5000);
                                           
                                           
                                           
                                           while (dataSet4.next()) { 
                                                          
                                                           
                                                          String dbca_Id=dataSet4.getString("ACCT_ID");
                                               System.out.println("acct_Id is: "+dbca_Id);
                                                          String              dbacct_name=dataSet4.getString("ACCT_NAME");
                                                          System.out.println("CUST_NAME is: "+dbacct_name);
                                                          String              dbacct_type=dataSet4.getString("ACCT_TYPE");
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
  
  public void dbval_Unmerged_RelatedAcctAddr()
              {
                             String uiAddressType="";
                             String uiPrimaryFlag="";
                             String uiAttentionTo="";
                             String uiUseCleansedAddrFlag ="";
                             String uiUseCleansedAddr="";
                             String uiCleansedAddrLine1="";
                             String orgCleansedAddrLine1="";
                               
              
                              try {

                                              Thread.sleep(10000);
                                               driver.findElement(By.xpath(triangularButton("Account Postal Address"))).click();
                                               Thread.sleep(10000);
                noRecords("Account Postal Address");
                Thread.sleep(10000);
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
                                 orgCleansedAddrLine1=driver.findElement(By.xpath("(//*[text()='ADDRESS TYPE']/following::tr[1]/td//div/following-sibling::div//td["+(7)+"]/div)["+i+"]")).getText();
                                           System.out.println("Original Address Line value is : "+orgCleansedAddrLine1);
                                           

                                                          DbEnvConnection test=new DbEnvConnection(scriptHelper);
                                                          String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment"); 
                                                           System.out.println("Env input from xlsx "+dbEnvInputXlsx);
                                                          
                                                           Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
                                                          String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("subjectAreaAcctPostAddr1");
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
                                                          String              dbprimary_flag=dataSet2.getString("PRIMARY_FLAG");
                                                          System.out.println("PRIMARY_FLAG is: "+dbprimary_flag);
                                                          String dbAttentionTo=dataSet2.getString("ATTENTION_TO");
                                               System.out.println("dbAttentionTo is: "+dbAttentionTo);
                                                          String         dbUseCleansedAddrFlag=dataSet2.getString("USE_CLEANSED_FLAG");
                                                          System.out.println("USE_CLEANSED_FLAG is: "+dbUseCleansedAddrFlag);
                                                          String dbuiCleansedAddrLine1=dataSet2.getString("C_ADDR_LINE_1");
                                               System.out.println("C_ADDR_LINE_1 is: "+dbuiCleansedAddrLine1);
                                                          String              dborgCleansedAddrLine1=dataSet2.getString("ADDR_LINE_1");
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
  
  public void identifyXREFCAIDbeforeUnmerge()
              {
                             
                             try
                             {
                                           DbEnvConnection test=new DbEnvConnection(scriptHelper);
                                            String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
                                            String ca_id2 = dataTable.getData("General_Data", "WINING_CA_ID");
                                            ca_id2 = ca_id2.trim();
                                            System.out.println("Env input from xlsx "+dbEnvInputXlsx);
                                            Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
                                            System.out.println("Env input from xlsx "+stmt);
                                           
                                            String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("XrefCAID"); 
                                            sqlPassed=sqlPassed.replace("winningcaid",ca_id2);
                                            
                                            System.out.println("Validation SQL query :"+sqlPassed);
                             
                                           
                                            ResultSet dataSet2=stmt.executeQuery(sqlPassed);
                                           Thread.sleep(10000);
                                           
                                            System.out.println("***************************  CA_ID store before unmerge    *******************************");

                                                          while (dataSet2.next()) { 

                                                          
                                                                        String orig_CAID=dataSet2.getString("ORIG_CA_ID");
                                                              System.out.println("ORIG CA ID  is: "+orig_CAID);
                                                              listXrefCAID.add(orig_CAID);


                                                          }
                                                          
                                                          System.out.println("Before Unmerge count of  ORIG_CA_ID "+listXrefCAID.size());
                                                          
                                                          System.out.print(" CA ID values are "+listXrefCAID);
                                                          
                                                          report.updateTestLog("CACM IDD", "ORIG_CA_IDs from XREF are  retrived sucessfully", Status.PASS);                           
                                           
                             } 
                             
                             catch (Exception e) {
                             
                             e.printStackTrace();
                             
                             System.out.println("ORIG_CA_ID from XRE are not retrived");
                             report.updateTestLog("CACM IDD", "ORIG_CA_IDs from XREF are not retrived", Status.FAIL);       
              } 
                             
                             
              }

  //this method performs the post-unmerge validation based on golden/non-golden status of the actual unmerged record
  public void checkRecordafterUnmrge()
              {
                String res = "";
                
                
                             try
                             {
                                           DbEnvConnection test=new DbEnvConnection(scriptHelper);
                                            String dbEnvInputXlsx = dataTable.getData("General_Data", "Environment");
                                            
                                            System.out.println("Env input from xlsx "+dbEnvInputXlsx);
                                            Statement stmt=test.dbConnectionString( dbEnvInputXlsx );
                                            System.out.println("Env input from xlsx "+stmt);

                                            String delim =  "','";
                                            res = String.join(delim, listXrefCAID);
                                            res= res.concat("'");
                                            res = res.substring(0,0)+ "'"+res.substring(0);
                                            System.out.println("All CA_IDs are  "+res);
                                           
                                            String sqlPassed=SQLPropertiesFileReader.readPropertiesfile("actualunmergedcaidvaluegolden"); 
                                            sqlPassed=sqlPassed.replace("actualunmergedcaid",res);
                                            System.out.println("Validation SQL query :"+sqlPassed);
                                            ResultSet dataSet2=stmt.executeQuery(sqlPassed);
                                           while (dataSet2.next()) { 

                                                          
                                                         actualunmergedcaidgolden=dataSet2.getString("CA_ID");
                                               System.out.println("Actual unmerged golden CA ID  is: "+actualunmergedcaidgolden);

                                           }
            
            String sqlPassed1=SQLPropertiesFileReader.readPropertiesfile("actualunmergedcaidvaluenongolden"); 
                                                           sqlPassed1=sqlPassed1.replace("actualunmergedcaid",res);
                                                           System.out.println("Validation SQL query :"+sqlPassed1);
                                                           ResultSet dataSet3=stmt.executeQuery(sqlPassed1);
                                                          while (dataSet3.next()) { 

                                                                        
                                                                        actualunmergedcaidnongolden=dataSet3.getString("CA_ID");
                                                              System.out.println("Actual unmerged non golden CA ID  is: "+actualunmergedcaidnongolden);
                                                              
             }
            
                                            try {
                                            if (!actualunmergedcaidgolden.isEmpty())
                                            {

                                           
                                            Thread.sleep(10000);
                                           
                                            System.out.println("***************************  golden CA_ID after unmerge    *******************************");
                                           
                                            try {
             report.updateTestLog("CACM IDD", "Golden Unmerged record present in BO : "+actualunmergedcaidgolden, Status.PASS);
                                           } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             try {
               unmergedCustomerSearch();
             } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             try {
             dbval_Unmerged_CAID();
             } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             try {
             dbval_Unmerged_ProductCategory();
             } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             
             
             try {
             dbval_Unmerged_relatedAccount();
             } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             try {
             dbval_Unmerged_RelatedAcctAddr();
             } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             Thread.sleep(10000);
             try {
                 dbval_Unmerged_Classification();
                                            }
                                            catch (Exception e) {
                             
                             e.printStackTrace();
                 }
             Thread.sleep(10000);
             try {
                 dbval_Unmerged_CustAddr();
                 } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
             
                                            }
                                            
                                            } catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
                                            
                                            try {
                                            if (!actualunmergedcaidnongolden.isEmpty())
                                            {
                Thread.sleep(10000);
                                                         System.out.println("***************************  non golden CA_ID  after unmerge    *******************************");
                try {
                                                         report.updateTestLog("CACM IDD", "Unmerged record present in Incomplete View : "+actualunmergedcaidnongolden, Status.PASS);
                }
                catch (Exception e) {
                             
                             e.printStackTrace();
                }
                

            }
                                           }             catch (Exception e) {
                                           
                                           e.printStackTrace();
                       }
                                           
                                           
                              } catch (Exception e) {
                             
                             e.printStackTrace();
                             
                             System.out.println("Actual Unmeregd record not found in either in Golden package or Incomplete package");
                             report.updateTestLog("CACM IDD", "Actual Unmeregd record not found in either in Golden package or Incomplete package", Status.FAIL);      
              } 
              
    listXrefCAID.clear();
    res = "";
    actualunmergedcaidgolden = "";
    actualunmergedcaidnongolden = "";
                             
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
                              Thread.sleep(5000);
                     
                 } }
         catch(Exception e)
         {
                
         }
      }
       
     //Method to handle error msg for rowid {0}
       public void errormsgRemove()
       {
              try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Record with rowID {0} was not found in database.'])")));

                WebElement err = driver.findElement(By.xpath("(//*[text()='Record with rowID {0} was not found in database.'])"));

              if (err.isDisplayed())
              {
                Thread.sleep(5000);  
                //driver.findElement(By.xpath("(//*[text()='Close'])[2]")).click();
                driver.findElement(By.xpath("(//*[contains(@id,'closeButtoncontainer')])[1]")).click();
                     Thread.sleep(5000);
                     System.out.println("Closing error msg successfully");
                     Thread.sleep(3000);
                               
                               
              }
              }
              catch(Exception e) 
              {
                e.printStackTrace(); 
                                  
              }
              
       }

     //Method to close CustoemrX tab
       public void closeCustomerX()
       {
                 try{
                              Thread.sleep(10000);
                               WebElement CustomerX=driver.findElement(By.xpath(CACMIDDRepo.CACM_IDD_Search_CustomerX));
              if (CustomerX.isDisplayed())
              {
                Thread.sleep(5000);  
                CustomerX.click();
                  Thread.sleep(5000);
                  System.out.println("Clicked on CustomerX ");
                  System.out.println("Closing CustomerX successfully");
                  Thread.sleep(3000);

              }
              }
              catch(Exception e) 
              {
                e.printStackTrace(); 
                                  
              }
              
       }

}
              
