package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command {
    @Override
    protected boolean isFinished() {
        return false;
    }
}
