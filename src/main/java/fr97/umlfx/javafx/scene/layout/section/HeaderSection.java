package fr97.umlfx.javafx.scene.layout.section;



import fr97.umlfx.common.Stereotype;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main section of every section pane
 * TODO treba da se kleanupuje
 */
public class HeaderSection extends Section {


    private final Label lblStereotype;
    private final Label lblText;

    private final ObjectProperty<Stereotype> stereotype;
    private final StringProperty text;

    public HeaderSection(){
        this("");
    }

    public HeaderSection(String text){
        this(text, Stereotype.NO_STEREOTYPE);
    }

    public HeaderSection(String text, Stereotype stereotype){
        super("header");
        this.stereotype = new SimpleObjectProperty<>(stereotype);
        this.text = new SimpleStringProperty(text);
        lblStereotype = new Label(stereotype.getText());
        lblStereotype.textProperty().bind(Bindings.createStringBinding(()->stereotypeProperty().get().getText(), this.stereotype));
        lblStereotype.setFont(Font.font("Verdana", 10));
        lblText = new Label(text);
        lblText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        setAlignment(Pos.BASELINE_CENTER);
        getChildren().addAll(lblStereotype, lblText);
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

    public String getText() {
        return lblText.textProperty().get();
    }

    public StringProperty textProperty() {
        return this.lblText.textProperty();
    }

    public void setText(String text) {
        this.lblText.textProperty().set(text);
    }


}
