package fr97.umlfx.classdiagram.edge.aggregation;

import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import fr97.umlfx.classdiagram.node.interfacenode.InterfaceNode;
import fr97.umlfx.common.node.comment.CommentNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AggregationEdgeTest {

    private UmlNode tail = new ClassNode();
    private UmlNode head = new InterfaceNode();
    private AggregationEdge edge = new AggregationEdge(tail, head);

    @Test
    @DisplayName("Copy not same as original test")
    void copyTest(){
        AggregationEdge copy = edge.copy();
        assertNotSame(edge, copy, "copy is same as original" + copy);
        assertNotSame(tail, copy.getTail(), "tail of copy is same as original " + copy);
        assertNotSame(head, copy.getHead(), "tail of copy is same as original " + copy);
    }

}