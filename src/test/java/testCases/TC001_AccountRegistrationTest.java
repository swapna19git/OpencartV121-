package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.Baseclass;

public class TC001_AccountRegistrationTest extends Baseclass {


	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("****Starting TC001_AccountRegistrationTest **** ");
		
		try
		{
		 HomePage hp=new HomePage(driver);
		 hp.clickMyAccount();
		 logger.info("****Clicked on MyAccount Link **** ");
		
		 hp.clickRegister();
		 logger.info("****Clicked on Register Link **** ");
	 
		 AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		 
		 logger.info("****providing customer details **** ");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");// randomly generated the email
		regpage.setTelephone(randomeNumber());
		
	    String password=randomeAlphaNumeric();
		
		regpage.setPassword (password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("****Validating expected message **** ");
		String confmsg=regpage.getConfirmationMsg();
	    if(confmsg.equals("Your Account Has Been Created!")) //gave wrong message to check for the errors
	        {
	    	Assert.assertTrue(true);
	        }
	    else
	        {
	    	logger.error("Test failed..");
			logger.debug("Debug logs..");//capture all debug logs
			Assert.assertTrue(false);
	         }
	    	
	    	//Assert.assertEquals(confmsg, "YES Your Account Has Been Created!");//if it fail catch will capture n show error and debug
	} //if any test fails ,we catch in try catch block
	
	catch(Exception e)
	     {
	    Assert.fail();//if test fails this will be captured
	      }
		logger.info("****Finished TC001_AccountRegistrationTest **** ");
	
	}	
}
