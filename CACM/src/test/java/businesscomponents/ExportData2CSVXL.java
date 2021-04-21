package businesscomponents;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
public class ExportData2CSVXL {

	public ResultSet rset;
    public String filename;
    public Boolean colomnName;
    public String charSep;  
    //Method to export SQL result in excel
    public void createFileCsvXL(ResultSet rset, String filename) throws Exception {
       // FileWriter cname = null;
    	FileInputStream Input_doc=new FileInputStream(filename);
    	HSSFWorkbook Workbook = new HSSFWorkbook(Input_doc);
        HSSFSheet desSheet = Workbook.getSheetAt(0);
       // HSSFSheet desSheet1 = Workbook.createSheet("After");
        // Code to clear row
        try {

        	
            int clearrow=desSheet.getLastRowNum();  
            for(int col1=0 ;col1< clearrow;col1++) {
            	 desSheet.removeRow(desSheet.getRow(col1));
            }
           System.out.println(clearrow +"Rows cleared Successfully");
           
                // WRITE COLOMN NAME
           Thread.sleep(5000);
            ResultSetMetaData rsmd = rset.getMetaData();
          //  cname = new FileWriter(filename);
            int columnsNumber = rsmd.getColumnCount();
            Row desRow1 = desSheet.createRow(0);
            for(int col=0 ;col < columnsNumber;col++) {
                Cell newpath = desRow1.createCell(col);
                newpath.setCellValue(rsmd.getColumnLabel(col+1));
            }
            System.out.println(columnsNumber +"Column name  Successfully");
            // Removing Previous values in excel 
            
            
            // WRITE DATA
            Thread.sleep(5000);
                while(rset.next()) {
                    System.out.println("Row number" + rset.getRow() );
                    Row desRow = desSheet.createRow(rset.getRow());
                    for(int col1=0 ;col1 < columnsNumber;col1++) {
                        Cell newpath1 = desRow.createCell(col1);
                        newpath1.setCellValue(rset.getString(col1+1));  
                    }
                    FileOutputStream fileOut = new FileOutputStream(filename);
                    Workbook.write(fileOut);
                    fileOut.close();
                   
                }
                System.out.println(columnsNumber +"SQL results updated Successfully");
            


        } catch (Exception e) {
            e.printStackTrace();

        } 

    }    
    //Method that exports excel results after update
    public void createFileCsvXLAfter(ResultSet rset, String filename) throws Exception {
        // FileWriter cname = null;
    	FileInputStream Input_doc=new FileInputStream(filename);
    	HSSFWorkbook Workbook = new HSSFWorkbook(Input_doc);
        HSSFSheet desSheet = Workbook.getSheetAt(1);
        String Shtname=  Workbook.getSheetName(1);
        System.out.println(Shtname +"Created Successfully");
     //	HSSFWorkbook Workbook = new HSSFWorkbook();
      //   HSSFSheet desSheet = Workbook.getSheet("After");
         try {
        	  // Removing Previous values in excel 
        	 
            int clearrow=desSheet.getLastRowNum();
             for(int col1=0 ;col1< clearrow;col1++) {
            	 desSheet.removeRow(desSheet.getRow(col1));
            }
            System.out.println(clearrow +"Rows cleared Successfully");
          
                 // WRITE COLOMN NAME
             Thread.sleep(5000);
             ResultSetMetaData rsmd = rset.getMetaData();
           //  cname = new FileWriter(filename);
             int columnsNumber = rsmd.getColumnCount();
             Row desRow1 = desSheet.createRow(0);
             Thread.sleep(5000);
             for(int col=0 ;col < columnsNumber;col++) {
                 Cell newpath = desRow1.createCell(col);
                 newpath.setCellValue(rsmd.getColumnLabel(col+1));
             }
             
        
             // WRITE DATA in to cell
                 Thread.sleep(5000);
                 while(rset.next()) {
                     System.out.println("Row number" + rset.getRow() );
                     Row desRow = desSheet.createRow(rset.getRow());
                     for(int col1=0 ;col1 < columnsNumber;col1++) {
                         Cell newpath1 = desRow.createCell(col1);
                         newpath1.setCellValue(rset.getString(col1+1));  
                     }
                     FileOutputStream fileOut1 = new FileOutputStream(filename);
                     Workbook.write(fileOut1);
                   //  fileOut1.close();
                    
                 }
               
             


         } catch (Exception e) {
             e.printStackTrace();

         } 
    }
//Method to export result
    public void createFileCsvXL2(ResultSet rset, String filename) throws Exception {
        // FileWriter cname = null;
     	FileInputStream Input_doc=new FileInputStream(filename);
     	HSSFWorkbook Workbook = new HSSFWorkbook(Input_doc);
         HSSFSheet desSheet = Workbook.getSheetAt(0);
        // HSSFSheet desSheet1 = Workbook.createSheet("After");
         // Code to clear row
         try {

         	
          
            
                 // WRITE COLOMN NAME
            Thread.sleep(5000);
             ResultSetMetaData rsmd = rset.getMetaData();
           //  cname = new FileWriter(filename);
             int columnsNumber = rsmd.getColumnCount();
             Row desRow1 = desSheet.getRow(0);
             int LatsCol1 =desRow1.getLastCellNum();
             int totcolmn=columnsNumber+LatsCol1;
             int col2=1;
             int col3=1;
             for(int col=LatsCol1 ;col < totcolmn;col++) {
            	
                 Cell newpath = desRow1.createCell(col);
                 newpath.setCellValue(rsmd.getColumnLabel(col2));
                 System.out.println("\nCurrent Element :" + rsmd.getColumnLabel(col2));
                 FileOutputStream fileOut2 = new FileOutputStream(filename);
                 Workbook.write(fileOut2);
                 col2=col2+1;
                 
             }
             System.out.println(columnsNumber +"Column name created  Successfully");
             // Removing Previous values in excel 
             
             
             // WRITE DATA
             Thread.sleep(5000);
                 while(rset.next()) {
                     System.out.println("Row number" + rset.getRow() );
                     Row desRow = desSheet.getRow(1);
                     for(int col1=LatsCol1 ;col1 < totcolmn;col1++) {
                    	 
                         Cell newpath1 = desRow.createCell(col1);
                         newpath1.setCellValue(rset.getString(col3)); 
                         System.out.println("\nCurrent Element Value :" + rset.getString(col3));
                         FileOutputStream fileOut3 = new FileOutputStream(filename);
                         Workbook.write(fileOut3);
                         col3=col3+1;
                     }
                    
                    // fileOut2.close();
                    
                 }
                 System.out.println(columnsNumber +"SQL results updated Successfully");
             


         } catch (Exception e) {
             e.printStackTrace();

         } 

     }    

}
