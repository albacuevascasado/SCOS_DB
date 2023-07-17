//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.11 alle 09:10:30 AM CEST 
//


package com.scos.XSDToJava1;

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
 *         &lt;element name="CommandHeader" type="{}CommandHeaderType"/&gt;
 *         &lt;element name="CommandParameter" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="formPos" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="editable" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="repType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="dynamic" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                 &lt;/sequence&gt;
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
@XmlType(name = "", propOrder = {
    "commandHeader",
    "commandParameter"
})
@XmlRootElement(name = "Command")
public class Command {

    @XmlElement(name = "CommandHeader", required = true)
    protected CommandHeaderType commandHeader;
    @XmlElement(name = "CommandParameter")
    protected List<Command.CommandParameter> commandParameter;

    /**
     * Recupera il valore della proprietà commandHeader.
     * 
     * @return
     *     possible object is
     *     {@link CommandHeaderType }
     *     
     */
    public CommandHeaderType getCommandHeader() {
        return commandHeader;
    }

    /**
     * Imposta il valore della proprietà commandHeader.
     * 
     * @param value
     *     allowed object is
     *     {@link CommandHeaderType }
     *     
     */
    public void setCommandHeader(CommandHeaderType value) {
        this.commandHeader = value;
    }

    /**
     * Gets the value of the commandParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commandParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommandParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Command.CommandParameter }
     * 
     * 
     */
    public List<Command.CommandParameter> getCommandParameter() {
        if (commandParameter == null) {
            commandParameter = new ArrayList<Command.CommandParameter>();
        }
        return this.commandParameter;
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
     *       &lt;sequence&gt;
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="formPos" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="editable" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="repType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="dynamic" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
        "id",
        "formPos",
        "type",
        "editable",
        "repType",
        "value",
        "dynamic"
    })
    public static class CommandParameter {

        @XmlElement(required = true)
        protected String id;
        protected int formPos;
        protected int type;
        protected int editable;
        protected int repType;
        @XmlElement(required = true)
        protected String value;
        protected int dynamic;

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
         * Recupera il valore della proprietà formPos.
         * 
         */
        public int getFormPos() {
            return formPos;
        }

        /**
         * Imposta il valore della proprietà formPos.
         * 
         */
        public void setFormPos(int value) {
            this.formPos = value;
        }

        /**
         * Recupera il valore della proprietà type.
         * 
         */
        public int getType() {
            return type;
        }

        /**
         * Imposta il valore della proprietà type.
         * 
         */
        public void setType(int value) {
            this.type = value;
        }

        /**
         * Recupera il valore della proprietà editable.
         * 
         */
        public int getEditable() {
            return editable;
        }

        /**
         * Imposta il valore della proprietà editable.
         * 
         */
        public void setEditable(int value) {
            this.editable = value;
        }

        /**
         * Recupera il valore della proprietà repType.
         * 
         */
        public int getRepType() {
            return repType;
        }

        /**
         * Imposta il valore della proprietà repType.
         * 
         */
        public void setRepType(int value) {
            this.repType = value;
        }

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
         * Recupera il valore della proprietà dynamic.
         * 
         */
        public int getDynamic() {
            return dynamic;
        }

        /**
         * Imposta il valore della proprietà dynamic.
         * 
         */
        public void setDynamic(int value) {
            this.dynamic = value;
        }

    }

}
