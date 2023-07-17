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
 * <p>Classe Java per CommandHeaderType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CommandHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cmdType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="manDispatch" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="release" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="relTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="relTime2" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="block" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="interlock" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ilStage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staticPtv" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dynamicPtv" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cev" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="pars" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="timeTagged" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="planned" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="execTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="execTime2" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="parent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="subsystem" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="earliest" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="latest" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="tcRequestId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="subSchedId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ackFlags" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommandHeaderType", propOrder = {
    "cmdType",
    "id",
    "manDispatch",
    "release",
    "relTime",
    "relTime2",
    "group",
    "block",
    "interlock",
    "ilStage",
    "staticPtv",
    "dynamicPtv",
    "cev",
    "pars",
    "timeTagged",
    "planned",
    "execTime",
    "execTime2",
    "parent",
    "startTime",
    "subsystem",
    "source",
    "earliest",
    "latest",
    "tcRequestId",
    "subSchedId",
    "ackFlags"
})
public class CommandHeaderType {

    @XmlElement(required = true)
    protected String cmdType;
    @XmlElement(required = true)
    protected String id;
    protected int manDispatch;
    protected int release;
    protected long relTime;
    protected long relTime2;
    protected int group;
    protected int block;
    protected int interlock;
    @XmlElement(required = true)
    protected String ilStage;
    protected int staticPtv;
    protected int dynamicPtv;
    protected int cev;
    @XmlElement(required = true)
    protected BigInteger pars;
    protected int timeTagged;
    protected int planned;
    protected long execTime;
    protected long execTime2;
    @XmlElement(required = true)
    protected String parent;
    protected long startTime;
    protected int subsystem;
    protected int source;
    protected long earliest;
    protected long latest;
    @XmlElement(required = true)
    protected BigInteger tcRequestId;
    @XmlElement(required = true)
    protected BigInteger subSchedId;
    protected int ackFlags;

    /**
     * Recupera il valore della proprietà cmdType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmdType() {
        return cmdType;
    }

    /**
     * Imposta il valore della proprietà cmdType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmdType(String value) {
        this.cmdType = value;
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
     * Recupera il valore della proprietà manDispatch.
     * 
     */
    public int getManDispatch() {
        return manDispatch;
    }

    /**
     * Imposta il valore della proprietà manDispatch.
     * 
     */
    public void setManDispatch(int value) {
        this.manDispatch = value;
    }

    /**
     * Recupera il valore della proprietà release.
     * 
     */
    public int getRelease() {
        return release;
    }

    /**
     * Imposta il valore della proprietà release.
     * 
     */
    public void setRelease(int value) {
        this.release = value;
    }

    /**
     * Recupera il valore della proprietà relTime.
     * 
     */
    public long getRelTime() {
        return relTime;
    }

    /**
     * Imposta il valore della proprietà relTime.
     * 
     */
    public void setRelTime(long value) {
        this.relTime = value;
    }

    /**
     * Recupera il valore della proprietà relTime2.
     * 
     */
    public long getRelTime2() {
        return relTime2;
    }

    /**
     * Imposta il valore della proprietà relTime2.
     * 
     */
    public void setRelTime2(long value) {
        this.relTime2 = value;
    }

    /**
     * Recupera il valore della proprietà group.
     * 
     */
    public int getGroup() {
        return group;
    }

    /**
     * Imposta il valore della proprietà group.
     * 
     */
    public void setGroup(int value) {
        this.group = value;
    }

    /**
     * Recupera il valore della proprietà block.
     * 
     */
    public int getBlock() {
        return block;
    }

    /**
     * Imposta il valore della proprietà block.
     * 
     */
    public void setBlock(int value) {
        this.block = value;
    }

    /**
     * Recupera il valore della proprietà interlock.
     * 
     */
    public int getInterlock() {
        return interlock;
    }

    /**
     * Imposta il valore della proprietà interlock.
     * 
     */
    public void setInterlock(int value) {
        this.interlock = value;
    }

    /**
     * Recupera il valore della proprietà ilStage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIlStage() {
        return ilStage;
    }

    /**
     * Imposta il valore della proprietà ilStage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIlStage(String value) {
        this.ilStage = value;
    }

    /**
     * Recupera il valore della proprietà staticPtv.
     * 
     */
    public int getStaticPtv() {
        return staticPtv;
    }

    /**
     * Imposta il valore della proprietà staticPtv.
     * 
     */
    public void setStaticPtv(int value) {
        this.staticPtv = value;
    }

    /**
     * Recupera il valore della proprietà dynamicPtv.
     * 
     */
    public int getDynamicPtv() {
        return dynamicPtv;
    }

    /**
     * Imposta il valore della proprietà dynamicPtv.
     * 
     */
    public void setDynamicPtv(int value) {
        this.dynamicPtv = value;
    }

    /**
     * Recupera il valore della proprietà cev.
     * 
     */
    public int getCev() {
        return cev;
    }

    /**
     * Imposta il valore della proprietà cev.
     * 
     */
    public void setCev(int value) {
        this.cev = value;
    }

    /**
     * Recupera il valore della proprietà pars.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPars() {
        return pars;
    }

    /**
     * Imposta il valore della proprietà pars.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPars(BigInteger value) {
        this.pars = value;
    }

    /**
     * Recupera il valore della proprietà timeTagged.
     * 
     */
    public int getTimeTagged() {
        return timeTagged;
    }

    /**
     * Imposta il valore della proprietà timeTagged.
     * 
     */
    public void setTimeTagged(int value) {
        this.timeTagged = value;
    }

    /**
     * Recupera il valore della proprietà planned.
     * 
     */
    public int getPlanned() {
        return planned;
    }

    /**
     * Imposta il valore della proprietà planned.
     * 
     */
    public void setPlanned(int value) {
        this.planned = value;
    }

    /**
     * Recupera il valore della proprietà execTime.
     * 
     */
    public long getExecTime() {
        return execTime;
    }

    /**
     * Imposta il valore della proprietà execTime.
     * 
     */
    public void setExecTime(long value) {
        this.execTime = value;
    }

    /**
     * Recupera il valore della proprietà execTime2.
     * 
     */
    public long getExecTime2() {
        return execTime2;
    }

    /**
     * Imposta il valore della proprietà execTime2.
     * 
     */
    public void setExecTime2(long value) {
        this.execTime2 = value;
    }

    /**
     * Recupera il valore della proprietà parent.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParent() {
        return parent;
    }

    /**
     * Imposta il valore della proprietà parent.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParent(String value) {
        this.parent = value;
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
     * Recupera il valore della proprietà earliest.
     * 
     */
    public long getEarliest() {
        return earliest;
    }

    /**
     * Imposta il valore della proprietà earliest.
     * 
     */
    public void setEarliest(long value) {
        this.earliest = value;
    }

    /**
     * Recupera il valore della proprietà latest.
     * 
     */
    public long getLatest() {
        return latest;
    }

    /**
     * Imposta il valore della proprietà latest.
     * 
     */
    public void setLatest(long value) {
        this.latest = value;
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

    /**
     * Recupera il valore della proprietà ackFlags.
     * 
     */
    public int getAckFlags() {
        return ackFlags;
    }

    /**
     * Imposta il valore della proprietà ackFlags.
     * 
     */
    public void setAckFlags(int value) {
        this.ackFlags = value;
    }

}
