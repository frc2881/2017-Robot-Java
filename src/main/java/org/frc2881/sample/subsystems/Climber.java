package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.frc2881.sample.commands.ClimbWithJoystick;

import static org.frc2881.sample.Robot.robotMap;

public class Climber extends Subsystem {
    private final SpeedController ratchetMotor = robotMap.climberRatchetMotor;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimbWithJoystick());
    }

    public void climb(double speed) {
        ratchetMotor.set(Math.max(speed, 0));
    }
}
