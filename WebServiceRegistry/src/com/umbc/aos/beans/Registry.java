package com.umbc.aos.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.umbc.aos.util.RegistryCleaner;

public class Registry {
	private static Map<String, List<WebServiceBean>> serviceMap;
	private static final String FILENAME = Registry.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "\\Registry.txt";
	private static final boolean enableCleaning = true;
	static
	{
		System.out.println("Initializing Registry..");
		//CreateFile(FILENAME);
		final File f = new File(Registry.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(f.getParentFile());
		serviceMap = loadChangesFromFile(); 
		if (null == serviceMap) {
			serviceMap = new HashMap<String, List<WebServiceBean>>();
		}
		if(enableCleaning) {
			//calling the cleaning service
			RegistryCleaner rc = new RegistryCleaner();
			Thread t = new Thread(rc);
			t.start();
		}
	}


	public static Map<String, List<WebServiceBean>> getServiceMap() {
		return serviceMap;
	}


	/**adding webservice to the map
	 * @param entry
	 * @return
	 */
	public static boolean add(WebServiceBean entry) {
		if (null != entry) {
			if (null != entry && serviceMap.containsKey(entry.getName())) {
				List<WebServiceBean> nodeList = serviceMap.get(entry.getName());
				if (!nodeList.contains(entry)) {
					nodeList.add(entry);
				}
				serviceMap.put(entry.getName().toLowerCase(), nodeList);

			}else {
				ArrayList<WebServiceBean> nodeList = new ArrayList<>();
				nodeList.add(entry);
				serviceMap.put(entry.getName().toLowerCase(), nodeList);

			}
			saveChangesToFile(serviceMap);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * remove node with specified IP address
	 * @param entry
	 * @return
	 */
	public static boolean remove(WebServiceBean entry) {

		if(null != entry) {
			for(String key:serviceMap.keySet()) {
				List<WebServiceBean> nodes= serviceMap.get(key);
				Iterator<WebServiceBean> it = nodes.iterator();
				while(it.hasNext()) {
					WebServiceBean wb = it.next();
					if(entry.getiPAddress().equalsIgnoreCase(wb.getiPAddress())) {
						nodes.remove(wb);
						break;
					}
				}
			}
			saveChangesToFile(serviceMap);
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * search Nodes providing specific service
	 * @param entry
	 * @return
	 */
	public static List<WebServiceBean> getValue(WebServiceBean entry) {
		System.out.println("in the get value "+ entry.getName());
		if(null != entry && null != entry.getName() && !"".equals(entry.getName())) {
			System.out.println(serviceMap);
			return serviceMap.get(entry.getName().toLowerCase());
		}else {
			return null;
		}
	}
	/*
public static void main(String[] args) {
	WebServiceBean wb = new WebServiceBean();
	wb.setiPAddress("0.0.0.0.0");
	wb.setName("add");

	WebServiceBean wb1 = new WebServiceBean();
	wb1.setiPAddress("0.0.0.0.0");
	wb1.setName("subtract");

	WebServiceBean wb3 = new WebServiceBean();
	wb3.setiPAddress("1.0.0.0.0");
	wb3.setName("add");

	WebServiceBean wb4 = new WebServiceBean();
	wb4.setiPAddress("1.0.0.0.0");
	wb4.setName("subtract");

	Registry.add(wb);
	Registry.add(wb1);
	Registry.add(wb3);
	Registry.add(wb4);

	System.out.println(Registry.serviceMap);
	wb3 = new WebServiceBean();
	wb3.setiPAddress("1.0.0.0.0");

	Registry.remove(wb3);
	System.out.println(Registry.serviceMap);

}*/
	private static Map<String, List<WebServiceBean>> loadChangesFromFile(){
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
			System.out.println("loadChangesFromFile" + serviceMap);

		} catch (IOException | ClassNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: Method " + "loadChangesFromFile" + Registry.class);
		}
		return serviceMap;
	}
	private static boolean saveChangesToFile(Map<String, List<WebServiceBean>> serviceMap) {
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
			System.out.println("saveChangesToFile" + serviceMap);
		} catch(IOException ex){
			ex.printStackTrace();
			System.out.println("Error: Method " + "saveChangesToFile" + Registry.class);
			return false;
		}
		return true;
	}
	private static boolean CreateFile(String fileName) {
		File file = new File(fileName);

		//Create the file
		try {
			if (file.createNewFile())
			{
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error:" + "CreateFile" + Registry.class);
			return false;
		}

	}
}
