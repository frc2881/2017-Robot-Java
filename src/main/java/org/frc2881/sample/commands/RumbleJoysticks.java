package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.TimedCommand;

import static org.frc2881.sample.Robot.oi;

/**
 * Buzzes both controllers for a brief period of time.
 */
public class RumbleJoysticks extends TimedCommand {
    private static final double DURATION = 1.0;  // seconds

    private final GenericHID[] controllers = {oi.driver, oi.operator};

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
        for (GenericHID controller : controllers) {
            controller.setRumble(RumbleType.kRightRumble, level);
        }
    }
}
