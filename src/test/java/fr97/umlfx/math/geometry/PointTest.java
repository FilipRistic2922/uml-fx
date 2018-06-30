package fr97.umlfx.math.geometry;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private Point a = Point.of(0, 0);
    private Point b = Point.of(0, 0);

    @Test
    @DisplayName("Construction test for integer")
    void constructionTestInt() {
        Point point = Point.of(5, 10);
        int expectedX = 5;
        int expectedY = 10;
        assertEquals(expectedX, point.getX(), "x must be 5 but it is " + point.getX());
        assertEquals(expectedY, point.getY(), "y must be 10 but it is " + point.getY());
    }

    @Test
    @DisplayName("Construction test for double")
    void constructionTestDouble() {
        Point point = Point.of(11.4, 33.5);
        int expectedX = 11;
        int expectedY = 34;
        assertEquals(expectedX, point.getX(), "x must be 11 but it is " + point.getX());
        assertEquals(expectedY, point.getY(), "y must be 34 but it is " + point.getY());
    }

    @Test
    @DisplayName("Distance test with positive numbers")
    void distanceTestPositiveNumbers() {
        /*
            given a(6,9) and b(3,5), distance d must be:
            d = sqrt((6-3)^2 + (9-5)^2) = sqrt(3^2 + 4^2) = sqrt(9 + 15) = sqrt(25) = 5
        */
        a.moveTo(6, 9);
        b.moveTo(3, 5);
        double expected = 5;
        assertEquals(expected, a.distance(b), "distance must be 5 but it is " + a.distance(b));
    }

    @Test
    @DisplayName("Distance test with negative numbers")
    void distanceTestNegativeNumbers() {
        /*
           given a(-7,-9) and b(5,-4), distance d must be:
           d = sqrt((-7-5)^2 + (-9-(-4))^2) = sqrt((-12)^2 + (-5)^2) = sqrt(144+25) = sqrt(149) = 13
        */
        a.moveTo(-7, -9);
        b.moveTo(5, -4);
        double expected = 13;
        assertEquals(expected, a.distance(b), "distance must be 13 but it is " + a.distance(b));
    }

    @Test
    @DisplayName("Distance test with null throws IllegalArgumentException")
    void distanceTestWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            a.distance(null);
        });
    }

    @Test
    @DisplayName("Move by 5 and -17")
    void moveByTest() {
        a.setX(4);
        a.setY(7);
        a.moveBy(5, -17);
        int expectedX = 9;
        int expectedY = -10;
        assertEquals(expectedX, a.getX(), "x must be 9 but it is " + a.getX());
        assertEquals(expectedY, a.getY(), "y must be -10 but it is " + a.getY());
    }

    @Test
    @DisplayName("Moved by should return point moved by 4 and 6")
    void movedByTest() {
        a.setX(-14);
        a.setY(19);
        Point movedPoint = a.movedBy(3, 6);
        int expectedX = -11;
        int expectedY = 25;
        assertEquals(expectedX, movedPoint.getX(), "x must be -11 but it is " + movedPoint.getX());
        assertEquals(expectedY, movedPoint.getY(), "y must be 25 but it is " + movedPoint.getY());
        assertNotSame(a,movedPoint, "Moved point shouldn't be same as a");
    }

    @Test
    @DisplayName("Copied point should be equal to original but not same")
    void copyTest() {
        Point copy = a.copy();
        assertEquals(copy, a);
        assertNotSame(copy, a);
    }

    @Test
    @DisplayName("Test conversion from my Point to javafx.geometry.Point2D")
    void to2DPointTest(){
        a.moveTo(7.7,4.4);
        Point2D point2D = a.toPoint2D();
        assertEquals(a.getX(), point2D.getX());
        assertEquals(a.getY(), point2D.getY());
    }


    @Test
    @DisplayName("Test conversion from javafx.geometry.Point2D to my Point")
    void from2DPointTest(){
        Point2D point2D = new  Point2D(11.9,6.5);
        Point point = Point.from(point2D);
        System.out.println("My point: " +point);
        int expectedX = 12;
        int expectedY = 7;
        assertEquals(expectedX, point.getX());
        assertEquals(expectedY, point.getY());
    }
}