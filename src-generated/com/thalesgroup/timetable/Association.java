//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.12 at 08:52:29 PM GMT 
//


package com.thalesgroup.timetable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Type describing an association between schedules
 * 
 * <p>Java class for Association complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Association">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="main" type="{http://www.thalesgroup.com/rtti/XmlTimetable/v8}AssocService"/>
 *         &lt;element name="assoc" type="{http://www.thalesgroup.com/rtti/XmlTimetable/v8}AssocService"/>
 *       &lt;/sequence>
 *       &lt;attribute name="tiploc" use="required" type="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}TiplocType" />
 *       &lt;attribute name="category" use="required" type="{http://www.thalesgroup.com/rtti/XmlTimetable/v8}CategoryType" />
 *       &lt;attribute name="isCancelled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="isDeleted" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Association", propOrder = {
    "main",
    "assoc"
})
public class Association {

    @XmlElement(required = true)
    protected AssocService main;
    @XmlElement(required = true)
    protected AssocService assoc;
    @XmlAttribute(name = "tiploc", required = true)
    protected String tiploc;
    @XmlAttribute(name = "category", required = true)
    protected CategoryType category;
    @XmlAttribute(name = "isCancelled")
    protected Boolean isCancelled;
    @XmlAttribute(name = "isDeleted")
    protected Boolean isDeleted;

    /**
     * Gets the value of the main property.
     * 
     * @return
     *     possible object is
     *     {@link AssocService }
     *     
     */
    public AssocService getMain() {
        return main;
    }

    /**
     * Sets the value of the main property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssocService }
     *     
     */
    public void setMain(AssocService value) {
        this.main = value;
    }

    /**
     * Gets the value of the assoc property.
     * 
     * @return
     *     possible object is
     *     {@link AssocService }
     *     
     */
    public AssocService getAssoc() {
        return assoc;
    }

    /**
     * Sets the value of the assoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssocService }
     *     
     */
    public void setAssoc(AssocService value) {
        this.assoc = value;
    }

    /**
     * Gets the value of the tiploc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiploc() {
        return tiploc;
    }

    /**
     * Sets the value of the tiploc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiploc(String value) {
        this.tiploc = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryType }
     *     
     */
    public CategoryType getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryType }
     *     
     */
    public void setCategory(CategoryType value) {
        this.category = value;
    }

    /**
     * Gets the value of the isCancelled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsCancelled() {
        if (isCancelled == null) {
            return false;
        } else {
            return isCancelled;
        }
    }

    /**
     * Sets the value of the isCancelled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCancelled(Boolean value) {
        this.isCancelled = value;
    }

    /**
     * Gets the value of the isDeleted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsDeleted() {
        if (isDeleted == null) {
            return false;
        } else {
            return isDeleted;
        }
    }

    /**
     * Sets the value of the isDeleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDeleted(Boolean value) {
        this.isDeleted = value;
    }

}
