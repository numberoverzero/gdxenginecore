package crossj.engine.util;

/**
 * Formatted description available at
 * https://gist.github.com/numberoverzero/8525669
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
        rollingAverage -= samples[oldest] / nsamples;
        samples[oldest] = remainder;
        rollingAverage += samples[oldest] / nsamples;
        oldest = (++oldest) % nsamples;
        remainder = remainderDt = 0;
    }

    public void update(float v, float stepDt) {
        float delta = remainderDt + stepDt;
        float stepWeight;
        while (delta >= resolution) {
            delta -= resolution;
            stepWeight = resolution - remainderDt;
            remainder = ((remainder * remainderDt) + (v * stepWeight)) / resolution;
            advance();
        }
        remainder = ((remainder * remainderDt) + (v * (delta - remainderDt))) / delta;
        remainderDt = delta;
    }

    public float getAverage() {
        float partialSample = ((remainder * remainderDt) + (samples[oldest] * (resolution - remainderDt))) / resolution;
        float correction = (partialSample - samples[oldest]) / nsamples;
        return rollingAverage + correction;
    }

}