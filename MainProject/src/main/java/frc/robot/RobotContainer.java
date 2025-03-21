// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.commands.AlgaeGroundIntakeCommand;
import frc.robot.commands.AlgaeL1IntakeCommand;
import frc.robot.commands.AlgaeL2IntakeCommand;
import frc.robot.commands.AlgaeScoreNetCommand;
import frc.robot.commands.AlgaeScoreProcessorCommand;
import frc.robot.commands.ArmClearanceCommand;
import frc.robot.commands.CoralClawIntakeCommand;
import frc.robot.commands.CoralL1ScoreCommand;
import frc.robot.commands.CoralL2ScoreCommand;
import frc.robot.commands.CoralLifterIntakeCommand;
import frc.robot.commands.CoralLifterOuttakeCommand;
import frc.robot.commands.HangApproachCommand;
import frc.robot.commands.HangCommand;
import frc.robot.commands.LifterClearanceCommand;
import frc.robot.commands.StowedCommand;
import frc.robot.commands.WarningLogCommand;

import java.io.File;
import swervelib.SwerveInputStream;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */

 /*
 TODO
  1. Tune F variable on drive motors
  2. pathplanner 
*/

public class RobotContainer
{

  private static CommandXboxController driverXbox = new CommandXboxController(0);
  private static XboxController driverXboxController = new XboxController(0);


  // The robot's subsystems and commands are defined here...

  private static ClawIntake clawIntake = new ClawIntake();
  private static Lifter lifter = new Lifter();
  private static LifterIntake lifterIntake = new LifterIntake();
  private static ClawElevator clawElevator = new ClawElevator();
  private static ClawArm clawArm = new ClawArm();

  private final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));

  

  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> driverXbox.getLeftY() * -1,
                                                                () -> driverXbox.getLeftX() * -1)
                                                            .withControllerRotationAxis(driverXbox::getRightX)
                                                            .deadband(Constants.OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(driverXbox::getRightX,
                                                                                             driverXbox::getRightY)
                                                           .headingWhile(true);

  /**
   * Clone's the angular velocity input stream and converts it to a robotRelative input stream.
   */
  SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
                                                             .allianceRelativeControl(false);

  SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                        () -> -driverXbox.getLeftY(),
                                                                        () -> -driverXbox.getLeftX())
                                                                    .withControllerRotationAxis(() -> driverXbox.getRawAxis(
                                                                        2))
                                                                    .deadband(Constants.OperatorConstants.DEADBAND)
                                                                    .scaleTranslation(0.8)
                                                                    .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleKeyboard     = driveAngularVelocityKeyboard.copy()
                                                                               .withControllerHeadingAxis(() ->
                                                                                                              Math.sin(
                                                                                                                  driverXbox.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2),
                                                                                                          () ->
                                                                                                              Math.cos(
                                                                                                                  driverXbox.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2))
                                                                               .headingWhile(true);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
    NamedCommands.registerCommand("test", Commands.print("I EXIST"));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {

    Command driveFieldOrientedDirectAngle      = drivebase.driveFieldOriented(driveDirectAngle);
    Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    Command driveRobotOrientedAngularVelocity  = drivebase.driveFieldOriented(driveRobotOriented);
    Command driveSetpointGen = drivebase.driveWithSetpointGeneratorFieldRelative(driveDirectAngle);
    Command driveFieldOrientedDirectAngleKeyboard      = drivebase.driveFieldOriented(driveDirectAngleKeyboard);
    Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    Command driveSetpointGenKeyboard = drivebase.driveWithSetpointGeneratorFieldRelative(driveDirectAngleKeyboard);


    

    // Right Bumper with A, B, X, Y
    Trigger rightBumperAndAButton = new Trigger(() -> (driverXboxController.getRightBumper() && driverXboxController.getAButton()));
    rightBumperAndAButton.whileTrue(new AlgaeL1IntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));

    Trigger rightBumperAndBButton = new Trigger(() -> (driverXboxController.getRightBumper() && driverXboxController.getBButton()));
    rightBumperAndBButton.whileTrue(new AlgaeL2IntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));

    Trigger rightBumperAndXButton = new Trigger(() -> driverXboxController.getRightBumper() && driverXboxController.getXButton());
    rightBumperAndXButton.whileTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));

    Trigger rightBumperAndYButton = new Trigger(() -> driverXboxController.getRightBumper() && driverXboxController.getYButton());
    rightBumperAndYButton.whileTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));

    // Left Bumper with A, B, X, Y
    Trigger leftBumperAndAButton = new Trigger(() -> driverXboxController.getLeftBumper() && driverXboxController.getAButton());
    leftBumperAndAButton.whileTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));

    Trigger leftBumperAndBButton = new Trigger(() -> driverXboxController.getLeftBumper() && driverXboxController.getBButton());
    leftBumperAndBButton.whileTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));

    Trigger leftBumperAndXButton = new Trigger(() -> driverXboxController.getLeftBumper() && driverXboxController.getXButton());
    leftBumperAndXButton.whileTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));

    Trigger leftBumperAndYButton = new Trigger(() -> driverXboxController.getLeftBumper() && driverXboxController.getYButton());
    leftBumperAndYButton.whileTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));


    driverXbox.a()
      .onTrue(new StowedCommand(clawIntake, clawArm, clawElevator, lifter, lifterIntake));
    driverXbox.b()
      .onTrue(new CoralLifterIntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake))
      .onFalse(Commands.runOnce(() -> lifterIntake.stop()));
    driverXbox.y()
      .onTrue(new CoralLifterOuttakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake))
      .onFalse(Commands.runOnce(() -> lifterIntake.stop()));

    driverXbox.start().onTrue(new HangApproachCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
    driverXbox.back().onTrue(new HangCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));

    driverXbox.povDown().onTrue(new LifterClearanceCommand(clawArm, clawElevator, lifter));
    driverXbox.povUp().onTrue(new ArmClearanceCommand(clawArm, clawElevator, lifter));
    driverXbox.povLeft().onTrue(new AlgaeL1IntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));



    
 
    
    
    if (RobotBase.isSimulation())
    {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
    } else
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }

    if (Robot.isSimulation())
    {
      driverXbox.start().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      driverXbox.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());

    }
    if (DriverStation.isTest())
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity); // Overrides drive command above!

      driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.y().whileTrue(drivebase.driveToDistanceCommand(1.0, 0.2));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.back().whileTrue(drivebase.centerModulesCommand());
      driverXbox.leftBumper().onTrue(Commands.none());
      driverXbox.rightBumper().onTrue(Commands.none());
    } else
    { 
      //driverXbox.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      //driverXbox.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
      //driverXbox.b().whileTrue(
      //    drivebase.driveToPose(
      //        new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
      //                        );
      driverXbox.start().whileTrue(Commands.none());
      driverXbox.back().whileTrue(Commands.none());
      driverXbox.leftBumper().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.rightBumper().onTrue(Commands.none());
    } 

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return drivebase.getAutonomousCommand("LinearPath");
    //trying to integrate pathplanner for LinearAuto (short, about three feet forward move)
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
