package base;

import org.testng.annotations.BeforeSuite;

public class TestBase extends Driver {
	
	String browserType = "firefox";
	String appUrl = "http://qa.elitecareer.net";
	
	@BeforeSuite
	public void setUp() {
		try {
			
			setDriver(browserType, appUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
