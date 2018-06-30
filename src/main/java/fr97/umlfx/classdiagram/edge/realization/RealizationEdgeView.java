package fr97.umlfx.classdiagram.edge.realization;

import fr97.umlfx.common.edge.AbstractEdgeView;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.Arrays;

public class RealizationEdgeView extends AbstractEdgeView {

    public RealizationEdgeView(RealizationEdge edge){
        super(edge);

        getHeadLine().getStrokeDashArray().addAll(10.0, 10.0);
        getMiddleLine().getStrokeDashArray().addAll(10.0, 10.0);
        getTailLine().getStrokeDashArray().addAll(10.0, 10.0);

        calculatePositions(null, null, null);
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected Group createShape(double startX, double startY, double endX, double endY) {
        Group group = new Group();

        double phi = Math.toRadians(25);
        int barb = 20;
        double dy = startY - endY;
        double dx = startX - endX;
        double theta = Math.atan2(dy, dx);
        double x = 0;
        double y = 0;
        double rho = theta + phi;

        double[] xs = new double[2];
        double[] ys = new double[2];

        for (int j = 0; j < 2; j++) {
            x = startX - barb * Math.cos(rho);
            y = startY - barb * Math.sin(rho);
            xs[j] = x;
            ys[j] = y;
            rho = theta - phi;
        }

        Polygon background = new Polygon();
        background.getPoints().addAll(startX, startY,
                xs[0], ys[0],
                xs[1], ys[1]);
        background.setFill(Color.WHITE);
        background.toBack();
        Line line1 = new Line(startX, startY, xs[0], ys[0]);
        Line line2 = new Line(startX, startY, xs[1], ys[1]);
        Line line3 = new Line(xs[0], ys[0], xs[1], ys[1]);

        shapeLines.addAll(Arrays.asList(line1, line2, line3));

        group.getChildren().addAll(background, line1, line2, line3);
        group.setUserData("shape");
        return group;
    }
}
