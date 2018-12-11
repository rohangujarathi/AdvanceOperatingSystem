
package com.umbc.aos.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.umbc.aos.ws package. 
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

    private final static QName _Listofwebservicebean_QNAME = new QName("http://ws.aos.umbc.com/", "listofwebservicebean");
    private final static QName _Webservicebean_QNAME = new QName("http://ws.aos.umbc.com/", "webservicebean");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.umbc.aos.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListofWebserviceBean }
     * 
     */
    public ListofWebserviceBean createListofWebserviceBean() {
        return new ListofWebserviceBean();
    }

    /**
     * Create an instance of {@link WebServiceBean }
     * 
     */
    public WebServiceBean createWebServiceBean() {
        return new WebServiceBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListofWebserviceBean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.aos.umbc.com/", name = "listofwebservicebean")
    public JAXBElement<ListofWebserviceBean> createListofwebservicebean(ListofWebserviceBean value) {
        return new JAXBElement<ListofWebserviceBean>(_Listofwebservicebean_QNAME, ListofWebserviceBean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebServiceBean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.aos.umbc.com/", name = "webservicebean")
    public JAXBElement<WebServiceBean> createWebservicebean(WebServiceBean value) {
        return new JAXBElement<WebServiceBean>(_Webservicebean_QNAME, WebServiceBean.class, null, value);
    }

}
