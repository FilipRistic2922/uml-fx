package fr97.umlfx.common.node.comment;

import fr97.umlfx.app.Theme;
import fr97.umlfx.common.node.AbstractNodeView;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CommentNodeView extends AbstractNodeView {

    private final StackPane container = new StackPane();
    private final TextArea textArea = new TextArea();

    public CommentNodeView(CommentNode node){
        super(node);

        container.setPadding(new Insets(10, 10, 10, 10));
        container.prefWidthProperty().bind(node.widthProperty());
        container.prefHeightProperty().bind(node.heightProperty());
        container.maxWidthProperty().bind(node.widthProperty());
        container.maxHeightProperty().bind(node.heightProperty());
        container.setBorder(Theme.defautTheme.borderProperty.get());
        container.getStyleClass().add("comment-stack");

        textArea.textProperty().bindBidirectional(node.textProperty());
        textArea.prefWidthProperty().bind(node.widthProperty().subtract(10));
        textArea.prefHeightProperty().bind(node.widthProperty().subtract(10));
        textArea.setPadding(new Insets(5, 5,5,5));
        textArea.setFont(Theme.defautTheme.fontProperty.get());
        textArea.setWrapText(true);

        container.getChildren().add(textArea);
        getChildren().add(container);
        createResizeLines(container);
    }



    @Override
    public void setSelected(boolean selected) {
        if(selected)
            container.setBorder(Theme.defautTheme.selectedBorderProperty.get());
        else
            container.setBorder(Theme.defautTheme.borderProperty.get());
    }

    @Override
    public Bounds getBounds() {
        return textArea.getBoundsInParent();
    }
}
