package fr97.umlfx.classdiagram.edge.aggregation;

import fr97.umlfx.common.edge.AbstractEdgeView;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AggregationEdgeView extends AbstractEdgeView {


    protected final List<Line> shapeLines = new ArrayList<>();

    public AggregationEdgeView(AggregationEdge edge) {
        super(edge);

        calculatePositions(null, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate() {
        //System.out.println("Updated");
        //TODO nije potrebno za sad, mislim da ce trebati
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Group createShape(double startX, double startY, double endX, double endY) {
        //System.out.println("Creating shape for " + startX + ", "+ startY + ", "+ endX + ", "+ endY);
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

        Polygon background = new Polygon();
        background.getPoints().setAll(startX, startY,
                xs[0], ys[0],
                x4, y4,
                xs[1], ys[1]);
        background.setFill(Color.WHITE);
        background.toBack();
        Line line1 = new Line(startX, startY, xs[0], ys[0]);
        Line line2 = new Line(startX, startY, xs[1], ys[1]);
        Line line3 = new Line(xs[0], ys[0], x4, y4);
        Line line4 = new Line(xs[1], ys[1], x4, y4);
        /*line1.setStrokeWidth(1);
        line2.setStrokeWidth(1);
        line3.setStrokeWidth(1);
        line4.setStrokeWidth(1);*/
        group.getChildren().addAll(background, line1, line2, line3, line4);
        shapeLines.addAll(Arrays.asList(line1, line2, line3, line4));
        if (super.isSelected())
            shapeLines.forEach(line -> line.setStroke(Color.RED));
        group.setUserData("shape");
        return group;
    }

}
