package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
    private boolean enabled;

    @Override
    protected void initDefaultCommand() {
        // No default command
    }

    public boolean isEnabled() {
        // To give the compressor time to build initial pressure, we may not interact with pistons until 10 seconds
        // after the robot has been turned on.  A smarter version of this might poll the compressor pressure switch
        // valve and set enabled based on sufficient pressure rather than elapsed time.  Alternatively, the pneumatics
        // example project uses an analog pressure sensor for the same purpose.
        if (!enabled && Timer.getFPGATimestamp() >= 10) {
            enabled = true;
        }
        return enabled;
    }

    /** The log method puts interesting information to the SmartDashboard. */
    public void log() {
    }
}
