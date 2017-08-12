package org.frc2881.sample.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PickupGear extends CommandGroup {

    public PickupGear() {
        addSequential(new IntakeGear());
        addSequential(new RumbleJoysticks());
    }
}
