package crossj.engine.util;

import com.badlogic.gdx.math.Vector2;

public class RollingAverageVector2 {
    private final RollingAverage x, y;
    private final Vector2 avg = new Vector2();

    public RollingAverageVector2(float duration, int samples) {
        x = new RollingAverage(duration, samples);
        y = new RollingAverage(duration, samples);
    }

    public RollingAverageVector2 setDuration(float duration) {
        x.setDuration(duration);
        y.setDuration(duration);
        return this;
    }

    public float getDuration() {
        return x.getDuration();
    }

    public RollingAverageVector2 update(Vector2 v, float stepDt) {
        x.update(v.x, stepDt);
        y.update(v.y, stepDt);
        avg.set(x.value(), y.value());
        return this;
    }

    public Vector2 value() {
        return avg;
    }
}
