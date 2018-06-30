package fr97.umlfx.math.geometry;

import javafx.geometry.Rectangle2D;

/**
 * Simple representation of rectangle in 2D integer space
 *
 * @author Filip
 */
public class Rectangle {

    private final Point start;

    private int width;
    private int height;

    private Rectangle(final Point start, final int width, final int height) {
        this.start = start;
        this.width = width;
        this.height = height;
    }

    public static Rectangle of(final int startX, final int startY, final int width, final int height) {
        return new Rectangle(Point.of(startX, startY), width, height);
    }

    public static Rectangle of(final double startX, final double startY, final double width, final double height) {
        int sX = (int) Math.round(startX);
        int sY = (int) Math.round(startY);
        int w = (int) Math.round(width);
        int h = (int) Math.round(height);
        return Rectangle.of(sX, sY, w, h);
    }

    /**
     * Creates new Rectangle with given starting point, width and height
     *
     * @param start  - starting point (top left coordinate)
     * @param width  - width of rectangle
     * @param height - height of rectangle
     * @return Rectangle with given starting coordinate, width and height
     */
    public static Rectangle of(final Point start, final int width, final int height) {
        if (start == null)
            throw new IllegalArgumentException("point can't be null");
        return Rectangle.of(start.getX(), start.getY(), width, height);
    }

    public static Rectangle from(Rectangle2D rectangle2D){
        return Rectangle.of(
                rectangle2D.getMinX(),
                rectangle2D.getMinY(),
                rectangle2D.getWidth(),
                rectangle2D.getHeight());
    }

    public Rectangle2D toRectangle2D(){
        return new  Rectangle2D(start.getX(), start.getY(), width, height);
    }

    /**
     * Creates copy with same properties as this rectangle
     *
     * @return copy of this rectangle
     */
    public Rectangle copy() {
        return new Rectangle(start.copy(), width, height);
    }

    public Point getStart() {
        return start;
    }

    public int getStartX() {
        return start.getX();
    }

    public int getStartY() {
        return start.getY();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getEndX() {
        return start.getX() + width;
    }

    public int getEndY() {
        return start.getY() + height;
    }

    public Point findCenter() {
        return Point.of(start.getX() + width / 2, start.getY() + height / 2);
    }

    public boolean contains(final int x, final int y) {
        return x >= start.getX() && x <= getEndX() && y >= start.getY() && y <= getEndY();
    }

    public boolean contains(Point point) {
        if (point == null)
            throw new IllegalArgumentException("point can't be null");
        return contains(point.getX(), point.getY());
    }

    public boolean contains(Rectangle rectangle){
        return this.contains(rectangle.start) && this.contains(Point.of(getEndX(), getEndY()));
    }

    public void moveBy(double xOffset, double yOffset) {
        start.moveBy(xOffset, yOffset);
    }

    public Rectangle movedBy(double xOffset, double yOffset) {
        return Rectangle.of(start.getX() + xOffset, start.getY() + yOffset, width, height);
    }

    public void moveTo(double x, double y) {
        start.moveTo(x, y);
    }

    /**
     * @param rect The rectangle to include.
     * @return A new rectangle that is this rectangle enlarged to include rect.
     */
    public Rectangle add(Rectangle rect)
    {
        if(rect == null)
            throw new  IllegalArgumentException("rect can't be null");
        int startX = Math.min(getStartX(), rect.getStartX());
        int startY = Math.min(getStartY(), rect.getStartY());
        int endX = Math.max(getEndX(), rect.getEndX());
        int endY = Math.max(getEndY(), rect.getEndY());
        int width = endX-startX;
        int height = endY-startY;
        return Rectangle.of(startX, startY, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (width != rectangle.width) return false;
        if (height != rectangle.height) return false;
        return start != null ? start.equals(rectangle.start) : rectangle.start == null;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "start=" + start +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
