package fr97.umlfx.menubar;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.command.CommandManager;
import fr97.umlfx.utils.ArgumentChecker;
import javafx.collections.ObservableList;

public class Menubar {

    private final CommandManager commandManager;
    private final ObservableList<UmlDiagram> diagrams;

    public Menubar(final CommandManager commandManager, final ObservableList<UmlDiagram> diagrams) {
        ArgumentChecker.notNull(commandManager, "CommandManager can't be null");
        ArgumentChecker.notNull(diagrams, "Diagram list can't be null");
        this.commandManager = commandManager;
        this.diagrams = diagrams;
    }

    public final CommandManager getCommandManager() {
        return commandManager;
    }

    public ObservableList<UmlDiagram> getDiagrams() {
        return diagrams;
    }
}
