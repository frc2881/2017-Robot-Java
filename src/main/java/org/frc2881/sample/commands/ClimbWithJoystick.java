package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;

public class ClimbWithJoystick extends Command {
    public ClimbWithJoystick() {
        requires(Robot.climber);
    }

    @Override
    protected void execute() {
        Robot.climber.climb(Robot.oi.climbJoystick.getAsDouble());
    }

    @Override
    protected boolean isFinished() {
        return false;  // Run forever unless interrupted
    }

    @Override
    protected void end() {
        Robot.climber.climb(0);
    }
}
