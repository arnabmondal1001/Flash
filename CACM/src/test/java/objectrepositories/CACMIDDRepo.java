package objectrepositories;

public class CACMIDDRepo {
	
    public static final String CACM_IDD_UI_TXT_LOGIN_USER_ID = "(//input[@class='infaField'])[1]";   
 public static final String CACM_IDD_UI_TXT_LOGIN_PWD = "(//input[@class='infaField'])[2]"; 
 public static final String CACM_IDD_UI_BTN_LOGIN = "//*[text()='Log In']";
 public static final String CACM_IDD_LogOut_Dropdown= "//img[@src=\"/e360/mdm/entity360view/res/images/help.png\"]//parent::td//following-sibling::td";
 public static final String CACM_IDD_LogOut_Button="(//*[text()='Log Out'])[2]";
 
 public static final String CACM_IDD_UI_Queries ="//*[text()='Queries']";
 //Customer Query
 public static final String CACM_IDD_Custoemr_Query="//*[text()='Customer Query']";
 public static final String CACM_IDD_OpenQueries="(//*[text()='Open Query'])[2]";
 
 //CA ID under Customer Query Search
 public static final String CACM_IDD_CA_ID_Under_Search="(//*[@class='searchParamFieldPanel'])[1]//input";
 //back up xpath of the above string = (//*[@class='textSearchRegular'])[1]//following::input[1]
 
 //for drop down (//*[@class='sip-combobox-font-inactive sip-combobox-input-inactive '])[1]
 
 public static final String CACM_IDD_UI_RunSearch ="//*[text()='Run Search']";
 public static final String CACM_IDD_UI_Open ="(//*[text()='Open'])[1]";
 
 //Sub Account Query
 public static final String CACM_IDD_Sub_Account_Query="//*[text()='Sub Account Query']";
 //public static final String CACM_IDD_OpenQueries="(//*[text()='Open Query'])[2]";
 
 public static final String Sub_Account_Type = "(//*[@class='sip-combobox-font-inactive sip-combobox-input-inactive '])[1]";
//public static final String Sub_Account_Status = "(//*[@class='searchTextField'])[2]";
 //public static final String Sub_Account_Status = "(//*[@class='searchParamFieldPanel'])[5]";
 public static final String Sub_Account_Status = "//*[contains(@title,' SUB ACCOUNT STATUS')]//following::input[1]";
 public static final String Sub_Account_Sub_Type = "//*[contains(@title,' SUB ACCOUNT SUBTYPE')]//following::input[2]";
 public static final String Sub_Account_Role = "//*[contains(@title,' SUB ACCOUNT ROLE')]//following::input[2]";
 public static final String Sub_Account_External_Id = "//*[contains(@title,' SUB ACCOUNT EXTERNAL ID')]//following::input[1]";
 public static final String Sub_Account_Id = "//*[contains(@title,' SUB ACCOUNT ID')]//following::input[1]";
 //*[contains(@title,' SUB ACCOUNT SUBTYPE')]//following::input[2]
 
 public static final String Total_Records_Field1 = "//*[contains(text(),'Total records:')]";
 public static final String Total_Records_Field2 = "//*[text()='Total records:(']//following::a[1]";
 public static final String Button = "(//img[@class='pagerButton'])[3]";
 //if(driver.findElements(By.xpath("//span[text()='No Records ']")).size()==1){ 
 
 public static final String pagecount =  "//input[contains(@id,'pagerInput_scrollersr')]//parent::td//following-sibling::td";
 
 public static final String openfirstrecord = "//*[text()='Open']";
 public static final String accountarrowbutton = "//img[@src='/bdd/images/icons/collapsed.png']";
 
 //  Account
 
public static final String accountQuerySearchtxt="//span[text()='Account Query']";

public static final String accountSearchtxt="((//label[contains(text(),'ACCOUNT ID')])[1]/..//div)[1]/input";

public static final String subjAreaAccountAcctId="//td[text()='ACCOUNT ID']/../td[2]/span";

public static final String subjAreaAccountAcctName="//td[text()='ACCOUNT NAME']/following-sibling::td/span";

//Account Search

public static final String searchAcctStatus="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[10]//input)[2]";

public static final String searchAcctType1="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[7]//input)[2]";

public static final String searchCountryCdDesc="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[4]//input)[2]";

public static final String searchProvinceCdDesc="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[5]//input)[2]";

public static final String searchAcctSubType="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[8]//input)[2]";

public static final String searchAcctStatusDesc="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[10]//input)[2]";

public static final String searchAcctFuncType="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[11]//input)[2]";

public static final String searchAcctNm="(//label[contains(text(),'ACCOUNT ID')]/following-sibling::div[2]//input)[1]";
public static final String Customer_PostalCode = "//*[contains(@title,' CLEANSED POSTAL CODE')]//following::input[1]";
public static final String Customer_Type = "//*[contains(@title,' CUSTOMER TYPE')]//following::input[2]";
public static final String Customer_SubType = "//*[contains(@title,' CUSTOMER SUBTYPE')]//following::input[2]";
public static final String Customer_Status = "//*[contains(@title,' CUSTOMER STATUS')]//following::input[2]";
public static final String Customer_Segmentation = "//*[contains(@title,' SEGMENTATION')]//following::input[2]";

//public static final String Total_Records_Field1 = "//*[contains(text(),'Total records:')]";
//public static final String Total_Records_Field2 = "//*[text()='Total records:(']//following::a[1]";
public static final String Total_Records_Field3="//*[@class='datascroller_right']/span";
public static final String CACM_IDD_Search_CustomerX="(//img[contains(@src,'exadel/siperian/renderkit/html/images/spn_close_btn.png.jsf')])[3]";
public static final String CACM_IDD_ConnectCust="//*[starts-with(@class,'searchResultOutputText')][text()='Customer ']";

//Merge

public static final String SearchCAID2="//label[contains(text(),'CA ID')]/following-sibling::div/input[1]";
public static final String  CACMIDD_UI_Button_OK="//*[text()='     OK    ']";  


}
