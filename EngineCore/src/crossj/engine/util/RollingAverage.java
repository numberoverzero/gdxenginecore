package crossj.engine.util;

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
        rollingAverage -= samples[oldest] / nsamples; // Remove oldest from
                                                      // rolling avg
        samples[oldest] = remainder; // Store newest sample in place of oldest
        rollingAverage += samples[oldest] / nsamples; // Apply newest sample to
                                                      // rolling avg
        oldest = (++oldest) % nsamples; // Advance oldest pointer;

        remainder = 0; // Reset remainder
        remainderDt = 0;
    }

    public void update(float v, float stepDt) {
        float delta = remainderDt + stepDt;
        float stepWeight;
        while (delta >= resolution) {
            delta -= resolution;
            stepWeight = resolution - remainderDt; // Amount of this dt needed
                                                   // to complete a sample

            remainder = ( // Weighted average of remainder from
                    (remainder * remainderDt) // previous step and partial value
                    + (v * stepWeight)) // from current step. Total weight
                    / resolution; // equals single sample resolution

            advance(); // Store the new sample and update rolling
        }

        // In the simple case (where we called advance in the loop above at
        // least once, remainderDt and remainder are both 0, and the following
        // simplifies to:
        // remainder = (0 + (v * delta)) / delta = v;

        // Otherwise, we never entered the loop above (haven't stepped far
        // enough for a full sample) and the coeffecient of v below is:
        // (delta - remainderDt) = (remainderDt + stepDt - remainderDt) = stepDt
        // and this is just a simple weighted average of the two parts of a
        // sample

        remainder = ((remainder * remainderDt) + (v * (delta - remainderDt))) / delta;
        remainderDt = delta;
    }

    public float getAverage() {
        float partialSample = ( // Still need to include
                (remainder * remainderDt) // the remainder, by
                + (samples[oldest] * (resolution - remainderDt))) // weighted
                                                                  // averaging
                / resolution; // with oldest sample.

        float correction = (partialSample - samples[oldest]) / nsamples; // Delta
                                                                         // between
                                                                         // partial
                                                                         // and
                                                                         // oldest,
                                                                         // scaled
        return rollingAverage + correction; // to number of samples
    }

}