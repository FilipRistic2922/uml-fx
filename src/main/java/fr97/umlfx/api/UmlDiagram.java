package fr97.umlfx.api;


import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.node.UmlParentNode;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.api.tool.UmlTool;
import fr97.umlfx.math.geometry.Point;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Describes common behaviour of every uml diagram
 */
public interface UmlDiagram {

    String ID_PREFIX = "Diagram_";

    StringProperty nameProperty();

    default String getName() {
        return nameProperty().get();
    }

    default void setName(String name) {
        nameProperty().set(name);
    }

    ObservableList<UmlNode> getNodes();

    default void setNodes(Collection<UmlNode> nodes) {
        getNodes().clear();
        getNodes().addAll(nodes);
    }

    default boolean addAllNodes(Collection<UmlNode> nodes) {
        return getNodes().addAll(nodes);
    }

    default boolean addNode(UmlNode node) {

        for (UmlNode n : getNodes()) {
            if(n instanceof UmlParentNode){
                if(n.getBounds().contains(node.getBounds())
                        && ((UmlParentNode) n).acceptedChildren().contains(node.getClass())){
                    ((UmlParentNode) n).addChild(node);
                }
            }
        }
        return getNodes().add(node);
    }

    default boolean removeNode(UmlNode node) {
        return getNodes().remove(node);
    }

    default boolean containsNode(UmlNode node) {
        return getNodes().contains(node);
    }

    default Optional<UmlNode> findNode(Point point) {
        List<UmlNode> nodes = getNodes().filtered(node -> node.getBounds().contains(point));
        return nodes.isEmpty() ? Optional.empty() : Optional.ofNullable(nodes.get(0));
    }

    ObservableList<UmlEdge> getEdges();

    default void setEdges(Collection<UmlEdge> edges) {
        getEdges().clear();
        getEdges().addAll(edges);
    }

    default boolean addAllEdges(Collection<UmlEdge> edges) {
        return getEdges().addAll(edges);
    }

    default boolean addEdge(UmlEdge edge) {
        return getEdges().add(edge);
    }

    default boolean removeEdge(UmlEdge edge) {
        return getEdges().remove(edge);
    }


    default boolean containsEdge(UmlEdge edge) {
        return getEdges().contains(edge);
    }

    default ObservableList<UmlElement> getElements() {
        ObservableList<UmlElement> nodes = FXCollections.observableArrayList();

        nodes.addAll(getNodes());
        nodes.addAll(getEdges());

        return nodes;
    }

    List<UmlTool> getSupportedTools();


    String getId();

}
