package fr97.umlfx.common.edge;

import fr97.umlfx.api.Side;
import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.edge.UmlEdgeView;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.app.Theme;
import fr97.umlfx.classdiagram.edge.aggregation.AggregationEdge;
import fr97.umlfx.classdiagram.edge.aggregation.AggregationEdgeView;
import fr97.umlfx.classdiagram.edge.association.AssociationEdge;
import fr97.umlfx.classdiagram.edge.association.AssociationEdgeView;
import fr97.umlfx.classdiagram.edge.composition.CompositionEdge;
import fr97.umlfx.classdiagram.edge.composition.CompositionEdgeView;
import fr97.umlfx.classdiagram.edge.dependancy.DependancyEdge;
import fr97.umlfx.classdiagram.edge.dependancy.DependancyEdgeView;
import fr97.umlfx.classdiagram.edge.inheritance.InheritanceEdge;
import fr97.umlfx.classdiagram.edge.inheritance.InheritanceEdgeView;
import fr97.umlfx.classdiagram.edge.realization.RealizationEdge;
import fr97.umlfx.classdiagram.edge.realization.RealizationEdgeView;
import fr97.umlfx.common.edge.comment.CommentEdge;
import fr97.umlfx.common.edge.comment.CommentEdgeView;
import fr97.umlfx.utils.ArgumentChecker;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEdgeView extends Group implements UmlEdgeView {

    private static final String ID_PREFIX = "EdgeView_";
    private static final double OFFSET = 20;
    private static int instanceCounter = 0;

    private final UmlEdge edge;

    private final Line tailLine = new Line();
    private final Line middleLine = new Line();
    private final Line headLine = new Line();

    protected final List<Line> shapeLines = new ArrayList<>();

    private final Text tailMult = new Text();
    private final Text headMult = new Text();
    private final Text middleLabel = new Text();

    private final DoubleProperty startX = new SimpleDoubleProperty(0);
    private final DoubleProperty endX = new SimpleDoubleProperty(0);
    private final DoubleProperty startY = new SimpleDoubleProperty(0);
    private final DoubleProperty endY = new SimpleDoubleProperty(0);

    private final ObjectProperty<Paint> stroke = new SimpleObjectProperty<>(Color.BLACK);
    private final DoubleProperty strokeWidth = new SimpleDoubleProperty(1);

    private BooleanProperty selected = new SimpleBooleanProperty(false);

    protected Side side = Side.NONE;

    /**
     * Factory method that provides correct implementation of AbstactEdgeView
     * based on given edge
     * @param edge given edge
     * @throws IllegalArgumentException if edge is null or there isn't view for such edge
     * @return AbstractEdgeView that corresponds to given edge
     */
    public static AbstractEdgeView of(UmlEdge edge) throws IllegalArgumentException{
        ArgumentChecker.notNull(edge, "edge can't be null");
        if(edge instanceof CommentEdge){
            return new CommentEdgeView((CommentEdge)edge);
        } else if(edge instanceof DependancyEdge){
            return new DependancyEdgeView((DependancyEdge)edge);
        } else if(edge instanceof AssociationEdge){
            return new AssociationEdgeView((AssociationEdge)edge);
        } else if(edge instanceof AggregationEdge){
            return new AggregationEdgeView((AggregationEdge)edge);
        } else if(edge instanceof CompositionEdge){
            return new CompositionEdgeView((CompositionEdge)edge);
        } else if(edge instanceof InheritanceEdge){
            return new InheritanceEdgeView((InheritanceEdge)edge);
        } else if(edge instanceof RealizationEdge){
            return new RealizationEdgeView((RealizationEdge)edge);
        }
        throw new IllegalStateException("Couldn't builder view for given edge " + edge);
    }

    /**
     * Creates visual representation of given {@link UmlEdge}
     * @param edge given edge
     * @throws IllegalArgumentException if given edge is null
     */
    protected AbstractEdgeView(UmlEdge edge) throws IllegalArgumentException{
        ArgumentChecker.notNull(edge, "edge can't be null");
        this.edge = edge;
        instanceCounter++;
        setId(ID_PREFIX + instanceCounter);

        /*tailMult.textProperty().bind(edge.getTailMultiplicity().umlNotationBinding());
        headMult.textProperty().bind(edge.getHeadMultiplicity().umlNotationBinding());
        middleLabel.textProperty().bind(edge.labelProperty());*/
        headLine.setStrokeWidth(2);
        middleLine.setStrokeWidth(2);
        tailLine.setStrokeWidth(2);

        createBindings();

        getChildren().addAll(tailLine, middleLine, headLine, tailMult, middleLabel, headMult);
    }

    /**
     * Updates line and text positions
     */
    protected final void update(){
        getChildren().clear();
        shapeLines.clear();
        getChildren().addAll(tailLine, middleLine, headLine, tailMult, middleLabel, headMult);
        onUpdate();
        updateText();
    }

    /**
     * Gets called by parent when there is something to update
     * TODO Nepotrebno za sada ali mislim da ce trebati ako se doda povezivanje koje nije samo po sredini
     */
    protected abstract void onUpdate();


    /**
     * Extending classes should override this method to change displayed shape at the end of the lines
     * @param startX starting x coordinate of shape
     * @param startY starting y coordinate of shape
     * @param endX ending x coordinate of shape
     * @param endY ending y coordinate of shape
     * @return {@link Group} which contains all necessary elements to draw shape
     */
    protected abstract Group createShape(double startX, double startY, double endX, double endY);

    /**
     * Binds all necessary properties
     */
    private void createBindings(){

        UmlNode tail = edge.getTail();
        UmlNode head = edge.getHead();

        //System.out.println("Bound to: \n   " + tail +"\n   " + head);

        tail.widthProperty().addListener(this::calculatePositions);
        tail.heightProperty().addListener(this::calculatePositions);
        tail.translateXProperty().addListener(this::calculatePositions);
        tail.translateYProperty().addListener(this::calculatePositions);

        head.widthProperty().addListener(this::calculatePositions);
        head.heightProperty().addListener(this::calculatePositions);
        head.translateXProperty().addListener(this::calculatePositions);
        head.translateYProperty().addListener(this::calculatePositions);

        edge.selectedProperty().addListener((obs, oldValue, newValue)->{
            if(oldValue != newValue)
                setSelected(newValue);
        });

        edge.getTailMultiplicity().umlNotationBinding().addListener(((obs, oldValue, newValue) -> {
            if(oldValue != null && !oldValue.equals(newValue)){
                tailMult.setText(newValue);
                updateText();
            }
        }));
        edge.getHeadMultiplicity().umlNotationBinding().addListener(((obs, oldValue, newValue) -> {
            if(oldValue != null && !oldValue.equals(newValue)){
                headMult.setText(newValue);
                updateText();
            }
        }));

        edge.labelProperty().addListener(((obs, oldValue, newValue) -> {
            if(oldValue != null && !oldValue.equals(newValue)){
                middleLabel.setText(newValue);
                updateText();
            }
        }));

        headLine.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                edge.selectedProperty().set(!selectedProperty().get());
        });
        middleLine.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                edge.selectedProperty().set(!selectedProperty().get());
        });
        tailLine.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                edge.selectedProperty().set(!selectedProperty().get());
        });

    }

    private void updateShape(){
        getChildren().removeIf(node -> "shape".equals(node.getUserData()));
        switch (edge.getDirection()) {
            case NONE:
                break;
            case TAIL_TO_HEAD: // Adds shape to head side
                getChildren().add(createShape(
                        getHeadLine().getEndX(), getHeadLine().getEndY(),
                        getHeadLine().getStartX(), getHeadLine().getStartY()));
                break;
            case HEAD_TO_TAIL: // Adds shape to tail side
                getChildren().add(createShape(
                        getTailLine().getStartX(), getTailLine().getStartY(),
                        getTailLine().getEndX(), getTailLine().getEndY()));
                break;
            case BIDIRECTIONAL: // Adds shape to both sides
                getChildren().addAll(
                        createShape(getHeadLine().getEndX(), getHeadLine().getEndY(),
                                getHeadLine().getStartX(), getHeadLine().getStartY()),
                        createShape(getTailLine().getStartX(), getTailLine().getStartY(),
                                getTailLine().getEndX(), getTailLine().getEndY()));
                break;
        }
    }

    /**
     * Updates positions of text
     */
    protected void updateText(){
        switch (side) {
            case EAST:
                updateTextEast();
                break;
            case WEST:
                updateTextWest();
                break;
            case NORTH:
                updateTextNorth();
                break;
            case SOUTH:
                updateTextSouth();
                break;
        }
        tailMult.toFront();
        headMult.toFront();
        middleLabel.toFront();
    }

    private void updateTextSouth() {
        tailMult.setX(tailLine.getStartX() + OFFSET);
        tailMult.setY(tailLine.getStartY() + OFFSET);
        headMult.setX(headLine.getEndX() + OFFSET);
        headMult.setY(headLine.getEndY() - OFFSET);
        middleLabel.setX((edge.getTail().getStartX()  + edge.getHead().getStartX())/2 + OFFSET);
        middleLabel.setY(middleLine.getEndY() + OFFSET);
    }

    private void updateTextNorth() {
        tailMult.setX(tailLine.getStartX() + OFFSET);
        tailMult.setY(tailLine.getStartY() - OFFSET);
        headMult.setX(headLine.getEndX() + OFFSET);
        headMult.setY(headLine.getEndY() + OFFSET);
        middleLabel.setX((edge.getTail().getStartX() + edge.getHead().getStartX())/2 + OFFSET);
        middleLabel.setY(middleLine.getEndY() + OFFSET);
    }

    private void updateTextWest() {
        tailMult.setX(tailLine.getStartX() - OFFSET - tailMult.getText().length() -5);
        tailMult.setY(tailLine.getStartY() + OFFSET);
        headMult.setX(headLine.getEndX() + OFFSET);
        headMult.setY(headLine.getEndY() + OFFSET);
        middleLabel.setX((edge.getTail().getStartX() + edge.getHead().getStartX())/2 + OFFSET);
        middleLabel.setY(headLine.getEndY() + OFFSET);
    }

    private void updateTextEast() {
        tailMult.setX(tailLine.getStartX() + OFFSET);
        tailMult.setY(tailLine.getStartY() + OFFSET);
        headMult.setX(headLine.getEndX() - OFFSET - headMult.getText().length() -5);
        headMult.setY(headLine.getEndY() + OFFSET);
        middleLabel.setX((edge.getTail().getStartX() + edge.getHead().getStartX())/2);
        middleLabel.setY(headLine.getEndY() + OFFSET);
    }

    /**
     * Calculates positions of lines based on positions of 2 nodes that this edge connects,
     * currently it only puts line on middle of closest side.
     * @param obs not used, can be null
     * @param oldValue not used, can be null
     * @param newValue not used, can be null
     */
    public void calculatePositions(Observable obs, Number oldValue, Number newValue){

        //TODO podeliti na vise metoda

        UmlNode tailNode = edge.getTail();
        UmlNode headNode = edge.getHead();

        //If headNode is east of tailNode:
        if (tailNode.getTranslateX() + tailNode.getWidth() <= headNode.getTranslateX()) { //Straight line if height difference is small
            if(Math.abs(tailNode.getTranslateY() + (tailNode.getHeight()/2) - (headNode.getTranslateY() + (headNode.getHeight()/2))) < 0){
                tailLine.setStartX(tailNode.getTranslateX() + tailNode.getWidth());
                tailLine.setStartY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));
                tailLine.setEndX(headNode.getTranslateX());
                tailLine.setEndY(headNode.getTranslateY() + (headNode.getHeight() / 2));

                middleLine.setStartX(0);
                middleLine.setStartY(0);
                middleLine.setEndX(0);
                middleLine.setEndY(0);

                headLine.setStartX(0);
                headLine.setStartY(0);
                headLine.setEndX(0);
                headLine.setEndY(0);
            } else {
                tailLine.setStartX(tailNode.getTranslateX() + tailNode.getWidth());
                tailLine.setStartY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));
                tailLine.setEndX(tailNode.getTranslateX() + tailNode.getWidth() + ((headNode.getTranslateX() - (tailNode.getTranslateX() + tailNode.getWidth()))/2));
                tailLine.setEndY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));

                middleLine.setStartX(tailNode.getTranslateX() + tailNode.getWidth() + ((headNode.getTranslateX() - (tailNode.getTranslateX() + tailNode.getWidth()))/2));
                middleLine.setStartY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));
                middleLine.setEndX(tailNode.getTranslateX() + tailNode.getWidth() + ((headNode.getTranslateX() - (tailNode.getTranslateX() + tailNode.getWidth()))/2));
                middleLine.setEndY(headNode.getTranslateY() + (headNode.getHeight() / 2));

                headLine.setStartX(tailNode.getTranslateX() + tailNode.getWidth() + ((headNode.getTranslateX() - (tailNode.getTranslateX() + tailNode.getWidth()))/2));
                headLine.setStartY(headNode.getTranslateY() + (headNode.getHeight() / 2));
                headLine.setEndX(headNode.getTranslateX());
                headLine.setEndY(headNode.getTranslateY() + (headNode.getHeight() / 2));
            }

            side = Side.EAST;
        }
        //If headNode is west of tailNode:
        else if (tailNode.getTranslateX() > headNode.getTranslateX() + headNode.getWidth()) {
            if(Math.abs(tailNode.getTranslateY() + (tailNode.getHeight()/2) - (headNode.getTranslateY() + (headNode.getHeight()/2))) < 0){
                tailLine.setStartX(tailNode.getTranslateX());
                tailLine.setStartY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));
                tailLine.setEndX(headNode.getTranslateX() + headNode.getWidth());
                tailLine.setEndY(headNode.getTranslateY() + (headNode.getHeight() / 2));

                middleLine.setStartX(0);
                middleLine.setStartY(0);
                middleLine.setEndX(0);
                middleLine.setEndY(0);

                headLine.setStartX(0);
                headLine.setStartY(0);
                headLine.setEndX(0);
                headLine.setEndY(0);
            } else {
                tailLine.setStartX(tailNode.getTranslateX());
                tailLine.setStartY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));
                tailLine.setEndX(headNode.getTranslateX() + headNode.getWidth()  + ((tailNode.getTranslateX() - (headNode.getTranslateX() + headNode.getWidth()))/2));
                tailLine.setEndY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));

                middleLine.setStartX(headNode.getTranslateX() + headNode.getWidth()  + ((tailNode.getTranslateX() - (headNode.getTranslateX() + headNode.getWidth()))/2));
                middleLine.setStartY(tailNode.getTranslateY() + (tailNode.getHeight() / 2));
                middleLine.setEndX(headNode.getTranslateX() + headNode.getWidth()  + ((tailNode.getTranslateX() - (headNode.getTranslateX() + headNode.getWidth()))/2));
                middleLine.setEndY(headNode.getTranslateY() + (headNode.getHeight() / 2));

                headLine.setStartX(headNode.getTranslateX() + headNode.getWidth()  + ((tailNode.getTranslateX() - (headNode.getTranslateX() + headNode.getWidth()))/2));
                headLine.setStartY(headNode.getTranslateY() + (headNode.getHeight() / 2));
                headLine.setEndX(headNode.getTranslateX() + headNode.getWidth());
                headLine.setEndY(headNode.getTranslateY() + (headNode.getHeight() / 2));
            }

            side = Side.WEST;
        }
        // If headNode is south of tailNode:
        else if (tailNode.getTranslateY() + tailNode.getHeight() < headNode.getTranslateY()){
            if(Math.abs(tailNode.getTranslateX() + (tailNode.getWidth()/2) - (headNode.getTranslateX() + (headNode.getWidth()/2))) < 0){
                tailLine.setStartX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                tailLine.setStartY(tailNode.getTranslateY() + tailNode.getHeight());
                tailLine.setEndX(headNode.getTranslateX() + (headNode.getWidth()/2));
                tailLine.setEndY(headNode.getTranslateY());

                middleLine.setStartX(0);
                middleLine.setStartY(0);
                middleLine.setEndX(0);
                middleLine.setEndY(0);

                headLine.setStartX(0);
                headLine.setStartY(0);
                headLine.setEndX(0);
                headLine.setEndY(0);
            } else {
                tailLine.setStartX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                tailLine.setStartY(tailNode.getTranslateY() + tailNode.getHeight());
                tailLine.setEndX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                tailLine.setEndY(tailNode.getTranslateY() + tailNode.getHeight() + ((headNode.getTranslateY() - (tailNode.getTranslateY() + tailNode.getHeight()))/2));

                middleLine.setStartX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                middleLine.setStartY(tailNode.getTranslateY() + tailNode.getHeight() + ((headNode.getTranslateY() - (tailNode.getTranslateY() + tailNode.getHeight()))/2));
                middleLine.setEndX(headNode.getTranslateX() + (headNode.getWidth()/2));
                middleLine.setEndY(tailNode.getTranslateY() + tailNode.getHeight() + ((headNode.getTranslateY() - (tailNode.getTranslateY() + tailNode.getHeight()))/2));

                headLine.setStartX(headNode.getTranslateX() + (headNode.getWidth()/2));
                headLine.setStartY(tailNode.getTranslateY() + tailNode.getHeight() + ((headNode.getTranslateY() - (tailNode.getTranslateY() + tailNode.getHeight()))/2));
                headLine.setEndX(headNode.getTranslateX() + (headNode.getWidth()/2));
                headLine.setEndY(headNode.getTranslateY());
            }

            side = Side.SOUTH;
        }
        //If headNode is north of tailNode:
        else if (tailNode.getTranslateY() >= headNode.getTranslateY() + headNode.getHeight()) {
            if(Math.abs(tailNode.getTranslateX() + (tailNode.getWidth()/2) - (headNode.getTranslateX() + (headNode.getWidth()/2))) < 0){
                tailLine.setStartX(tailNode.getTranslateX() + (tailNode.getWidth() / 2));
                tailLine.setStartY(tailNode.getTranslateY());
                tailLine.setEndX(headNode.getTranslateX() + (headNode.getWidth()/2));
                tailLine.setEndY(headNode.getTranslateY() + headNode.getHeight());

                middleLine.setStartX(0);
                middleLine.setStartY(0);
                middleLine.setEndX(0);
                middleLine.setEndY(0);

                headLine.setStartX(0);
                headLine.setStartY(0);
                headLine.setEndX(0);
                headLine.setEndY(0);
            } else {
                tailLine.setStartX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                tailLine.setStartY(tailNode.getTranslateY());
                tailLine.setEndX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                tailLine.setEndY(headNode.getTranslateY() + headNode.getHeight() + ((tailNode.getTranslateY() - (headNode.getTranslateY() + headNode.getHeight()))/2));

                middleLine.setStartX(tailNode.getTranslateX() + (tailNode.getWidth() /2));
                middleLine.setStartY(headNode.getTranslateY() + headNode.getHeight() + ((tailNode.getTranslateY() - (headNode.getTranslateY() + headNode.getHeight()))/2));
                middleLine.setEndX(headNode.getTranslateX() + (headNode.getWidth()/2));
                middleLine.setEndY(headNode.getTranslateY() + headNode.getHeight() + ((tailNode.getTranslateY() - (headNode.getTranslateY() + headNode.getHeight()))/2));

                headLine.setStartX(headNode.getTranslateX() + (headNode.getWidth()/2));
                headLine.setStartY(headNode.getTranslateY() + headNode.getHeight() + ((tailNode.getTranslateY() - (headNode.getTranslateY() + headNode.getHeight()))/2));
                headLine.setEndX(headNode.getTranslateX() + (headNode.getWidth()/2));
                headLine.setEndY(headNode.getTranslateY() + headNode.getHeight());
            }

            side = Side.NORTH;
        }
        updateShape();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UmlEdge getEdge() {
        return edge;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Line getTailLine() {
        return tailLine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Line getMiddleLine() {
        return middleLine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Line getHeadLine() {
        return headLine;
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
    public DoubleProperty endXProperty() {
        return endX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty endYProperty() {
        return endY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Text getTailMultiplicity() {
        return tailMult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Text getHeadMultiplicity() {
        return headMult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Text getMiddleLabel() {
        return middleLabel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Paint> strokeProperty() {
        return stroke;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleProperty strokeWidthProperty() {
        return strokeWidth;
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
    public void setSelected(boolean selected) {
        this.selected.set(selected);
        if (selected){
            headLine.setStroke(Color.RED);
            middleLine.setStroke(Color.RED);
            tailLine.setStroke(Color.RED);
            shapeLines.forEach(line -> line.setStroke(Color.RED));
        } else {
            headLine.setStroke(Color.BLACK);
            middleLine.setStroke(Color.BLACK);
            tailLine.setStroke(Color.BLACK);
            shapeLines.forEach(line -> line.setStroke(Color.BLACK));
        }
    }

}
