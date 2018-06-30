package fr97.umlfx.common.node;

import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.views.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class NodeEditorController implements FXMLController<UmlNode> {


    private UmlNode node;

    private Consumer<Event> onApply = event -> {
    };
    private Consumer<Event> onCancel = event -> {
    };
    @FXML
    private Button btnApply, btnCancel;


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnApply.setOnAction(event -> onApply.accept(event));
        btnCancel.setOnAction(event -> onCancel.accept(event));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(UmlNode model) {
        this.node = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UmlNode getModel() {
        return node;
    }

    public void setOnApply(Consumer<Event> onApply) {
        this.onApply = onApply;
    }

    public void setOnCancel(Consumer<Event> onCancel) {
        this.onCancel = onCancel;
    }
}
