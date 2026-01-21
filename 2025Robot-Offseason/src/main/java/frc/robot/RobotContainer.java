// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.L1Command;
import frc.robot.commands.L2Command;
import frc.robot.commands.L3Command;
import frc.robot.commands.L4Command;
import frc.robot.commands.StowedCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import swervelib.SwerveInputStream;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */

 /*
 TODO
*/

public class RobotContainer
{

  private static CommandXboxController driverXbox = new CommandXboxController(2);
  private static CommandXboxController operatorXbox = new CommandXboxController(3);
 


  // The robot's subsystems and commands are defined here...

  private static Arm arm = new Arm();
  private static Wrist wrist = new Wrist();
  private static Hand hand = new Hand();

  private final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));

 SendableChooser<Command> autoChooser;
  //LOOK HERE AUTO

  

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
    .withControllerHeadingAxis(() -> Math.sin(driverXbox.getRawAxis(2) * Math.PI) * (Math.PI * 2), () -> Math.cos(driverXbox.getRawAxis(2) * Math.PI) * (Math.PI * 2)).headingWhile(true);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
    NamedCommands.registerCommand("test", Commands.print("I EXIST"));
    //NamedCommands.registerCommand("AutoCoralScore", new AutoCoralScoreCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));

   autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    //autoChooser.addOption("LinearAuto", LinearAuto()); 
    //autoChooser.addOption("CrookedAuto", CrookedAuto());
    //autoChooser.addOption("ScoreCoralCenter", ScoreCoralCenter());
    //autoChooser.addOption("ScoreCoralLeft", ScoreCoralLeft());
    //autoChooser.addOption("ScoreCoralRight", ScoreCoralRight());
    //autoChooser.addOption("CrookedAuto", NewAuto());
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
    
    
    driverXbox.a()
      .onTrue(new L1Command(arm, wrist, hand));
    driverXbox.b()
      .onTrue(new L2Command(arm, wrist, hand));
    driverXbox.x()
      .onTrue(new L3Command(arm, wrist, hand));
    driverXbox.y()
      .onTrue(new L4Command(arm, wrist, hand));

    driverXbox.leftBumper()
      .onTrue(Commands.runOnce(() -> hand.intake(Constants.HandConstants.INTAKE_SPEED)))
      .onFalse(Commands.runOnce(() -> hand.stop()));
    driverXbox.rightBumper()
      .onTrue(Commands.runOnce(() -> hand.outtake(Constants.HandConstants.OUTTAKE_SPEED)))
      .onFalse(Commands.runOnce(() -> hand.stop()));

    //driverXbox.povDown().onTrue(new StowedCommand(arm, wrist, hand));
    driverXbox.povRight().onTrue(new StowedCommand(arm, wrist, hand));
    driverXbox.povLeft().onTrue(new IntakeCommand(arm, wrist, hand));
    //driverXbox.povUp().onTrue(new IntakeCommand(arm, wrist, hand));

/* 
    operatorXbox.a().onTrue(Commands.runOnce(() -> clawArm.moveStowedPos()));
    //operatorXbox.b().onTrue(Commands.runOnce(() -> lifter.moveAlgaeIntakePos()));
    
    operatorXbox.x().onTrue(Commands.runOnce(() -> clawArm.moveAlgaeTransferPos()));
    operatorXbox.y().onTrue(Commands.runOnce(() -> clawArm.moveClear()));


    operatorXbox.povDown().onTrue(Commands.runOnce(() -> lifter.moveStowedPos()));
    operatorXbox.povRight().onTrue(Commands.runOnce(() -> lifter.moveCoralIntakePos()));
    operatorXbox.povLeft().onTrue(Commands.runOnce(() -> lifter.moveCoralScorePos()));
    operatorXbox.povUp().onTrue(Commands.runOnce(() -> lifter.moveClear()));

    operatorXbox.rightBumper().and(operatorXbox.a()).onTrue(Commands.runOnce(() -> clawArm.moveL1CoralScoringPos()));
    operatorXbox.rightBumper().and(operatorXbox.b()).onTrue(Commands.runOnce(() -> clawArm.moveL2CoralScoringPos()));
    
    
    //operatorXbox.rightBumper().and(operatorXbox.x()).onTrue(Commands.runOnce(() -> clawArm.moveL1AlgaeIntakePos()));
    //operatorXbox.rightBumper().and(operatorXbox.y()).onTrue(Commands.runOnce(() -> clawArm.moveL2AlgaeIntakePos()));
    
    operatorXbox.leftBumper().and(operatorXbox.a()).onTrue(Commands.runOnce(() -> clawArm.moveCoralHumanPos()));
    //operatorXbox.leftBumper().and(operatorXbox.b()).onTrue(Commands.runOnce(() -> ()));
    //operatorXbox.leftBumper().and(operatorXbox.x()).onTrue(Commands.runOnce(() -> ()));
    operatorXbox.leftBumper().and(operatorXbox.y()).onTrue(Commands.runOnce(() -> clawArm.moveAlgaeScorePos()));

    operatorXbox.start().onTrue(Commands.runOnce(() ->  clawElevator.moveStowedPos()));
    operatorXbox.back().onTrue(Commands.runOnce(() -> clawElevator.moveTopPos()));
*/
    /*
    
    driverXbox.rightBumper()
      .onTrue(new HangCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake))
      .onFalse(Commands.runOnce(() -> lifter.releaseBreak()));
    driverXbox.leftBumper()
    .onTrue(new HangApproachCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
    

    driverXbox.a()
      .onTrue(Commands.runOnce(() -> lifterIntake.intake(Constants.LifterIntakeConstants.CORAL_INTAKE_SPEED)))
      .onFalse(Commands.runOnce(() -> lifterIntake.stop()));
    driverXbox.b()
      .onTrue(Commands.runOnce(() -> lifterIntake.outtake(Constants.LifterIntakeConstants.CORAL_OUTTAKE_SPEED)))
      .onFalse(Commands.runOnce(() -> lifterIntake.stop()));
    driverXbox.x()
      .onTrue(Commands.runOnce(() -> clawIntake.intake(Constants.ClawIntakeConstants.CORAL_INTAKE_SPEED)))
      .onFalse(Commands.runOnce(() -> clawIntake.intake(Constants.ClawIntakeConstants.ALGAE_HOLD_SPEED)));
    driverXbox.y()
      .onTrue(Commands.runOnce(() -> clawIntake.outtake(Constants.ClawIntakeConstants.CORAL_OUTTAKE_SPEED)))
      .onFalse(Commands.runOnce(() -> clawIntake.stop()));  */

    
 
    
    
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
    if (DriverStation.isTest()){
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity); // Overrides drive command above!

      driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.y().whileTrue(drivebase.driveToDistanceCommand(1.0, 0.2));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.back().whileTrue(drivebase.centerModulesCommand());
      driverXbox.leftBumper().onTrue(Commands.none());
      driverXbox.rightBumper().onTrue(Commands.none());
    } 
    else{ 
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      //driverXbox.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
      //driverXbox.b().whileTrue(
      //    drivebase.driveToPose(
      //        new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
      //                        );
      //driverXbox.start().whileTrue(Commands.none());
      //driverXbox.back().whileTrue(Commands.none());
      driverXbox.povUp().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.povDown().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      //driverXbox.rightBumper().onTrue(Commands.none());
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
  return autoChooser.getSelected();
    //return drivebase.getAutonomousCommand("LinearPath");
    //trying to integrate pathplanner for LinearAuto (short, about three feet forward move)
  }
  
  public Command LinearAuto() {
  return new PathPlannerAuto("LinearAuto");
  } 

  public Command CrookedAuto() {
    return new PathPlannerAuto("CrookedAuto");
  }

  public Command ScoreCoralCenter() {
    return new PathPlannerAuto("ScoreCoralCenter");
  }

  public Command ScoreCoralLeft() {
    return new PathPlannerAuto("ScoreCoralLeft");
  }

  public Command ScoreCoralRight() {
    return new PathPlannerAuto("ScoreCoralRight");
  }
   

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
