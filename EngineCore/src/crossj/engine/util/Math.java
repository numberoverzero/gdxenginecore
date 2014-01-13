package crossj.engine.util;

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
}
