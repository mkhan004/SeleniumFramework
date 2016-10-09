package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.ReadProperties;

public class Driver extends ReadProperties {
	public static WebDriver driver;
	
	public void setDriver(String browserType, String appUrl) {
		
		if( browserType.equalsIgnoreCase("firefox") ) {
			initFirefox();
		} else {
			initFirefox();
		}
		
		setDriverProperty();
		driver.get(appUrl);
	}

	private void initFirefox() {
		driver = new FirefoxDriver();
	}
	
	private void setDriverProperty() {
		driver.manage().window().maximize();
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
}
