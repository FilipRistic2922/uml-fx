package fr97.umlfx.classdiagram.node.interfacenode;


import fr97.umlfx.api.node.UmlNamedNode;
import fr97.umlfx.common.AccessModifier;
import fr97.umlfx.common.Function;
import fr97.umlfx.common.Stereotype;
import fr97.umlfx.common.node.AbstractNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * InterfaceNode is an implementation of {@link fr97.umlfx.api.node.UmlNode}
 * It extends its basic node with additional functions
 */
public class InterfaceNode extends AbstractNode implements UmlNamedNode {

    private final StringProperty name = new SimpleStringProperty(getId());

    private final ObjectProperty<AccessModifier> accessModifier = new SimpleObjectProperty<>(AccessModifier.PUBLIC);

    private final ObservableList<Function> functions = FXCollections.observableArrayList();
    private final ObjectProperty<Stereotype> stereotype = new SimpleObjectProperty<>(Stereotype.INTERFACE);

    public InterfaceNode(){

    }

    public InterfaceNode(String name){
        if(name != null)
            this.name.set(name);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<Function> getFunctions(){
        return functions;
    }

    public boolean addMethod(Function function){
        return functions.add(function);
    }

    public boolean hasMethod(Function function){
        return functions.contains(function);
    }

    public boolean removeMethod(Function function){
        return functions.remove(function);
    }


    public Stereotype getStereotype() {
        return stereotype.get();
    }

    public ObjectProperty<Stereotype> stereotypeProperty() {
        return stereotype;
    }

    public void setStereotype(Stereotype stereotype) {
        this.stereotype.set(stereotype);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceNode copy() {

        InterfaceNode copy = new InterfaceNode(name.get());

        copy.setStart(this.getStart().copy());
        copy.setWidth(this.getWidth());
        copy.setHeight(this.getHeight());
        copy.setTranslateX(this.getTranslateX());
        copy.setTranslateY(this.getTranslateY());
        copy.setWidthScale(this.getWidthScale());
        copy.setHeightScale(this.getHeightScale());
        copy.stereotype.set(this.stereotype.get());

        return copy;
    }

    @Override
    public String toString() {
        return "InterfaceNode{" +
                "name=" + name +
                "functions=" + functions +
                "super=" + super.toString() +
                '}';
    }

}
