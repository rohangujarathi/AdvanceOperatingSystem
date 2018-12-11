package com.umbc.aos.thread;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.umbc.aos.ws.RegistryService;
import com.umbc.aos.ws.RegistryServiceImplService;
import com.umbc.aos.ws.WebServiceBean;

public class RegisterToRegistry implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			
			boolean flag = false;
			InetAddress ip = InetAddress.getLocalHost();
			WebServiceBean wb = new WebServiceBean();
			wb.setIPAddress(ip.getHostAddress()+':'+ 8081);
			wb.setName("ADD");
			wb.setWSDLLocation("http://"+ip.getHostAddress()+":8081/AdditionWS/AddNumbers?wsdl");

			System.out.println(wb.getIPAddress());
			while(!flag) {
				try {
					RegistryServiceImplService regis = new RegistryServiceImplService();
					RegistryService service = regis.getRegistryServiceImplPort();
					service.addService(wb);
					flag = true;
					System.out.println("Add: registered Successfully");
				}catch(Exception e){
					System.out.println("Add: Error while publishing to registry: will connect again in 5 seconds");
					flag = false;
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
