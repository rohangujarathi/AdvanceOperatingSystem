package com.umbc.aos.beans;

import java.io.Serializable;

public class WebServiceBean implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 4352027076286477653L;
private String name;
private String iPAddress;
private String wSDLLocation;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getiPAddress() {
	return iPAddress;
}
public void setiPAddress(String iPAddress) {
	this.iPAddress = iPAddress;
}
public String getwSDLLocation() {
	return wSDLLocation;
}
public void setwSDLLocation(String wSDLLocation) {
	this.wSDLLocation = wSDLLocation;
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((iPAddress == null) ? 0 : iPAddress.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((wSDLLocation == null) ? 0 : wSDLLocation.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	WebServiceBean other = (WebServiceBean) obj;
	if (iPAddress == null) {
		if (other.iPAddress != null)
			return false;
	} else if (!iPAddress.equals(other.iPAddress))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (wSDLLocation == null) {
		if (other.wSDLLocation != null)
			return false;
	} else if (!wSDLLocation.equals(other.wSDLLocation))
		return false;
	return true;
}
@Override
public String toString() {
	return "WebServiceBean [name=" + name + ", iPAddress=" + iPAddress + ", wSDLLocation=" + wSDLLocation + "]";
}


}
