package org.frc2881.sample;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name. This provides
 * flexibility changing wiring, makes checking the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    private static final boolean TEST_BOARD = false;

    //
    // CAN bus addresses (0-63)

    public final PowerDistributionPanel pdp = new PowerDistributionPanel(10);

    private final int pneumaticsControlModule = 11;
    public final Compressor compressor = new Compressor(0);

    //
    // PWM Outputs (0-9)

    private final SpeedController rearLeftMotor = new Spark(1);
    private final SpeedController rearRightMotor = new Spark(TEST_BOARD ? 0 : 9);
    private final SpeedController frontLeftMotor = new Spark(3);
    private final SpeedController frontRightMotor = new Spark(2);
    public final RobotDrive robotDrive = new RobotDrive(
            frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    public final SpeedController climberRatchetMotor = TEST_BOARD ? new CANTalon(0) : new Spark(7);

    public final SpeedController gearIntakeMotor = TEST_BOARD ? new CANTalon(1) : new Spark(8);
    public final int gearIntakeMotorPdpChannel = 3;

    //
    // Pneumatics Outputs (0-7)

    public final Solenoid driveShifter1 = new Solenoid(pneumaticsControlModule, 0);
    public final Solenoid driveShifter2 = new Solenoid(pneumaticsControlModule, 3);
    public final Solenoid gearIntakePiston = new Solenoid(pneumaticsControlModule, 2);

    //
    // Additional configuration

    public RobotMap() {
        rearRightMotor.setInverted(true);
        frontRightMotor.setInverted(true);
    }
}
