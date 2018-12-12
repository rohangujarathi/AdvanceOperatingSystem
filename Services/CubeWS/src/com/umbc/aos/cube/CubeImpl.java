package com.umbc.aos.cube;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jws.WebService;

import com.umbc.aos.ws.RegistryService;
import com.umbc.aos.ws.RegistryServiceImplService;
import com.umbc.aos.ws.WebServiceBean;



@WebService(endpointInterface = "com.umbc.aos.cube.CubeInterface")
public class CubeImpl implements CubeInterface{
	static {
		RegistryServiceImplService regis = new RegistryServiceImplService();
		RegistryService service = regis.getRegistryServiceImplPort();
		WebServiceBean wb = new WebServiceBean();
		try {
			InetAddress ip = InetAddress.getLocalHost();
			wb.setIPAddress(ip.getHostAddress()+':'+ 8080);
			wb.setName("cube");
			wb.setWSDLLocation("http://"+ip.getHostAddress()+":8080/CubeWS/CubeNumbers?wsdl");
			service.addService(wb);
			System.out.println(wb.getIPAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Override
	public int cubeNumbers(int a){
		return a*a*a;
	}
}
