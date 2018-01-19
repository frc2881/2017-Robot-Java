package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;

/**
 * Gear pouch strategy for hanging a gear on the peg.
 */
public class ScoreGear extends Command {
    public ScoreGear() {
        requires(Robot.gearPouch);
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.gearPouch.resetTimer();
    }

    @Override
    protected void execute() {
        // Run the pouch motor backward
        Robot.gearPouch.ejectGear();

        // After a short delay, drop the pouch to leave the gear hanging on the peg
        if (Robot.gearPouch.getTimer() >= 0.250) {
            Robot.gearPouch.pouchDown();

            // Move away from the gear peg for half a second to clear the peg
            if (Robot.gearPouch.getTimer() < 0.250 + 0.500) {
                Robot.driveTrain.driveStraight(0.8);
            } else {
                Robot.driveTrain.stop();
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.gearPouch.stopGearMotor();
        Robot.gearPouch.pouchUp();
    }
}
