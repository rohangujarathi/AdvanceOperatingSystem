package com.umbc.aos.service;

import javax.jws.WebService;

import com.umbc.aos.bean.LoadInformation;
import com.umbc.aos.threads.UpdateFromRegistry;

@WebService(endpointInterface = "com.umbc.aos.service.LoadServiceImpl")
public class LoadServiceImpl implements LoadService{
	
	{
		UpdateFromRegistry pingWork =  new UpdateFromRegistry();
		Thread pingThread = new Thread(pingWork);
		pingThread.start();
		
	}
	@Override
	public String FindServer(String service) {
		// TODO Auto-generated method stub
		//check if service exists
		System.out.println("LoadBalancer: service requested "+service);
		String name = LoadInformation.getTheServiceNode(service);
		System.out.println("LoadBalancer: "+ name);
		return name;
	}
}
