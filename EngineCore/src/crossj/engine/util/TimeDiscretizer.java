package crossj.engine.util;

/**
 * Generalized from {@link crossj.engine.physics.World#step(float)} time
 * discretization, based on
 * http://gafferongames.com/game-physics/fix-your-timestep/
 *
 * @author Joe
 *
 */
public class TimeDiscretizer {
    private float accumulator;
    private final float stepSize;
    private int stepCount;

    public TimeDiscretizer(float stepSize) {
        this.stepSize = stepSize;
    }

    public TimeDiscretizer update(float delta) {
        accumulator += delta;
        stepCount = (int) (accumulator / stepSize);
        accumulator -= stepCount * stepSize;
        return this;
    }

    public float getStepSize() {
        return stepSize;
    }

    public int getElapsedSteps() {
        return stepCount;
    }

    public float getElapsedExact() {
        return accumulator + (stepCount * stepSize);
    }

    /**
     * Attempt to step by one. If there are steps from the most recent update
     * are available, reduces count by one and returns true.
     */
    public boolean step() {
        return step(1);
    }

    /**
     * Attempt to step by n. If there are n or more steps from the most recent
     * update available, reduces count by n and returns true.
     */
    public boolean step(int n) {
        if (stepCount >= n) {
            stepCount -= n;
            return true;
        }
        return false;
    }

    /**
     * Reset accumulator and stepCount
     */
    public void clear() {
        stepCount = 0;
        accumulator = 0;
    }
}
