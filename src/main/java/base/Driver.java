package base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import utils.ReadProperties;

public class Driver extends ReadProperties {
	public static WebDriver driver;
	static DesiredCapabilities caps;
	public static final String USERNAME = getSauceLabsProperty("USERNAME");
	public static final String ACCESS_KEY = getSauceLabsProperty("ACCESS_KEY");
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	
	public void setDriver(String browserType, String appUrl) {
		if( browserType.equalsIgnoreCase("firefox") ) {
			initFirefox();
		} else if( browserType.equalsIgnoreCase("chrome") ) {
			initChrome();
		} else if( browserType.equalsIgnoreCase("safari") ) {
			initSafari();
		} else {
			initFirefox();
		}
		
		setDriverProperty();
		driver.get(appUrl);
	}

	private void initFirefox() {
		driver = new FirefoxDriver();
	}
	
	private void initChrome() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
		driver = new ChromeDriver();
	}
	
	/**
	 * To support Safari you must need to install `WebDriver` extension
	 * in your Safari browser.
	 * 
	 * To Install `WebDriver` extension:
	 *  - visit: http://www.seleniumhq.org/download/
	 *  - download: SafariDriver (latest release)
	 *  - install: install SafariDriver, trust and enable as an extension in your browser
	 */
	private void initSafari() {
		driver = new SafariDriver();
	}
	
	private void setDriverProperty() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public void setSauceLabs(String appUrl) {
		String browserType = getSauceLabsProperty("browser");
		String browserVersion = getSauceLabsProperty("browserVersion");
		String operatingSystem = getSauceLabsProperty("opratingSystem");
				
		try {
			caps = setDesiredCapabilities(browserType, browserVersion, operatingSystem);
			driver = new RemoteWebDriver(new URL(URL), caps);
			driver.get(appUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private DesiredCapabilities setDesiredCapabilities(String browserType, String browserVersion, String operatingSystem) {
		if (browserType.equalsIgnoreCase("firefox")) {
			caps = DesiredCapabilities.firefox();
		} else if (browserType.equalsIgnoreCase("chrome")) {
			caps = DesiredCapabilities.chrome();
		} else if (browserType.equalsIgnoreCase("safari")) {
			caps = DesiredCapabilities.safari();
		} else if (browserType.equalsIgnoreCase("IE")) {
			caps = DesiredCapabilities.internetExplorer();
		} else {
			caps = DesiredCapabilities.firefox();
		}
		
		caps.setCapability("version", browserVersion);
		caps.setCapability("platform", operatingSystem);
		
		return caps;
	}
	
	
}
