package org.quartzpowered.engine.math;

public class MathUtil {
    static public final double DOUBLE_ROUNDING_ERROR = 0.00000000001;

    static public boolean isZero (double value) {
        return Math.abs(value) <= DOUBLE_ROUNDING_ERROR;
    }


}
