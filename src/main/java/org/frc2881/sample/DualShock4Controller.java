package org.frc2881.sample;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.hal.HAL;

/**
 * Handles input from a Sony PS4 DualShock Wireless Controller.
 *
 * <p>Team 2881 uses 2 types of controllers in 2017: the PS4 DualShock4 controller and the Logitech Gamepad F310.
 * This class is for the DualShock4 only.  The Logitech Gamepad is very similar to an Xbox controller so use the
 * {@link XboxController} class for the Gamepad.
 *
 * <p>Note, for better reliability, don't use the wireless connection.  Plug the controller into the driver station
 * using a micro USB cable.</p>
 */
public class DualShock4Controller extends XboxController {
    public static final int NONE = -1;

    private final int virtualXboxPort;
    private short m_leftRumble;
    private short m_rightRumble;

    /** Constructor for a controller without support for rumble feedback. */
    public DualShock4Controller(int port) {
        this(port, NONE);
    }

    /** Constructor that supports rumble feedback using the InputMapper tool (see https://inputmapper.com). */
    public DualShock4Controller(int port, int virtualXboxPort) {
        super(port);
        this.virtualXboxPort = virtualXboxPort;
    }

    @Override
    public double getX(Hand hand) {
        return getRawAxis(hand == Hand.kLeft ? 0 : 2);
    }

    @Override
    public double getY(Hand hand) {
        return getRawAxis(hand == Hand.kLeft ? 1 : 5);
    }

    /** Lower trigger on the front of the controller.  This is the digital (on/off) version of {@link #getTriggerAxis(Hand)}. */
    @Override
    public boolean getTrigger(Hand hand) {
        return getRawButton(hand == Hand.kLeft ? 7 : 8);
    }

    /** Lower trigger on the front of the controller.  This is the analog version of {@link #getTrigger(Hand)}. */
    @Override
    public double getTriggerAxis(Hand hand) {
        // Note that the raw value ranges from -1 to 1 which means that the default, at rest, is -1.  That's
        // undesirable when wired directly up to a motor, so this rescales it to range from 0 to 1 (which is what
        // the equivalent Xbox controller does).
        return (getRawAxis(hand == Hand.kLeft ? 3 : 4) + 1) / 2;  // adjust range from -1->1 to 0->1
    }

    /** Upper trigger button on the front of the controller. */
    @Override
    public boolean getBumper(Hand hand) {
        return super.getBumper(hand);
    }

    /** Button built into the joystick (push down on the joystick). */
    @Override
    public boolean getStickButton(Hand hand) {
        return getRawButton(hand == Hand.kLeft ? 11 : 12);
    }

    /** Left.  Generally means "Menu" (or documents) on PS4. */
    public boolean getPinkSquareButton() {
        return getRawButton(1);
    }

    /** Right.  Generally means "Yes" on PS4. */
    public boolean getRedCircleButton() {
        return getRawButton(3);
    }

    /** Down.  Generally means "No" on PS4. */
    public boolean getBlueXButton() {
        return getRawButton(2);
    }

    /** Up.  Generally means "Viewpoint" (or head or direction) on PS4. */
    public boolean getGreenTriangleButton() {
        return getRawButton(4);
    }

    /** Small button to the left of the controller trackpad. */
    public boolean getShareButton() {
        return getRawButton(9);
    }

    /** Small button to the right of the controller trackpad. */
    public boolean getOptionsButton() {
        return getRawButton(10);
    }

    /** Button built into the trackpad. */
    public boolean getTrackpadButton() {
        return getRawButton(14);
    }

    /** Down. Maps the Xbox "A" button function to the PS4 button at the same location. */
    @Override
    public boolean getAButton() {
        return getBlueXButton();
    }

    /** Right. Maps the Xbox "B" button function to the PS4 button at the same location. */
    @Override
    public boolean getBButton() {
        return getRedCircleButton();
    }

    /** Left. Maps the Xbox "X" button function to the PS4 button at the same location. */
    @Override
    public boolean getXButton() {
        return getPinkSquareButton();
    }

    /** Up. Maps the Xbox "Y" button function to the PS4 button at the same location. */
    @Override
    public boolean getYButton() {
        return getGreenTriangleButton();
    }

    /** Maps the Xbox "Back" button function to the PS4 button at the same location. */
    @Override
    public boolean getBackButton() {
        return getShareButton();
    }

    /** Maps the Xbox "Start" button function to the PS4 button at the same location. */
    @Override
    public boolean getStartButton() {
        return getOptionsButton();
    }

    /**
     * Returns an angle (in degrees) representing the button or buttons pressed in the cluster of 4 "POV" buttons on
     * the left-hand-side of the controller.  Straight up is 0 degrees, proceeds clockwise in increments of 45.
     */
    @Override
    public int getPOV(int pov) {
        return super.getPOV(pov);
    }

    @Override
    public void setRumble(RumbleType type, double value) {
        if (virtualXboxPort == NONE) {
            return;  // Rumble support requires a virtual Xbox-type device on the driver station
        }
        if (value < 0) {
            value = 0;
        } else if (value > 1) {
            value = 1;
        }
        if (type == RumbleType.kLeftRumble) {
            m_leftRumble = (short) (value * 65535);
        } else {
            m_rightRumble = (short) (value * 65535);
        }
        HAL.setJoystickOutputs((byte) virtualXboxPort, 0, m_leftRumble, m_rightRumble);
    }
}
