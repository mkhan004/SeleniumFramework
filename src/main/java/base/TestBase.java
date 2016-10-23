package base;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import pages.MenuBar;
import pages.SignInPage;

@Listeners(listener.EliteListener.class)
public class TestBase extends Driver {
	
	String browserType = getProperty("browser");
	String appUrl = getProperty("appUrl");
	
	// object for all classes in `pages` package
	protected static SignInPage signInPage;
	protected static MenuBar menuBar;
	
	@BeforeSuite
	public void setUp() {
		try {
			
			setDriver(browserType, appUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		signInPage = PageFactory.initElements(driver, SignInPage.class);
		menuBar = PageFactory.initElements(driver, MenuBar.class);
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}
