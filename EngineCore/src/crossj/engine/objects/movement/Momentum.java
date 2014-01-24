package crossj.engine.objects.movement;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.util.RollingAverageVector2;

/**
 * All vectors are returned with an origin at the last updated position (except
 * getPosition which would otherwise always return 0)
 */
public class Momentum {
    private static final int DEFAULT_SAMPLES = 16;
    Vector2 focus, position;
    RollingAverageVector2 focusAverage, positionAverage;

    public Momentum(float duration) {
        focus = new Vector2();
        position = new Vector2();
        focusAverage = new RollingAverageVector2(duration, DEFAULT_SAMPLES);
        positionAverage = new RollingAverageVector2(duration, DEFAULT_SAMPLES);
    }

    /**
     * Set the duration for the average to track
     *
     * @return This object for chaining
     */
    public Momentum setDuration(float duration) {
        focusAverage.setDuration(duration);
        positionAverage.setDuration(duration);
        return this;
    }

    public float getDuration() {
        return focusAverage.getDuration();
    }

    /**
     * update averages and set current position and focus to the given values
     *
     * @return This object for chaining
     */
    public Momentum update(Vector2 position, Vector2 focus, float stepDt) {
        this.position.set(position);
        positionAverage.update(position, stepDt);
        this.focus.set(focus);
        focusAverage.update(focus, stepDt);
        return this;
    }

    public void update(float stepDt) {

    }

    public Vector2 getFocus() {
        return null;
    }

    public Vector2 getAvgFocus() {
        return null;
    }

    public Vector2 getNormFocus() {
        return null;
    }

    public Vector2 getNormAvgFocus() {
        return null;
    }

    /**
     * length of the projection of the unit vector of the average focus along
     * the unit vector of the focus
     *
     * @return value on the interval [-1, 1]
     */
    public float getPctFocus() {
        return 0;
    }

    public Vector2 getPosition() {
        return null;
    }

    public Vector2 getAvgPosition() {
        return null;
    }

    public Vector2 getNormPosition() {
        return null;
    }

    public Vector2 getNormAvgPosition() {
        return null;
    }

    /**
     * length of the projection of the unit vector of the average position along
     * the unit vector of the focus
     *
     * @return value on the interval [-1, 1]
     */
    public float getPctPosition() {
        return 0;
    }

}
