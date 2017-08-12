Sample Robot
============

## Install Instructions

Install the Talon SRX driver so that it's available to Java:
  
1. Download the CTRE Toolsuite Installer at http://www.ctr-electronics.com/hro.html#product_tabs_technical_resources.

2. Copy the CTRLLib.jar into your local Maven repository: 

```bash
mvn install:install-file -Dfile=Downloads/CTRE/java/lib/CTRLib.jar \
   -DgroupId=edu.wpi.first.ctrlib -DartifactId=ctrlib -Dversion=4.4.1.14 -Dpackaging=jar
```

(Note: this assumes Maven.  We'll probably update these instructions to use jars in ~/wpilib instead of Maven.)

## Java FAQ

The FRC roboRIO uses [Oracle Java SE Embedded 8](http://docs.oracle.com/javase/8/javase-embedded.htm) in the
"compact2" profile.
 
The Java virtual machine (JVM) has almost all the standard Java libraries from `java.lang`, `java.io`, `java.math`,
`java.net`, `java.nio`, `java.text`, `java.time`, `java.util` plus JDBC, RMI, XML support.   There are only a few
exceptions, eg. `java.lang.instrument`, `java.lang.management`, `java.security.acl`, `java.util.prefs`.
See the [Oracle Documentation](http://www.oracle.com/technetwork/java/embedded/resources/tech/compact-profiles-overview-2157132.html)
for details.  The WPILib code that configures the JVM profile is [here](https://github.com/wpilibsuite/java-installer/blob/master/src/main/java/edu/wpi/first/wpilib/javainstaller/controllers/CreateJreController.java).

Configure your Java IDE to call `javac` with the argument `-profile compact2` to ensure that you only use core
libraries available on the roboRIO.


## Lady Cans:

Inputs

 - Joysticks
    - Y axis L, Y axis R -> Tank Drive
    - R Button -> Drive Shifters, Drive Shifters 2
      - If either or both triggers pressed then shift up a gear, else shift down a gear
    
 - GamePad
    - Uses left joystick to control intake motors unless lock is engaged

Devices

 4-Motor Tank Drive
  - SPARK controllers
  - Open4Motor component in LabView
  - PWM0, PWM1, PWM2, PWM3

 Left Fly Wheel
   - Talon SRX controller
   - CAN 0
 
 Right Fly Wheel
   - Talon SRX controller
   - CAN 1

 IntakeMotor1
  - SPARK
  - PWM5

 IntakeMotor2
  - SPARK
  - PWM6

 CarouselMotor
  - SPARK
  - PWM4

 ScalingLockServo
  - Servo
  - PWM9
  - 100 (what does this mean?)

 CarouselPiston
  - Solenoid 4
  - Single
  - PneumaticsControlModule CAN address 11
  
 DriveShifters
  - Solenoid 0
  - Single
  - PneumaticsControlModule CAN address 11
  
 DriveShifters2
  - Solenoid 7
  - Single
  - PneumaticsControlModule CAN address 11
  
 IntakeShifters
  - Solenoid 1
  - Single
  - PneumaticsControlModule CAN address 11
  
 ScalingLockPiston
  - Solenoid 3
  - Single
  - PneumaticsControlModule CAN address 11

 GamePad
  - USB 0

 PS4
  - USB 2

 LeftDriveEncoder
  - A Channel: DIO 5
  - B Channel: DIO 6

 RightDriveEncoder
  - A Channel: DIO 7
  - B Channel: DIO 8
