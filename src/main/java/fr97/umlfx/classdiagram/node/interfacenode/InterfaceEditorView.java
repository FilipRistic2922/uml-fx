package fr97.umlfx.classdiagram.node.interfacenode;

import fr97.umlfx.views.FXMLView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;


public class InterfaceEditorView extends FXMLView<StackPane, InterfaceNode> {

    public InterfaceEditorView(InterfaceNode model) throws IllegalStateException, IllegalArgumentException {
        super(model);
    }


}
