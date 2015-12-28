//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.12 at 08:53:01 PM GMT 
//


package com.thalesgroup.timetable;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}LocationRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}TocRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LateRunningReasons" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}Reason" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CancellationReasons" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}Reason" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}Via" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}CISSource" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="timetableId" use="required" type="{http://www.thalesgroup.com/rtti/XmlRefData/v3}TimetableIdType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "locationRef",
    "tocRef",
    "lateRunningReasons",
    "cancellationReasons",
    "via",
    "cisSource"
})
@XmlRootElement(name = "PportTimetableRef")
public class PportTimetableRef {

    @XmlElement(name = "LocationRef")
    protected List<LocationRef> locationRef;
    @XmlElement(name = "TocRef")
    protected List<TocRef> tocRef;
    @XmlElement(name = "LateRunningReasons")
    protected PportTimetableRef.LateRunningReasons lateRunningReasons;
    @XmlElement(name = "CancellationReasons")
    protected PportTimetableRef.CancellationReasons cancellationReasons;
    @XmlElement(name = "Via")
    protected List<Via> via;
    @XmlElement(name = "CISSource")
    protected List<CISSource> cisSource;
    @XmlAttribute(name = "timetableId", required = true)
    protected String timetableId;

    /**
     * Gets the value of the locationRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocationRef }
     * 
     * 
     */
    public List<LocationRef> getLocationRef() {
        if (locationRef == null) {
            locationRef = new ArrayList<LocationRef>();
        }
        return this.locationRef;
    }

    /**
     * Gets the value of the tocRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tocRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTocRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TocRef }
     * 
     * 
     */
    public List<TocRef> getTocRef() {
        if (tocRef == null) {
            tocRef = new ArrayList<TocRef>();
        }
        return this.tocRef;
    }

    /**
     * Gets the value of the lateRunningReasons property.
     * 
     * @return
     *     possible object is
     *     {@link PportTimetableRef.LateRunningReasons }
     *     
     */
    public PportTimetableRef.LateRunningReasons getLateRunningReasons() {
        return lateRunningReasons;
    }

    /**
     * Sets the value of the lateRunningReasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link PportTimetableRef.LateRunningReasons }
     *     
     */
    public void setLateRunningReasons(PportTimetableRef.LateRunningReasons value) {
        this.lateRunningReasons = value;
    }

    /**
     * Gets the value of the cancellationReasons property.
     * 
     * @return
     *     possible object is
     *     {@link PportTimetableRef.CancellationReasons }
     *     
     */
    public PportTimetableRef.CancellationReasons getCancellationReasons() {
        return cancellationReasons;
    }

    /**
     * Sets the value of the cancellationReasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link PportTimetableRef.CancellationReasons }
     *     
     */
    public void setCancellationReasons(PportTimetableRef.CancellationReasons value) {
        this.cancellationReasons = value;
    }

    /**
     * Gets the value of the via property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the via property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Via }
     * 
     * 
     */
    public List<Via> getVia() {
        if (via == null) {
            via = new ArrayList<Via>();
        }
        return this.via;
    }

    /**
     * Gets the value of the cisSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cisSource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCISSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CISSource }
     * 
     * 
     */
    public List<CISSource> getCISSource() {
        if (cisSource == null) {
            cisSource = new ArrayList<CISSource>();
        }
        return this.cisSource;
    }

    /**
     * Gets the value of the timetableId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimetableId() {
        return timetableId;
    }

    /**
     * Sets the value of the timetableId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimetableId(String value) {
        this.timetableId = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}Reason" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "reason"
    })
    public static class CancellationReasons {

        @XmlElement(name = "Reason", required = true)
        protected List<Reason> reason;

        /**
         * Gets the value of the reason property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reason property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReason().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Reason }
         * 
         * 
         */
        public List<Reason> getReason() {
            if (reason == null) {
                reason = new ArrayList<Reason>();
            }
            return this.reason;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.thalesgroup.com/rtti/XmlRefData/v3}Reason" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "reason"
    })
    public static class LateRunningReasons {

        @XmlElement(name = "Reason", required = true)
        protected List<Reason> reason;

        /**
         * Gets the value of the reason property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reason property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReason().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Reason }
         * 
         * 
         */
        public List<Reason> getReason() {
            if (reason == null) {
                reason = new ArrayList<Reason>();
            }
            return this.reason;
        }

    }

}
