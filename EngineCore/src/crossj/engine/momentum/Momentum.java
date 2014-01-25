package crossj.engine.momentum;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.util.MathUtil;
import crossj.engine.util.RollingAverageVector2;

public class Momentum {
    ExponentialFalloff radialFalloff, angularFalloff;
    RollingAverageVector2 focusAverage;
    Vector2 focus, momentum, tmpFocus, tmpFocusAverage;

    public Momentum() {
        focus = new Vector2();
        momentum = new Vector2();
        tmpFocus = new Vector2();
        tmpFocusAverage = new Vector2();

        // Track averages for 6 seconds, persist updates every 6/16 seconds
        float duration = 6;
        int samples = 16;
        focusAverage = new RollingAverageVector2(duration, samples);

        // Further than 60 units away = 0 momentum
        // Closer than 15 units away = 1 momentum
        radialFalloff = new ExponentialFalloff(15, 60, 0, 1);

        // Further than 25 degrees away = 0 momentum
        // Closer than 5 degrees away = 1 momentum
        angularFalloff = new ExponentialFalloff(5 * MathUtils.degreesToRadians, 25 * MathUtils.degreesToRadians, 0, 1);
    }

    public void update(Vector2 focus, float stepDt) {
        focusAverage.update(focus, stepDt);
        this.focus.set(focus);
        recalculate();
    }

    private void recalculate() {
        // Convert to polar coordinates for radial/angular falloff calculation
        MathUtil.toPolar(tmpFocusAverage.set(focusAverage.value()));
        MathUtil.toPolar(tmpFocus.set(focus));

        // Compute falloff of average's radial distance from focus
        float deltaRadius = tmpFocusAverage.x - tmpFocus.x;
        momentum.x = radialFalloff.valueAt(deltaRadius);

        // Compute falloff of average's angle from focus
        // NOTE: correction is necessary when deltaAngle > PI because atan2
        // returns values on [-PI, PI] and we want the smallest angle between
        // them, not the angle from origin
        float deltaAngle = Math.abs(tmpFocusAverage.y - tmpFocus.y);
        if (deltaAngle >= MathUtils.PI) {
            deltaAngle = MathUtils.PI2 - deltaAngle;
        }
        momentum.y = angularFalloff.valueAt(deltaAngle);
    }

    public float getRadialMomentum() {
        return momentum.x;
    }

    public float getAngularMomentum() {
        return momentum.y;
    }

    public Vector2 getFocus() {
        return focus;
    }

    public Vector2 getFocusAverage() {
        return focusAverage.value();
    }

    public ExponentialFalloff getRadialFalloff() {
        return radialFalloff;
    }

    public ExponentialFalloff getAngularFalloff() {
        return angularFalloff;
    }

    public float getDuration() {
        return focusAverage.getDuration();
    }

    public void setDuration(float duration) {
        focusAverage.setDuration(duration);
    }

}
