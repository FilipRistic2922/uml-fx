package fr97.umlfx.common.tools;

import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.api.tool.UmlTool;
import fr97.umlfx.common.AbstractDiagramView;
import fr97.umlfx.common.node.AbstractNodeView;
import fr97.umlfx.math.geometry.Point;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

public class MoveTool implements UmlTool {

    private static final StringProperty NAME = new SimpleStringProperty("Move");
    private static final StringProperty TOOLTIP = new SimpleStringProperty("Use this tool to move elements");

    private Point start = Point.of(0, 0);
    private Rectangle moveRect = new Rectangle(0, 0, Color.rgb(25, 130, 170, 0.5));
    private UmlNode movingNode;

    private UmlNode movingCopy;
    private AbstractNodeView movingNodeView;

    @Override
    public StringProperty nameProperty() {
        return NAME;
    }

    @Override
    public StringProperty tooltipProperty() {
        return TOOLTIP;
    }

    @Override
    public void onMouseEvent(MouseEvent event, UmlDiagram diagram) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                onMousePressed(event, diagram);
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                onMouseDragged(event, diagram);
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                onMouseReleased(event, diagram);
            }
        }
    }

    private void onMousePressed(MouseEvent event, UmlDiagram diagram) {
        diagram.getNodes().stream()
                .filter(n -> n.getBounds().contains(Point.of(event.getX(), event.getY())))
                .findFirst()
                .ifPresent(n -> {
                    start.moveTo(event.getX(), event.getY());
                    movingCopy = n.copy();
                    movingCopy.setTranslateX(event.getX());
                    movingCopy.setTranslateY(event.getY());

                    /*moveRect.setX(event.getX());
                    moveRect.setY(event.getY());
                    moveRect.setWidth(n.getWidth());
                    moveRect.setHeight(n.getHeight());*/
                    movingNode = n;
                    movingNodeView = AbstractNodeView.of(movingCopy);
                    movingNodeView.fillProperty().set(Color.rgb(25, 130, 170, 0.5));
                    ((Pane) event.getSource()).getChildren().add(movingNodeView);
                });

    }

    private void onMouseDragged(MouseEvent event, UmlDiagram diagram) {
        if (movingCopy != null && event.getX() > 0 && event.getY()>0) {
            movingCopy.setTranslateX(event.getX());
            movingCopy.setTranslateY(event.getY());
        }
    }

    private void onMouseReleased(MouseEvent event, UmlDiagram diagram) {
        if (movingNode != null) {
            movingNode.setTranslateX(movingCopy.getTranslateX());
            movingNode.setTranslateY(movingCopy.getTranslateY());
            // TODO Mozda da se napravi da resajzuje tokom pomeranja a ne na kraju
            fr97.umlfx.math.geometry.Rectangle rect = fr97.umlfx.math.geometry.Rectangle.of(0,0,0,0);
            for(UmlNode n : diagram.getNodes())
                rect = rect.add(n.getBounds());

            Pane p = (Pane)event.getSource();
            p.setPrefWidth(rect.getWidth() > AbstractDiagramView.MINIMUM_SIZE ? rect.getWidth() : AbstractDiagramView.MINIMUM_SIZE);
            p.setPrefHeight(rect.getHeight() > AbstractDiagramView.MINIMUM_SIZE ? rect.getHeight() : AbstractDiagramView.MINIMUM_SIZE);
        }
        ((Pane) event.getSource()).getChildren().remove(movingNodeView);
        movingNodeView = null;
        movingNode = null;
        movingCopy = null;
    }


    @Override
    public void onKeyEvent(KeyEvent event, UmlDiagram diagram) {

    }
}
