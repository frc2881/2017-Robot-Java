package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.DriveTrain.Flip;

public class FlipForward extends InstantCommand {
    @Override
    protected void initialize() {
        Robot.driveTrain.setFlip(Flip.FORWARD);
    }
}
