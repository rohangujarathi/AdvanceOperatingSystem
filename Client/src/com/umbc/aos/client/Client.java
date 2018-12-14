package com.umbc.aos.client;


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
import com.predic8.wsdl.*;

public class Client{
	//This method gets the response from the load balancer and returns it as a soap body
	public SOAPBody getResponse(String wsdl, String arg0, String arg1, String arg2) throws Exception{
		String operationName = null; 
		String tns = null;
		String serviceName = null;
		String servicePort = null;
		String request = null;
		SOAPMessage response;
		WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(wsdl);
        tns = defs.getTargetNamespace();

        for (com.predic8.wsdl.Service service : defs.getServices()) {
            serviceName = service.getName();
            for (Port port : service.getPorts()) {
               servicePort = port.getName();
            }
        }
        for (Binding bnd : defs.getBindings()){
        	for (BindingOperation bop : bnd.getOperations()) {
              operationName = bop.getName();
        }
        }
		String[] x = tns.split("//");
		String[] y = x[1].split("\\.");        
	    QName serviceName1 = new QName(tns, serviceName);
	    QName portName = new QName(tns, servicePort);
	    
	    //Call web service
	    if(arg2==null){
	    	request = generateRequestXML(tns, operationName, arg0, arg1, y[0]);
	    	response = invoke(serviceName1, portName, wsdl, operationName, request);
	    }
	    //call load balancer
	    else{
	    	request = generateRequestXML(tns, arg2);
	    	response = invoke(serviceName1, portName, wsdl, operationName, request);
	    }
	    SOAPBody body = response.getSOAPBody();
	    return body;
	}
	
	//Generate request XML for requested service
	public String generateRequestXML(String tns, String operationName, String arg0, String arg1, String operator){
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" + operator + "=\""+ tns + "\">"
     		   +"<soapenv:Header/>"
     		   +"<soapenv:Body>"
     		      +"<"+ operator +":" + operationName + ">"
     		         +"<arg0>" +arg0+ "</arg0>"
     		         +"<arg1>" +arg1+ "</arg1>"
     		      +"</" + operator +":"+ operationName +">"
     		   +"</soapenv:Body>"
     		+"</soapenv:Envelope>";
        
        return request;
	}
	
	//Generate request XML for load balancer
	public String generateRequestXML(String tns, String arg0){
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\""+ tns + "\">"
     		   +"<soapenv:Header/>"
     		   +"<soapenv:Body>"
     		   		+"<ser:FindServer>"
     		   			+"<arg0>" +arg0+ "</arg0>"
     		        +"</ser:FindServer>"
     		   +"</soapenv:Body>"
     		+"</soapenv:Envelope>";
        
        return request;
	}
	
	//This method will fetch the response from the service and returns it in the form of SOAP message
	public static SOAPMessage invoke(QName serviceName, QName portName, String endpointUrl, 
            String soapActionUri, String data) throws Exception {
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
}