package org.frc2881.sample;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name. This provides
 * flexibility changing wiring, makes checking the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    //
    // CAN bus addresses (0-63)

    public final PowerDistributionPanel pdp = new PowerDistributionPanel(10);

    private final int pneumaticsControlModule = 11;

    //
    // PWM Outputs (0-9)

    private final Spark rearLeftMotor = new Spark(3);
    private final Spark rearRightMotor = new Spark(4);
    private final Spark frontLeftMotor = new Spark(2);
    private final Spark frontRightMotor = new Spark(5);
    private final SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);
    private final SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, rearRightMotor);
    public final DifferentialDrive robotDrive = new DifferentialDrive(leftMotors, rightMotors);
    public final Encoder leftEncoder = new Encoder(5, 6, false, CounterBase.EncodingType.k2X);
    public final Encoder rightEncoder = new Encoder(7, 8, false, CounterBase.EncodingType.k2X);
    public final AHRS navX = new AHRS(SPI.Port.kMXP);

    public final Spark climberRatchetMotor = new Spark(6);

    public final Spark gearIntakeMotor = new Spark(1);
    public final int gearIntakeMotorPdpChannel = 3;

    //
    // Pneumatics Outputs (0-7)

    public final Solenoid driveShifter1 = new Solenoid(pneumaticsControlModule, 0);
    public final Solenoid driveShifter2 = new Solenoid(pneumaticsControlModule, 3);
    public final Solenoid gearIntakePiston = new Solenoid(pneumaticsControlModule, 2);

    //
    // Additional configuration

    public RobotMap() {
        rearRightMotor.setInverted(false);
        frontRightMotor.setInverted(false);

        frontLeftMotor.setName("DriveTrain", "Front Left Motor");
        frontRightMotor.setName("DriveTrain", "Front Right Motor");
        rearLeftMotor.setName("DriveTrain", "Rear Left Motor");
        rearRightMotor.setName("DriveTrain", "Rear Right Motor");
        leftMotors.setName("DriveTrain", "Left Motors");
        rightMotors.setName("DriveTrain", "Right Motors");
        driveShifter1.setName("DriveTrain", "Shifter 1");
        driveShifter2.setName("DriveTrain", "Shifter 2");
        leftEncoder.setName("DriveTrain", "Left Encoder");
        rightEncoder.setName("DriveTrain", "Right Encoder");
        navX.setName("DriveTrain", "NavX");

        climberRatchetMotor.setName("Climber", "Climber Motor");

        gearIntakeMotor.setName("GearPouch", "Intake Motor");
    }
}
