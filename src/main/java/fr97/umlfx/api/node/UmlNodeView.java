package fr97.umlfx.api.node;

import fr97.umlfx.math.geometry.Point;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;

public interface UmlNodeView {

    String ID_PREFIX = "NodeView_";

    UmlNode getNode();

    ObjectProperty<Paint> fillProperty();

    DoubleProperty startXProperty();

    default double getStartX() {
        return startXProperty().get();
    }
    default Point getStart(){
        return Point.of(getStartX(), getStartY());
    }

    default void setStartX(double x) {
        startXProperty().set(x);
    }

    default void setStart(Point start){
        startXProperty().set(start.getX());
        startYProperty().set(start.getY());
    }

    DoubleProperty startYProperty();

    default double getStartY() {
        return startYProperty().get();
    }

    default void setStartY(double y) {
        startYProperty().set(y);
    }

    DoubleProperty widthProperty();

    default double getWidth() {
        return widthProperty().get();
    }

    default void setWidth(double width) {
        widthProperty().set(width);
    }

    DoubleProperty heightProperty();

    default double getHeight() {
        return heightProperty().get();
    }

    default void setHeight(double height) {
        heightProperty().set(height);
    }

    void setSelected(boolean selected);

    Bounds getBounds();

    boolean contains(Point point);

}
