package fr97.umlfx.classdiagram;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.command.CommandManager;
import fr97.umlfx.common.AbstractDiagramController;
import fr97.umlfx.common.AbstractDiagramView;
import fr97.umlfx.workspace.toolbar.Toolbar;

/**
 * TODO da se prebaci deo koda iz AbstractDiagramController
 */
public class ClassDiagramController extends AbstractDiagramController {

    /**
     * Creates new instance of ClassDiagramController
     * @param diagram
     * @param diagramView
     * @param commandManager
     * @param toolbar
     */
    public ClassDiagramController(final UmlDiagram diagram,
                                  final AbstractDiagramView diagramView,
                                  final CommandManager commandManager,
                                  final Toolbar toolbar){
        super(diagram, diagramView, commandManager, toolbar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {

    }
}
