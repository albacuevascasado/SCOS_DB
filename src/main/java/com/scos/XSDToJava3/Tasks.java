//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.17 alle 05:17:44 PM CEST 
//


package com.scos.XSDToJava3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BaseHeader" type="{}BaseHeader"/&gt;
 *         &lt;element ref="{}Task" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NoTask" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "baseHeader",
    "task"
})
@XmlRootElement(name = "Tasks")
public class Tasks {

    @XmlElement(name = "BaseHeader", required = true)
    protected BaseHeader baseHeader;
    @XmlElement(name = "Task", required = true)
    protected List<Task> task;
    @XmlAttribute(name = "NoTask", required = true)
    protected BigInteger noTask;

    /**
     * Recupera il valore della proprietà baseHeader.
     * 
     * @return
     *     possible object is
     *     {@link BaseHeader }
     *     
     */
    public BaseHeader getBaseHeader() {
        return baseHeader;
    }

    /**
     * Imposta il valore della proprietà baseHeader.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseHeader }
     *     
     */
    public void setBaseHeader(BaseHeader value) {
        this.baseHeader = value;
    }

    /**
     * Gets the value of the task property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the task property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTask().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Task }
     * 
     * 
     */
    public List<Task> getTask() {
        if (task == null) {
            task = new ArrayList<Task>();
        }
        return this.task;
    }

    /**
     * Recupera il valore della proprietà noTask.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNoTask() {
        return noTask;
    }

    /**
     * Imposta il valore della proprietà noTask.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNoTask(BigInteger value) {
        this.noTask = value;
    }

}
