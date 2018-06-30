package fr97.umlfx.common.edge.comment;

import fr97.umlfx.common.edge.AbstractEdgeView;
import javafx.scene.Group;

public class CommentEdgeView extends AbstractEdgeView {


    public CommentEdgeView(CommentEdge edge){
        super(edge);

        calculatePositions(null,null,null);
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected Group createShape(double startX, double startY, double endX, double endY) {
        return new Group();
    }
}
