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
//	public static void main(String[] args) throws Exception{
	public SOAPBody getResponse(String wsdl, String arg0, String arg1, String arg2) throws Exception{
		String operationName = null; 
		String tns = null;
		String serviceName = null;
		String servicePort = null;
		String request = null;
		SOAPMessage response;
//		String wsdl = "http://localhost:8080/AdditionWS/AddNumbers?wsdl";
//		int arg0 = 2;
//		int arg1 = 3;
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
//        System.out.println("Operation_Name: ");
//        System.out.println(operationName);
//        System.out.println("tns: ");
//        System.out.println(tns);
//        System.out.println("service_Name: ");
//        System.out.println(serviceName);
//        System.out.println("port_Name: ");
//        System.out.println(servicePort);
        
		String[] x = tns.split("//");
		String[] y = x[1].split("\\.");
//        System.out.println();
        
	    QName serviceName1 = new QName(tns, serviceName);
	    QName portName = new QName(tns, servicePort);
	    
	    if(arg2==null){
	    	request = generateRequestXML(tns, operationName, arg0, arg1, y[0]);
	    	response = invoke(serviceName1, portName, wsdl, operationName, request);
	    	
	    }
	    else{
	    	request = generateRequestXML(tns, arg2);
	    	response = invoke(serviceName1, portName, wsdl, operationName, request);
	    	
	    }
	    SOAPBody body = response.getSOAPBody();
//        SOAPMessage response = invoke(serviceName1, portName, wsdl, operationName, request);
	    
//	    System.out.println(body.getFirstChild().getFirstChild().getTextContent());
//	    if (body.hasFault()) {
//	        System.out.println(body.getFault());
//	    } else {
//	        BufferedOutputStream out = new BufferedOutputStream(System.out);
//	        response.writeTo(out);
//	        out.flush();
//	        System.out.println();
//	    }  
	    return body;
	}
	
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