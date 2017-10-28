package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.GearPouch;

/**
 * Gear pouch strategy for hanging a gear on the peg.
 */
public class ScoreGear extends Command {
    private final GearPouch gearPouch = Robot.gearPouch;

    public ScoreGear() {
        requires(gearPouch);
    }

    @Override
    protected void initialize() {
        gearPouch.resetTimer();
    }

    @Override
    protected void execute() {
        // Run the motor backward
        gearPouch.ejectGear();

        // After a short delay, drop the pouch to leave the gear hanging on the peg
        if (gearPouch.getTimer() >= 0.250) {
            gearPouch.pouchDown();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        gearPouch.stopGearMotor();
        gearPouch.pouchUp();
    }
}
