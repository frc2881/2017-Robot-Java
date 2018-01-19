package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.frc2881.sample.Robot;

/**
 * Buzzes both controllers for a brief period of time.
 */
public class RumbleJoysticks extends TimedCommand {
    private static final double DURATION = 2.0;  // seconds

    public RumbleJoysticks() {
        super(DURATION);
    }

    @Override
    protected void initialize() {
        setRumble(1);
    }

    @Override
    protected void end() {
        setRumble(0);
    }

    private void setRumble(double level) {
        Robot.oi.driver.setRumble(RumbleType.kRightRumble, level);
    }
}
