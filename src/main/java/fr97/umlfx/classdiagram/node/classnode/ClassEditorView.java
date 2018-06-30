package fr97.umlfx.classdiagram.node.classnode;

import fr97.umlfx.app.Theme;
import fr97.umlfx.views.FXMLView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class ClassEditorView extends FXMLView<StackPane,ClassNode> {

    public ClassEditorView(ClassNode model) throws IllegalStateException, IllegalArgumentException {
        super(model);

    }


}
