package businesscomponents;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import com.util.frameworkutils.Status;


import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
public class ComapreExcel extends ReusableLibrary {
	public ComapreExcel(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	//Class to compare two different excel
	public  void excelcomparison(String filename1,String filename2,String resultfile)
	{
	try 
	{
        // get input excel files
		FileInputStream Input_doc1=new FileInputStream(filename1); 
		FileInputStream Input_doc2=new FileInputStream(filename2);
       // System.out.println(excellFile1);
        // Create Workbook instance holding reference to .xlsx file
        HSSFWorkbook workbook1 = new HSSFWorkbook(Input_doc1);
        HSSFWorkbook workbook2 = new HSSFWorkbook(Input_doc2);

        // Get first/desired sheet from the workbook
        HSSFSheet sheet1 = workbook1.getSheetAt(0);
        HSSFSheet sheet2 = workbook2.getSheetAt(0);

        // Compare sheets
        if(compareTwoSheets(sheet1, sheet2,resultfile, filename1)) {
            System.out.println("\n\nThe two excel sheets are Equal");
            report.updateTestLog("Outbound", "two excel sheets are Equal", Status.PASS);
             
        } else {

            System.out.println("\n\nThe two excel sheets are Not Equal");
            report.updateTestLog("Outbound", "two excel sheets are not Equal", Status.FAIL);
        }
        
        //close files
        Input_doc1.close();
        Input_doc2.close();
      

    } 
	catch (Exception e) {
        e.printStackTrace();
    }

}
	// Compare Two Sheets
    public   boolean compareTwoSheets(HSSFSheet sheet1, HSSFSheet sheet2,String resultfile,String filename1) throws IOException {
  	    
        int firstRow1 = sheet1.getFirstRowNum();
      //  int firstRow=firstRow1+1;
        int lastRow1 = sheet1.getLastRowNum();
        boolean equalSheets = true;
        for(int i=firstRow1+1; i <= lastRow1; i++) {
            
          //  System.out.println("\n\nComparing Row "+i);
        	System.out.println("Comparing Row "+i);
            HSSFRow row1 = sheet1.getRow(i);
            HSSFRow row2 = sheet2.getRow(i);
            if(!compareTwoRows(row1, row2,resultfile,filename1)) {
                equalSheets = false;
                System.out.println("Row "+i+" - Not Equal");
                //report.updateTestLog("Row" +i, "Not Equal", Status.FAIL);
               // break;
            } else {
                System.out.println("Row "+i+" - Equal");
                //report.updateTestLog("Row" +i, "Equal", Status.PASS);
            }
        }
        return equalSheets;
    }

    // Compare Two Rows
    public   boolean  compareTwoRows(HSSFRow row1, HSSFRow row2,String resultfile,String filename1) throws IOException {
    	// Setting Result file 
    //	String resultfile =System.getProperty("user.dir");
  	//    resultfile=resultfile+"\\target\\Results\\Query Results\\output.txt";
    	
        if((row1 == null) && (row2 == null)) {
            return true;
        } else if((row1 == null) || (row2 == null)) {
            return false;
        }
        
        int firstCell1 = row1.getFirstCellNum();
        int lastCell1 = row1.getLastCellNum();
        boolean equalRows = true;
        
        // Compare all cells in a row
        for(int i=firstCell1; i <= lastCell1; i++) {
            HSSFCell cell1 = row1.getCell(i);
            HSSFCell cell2 = row2.getCell(i);
            getHeaderVal(filename1,i,resultfile);
            //Setting result file
            PrintWriter  out = new PrintWriter(new FileWriter(resultfile,true));
            //Comparison starts
            if(!compareTwoCells(cell1, cell2,resultfile)) {
                equalRows = false;
                System.err.println("       Cell "+i+" - Not Equal");
                System.out.println("       Cell "+i+" - Not Equal");  
                report.updateTestLog("", "Not Equal", Status.FAIL);
	             out.append('\n');
	          	 out.println("- Not Equal");   		          	
	          	 out.close();
              //  break;
            } else {
                System.out.println("       Cell "+i+" - Equal");
                report.updateTestLog("", "Equal", Status.PASS);
                 out.append('\n');
	          	 out.println(" -Equal");   		          	
	          	 out.close();
            }
        }
        return equalRows;
    }

    // Compare Two Cells
   
	public  boolean compareTwoCells(HSSFCell cell1, HSSFCell cell2,String resultfile) throws IOException {
    	
  	   PrintWriter  out = new PrintWriter(new FileWriter(resultfile,true));
        if((cell1 == null) && (cell2 == null)) {
        	out.append('\n');
            out.close();
          	 out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
          	report.updateTestLog( "     Value","Outbound:" +cell1.getStringCellValue() + " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
            return true;
        }
        else if((cell1 == null) || (cell2 == null)) {
        	out.append('\n');
            out.close();
          	 out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
          	report.updateTestLog( "     Value","Outbound:" +cell1.getStringCellValue() + " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
            return false;
        }
        
        else if((cell1 == null) || (cell2.toString().equalsIgnoreCase(" "))) {
        	//out.close();
         
            out.append('\n');
            out.close();
          	 out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
          	report.updateTestLog( "     Value","Outbound:" +cell1.getStringCellValue() + " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
            return true;
        }
        
        boolean equalCells = false;
        int type1 = cell1.getCellType();
        int type2 = cell2.getCellType();
        if (type1 == type2) {
            if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
                // Compare cells based on its type
                switch (cell1.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                    	System.out.println("Outbound "+cell1.getCellFormula()+" OVC DB "+cell2.getCellFormula());
                    	 equalCells = true;  
                    	 out.append('\n');
     		          	 out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
     		          	report.updateTestLog( "     Value","Outbound:" +cell1.getStringCellValue() + " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
                    }
                    else 
                    { System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());   
                      out.append('\n');
		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue() + " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
		          	
                    }    
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (cell1.getNumericCellValue() == cell2
                            .getNumericCellValue()) {
                    	System.out.println("Outbound "+cell1.getNumericCellValue()+" OVC DB "+cell2.getNumericCellValue());
                        equalCells = true;
                        out.append('\n');
    		          	out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 	
    		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue() + " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
    		          	
                    }
                    else 
                    {  System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());   
                      out.append('\n');
		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
		          	report.updateTestLog( "     Value","Outbound:"+cell1.getStringCellValue()+ " OVC DB:" +cell2.getStringCellValue(), Status.DONE);
		          	  
                    }
                    break;	
                    
                   
                case HSSFCell.CELL_TYPE_STRING:
                	
                    if (cell1.getStringCellValue().equals(cell2
                            .getStringCellValue())) {
                    	System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
                       equalCells = true;
                       //Code to update result file
  		              out.append('\n');
  		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
  		          	report.updateTestLog( "     Value","Outbound:" +cell1.getStringCellValue() +" OVC DB:"+cell2.getStringCellValue(), Status.DONE); 
  		          	 
                    }
                    
                    else {
                    System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
                    //Code to update result file
		            out.append('\n');
		          	out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());  
		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue() +" OVC DB:"+cell2.getStringCellValue(),Status.DONE);
		          
		          	}
                    break;
                    
                case HSSFCell.CELL_TYPE_BLANK:
                    if (cell2.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        equalCells = true;
                        out.append('\n');
    		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
    		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue()+ " OVC DB:"+cell2.getStringCellValue(), Status.DONE);    
    		          	    
                    }
                    else {
                        System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
                        out.append('\n');
    		          	out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 	
    		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue()+ " OVC DB:" +cell2.getStringCellValue(), Status.DONE);    
    		          	equalCells = true;   
                    }
                    
                    break;
                   
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    if (cell1.getBooleanCellValue() == cell2
                            .getBooleanCellValue()) {
                    	System.out.println("Outbound "+cell1.getBooleanCellValue()+" OVC DB "+cell2.getBooleanCellValue());
                        equalCells = true;
                        out.append('\n');
    		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
    		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue()+ " OVC DB:" +cell2.getStringCellValue(),  Status.DONE);  
    		          	  
                    }
                    else 
                    {    System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
                      out.append('\n');
		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue()+ " OVC DB:"+ cell2.getStringCellValue(),  Status.DONE); 
		          	  
                    }
                    break;
//                    else {
//                    	System.out.println("Outbound "+cell1.getStringCellValue()+" After"+cell2.getStringCellValue()+" Equal");
//                    }
                case HSSFCell.CELL_TYPE_ERROR:
                    if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                    	System.out.println("Outbound "+cell1.getErrorCellValue()+" OVC DB "+cell2.getErrorCellValue());
                        equalCells = true;
                        out.append('\n');
  		          	   out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
  		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue() +" OVC DB:" +cell2.getStringCellValue(),  Status.DONE); 
  		          	   
                    }
                    else 
                        {System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
                        out.append('\n');
  		          	    out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 
  		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue()+ " OVC DB:" +cell2.getStringCellValue(), Status.DONE); 
  		          	    
                        }
                    break;
                default:
                    if (cell1.getStringCellValue().equals(
                            cell2.getStringCellValue())) {
                    	System.out.println("Outbound "+cell1.getStringCellValue()+" After"+cell2.getStringCellValue());
                        equalCells = true;
                        out.append('\n');
  		          	    out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 	
  		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue() +" OVC DB:"+cell2.getStringCellValue(),  Status.DONE); 
                        break;
                        
                        
                    }
                    else 
                    { System.out.println("Outbound "+cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue());
                     out.append('\n');
		          	  out.println(cell1.getStringCellValue()+" OVC DB "+cell2.getStringCellValue()); 	
		          	report.updateTestLog("     Value","Outbound:" +cell1.getStringCellValue() +" OVC DB:"+cell2.getStringCellValue(), Status.DONE); 
                    }
                }
            } else 
            {
            	out.close();
            	return false;
            }
        } else {
        	out.close();
            //return false;
        	return true;
        }
        out.close();
        return equalCells;
    }
   //Method to get excel header value 
    public void getHeaderVal(String file1,int i,String resultfile) throws IOException
    {

    	FileInputStream fis = new FileInputStream(new File(file1));
    		    @SuppressWarnings("resource")
    		      HSSFWorkbook workbook = new HSSFWorkbook(fis);
    		      HSSFSheet spreadsheet = workbook.getSheetAt(0);
    		      HSSFRow row1 = spreadsheet.getRow(0);
    		      HSSFCell cell1 = row1.getCell(i);

    		          try
    		          {
    		        	  report.updateTestLog("Column Name", cell1.getStringCellValue(),Status.DONE);
    		              System.out.println(cell1.getStringCellValue());
    		              PrintWriter  out = new PrintWriter(new FileWriter(resultfile,true));
    		              out.append('\n');
    		          	  out.println(cell1.getStringCellValue());   		          	
    		          	  out.close();
    		          	 fis.close(); 
    		          }catch(Exception e)
    		          {
    		        	  e.printStackTrace();  
    		          }
    		      }
    //Below Code for Handling normal excel comparison 	for update	     
    public  void excelcomparisonUpdate(String filename1,String filename2,String resultfile)
	{
	try 
	{
        // get input excel files
		FileInputStream Input_doc1=new FileInputStream(filename1); 
		FileInputStream Input_doc2=new FileInputStream(filename2);
       // System.out.println(excellFile1);
        // Create Workbook instance holding reference to .xlsx file
        HSSFWorkbook workbook1 = new HSSFWorkbook(Input_doc1);
        HSSFWorkbook workbook2 = new HSSFWorkbook(Input_doc2);

        // Get first/desired sheet from the workbook
        HSSFSheet sheet1 = workbook1.getSheetAt(0);
        HSSFSheet sheet2 = workbook2.getSheetAt(0);

        // Compare sheets
        if(compareTwoSheetsUpdate(sheet1, sheet2,resultfile, filename1)) {
            System.out.println("\n\nThe two excel sheets are Equal");
            report.updateTestLog("Update", "two excel sheets are Equal", Status.PASS);
             
        } else {

            System.out.println("\n\nThe two excel sheets are Not Equal");
            report.updateTestLog("Update", "two excel sheets are not Equal", Status.FAIL);
        }
        
        //close files
        Input_doc1.close();
        Input_doc2.close();
      

    } 
	catch (Exception e) {
        e.printStackTrace();
    }

}
	// Compare Two Sheets
    public   boolean compareTwoSheetsUpdate(HSSFSheet sheet1, HSSFSheet sheet2,String resultfile,String filename1) throws IOException {
  	    
        int firstRow1 = sheet1.getFirstRowNum();
      //  int firstRow=firstRow1+1;
        int lastRow1 = sheet1.getLastRowNum();
        boolean equalSheets = true;
        for(int i=firstRow1+1; i <= lastRow1; i++) {
            
          //  System.out.println("\n\nComparing Row "+i);
        	System.out.println("Comparing Row "+i);
            HSSFRow row1 = sheet1.getRow(i);
            HSSFRow row2 = sheet2.getRow(i);
            if(!compareTwoRowsUpdate(row1, row2,resultfile,filename1)) {
                equalSheets = false;
                System.out.println("Row "+i+" - Not Equal");
                //report.updateTestLog("Row" +i, "Not Equal", Status.FAIL);
               // break;
            } else {
                System.out.println("Row "+i+" - Equal");
                //report.updateTestLog("Row" +i, "Equal", Status.PASS);
            }
        }
        return equalSheets;
    }

    // Compare Two Rows
    public   boolean  compareTwoRowsUpdate(HSSFRow row1, HSSFRow row2,String resultfile,String filename1) throws IOException {
    	// Setting Result file 
    //	String resultfile =System.getProperty("user.dir");
  	//    resultfile=resultfile+"\\target\\Results\\Query Results\\output.txt";
    	
        if((row1 == null) && (row2 == null)) {
            return true;
        } else if((row1 == null) || (row2 == null)) {
            return false;
        }
        
        int firstCell1 = row1.getFirstCellNum();
        int lastCell1 = row1.getLastCellNum();
        boolean equalRows = true;
        
        // Compare all cells in a row
        for(int i=firstCell1; i <= lastCell1; i++) {
            HSSFCell cell1 = row1.getCell(i);
            HSSFCell cell2 = row2.getCell(i);
            getHeaderVal(filename1,i,resultfile);
            //Setting result file
            PrintWriter  out = new PrintWriter(new FileWriter(resultfile,true));
            //Comparison starts
            if(!compareTwoCellsUpdate(cell1, cell2,resultfile)) {
                equalRows = false;
                System.err.println("       Cell "+i+" - Not Equal");
                System.out.println("       Cell "+i+" - Not Equal");  
                report.updateTestLog("", "Not Equal", Status.FAIL);
	             out.append('\n');
	          	 out.println("- Not Equal");   		          	
	          	 out.close();
              //  break;
            } else {
                System.out.println("       Cell "+i+" - Equal");
                report.updateTestLog("", "Equal", Status.PASS);
                 out.append('\n');
	          	 out.println(" -Equal");   		          	
	          	 out.close();
            }
        }
        return equalRows;
    }

    // Compare Two Cells
    @SuppressWarnings("null")
	public  boolean compareTwoCellsUpdate(HSSFCell cell1, HSSFCell cell2,String resultfile) throws IOException {
    	
  	   PrintWriter  out = new PrintWriter(new FileWriter(resultfile,true));
        if((cell1 == null) && (cell2 == null)) {
        	out.append('\n');
            out.close();
          	 out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 
          	report.updateTestLog( "     Value","Before:" +cell1.getStringCellValue() + " After: " +cell2.getStringCellValue(), Status.DONE); 
            return true;
        }
        else if((cell1 == null) || (cell2 == null)) {
        	out.append('\n');
            out.close();
          	 out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 
          	report.updateTestLog( "     Value","Before:" +cell1.getStringCellValue() + " After: " +cell2.getStringCellValue(), Status.DONE); 
            return false;
        }
        
        else if((cell1 == null) || (cell2.toString().equalsIgnoreCase(" "))) {
        	//out.close();
         
            out.append('\n');
            out.close();
          	 out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 
          	report.updateTestLog( "     Value","Before:" +cell1.getStringCellValue() + " After: " +cell2.getStringCellValue(), Status.DONE); 
            return true;
        }
        
        boolean equalCells = false;
        int type1 = cell1.getCellType();
        int type2 = cell2.getCellType();
        if (type1 == type2) {
            if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
                // Compare cells based on its type
                switch (cell1.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                    	System.out.println("Before "+cell1.getCellFormula()+" After: "+cell2.getCellFormula());
                    	 equalCells = true;  
                    	 out.append('\n');
     		          	 out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 
     		          	report.updateTestLog( "	Value","Before:" +cell1.getStringCellValue() + " After: " +cell2.getStringCellValue(), Status.DONE); 
                    }
                    else 
                    { System.out.println("Before "+cell1.getStringCellValue()+"After"+cell2.getStringCellValue());   
                      out.append('\n');
		          	  out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 
		          	report.updateTestLog("	Value","Before:" +cell1.getStringCellValue() + " After: " +cell2.getStringCellValue(), Status.DONE); 
		          	
                    }    
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (cell1.getNumericCellValue() == cell2
                            .getNumericCellValue()) {
                    	System.out.println("Before "+cell1.getNumericCellValue()+" After: "+cell2.getNumericCellValue());
                        equalCells = true;
                        out.append('\n');
    		          	out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 	
    		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue() + " After: " +cell2.getStringCellValue(), Status.DONE); 
    		          	
                    }
                    else 
                    {  System.out.println("Before "+cell1.getStringCellValue()+" After: "+cell2.getStringCellValue());   
                      out.append('\n');
		          	  out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue());
		          	report.updateTestLog( "     Value","Before:"+cell1.getStringCellValue()+ " After: " +cell2.getStringCellValue(), Status.DONE);
		          	  
                    }
                    break;	
                    
                   
                case HSSFCell.CELL_TYPE_STRING:
                	
                    if (cell1.getStringCellValue().equals(cell2
                            .getStringCellValue())) {
                    	System.out.println("Before:"+cell1.getStringCellValue()+" After: "+cell2.getStringCellValue());
                       equalCells = true;
                       //Code to update result file
  		              out.append('\n');
  		          	  out.println(cell1.getStringCellValue()+"Excel "+cell2.getStringCellValue()); 
  		          	report.updateTestLog( "     Value","Before:" +cell1.getStringCellValue() +" After: "+cell2.getStringCellValue(), Status.DONE); 
  		          	 
                    }
                    
                    else {
                    System.out.println("Before "+cell1.getStringCellValue()+" After:"+cell2.getStringCellValue());
                    //Code to update result file
		            out.append('\n');
		          	out.println("Before "+cell1.getStringCellValue()+"After:"+cell2.getStringCellValue());  
		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue() +" After: "+cell2.getStringCellValue(),Status.DONE);
		          
		          	}
                    break;
                    
                case HSSFCell.CELL_TYPE_BLANK:
                    if (cell2.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        equalCells = true;
                        out.append('\n');
    		          	  out.println(cell1.getStringCellValue()+" Excel "+cell2.getStringCellValue()); 
    		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue()+ " After: "+cell2.getStringCellValue(), Status.DONE);    
    		          	    
                    }
                    else {
                        System.out.println("Before "+cell1.getStringCellValue()+" After: "+cell2.getStringCellValue());
                        out.append('\n');
    		          	out.println(cell1.getStringCellValue()+" Excel"+cell2.getStringCellValue()); 	
    		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue()+ " After: " +cell2.getStringCellValue(), Status.DONE);    
    		          	equalCells = true;   
                    }
                    
                    break;
                   
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    if (cell1.getBooleanCellValue() == cell2
                            .getBooleanCellValue()) {
                    	System.out.println("Before "+cell1.getBooleanCellValue()+" After:"+cell2.getBooleanCellValue());
                        equalCells = true;
                        out.append('\n');
    		          	  out.println(cell1.getStringCellValue()+"Excel "+cell2.getStringCellValue()); 
    		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue()+ " After: " +cell2.getStringCellValue(),  Status.DONE);  
    		          	  
                    }
                    else 
                    {    System.out.println("Before "+cell1.getStringCellValue()+" After "+cell2.getStringCellValue());
                      out.append('\n');
		          	  out.println(cell1.getStringCellValue()+" Excel"+cell2.getStringCellValue()); 
		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue()+ " After: "+ cell2.getStringCellValue(),  Status.DONE); 
		          	  
                    }
                    break;

                case HSSFCell.CELL_TYPE_ERROR:
                    if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                    	System.out.println("Before "+cell1.getErrorCellValue()+" After: "+cell2.getErrorCellValue());
                        equalCells = true;
                        out.append('\n');
  		          	   out.println(cell1.getStringCellValue()+"Excel"+cell2.getStringCellValue()); 
  		          	report.updateTestLog("     Value","Before:" +cell1.getStringCellValue() +" After: " +cell2.getStringCellValue(),  Status.DONE); 
  		          	   
                    }
                    else 
                        {System.out.println("Before"+cell1.getStringCellValue()+"After"+cell2.getStringCellValue());
                        out.append('\n');
  		          	    out.println(cell1.getStringCellValue()+" Excel"+cell2.getStringCellValue()); 
  		          	report.updateTestLog("     Value","Before: " +cell1.getStringCellValue()+ " After: " +cell2.getStringCellValue(), Status.DONE); 
  		          	    
                        }
                    break;
                default:
                    if (cell1.getStringCellValue().equals(
                            cell2.getStringCellValue())) {
                    	System.out.println("Before:  "+cell1.getStringCellValue()+" After: "+cell2.getStringCellValue());
                        equalCells = true;
                        out.append('\n');
  		          	    out.println(cell1.getStringCellValue()+"Excel "+cell2.getStringCellValue()); 	
  		          	report.updateTestLog("     Value","Before: " +cell1.getStringCellValue() +" After: "+cell2.getStringCellValue(),  Status.DONE); 
                        break;
                        
                        
                    }
                    else 
                    { System.out.println("Before: "+cell1.getStringCellValue()+" After: "+cell2.getStringCellValue());
                     out.append('\n');
		          	  out.println(cell1.getStringCellValue()+"Excel "+cell2.getStringCellValue()); 	
		          	report.updateTestLog("     Value","Before: " +cell1.getStringCellValue() +" After: "+cell2.getStringCellValue(), Status.DONE); 
                    }
                }
            } else 
            {
            	out.close();
            	return false;
            }
        } else {
        	out.close();
            //return false;
        	return true;
        }
        out.close();
        return equalCells;
    }
       
}
