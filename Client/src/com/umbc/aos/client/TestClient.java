package com.umbc.aos.client;
import javax.xml.soap.SOAPBody;
public class TestClient {
	public static void main(String args[]) throws Exception{
		String list[] = {"add", "multiply", "divide", "power"};
		String wsdl = "http://192.168.0.7:8080/LoadBalancerService/loadService?wsdl";
		String arg0 = "20";
		String arg1 = "10";
		for(int i=0; i<2; i++){
			for(int j=0; j<list.length; j++){
				Client c = new Client();
				SOAPBody lbresponse = c.getResponse(wsdl,arg0,arg1, list[j]);
				String servicewsdl = lbresponse.getFirstChild().getFirstChild().getTextContent();
				System.out.println("The wsdl returned by load balancer is");
				System.out.println(servicewsdl);
				SOAPBody response = c.getResponse(servicewsdl,arg0,arg1, null);
				String ans = response.getFirstChild().getFirstChild().getTextContent();
				System.out.println(ans);
			}
		}
//		Client c = new Client();
//		SOAPBody lbresponse = c.getResponse(wsdl,arg0,arg1, "power");
//		String servicewsdl = lbresponse.getFirstChild().getFirstChild().getTextContent();
//		System.out.println("The wsdl returned by load balancer is");
//		System.out.println(servicewsdl);
//		SOAPBody response = c.getResponse(servicewsdl,arg0,arg1, null);
//		String ans = response.getFirstChild().getFirstChild().getTextContent();
//		System.out.println(ans);
	}
}
