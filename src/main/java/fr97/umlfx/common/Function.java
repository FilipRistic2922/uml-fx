package fr97.umlfx.common;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Iterator;

public class Function {

    private final StringProperty returnType;
    private final ObjectProperty<AccessModifier> accessModifier;
    private final StringProperty name;
    private final ObservableMap<String, String> parameters;
    private final BooleanProperty isStatic = new SimpleBooleanProperty(false);
    public Function(String returnType, AccessModifier accessModifier, String name) {
        this(returnType, accessModifier, name, new HashMap<>());
    }

    public Function(String returnType, AccessModifier accessModifier, String name, HashMap<String, String> parameters) {
        this.returnType = new SimpleStringProperty(returnType);
        this.accessModifier = new SimpleObjectProperty<>(accessModifier);
        this.name = new SimpleStringProperty(name);
        this.parameters = FXCollections.observableMap(parameters);
    }

    public Function(String returnType, AccessModifier accessModifier, String name, HashMap<String, String> parameters, boolean isStatic) {
        this.returnType = new SimpleStringProperty(returnType);
        this.accessModifier = new SimpleObjectProperty<>(accessModifier);
        this.name = new SimpleStringProperty(name);
        this.parameters = FXCollections.observableMap(parameters);
        this.isStatic.set(isStatic);
    }

    public String getReturnType() {
        return returnType.get();
    }

    public StringProperty returnTypeProperty() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType.set(returnType);
    }

    public AccessModifier getAccessModifier() {
        return accessModifier.get();
    }

    public ObjectProperty<AccessModifier> accessModifierProperty() {
        return accessModifier;
    }

    public void setAccessModifier(AccessModifier accessModifier) {
        this.accessModifier.set(accessModifier);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableMap<String, String> getParameters() {
        return parameters;
    }

    public boolean isStatic() {
        return isStatic.get();
    }

    public BooleanProperty staticProperty() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic.set(isStatic);
    }

    public StringBinding bindSignature() {
        StringBinding binding = Bindings.createStringBinding(() -> {
            StringBuilder builder = new StringBuilder();
            builder.append(accessModifier.get().getUmlSign())
                    .append(" ")
                    .append(name.get())
                    .append(" (");
            for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext(); ) {
                String key = iter.next();
                builder.append(key)
                        .append(" : ")
                        .append(parameters.get(key));
                if (iter.hasNext())
                    builder.append(", ");
            }

            builder.append(") : ")
                    .append(returnType.get());

            return builder.toString();
        }, returnType, accessModifier, name, parameters);

        return binding;
    }

    public Function copy(){
        Function copy = new Function(this.getReturnType(),this.getAccessModifier(), this.getName());
        this.parameters.forEach(copy.parameters::put);
        return copy;
    }

    @Override
    public String toString() {
        return "Function{" +
                "returnType=" + returnType.get() +
                ", accessModifier=" + accessModifier.get() +
                ", name=" + name.get() +
                ", parameters=" + parameters +
                '}';
    }
}
