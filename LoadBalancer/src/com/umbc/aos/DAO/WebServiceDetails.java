package com.umbc.aos.DAO;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import com.umbc.aos.bean.LoadInformation;
import com.umbc.aos.ws.ListofWebserviceBean;
import com.umbc.aos.ws.RegistryService;
import com.umbc.aos.ws.RegistryServiceImplService;
import com.umbc.aos.ws.WebServiceBean;

public class WebServiceDetails {

	public boolean getAllWebServicesFromRegistry() {
		RegistryServiceImplService regis = new RegistryServiceImplService();
		RegistryService service = regis.getRegistryServiceImplPort();
		ListofWebserviceBean allWebService = service.getAllWebServiceDetails();
		List<WebServiceBean> allWebServiceList = allWebService.getListOfBeans();
		System.out.println("LoadBalancer: " + allWebServiceList);
		boolean success = LoadInformation.addAllWebService(allWebServiceList);
		return success;
		
	}
	
	public static void main(String[] args) {
		RegistryServiceImplService regis = new RegistryServiceImplService();
		RegistryService service = regis.getRegistryServiceImplPort();
		WebServiceBean wb = new WebServiceBean();
		try {
			InetAddress ip = InetAddress.getLocalHost();
			wb.setIPAddress("192.168.0.7:8080");
			wb.setName("Add");
			wb.setWSDLLocation("http://192.168.0.7:8080/AdditionWS/AddNumbers?wsdl");
			System.out.println("abc");
			service.addService(wb);
			System.out.println(wb.getIPAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	}

