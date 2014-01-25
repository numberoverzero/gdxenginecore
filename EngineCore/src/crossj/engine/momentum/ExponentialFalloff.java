package crossj.engine.momentum;

import crossj.engine.util.MathUtil;

/**
 * Used for determining momentum for a given distance from origin along one
 * dimension. Falloff is calculated from the equation y = e^x - 1 where input is
 * scaled from the given range to the fixed range [0, ln(65)] and output is
 * scaled from the fixed range [0, 64] to the given output range.
 */
public class ExponentialFalloff {
    float minInput, maxInput, minOutput, maxOutput;
    static final float MIN_INPUT = 0, MAX_INPUT = (float) Math.log(65);
    static final float MIN_OUTPUT = 0, MAX_OUTPUT = 64;

    public ExponentialFalloff(float minInput, float maxInput, float minOutput, float maxOutput) {
        this.minInput = minInput;
        this.maxInput = maxInput;

        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
    }

    public float valueAt(float x) {
        x = Math.abs(x);
        if (x < minInput) {
            return maxOutput;
        } else if (x > maxInput) {
            return minOutput;
        } else {
            // Flip input - e^x is monotonically increasing but we want small
            // deltas to have a large value, and vice versa
            x = maxInput - x;

            // Translate x from [minInput, maxInput] to [MIN_INPUT, MAX_INPUT]
            x = MathUtil.translate(x, minInput, maxInput, MIN_INPUT, MAX_INPUT);
            x = (float) Math.exp(x) - 1;

            // Translate from [MIN_OUTPUT, MAX_OUTPUT] to [minOutput, maxOutput]
            return MathUtil.translate(x, MIN_OUTPUT, MAX_OUTPUT, minOutput, maxOutput);
        }
    }

    public float getMinInput() {
        return minInput;
    }

    public void setMinInput(float minInput) {
        this.minInput = minInput;
    }

    public float getMaxInput() {
        return maxInput;
    }

    public void setMaxInput(float maxInput) {
        this.maxInput = maxInput;
    }

    public float getMinOutput() {
        return minOutput;
    }

    public void setMinOutput(float minOutput) {
        this.minOutput = minOutput;
    }

    public float getMaxOutput() {
        return maxOutput;
    }

    public void setMaxOutput(float maxOutput) {
        this.maxOutput = maxOutput;
    }

}
