package fr97.umlfx.workspace;

import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.command.CommandManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Workspace model contains all data needed for workspace and its children
 *
 */
public class Workspace {

    private ObservableList<UmlDiagram> diagrams;

    private final Stage stage;

    private final ObjectProperty<UmlDiagram> activeDiagram;

    private final CommandManager commandManager;

    public Workspace(ObservableList<UmlDiagram> diagrams, CommandManager commandManager, Stage stage) {
        this.diagrams = diagrams;
        this.activeDiagram = new SimpleObjectProperty<>();
        this.commandManager = commandManager;
        this.stage = stage;
    }

    public ObservableList<UmlDiagram> getDiagrams() {
        return diagrams;
    }

    public Optional<UmlDiagram> getActiveDiagram() {
        return Optional.ofNullable(activeDiagram.get());
    }

    public ObjectProperty<UmlDiagram> activeDiagramProperty() {
        return activeDiagram;
    }

    public void setActiveDiagram(UmlDiagram activeDiagram) {
        this.activeDiagram.set(activeDiagram);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Stage getStage() {
        return stage;
    }
}
