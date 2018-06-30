package fr97.umlfx.common;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.node.UmlNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class AbstractDiagram implements UmlDiagram {

    private static int instanceCounter = 0;

    private transient final StringProperty name = new SimpleStringProperty();

    private transient final ObservableList<UmlNode> nodes = FXCollections.observableArrayList();

    private transient final ObservableList<UmlEdge> edges = FXCollections.observableArrayList();

    private final String id;

    protected AbstractDiagram(String name) {
        instanceCounter++;
        this.id = ID_PREFIX + instanceCounter;
        this.name.set(name);
    }

    protected AbstractDiagram() {
        instanceCounter++;
        this.id = ID_PREFIX + instanceCounter;
        this.name.set(id);
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public ObservableList<UmlNode> getNodes() {
        return nodes;
    }

    @Override
    public ObservableList<UmlEdge> getEdges() {
        return edges;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractDiagram{" +
                "name=" + name.get() +
                ", nodes=" + nodes +
                ", edges=" + edges +
                ", id='" + id + '\'' +
                '}';
    }

}
