package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;

public class TestSignInPage extends TestBase {
	
	@BeforeClass
	public void classSetUp() {
		menuBar.signInLink.click();
	}

	@Test
	public void testSignInWithValidCredentials() {
		signInPage.signIn(getProperty("email"), getProperty("password"));
		String url = signInPage.getCurrentUrl();
		
		
		menuBar.logoutLink.click();
		menuBar.signInLink.click();
		
		Assert.assertTrue(url.contains("/profile.php"));
	}
	
	@Test
	public void testSignInWithValidEmailInvalidPassword() {
		signInPage.signIn(getProperty("email"), "invalid");
		String url = signInPage.getCurrentUrl();
		Assert.assertTrue(url.contains("/signin.php"));
		Assert.assertEquals(signInPage.errorWebElementList.get(0).getText(), "* Email or Password is incorrect.");
	}
	
	@AfterClass
	public void classTearDown() {
		menuBar.jobsearchLink.click();
	}
}
