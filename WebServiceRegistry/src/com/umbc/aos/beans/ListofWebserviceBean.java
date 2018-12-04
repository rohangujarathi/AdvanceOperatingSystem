package com.umbc.aos.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="listofwebservicebean")
public class ListofWebserviceBean {
	
	List<WebServiceBean> listOfBeans;

	public List<WebServiceBean> getListOfBeans() {
		return listOfBeans;
	}

	public void setListOfBeans(List<WebServiceBean> listOfBeans) {
		this.listOfBeans = listOfBeans;
	}
	
	
}
