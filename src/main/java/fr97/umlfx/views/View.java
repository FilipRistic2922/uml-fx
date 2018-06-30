package fr97.umlfx.views;

import javafx.scene.Node;

/**
 * <p>
 * Interface that describes behavior of an view JavaFX, it must provide root node that is instance of {@link Node},
 * that node should have all other necessary nodes to represent view set as children.
 * </p>
 * @param <N> root type
 */
public interface View<N extends Node>{

    /**
     * Gets root of view, all other elements of this view should be inside root node
     * @return root {@link Node} of this view
     */
    N getRoot();


}
