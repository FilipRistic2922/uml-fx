package fr97.umlfx.classdiagram.node.classnode;


import fr97.umlfx.api.node.UmlNamedNode;
import fr97.umlfx.common.AccessModifier;
import fr97.umlfx.common.Field;
import fr97.umlfx.common.Function;
import fr97.umlfx.common.Stereotype;
import fr97.umlfx.common.node.AbstractNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

/**
 * ClassNode is an implementation of {@link fr97.umlfx.api.node.UmlNode}
 * It extends its basic node with additional fields and functions
 *
 * TODO odliciti da li klasa treba da nasledjuje InterfaceNode
 */
public class ClassNode extends AbstractNode implements UmlNamedNode {

    private final StringProperty name = new SimpleStringProperty(getId());

    private final ObjectProperty<AccessModifier> accessModifier = new SimpleObjectProperty<>(AccessModifier.PUBLIC);


    private final ObservableList<Function> functions = FXCollections.observableArrayList();
    private final ObservableList<Field> fields = FXCollections.observableArrayList();
    private final ObjectProperty<Stereotype> stereotype = new SimpleObjectProperty<>(Stereotype.NO_STEREOTYPE);

    public ClassNode() {

    }

    public ClassNode(String name) {
        if (name != null)
            this.name.set(name);

    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<Field> getFields(){
        return fields;
    }

    public ObservableList<Function> getFunctions(){
        return functions;
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

    @Override
    public ClassNode copy() {

        ClassNode copy = new ClassNode(name.get());

        copy.setStart(this.getStart().copy());
        copy.setWidth(this.getWidth());
        copy.setHeight(this.getHeight());
        copy.setTranslateX(this.getTranslateX());
        copy.setTranslateY(this.getTranslateY());
        copy.setWidthScale(this.getWidthScale());
        copy.setHeightScale(this.getHeightScale());
        copy.getFields().addAll(this.getFields().stream().map(Field::copy).collect(Collectors.toList()));
        copy.getFunctions().addAll(this.getFunctions().stream().map(Function::copy).collect(Collectors.toList()));
        return copy;
    }

    @Override
    public String toString() {
        return "ClassNode{" +
                "name=" + name +
                "fields=" + fields +
                "functions=" + functions +
                "super=" + super.toString() +
                '}';
    }
}
