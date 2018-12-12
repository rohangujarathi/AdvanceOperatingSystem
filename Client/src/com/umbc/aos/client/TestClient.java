package com.umbc.aos.client;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.xml.soap.SOAPBody;
public class TestClient {
	public static void main(String args[]) throws Exception{
		System.out.println("xyz");
		String list[] = {"add", "sub", "multiply", "divide", "power", "gcd", "lcm"};
		String lb1 = "http://192.168.0.7:8080/LoadBalancerService/loadService?wsdl";
		String lb2 = "http://192.168.0.12:8080/LoadBalancerService/loadService?wsdl";
		String arg0 = "20";
		String arg1 = "10";

		
		for(int i=0; i<1; i++){
			for(int j=0; j<1; j++){
				Client c = new Client();
				SOAPBody lbresponse = null;
				if(isLoadBalancerReachable("192.168.0.7")) {
					lbresponse = c.getResponse(lb1,arg0,arg1, list[j]);
				}
				else if(isLoadBalancerReachable("192.168.0.12")){
					lbresponse = c.getResponse(lb2,arg0,arg1, list[j]);
				}
				String servicewsdl = lbresponse.getFirstChild().getFirstChild().getTextContent();
				System.out.println("The wsdl returned by load balancer is");
				System.out.println(servicewsdl);
				SOAPBody response = c.getResponse(servicewsdl,arg0,arg1, null);
				String ans = response.getFirstChild().getFirstChild().getTextContent();
				System.out.println(ans);
			}
		}
	}
	
	public static boolean isLoadBalancerReachable(String ip) {
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
}
