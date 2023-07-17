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
 * <p>Classe Java per SequenceHeaderType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SequenceHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="seqType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pars" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cmds" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="startTime2" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="subsystem" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tcRequestId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="subSchedId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SequenceHeaderType", propOrder = {
    "seqType",
    "id",
    "pars",
    "cmds",
    "startTime",
    "startTime2",
    "subsystem",
    "source",
    "tcRequestId",
    "subSchedId"
})
public class SequenceHeaderType {

    @XmlElement(required = true)
    protected String seqType;
    @XmlElement(required = true)
    protected String id;
    protected int pars;
    protected int cmds;
    protected long startTime;
    protected long startTime2;
    protected int subsystem;
    protected int source;
    @XmlElement(required = true)
    protected BigInteger tcRequestId;
    @XmlElement(required = true)
    protected BigInteger subSchedId;

    /**
     * Recupera il valore della proprietà seqType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeqType() {
        return seqType;
    }

    /**
     * Imposta il valore della proprietà seqType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeqType(String value) {
        this.seqType = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà pars.
     * 
     */
    public int getPars() {
        return pars;
    }

    /**
     * Imposta il valore della proprietà pars.
     * 
     */
    public void setPars(int value) {
        this.pars = value;
    }

    /**
     * Recupera il valore della proprietà cmds.
     * 
     */
    public int getCmds() {
        return cmds;
    }

    /**
     * Imposta il valore della proprietà cmds.
     * 
     */
    public void setCmds(int value) {
        this.cmds = value;
    }

    /**
     * Recupera il valore della proprietà startTime.
     * 
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Imposta il valore della proprietà startTime.
     * 
     */
    public void setStartTime(long value) {
        this.startTime = value;
    }

    /**
     * Recupera il valore della proprietà startTime2.
     * 
     */
    public long getStartTime2() {
        return startTime2;
    }

    /**
     * Imposta il valore della proprietà startTime2.
     * 
     */
    public void setStartTime2(long value) {
        this.startTime2 = value;
    }

    /**
     * Recupera il valore della proprietà subsystem.
     * 
     */
    public int getSubsystem() {
        return subsystem;
    }

    /**
     * Imposta il valore della proprietà subsystem.
     * 
     */
    public void setSubsystem(int value) {
        this.subsystem = value;
    }

    /**
     * Recupera il valore della proprietà source.
     * 
     */
    public int getSource() {
        return source;
    }

    /**
     * Imposta il valore della proprietà source.
     * 
     */
    public void setSource(int value) {
        this.source = value;
    }

    /**
     * Recupera il valore della proprietà tcRequestId.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTcRequestId() {
        return tcRequestId;
    }

    /**
     * Imposta il valore della proprietà tcRequestId.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTcRequestId(BigInteger value) {
        this.tcRequestId = value;
    }

    /**
     * Recupera il valore della proprietà subSchedId.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSubSchedId() {
        return subSchedId;
    }

    /**
     * Imposta il valore della proprietà subSchedId.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSubSchedId(BigInteger value) {
        this.subSchedId = value;
    }

}
