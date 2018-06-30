package fr97.umlfx.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * This class is used to represent multiplicity between elements
 * TODO klasa je istestirana, treba da se implementira prikaz samo i da se napravi editor
 */
public class Multiplicity {

    private StringProperty lowerBound = new SimpleStringProperty("0");

    private StringProperty higherBound = new SimpleStringProperty("0");

    public Multiplicity(){

    }

    public Multiplicity(String lowerBound, String higherBound){
        this.lowerBound.set(lowerBound);
        this.higherBound.set(higherBound);
    }

    public String getLowerBound() {
        return lowerBound.get();
    }

    public StringProperty lowerBoundProperty() {
        return lowerBound;
    }

    public String getHigherBound() {
        return higherBound.get();
    }

    public StringProperty higherBoundProperty() {
        return higherBound;
    }

    public StringBinding umlNotationBinding(){
        return Bindings.createStringBinding(
                ()->lowerBound.get() + ".."+higherBound.get(),
                lowerBound, higherBound
        );
    }

    @Override
    public String toString() {
        return "Multiplicity{" +
                "lowerBound=" + lowerBound +
                ", higherBound=" + higherBound +
                '}';
    }
}
