package com.umbc.aos.lcm;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import javax.jws.WebService;

import com.umbc.aos.lcm.Utils;
import com.umbc.aos.ws.WebServiceClient;


@WebService(endpointInterface = "com.umbc.aos.lcm.LCMInterface")
public class LCMImpl implements LCMInterface{
	static {
		try {
			String[] registries = Utils.getProperty("config").split(",");
			int count = 0;
			for(String ip:registries) {
				String registry = "http://"+ ip + "/WebRegistry/Registry?wsdl";
				if(isRegistryReachable(ip.split(":")[0], ip.split(":")[1])) {
	            	WebServiceClient.addServicetoRegistry(registry, "lcm");
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
	public int lcmOfTwoNumbers(int a, int b){

		int lcm = (a > b) ? a : b;
        while(true)
        {
            if( lcm % a == 0 && lcm % b == 0 )
            {
                return lcm;
            }
            ++lcm;
        }
	}
}
