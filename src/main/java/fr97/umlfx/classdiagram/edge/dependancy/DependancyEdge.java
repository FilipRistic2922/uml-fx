package fr97.umlfx.classdiagram.edge.dependancy;

import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;

public class DependancyEdge extends AbstractEdge {

    public DependancyEdge(UmlNode tail, UmlNode head){
        super(tail, head);
        setDirection(Direction.TAIL_TO_HEAD);
    }

    @Override
    public DependancyEdge copy() {
        return new DependancyEdge(getTail().copy(), getHead().copy());
    }
}
