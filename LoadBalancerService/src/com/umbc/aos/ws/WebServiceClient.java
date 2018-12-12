package com.umbc.aos.ws;


import java.io.BufferedOutputStream;
import java.io.StringReader;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WebServiceClient {
	public static SOAPMessage getallServicesFromRegistry(String registrywsdl) throws Exception{
		//public static void main(String args[]) {
		SOAPMessage response = null;
		try {
			
			QName qserviceName = new QName("http://ws.aos.umbc.com/", "RegistryServiceImplService");
			QName portName = new QName("http://ws.aos.umbc.com/", "RegistryServiceImplPort");
			String request = generateRequestXML();
			response = invoke(qserviceName, portName, registrywsdl, "getAllWebServiceDetails", request);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("getting services from registry failed");
			//			throw e;
		}
		return response;


	}
	public static String generateRequestXML(){

		String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.aos.umbc.com/\">" 
				+ "<soapenv:Header/>"  
				+ "<soapenv:Body>" 
				+ "<ws:getAllWebServiceDetails/>" 
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