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
import java.util.concurrent.ConcurrentHashMap;

import com.umbc.aos.util.FileUtils;
import com.umbc.aos.util.RegistryCleaner;
import com.umbc.aos.util.RegistryUtil;

public class Registry {
	//map containing entry for each service as <Service Name> : <name, ipaddress, wsdllocation>
	private static Map<String, List<WebServiceBean>> serviceMap;
	private static final String FILENAME = Registry.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "\\Registry4.txt";
	private static final boolean enableCleaning = true;
	static
	{
		System.out.println("WebRegistry: Initializing Registry..");
		//CreateFile(FILENAME);
		final File f = new File(Registry.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(f.getParentFile());
		serviceMap = FileUtils.loadChangesFromFile(); 
		if (null == serviceMap) {
			serviceMap = new ConcurrentHashMap<String, List<WebServiceBean>>();
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
	 * @throws Exception 
	 */
	public static boolean add(WebServiceBean entry) throws Exception {
		if (null != entry) {
			entry.setName(entry.getName().toLowerCase());
			if (serviceMap.containsKey(entry.getName())) {
				List<WebServiceBean> nodeList = serviceMap.get(entry.getName());
				if (!nodeList.contains(entry)) {
					nodeList.add(entry);
				}
				serviceMap.put(entry.getName(), nodeList);

			}else {
				ArrayList<WebServiceBean> nodeList = new ArrayList<>();
				nodeList.add(entry);
				serviceMap.put(entry.getName(), nodeList);

			}
			
			FileUtils.saveChangesToFile(serviceMap);
			RegistryUtil.sendInformationToRegistry(entry);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param entry
	 * @return
	 */
	public static boolean addForWebRegistry(WebServiceBean entry) {
		if (null != entry) {
			entry.setName(entry.getName().toLowerCase());
			if (serviceMap.containsKey(entry.getName())) {
				List<WebServiceBean> nodeList = serviceMap.get(entry.getName());
				if (!nodeList.contains(entry)) {
					nodeList.add(entry);
				}
				serviceMap.put(entry.getName(), nodeList);

			}else {
				ArrayList<WebServiceBean> nodeList = new ArrayList<>();
				nodeList.add(entry);
				serviceMap.put(entry.getName(), nodeList);

			}
			
			FileUtils.saveChangesToFile(serviceMap);
			
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
			FileUtils.saveChangesToFile(serviceMap);
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
	public static List<WebServiceBean> getService(WebServiceBean entry) {
		
		System.out.println("in the get value "+ entry.getName());
		if(null != entry && null != entry.getName() && !"".equals(entry.getName())) {
			entry.setName(entry.getName().toLowerCase());
			System.out.println(serviceMap);
			return serviceMap.get(entry.getName());
		}else {
			return null;
		}
	}
}
