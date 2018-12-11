package com.umbc.aos.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.umbc.aos.beans.Registry;
import com.umbc.aos.beans.WebServiceBean;

public class RegistryCleaner implements Runnable{
	@Override
	public void run() {
		System.out.println("WebRegistry: Starting the Cleaning Service");
		try {
			System.out.println("WebRegistry:" + FileUtils.getProperty("r1"));
			while(true) {
				// TODO Auto-generated method stub
				while(Registry.getServiceMap().size() <= 0) {
					System.out.println("WebRegistry: Registry is empty going to sleep");
					Thread.sleep(5000);
				}
				Map<String, List<WebServiceBean>> registry = Registry.getServiceMap();
				Set<String> servers = new HashSet<String>();
				for(String name: registry.keySet()) {
					List<WebServiceBean> listOfServers = registry.get(name);
					for(WebServiceBean wb: listOfServers) {
						servers.add(wb.getiPAddress());
					}
				}
				for(String server: servers) {
					//splitting the IP:port into IP and port 
					String serverAddress = server.split(":")[0];
					int TcpServerPort = Integer.parseInt(server.split(":")[1]);
					try {
						Socket s = new Socket();
						SocketAddress addr = new InetSocketAddress(serverAddress, TcpServerPort);
						System.out.println("connecting " + server);
						s.connect(addr, 2000);
				
					} catch (Exception ex) {
						/* ignore */
						//since the server was not available
						ex.printStackTrace();
						System.out.println("WebRegistry: The "+serverAddress+" is not alive..removing");
						WebServiceBean wb = new WebServiceBean();
						wb.setiPAddress(serverAddress+":"+TcpServerPort);
						Registry.remove(wb);
					}

				}

				System.out.println("WebRegistry: Sleeping for 5 seconds");
				Thread.sleep(5000);
			} 
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("WebRegistry: Thread Error: "+ RegistryCleaner.class);
			e.printStackTrace();
		}
	}
}


