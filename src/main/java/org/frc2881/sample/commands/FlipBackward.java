package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.DriveTrain;
import org.frc2881.sample.subsystems.DriveTrain.Flip;

public class FlipBackward extends InstantCommand {
    private final DriveTrain driveTrain = Robot.driveTrain;

    @Override
    protected void initialize() {
        driveTrain.setFlip(Flip.BACKWARD);
    }
}
