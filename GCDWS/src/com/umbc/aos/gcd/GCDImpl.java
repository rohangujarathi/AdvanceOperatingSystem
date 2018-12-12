package com.umbc.aos.gcd;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import javax.jws.WebService;

import com.umbc.aos.gcd.Utils;
import com.umbc.aos.ws.WebServiceClient;


@WebService(endpointInterface = "com.umbc.aos.gcd.GCDInterface")
public class GCDImpl implements GCDInterface{
	static {
		try {
			String[] registries = Utils.getProperty("config").split(",");
			int count = 0;
			for(String ip:registries) {
				String registry = "http://"+ ip + "/WebRegistry/Registry?wsdl";
				if(isRegistryReachable(ip.split(":")[0], ip.split(":")[1])) {
	            	WebServiceClient.addServicetoRegistry(registry, "gcd");
	            	break;
				}
				else {
					count++;
					System.out.println("Registry with ip and port " +ip+ " is not reachable" );
				}
				if(count==registries.length) {
					System.out.println("All the registries are currently down. Please try again later");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("AdditionImpl static block failed");
			}
	}
	
	public static boolean isRegistryReachable(String ip, String port) {
		try {
		Socket soc = new Socket();
		SocketAddress endpoint = new InetSocketAddress(ip, Integer.parseInt(port));
		soc.connect(endpoint, 1000);
		soc.close();
		return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	@Override
	public int gcdOfTwoNumbers(int a, int b){
		while (a != b) {
        	if(a > b)
                a = a - b;
            else
                b = b - a;
        }
		return b;
	}
}
