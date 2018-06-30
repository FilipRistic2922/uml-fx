package fr97.umlfx.common.node.comment;

import fr97.umlfx.common.node.NodeEditorView;
import fr97.umlfx.views.FXMLView;
import fr97.umlfx.views.View;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class CommentEditorView implements View<TextArea> {

    private final TextArea textArea;

    public CommentEditorView(CommentNode model) {

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.textProperty().bindBidirectional(model.textProperty());
    }

    @Override
    public TextArea getRoot() {
        return textArea;
    }
}
