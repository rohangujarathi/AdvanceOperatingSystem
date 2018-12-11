package com.umbc.aos.add;

import javax.jws.WebService;

import com.umbc.aos.thread.RegisterToRegistry;



@WebService(endpointInterface = "com.umbc.aos.add.AdditionInterface")
public class AdditionImpl implements AdditionInterface{
	static {
		System.out.println("AddService: register to registry");
		RegisterToRegistry rtrWork = new RegisterToRegistry();
		Thread rtrThread = new Thread(rtrWork);
		rtrThread.start();
	}
	@Override
	public int addNumbers(int a, int b){
		return a+b;
	}
}
