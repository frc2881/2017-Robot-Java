package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.GearPouch;

/**
 * Gear pouch strategy for hanging a gear on the peg.
 */
public class PlaceGear extends Command {
    private final GearPouch gearPouch = Robot.gearPouch;

    public PlaceGear() {
        requires(gearPouch);
    }

    @Override
    protected void initialize() {
        gearPouch.pouchDown();
    }

    @Override
    protected void execute() {
        gearPouch.ejectGear();
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
