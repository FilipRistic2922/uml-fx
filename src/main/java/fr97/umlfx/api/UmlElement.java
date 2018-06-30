package fr97.umlfx.api;


import javafx.beans.property.BooleanProperty;

import java.io.Serializable;

/**
 * Common type for both nodes and edges, describes their common behaviour
 */
public interface UmlElement {


    /**
     *
     * @return {@link BooleanProperty} representing whether this is element is selected
     */
    BooleanProperty selectedProperty();

    /**
     * Gets string id of this element
     * @return String id
     */
    String getId();


}
