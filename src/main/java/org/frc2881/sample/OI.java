package org.frc2881.sample;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import org.frc2881.sample.commands.DriveWithJoysticks;
import org.frc2881.sample.commands.FlipBackward;
import org.frc2881.sample.commands.FlipForward;
import org.frc2881.sample.commands.PickupGear;
import org.frc2881.sample.commands.ScoreGear;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command
 * groups that allow control of the robot.
 */
public class OI {

    // The FRC Driver Station should show:
    //  0 Controller (Gamepad F310)
    //  1 Wireless Controller
    //  2 Controller (XBOX 360 For Windows)

    //
    // Driver control

    /** PS4 controller used by the driver. */
    public final DualShock4Controller driver = new DualShock4Controller(2, 0);  // use (1, 2) w/InputMapper to get rumble feedback, (1) otherwise

    /** HID with the left joystick for the driver, wired to tank drive. */
    public final GenericHID driverLeft = driver;
    /** HID with the right joystick for the driver, wired to tank drive. */
    public final GenericHID driverRight = driver;

    public final Button highGear = buttonFrom(() -> driver.getBumper(Hand.kRight));

    public final Button goForward = buttonFrom(driver::getRedCircleButton);
    public final Button goBackward = buttonFrom(driver::getPinkSquareButton);

    public final Button pickupGear = buttonFrom(() -> driver.getTrigger(Hand.kRight));
    public final Button scoreGear = buttonFrom(() -> driver.getBumper(Hand.kLeft));

    //
    // Mechanism Operator control

    /** GamePad controller used by the mechanism operator. */
    public final XboxController operator = new XboxController(1);

    public final DoubleSupplier climbJoystick = () -> Math.max(0, -operator.getY(Hand.kRight));

    //
    // Bind controls to commands

    public void initCommands() {
        highGear.whileHeld(new DriveWithJoysticks(true));

        // Holding down gearIntake runs intake, releasing it stops intake.  Release the button when
        // the gamepad rumbles to indicate a gear is in the pouch.
        pickupGear.whileHeld(new PickupGear());

        // Hold down the gearEject button, drive away, release the button (maybe too simple, needs testing)
        scoreGear.whileHeld(new ScoreGear());

        // Swap the idea of front-to-back
        goForward.whenActive(new FlipForward());
        goBackward.whenActive(new FlipBackward());
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
