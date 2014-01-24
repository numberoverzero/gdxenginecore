package crossj.engine.objects.movement;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.util.MathUtil;
import crossj.engine.util.RollingAverageVector2;

public class Momentum {
    private static final int DEFAULT_SAMPLES = 16;
    Vector2 focus, position, tmp, tmp2, tmp3;
    RollingAverageVector2 focusAverage, positionAverage;

    public Momentum(float duration) {
        tmp = new Vector2();
        tmp2 = new Vector2();
        tmp3 = new Vector2();
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

    public Vector2 getFocus() {
        return tmp.set(focus).sub(position);
    }

    public Vector2 getAvgFocus() {
        return tmp.set(focusAverage.value()).sub(position);
    }

    public Vector2 getNormFocus() {
        return getFocus().nor();
    }

    public Vector2 getNormAvgFocus() {
        return getAvgFocus().nor();
    }

    /**
     * scalar projection of the normalized average focus along the normalized
     * focus
     *
     * @return value on the interval [-1, 1]
     */
    public float getPctFocus() {
        tmp2.set(getNormAvgFocus());
        tmp3.set(getNormFocus());
        return MathUtil.scalarProjection(tmp2, tmp3);
    }

    public Vector2 getPosition() {
        return tmp.set(position);
    }

    public Vector2 getAvgPosition() {
        return tmp.set(positionAverage.value()).sub(position);
    }

    public Vector2 getNormAvgPosition() {
        return getAvgPosition().nor();
    }

    /**
     * scalar projection length of the normalized average position along the
     * normalized focus
     *
     * @return value on the interval [-1, 1]
     */
    public float getPctPosition() {
        tmp2.set(getNormAvgPosition());
        tmp3.set(getNormFocus());
        return MathUtil.scalarProjection(tmp2, tmp3);
    }

}
