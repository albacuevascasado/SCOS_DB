//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.11 alle 09:11:28 AM CEST 
//


package com.scos.XSDToJava2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per BaseHeaderType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BaseHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="genTime" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="relType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseHeaderType", propOrder = {
    "category",
    "source",
    "genTime",
    "relType",
    "version",
    "startTime"
})
public class BaseHeaderType {

    protected int category;
    @XmlElement(required = true)
    protected String source;
    @XmlElement(required = true)
    protected BigInteger genTime;
    protected int relType;
    @XmlElement(required = true)
    protected String version;
    @XmlElement(required = true)
    protected BigInteger startTime;

    /**
     * Recupera il valore della proprietà category.
     * 
     */
    public int getCategory() {
        return category;
    }

    /**
     * Imposta il valore della proprietà category.
     * 
     */
    public void setCategory(int value) {
        this.category = value;
    }

    /**
     * Recupera il valore della proprietà source.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Imposta il valore della proprietà source.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Recupera il valore della proprietà genTime.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGenTime() {
        return genTime;
    }

    /**
     * Imposta il valore della proprietà genTime.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGenTime(BigInteger value) {
        this.genTime = value;
    }

    /**
     * Recupera il valore della proprietà relType.
     * 
     */
    public int getRelType() {
        return relType;
    }

    /**
     * Imposta il valore della proprietà relType.
     * 
     */
    public void setRelType(int value) {
        this.relType = value;
    }

    /**
     * Recupera il valore della proprietà version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Imposta il valore della proprietà version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Recupera il valore della proprietà startTime.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStartTime() {
        return startTime;
    }

    /**
     * Imposta il valore della proprietà startTime.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStartTime(BigInteger value) {
        this.startTime = value;
    }

}
