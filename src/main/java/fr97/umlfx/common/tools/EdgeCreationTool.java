package fr97.umlfx.common.tools;

import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.api.tool.UmlTool;
import fr97.umlfx.classdiagram.edge.inheritance.InheritanceEdge;
import fr97.umlfx.classdiagram.edge.realization.RealizationEdge;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import fr97.umlfx.classdiagram.node.interfacenode.InterfaceNode;
import fr97.umlfx.common.edge.comment.CommentEdge;
import fr97.umlfx.common.node.comment.CommentNode;
import fr97.umlfx.math.geometry.Point;
import fr97.umlfx.utils.ArgumentChecker;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.function.BiFunction;

public class EdgeCreationTool implements UmlTool {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty tooltip = new SimpleStringProperty();
    private final BiFunction<UmlNode, UmlNode, UmlEdge> edgeSupplier;

    private UmlNode tailNode;
    private UmlNode headNode;

    private final Line dragLine = new Line();

    public EdgeCreationTool(BiFunction<UmlNode, UmlNode, UmlEdge> edgeSupplier, StringBinding name, StringBinding tooltip) {
        ArgumentChecker.notNull(edgeSupplier, "Edge supplier can't be null");
        ArgumentChecker.notNull(name, "name binding can't be null");
        ArgumentChecker.notNull(tooltip, "name binding can't be null");
        this.edgeSupplier = edgeSupplier;
        this.name.bind(name);
        this.tooltip.bind(tooltip);
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public StringProperty tooltipProperty() {
        return tooltip;
    }

    @Override
    public void onMouseEvent(MouseEvent event, UmlDiagram diagram) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
            onMouseClicked(event, diagram);
        else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            // System.out.println("Event source: " + event.getSource());
            /*Optional<UmlNode> clickedNode = */
            diagram.getNodes().stream()
                    .filter(n -> n.getBounds().contains(Point.of(event.getX(), event.getY())))
                    .findFirst()
                    .ifPresent(node -> onMousePressedOnNode(event, diagram, node));

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
            onMouseDragged(event, diagram);
        else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            diagram.getNodes().stream()
                    .filter(n -> n.getBounds().contains(Point.of(event.getX(), event.getY())))
                    .findFirst()
                    .ifPresent(node -> onMouseReleasedOnNode(event, diagram, node));
            ((Pane) event.getSource()).getChildren().remove(dragLine);
        }


    }

    private void onMousePressedOnNode(MouseEvent event, UmlDiagram diagram, UmlNode node) {
        dragLine.setStartX(event.getX());
        dragLine.setStartY(event.getY());
        dragLine.setEndX(event.getX());
        dragLine.setEndY(event.getY());
        ((Pane) event.getSource()).getChildren().add(dragLine);
        //System.out.println("Clicked on node: " + node);
        tailNode = node;

    }

    private void onMouseDragged(MouseEvent event, UmlDiagram diagram) {
        dragLine.setEndX(event.getX());
        dragLine.setEndY(event.getY());

    }

    private void onMouseReleasedOnNode(MouseEvent event, UmlDiagram diagram, UmlNode node) {
        //System.out.println("Ended on node: " + node);
        headNode = node;
        UmlEdge e = edgeSupplier.apply(tailNode, headNode);
        if (e instanceof CommentEdge) {
            if (headNode instanceof CommentNode)
                diagram.getEdges().add(edgeSupplier.apply(tailNode, headNode));
            else if (tailNode instanceof CommentNode)
                diagram.getEdges().add(edgeSupplier.apply(headNode, tailNode));
        } else if (headNode instanceof CommentNode || tailNode instanceof CommentNode) {
            // System.out.println("Won't connect");
            return;
        } else if (e instanceof RealizationEdge) {
            if (tailNode instanceof InterfaceNode && !(headNode instanceof InterfaceNode))
                diagram.getEdges().add(edgeSupplier.apply(headNode, tailNode));
            else if (!(tailNode instanceof InterfaceNode) && headNode instanceof InterfaceNode)
                diagram.getEdges().add(edgeSupplier.apply(tailNode, headNode));

            //System.out.println("Last option");
        } else if (e instanceof InheritanceEdge) {
            if (tailNode instanceof InterfaceNode && headNode instanceof InterfaceNode)
                diagram.getEdges().add(edgeSupplier.apply(tailNode, headNode));
            else if (tailNode instanceof ClassNode && headNode instanceof ClassNode)
                diagram.getEdges().add(edgeSupplier.apply(tailNode, headNode));
        } else {
            diagram.getEdges().add(edgeSupplier.apply(tailNode, headNode));
        }
        tailNode = null;
        headNode = null;
    }


    private void onMouseClicked(MouseEvent event, UmlDiagram diagram) {

    }

    @Override
    public void onKeyEvent(KeyEvent event, UmlDiagram diagram) {

    }
}
