package com.umbc.aos.util;

public class SyncOtherWebservices implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(RegistryUtil.buffer.isEmpty()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
