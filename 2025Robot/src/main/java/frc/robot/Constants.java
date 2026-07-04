// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

  // Bench bring-up mode: replaces all competition mechanism bindings with jog sticks +
  // small closed-loop test moves (see RobotContainer.configureBenchTestBindings()).
  // Set false to restore competition bindings for the demo once mechanisms are verified.
  public static final boolean BENCH_TEST_MODE = true;

  // 2026-07-04 parade demo cycle: lifter up -> arm out -> elevator up -> reverse, repeating.
  // Runs as the autonomous command; in bench mode POV-right also toggles it in teleop.
  public static class DemoConstants {
    public static final double LIFTER_POS = 120;                                          // deg
    public static final double ARM_POS = ClawArmConstants.MAX_POS_DEGREES - 5;            // 123 deg
    public static final double ELEVATOR_POS = ClawElevatorConstants.MAX_POS_INCHES - 2;   // 21.25 in
    public static final double PAUSE_SECONDS = 0.75;
    // Fallback timeouts on non-collision stages so a stall can't hang the show;
    // the two collision gates (lifter-up before arm, arm-stowed before lifter-down)
    // intentionally have NO timeout — a stall there freezes the cycle in a safe pose.
    public static final double ARM_STAGE_TIMEOUT = 25;
    public static final double ELEVATOR_STAGE_TIMEOUT = 15;
  }

  public static class SubsystemEnables {
    public static final boolean BRAKES_ENABLED = false; // Brakes/servos
    public static final boolean CLAW_INTAKE_ENABLED = false;
    public static final boolean LIFTER_INTAKE_ENABLED = false;
    
    public static final boolean CLAW_ELEVATOR_ENABLED = true;
    public static final boolean CLAW_ARM_ENABLED = true;
    public static final boolean LIFTER_ENABLED = true;
  }


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
    public static final double ALGAE_INTAKE_SPEED = 1;
    public static final double ALGAE_HOLD_SPEED = 0.07;
    public static final double ALGAE_OUTTAKE_SPEED = 1;
    public static final double CORAL_INTAKE_SPEED = 0.5;
    public static final double CORAL_OUTTAKE_SPEED = 0.5;
  }

  public static class LifterConstants{
    // Hardware: 2x TalonSRX + quad encoders, mechanically linked sides.
    // Measured range: 0 (stowed) to 6733 ticks (full deploy) = 180 degrees.
    // Encoders are zeroed at boot (Lifter constructor) — mechanism MUST be at stow before power-on.
    public static final int RIGHT_MOTOR_ID = 31;
    public static final int LEFT_MOTOR_ID = 32;
    // TO-VERIFY on bench (blip test, ~3% output in Tuner): positive output must move BOTH sides
    // toward deploy AND count BOTH sensors up. On Phoenix 5, setInverted does NOT flip the quad
    // sensor — phase must be validated per side. Wrong phase on one side = the sides fight.
    // Bench-verified 2026-07-04: positive output moves both sides out; right sensor counted up,
    // left counted DOWN (divergence fault right +54 / left -65 ticks) -> left phase flipped.
    public static final boolean RIGHT_MOTOR_INVERT = false;
    public static final boolean LEFT_MOTOR_INVERT = true;
    public static final boolean RIGHT_SENSOR_PHASE = true;
    public static final boolean LEFT_SENSOR_PHASE = true;

    public static final double TICKS_PER_DEGREE = 6733.0 / 180.0; // ~37.4

    // Motion Magic, Phoenix 5 native units: cruise = ticks/100ms, accel = ticks/100ms/s.
    // BENCH values: ~27 deg/s cruise -> full 180 deg travel in ~7 s. Raise only after tuning kF.
    public static final double MOTOR_MAX_VELOCITY = 100;
    public static final double MOTOR_MAX_ACCELERATION = 200;

    // kF drives Motion Magic — measure it, don't guess: run a fixed duty in Tuner, read
    // velocity in ticks/100ms, then kF = duty * 1023 / velocity. Zero until measured.
    public static final double MOTOR_F = 0;
    public static final double MOTOR_P = 0.8;
    public static final double MOTOR_I = 0;
    public static final double MOTOR_D = 0;

    // Bring-up safety clamps
    public static final double PEAK_OUTPUT = 0.2;              // duty cycle, both directions
    public static final int CONTINUOUS_CURRENT_LIMIT = 10;     // A — raise if it can't lift
    public static final int PEAK_CURRENT_LIMIT = 20;           // A
    public static final int PEAK_CURRENT_DURATION_MS = 200;
    public static final int FORWARD_SOFT_LIMIT_TICKS = 6800;   // just past full deploy
    public static final int REVERSE_SOFT_LIMIT_TICKS = -150;   // just past stow
    public static final double DIVERGENCE_FAULT_DEGREES = 3.0; // left/right split that latches a fault
    public static final double JOG_MAX_OUTPUT = 0.15;
    public static final double MAX_POS_DEGREES = 180;

    // Lifter positions (degrees). Remapped from the old NEO-rotation setpoints (10..198) via
    // (rot - 10) * 180 / 188 — the old values were encoder rotations, NOT degrees; 193/198
    // taken as degrees would overrun the 180-degree hard stop. TO-VERIFY each on bench.
    public static final double STOWED_POS = 0;                 // was 10 rot
    public static final double HANG_POS = 0;                   // was 10 rot
    public static final double CORAL_SCORE_POS = 87.1;         // was 101 rot
    public static final double CLEARANCE_POS = 114.9;          // was 130 rot
    public static final double ALGAE_INTAKE_POS = 114.9;       // was 130 rot
    public static final double ALGAE_SCORE_POS = 113.0;        // was 128 rot
    public static final double CORAL_INTAKE_POS = 175.2;       // was 193 rot
    public static final double CLIMBING_APPROACH_POS = 180.0;  // was 198 rot

  }

  public static class ClawArmConstants{
    // Hardware: TalonSRX + quad encoder. Measured range: 0 - 795000 ticks = 128 degrees.
    // Encoder zeroed at boot — arm MUST be at stow before power-on.
    public static final int MOTOR_ID = 34;
    // Bench-verified 2026-07-04: positive output moves arm the correct direction, but the
    // encoder counted DOWN (recorded -260k ticks over 7s at +0.2 duty) -> phase flipped true.
    public static final boolean MOTOR_INVERT = false;
    public static final boolean SENSOR_PHASE = true;

    public static final double TICKS_PER_DEGREE = 795000.0 / 128.0; // ~6211

    // Motion Magic, Phoenix 5 native units (ticks/100ms, ticks/100ms/s). ~8 deg/s for the
    // parade demo cycle (bench-tested at 5 deg/s first; cruise duty = 5000*kF/1023 ~= 0.27).
    public static final double MOTOR_MAX_VELOCITY = 5000;
    public static final double MOTOR_MAX_ACCELERATION = 10000;
    // Measured 2026-07-04: 0.2 duty -> ~3660 ticks/100ms => kF = 0.2*1023/3660 ~= 0.056
    public static final double MOTOR_F = 0.056;
    public static final double MOTOR_P = 0.05;
    public static final double MOTOR_I = 0;
    public static final double MOTOR_D = 0;

    // Bring-up safety clamps. Peak raised 0.2 -> 0.4 for the demo-cycle cruise headroom.
    public static final double PEAK_OUTPUT = 0.4;
    public static final int CONTINUOUS_CURRENT_LIMIT = 10;
    public static final int PEAK_CURRENT_LIMIT = 20;
    public static final int PEAK_CURRENT_DURATION_MS = 200;
    public static final int FORWARD_SOFT_LIMIT_TICKS = 800000;
    public static final int REVERSE_SOFT_LIMIT_TICKS = -5000;
    public static final double JOG_MAX_OUTPUT = 0.1;
    public static final double MAX_POS_DEGREES = 128;

    // Claw Arm Positions (degrees). Carried 1:1 from the old TalonFX "rotation" setpoints —
    // scaling is unverified on the new sensor; TO-VERIFY each on bench.
    public static final double STOWED_POS = 0.77; //0.2
    public static final double CLIMBING_APPROACH_POS = 0;
    public static final double ALGAE_TRANSFER_POS = 12.76; //10.76
    public static final double L1_CORAL_SCORING_POS = 23.26; //22
    public static final double L2_CORAL_SCORING_POS = 44; //20.6 //36.47 //38
    public static final double L1_ALGAE_INTAKE_POS = 26.55; //25.55 //24.7 //22.77 //lifter hits on way back in
    public static final double L2_ALGAE_INTAKE_POS = 38; //33.89 //19.8 //34.14
    public static final double CLEARANCE_POS = 30; //28 //27.6
    public static final double CORAL_HUMAN_POS = 30; //37.6
    public static final double ALGAE_SCORE_POS = 53; //48.9
    public static final double DEPLOY_LINE_UP_POS = 6.37;
  }

  public static class ClawElevatorConstants{
    // Hardware: TalonSRX + quad encoder, CAN 36 (re-ID'd from the old 50 — NOTES.md updated).
    // Measured range: 0 - 3214 ticks = 23.25 inches. Encoder zeroed at boot — start at bottom.
    public static final int MOTOR_ID = 36;
    // Bench 2026-07-04: positive command drove the elevator DOWN -> invert true. Retest
    // showed ticks counting NEGATIVE while moving up (invert does NOT flip the quad sensor)
    // -> phase flipped true. Elevator free-falls at 0 output; it only lifts at ~0.25 duty.
    public static final boolean MOTOR_INVERT = true;
    public static final boolean SENSOR_PHASE = true;

    // Calibrated 2026-07-04: full physical travel measured at 3178 ticks top, exactly 0 back
    // at bottom (old assumed 3214 was within 1% — kept the measured value).
    public static final double TICKS_PER_INCH = 3178.0 / 23.25; // ~136.7

    // Motion Magic, Phoenix 5 native units (ticks/100ms, ticks/100ms/s).
    // Measured: 0.25 duty climbs at only ~16 ticks/100ms and stalls on binding spots —
    // gravity+friction eat ~0.2 duty. Cruise kept low until the raised peak is bench-proven.
    public static final double MOTOR_MAX_VELOCITY = 40;
    public static final double MOTOR_MAX_ACCELERATION = 80;
    // Rough net-of-gravity estimate from the calibration climb: (0.25-0.2)*1023/16 ~= 3.2.
    public static final double MOTOR_F = 3.0;
    public static final double MOTOR_P = 2.0;
    public static final double MOTOR_I = 0;
    public static final double MOTOR_D = 0;
    // Constant duty added to every Motion Magic command to cancel gravity (arb feedforward).
    public static final double GRAVITY_FF = 0.2;

    // Bring-up safety clamps. Asymmetric: gravity helps down, so down stays gentle while up
    // gets real authority (0.25 could not lift the carriage unassisted).
    public static final double PEAK_OUTPUT_UP = 0.55;
    public static final double PEAK_OUTPUT_DOWN = 0.25;
    public static final int CONTINUOUS_CURRENT_LIMIT = 15;     // 10 was marginal for the climb
    public static final int PEAK_CURRENT_LIMIT = 25;
    public static final int PEAK_CURRENT_DURATION_MS = 200;
    public static final int FORWARD_SOFT_LIMIT_TICKS = 3100;   // measured top = 3178
    public static final int REVERSE_SOFT_LIMIT_TICKS = -50;
    public static final double JOG_MAX_UP = 0.45;
    public static final double JOG_MAX_DOWN = 0.2;
    public static final double MAX_POS_INCHES = 23.25;

    // Elevator positions (inches from bottom). L2 restored from the old TalonFX ratio
    // (stow -0.75, L2 -28, TOP -46 rotations over 23.25 in) — the conversion had collapsed
    // L2 and TOP to the same full-travel value. TO-VERIFY on bench.
    public static final double STOWED_POS = 0;
    public static final double L1_CORAL_SCORING_POS = 0;
    public static final double L1_ALGAE_INTAKE_POS = 0;
    public static final double CORAL_HUMAN_POS = 0;
    public static final double CLIMBING_APPROACH_POS = 0;
    public static final double ALGAE_TRANSFER_POS = 0;
    public static final double L2_CORAL_SCORING_POS = 14.0;  // was 23 (== TOP)
    public static final double L2_ALGAE_INTAKE_POS = 14.0;   // was 23 (== TOP)
    public static final double ALGAE_SCORE_POS = 14.0;       // was 23 (== TOP)
    public static final double TOP_POS = 23.0;
  }

  public static class LifterIntakeConstants{
    public static final int MOTOR_ID = 35;
    public static final boolean LIFTER_MOTOR_INVERT = false;
    public static final double ALGAE_INTAKE_SPEED = 1;
    public static final double ALGAE_OUTTAKE_SPEED = 1;
    public static final double CORAL_INTAKE_SPEED = 1;
    public static final double CORAL_OUTTAKE_SPEED = 1;
    public static final double AUTO_CORAL_OUTTAKE_SPEED = 0.5;
  }
}


