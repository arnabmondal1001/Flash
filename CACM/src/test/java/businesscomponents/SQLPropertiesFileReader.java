package businesscomponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * This class is used to read the property of sqlParameter files 
 *
 */
public class SQLPropertiesFileReader {
	
public static	Properties property=new Properties() ;
public static 	FileInputStream fis=null;
public static String  query=null;

/**
 * 
 * Generic query that is type of String is passed to the method. Each query present in sqlParameter.properties is parameterized.   
 * It returns String value. 
 * 
 */
public static String readPropertiesfile(String query){

	  try {
		  
		    fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources"+"\\sqlParameter.properties");
			property.load(fis);
			query=property.getProperty(query);
			
			
	      } catch (FileNotFoundException e) {
		
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		
	      } catch (IOException e) {
	    	System.out.println(e.getMessage());
			e.printStackTrace();
		}
	  return query;
}

}
