package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.Pneumatics;

public class InitializePneumatics extends Command {
    private final Pneumatics pneumatics = Robot.pneumatics;
    private boolean finished;

    public InitializePneumatics() {
        // Require every subsystem that contains a solenoid
        requires(Robot.driveTrain);
        requires(Robot.gearPouch);
        requires(Robot.climber);
        // Lock all the subsystems until pneumatics control has been enabled and pistons set to their initial position
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        finished = false;
    }

    @Override
    protected void execute() {
        // Wait for pressure to build
        if (!pneumatics.isEnabled()) {
            return;
        }

        // Set initial state and release subsystem locks
        Robot.driveTrain.lowGear();
        Robot.gearPouch.pouchUp();
        finished = true;
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
}
