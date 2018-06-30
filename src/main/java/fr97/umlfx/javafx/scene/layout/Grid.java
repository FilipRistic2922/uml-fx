package fr97.umlfx.javafx.scene.layout;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Grid to show behind diagram
 *
 * TODO treba da se ubaci iza dijagrama
 */
public class Grid extends Region {

    private static final double PIXEL_OFFSET = 0.5;
    private Canvas canvas ;
    private ObjectProperty<Paint> paint;
    private final int colCount;
    private final int rowCount;

    public Grid(int colCount, int rowCount) {
        this(colCount, rowCount, Color.GRAY);
    }

    public Grid(int colCount, int rowCount, Paint paint) {
        this.colCount = colCount;
        this.rowCount = rowCount;
        this.paint = new SimpleObjectProperty<>(paint);

        this.paint.addListener(c->layoutChildren());

        canvas = new Canvas();
        getChildren().add(canvas);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void layoutChildren() {
        double w = getWidth() - getPadding().getLeft() - getPadding().getRight() ;
        double h = getHeight() - getPadding().getTop() - getPadding().getBottom() ;

        canvas.setWidth(w+1);
        canvas.setHeight(h+1);

        canvas.setLayoutX(getPadding().getLeft());
        canvas.setLayoutY(getPadding().getRight());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, w, h);
        gc.setStroke(paint.get());
        for (int i = 0; i <= colCount; i++) {

            double x = w*i/ colCount + PIXEL_OFFSET;

            gc.strokeLine(x, 0, x, h);
        }

        for (int j = 0; j <= rowCount; j++) {
            double y = h*j/ rowCount + PIXEL_OFFSET;
            gc.strokeLine(0, y, w, y);
        }
    }

    @Override
    protected double computePrefWidth(double height) {
        return 20 * colCount;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 20 * rowCount;
    }

    public Paint getPaint() {
        return paint.get();
    }

    public ObjectProperty<Paint> paintProperty() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint.set(paint);
    }
}