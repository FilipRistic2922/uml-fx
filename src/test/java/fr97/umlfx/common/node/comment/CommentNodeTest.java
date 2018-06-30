package fr97.umlfx.common.node.comment;

import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentNodeTest {

    private CommentNode node = new CommentNode();

    @Test
    @DisplayName("Copy not same as original test")
    void copyTest(){
        CommentNode copy = node.copy();
        assertNotSame(node, copy, "copy is same as original");
    }

}