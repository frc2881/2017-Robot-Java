package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.Climber;

import java.util.function.DoubleSupplier;

public class ClimbWithJoystick extends Command {
    private final DoubleSupplier joystick = Robot.oi.climbJoystick;
    private final Climber climber = Robot.climber;

    public ClimbWithJoystick() {
        requires(climber);
    }

    @Override
    protected void execute() {
        climber.climb(joystick.getAsDouble());
    }

    @Override
    protected boolean isFinished() {
        return false;  // Run forever unless interrupted
    }

    @Override
    protected void end() {
        climber.climb(0);
    }
}
