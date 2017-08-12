package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.frc2881.sample.commands.DriveWithJoysticks;

import static org.frc2881.sample.Robot.robotMap;

public class DriveTrain extends Subsystem {
    // Joysticks don't center perfectly.  Treat any input <= 0.1 as "off".
    private static final double DEADBAND = 0.1;

    private final RobotDrive drive = robotMap.robotDrive;
    private final Solenoid shifter1 = robotMap.driveShifter1;
    private final Solenoid shifter2 = robotMap.driveShifter2;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoysticks());
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        // Use 'squaredInputs' to get better control at low speed
        drive.tankDrive(adj(leftSpeed), adj(rightSpeed), true);
    }

    private double adj(double speed) {
        return Math.abs(speed) <= DEADBAND ? 0.0 : speed;
    }

    public void lowGear() {
        setShifters(false);
    }

    public void highGear() {
        setShifters(true);
    }

    private void setShifters(boolean on) {
        shifter1.set(on);
        shifter2.set(on);
    }
}
