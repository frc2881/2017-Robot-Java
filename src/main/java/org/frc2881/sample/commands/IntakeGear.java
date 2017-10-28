package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.GearPouch;
import org.frc2881.sample.utils.AmpMonitor;

/**
 * Gear pouch strategy for picking up a gear from the floor.
 * <p>
 * Don't wire this command directly to a controller.  Use {@link PickupGear} instead.
 */
public class IntakeGear extends Command {
    /** Give the gear pouch time to drop before starting the motor. */
    private static final double MOTOR_DELAY = 0.750;  // seconds

    private static final double CURRENT_MONITOR_DELAY = MOTOR_DELAY + 0.200;  // seconds

    /** Current value that indicates that a motor is likely stalled. */
    private static final double TURN_OFF_CURRENT = 18.5;  // amps

    private final GearPouch gearPouch = Robot.gearPouch;
    private final AmpMonitor ampMonitor = new AmpMonitor(TURN_OFF_CURRENT, gearPouch::getMotorCurrent);
    private boolean monitoringAmps;

    public IntakeGear() {
        requires(gearPouch);
    }

    @Override
    protected void initialize() {
        gearPouch.resetTimer();
        gearPouch.pouchDown();
        monitoringAmps = false;
    }

    @Override
    protected void execute() {
        if (gearPouch.getTimer() >= MOTOR_DELAY) {
            gearPouch.intakeGear();
        }
        if (!monitoringAmps && gearPouch.getTimer() >= CURRENT_MONITOR_DELAY) {
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
        gearPouch.stopGearMotor();
        gearPouch.pouchUp();
    }
}
