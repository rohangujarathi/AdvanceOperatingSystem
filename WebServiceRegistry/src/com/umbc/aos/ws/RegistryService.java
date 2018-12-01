package com.umbc.aos.ws;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.umbc.aos.beans.ListofWebserviceBean;
import com.umbc.aos.beans.WebServiceBean;



@WebService
@SOAPBinding(style = Style.RPC)
public interface RegistryService {
	public boolean AddService(WebServiceBean webBean);
	public boolean DeleteService(WebServiceBean webBean);
	public ListofWebserviceBean SearchService(WebServiceBean webBean);	
}
