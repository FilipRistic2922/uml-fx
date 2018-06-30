package fr97.umlfx.api.node;



import fr97.umlfx.api.UmlElement;
import fr97.umlfx.math.geometry.Point;
import fr97.umlfx.math.geometry.Rectangle;
import javafx.beans.property.DoubleProperty;

/**
 * UmlNode describes common behaviour of all different nodes that can be added to different diagrams
 */
public interface UmlNode extends UmlElement {

    String ID_PREFIX = "Node_";

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

    default Rectangle getBounds() {
        return Rectangle.of(
                translateXProperty().get(),
                translateYProperty().get(),
                widthProperty().get(),
                heightProperty().get());
    }

    DoubleProperty translateXProperty();

    default double getTranslateX(){
        return translateXProperty().get();
    }

    default void setTranslateX(double x){
        translateXProperty().set(x);
    }

    DoubleProperty translateYProperty();

    default double getTranslateY(){
        return translateYProperty().get();
    }

    default void setTranslateY(double y){
        translateYProperty().set(y);
    }

    DoubleProperty widthScaleProperty();

    default double getWidthScale(){
        return widthScaleProperty().get();
    }

    default void setWidthScale(double scale){
        widthScaleProperty().set(scale);
    }

    DoubleProperty heightScaleProperty();

    default double getHeightScale(){
        return heightScaleProperty().get();
    }

    default void setHeightScale(double scale){
        heightScaleProperty().set(scale);
    }


    UmlNode copy();

}
