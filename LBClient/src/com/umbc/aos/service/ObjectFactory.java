
package com.umbc.aos.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.umbc.aos.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindServer_QNAME = new QName("http://service.aos.umbc.com/", "FindServer");
    private final static QName _FindServerResponse_QNAME = new QName("http://service.aos.umbc.com/", "FindServerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.umbc.aos.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindServer }
     * 
     */
    public FindServer createFindServer() {
        return new FindServer();
    }

    /**
     * Create an instance of {@link FindServerResponse }
     * 
     */
    public FindServerResponse createFindServerResponse() {
        return new FindServerResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindServer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.aos.umbc.com/", name = "FindServer")
    public JAXBElement<FindServer> createFindServer(FindServer value) {
        return new JAXBElement<FindServer>(_FindServer_QNAME, FindServer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindServerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.aos.umbc.com/", name = "FindServerResponse")
    public JAXBElement<FindServerResponse> createFindServerResponse(FindServerResponse value) {
        return new JAXBElement<FindServerResponse>(_FindServerResponse_QNAME, FindServerResponse.class, null, value);
    }

}
