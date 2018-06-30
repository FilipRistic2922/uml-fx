package fr97.umlfx.classdiagram.node.packagenode;


import fr97.umlfx.api.node.UmlParentNode;
import fr97.umlfx.api.node.UmlNamedNode;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import fr97.umlfx.common.node.AbstractNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * PackageNode implementation of {@link UmlNode}, this node is also an {@link UmlParentNode}
 * meaning that it can be container for other nodes
 *
 * TODO treba da se napravi view za ovo cudo, prolazi testove.
 */
public class PackageNode extends AbstractNode implements UmlNamedNode, UmlParentNode {

    private StringProperty packageName = new SimpleStringProperty(getId());
    private Set<Class<? extends UmlNode>> acceptedNodes = new HashSet<>(Arrays.asList(ClassNode.class, PackageNode.class));
    private ObservableList<UmlNode> children = FXCollections.observableArrayList();

    public PackageNode(String name) {
        if (name != null)
            packageName.set(name);
    }

    @Override
    public StringProperty nameProperty() {
        return packageName;
    }

    @Override
    public PackageNode copy() {
        PackageNode copy = new PackageNode(getName());

        copy.setStart(this.getStart().copy());
        copy.setWidth(this.getWidth());
        copy.setHeight(this.getHeight());
        copy.setTranslateX(this.getTranslateX());
        copy.setTranslateY(this.getTranslateY());
        copy.setWidthScale(this.getWidthScale());
        copy.setHeightScale(this.getHeightScale());

        return copy;
    }

    @Override
    public ObservableList<UmlNode> getChildren() {
        return children;
    }

    @Override
    public Set<Class<? extends UmlNode>> acceptedChildren() {
        return acceptedNodes;
    }
}
