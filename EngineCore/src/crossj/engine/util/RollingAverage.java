package crossj.engine.util;

/**
 * Formatted description available at
 * https://gist.github.com/numberoverzero/8536341
 */
public class RollingAverage {
    private final float resolution;

    private float remainderDt;
    private float remainder;

    private final float[] samples;
    private final int nsamples;
    private int oldest;

    private float rollingAverage;

    public RollingAverage(float duration, int samples) {
        this.samples = new float[samples];
        nsamples = samples;
        resolution = duration / samples;
    }

    private void advance() {
        rollingAverage += (remainder - samples[oldest]) / nsamples;
        samples[oldest] = remainder;
        oldest = (++oldest) % nsamples;
        remainder = remainderDt = 0;
    }

    public void update(float v, float stepDt) {
        float stepWeight, delta = remainderDt + stepDt;
        while (delta >= resolution) {
            delta -= resolution;
            stepWeight = resolution - remainderDt;
            remainder = ((remainder * remainderDt) + (v * stepWeight)) / resolution;
            advance();
        }
        remainder = ((remainder * remainderDt) + (v * (delta - remainderDt))) / delta;
        remainderDt = delta;
        if(Float.isNaN(remainder)) {
            remainder = remainderDt = 0;
        }
    }

    public float getAverage() {
        float partialSample = ((remainder * remainderDt) + (samples[oldest] * (resolution - remainderDt))) / resolution;
        float correction = (partialSample - samples[oldest]) / nsamples;
        return rollingAverage + correction;
    }

    public float value() {
        return getAverage();
    }
}