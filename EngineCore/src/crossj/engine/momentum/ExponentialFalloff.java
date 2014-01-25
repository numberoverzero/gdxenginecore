package crossj.engine.momentum;

import com.badlogic.gdx.math.Vector2;

/**
 * Used for determining momentum for a given distance from origin along one
 * dimension. For the Falloff (xMin=1, xMax=10, yMin=10, yMax=100) any x value
 * less than 1 will return 100, any x value greater than 10 will return 10, and
 * any x value between 1 and 10 will return a value between 10 and 100 using an
 * exponential function calculated from the given endpoints.
 */
public class ExponentialFalloff {
    /**
     * minInput, maxOutput
     */
    Vector2 p1 = new Vector2();

    /**
     * maxInput, minOutput
     */
    Vector2 p2 = new Vector2();

    /**
     * a, b
     */
    Vector2 eq = new Vector2();

    public ExponentialFalloff(float minInput, float maxInput, float minOutput, float maxOutput) {
        p1.set(minInput, maxOutput);
        p2.set(maxInput, minOutput);
        recalculate();
    }

    public float valueAt(float x) {
        x = Math.abs(x);
        if (x < p1.x) {
            return p1.y;
        } else if (x > p2.x) {
            return p2.y;
        } else {
            return (float) (eq.x * Math.pow(eq.y, x));
        }
    }

    private void recalculate() {
        double b = Math.pow((p1.y / p2.y), (p2.x - p1.x));
        double a = p2.y / Math.pow(b, p2.x);
        eq.set((float) a, (float) b);
    }

    public float getMinInput() {
        return p1.x;
    }

    public void setMinInput(float minInput) {
        p1.x = minInput;
        recalculate();
    }

    public float getMaxInput() {
        return p2.x;
    }

    public void setMaxInput(float maxInput) {
        p2.x = maxInput;
        recalculate();
    }

    public float getMinOutput() {
        return p2.y;
    }

    public void setMinOutput(float minOutput) {
        p2.y = minOutput;
        recalculate();
    }

    public float getMaxOutput() {
        return p1.y;
    }

    public void setMaxOutput(float maxOutput) {
        p1.y = maxOutput;
        recalculate();
    }

}
