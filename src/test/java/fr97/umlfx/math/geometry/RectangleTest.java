package fr97.umlfx.math.geometry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    private Rectangle rectA = Rectangle.of(0, 0, 0, 0);
    private Rectangle rectB = Rectangle.of(0, 0, 0, 0);
    private Point pointA = Point.of(0, 0);


    @Test
    @DisplayName("Construction test for double")
    void constructionTestForDouble() {
        Rectangle rect = Rectangle.of(4.4, 5.51, 16.5, 7.49);

        int expectedStartX = 4;
        int expectedStartY = 6;
        int expectedWidth = 17;
        int expectedHeight = 7;
        int expectedEndX = expectedStartX + expectedWidth;
        int expectedEndY = expectedStartY + expectedHeight;

        assertEquals(expectedStartX, rect.getStartX(), "Start X");
        assertEquals(expectedStartY, rect.getStartY(), "Start Y");
        assertEquals(expectedWidth, rect.getWidth(), "Width");
        assertEquals(expectedHeight, rect.getHeight(), "Height");
        assertEquals(expectedEndX, rect.getEndX(), "End X");
        assertEquals(expectedEndY, rect.getEndY(), "End Y");
    }

    @Test
    @DisplayName("Construction test for Point")
    void constructionTestForPoint() {
        Point p = Point.of(12, -3);
        Rectangle rect = Rectangle.of(p, 5, 18);
        int expectedStartX = 12;
        int expectedStartY = -3;

        assertEquals(p, rect.getStart(), "Points must be equal");
        assertNotSame(p, rect.getStart(), "Points can't be same");
        assertEquals(expectedStartX, rect.getStartX(), "Start X");
        assertEquals(expectedStartY, rect.getStartY(), "Start Y");
    }

    @Test
    @DisplayName("Construction test with null point, throws IllegalArgumentException")
    void constructionTestWithNullShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle.of(null, 40, -40);
        });
    }

    @Test
    @DisplayName("Contains point test with point inside rectangle")
    void containsPointInsideRectangle() {
        rectA.moveTo(3, 3);
        rectA.setWidth(7);
        rectA.setHeight(8);
        pointA.moveTo(5, 4);
        boolean expected = true;
        boolean actual = rectA.contains(pointA);
        assertEquals(expected, actual, "Rectangle should contain point but it doesn't");
    }

    @Test
    @DisplayName("Contains point test with point on outside of rectangle")
    void containsPointOnRectangleBorder() {
        rectA.moveBy(6, 4);
        rectA.setWidth(12);
        rectA.setHeight(8);
        pointA.moveTo(3, 2);
        boolean expected = false;
        boolean actual = rectA.contains(pointA);
        assertEquals(expected, actual, "Rectangle shouldn't contain point but it does");
    }

    @Test
    @DisplayName("Contains point must throw exception when given point is null")
    void containsPointMustThrowException() {
        rectA.moveTo(12, 35);
        //assertThrows(IllegalArgumentException.class, rectA::contains);
    }

    @Test
    @DisplayName("Moved by should return rectangle moved by 12 and -37")
    void movedByShouldReturnRectangleMovedByGivenValues() {
        rectA.moveTo(-23, 40);
        Rectangle newRect = rectA.movedBy(12, -37);
        int expectedX = -11;
        int expectedY = 3;
        assertEquals(expectedX, newRect.getStartX());
        assertEquals(expectedY, newRect.getStartY());
        assertNotSame(rectA, newRect);
    }

    @Test
    @DisplayName("Add rectangle test with given rectangle containing rectangle that we add to")
    void addRectangleWhenGivenRectangleThatContainsIt() {
        /*
                Example:
             _________________
            \                \
            \     _______    \
            \    \      \    \
            \    \ rectA\    \
            \    \______\    \
            \                \
            \       rectB    \
            \________________\
         */

        rectA.moveTo(3, 4);
        rectA.setWidth(4);
        rectA.setHeight(9);
        rectB.moveTo(1, -3);
        rectB.setWidth(11);
        rectB.setHeight(19);

        assertTrue(rectB.contains(rectA));
        assertFalse(rectA.contains(rectB));
        Rectangle rectC = rectA.add(rectB);

        int expectedStartX = 1;
        int expectedStartY = -3;
        int expectedWidth = 11;
        int expectedHeight = 19;

        assertEquals(expectedStartX, rectC.getStartX(), "Start X");
        assertEquals(expectedStartY, rectC.getStartY(), "Start Y");
        assertEquals(expectedWidth, rectC.getWidth(), "Width");
        assertEquals(expectedHeight, rectC.getHeight(), "Height");

        System.out.println(rectC);

    }


    @Test
    @DisplayName("Add rectangle must throw exception when given null")
    void addRectangleMustThrowExceptionWhenGivenNull() {
        rectA.moveTo(3, 4);
        assertThrows(IllegalArgumentException.class, () -> rectA.add(null));
    }

    @Test
    @DisplayName("Find center of Rectangle starting on 0,0 with width and height 2 should be Point(1,1)")
    void findCenterTest() {
        rectA.setWidth(2);
        rectA.setHeight(2);
        Point expected = Point.of(1, 1);
        assertEquals(expected, rectA.findCenter(), "Center should be equal to Point(1,1)");
    }


    @Test
    @DisplayName("Copied rectangle should be equal to original but not same")
    void copyTest() {
        Rectangle copy = rectA.copy();
        assertEquals(copy, rectA);
        assertNotSame(copy, rectA);
    }
}