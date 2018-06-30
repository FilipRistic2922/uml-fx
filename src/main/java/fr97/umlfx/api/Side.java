package fr97.umlfx.api;

import java.io.Serializable;

/**
 * Enum representing sides, used for finding on which side should
 * {@link fr97.umlfx.api.edge.UmlEdge} connect to {@link fr97.umlfx.api.node.UmlNode}
 */
public enum  Side {
    NONE, NORTH, SOUTH, EAST, WEST
}
