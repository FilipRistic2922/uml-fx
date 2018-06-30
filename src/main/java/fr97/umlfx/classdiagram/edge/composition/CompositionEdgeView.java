package fr97.umlfx.classdiagram.edge.composition;

import fr97.umlfx.common.edge.AbstractEdgeView;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.Arrays;

public class CompositionEdgeView extends AbstractEdgeView {

    public CompositionEdgeView(CompositionEdge edge) {
        super(edge);

        calculatePositions(null, null, null);
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected Group createShape(double startX, double startY, double endX, double endY) {
        Group group = new Group();
        double phi = Math.toRadians(40);
        int barb = 14;
        double dy = startY - endY;
        double dx = startX - endX;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + phi;

        double[] xs = new double[2];
        double[] ys = new double[2];
        double x4, y4;
        x4 = startX - 23 * Math.cos(theta);
        y4 = startY - 23 * Math.sin(theta);
        for (int j = 0; j < 2; j++) {
            x = startX - barb * Math.cos(rho);
            y = startY - barb * Math.sin(rho);
            xs[j] = x;
            ys[j] = y;
            rho = theta - phi;
        }
        Polygon diamondBackground = new Polygon();
        diamondBackground.getPoints().setAll(startX, startY,
                xs[0], ys[0],
                x4, y4,
                xs[1], ys[1]);
        diamondBackground.fillProperty().bind(Bindings.createObjectBinding(
                () -> isSelected() ? Color.RED : Color.BLACK,
                selectedProperty()
        ));
        Line line1 = new Line(startX, startY, xs[0], ys[0]);
        Line line2 = new Line(startX, startY, xs[1], ys[1]);
        Line line3 = new Line(xs[0], ys[0], x4, y4);
        Line line4 = new Line(xs[1], ys[1], x4, y4);
        /*line1.setStrokeWidth(super.STROKE_WIDTH);
        line2.setStrokeWidth(super.STROKE_WIDTH);
        line3.setStrokeWidth(super.STROKE_WIDTH);
        line4.setStrokeWidth(super.STROKE_WIDTH);*/
        group.getChildren().addAll(diamondBackground, line1, line2, line3, line4);
        //diamondBackground.toBack();
        shapeLines.addAll(Arrays.asList(line1, line2, line3, line4));

        /*if (isSelected())
            for (Line l : shapeLines)
                l.setStroke(Color.RED);*/
        group.setUserData("shape");
        return group;
    }
}
