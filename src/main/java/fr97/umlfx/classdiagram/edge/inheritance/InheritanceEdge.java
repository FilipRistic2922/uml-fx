package fr97.umlfx.classdiagram.edge.inheritance;

import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;

public class InheritanceEdge extends AbstractEdge {

    public InheritanceEdge(UmlNode tail, UmlNode head){
        super(tail, head);
        setDirection(Direction.TAIL_TO_HEAD);
    }

    @Override
    public InheritanceEdge copy() {
        return new InheritanceEdge(getTail().copy(), getHead().copy());
    }

}
