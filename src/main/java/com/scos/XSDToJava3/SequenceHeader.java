//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.17 alle 05:17:44 PM CEST 
//


package com.scos.XSDToJava3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 minOccurs mandatory fields | maxOccurs all the fields
 *             
 * 
 * <p>Classe Java per SequenceHeader complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SequenceHeader"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence maxOccurs="10" minOccurs="7"&gt;
 *         &lt;element name="Field"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;group ref="{}ParamDetails"/&gt;
 *                 &lt;attribute name="name" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="SEQ TYPE"/&gt;
 *                       &lt;enumeration value="ID"/&gt;
 *                       &lt;enumeration value="PARS"/&gt;
 *                       &lt;enumeration value="CMDS"/&gt;
 *                       &lt;enumeration value="STARTTIME"/&gt;
 *                       &lt;enumeration value="STARTTIME2"/&gt;
 *                       &lt;enumeration value="SUBSYSTEM"/&gt;
 *                       &lt;enumeration value="SOURCE"/&gt;
 *                       &lt;enumeration value="TC REQUEST ID"/&gt;
 *                       &lt;enumeration value="SUB-SCHED ID"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SequenceHeader", propOrder = {
    "field"
})
public class SequenceHeader {

    @XmlElement(name = "Field", required = true)
    protected List<SequenceHeader.Field> field;

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SequenceHeader.Field }
     * 
     * 
     */
    public List<SequenceHeader.Field> getField() {
        if (field == null) {
            field = new ArrayList<SequenceHeader.Field>();
        }
        return this.field;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;group ref="{}ParamDetails"/&gt;
     *       &lt;attribute name="name" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;enumeration value="SEQ TYPE"/&gt;
     *             &lt;enumeration value="ID"/&gt;
     *             &lt;enumeration value="PARS"/&gt;
     *             &lt;enumeration value="CMDS"/&gt;
     *             &lt;enumeration value="STARTTIME"/&gt;
     *             &lt;enumeration value="STARTTIME2"/&gt;
     *             &lt;enumeration value="SUBSYSTEM"/&gt;
     *             &lt;enumeration value="SOURCE"/&gt;
     *             &lt;enumeration value="TC REQUEST ID"/&gt;
     *             &lt;enumeration value="SUB-SCHED ID"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value",
        "type",
        "units"
    })
    public static class Field {

        @XmlElement(name = "Value", required = true)
        protected String value;
        @XmlElement(name = "Type", required = true)
        protected String type;
        @XmlElement(name = "Units")
        protected String units;
        @XmlAttribute(name = "name", required = true)
        protected String name;

        /**
         * Recupera il valore della proprietà value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Imposta il valore della proprietà value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Recupera il valore della proprietà type.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Imposta il valore della proprietà type.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Recupera il valore della proprietà units.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnits() {
            return units;
        }

        /**
         * Imposta il valore della proprietà units.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnits(String value) {
            this.units = value;
        }

        /**
         * Recupera il valore della proprietà name.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Imposta il valore della proprietà name.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }

}
