//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.27 at 12:09:28 pm EAT 
//


package com.secpayment.payment;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionMethodCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PromotionMethodCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CrossSell"/>
 *     &lt;enumeration value="UpSell"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PromotionMethodCodeType")
@XmlEnum
public enum PromotionMethodCodeType {


    /**
     * 
     *                         Cross Sell
     *                     
     * 
     */
    @XmlEnumValue("CrossSell")
    CROSS_SELL("CrossSell"),

    /**
     * 
     *                         Up Sell
     *                     
     * 
     */
    @XmlEnumValue("UpSell")
    UP_SELL("UpSell"),

    /**
     * 
     *                         Reserved. Do not use.
     *                     
     * 
     */
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    PromotionMethodCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PromotionMethodCodeType fromValue(String v) {
        for (PromotionMethodCodeType c: PromotionMethodCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}