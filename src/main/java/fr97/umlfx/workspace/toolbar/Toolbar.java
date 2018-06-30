package fr97.umlfx.workspace.toolbar;

import fr97.umlfx.api.tool.UmlTool;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collection;
import java.util.Optional;

/**
 * Toolbar model, contains tools supported by active diagram, also has property of currently selected tool
 */
public class Toolbar {

    private final ObjectProperty<UmlTool> activeTool = new SimpleObjectProperty<>();
    private final ObservableList<UmlTool> tools = FXCollections.observableArrayList();

    public Toolbar(Collection<UmlTool> tools){

        this.tools.addAll(tools);
        this.activeTool.addListener(l ->{
            //System.out.println("Tool changed to " + activeTool.get());
        });
    }

    public Optional<UmlTool> getActiveTool(){
        return Optional.ofNullable(activeTool.get());
    }
    public ObjectProperty<UmlTool> activeToolProperty() {
        return activeTool;
    }

    public void setActiveTool(UmlTool activeTool) {
        this.activeTool.set(activeTool);
    }

    public ObservableList<UmlTool> getTools() {
        return tools;
    }
}
