package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.Baseclass;
import utilities.DataProviders;

/*Data is valid   -login success-test pass-logout 
                  -login failed-test fail
  Data is invalid -login success-test fail logout
                  -login failed-test pass
*/
	
public class TC003_LoginDDT extends Baseclass {
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="DataDriven") //getting from utilites data providers
	public void verify_loginDDT(String email,String pwd,String exp) throws InterruptedException
	{
		logger.info("***..Starting TC003_LoginDDT***..");
		try 
		{
		//HomePage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Valid"))//if data valid
		{
			if(targetPage==true) //MyAcc page displayed
			{
				macc.clickLogout();
				Assert.assertTrue(true); //if true then it will logout,since no execution after Assert method
				
			}
			else   //this will execute if the targetpage is false ..means login fail
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("InValid"))//if data Invalid
		{
			if(targetPage==true) //MyAcc page displayed,its negetive testing
			{
				macc.clickLogout();
				Assert.assertTrue(false); //if false then it will logout,since no execution after Assert method
			}
			else      //this will execute if the targetpage is false ..means login fail since gave Invalid data.
			{                                //Its expected fail, Hence the Test passed
				Assert.assertTrue(true);
			}	
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		Thread.sleep(5000);
		logger.info("***..Finished TC003_LoginDDT***..");	
}
}
