// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag
  public static final double MAX_SPEED  = Units.feetToMeters(14.5);
  // Maximum speed of the robot in meters per second, used to limit acceleration.

//  public static final class AutonConstants
//  {
//
//    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
//    public static final PIDConstants ANGLE_PID       = new PIDConstants(0.4, 0, 0.01);
//  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants{
    // Joystick Deadband
    public static final double DEADBAND        = 0.15;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
  }

  public static class ClawIntakeConstants{
    public static final int MOTOR_ID = 33;
    public static final boolean MOTOR_INVERT = false;
  }

  public static class ClawArmConstants{
    public static final int MOTOR_ID = 34;
    
    // Claw Arm Positions
    public static final double STOWED_POS = 0.77;//
    public static final double CLIMBING_APPROACH_POS = 0.77;//
    public static final double ALGAE_TRANSFER_POS = 10.47; //
    public static final double MIN_ARM = 19.2; //
    public static final double L1_CORAL_SCORING_POS = 22; //
    public static final double L2_CORAL_SCORING_POS = 20.6; //
    public static final double L1_ALGAE_INTAKE_POS = 24.7; //
    public static final double L2_ALGAE_INTAKE_POS = 19.8; //
    public static final double CORAL_HUMAN_POS = 37.6; //
    public static final double ALGAE_SCORE_POS = 48.8; //
  }

  public static class LifterConstants{
    public static final int LIFTER_RIGHT_MOTOR_ID = 31;
    public static final int LIFTER_LEFT_MOTOR_ID = 32;
    public static final int LIFTER_INTAKE_MOTOR_ID = 35;
    public static final boolean LIFTER_RIGHT_MOTOR_INVERT = false;
    public static final boolean LIFTER_LEFT_MOTOR_INVERT = true;
    public static final double DRIVE_MOTOR_IZ = 0;
    public static final double DRIVE_MOTOR_MAX_VELOCITY = 8000;
    public static final double DRIVE_MOTOR_ALLOWED_ERROR = 0;
    public static final double DRIVE_MOTOR_MIN_OUTPUT = -1;
    public static final double DRIVE_MOTOR_MAX_ACCELERATION = 8000;
    public static final double LIFTER_RIGHT_P = 0.15;
    public static final double LIFTER_RIGHT_I = 0;
    public static final double LIFTER_RIGHT_D = 0.001;
    public static final double LIFTER_LEFT_P = 0.15;
    public static final double LIFTER_LEFT_I = 0;
    public static final double LIFTER_LEFT_D = 0.001;

    // Lifter Positions
    public static final double STOWED_POS = 10; //
    public static final double HANG_POS = 15; //
    public static final double CORAL_SCORE_POS = 101; //
    public static final double ALGAE_INTAKE_POS = 125.1; //
    public static final double ALGAE_SCORE_POS = 125.1; //
    public static final double ARM_CLEARANCE_POS = 118; 
    public static final double CORAL_INTAKE_POS = 198; //
    public static final double CLIMBING_APPROACH_POS = 198; //

  }

  public static class ClawElevatorConstants{
    public static final int MOTOR_ID = 50;
    
    // Elevator Positions
    public static final double STOWED_POS = 0; //
    public static final double L1_CORAL_SCORING_POS = 0; //
    public static final double L1_ALGAE_INTAKE_POS = 0; //
    public static final double CORAL_HUMAN_POS = 0; //
    public static final double CLIMBING_APPROACH_POS = 0; //
    public static final double ALGAE_TRANSFER_POS = 0;  //
    public static final double L2_CORAL_SCORING_POS = 21.8; //
    public static final double L2_ALGAE_INTAKE_POS = 28.75; //
    public static final double ALGAE_SCORE_POS = 27.6; //

  }

  public static class LifterIntakeConstants{
    public static InvertedValue LIFTER_MOTOR_INVERT = InvertedValue.Clockwise_Positive;
  }
}


