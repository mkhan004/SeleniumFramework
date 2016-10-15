package base;

import org.testng.annotations.BeforeSuite;

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

}
