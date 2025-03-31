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
    public static final InvertedValue MOTOR_INVERT = InvertedValue.Clockwise_Positive;
    public static final double ALAGE_INTAKE_SPEED = 1;
    public static final double ALAGE_OUTTAKE_SPEED = 1;
    public static final double CORAL_INTAKE_SPEED = 0.5;
    public static final double CORAL_OUTTAKE_SPEED = 0.5;
  }

  public static class LifterConstants{
    public static final int RIGHT_MOTOR_ID = 31;
    public static final int LEFT_MOTOR_ID = 32;
    public static final boolean RIGHT_MOTOR_INVERT = false;
    public static final boolean LEFT_MOTOR_INVERT = true;
    //6000-8000
    public static final double MOTOR_MAX_VELOCITY = 8000;
    public static final double MOTOR_MAX_ACCELERATION = 24000;
    public static final double MOTOR_ALLOWED_ERROR = 0.8;

    public static final double MOTOR_MIN_OUTPUT = -1;
    public static final double MOTOR_P = 0.4;
    public static final double MOTOR_I = 0;
    public static final double MOTOR_D = 0.001;


    // Lifter Positions
    public static final double STOWED_POS = 10; 
    public static final double HANG_POS = 10; 
    public static final double CORAL_SCORE_POS = 101; 
    public static final double CLEARANCE_POS = 118; //118 
    public static final double ALGAE_INTAKE_POS = 125; //128
    public static final double ALGAE_SCORE_POS = 128; //125.1
    public static final double CORAL_INTAKE_POS = 193; 
    public static final double CLIMBING_APPROACH_POS = 198; 

  }

  public static class ClawArmConstants{
    public static final int MOTOR_ID = 34;
    
    // Claw Arm Positions
    public static final double STOWED_POS = 0.77; //0.2
    public static final double CLIMBING_APPROACH_POS = 0.77;
    public static final double ALGAE_TRANSFER_POS = 10.76; 
    public static final double L1_CORAL_SCORING_POS = 23.26; //22
    public static final double L2_CORAL_SCORING_POS = 36.47; //20.6
    public static final double L1_ALGAE_INTAKE_POS = 25.55; //24.7 //22.77 //lifter hits on way back in
    public static final double L2_ALGAE_INTAKE_POS = 33.98; //19.8 //34.14
    public static final double CLEARANCE_POS = 29; //28 //27.6
    public static final double CORAL_HUMAN_POS = 30; //37.6
    public static final double ALGAE_SCORE_POS = 48.9; //48.8
  }

  public static class ClawElevatorConstants{
    public static final int MOTOR_ID = 50;
    
    // Elevator Positions
    public static final double STOWED_POS = -0.75; //0 //2 //check with Harrison
    public static final double L1_CORAL_SCORING_POS = 0; 
    public static final double L1_ALGAE_INTAKE_POS = 0; 
    public static final double CORAL_HUMAN_POS = 0; 
    public static final double CLIMBING_APPROACH_POS = 0; 
    public static final double ALGAE_TRANSFER_POS = 0;  
    public static final double L2_CORAL_SCORING_POS = 28; 
    public static final double L2_ALGAE_INTAKE_POS = 28; 
    public static final double ALGAE_SCORE_POS = 28; //28
    public static final double TOP_POS = -46.81; //28 //49

  }

  public static class LifterIntakeConstants{
    public static final int MOTOR_ID = 35;
    public static InvertedValue LIFTER_MOTOR_INVERT = InvertedValue.Clockwise_Positive;
    public static final double ALAGE_INTAKE_SPEED = 1;
    public static final double ALAGE_OUTTAKE_SPEED = 1;
    public static final double CORAL_INTAKE_SPEED = 1;
    public static final double CORAL_OUTTAKE_SPEED = 1;
  }
}


