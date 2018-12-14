package com.umbc.aos.ws;


import java.io.StringReader;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.umbc.aos.power.Utils;

public class WebServiceClient {
	public static void addServicetoRegistry(String registrywsdl, String name) throws Exception{
		try {
			String port = Utils.getProperty("servicePort");
			String ip = Utils.getProperty("serviceIP");
//		InetAddress ip = InetAddress.getLocalHost();
		String ipaddress = ip+':'+ port;
		String serviceName = name;
		QName qserviceName = new QName("http://ws.aos.umbc.com/", "RegistryServiceImplService");
	    QName portName = new QName("http://ws.aos.umbc.com/", "RegistryServiceImplPort");
	    String servicewsdl = "http://"+ip+":" + port + "/PowerWS/PowerNumbers?wsdl";
    	String request = generateRequestXML(servicewsdl, ipaddress, serviceName);
    	invoke(qserviceName, portName, registrywsdl, "AddService", request);
	}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("addServicetoRegistry failed");
			throw e;
		}
	}
	public static String generateRequestXML(String wsdl, String ipaddress, String servicename){
		
		String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.aos.umbc.com/\">"
		   +"<soapenv:Header/>"
		   + "<soapenv:Body>"
		     + "<ws:AddService>"
		         + "<arg0>"
		            + "<!--Optional:-->"
		            + "<name>"+ servicename +"</name>"
		            + "<!--Optional:-->"
		            + "<iPAddress>" + ipaddress + "</iPAddress>"
		            + "<!--Optional:-->"
		            + "<wSDLLocation>" + wsdl +"</wSDLLocation>"
		         + "</arg0>"
		      + "</ws:AddService>"
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