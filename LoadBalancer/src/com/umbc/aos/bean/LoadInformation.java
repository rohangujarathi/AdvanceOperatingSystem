package com.umbc.aos.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umbc.aos.ws.WebServiceBean;

public class LoadInformation {
	private static Map<String, List<String>> wsdlinfo = new HashMap<String, List<String>>();
	private static Map<String, Integer> loadinfo = new HashMap<String, Integer>();
	public static Map<String, List<String>> getWsdlinfo() {
		return wsdlinfo;
	}
	public static void setWsdlinfo(Map<String, List<String>> wsdlinfo) {
		LoadInformation.wsdlinfo = wsdlinfo;
	}

	public static String getTheServiceNode(String service) {
		if(null != service && !"".equals(service)) {
			service = service.toLowerCase();
			if(LoadInformation.getWsdlinfo().containsKey(service)) {
				List<String> listOfAvailableNodes = wsdlinfo.get(service);
				//round robin load balancing
				System.out.println(listOfAvailableNodes);
				int index = loadinfo.get(service);
				//if the servers were removed then
				if(index >= wsdlinfo.get(service).size()) {
					index = index%(listOfAvailableNodes.size());
				}
				
				loadinfo.put(service, (index +1) % listOfAvailableNodes.size());
				return listOfAvailableNodes.get(index);

			}}
		return null;
	}
	//taking all the webservices available at webregistry
	public static boolean addAllWebService(List<WebServiceBean> listOfAllWebService) {
		for(WebServiceBean wsb: listOfAllWebService) {
			if(wsdlinfo.containsKey(wsb.getName())) {
				List<String> list = wsdlinfo.get(wsb.getName());
				list.add(wsb.getWSDLLocation());
			}else {
				List<String> list = new ArrayList<String>();
				list.add(wsb.getWSDLLocation());
				wsdlinfo.put(wsb.getName(), list);
				loadinfo.put(wsb.getName(), 0);
			}

		}
		return true;
	}

}
