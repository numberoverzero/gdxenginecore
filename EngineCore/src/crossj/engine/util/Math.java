package crossj.engine.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Math {
    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    public static int constrain(int v, int a, int b) {
        return max(a, min(b, v));
    }

    public static float constrain(float v, float a, float b) {
        return max(a, min(b, v));
    }

    public static Vector2 constrain(Vector2 v, float a, float b) {
        return v.set(constrain(v.x, a, b), constrain(v.y, a, b));
    }

    public static Vector2 constrain(Vector2 v, Vector2 a, Vector2 b) {
        return v.set(constrain(v.x, a.x, b.x), constrain(v.y, a.y, b.y));
    }

    public static float angle(Vector2 v1, Vector2 v2) {
        float degrees = new Vector2(v2).sub(v1).angle();
        return degrees * MathUtils.degreesToRadians;
    }

    public static Vector2 midpoint(Vector2 v1, Vector2 v2) {
        return new Vector2((v2.x + v1.x) / 2, (v2.y + v1.y) / 2);
    }

    public static int mod(int v, int max) {
        return (v % max + max) % max;
    }

    public static int wrap(int v, int max) {
        return wrap(v, 0, max);
    }

    public static int wrap(int v, int min, int max) {
        // Normalize all calculations by subtracting min
        return (min == max) ? max : mod((v - min), (max - min)) + min;
    }

    public static float lerp(float t, float v0, float v1) {
        return v0 + t * (v1 - v0);
    }

    public static Vector2 randomVector2(float min, float max) {
        return randomVector2(min, max, min, max);
    }

    public static Vector2 randomVector2(float minX, float maxX, float minY, float maxY) {
        return new Vector2(MathUtils.random(minX, maxX), MathUtils.random(minY, maxY));
    }
}
