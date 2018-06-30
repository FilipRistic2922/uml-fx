package fr97.umlfx.classdiagram.edge.composition;

import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;

public class CompositionEdge extends AbstractEdge {


    public CompositionEdge(UmlNode tail, UmlNode head){
        super(tail, head);
        setDirection(Direction.TAIL_TO_HEAD);
    }

    @Override
    public CompositionEdge copy() {
        return new CompositionEdge(getTail().copy(), getHead().copy());
    }

}
