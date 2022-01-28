package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
	
	private static String filePath;
	static Properties prop = new Properties(); 

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		PropertyFile.filePath = filePath;
	}
	
	public static Properties getPropertiesFile() {
		try {
			prop.load(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
		return prop;
	}

}
