package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc2881.sample.commands.DriveWithJoysticks;

import static org.frc2881.sample.Robot.robotMap;

public class DriveTrain extends Subsystem {
    public enum Flip {
        FORWARD,
        BACKWARD,
    }

    // Joysticks don't center perfectly.  Treat any input <= 0.1 as "off".
    private static final double DEADBAND = 0.1;

    private final RobotDrive drive = robotMap.robotDrive;
    private final Solenoid shifter1 = robotMap.driveShifter1;
    private final Solenoid shifter2 = robotMap.driveShifter2;
    private Flip flip = Flip.FORWARD;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoysticks());
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        if (flip == Flip.BACKWARD) {
            // Flip left & right, reverse sign
            double tempLeftSpeed = leftSpeed;
            leftSpeed = -rightSpeed;
            rightSpeed = -tempLeftSpeed;
        }

        // Use 'squaredInputs' to get better control at low speed
        drive.tankDrive(adj(leftSpeed), adj(rightSpeed), true);
    }

    public void setFlip(Flip flip) {
        this.flip = flip;
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

    /** The log method puts interesting information to the SmartDashboard. */
    public void log() {
//        SmartDashboard.putNumber("Left Distance", leftEncoder.getDistance());
//        SmartDashboard.putNumber("Right Distance", rightEncoder.getDistance());
//        SmartDashboard.putNumber("Left Speed", leftEncoder.getRate());
//        SmartDashboard.putNumber("Right Speed", rightEncoder.getRate());
//        SmartDashboard.putNumber("Gyro", gyro.getAngle());
    }
}
