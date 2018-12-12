package com.umbc.aos.threads;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.NodeList;

import com.umbc.aos.bean.LoadInformation;
import com.umbc.aos.ws.WebServiceBean;
import com.umbc.aos.ws.WebServiceClient;

public class UpdateFromRegistry implements Runnable{
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//connecting to webservice
		System.out.println("LoadBalancer: Connecting to registry for updating webservices details");
		while(true) {
			try {
				SOAPMessage response = null;
				String[] registries = Utils.getProperty("config").split(",");
				int count = 0;
				for(String ip:registries) {
					String registrywsdl = "http://"+ ip + "/WebRegistry/Registry?wsdl";
					if(isRegistryReachable(ip.split(":")[0])) {
						response = WebServiceClient.getallServicesFromRegistry(registrywsdl);
		            	break;
					}
					else {
						count++;
						System.out.println("Registry with ip and port " +ip+ " is not reachable" );
					}
				}
				if(count==registries.length) {
					System.out.println("All the registries are currently down. Please try again later");
				}
				
				SOAPBody body = response.getSOAPBody();
				List<WebServiceBean> wblist = fetchDetailsFromResponse(body);
				LoadInformation.addAllWebService(wblist);
				
			}catch(Exception e) {
				System.out.println("LoadBalancer: Connection failed connecting again in 5 seconds");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isRegistryReachable(String ip) {
		try {
		Socket soc = new Socket();
		SocketAddress endpoint = new InetSocketAddress(ip, 8080);
		soc.connect(endpoint, 1000);
		soc.close();
		return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public static List<WebServiceBean> fetchDetailsFromResponse(SOAPBody body){
		//		body.getFirstChild().getFirstChild().getTextContent();
		NodeList list = body.getFirstChild().getFirstChild().getChildNodes();
		List<WebServiceBean> wbList =  new ArrayList<>();
		for(int i = 0; i < list.getLength();i++) {
			NodeList node = list.item(i).getChildNodes();
			WebServiceBean wb =  new WebServiceBean();
			for(int j = 0; j< node.getLength();j++) {
				if("name".equalsIgnoreCase(node.item(j).getNodeName())) {
					wb.setName(node.item(j).getNodeValue());
				}else if("IPAddress".equalsIgnoreCase(node.item(j).getNodeName())) {
					wb.setIPAddress(node.item(j).getNodeValue());
				}else if("WSDLLocation".equalsIgnoreCase(node.item(j).getNodeName())) {
					wb.setWSDLLocation(node.item(j).getNodeValue());
				}
			}
			wbList.add(wb);
		}

		return wbList;

	}

}
