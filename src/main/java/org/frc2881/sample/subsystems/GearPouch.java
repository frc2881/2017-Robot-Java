package org.frc2881.sample.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.frc2881.sample.Robot.robotMap;

@SuppressWarnings("FieldCanBeLocal")
public class GearPouch extends Subsystem {
    private final PowerDistributionPanel pdp = robotMap.pdp;
    private final int motorPdpChannel = robotMap.gearIntakeMotorPdpChannel;
    private final SpeedController motor = robotMap.gearIntakeMotor;
    private final Solenoid piston = robotMap.gearIntakePiston;
    private final Timer timer = new Timer();

    @Override
    protected void initDefaultCommand() {
        // No default command
    }

    public void resetTimer() {
        timer.reset();
        timer.start();
    }

    public double getTimer() {
        return timer.get();
    }

    public void pouchDown() {
        piston.set(true);
    }

    public void pouchUp() {
        piston.set(false);
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

    /** The log method puts interesting information to the SmartDashboard. */
    public void log() {
        SmartDashboard.putNumber("Gear Intake Motor", motor.get());
        SmartDashboard.putBoolean("Gear Intake Piston", piston.get());
        SmartDashboard.putNumber("Gear Intake Current", getMotorCurrent());
        SmartDashboard.putNumber("Gear Intake Timer", getTimer());
    }
}
