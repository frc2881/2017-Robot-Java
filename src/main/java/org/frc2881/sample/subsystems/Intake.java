package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.frc2881.sample.Robot.robotMap;

public class Intake extends Subsystem {
    private final SpeedController intakeMotor1 = robotMap.intakeMotor1;
    private final SpeedController intakeMotor2 = robotMap.intakeMotor2;
    private final Solenoid shifter = robotMap.intakeShifter;

    @Override
    protected void initDefaultCommand() {
        // No default command
    }

    public void lowGear() {
        shifter.set(false);
    }

    public void highGear() {
        shifter.set(true);
    }
}
