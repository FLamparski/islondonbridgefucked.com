//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.12 at 08:50:47 PM GMT 
//


package com.thalesgroup.pushport;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlertAudienceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AlertAudienceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Customer"/>
 *     &lt;enumeration value="Staff"/>
 *     &lt;enumeration value="Operations"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AlertAudienceType", namespace = "http://www.thalesgroup.com/rtti/PushPort/TrainAlerts/v1")
@XmlEnum
public enum AlertAudienceType {

    @XmlEnumValue("Customer")
    CUSTOMER("Customer"),
    @XmlEnumValue("Staff")
    STAFF("Staff"),
    @XmlEnumValue("Operations")
    OPERATIONS("Operations");
    private final String value;

    AlertAudienceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AlertAudienceType fromValue(String v) {
        for (AlertAudienceType c: AlertAudienceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
