package crossj.engine.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathTest {
    @Test
    public void testConstrainOutOfRangeBelow() {
        assertEquals(4, Math.constrain(1, 4, 7));
    }

    @Test
    public void testConstrainOutOfRangeNegativeBelow() {
        assertEquals(-2, Math.constrain(-3, -2, 7));
    }

    @Test
    public void testConstrainOutOfRangeAbove() {
        assertEquals(7, Math.constrain(12, 4, 7));
    }

    @Test
    public void testConstrainOutOfRangeNegativeAbove() {
        assertEquals(-3, Math.constrain(-1, -7, -3));
    }

    @Test
    public void testConstrainInRange() {
        assertEquals(5, Math.constrain(5, 4, 7));
    }

    @Test
    public void testConstrainInRangeNegative() {
        assertEquals(-5, Math.constrain(-5, -7, -3));
    }

    @Test
    public void testModAbove() {
        assertEquals(2, Math.mod(5, 3));
    }

    @Test
    public void testModBelow() {
        assertEquals(2, Math.mod(2, 3));
    }

    @Test
    public void testModNegativeAbove() {
        assertEquals(-2, Math.mod(-5, -3));
    }

    @Test
    public void testModNegativeBelow() {
        assertEquals(-2, Math.mod(-2, -3));
    }

    @Test
    public void testWrapBelow() {
        assertEquals(5, Math.wrap(2, 4, 7));
    }

    @Test
    public void testWrapAbove() {
        assertEquals(5, Math.wrap(8, 4, 7));
    }

    @Test
    public void testWrapUpperLimit() {
        assertEquals(4, Math.wrap(7, 4, 7));
    }

    @Test
    public void testWrapLowerLimit() {
        assertEquals(4, Math.wrap(4, 4, 7));
    }

    @Test
    public void testWrapNegativeBelow() {
        assertEquals(-6, Math.wrap(-9, -7, -4));
    }

    @Test
    public void testWrapNegativeAbove() {
        assertEquals(-5, Math.wrap(-2, -7, -4));
    }

    @Test
    public void testWrapNegativeUpperLimit() {
        assertEquals(-7, Math.wrap(-4, -7, -4));
    }

    @Test
    public void testWrapNegativeLowerLimit() {
        assertEquals(-7, Math.wrap(-7, -7, -4));
    }

    @Test
    public void testWrapEqualMinMax() {
        assertEquals(5, Math.wrap(-1, 5, 5));
    }
}
