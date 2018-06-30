package fr97.umlfx.classdiagram.edge.aggregation;

import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.common.edge.AbstractEdge;

public class AggregationEdge extends AbstractEdge {

    public AggregationEdge(UmlNode tail, UmlNode head){
        super(tail, head);
        setDirection(Direction.TAIL_TO_HEAD);
    }

    @Override
    public AggregationEdge copy() {
        return new AggregationEdge(getTail().copy(), getHead().copy());
    }

}
