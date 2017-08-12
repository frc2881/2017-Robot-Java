package org.frc2881.sample.utils;

import java.util.function.DoubleSupplier;

/**
 * Helper for monitoring for conditions where motors suck too much current, usually because the motor isn't turning.
 */
public class AmpMonitor {
    private final double threshold;
    private final DoubleSupplier currentSupplier;
    private double previous;
    private long violations;

    public AmpMonitor(double threshold, DoubleSupplier currentSupplier) {
        this.threshold = threshold;
        this.currentSupplier = currentSupplier;
    }

    public void reset() {
        previous = Double.MAX_VALUE;
        violations = 0;
    }

    public boolean checkTriggered() {
        // Is current above threshold and not dropping?  (ie. is motor stopped & not accelerating?)
        double current = currentSupplier.getAsDouble();
        if (current >= threshold && current >= previous) {
            violations++;
        }
        previous = current;

        return isTriggered();
    }

    public boolean isTriggered() {
        // Require at least 2 violations to get past noise due to initial conditions
        return violations >= 2;
    }
}
