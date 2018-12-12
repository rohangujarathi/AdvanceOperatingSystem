package com.umbc.aos.divide;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
	public static String getProperty(String property) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = Utils.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			return prop.getProperty(property);
		}
		catch (IOException e) {
			// TODO: handle exception
		}
		finally {
			if(input!=null) {
				try {
				input.close();
				}
				catch (IOException e) {
					// TODO: handle exception
				}
			}
		}
		return "";
	}
}
