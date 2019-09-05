package fr97.umlfx.math.geometry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    private Point a = Point.of(0, 0);
    private Point b = Point.of(1, 1);

    @Test
    @DisplayName("Construction test using 2 Points")
    void constructionTestUsingPoints() {
        Line line = Line.of(a, b);

        assertEquals(a.getX(), line.getStartX());
        assertEquals(a.getY(), line.getStartY());
        assertEquals(b.getX(), line.getEndX());
        assertEquals(b.getY(), line.getEndY());
    }

    @Test
    void testOf() {
    }

    @Test
    void testOf1() {
    }

    @Test
    void from() {
    }

    @Test
    void toFXLine() {
    }

    @Test
    void copy() {
    }

    @Test
    void distanceFrom() {
    }
}