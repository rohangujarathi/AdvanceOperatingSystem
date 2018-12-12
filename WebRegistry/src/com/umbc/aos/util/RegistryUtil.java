package com.umbc.aos.util;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.umbc.aos.beans.WebServiceBean;

public class RegistryUtil {
	
	public static Map<String, WebServiceBean> buffer = new ConcurrentHashMap<String, WebServiceBean>();
	
	public static boolean sendInformationToRegistry(WebServiceBean wb) throws Exception {
		String[] registries = FileUtils.getProperty("registries").split(",");
		
		for(String ip: registries) {
			boolean flag = ClientCalltoRegistry(ip, wb);
			if (!flag) {
				buffer.put(ip, wb);
				
			}
		}
		
		return false;
	}
	public static boolean ClientCalltoRegistry(String registryip, WebServiceBean wb) throws Exception {
		try {
			String serviceip = wb.getiPAddress(); 
			String serviceName = wb.getName();
			QName qserviceName = new QName("http://ws.aos.umbc.com/", "RegistryServiceImplService");
		    QName portName = new QName("http://ws.aos.umbc.com/", "RegistryServiceImplPort");
		    String servicewsdl = wb.getwSDLLocation();
	    	String request = generateRequestXML(servicewsdl, serviceip, serviceName);
	    	String registrywsdl = "http://" + registryip + "/WebRegistry/Registry?wsdl";
	    	//invoking registry
	    	invoke(qserviceName, portName, registrywsdl, serviceName, request);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("ClientCalltoRegistry failed");
			return false;
		}
		return true;
	}
	
	public static String generateRequestXML(String wsdl, String ipaddress, String servicename){
		
		String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.aos.umbc.com/\">"
		   +"<soapenv:Header/>"
		   + "<soapenv:Body>"
		     + "<ws:AddServiceForRegistry>"
		         + "<arg0>"
		            + "<!--Optional:-->"
		            + "<name>"+ servicename +"</name>"
		            + "<!--Optional:-->"
		            + "<iPAddress>" + ipaddress + "</iPAddress>"
		            + "<!--Optional:-->"
		            + "<wSDLLocation>" + wsdl +"</wSDLLocation>"
		         + "</arg0>"
		      + "</ws:AddServiceForRegistry>"
		   + "</soapenv:Body>"
		+ "</soapenv:Envelope>";
		
        return request;
	}
	
	public static SOAPMessage invoke(QName serviceName, QName portName, String endpointUrl, 
            String soapActionUri, String data) throws Exception {
		try {
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);

        Dispatch dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

        // The soapActionUri is set here. otherwise we get a error on .net based services.
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, true);
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapActionUri);

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();

        StreamSource preppedMsgSrc = new StreamSource(new StringReader(data));
        soapPart.setContent(preppedMsgSrc);

        message.saveChanges();

        SOAPMessage response = (SOAPMessage) dispatch.invoke(message);

        return response;
		}
		catch(Exception e){
			System.out.println("invoke method failed");
			throw e;
		}
    }
}
