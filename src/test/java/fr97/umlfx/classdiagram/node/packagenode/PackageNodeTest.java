package fr97.umlfx.classdiagram.node.packagenode;

import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackageNodeTest {

    private ClassNode cnode = new ClassNode("Class Node");
    private PackageNode pnode1 = new PackageNode("Package Node");
    private PackageNode pnode2 = new PackageNode("Package Node");

    @Test
    @DisplayName("PackageNode should add ClassNode and another PackageNode")
    void testAddsAcceptedNodes(){

        assertTrue(pnode1.acceptedChildren().contains(cnode.getClass()));
        assertTrue(pnode1.acceptedChildren().contains(pnode2.getClass()));

        assertTrue(pnode1.addChild(cnode));
        assertTrue(pnode1.addChild(pnode2));

        assertTrue(pnode1.getChildren().contains(cnode));
        assertTrue(pnode1.getChildren().contains(pnode2));
    }

    @Test
    @DisplayName("PackageNode should not add itself as children")
    void testDoesntAddItself(){

        assertFalse(pnode1.addChild(pnode1));

        assertFalse(pnode1.getChildren().contains(pnode1),
                "Package node shouldn't contain itself, node state: " + pnode1.toString());
    }

    @Test
    @DisplayName("Copy not same as original test")
    void copyTest(){
        PackageNode copy = pnode1.copy();
        assertNotSame( pnode1, copy, "copy is same as original");
    }
}