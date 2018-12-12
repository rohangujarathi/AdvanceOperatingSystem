
package com.umbc.aos.client;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import javax.xml.soap.SOAPBody;


public class DemoClient {
	

	
	public static void main(String args[]) throws Exception {
		
		String lb1 = "http://192.168.0.7:8080/LoadBalancerService/loadService?wsdl";
		String lb2 = "http://192.168.0.12:8080/LoadBalancerService/loadService?wsdl";
		System.out.println("Welcome To our distributed application.");
		String choice = null;
		while(choice!="exit") {	
			Scanner sc = new Scanner(System.in);
			System.out.println("Please select a operation to perform from below");
			System.out.println("1. Add");
			System.out.println("2. Subtract");
			System.out.println("3. Multiply");
			System.out.println("4. Divide");
			System.out.println("5. Power");
			System.out.println("6. GCD");
			System.out.println("7. LCM");
			System.out.println("To exit the application, type \"exit\"");
			choice = sc.next();
			if(choice.equals("exit")) {
				break;
			}
			if(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") &&
					!choice.equals("6") && !choice.equals("7")) {
				System.out.println("The value entered is incorrect");
				continue;
			}
			String operation = null;
			System.out.println("Enter the first number");
			String arg0 = sc.next();
			System.out.println("Enter the second number");
			String arg1 = sc.next();
//			sc.close();
			Client c = new Client();
			operation = findOperation(choice);
			SOAPBody lbresponse = null;
			if(isLoadBalancerReachable("192.168.0.7")) {
				lbresponse = c.getResponse(lb1,arg0,arg1, operation);
			}
			else if(isLoadBalancerReachable("192.168.0.12")){
				System.out.println(lb2);
				System.out.println(arg0);
				System.out.println(arg1);
				System.out.println(operation);
				lbresponse = c.getResponse(lb2,arg0,arg1, operation);
			}
			String servicewsdl = lbresponse.getFirstChild().getFirstChild().getTextContent();
			System.out.println("The wsdl returned by load balancer is");
			System.out.println(servicewsdl);
			SOAPBody response = c.getResponse(servicewsdl,arg0,arg1, null);
			String ans = response.getFirstChild().getFirstChild().getTextContent();
			System.out.println();
			System.out.println("The value returned by the service is " + ans);
			System.out.println();
			System.out.println("......................|||||......................|||||......................");
			
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
	public static String findOperation(String choice) {
		if(choice.equals("1")) {
			return "add";
		}
		else if(choice.equals("2")) {
			return "sub";
		}
		else if(choice.equals("3")) {
			return  "multiply";
		}
		else if(choice.equals("4")) {
			return  "divide";
		}
		else if(choice.equals("5")) {
			return  "pow";
		}
		else if(choice.equals("6")) {
			return  "gcd";
		}
		else if(choice.equals("7")) {
			return  "lcm";
		}
		return null;
	}
}
