package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.utils.AmpMonitor;

/**
 * Gear pouch strategy for picking up a gear from the floor.
 * <p>
 * Don't wire this command directly to a controller.  Use {@link PickupGear} instead.
 */
public class IntakeGear extends Command {
    /** Current value that indicates that a motor is likely stalled. */
    private static final double TURN_OFF_CURRENT = 18.5;  // amps

    private final AmpMonitor ampMonitor = new AmpMonitor(TURN_OFF_CURRENT, Robot.gearPouch::getMotorCurrent);
    private boolean monitoringAmps;

    public IntakeGear() {
        requires(Robot.gearPouch);
    }

    @Override
    protected void initialize() {
        Robot.gearPouch.resetTimer();
        Robot.gearPouch.pouchDown();
        monitoringAmps = false;
    }

    @Override
    protected void execute() {
        // After a short delay, start the intake motor
        if (Robot.gearPouch.getTimer() >= 0.750) {
            Robot.gearPouch.intakeGear();
        }
        // Let the motor get started then start monitoring the current used by the motor.
        if (!monitoringAmps && Robot.gearPouch.getTimer() >= 0.750 + 0.200) {
            ampMonitor.reset();
            monitoringAmps = true;
        }
    }

    @Override
    protected boolean isFinished() {
        // If the current is high and not dropping then the motor is probably not turning because it has pulled
        // a gear all the way into the gear pouch.
        return monitoringAmps && ampMonitor.checkTriggered();
    }

    @Override
    protected void end() {
        Robot.gearPouch.stopGearMotor();
        Robot.gearPouch.pouchUp();
    }
}
