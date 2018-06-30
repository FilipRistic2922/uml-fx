package fr97.umlfx.api.node;

import javafx.beans.property.StringProperty;

/**
 * Describes {@link UmlNode} which has name
 */
public interface UmlNamedNode extends UmlNode {

    /**
     * Gets name of this node
     * @return String name of this node
     */
    default String getName(){
        return nameProperty().get();
    }

    /**
     * Sets given name to this node
     * @param name given name
     */
    default void setName(String name){
        nameProperty().set(name);
    }

    /**
     * Name property of this node
     */
    StringProperty nameProperty();

}
