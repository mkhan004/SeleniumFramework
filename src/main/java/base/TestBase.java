package base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(listener.EliteListener.class)
public class TestBase extends Driver {
	
	String browserType = getProperty("browser");
	String appUrl = getProperty("appUrl");
	
	@BeforeSuite
	public void setUp() {
		try {
			
			setDriver(browserType, appUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}
