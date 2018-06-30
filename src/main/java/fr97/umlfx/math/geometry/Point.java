package fr97.umlfx.math.geometry;

import javafx.geometry.Point2D;

/**
 * Simple implementation of Point in 2D integer space
 *
 * @author Filip
 */
public class Point {

    /**
     *  X coordinate
     */
    private int x;

    /**
     * Y coordinate
     */
    private int y;

    /**
     *
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    private Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Factory method for creating Point on given (x,y) position
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    public static Point of(final int x, final int y) {
        return new Point(x, y);
    }

    /**
     * Factory method for creating Point on given (x,y) position
     * Rounds x and y values using Math::round function
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    public static Point of(final double x, final double y) {
        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    public static Point from(Point2D point2D){
        return Point.of(point2D.getX(), point2D.getY());
    }

    public Point2D toPoint2D(){
        return new Point2D(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Moves x and y coordinate of this point by given xOffset and yOffset values
     *
     * @param xOffset - amount to add to x (this.x += y)
     * @param yOffset - amount to add to y (this.y += y)
     */
    public void moveBy(final double xOffset, final double yOffset) {
        this.x += (int) Math.round(xOffset);
        this.y += (int) Math.round(yOffset);
    }

    /**
     *
     * @param xOffset
     * @param yOffset
     * @return new Point moved by xOffset and yOffset
     */
    public Point movedBy(final double xOffset, final double yOffset){
        return Point.of(x+xOffset, y+yOffset);
    }

    /**
     * Moves this point to new one based on given x and y values
     * @param x - new x coordinate (this.x = x)
     * @param y - new y coordinate (this.y = y)
     */
    public void moveTo(final double x, final double y){
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
    }

    /**
     * Calculated distance between this and given point
     * @param point - given point
     * @return distance between this and given point
     */
    public double distance(Point point) {
        if (point == null)
            throw new IllegalArgumentException("Point can't be null");
        int x0 = point.x - x;
        int y0 = point.y - y;
        return Math.sqrt(x0 * x0 + y0 * y0);
    }

    public Point copy(){
        return new Point(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    /*@Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }*/

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
