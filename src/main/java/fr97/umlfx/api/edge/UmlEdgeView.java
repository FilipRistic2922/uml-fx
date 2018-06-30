package fr97.umlfx.api.edge;

import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.math.geometry.Point;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public interface UmlEdgeView {

    UmlEdge getEdge();

    Line getTailLine();
    Line getMiddleLine();
    Line getHeadLine();

    DoubleProperty startXProperty();
    default double getStartX() {
        return startXProperty().get();
    }
    default void setStartX(double x) {
        startXProperty().set(x);
    }

    DoubleProperty startYProperty();
    default double getStartY() {
        return startYProperty().get();
    }
    default void setStartY(double y) {
        startYProperty().set(y);
    }

    default Point getStart(){
        return Point.of(getStartX(), getStartY());
    }

    DoubleProperty endXProperty();
    default double getEndX() {
        return endXProperty().get();
    }
    default void setEndX(double x) {
        endXProperty().set(x);
    }

    DoubleProperty endYProperty();
    default double getEndY() {
        return endYProperty().get();
    }
    default void setEndY(double y) {
        endYProperty().set(y);
    }

    default Point getEnd(){
        return Point.of(getEndX(), getEndY());
    }

    Text getTailMultiplicity();
    Text getHeadMultiplicity();
    Text getMiddleLabel();

    ObjectProperty<Paint> strokeProperty();
    DoubleProperty strokeWidthProperty();


    BooleanProperty selectedProperty();
    default boolean isSelected(){
        return selectedProperty().get();
    }
    void setSelected(boolean selected);

}
