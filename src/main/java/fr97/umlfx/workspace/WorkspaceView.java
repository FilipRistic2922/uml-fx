package fr97.umlfx.workspace;


import fr97.umlfx.views.FXMLView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

/**
 * Workspace visual representation
 *
 * @see fr97.umlfx.views.FXMLView
 */
public class WorkspaceView extends FXMLView<BorderPane, Workspace> {

    /**
     * {@inheritDoc}
     */
    public WorkspaceView(Workspace workspace){
        super(workspace);

    }

}
