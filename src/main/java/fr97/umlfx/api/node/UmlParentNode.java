package fr97.umlfx.api.node;

import fr97.umlfx.math.geometry.Point;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * Represents node that can contain children, for example you can put different nodes in package node
 *
 * TODO trenutno nima nijedna implementacija, testovi pokazuju da radi, treba da vidim kako da implementiram gui
 */
public interface UmlParentNode extends UmlNode {

    ObservableList<UmlNode> getChildren();

    default void setChildren(Collection<UmlNode> children){
        getChildren().clear();
        getChildren().addAll(children);
    }

    default boolean addChild(UmlNode node){
        if(node == this)
            return false;
        if(!acceptedChildren().contains(node.getClass()))
            return false;
        return getChildren().add(node);
    }

    default boolean removeChild(UmlNode node){
        return getChildren().remove(node);
    }

    default Optional<UmlNode> findNode(Point point){
        // If parent doesn't contain point children won't contain it too
        if(!getBounds().contains(point))
            return Optional.empty();

        // If it doesn't have children it return empty
        if(getChildren().isEmpty())
            return Optional.empty();

        // find first child containing given point
        List<UmlNode> filteredNodes = getChildren().filtered(node -> node.getBounds().contains(point));
        UmlNode target = filteredNodes.isEmpty() ? null : filteredNodes.get(0);

        if(target == null)
            return Optional.empty();

        if(target instanceof UmlParentNode)
            return Optional.of(((UmlParentNode) target).findNode(point).orElse(target));

        return Optional.of(target);
    }

    /**
     * Returns set of accepted children types
     * @return Set of accepted children types
     */
    Set<Class<? extends UmlNode>> acceptedChildren();


}
