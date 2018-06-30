package fr97.umlfx.common;

import fr97.umlfx.api.UmlDiagram;
import javafx.scene.layout.Pane;

public class AbstractDiagramView extends Pane {

    public static final int MINIMUM_SIZE = 2000;

    private final UmlDiagram diagram;

    protected AbstractDiagramView(UmlDiagram diagram){
        this.diagram = diagram;

    }


}
