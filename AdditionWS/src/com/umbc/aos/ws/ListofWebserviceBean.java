
package com.umbc.aos.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listofWebserviceBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listofWebserviceBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listOfBeans" type="{http://ws.aos.umbc.com/}webServiceBean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listofWebserviceBean", propOrder = {
    "listOfBeans"
})
public class ListofWebserviceBean {

    @XmlElement(nillable = true)
    protected List<WebServiceBean> listOfBeans;

    /**
     * Gets the value of the listOfBeans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listOfBeans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListOfBeans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WebServiceBean }
     * 
     * 
     */
    public List<WebServiceBean> getListOfBeans() {
        if (listOfBeans == null) {
            listOfBeans = new ArrayList<WebServiceBean>();
        }
        return this.listOfBeans;
    }

}
