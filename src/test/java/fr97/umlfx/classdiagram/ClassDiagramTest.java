package fr97.umlfx.classdiagram;

import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.classdiagram.edge.realization.RealizationEdge;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import fr97.umlfx.classdiagram.node.interfacenode.InterfaceNode;
import fr97.umlfx.math.geometry.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClassDiagramTest {

    private ClassDiagram diagram = new ClassDiagram("Diagram");
    private Point p = Point.of(0, 0);
    private UmlNode node = new ClassNode();
    private UmlNode node2 = new InterfaceNode();
    private UmlEdge edge = new RealizationEdge(node, node2);

    @Test
    @DisplayName("When diagram contains node on given point it should return node")
    void findNodeTest() {
        node.setTranslateX(10);
        node.setTranslateY(24);
        node.setWidth(50);
        node.setHeight(43);
        diagram.addNode(node);
        p.moveTo(30, 34);
        Optional<UmlNode> result = diagram.findNode(p);
        if (result.isPresent()) {
            assertEquals(node, result.get(), "nodes are not the same");
        } else {
            fail("diagram didn't find node even though it was there");

        }
    }

    @Test
    @DisplayName("Diagram shouldn't remove edge if copy of edge is given")
    void findEdgeTest() {
        diagram.addEdge(edge);
        UmlEdge copy = edge.copy();
        assertTrue(diagram.containsEdge(edge), "diagram didn't contain edge before deleting");
        assertFalse(diagram.removeEdge(copy), "diagram deleted edge when given copy");
    }


}