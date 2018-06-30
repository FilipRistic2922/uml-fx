package fr97.umlfx.common.node;


import fr97.umlfx.api.node.UmlNode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Implementation of common behaviour for every {@link UmlNode}
 */
public abstract class AbstractNode implements UmlNode {

    protected static final int MIN_WIDTH = 140;
    protected static final int MIN_HEIGHT = 140;

    private static int instanceCounter = 0;

    private final DoubleProperty startX = new SimpleDoubleProperty(0);
    private final DoubleProperty startY = new SimpleDoubleProperty(0);
    private final DoubleProperty width = new SimpleDoubleProperty(MIN_WIDTH);
    private final DoubleProperty height = new SimpleDoubleProperty(MIN_HEIGHT);
    private final DoubleProperty translateX = new SimpleDoubleProperty(0);
    private final DoubleProperty translateY = new SimpleDoubleProperty(0);
    private final DoubleProperty widthScale = new SimpleDoubleProperty(1.0);
    private final DoubleProperty heightScale = new SimpleDoubleProperty(1.0);
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final String id;

    protected AbstractNode() {
        instanceCounter++;
        id = ID_PREFIX + instanceCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty startXProperty() {
        return startX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty startYProperty() {
        return startY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty widthProperty() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty heightProperty() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty translateXProperty() {
        return translateX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty translateYProperty() {
        return translateY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty widthScaleProperty() {
        return widthScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty heightScaleProperty() {
        return heightScale;
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
    public abstract UmlNode copy();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractNode{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", width=" + width +
                ", height=" + height +
                ", translateX=" + translateX +
                ", translateY=" + translateY +
                ", widthScale=" + widthScale +
                ", heightScale=" + heightScale +
                ", id='" + id + '\'' +
                '}';
    }
}
