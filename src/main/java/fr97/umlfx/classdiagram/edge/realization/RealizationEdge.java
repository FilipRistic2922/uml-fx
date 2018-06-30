package fr97.umlfx.classdiagram.edge.realization;

import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;

public class RealizationEdge extends AbstractEdge {

    public RealizationEdge(UmlNode tail, UmlNode head){

        super(tail, head);
        setDirection(Direction.TAIL_TO_HEAD);
    }

    @Override
    public RealizationEdge copy() {
        return new RealizationEdge(getTail().copy(), getHead().copy());
    }

}
