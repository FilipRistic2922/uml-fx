package fr97.umlfx.common;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;

public class Field {

    private StringProperty type;
    private ObjectProperty<AccessModifier> accessModifier;
    private StringProperty fieldName;
    private StringProperty defaultValue = new SimpleStringProperty();
    private BooleanProperty isConstant;
    private BooleanProperty isStatic;


    public Field(String type, AccessModifier accessModifier, String fieldName) {
        this(type, accessModifier, fieldName, false);
    }

    public Field(String type, AccessModifier accessModifier, String fieldName, boolean isConstant) {
        this(type, accessModifier, fieldName, "", isConstant, false);
    }

    public Field(String type, AccessModifier accessModifier, String fieldName, boolean isConstant, boolean isStatic) {
        this(type, accessModifier, fieldName, "", isConstant, isStatic);
    }

    public Field(String type, AccessModifier accessModifier, String fieldName, String defaultValue, boolean isConstant, boolean isStatic) {

        this.defaultValue.addListener((obs, oldValue, newValue)->{
            if(oldValue != newValue){
                if(newValue == null || newValue.equals(""))
                    this.defaultValue.set("None");
            }
        });

        this.type = new SimpleStringProperty(type);
        this.accessModifier = new SimpleObjectProperty<>(accessModifier);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.defaultValue.set(defaultValue);
        this.isConstant = new SimpleBooleanProperty(isConstant);
        this.isStatic = new SimpleBooleanProperty(isStatic);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
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

    public String getFieldName() {
        return fieldName.get();
    }

    public StringProperty fieldNameProperty() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName.set(fieldName);
    }

    public String getDefaultValue() {
        return defaultValue.get();
    }

    public StringProperty defaultValueProperty() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue.set(defaultValue);
    }

    public boolean isConstant() {
        return isConstant.get();
    }

    public BooleanProperty constantProperty() {
        return isConstant;
    }

    public void setConstant(boolean isConstant) {
        this.isConstant.set(isConstant);
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
                    .append(fieldName.get())
                    .append(" : ")
                    .append(type.get());

            if (defaultValue.get() != null && !defaultValue.get().equals("") && !defaultValue.get().toLowerCase().equals("none")) {
                builder.append(" = ")
                        .append(defaultValue.get());
            }

            if (isConstant.get()) {
                builder.append(" {constant} ");
            }

            return builder.toString();
        }, type, accessModifier, fieldName, defaultValue, isConstant, isStatic);
        return binding;
    }

    public Field copy(){
        Field copy = new Field(this.getType(), this.getAccessModifier(), this.getFieldName());
        copy.setStatic(this.isStatic());
        copy.setConstant(this.isConstant());
        copy.setDefaultValue(this.getDefaultValue());
        return copy;
    }

    @Override
    public String toString() {
        return "Field{" +
                "type=" + type.get() +
                ", accessModifier=" + accessModifier.get() +
                ", fieldName=" + fieldName.get() +
                ", defaultValue=" + defaultValue.get() +
                ", isConstant=" + isConstant.get() +
                ", isStatic=" + isStatic.get() +
                '}';
    }
}
