package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
//all Page object classes in the project need constructor so created a parent class which can extends to all child classes
	
	WebDriver driver;//drivaer variable
	//PAGE OBJECT MODEL class
	//constructor //name should be equal to class name
	
	public BasePage(WebDriver driver)
	{
	this.driver=driver;
	PageFactory.initElements(driver, this);
	}
	
}
