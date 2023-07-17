//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.11 alle 09:11:28 AM CEST 
//


package com.scos.XSDToJava2;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scos.XSDToJava2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scos.XSDToJava2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sequence }
     * 
     */
    public Sequence createSequence() {
        return new Sequence();
    }

    /**
     * Create an instance of {@link Command }
     * 
     */
    public Command createCommand() {
        return new Command();
    }

    /**
     * Create an instance of {@link Command.CommandParameter }
     * 
     */
    public Command.CommandParameter createCommandCommandParameter() {
        return new Command.CommandParameter();
    }

    /**
     * Create an instance of {@link Sequence.SequenceParameter }
     * 
     */
    public Sequence.SequenceParameter createSequenceSequenceParameter() {
        return new Sequence.SequenceParameter();
    }

    /**
     * Create an instance of {@link FlightPlan }
     * 
     */
    public FlightPlan createFlightPlan() {
        return new FlightPlan();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Tasks }
     * 
     */
    public Tasks createTasks() {
        return new Tasks();
    }

    /**
     * Create an instance of {@link BaseHeaderType }
     * 
     */
    public BaseHeaderType createBaseHeaderType() {
        return new BaseHeaderType();
    }

    /**
     * Create an instance of {@link Task }
     * 
     */
    public Task createTask() {
        return new Task();
    }

    /**
     * Create an instance of {@link SequenceHeaderType }
     * 
     */
    public SequenceHeaderType createSequenceHeaderType() {
        return new SequenceHeaderType();
    }

    /**
     * Create an instance of {@link CommandHeaderType }
     * 
     */
    public CommandHeaderType createCommandHeaderType() {
        return new CommandHeaderType();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.Id }
     * 
     */
    public Command.CommandParameter.Id createCommandCommandParameterId() {
        return new Command.CommandParameter.Id();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.FormPos }
     * 
     */
    public Command.CommandParameter.FormPos createCommandCommandParameterFormPos() {
        return new Command.CommandParameter.FormPos();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.Type }
     * 
     */
    public Command.CommandParameter.Type createCommandCommandParameterType() {
        return new Command.CommandParameter.Type();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.Editable }
     * 
     */
    public Command.CommandParameter.Editable createCommandCommandParameterEditable() {
        return new Command.CommandParameter.Editable();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.RepType }
     * 
     */
    public Command.CommandParameter.RepType createCommandCommandParameterRepType() {
        return new Command.CommandParameter.RepType();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.Value }
     * 
     */
    public Command.CommandParameter.Value createCommandCommandParameterValue() {
        return new Command.CommandParameter.Value();
    }

    /**
     * Create an instance of {@link Command.CommandParameter.Dynamic }
     * 
     */
    public Command.CommandParameter.Dynamic createCommandCommandParameterDynamic() {
        return new Command.CommandParameter.Dynamic();
    }

    /**
     * Create an instance of {@link Sequence.SequenceParameter.Id }
     * 
     */
    public Sequence.SequenceParameter.Id createSequenceSequenceParameterId() {
        return new Sequence.SequenceParameter.Id();
    }

    /**
     * Create an instance of {@link Sequence.SequenceParameter.Type }
     * 
     */
    public Sequence.SequenceParameter.Type createSequenceSequenceParameterType() {
        return new Sequence.SequenceParameter.Type();
    }

    /**
     * Create an instance of {@link Sequence.SequenceParameter.RepType }
     * 
     */
    public Sequence.SequenceParameter.RepType createSequenceSequenceParameterRepType() {
        return new Sequence.SequenceParameter.RepType();
    }

    /**
     * Create an instance of {@link Sequence.SequenceParameter.Value }
     * 
     */
    public Sequence.SequenceParameter.Value createSequenceSequenceParameterValue() {
        return new Sequence.SequenceParameter.Value();
    }

}
