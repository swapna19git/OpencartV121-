package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;//Log4j
import org.apache.logging.log4j.LogManager; //Log4j

public class Baseclass {
	
public static WebDriver driver; //made driver as static from step 8.4,to be accessible for the new abject created for capturescreenshot method in Extent Report utilities 
public Logger logger; //Log4j
public Properties p;


   @SuppressWarnings("deprecation")
   @BeforeClass(groups= {"Sanity","Regression","Master"})
   @Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException
	{
	        //loading config.properties file
	        FileReader file=new FileReader("./src//test//resources//config.properties");
	        p=new Properties(); //create An object for properties p (file)
	        p.load(file);
	   
	        logger=LogManager.getLogger(this.getClass());//LOG4J2//dynamically call the current class and store in variable.
	
	   //OS	 if remote execution 
	        if(p.getProperty("execution_env").equalsIgnoreCase("remote"))//from config.properties file /resourse
	        {
	        	DesiredCapabilities capabilities=new DesiredCapabilities();
	        	
	        	if(os.equalsIgnoreCase("windows")) //xml file
	        	{
	        	capabilities.setPlatform(Platform.WIN11);//for windows operating system
	        	}
	        	else if (os.equalsIgnoreCase("linux")) 
	        	{
	        		capabilities.setPlatform(Platform.LINUX);
	        	}
	        	else if (os.equalsIgnoreCase("mac")) 
	        	{
	        		capabilities.setPlatform(Platform.MAC);
	        	}
	        	else
	        	{
	        		System.out.println("no matching os");
	        		return;
	        	}
	        
	       
	      //browser
	        	switch(br.toLowerCase())
	        	{
	        	case "chrome": capabilities.setBrowserName("chrome");break;
	         	case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
	         	case "firefox": capabilities.setBrowserName("firefox");break;
				default:System.out.println("No Matching Browser");return;//if invalid browser exit from the total test,instead of executing rest of the code
	        	}
	         driver=new RemoteWebDriver(new URL("http://192.168.1.231:4444"), capabilities);
	        }
	        
        if(p.getProperty("execution_env").equalsIgnoreCase("local"))
           {
	        //for local execution use this below 
	        switch(br.toLowerCase())
			{
			case"chrome":driver=new ChromeDriver();break;
			case"edge":driver=new EdgeDriver();break;
			case"firefox":driver=new FirefoxDriver();break;
			default:System.out.println("Invalid browser name..");return;//if invalid browser exit from the total test,instead of executing rest of the code
			}
           }
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
			driver.get(p.getProperty("appURL"));//reading URL from properties file
			driver.manage().window().maximize();		
           }

	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	@SuppressWarnings("deprecation")
	public String randomeString()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}

	@SuppressWarnings("deprecation")
	public String randomeNumber()
	{
		String generatedstring=RandomStringUtils.randomNumeric(10);
		return generatedstring;
	}

	@SuppressWarnings("deprecation")
	public String randomeAlphaNumeric()
	     {
		String generatedstring=RandomStringUtils.randomNumeric(3);
		String generatednumber=RandomStringUtils.randomNumeric(3);
		return (generatedstring+"@"+generatednumber);
	     }
	
	public String captureScreen(String tname) throws IOException //new method to capture screenshot with method name tname
	     {
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()); //capture screenshot with timestamp
		
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver; //special interface 
		File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname +"_"+ timeStamp +".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile); //renamed to targetfile
		
		return targetFilePath; //return path of targetfile to use in extent report without return it will be available
	
}}
