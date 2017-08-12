package org.frc2881.sample;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc2881.sample.commands.DrivePastLine;
import org.frc2881.sample.commands.InitializePneumatics;
import org.frc2881.sample.subsystems.Climber;
import org.frc2881.sample.subsystems.DriveTrain;
import org.frc2881.sample.subsystems.GearPouch;
import org.frc2881.sample.subsystems.Intake;
import org.frc2881.sample.subsystems.Pneumatics;
import org.frc2881.sample.subsystems.Shooter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the build.properties file in the project.
 */
public class Robot extends IterativeRobot {
    private static final boolean SMART_DASHBOARD = false;

    public static RobotMap robotMap;
    public static OI oi;

    // Subsystems
    public static Pneumatics pneumatics;
    public static DriveTrain driveTrain;
    public static GearPouch gearPouch;
    public static Intake intake;
    public static Climber climber;
    public static Shooter shooter;

    private Supplier<Command> autonomousChooser;
    private Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Setup devices
        robotMap = new RobotMap();

        // Setup subsystems composed of devices
        pneumatics = register(new Pneumatics());
        driveTrain = register(new DriveTrain());
        gearPouch = register(new GearPouch());
        intake = register(new Intake());
        climber = register(new Climber());
        shooter = register(new Shooter());

        // Setup user controls & wire them to subsystems
        oi = new OI();

        // Which program should be run in autonomous mode?
        autonomousChooser = configureAutonomousMenu();

        // Send USB webcam video to the driver Dashboard
        //CameraServer.getInstance().startAutomaticCapture();
        //CameraServer.getInstance().addAxisCamera("axis-camera.local");

        // Once the compressor is ready, move all the pistons to the desired initial state
        new InitializePneumatics().start();
    }

    /** Report component status back to the dashboard. */
    private <T extends NamedSendable> T register(T component) {
        SmartDashboard.putData(component);
        return component;
    }

    /**
     * Interfaces with the Java SmartDashboard or LabVIEW Dashboard to choose the command to run in autonomous mode.
     */
    private Supplier<Command> configureAutonomousMenu() {
        // Build the menu of commands.  If LabVIEW Dashboard then it must be programmed with the same menu.
        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("Drive Past Line", new DrivePastLine());
        commands.put("Disabled", new InstantCommand());

        // Assume the first menu option is the default
        Map.Entry<String, Command> defaultEntry = commands.entrySet().iterator().next();

        if (SMART_DASHBOARD) {
            // Java SmartDashboard
            SendableChooser<Command> chooser = new SendableChooser<>();
            commands.forEach((name, command) -> {
                if (name.equals(defaultEntry.getKey())) {
                    chooser.addDefault(name, command);
                } else {
                    chooser.addObject(name, command);
                }
            });
            SmartDashboard.putData("Auto mode", chooser);
            return chooser::getSelected;
        } else {
            // LabVIEW Dashboard options are configured in LabVIEW
            return () -> {
                String selected = SmartDashboard.getString("Auto Selector", null);
                return commands.getOrDefault(selected, defaultEntry.getValue());
            };
        }
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = autonomousChooser.get();
        autonomousCommand.start();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}
