package fr97.umlfx.classdiagram.edge.association;

import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;

public class AssociationEdge extends AbstractEdge {

    public AssociationEdge(UmlNode tail, UmlNode head){
        super(tail, head);
        setDirection(Direction.TAIL_TO_HEAD);
    }

    @Override
    public AssociationEdge copy() {
        return new AssociationEdge(getTail().copy(), getHead().copy());
    }


}
