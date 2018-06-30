package fr97.umlfx.common.edge;


import fr97.umlfx.api.Multiplicity;
import fr97.umlfx.api.edge.Direction;
import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.utils.ArgumentChecker;
import javafx.beans.property.*;

/**
 * Implementation of common behaviour for every {@link UmlEdge}
 */
public abstract class AbstractEdge implements UmlEdge {

    private static int instanceCounter = 0;

    private final ObjectProperty<UmlNode> head = new SimpleObjectProperty<>();
    private final ObjectProperty<UmlNode> tail = new SimpleObjectProperty<>();
    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>(Direction.NONE);
    private final String id;
    private final StringProperty label = new SimpleStringProperty("");
    private final Multiplicity headMult = new Multiplicity();
    private final Multiplicity tailMult = new Multiplicity();

    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    protected AbstractEdge(UmlNode tail, UmlNode head) throws IllegalArgumentException {
        instanceCounter++;
        id = ID_PREFIX + instanceCounter;
        this.tail.set(tail);
        this.head.set(head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<UmlNode> tailProperty() {
        return tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<UmlNode> headProperty() {
        return head;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Direction> directionProperty() {
        return direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Multiplicity getTailMultiplicity() {
        return tailMult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Multiplicity getHeadMultiplicity() {
        return headMult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringProperty labelProperty() {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractEdge{" +
                "head=" + head +
                ", tail=" + tail +
                ", direction=" + direction +
                ", id='" + id + '\'' +
                '}';
    }
}
