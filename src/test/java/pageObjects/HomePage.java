package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

	//PAGE OBJECT MODEL class
	//constructor //name should be equal to class name
	
	public HomePage(WebDriver driver)
	{
     super(driver);
	}
	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement InkMyaccount;
	
	@FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Register']")
	WebElement InkRegister;
	
	@FindBy(linkText = "Login") // Login link added in step5
	WebElement linkLogin;
	
	 public void clickMyAccount() throws InterruptedException
      {
	   InkMyaccount.click();
	   Thread.sleep(3000);
	   }
	 
	 public void clickRegister()
	   {
	    InkRegister.click();
	   }
	 public void clickLogin()
	   {
	   linkLogin.click();
	   }
	 
	 
	 
}
