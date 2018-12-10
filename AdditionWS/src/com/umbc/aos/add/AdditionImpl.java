package com.umbc.aos.add;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import javax.jws.WebService;
import com.umbc.aos.ws.WebServiceClient;

@WebService(endpointInterface = "com.umbc.aos.add.AdditionInterface")
public class AdditionImpl implements AdditionInterface{
	static {
		try {
			String registry1 = "http://192.168.0.7:8080/WebRegistry/Registry?wsdl";
			String registry2 = "http://192.168.0.12:8080/WebRegistry/Registry?wsdl";
			
            if(isRegistryReachable("192.168.0.50")) {
            	WebServiceClient.addServicetoRegistry(registry1, "add");
            }
            else if(isRegistryReachable("192.168.0.60")) {
            	WebServiceClient.addServicetoRegistry(registry2, "add");
            }
            else {
            	System.out.println("All the registries are currently down. Please try again later");
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("AdditionImpl static block failed");
			
//			e.printStackTrace();
		}
	}
	
	public static boolean isRegistryReachable(String ip) {
//		boolean reachable = InetAddress.getByName(ip).isReachable(100);
//		InetAddress address = InetAddress.getByName(ip);
//		boolean reachable = address.isReachable(100);
//        return reachable;
		try {
		Socket soc = new Socket();
		SocketAddress endpoint = new InetSocketAddress(ip, 8080);
		soc.connect(endpoint, 1000);
		return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	@Override
	public int addNumbers(int a, int b){
		return a+b;
	}
}
