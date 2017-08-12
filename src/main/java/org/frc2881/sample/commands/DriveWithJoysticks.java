package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.DriveTrain;

/**
 * Standard tank drive w/a pair of pair of joysticks on a gamepad-style controller.
 */
public class DriveWithJoysticks extends Command {
    private final GenericHID joystickLeft = Robot.oi.driverLeft;
    private final GenericHID joystickRight = Robot.oi.driverRight;
    private final DriveTrain driveTrain = Robot.driveTrain;
    private final boolean highGear;

    public DriveWithJoysticks() {
        this(false);
    }

    public DriveWithJoysticks(boolean highGear) {
        super(String.format("DriveIn%sGear", highGear ? "High" : "Low"));
        requires(driveTrain);
        this.highGear = highGear;
    }

    @Override
    protected void initialize() {
        if (highGear) {
            driveTrain.highGear();
        } else {
            driveTrain.lowGear();
        }
    }

    @Override
    protected void execute() {
        double left = joystickLeft.getY(Hand.kLeft);
        double right = joystickRight.getY(Hand.kRight);
        driveTrain.tankDrive(left, right);
    }

    @Override
    protected boolean isFinished() {
        return false;  // Run forever unless interrupted
    }

    @Override
    protected void end() {
        driveTrain.lowGear();
    }
}
