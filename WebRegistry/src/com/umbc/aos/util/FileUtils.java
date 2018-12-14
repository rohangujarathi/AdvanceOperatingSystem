package com.umbc.aos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.umbc.aos.beans.WebServiceBean;

public class FileUtils {
//	private static final String FILENAME = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "\\Registry4.txt";
	private static final String FILENAME = "Registry.txt";
	public static Map<String, List<WebServiceBean>> loadChangesFromFile(){
		Map<String, List<WebServiceBean>> serviceMap = null;
		try {
			//creating the file
			CreateFile(FILENAME);
			FileInputStream file = new FileInputStream(FILENAME); 
			ObjectInputStream in = new ObjectInputStream(file); 
			// Method for deserialization of object 
			serviceMap = (Map<String, List<WebServiceBean>>)in.readObject(); 
			in.close();
			file.close();
			//log
			System.out.println("WebRegistry: loadChangesFromFile" + serviceMap);

		} catch (IOException | ClassNotFoundException e) {

			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("WebRegistory: Error: Method " + "loadChangesFromFile" + FileUtils.class);
		}
		return serviceMap;
	}
	public static boolean saveChangesToFile(Map<String, List<WebServiceBean>> serviceMap) {
		try{
			//Creating the file
			CreateFile(FILENAME);
			//Saving of object in a file 
			FileOutputStream file = new FileOutputStream(FILENAME); 
			ObjectOutputStream out = new ObjectOutputStream(file); 

			// Method for serialization of object 
			out.writeObject(serviceMap); 

			out.close(); 
			file.close(); 

			//log
			System.out.println("WebRegistry: saveChangesToFile" + serviceMap);
		} catch(IOException ex){
			//ex.printStackTrace();
			System.out.println("WebRegistry: Error: Method " + "saveChangesToFile" + FileUtils.class);
			return false;
		}
		return true;
	}
	public static boolean CreateFile(String fileName) {
		File file = new File(fileName);

		//Create the file
		try {
			if (file.createNewFile())
			{
				return true;
			} else {
//				System.out.println(file.getAbsolutePath());
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("WebRegistry: Error:" + "CreateFile" + FileUtils.class);
			return false;
		}

	}


	public static String getProperty(String property) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = FileUtils.class.getClassLoader().getResourceAsStream("registry.properties"); 
			prop.load(input);
			// get the property value and print it out
			return prop.getProperty(property);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	public static boolean isAlive(String ip, int port) {
		try {
			Socket s = new Socket();
			SocketAddress addr = new InetSocketAddress(ip, port);
			System.out.println("connecting " + ip);
			s.connect(addr, 2000);
			return true;
		} catch (Exception ex) {
			return false;
		}
		
	}
}

