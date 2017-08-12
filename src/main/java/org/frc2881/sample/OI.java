package org.frc2881.sample;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.frc2881.sample.commands.PlaceGear;
import org.frc2881.sample.commands.DriveWithJoysticks;
import org.frc2881.sample.commands.PickupGear;
import org.frc2881.sample.commands.ShootBalls;

import java.util.function.DoubleSupplier;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command
 * groups that allow control of the robot.
 */
public class OI {

    //
    // Button map.  Based on XboxController, probably (definitely!) doesn't match our actual controllers.

    private static final int BUTTON_A = 1;
    private static final int BUTTON_B = 2;
    private static final int BUTTON_X = 3;
    private static final int BUTTON_Y = 4;
    private static final int BUTTON_BUMPER_LEFT = 5;
    private static final int BUTTON_BUMPER_RIGHT = 6;
    private static final int BUTTON_BACK = 7;
    private static final int BUTTON_START = 8;
    private static final int BUTTON_STICK_LEFT = 9;
    private static final int BUTTON_STICK_RIGHT = 10;

    //
    // Driver control

    /** PS4 controller used by the driver. */
    public final XboxController driver = new XboxController(2);

    /** HID with the left joystick for the driver, wired to tank drive. */
    public final GenericHID driverLeft = driver;
    /** HID with the right joystick for the driver, wired to tank drive. */
    public final GenericHID driverRight = driver;

    public final Button highGear = new JoystickButton(driver, BUTTON_BUMPER_LEFT);  // TODO: set button

    //
    // Mechanism Operator control

    /** GamePad controller used by the mechanism operator. */
    public final XboxController operator = new XboxController(0);

    public final DoubleSupplier climbJoystick = () -> operator.getY(Hand.kRight);

    public final Button pickupGear = new JoystickButton(operator, BUTTON_BUMPER_LEFT);  // TODO: set button
    public final Button placeGear = new JoystickButton(operator, BUTTON_BUMPER_RIGHT);  // TODO: set button

    public final Button shootBalls = new JoystickButton(operator, BUTTON_A);  // TODO: set button

    //
    // Bind controls to commands

    public OI() {
        highGear.whileHeld(new DriveWithJoysticks(true));

        // Holding down gearIntake runs intake, releasing it stops intake.  Release the button when
        // the gamepad rumbles to indicate a gear is in the pouch.
        pickupGear.whileHeld(new PickupGear());

        // Hold down the gearEject button, drive away, release the button (maybe too simple, needs testing)
        placeGear.whileHeld(new PlaceGear());

        shootBalls.whileHeld(new ShootBalls());
    }
}
