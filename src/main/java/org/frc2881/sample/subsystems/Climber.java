package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.frc2881.sample.commands.ClimbWithJoystick;

import static org.frc2881.sample.Robot.robotMap;

public class Climber extends Subsystem {
    private final SpeedController ratchetMotor = robotMap.climberRatchetMotor;
    private final Solenoid brake = robotMap.climberLockPiston;  // TODO: remove, using a ratchet now

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimbWithJoystick());
    }

    public void climb(double speed) {
        ratchetMotor.set(Math.max(speed, 0));
    }

    public void applyBrake() {
        brake.set(true);
    }

    public void releaseBrake() {
        brake.set(false);
    }
}
