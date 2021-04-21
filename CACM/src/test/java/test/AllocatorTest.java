package test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import com.util.frameworkutils.Util;

import supportlibraries.ParallelRunner;
import supportlibraries.SeleniumTestParameters;
import supportlibraries.TestCaseMain;

@RunWith(supportlibraries.Parallelized.class)
public class AllocatorTest extends TestCaseMain	
{	
	@Parameters(name="{1}")
	public static Iterable<Object []> data(){
		List<Object[]> params = new ArrayList <Object[]>();	
		int i = 0;
		double j = 0, cnt = 0;
		for(SeleniumTestParameters param : testInstancesToRun) {
			params.add (new Object[] { param, param.getCurrentTestcase() });
			i++;
			try
			{
				cnt = Double.parseDouble(param.getCount());			
			}catch(NullPointerException e){}
			j = j + cnt;
		}
		System.out.println("Tests selected for run : "+(int)j);
		
		String strTestCategoryToRun = System.getenv("TESTCATEGORY");
		if(strTestCategoryToRun == null){
			strTestCategoryToRun = properties.getProperty("TestCategory");
		}
		if(strTestCategoryToRun.toLowerCase().contains("auto") | strTestCategoryToRun.toLowerCase().contains("endofday")){			
		}else{
			Collections.shuffle(params);
		}
		
		return params;
	}

	private SeleniumTestParameters seleniumTestParameters;
	private String testName;
	
	public AllocatorTest (SeleniumTestParameters s, String testName){
		this.seleniumTestParameters=s;
		this.testName = testName;
	}
	@Rule public TestName name = new TestName();
	
	@Test
	public void executeTestBatch()
	{		
		Date startTime;
        Date endTime;	
        startTime = Util.getCurrentTime();
//		 System.out.println("Starting test: " + name.getMethodName());	
         
         
		ParallelRunner testRunner =	new ParallelRunner(this.seleniumTestParameters, summaryReport, "Run Manager", reportPath, RetryCount);		
		Map testresult=null;
		try
		{
			testresult=testRunner.call();				
		}
		catch (Exception e)
		{
//			System.out.println("Failure catched at Test Class level");
			e.printStackTrace();
	        Assert.fail( e.getMessage());
		}
	    try 
	    {				
				if(((String) testresult.get("TestStatus")).equalsIgnoreCase("failed"))
				{
//					 System.out.println("Test Method: " + name.getMethodName()+"Failed");
					Assert.fail(((String) testresult.get("ErrorDescription")));
					
				}
				else
					Assert.assertTrue(true);
		 } 
	    catch (Exception e) 
	    {
			e.printStackTrace();
	        Assert.fail( e.getMessage());
	    }			
	    finally
	    {
	    	if(((String) testresult.get("TestStatus")).equalsIgnoreCase("failed"))
	    	{
	    		strStatus = "false";
	    	}
            //calculating the running time for each test case
            endTime = Util.getCurrentTime();
            long timeDifference = endTime.getTime() - startTime.getTime();
            timeDifference /= 1000;    // to convert from milliseconds to seconds            
            if(timeDifference > 5400)
            {
                   System.out.println("The Test Method: " + name.getMethodName() + " is running for more than 90 mins");
                   Assert.fail();//break;
            }                    
                                              
         }
		
			if(frameworkParameters.getStopExecution()) 
			{
				Assert.fail();//break;
			}	

	}

}