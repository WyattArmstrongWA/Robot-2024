package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kBack.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton intakeOn = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton outtakeOn = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton shooterOn = new JoystickButton(driver, XboxController.Button.kX.value);
    //private final JoystickButton shootinOn = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton wristUpOn = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton wristDownOn = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    /* Subsystems */
    
    private final Swerve s_Swerve = new Swerve();

    private final TalonFX intake = new TalonFX(17);
    

    private void intakeOnCommand() {
        intake.set(0.95);
        
    }
     private void intakeOffCommand() {
        intake.set(0);
        
    }
    private void outakeOnCommand() {
        intake.set(-0.75);
    }
     private void outakeOffCommand() {
        intake.set(0);
    }

    
    private final TalonFX shooter = new TalonFX(13);
    private final Spark shooterLeft = new Spark(20);
    private final Spark shooterRight = new Spark(21);

    private void shooterOnCommand() {
        shooter.set(0.25);
        shooterLeft.set(0.25);
        shooterRight.set(0.25);
    }
     private void shooterOffCommand() {
        shooter.set(0);
        shooterLeft.set(0);
        shooterRight.set(0);
    }
  

    private final TalonFX wrist = new TalonFX(49);

    private void wristUpOnCommand() {
        wrist.set(0.2);
    }
     private void wristUpOffCommand() {
        wrist.set(0);
    }
    private void wristDownOnCommand() {
        wrist.set(-0.2);
    }
     private void wristDownOffCommand() {
        wrist.set(0);
    }




    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
          
        // Configure the button bindings
        configureButtonBindings();
    }
   
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
        intakeOn.onTrue(new InstantCommand(() -> intakeOnCommand()));
        outtakeOn.onTrue(new InstantCommand(() -> outakeOnCommand()));
       
        intakeOn.onFalse(new InstantCommand(() -> intakeOffCommand()));
        outtakeOn.onFalse(new InstantCommand(() -> outakeOffCommand()));
        
        shooterOn.onTrue(new InstantCommand(() -> shooterOnCommand()));

        shooterOn.onFalse(new InstantCommand(() -> shooterOffCommand()));

        wristUpOn.onTrue(new InstantCommand(() -> wristUpOnCommand()));
        wristUpOn.onFalse(new InstantCommand(() -> wristUpOffCommand()));

        wristDownOn.onTrue(new InstantCommand(() -> wristDownOnCommand()));
        wristDownOn.onFalse(new InstantCommand(() -> wristDownOffCommand()));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new LeaveZone(s_Swerve);
    }
}
