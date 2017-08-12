package org.frc2881.sample;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
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

    //
    // CAN bus addresses (0-63)

    public final PowerDistributionPanel pdp = new PowerDistributionPanel(10);
    public final CANSpeedController leftFlyWheel = new CANTalon(0);
    public final CANSpeedController rightFlyWheel = new CANTalon(1);

    private final int pneumaticsControlModule = 11;
    public final Compressor compressor = new Compressor(0);

    //
    // PWM Outputs (0-9)

    private final SpeedController rearLeftMotor = new Spark(1);
    private final SpeedController rearRightMotor = new Spark(9);
    private final SpeedController frontLeftMotor = new Spark(3);
    private final SpeedController frontRightMotor = new Spark(2);
    public final RobotDrive robotDrive = new RobotDrive(
            frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    public final SpeedController climberRatchetMotor = new Spark(7);

    public final SpeedController gearIntakeMotor = new Spark(8);
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
        leftFlyWheel.setInverted(true);
        rightFlyWheel.setInverted(true);
    }
}
