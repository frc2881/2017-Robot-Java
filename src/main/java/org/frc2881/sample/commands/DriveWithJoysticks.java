package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;

/**
 * Standard tank drive w/a pair of pair of joysticks on a gamepad-style controller.
 */
public class DriveWithJoysticks extends Command {
    private final boolean highGear;

    public DriveWithJoysticks() {
        this(false);
    }

    public DriveWithJoysticks(boolean highGear) {
        super(String.format("DriveIn%sGear", highGear ? "High" : "Low"));
        requires(Robot.driveTrain);
        this.highGear = highGear;
    }

    @Override
    protected void initialize() {
        if (highGear) {
            Robot.driveTrain.highGear();
        } else {
            Robot.driveTrain.lowGear();
        }
    }

    @Override
    protected void execute() {
        double left = Robot.oi.driverLeft.getY(Hand.kLeft);
        double right = Robot.oi.driverRight.getY(Hand.kRight);
        Robot.driveTrain.tankDrive(left, right);
    }

    @Override
    protected boolean isFinished() {
        return false;  // Run forever unless interrupted
    }

    @Override
    protected void end() {
        Robot.driveTrain.lowGear();
    }
}
