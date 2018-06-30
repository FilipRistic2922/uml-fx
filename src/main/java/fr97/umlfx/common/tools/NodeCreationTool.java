package fr97.umlfx.common.tools;

import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.api.tool.UmlTool;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.function.Supplier;

public class NodeCreationTool implements UmlTool {

    private Supplier<UmlNode> nodeSupplier;

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty tooltip = new SimpleStringProperty();
    public NodeCreationTool(Supplier<UmlNode> nodeSupplier, StringBinding name, StringBinding tooltip){
        this.nodeSupplier = nodeSupplier;
        this.name.bind(name);
        this.tooltip.bind(tooltip);
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public StringProperty tooltipProperty() {
        return tooltip;
    }


    @Override
    public void onMouseEvent(MouseEvent event, UmlDiagram diagram) {

        if(event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY){
            UmlNode n = nodeSupplier.get();
            n.setTranslateX(event.getX());
            n.setTranslateY(event.getY());
            diagram.getNodes().add(n);
        }
        event.consume();
    }

    @Override
    public void onKeyEvent(KeyEvent event, UmlDiagram diagram) {

    }
}
