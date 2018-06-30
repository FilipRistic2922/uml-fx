package fr97.umlfx.common.tools;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.tool.UmlTool;
import fr97.umlfx.math.geometry.Line;
import fr97.umlfx.math.geometry.Point;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionTool implements UmlTool {

    private static final StringProperty NAME = new SimpleStringProperty("Select");
    private static final StringProperty TOOLTIP = new SimpleStringProperty("Use this tool to select elements");

    private double startX = 0;
    private double startY = 0;
    private final Rectangle selectionRectangle = new Rectangle(0, 0, 0, 0);

    public SelectionTool() {

        selectionRectangle.setFill(Color.rgb(25, 120, 150, 0.3));
        selectionRectangle.setStroke(Color.BLACK);
        selectionRectangle.setStrokeWidth(1);
        selectionRectangle.getStrokeDashArray().setAll(5.0, 5.0);
    }

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
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseButton.PRIMARY)
            onMousePressed(event, diagram);
        else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && event.getButton() == MouseButton.PRIMARY)
            onMouseDragged(event, diagram);
        else if (event.getEventType() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseButton.PRIMARY)
            onMouseReleased(event, diagram);
    }


    private void onMousePressed(MouseEvent event, UmlDiagram diagram) {
        startX = event.getX();
        startY = event.getY();
        selectionRectangle.setX(event.getX());
        selectionRectangle.setY(event.getY());
        selectionRectangle.setWidth(1);
        selectionRectangle.setHeight(1);
        ((Pane) event.getSource()).getChildren().add(selectionRectangle);
    }

    private void onMouseDragged(MouseEvent event, UmlDiagram diagram) {
        selectionRectangle.setX(Math.min(startX, event.getX()));
        selectionRectangle.setY(Math.min(startY, event.getY()));
        selectionRectangle.setWidth(Math.abs(startX - event.getX()));
        //selectionRectangle.setHeight(Math.abs(startY - event.getY()));
        selectionRectangle.setHeight(Math.max(event.getY() - startY, startY - event.getY()));
    }

    private void onMouseReleased(MouseEvent event, UmlDiagram diagram) {
        diagram.getNodes().forEach(node -> node.selectedProperty().set(false));

        if (selectionRectangle.getWidth() < 10 && selectionRectangle.getHeight() < 10) {
            diagram.getNodes().stream()
                    .filter(n -> n.getBounds().contains(Point.of(event.getX(), event.getY())))
                    .findFirst()
                    .ifPresent(n -> n.selectedProperty().set(true));

        } else {
            diagram.getNodes().stream()
                    .filter(n ->
                            selectionRectangle.contains(n.getTranslateX(), n.getTranslateY())
                                    || selectionRectangle.contains(n.getTranslateX() + n.getWidth(), n.getTranslateY() + n.getHeight())
                    )
                    .forEach(n -> n.selectedProperty().set(true));
        }
        ((Pane) event.getSource()).getChildren().remove(selectionRectangle);
    }

    @Override
    public void onKeyEvent(KeyEvent event, UmlDiagram diagram) {

    }


}
