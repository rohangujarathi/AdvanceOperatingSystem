package com.umbc.aos.threads;

import com.umbc.aos.DAO.WebServiceDetails;

public class UpdateFromRegistry implements Runnable{
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//connecting to webservice
		System.out.println("LoadBalancer: Connecting to registry for updating webservices details");
		while(true) {
			try {
				WebServiceDetails ws = new WebServiceDetails();
				ws.getAllWebServicesFromRegistry();
				System.out.println("LoadBalancer: Service Details Updated");
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

}
