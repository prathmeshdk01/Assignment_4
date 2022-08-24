package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	//Properties properties;
	
	
	public String getProperty(String propName) throws IOException
	{
		FileReader fileReader = new FileReader("src\\test\\resources\\config\\config.properties");
		Properties properties = new Properties();
		properties.load(fileReader);
		return properties.getProperty(propName);
	}

}
