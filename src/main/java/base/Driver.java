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
	public static final String USERNAME = getProperty("saucelabsUSERNAME");
	public static final String ACCESS_KEY = getProperty("saucelabsACCESS_KEY");
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

//	public static void main(String[] args) {
//		initSauceLabs();
//	}
	
	public void setDriver(String browserType, String appUrl) {
		
		if( browserType.equalsIgnoreCase("firefox") ) {
			initFirefox();
		} else if( browserType.equalsIgnoreCase("chrome") ) {
			initChrome();
		} else if( browserType.equalsIgnoreCase("safari") ) {
			initSafari();
		} else if( browserType.equalsIgnoreCase("saucelabs") ) {
			initSauceLabs();
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
	
	private void initSauceLabs() {
		String browserType = getProperty("saucelabsBrowser");
		String browserVersion = getProperty("saucelabsBrowserVersion");
		String operatingSystem = getProperty("saucelabsOpratingSystem");
		
		System.out.println("browserType: " + browserType);
		System.out.println("browserVersion: " + browserVersion);
		System.out.println("operatingSystem: " + operatingSystem);
		System.out.println("URL: " + URL);
		System.out.println("appUrl: " + getProperty("appUrl"));
		
		try {
			caps = getDesiredCapabilities(browserType, browserVersion, operatingSystem);
			driver = new RemoteWebDriver(new URL(URL), caps);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private DesiredCapabilities getDesiredCapabilities(String browserType, String browserVersion, String operatingSystem) {
		if (browserType.equalsIgnoreCase("firefox")) {
			caps = DesiredCapabilities.firefox();
		} else if (browserType.equalsIgnoreCase("chrome")) {
			caps = DesiredCapabilities.chrome();
		} else if (browserType.equalsIgnoreCase("safari")) {
			caps = DesiredCapabilities.safari();
		} else {
			caps = DesiredCapabilities.firefox();
		}
		
		caps.setCapability("version", browserVersion);
		caps.setCapability("platform", operatingSystem);
		
		return caps;
	}
	
	private void setDriverProperty() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
}
