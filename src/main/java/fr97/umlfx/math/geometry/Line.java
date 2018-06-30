package fr97.umlfx.math.geometry;

/**
 * Simple implementation of Line in 2D integer space
 *
 * @author Filip
 */
public class Line {

    private final Point start;
    private final Point end;

    private Line(final Point start, final Point end) {
        this.start = start;
        this.end = end;
    }

    public static Line of(final Point start, final Point end) {
        if (start == null || end == null)
            throw new IllegalArgumentException("start and end point can't be null");
        return Line.of(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public static Line of(int startX, int startY, int endX, int endY) {
        return new Line(Point.of(startX, startY), Point.of(endX, endY));
    }

    public static Line of(double startX, double startY, double endX, double endY) {
        return new Line(Point.of(startX, startY), Point.of(endX, endY));
    }

    public static Line from(javafx.scene.shape.Line fxLine) {
        return Line.of(fxLine.getStartX(), fxLine.getStartY(), fxLine.getEndX(), fxLine.getEndY());
    }

    public javafx.scene.shape.Line toFXLine(){
        return new javafx.scene.shape.Line(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public Line copy() {
        return new Line(this.start.copy(), this.end.copy());
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int getStartX() {
        return start.getX();
    }

    public void setStartX(int x) {
        start.setX(x);
    }

    public int getEndX() {
        return end.getX();
    }

    public int getEndY(){
        return end.getY();
    }

    public int getStartY(){
        return start.getY();
    }
    public void setStartY(int y) {
        start.setY(y);
    }


    public double distanceFrom(Point point){

        /*
         *                      . Point(x,y)
         *                      \
         *                      \ - projection line of point to line
         *                      \
         *                      \
         *         Start.-------.-------------------.End
         *               \_____| P
         *        0 < projectionDistance < 1
         *
         *
         *                      . Point(x,y)                                    . Point(x,y)
         *                      \                                               \
         *                      \                                               \
         *                      \                                               \
         *                      \                                               \
         *             ---------.----Start.--------------------------.End--------.
         *                   P1 \________|                        P2 \__________|
         *              projectionDistance1 < 0                      projectionDistance1 > 1
         *
         *              Nekada projekcija tacke na pravu pada van datog segmenta prave
         *              Tada je najbliza presecan tacka sa linijom:
         *
         *              pocetna tacka linije ako je distanca projekcije < 0
         *              krajnja tacka linije ako je distanca projekcije > 1
         *
         *
         */

        double A = getStartX() - start.getX(); // Vector: Point.x  - Line.startX
        double B = getStartY() - start.getY(); // Vector: Point.y  - Line.starty
        double C = getEndX() - start.getX(); // Vector: Line.endX  - Line.startX
        double D = getEndY() - start.getX(); // Vector: Line.endY  - Line.startY

        double scalar = A * C + B * D; // skalarni proizvod vektora
        double len_sqr = C * C + D * D; // duzina linije na kvadrat
        double projectionDistance = -1; // Distanca od pocetne tacke linije do projekcije
        if(len_sqr != 0) // ako linija ima duzinu nula tj. linija je u stvari tacka
            projectionDistance = scalar / len_sqr;

        // Koordinate tacke preseka (tacka P na slici)
        double xx = 0;
        double yy = 0;

        if(projectionDistance < 0){ // linija je tacka ili se tacka projektuje
            xx = getStartX();
            yy = getStartY();
        } else if(projectionDistance > 1){
            xx = getEndX();
            yy = getEndY();
        } else {
            xx = getStartX() + projectionDistance * C;
            yy = getStartY() + projectionDistance * D;
        }

        // Formula za distancu izmedju dve date tacke
        double dx =  point.getX() - xx;
        double dy = point.getY() - yy;
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (start != null ? !start.equals(line.start) : line.start != null) return false;
        return end != null ? end.equals(line.end) : line.end == null;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
