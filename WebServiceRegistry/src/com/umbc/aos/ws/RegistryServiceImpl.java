package com.umbc.aos.ws;

import java.util.List;

import javax.jws.WebService;

import com.umbc.aos.beans.ListofWebserviceBean;
import com.umbc.aos.beans.Registry;
import com.umbc.aos.beans.WebServiceBean;


@WebService(endpointInterface = "com.umbc.aos.ws.RegistryService")
public class RegistryServiceImpl implements RegistryService {

	@Override
	public boolean AddService(WebServiceBean webBean) {
		// TODO Auto-generated method stub
		System.out.println("AddService Initiated for" + webBean );
		return Registry.add(webBean);

	}

	@Override
	public boolean DeleteService(WebServiceBean webBean) {
		// TODO Auto-generated method stub
		System.out.println("Delete Service Initiated for" + webBean );
		return Registry.remove(webBean);
	}

	@Override
	public ListofWebserviceBean SearchService(WebServiceBean webBean) {
		// TODO Auto-generated method stub
		System.out.println("Search Service For " + webBean);
		ListofWebserviceBean lowb = new ListofWebserviceBean();
		List<WebServiceBean> x  = Registry.getValue(webBean);
		System.out.println("in the Searchservice method "+x);
		lowb.setListOfBeans(x);
		return lowb;
	}

	

}
