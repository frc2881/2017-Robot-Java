package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.sample.Robot;
import org.frc2881.sample.subsystems.Shooter;

public class ShootBalls extends Command {
    private static final double AGITATOR_DELAY = 1;  // seconds

    private final Shooter shooter = Robot.shooter;
    private final Timer timer = new Timer();

    public ShootBalls() {
        requires(shooter);
    }

    @Override
    protected void initialize() {
        timer.start();
    }

    @Override
    protected void execute() {
        shooter.startFlyWheels();
        if (timer.get() >= AGITATOR_DELAY) {
            shooter.startAgitator();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.stopFlyWheels();
        shooter.stopAgitator();
    }
}
