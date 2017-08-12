package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.frc2881.sample.Robot.robotMap;

public class Shooter extends Subsystem {
    private final SpeedController leftFlyWheel = robotMap.leftFlyWheel;
    private final SpeedController rightFlyWheel = robotMap.rightFlyWheel;
    private final SpeedController agitatorMotor = robotMap.agitatorMotor;

    @Override
    protected void initDefaultCommand() {
        // No default command
    }

    public void startFlyWheels() {
        setFlyWheels(1);
    }

    public void stopFlyWheels() {
        setFlyWheels(0);
    }

    private void setFlyWheels(double speed) {
        leftFlyWheel.set(speed);
        rightFlyWheel.set(speed);
    }

    public void startAgitator() {
        agitatorMotor.set(0.5);
    }

    public void stopAgitator() {
        agitatorMotor.set(0);
    }
}
