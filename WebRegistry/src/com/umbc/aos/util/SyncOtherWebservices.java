package com.umbc.aos.util;

import java.util.Iterator;
import java.util.Map.Entry;

import com.umbc.aos.beans.WebServiceBean;

public class SyncOtherWebservices implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				if (RegistryUtil.buffer.isEmpty()) {
					System.out.println("WebRegistry- SyncOtherRegistry: Buffer is empty");
					Thread.sleep(5000);
				} else {
					Iterator<Entry<String, WebServiceBean>> iter = RegistryUtil.buffer.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<String, WebServiceBean> entry = iter.next();
						String key = entry.getKey();
						WebServiceBean value = entry.getValue();
						boolean flag = RegistryUtil.ClientCalltoRegistry(key, value);
						if (flag) {
							iter.remove();
						}
					}
				}
			} catch (InterruptedException e) {
				System.out.println("WebRegistry: Error While In SyncOtherWebService Thread");
			} catch (Exception e) {
				System.out.println("WebRegistry: Error While In SyncOtherWebService Thread");
			}
		}
	}
}
