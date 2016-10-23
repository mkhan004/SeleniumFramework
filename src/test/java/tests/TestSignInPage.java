package tests;

import org.testng.Assert;
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
		Assert.assertTrue(url.contains("/profiles.php"));
	}
}
