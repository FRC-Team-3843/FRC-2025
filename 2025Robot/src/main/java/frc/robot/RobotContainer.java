// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


////
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
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
import frc.robot.commands.AutoCoralScoreCommand;
import frc.robot.commands.HangApproachCommand;
import frc.robot.commands.HangCommand;
import frc.robot.commands.SwapToClawCommand;
import frc.robot.commands.SwapToLifterCommand;

import java.io.File;
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

  private static ClawIntake clawIntake = new ClawIntake();
  private static Lifter lifter = new Lifter();
  private static LifterIntake lifterIntake = new LifterIntake();
  private static ClawElevator clawElevator = new ClawElevator();
  private static ClawArm clawArm = new ClawArm();

  private final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));

 private final PowerDistribution pdp = new PowerDistribution(1, PowerDistribution.ModuleType.kCTRE);

  

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




    //
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
    Command driveFieldOrientedDirectAngleKeyboard      = drivebase.driveFieldOriented(driveDirectAngleKeyboard);
    Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    
    
    if (Constants.BENCH_TEST_MODE) {
      configureBenchTestBindings();
    } else {
      configureCompetitionBindings();
    }

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
      driverXbox.povUp().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
    }

  }

  /**
   * Bench bring-up controls (operator pad, port 3). Open-loop jogs on the sticks, small
   * closed-loop Motion Magic test moves on the buttons, panic-stop on Back. No competition
   * sequences are bound in this mode.
   */
  private void configureBenchTestBindings()
  {
    // Lifter jog: left stick Y, push up = positive output. Divergence watchdog in
    // Lifter.periodic() still applies. Release = stop.
    operatorXbox.axisMagnitudeGreaterThan(XboxController.Axis.kLeftY.value, 0.2)
        .whileTrue(Commands.runEnd(
            () -> lifter.jog(-operatorXbox.getLeftY() * Constants.LifterConstants.JOG_MAX_OUTPUT),
            () -> lifter.stopMotor(), lifter));

    // Elevator jog: right stick Y, push up = positive output. On release it HOLDS position
    // closed-loop instead of going neutral — the carriage free-falls at 0 output.
    // Back (panic) or disable still gives true neutral.
    operatorXbox.axisMagnitudeGreaterThan(XboxController.Axis.kRightY.value, 0.2)
        .whileTrue(Commands.runEnd(
            () -> clawElevator.jog(-operatorXbox.getRightY() * Constants.ClawElevatorConstants.JOG_MAX_UP),
            () -> clawElevator.holdPosition(), clawElevator));

    // Arm homing jog: POV up = positive output, POV down = negative, while held. The arm is
    // backdriven and can't be positioned by hand, so this bypasses soft limits while held
    // (they're relative to boot zero and would block driving toward the true stow position).
    operatorXbox.povUp().whileTrue(Commands.startEnd(
        () -> {
          clawArm.setSoftLimitsEnabled(false);
          clawArm.jog(Constants.ClawArmConstants.JOG_MAX_OUTPUT);
        },
        () -> {
          clawArm.stop();
          clawArm.setSoftLimitsEnabled(true);
        }, clawArm));
    operatorXbox.povDown().whileTrue(Commands.startEnd(
        () -> {
          clawArm.setSoftLimitsEnabled(false);
          clawArm.jog(-Constants.ClawArmConstants.JOG_MAX_OUTPUT);
        },
        () -> {
          clawArm.stop();
          clawArm.setSoftLimitsEnabled(true);
        }, clawArm));

    // Once the arm physically sits at stow: Start = re-zero the arm encoder in place,
    // making setpoints and soft limits valid without a power cycle.
    operatorXbox.start().onTrue(Commands.runOnce(() -> clawArm.zeroEncoder(), clawArm));

    // Small closed-loop test moves (Motion Magic latches — Back or disable to stop).
    // A = lifter to clearance so the arm can be exercised without the two colliding.
    operatorXbox.a().onTrue(Commands.runOnce(() -> lifter.setPos(Constants.LifterConstants.CLEARANCE_POS), lifter));
    operatorXbox.b().onTrue(Commands.runOnce(() -> lifter.setPos(0), lifter));
    operatorXbox.x().onTrue(Commands.runOnce(() -> clawElevator.setPos(2), clawElevator));
    operatorXbox.y().onTrue(Commands.runOnce(() -> clawElevator.setPos(0), clawElevator));
    operatorXbox.leftBumper().onTrue(Commands.runOnce(() -> clawArm.setPos(5), clawArm));
    operatorXbox.rightBumper().onTrue(Commands.runOnce(() -> clawArm.setPos(Constants.ClawArmConstants.STOWED_POS), clawArm));

    // Panic: neutral every mechanism.
    operatorXbox.back().onTrue(Commands.runOnce(() -> {
      lifter.stopMotor();
      clawElevator.stop();
      clawArm.stop();
    }, lifter, clawElevator, clawArm));

    // Parade demo cycle, toggleable in teleop. Press again (or Back) to stop.
    operatorXbox.povRight().toggleOnTrue(buildDemoCycleCommand());
  }

  /**
   * 2026-07-04 parade demo: continuously cycle lifter -> arm -> elevator out and back.
   * The two collision gates (lifter up before the arm moves, arm stowed before the lifter
   * descends) have no timeout: if a mechanism stalls or the lifter divergence watchdog
   * latches, the cycle freezes in a safe pose instead of driving into a collision.
   */
  private Command buildDemoCycleCommand()
  {
    return Commands.sequence(
        // Out: lifter first — the arm collides with the lifter unless it's raised.
        Commands.runOnce(() -> lifter.setPos(Constants.DemoConstants.LIFTER_POS), lifter),
        Commands.waitUntil(() -> lifter.isAtPosition(Constants.DemoConstants.LIFTER_POS)), // collision gate
        Commands.waitSeconds(Constants.DemoConstants.PAUSE_SECONDS),
        Commands.runOnce(() -> clawArm.setPos(Constants.DemoConstants.ARM_POS), clawArm),
        Commands.waitUntil(() -> clawArm.isAtPosition(Constants.DemoConstants.ARM_POS))
            .withTimeout(Constants.DemoConstants.ARM_STAGE_TIMEOUT),
        Commands.runOnce(() -> clawElevator.setPos(Constants.DemoConstants.ELEVATOR_POS), clawElevator),
        Commands.waitUntil(() -> clawElevator.isAtPosition(Constants.DemoConstants.ELEVATOR_POS))
            .withTimeout(Constants.DemoConstants.ELEVATOR_STAGE_TIMEOUT),
        Commands.waitSeconds(Constants.DemoConstants.PAUSE_SECONDS),
        // Back: exact reverse order.
        Commands.runOnce(() -> clawElevator.setPos(0), clawElevator),
        Commands.waitUntil(() -> clawElevator.isAtPosition(0))
            .withTimeout(Constants.DemoConstants.ELEVATOR_STAGE_TIMEOUT),
        Commands.runOnce(() -> clawArm.moveStowedPos(), clawArm),
        Commands.waitUntil(() -> clawArm.isAtStowedPos()), // collision gate
        Commands.runOnce(() -> lifter.setPos(Constants.LifterConstants.STOWED_POS), lifter),
        Commands.waitUntil(() -> lifter.isAtPosition(Constants.LifterConstants.STOWED_POS)),
        Commands.waitSeconds(Constants.DemoConstants.PAUSE_SECONDS)
    ).repeatedly();
  }

  private void configureCompetitionBindings()
  {
    operatorXbox.a()
      .onTrue(new AlgaeGroundIntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
    operatorXbox.b()
      .onTrue(new AlgaeL1IntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
    operatorXbox.x()
      .onTrue(new AlgaeL2IntakeCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
    operatorXbox.y()
      .onTrue(new AlgaeScoreNetCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));

    operatorXbox.leftBumper()
      .onTrue(new SwapToLifterCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
    operatorXbox.rightBumper()
      .onTrue(new SwapToClawCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));

    operatorXbox.povDown().onTrue(Commands.runOnce(() -> lifter.moveStowedPos()));
    operatorXbox.povRight().onTrue(Commands.runOnce(() -> lifter.moveCoralIntakePos()));
    operatorXbox.povLeft().onTrue(Commands.runOnce(() -> lifter.moveCoralScorePos()));
    operatorXbox.povUp().onTrue(Commands.runOnce(() -> lifter.moveClear()));

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
    
    
    driverXbox.rightBumper()
      .onTrue(new HangCommand(lifterIntake, clawArm, clawElevator, lifter, clawIntake));
      
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
      .onFalse(Commands.runOnce(() -> clawIntake.stop()));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
    public Command getAutonomousCommand()
  {
    // Parade demo: enabling autonomous runs the mechanism cycle continuously.
    return buildDemoCycleCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
