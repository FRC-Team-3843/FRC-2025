// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.sim.PhysicsSim;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final TalonFX m_fx = new TalonFX(34);
  private final MotionMagicVoltage m_mmReq = new MotionMagicVoltage(0);
  //private final XboxController m_joystick = new XboxController(0);

  private int m_printCount = 0;

  private final Mechanisms m_mechanisms = new Mechanisms();
/* 
  @Override
  public void simulationInit() {
    PhysicsSim.getInstance().addTalonFX(m_fx, 0.001);
  }

  @Override
  public void simulationPeriodic() {
    PhysicsSim.getInstance().run();
  }
 */
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    TalonFXConfiguration cfg = new TalonFXConfiguration();

    /* Configure gear ratio */
    FeedbackConfigs fdb = cfg.Feedback;
    fdb.SensorToMechanismRatio = 1; // 1 rotor rotations per mechanism rotation

    /* Configure Motion Magic */
    MotionMagicConfigs mm = cfg.MotionMagic;
      mm.MotionMagicAcceleration = 50;
      mm.MotionMagicCruiseVelocity = 10;
    //mm.withMotionMagicCruiseVelocity(RotationsPerSecond.of(100)) // 5 (mechanism) rotations per second cruise
     // .withMotionMagicAcceleration(RotationsPerSecondPerSecond.of(500)) // Take approximately 0.5 seconds to reach max vel
      // Take approximately 0.1 seconds to reach max accel 
      //.withMotionMagicJerk(RotationsPerSecondPerSecond.per(Second).of(100));

    Slot0Configs slot0 = cfg.Slot0;
    slot0.kS = 0.25; // Add 0.25 V output to overcome static friction
    slot0.kV = 0.012; // A velocity target of 1 rps results in 0.12 V output
    slot0.kA = 0.001; // An acceleration of 1 rps/s requires 0.01 V output
    slot0.kP = 5; // A position error of 0.2 rotations results in 12 V output
    slot0.kI = 0; // No output for integrated error
    slot0.kD = 0; // A velocity error of 1 rps results in 0.5 V output

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_fx.getConfigurator().apply(cfg);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }

    SmartDashboard.setDefaultNumber("Target Position", 0);
    SmartDashboard.setDefaultNumber("Target Velocity", 0);
    SmartDashboard.setDefaultBoolean("Control Mode", false);
    SmartDashboard.setDefaultBoolean("Reset Encoder", false);
  }

  @Override
  public void robotPeriodic() {
    if (++m_printCount >= 10) {
      m_printCount = 0;
      System.out.println("Pos: " + m_fx.getPosition());
      System.out.println("Vel: " + m_fx.getVelocity());
      System.out.println();
    }
    m_mechanisms.update(m_fx.getPosition(), m_fx.getVelocity());
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    /* Deadband the joystick 
    double leftY = m_joystick.getLeftY();
    if (Math.abs(leftY) < 0.1) leftY = 0;

    m_fx.setControl(m_mmReq.withPosition(leftY * 10).withSlot(0));
    if (m_joystick.getBButton()) {
      m_fx.setPosition(Rotations.of(1)); */
    
    if (SmartDashboard.getBoolean("Control Mode", false)) {
        /*
         * Get the target velocity from SmartDashboard and set it as the setpoint
         * for the closed loop controller.
         */
        double targetVelocity = SmartDashboard.getNumber("Target Velocity", 0);
        VelocityVoltage velocityRequest = new VelocityVoltage(0).withSlot(1);
        m_fx.setControl(velocityRequest.withVelocity(targetVelocity));
      } else {
        /*
         * Get the target position from SmartDashboard and set it as the setpoint
         * for the closed loop controller.
         */
        double targetPosition = SmartDashboard.getNumber("Target Position", 0);
        MotionMagicVoltage mmr = new MotionMagicVoltage (0);
        mmr.withPosition(targetPosition);

        //PositionVoltage positionRequest = new PositionVoltage(0).withSlot(0);
        //m_fx.setControl(positionRequest.withPosition(targetPosition));
        m_fx.setControl(mmr);
      }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}