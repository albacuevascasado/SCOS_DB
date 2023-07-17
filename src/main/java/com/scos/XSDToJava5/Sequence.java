//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.13 alle 12:53:42 PM CEST 
//


package com.scos.XSDToJava5;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="SequenceHeader" type="{}GenericRecord"/&gt;
 *         &lt;element name="SequenceParameter" type="{}GenericRecord" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sequenceHeader",
    "sequenceParameter"
})
@XmlRootElement(name = "Sequence")
public class Sequence {

    @XmlElement(name = "SequenceHeader", required = true)
    protected GenericRecord sequenceHeader;
    @XmlElement(name = "SequenceParameter")
    protected List<GenericRecord> sequenceParameter;

    /**
     * Recupera il valore della proprietà sequenceHeader.
     * 
     * @return
     *     possible object is
     *     {@link GenericRecord }
     *     
     */
    public GenericRecord getSequenceHeader() {
        return sequenceHeader;
    }

    /**
     * Imposta il valore della proprietà sequenceHeader.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericRecord }
     *     
     */
    public void setSequenceHeader(GenericRecord value) {
        this.sequenceHeader = value;
    }

    /**
     * Gets the value of the sequenceParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sequenceParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSequenceParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericRecord }
     * 
     * 
     */
    public List<GenericRecord> getSequenceParameter() {
        if (sequenceParameter == null) {
            sequenceParameter = new ArrayList<GenericRecord>();
        }
        return this.sequenceParameter;
    }

}
