package fr97.umlfx.api.edge;


import fr97.umlfx.api.Multiplicity;
import fr97.umlfx.api.UmlElement;
import fr97.umlfx.api.node.UmlNode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 * UmlEdge describes common behaviour of all different edges that can be added to different diagrams
 */
public interface UmlEdge extends UmlElement {

    String ID_PREFIX = "Edge_";

    /**
     * Node on the start of this edge
     */
    ObjectProperty<UmlNode> tailProperty();

    default UmlNode getTail(){
       return tailProperty().get();
    }

    default void setTail(UmlNode node){
        tailProperty().set(node);
    }

    /**
     * Node on the end of this edge
     */
    ObjectProperty<UmlNode> headProperty();

    default UmlNode getHead(){
        return headProperty().get();
    }

    default void setHead(UmlNode node){
        headProperty().set(node);
    }

    /**
     *
     * Direction of this edge
     */
    ObjectProperty<Direction> directionProperty();

    /**
     * Gets direction of this edge
     * @return Direction
     */
    default Direction getDirection(){
        return directionProperty().get();
    }

    default void setDirection(Direction direction){
        directionProperty().set(direction);
    }

    Multiplicity getTailMultiplicity();

    Multiplicity getHeadMultiplicity();

    StringProperty labelProperty();

    default String getLabel(){
        return labelProperty().get();
    }
    default void setLabel(String headMultiplicity){
        labelProperty().set(headMultiplicity);
    }

    BooleanProperty selectedProperty();

    UmlEdge copy();
}
