package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import org.frc2881.sample.Robot;
import org.frc2881.sample.utils.AmpMonitor;

import static org.frc2881.sample.Robot.robotMap;

/**
 * Autonomous command drives forward for a few seconds at half speed in low gear.
 */
public class DrivePastLine extends TimedCommand {
    private static final double DURATION = 5;  // seconds
    private static final double CURRENT_SAFETY_THRESHOLD = 20;  // current in amps

    private final AmpMonitor ampMonitor;

    public DrivePastLine() {
        super(DURATION);
        requires(Robot.driveTrain);

        // Stop the robot if it seems like we've hit something
        ampMonitor = new AmpMonitor(CURRENT_SAFETY_THRESHOLD, robotMap.pdp::getTotalCurrent);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.lowGear();
        ampMonitor.reset();
    }

    @Override
    protected void execute() {
        Robot.driveTrain.tankDrive(0.5, 0.5);
    }

    @Override
    protected boolean isFinished() {
        return super.isFinished() || ampMonitor.checkTriggered();
    }
}
