package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadProperties {
		
	public static String getProperty(String key) {
		String value = null;
		
		Properties prop = loadProperties("testRun.properties");
		value = prop.getProperty(key);
		
		System.out.println(prop.getProperty("browser"));
		if(value == null) {
			if(prop.getProperty("browser").equalsIgnoreCase("saucelabs")) {
				String testEnv = prop.getProperty("testEnv");
				if (!key.contains("saucelabs")) {
				prop = loadProperties(testEnv + ".properties");
				} else {
					prop = loadProperties("saucelabs.properties");
				}
				
				if(prop != null) {
					value = prop.getProperty(key);
				}
				
			} else {
				if( value == null ) {
					String testEnv = prop.getProperty("testEnv");
					prop = loadProperties(testEnv + ".properties");
					if(prop == null) {
						prop = loadProperties("qa.properties");
					}
					
					if(prop != null) {
						value = prop.getProperty(key);
					}
				}
			}
		
		}
		
		return value;
	}

	// load entire content of properties file
	private static Properties loadProperties( String propertyFileName ) {
		Properties property = null;
		
		try {
			File file = new File("./src/main/resources/properties/" + propertyFileName);
			FileInputStream fileStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fileStream);
			property = prop;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;
	}
}
