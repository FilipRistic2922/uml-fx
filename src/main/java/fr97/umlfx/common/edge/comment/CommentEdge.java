package fr97.umlfx.common.edge.comment;


import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;
import fr97.umlfx.common.node.comment.CommentNode;

/**
 * Comment edge represents a connection between {@link CommentNode} and any other {@link UmlNode}
 *
 */
public class CommentEdge extends AbstractEdge {

    public CommentEdge(UmlNode tail, UmlNode head){
        super(tail, head);
    }


    @Override
    public CommentNode getHead() {
        return (CommentNode)headProperty().get();
    }

    @Override
    public CommentEdge copy() {
        return new CommentEdge(getTail().copy(), getHead().copy());
    }
}
