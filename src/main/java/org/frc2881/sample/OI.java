package org.frc2881.sample;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import org.frc2881.sample.commands.DriveWithJoysticks;
import org.frc2881.sample.commands.PickupGear;
import org.frc2881.sample.commands.PlaceGear;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command
 * groups that allow control of the robot.
 */
public class OI {

    //
    // Driver control

    /** PS4 controller used by the driver. */
    public final DualShock4Controller driver = new DualShock4Controller(0, 1);

    /** HID with the left joystick for the driver, wired to tank drive. */
    public final GenericHID driverLeft = driver;
    /** HID with the right joystick for the driver, wired to tank drive. */
    public final GenericHID driverRight = driver;

    public final Button highGear = buttonFrom(() -> driver.getBumper(Hand.kRight));

    //
    // Mechanism Operator control

    /** GamePad controller used by the mechanism operator. */
    public final XboxController operator = new XboxController(2);

    public final DoubleSupplier climbJoystick = () -> driver.getTriggerAxis(Hand.kRight);

    public final Button pickupGear = buttonFrom(() -> driver.getTrigger(Hand.kLeft));
    public final Button placeGear = buttonFrom(driver::getBlueXButton);

    //
    // Bind controls to commands

    public void initCommands() {
        highGear.whileHeld(new DriveWithJoysticks(true));

        // Holding down gearIntake runs intake, releasing it stops intake.  Release the button when
        // the gamepad rumbles to indicate a gear is in the pouch.
        pickupGear.whileHeld(new PickupGear());

        // Hold down the gearEject button, drive away, release the button (maybe too simple, needs testing)
        placeGear.whileHeld(new PlaceGear());
    }

    private Button buttonFrom(BooleanSupplier fn) {
        return new Button() {
            @Override
            public boolean get() {
                return fn.getAsBoolean();
            }
        };
    }
}
