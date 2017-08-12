package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.frc2881.sample.Robot.robotMap;

@SuppressWarnings("FieldCanBeLocal")
public class GearPouch extends Subsystem {
    private final PowerDistributionPanel pdp = robotMap.pdp;
    private final int motorPdpChannel = robotMap.gearIntakeMotorPdpChannel;
    private final SpeedController motor = robotMap.gearIntakeMotor;
    private final Solenoid piston = robotMap.gearIntakePiston;

    @Override
    protected void initDefaultCommand() {
        // No default command
    }

    public void pouchDown() {
        piston.set(false);
    }

    public void pouchUp() {
        piston.set(true);
    }

    public void intakeGear() {
        motor.set(1);
    }

    public void ejectGear() {
        motor.set(-0.5);
    }

    public void stopGearMotor() {
        motor.set(0);
    }

    public double getMotorCurrent() {
        return pdp.getCurrent(motorPdpChannel);
    }
}
