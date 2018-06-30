package fr97.umlfx.common.node;


import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.api.node.UmlNodeView;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import fr97.umlfx.classdiagram.node.classnode.ClassNodeView;
import fr97.umlfx.classdiagram.node.interfacenode.InterfaceNode;
import fr97.umlfx.classdiagram.node.interfacenode.InterfaceNodeView;
import fr97.umlfx.classdiagram.node.packagenode.PackageNode;
import fr97.umlfx.common.node.comment.CommentNode;
import fr97.umlfx.common.node.comment.CommentNodeView;
import fr97.umlfx.math.geometry.Point;

import fr97.umlfx.utils.ArgumentChecker;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public abstract class AbstractNodeView extends Group implements UmlNodeView {

    private static int objectCount = 0;
    private final ObjectProperty<Paint> fill = new SimpleObjectProperty<>(Color.LIGHTSKYBLUE);
    private final DoubleProperty startX = new SimpleDoubleProperty(0);
    private final DoubleProperty startY = new SimpleDoubleProperty(0);
    private final DoubleProperty width = new SimpleDoubleProperty();
    private final DoubleProperty height = new SimpleDoubleProperty();

    private final UmlNode node;

    /**
     * Factory method that provides correct implementation of AbstactNodeView
     * based on given node
     * @param node given node
     * @throws IllegalArgumentException if node is null or there isn't view for such node
     * @return AbstractNodeView that corresponds to given node
     */
    public static AbstractNodeView of(UmlNode node) throws IllegalArgumentException {
        ArgumentChecker.notNull(node, "node can't be null");
        if (node instanceof ClassNode) {
            return new ClassNodeView((ClassNode) node);
        } else if (node instanceof InterfaceNode) {
            return new InterfaceNodeView((InterfaceNode) node);
        } else if (node instanceof PackageNode) {

        } else if(node instanceof CommentNode) {
            return new CommentNodeView((CommentNode)node);
        }

        throw new IllegalArgumentException("this uml node doesn't have supporting view");
    }

    protected AbstractNodeView(final UmlNode node) {
        ArgumentChecker.notNull(node, "node can't be null");
        objectCount++;
        setId(ID_PREFIX + objectCount);

        this.node = node;

        layoutXProperty().bindBidirectional(startX);
        layoutYProperty().bindBidirectional(startY);

        startX.bindBidirectional(node.startXProperty());
        startY.bindBidirectional(node.startYProperty());

        width.bind(node.widthProperty());
        height.bind(node.heightProperty());

        translateXProperty().bindBidirectional(node.translateXProperty());
        translateYProperty().bindBidirectional(node.translateYProperty());

        startX.set(node.getStartX());
        startY.set(node.getStartY());

        node.selectedProperty().addListener((obs, oldValue, newValue)->{
            if(oldValue != newValue)
                setSelected(newValue);
        });
    }

    protected void createResizeLines(Region region){

        Line shortHandleLine = new Line();
        Line longHandleLine = new Line();

        shortHandleLine.startXProperty().bind(region.widthProperty().subtract(7));
        shortHandleLine.startYProperty().bind(region.heightProperty().subtract(3));
        shortHandleLine.endXProperty().bind(region.widthProperty().subtract(3));
        shortHandleLine.endYProperty().bind(region.heightProperty().subtract(7));
        longHandleLine.startXProperty().bind(region.widthProperty().subtract(15));
        longHandleLine.startYProperty().bind(region.heightProperty().subtract(3));
        longHandleLine.endXProperty().bind(region.widthProperty().subtract(3));
        longHandleLine.endYProperty().bind(region.heightProperty().subtract(15));

        this.getChildren().addAll(shortHandleLine, longHandleLine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UmlNode getNode(){
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Paint> fillProperty() {
        return fill;
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
    public boolean contains(Point point) {
        int x = point.getX();
        int y = point.getY();
        return (x >= getTranslateX() && x <= getTranslateX() + width.get() && y >= getTranslateY() && y <= getTranslateY() + height.get());
    }

}
