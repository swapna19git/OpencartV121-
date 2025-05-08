package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) //constructor
	{
		super(driver);
	}
	
	//My Account page
	@FindBy(xpath="//h2[normalize-space()='My Account']") //MyAccount Page heading
	WebElement msgHeading;  //we don't validate in page object class so we can do below action method verify
	      
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']") //added in step6
	WebElement InkLogout;
	
	
	
	//verify my account is displayed either true or false
	public boolean isMyAccountPageExists()
	{
     	try 
	    {
		return(msgHeading.isDisplayed());	//if displyed it will return true,
		                                     //ifnot dispalyed the catch block will return the exception e and false.
	    }
	   catch(Exception e)
	    {
	    return false;
	    }
	}
	
	public void clickLogout()
	{
		InkLogout.click();
	}
	
	
	
	
	
	
}
