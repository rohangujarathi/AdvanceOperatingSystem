package com.umbc.aos.add;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jws.WebService;

import com.umbc.aos.ws.RegistryService;
import com.umbc.aos.ws.RegistryServiceImplService;
import com.umbc.aos.ws.WebServiceBean;



@WebService(endpointInterface = "com.umbc.aos.add.AdditionInterface")
public class AdditionImpl implements AdditionInterface{
	static {
		RegistryServiceImplService regis = new RegistryServiceImplService();
		RegistryService service = regis.getRegistryServiceImplPort();
		WebServiceBean wb = new WebServiceBean();
		try {
			InetAddress ip = InetAddress.getLocalHost();
			wb.setIPAddress(ip.getHostAddress()+':'+ 8080);
			wb.setName("ADD");
			wb.setWSDLLocation("http://"+ip.getHostAddress()+":8080/AdditionWS/AddNumbers?wsdl");
			System.out.println("abc");
			service.addService(wb);
			System.out.println(wb.getIPAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Override
	public int addNumbers(int a, int b){
		return a+b;
	}
}
