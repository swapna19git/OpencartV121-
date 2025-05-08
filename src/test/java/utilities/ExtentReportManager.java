package utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration. Theme;

import testBase.Baseclass;

public class ExtentReportManager implements ITestListener {
public ExtentSparkReporter sparkReporter;
public ExtentReports extent;
public ExtentTest test;

String repName;

public void onStart(ITestContext testContext) //test method executed will be stored in this variable
{
	/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	Date dt=new Date();
    String currentdatetimestamp=df.format(dt);*/
   
	//instead use single line
	String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
	
	repName="Test-Report-" + timeStamp + ".html"; //first test report name + timestamp + html 
	sparkReporter =new ExtentSparkReporter(".\\reports\\" + repName);// specify location of the report

	sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
	sparkReporter.config().setReportName("opencart Functional Testing"); // name of report
	sparkReporter.config().setTheme(Theme.DARK);
	
	extent=new ExtentReports(); 
	extent.attachReporter(sparkReporter);
	extent.setSystemInfo("Application","opencart"); //below hard coded project specific details
	extent.setSystemInfo("Module","Admin"); //project details
	extent.setSystemInfo("Sub Module","Customers"); //project details
	extent.setSystemInfo("User Name",System.getProperty("user.name")); //current user of the system
	extent.setSystemInfo("Environemnt","QA"); //hardcoded
	
	String os=testContext.getCurrentXmlTest().getParameter("os");//testContext , get os from xml
	extent.setSystemInfo("Operating System", os);
	
	String browser = testContext.getCurrentXmlTest().getParameter("browser"); //testContext current test method ,current xml test  file,from os
	extent.setSystemInfo("Browser", browser);
	
	List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups(); //get CurrentXml get groups list collection info from include section of xml
	if(!includedGroups.isEmpty()) //if group info empty no return , if not empty return group  info
	{
	extent.setSystemInfo("Groups", includedGroups.toString());
	}
}

  public void onTestSuccess(ITestResult result) //on success,the Result from the name of the test class will be saved in reports
          {
        test=extent.createTest(result.getTestClass().getName());//to display results of test class name(TC001) in report Instead of all methods from that class
        test.assignCategory(result.getMethod().getGroups()); // to display groups in report
        test.log(Status.PASS,result.getName()+"got successfully executed"); //to display result message 
          }
  
  public void onTestFailure(ITestResult result)  //ON test failure
         {
    test=extent.createTest(result.getTestClass().getName()); //new test entry into report od createTest
    test.assignCategory(result.getMethod().getGroups());
   
    test.log(Status.FAIL, result.getName()+" got failed"); 
    test.log(Status. INFO, result.getThrowable().getMessage());
        
           try  //capture screenshot on ontestfailure 
           { 
	       String imgPath = new Baseclass().captureScreen(result.getName()); //created new object of Baseclass as the capture scrnmethod is not static  //added one method to capture screenshot to Baseclass from step8
           test.addScreenCaptureFromPath(imgPath); // capture
            }
         catch(IOException e1) //capture exception on screenshot not available
         {
          e1.printStackTrace();//predefined method to print error on console window
         }}
         
  public void onTestSkipped(ITestResult result)
        {
	  test=extent.createTest(result.getTestClass().getName());
	  test.assignCategory(result.getMethod().getGroups());
	  test.log(Status.SKIP, result.getName()+" got skipped");
	  test.log(Status.INFO, result.getThrowable().getMessage());
        }
 
  public void onFinish(ITestContext testContext)
      {
	 
	  extent.flush();
	 
	  String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
	  File extentReport=new File(pathOfExtentReport);
	  try
	  {
	   Desktop.getDesktop().browse(extentReport.toURI());//open report on browser automatically
	  } 
	  catch(IOException e) //if extent report not available error
	  {
	  e.printStackTrace();
	  }
      }		
      }


	  /*   //to send test reports to team by email 
	    try
	        { 
	        URL url = new 
	        URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		 // Create the email message
			ImageHtmlEmail email=new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("swapnakurre19@googlemail.com", "passownpassword"));
			email.setSSLOnConnect(true);
			email.setFrom("pavanoltraining@gmail.com"); //Sender
			email.setSubject("Test Results");
			email.setMsg("Please find Attached Report....");
			email.addTo("kishore.narla@gmail.com"); //Receiver
		    email.attach(url, "extent report", "please check report...");
			email.send(); // send the email
		    }
			catch(Exception e)
	        { 
		      e.printStackTrace();  
	        }*/

