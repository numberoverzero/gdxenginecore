package crossj.engine.util;

/**
 * Formatted description available at
 * https://gist.github.com/numberoverzero/8536341
 */
public class RollingAverage {
    private float resolution; // Length of one sample

    private float remainderDt;
    private float remainder;

    private final float[] samples;
    private final int nSamples;
    private int oldest;

    private float rollingAverage;

    public RollingAverage(float duration, int samples) {
        this.samples = new float[samples];
        nSamples = samples;
        resolution = duration / nSamples;
    }

    private void advance() {
        rollingAverage += (remainder - samples[oldest]) / nSamples;
        samples[oldest++] = remainder;
        oldest %= nSamples;
        remainder = remainderDt = 0;
    }

    public RollingAverage setDuration(float duration) {
        // Scaling factor to translate remainderDt
        float scale = duration / (resolution * nSamples);
        resolution = duration / nSamples;
        remainderDt *= scale;
        return this;
    }

    public float getDuration() {
        return resolution * nSamples;
    }

    public RollingAverage update(float v, float stepDt) {
        float stepWeight, delta = remainderDt + stepDt;
        while (delta >= resolution) {
            delta -= resolution;
            stepWeight = resolution - remainderDt;
            remainder = ((remainder * remainderDt) + (v * stepWeight)) / resolution;
            advance();
        }
        remainder = ((remainder * remainderDt) + (v * (delta - remainderDt))) / delta;
        remainderDt = delta;
        if (Float.isNaN(remainder)) {
            remainder = remainderDt = 0;
        }
        return this;
    }

    public float value() {
        float partialSample = ((remainder * remainderDt) + (samples[oldest] * (resolution - remainderDt))) / resolution;
        float correction = (partialSample - samples[oldest]) / nSamples;
        return rollingAverage + correction;
    }
}