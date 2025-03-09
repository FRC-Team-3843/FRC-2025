package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawArm extends SubsystemBase{
    private final TalonFX clawArmMotor = new TalonFX(Constants.ClawConstants.CLAW_ARM_MOTOR_ID);
    private final MotionMagicVoltage clawArmMM = new MotionMagicVoltage(0);
    public final TalonFXConfiguration clawArmConfig = new TalonFXConfiguration();
	public Object moveAlgaeIntakePos;
    public ClawArm () {

        FeedbackConfigs fdb = clawArmConfig.Feedback;
        fdb.SensorToMechanismRatio = 1;

        MotionMagicConfigs clawArmMMConfig = clawArmConfig.MotionMagic;
        clawArmMMConfig.MotionMagicAcceleration = 100;
        clawArmMMConfig.MotionMagicCruiseVelocity = 500;
        

        Slot0Configs clawArmSlot0Configs = clawArmConfig.Slot0;
        clawArmSlot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
        clawArmSlot0Configs.kV = 0.012; // A velocity target of 1 rps results in 0.12 V output
        clawArmSlot0Configs.kA = 0.001; // An acceleration of 1 rps/s requires 0.01 V output
        clawArmSlot0Configs.kP = 5; // A position error of 0.2 rotations results in 12 V output
        clawArmSlot0Configs.kI = 0; // No output for integrated error
        clawArmSlot0Configs.kD = 0; // A velocity error of 1 rps results in 0.5 V output

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = clawArmMotor.getConfigurator().apply(clawArmConfig);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }

    }
    private void setPos(double targetPosition) {
        System.out.println("hey?");
        clawArmMM.withPosition(targetPosition);
        clawArmMotor.setControl(clawArmMM);
    }
    public void stowedPos() { 
        setPos(Constants.ClawConstants.STOWED_POS);
    }

    public void algaescorePos() {
        setPos(Constants.ClawConstants.ALGAE_SCORE_POS);
    }

    public void straightPos() {
        setPos(Constants.ClawConstants.STRAIGHT_POS);
    }

    public void lowCoralScoringPos() {
        setPos(Constants.ClawConstants.LOW_CORAL_SCORING_POS);
    }

    public void middleCoralScoringPos() {
        setPos(Constants.ClawConstants.MIDDLE_CORAL_SCORING_POS);
    }
    public void coralHumanPos() {
        setPos(Constants.ClawConstants.CORAL_HUMAN_POS);
    }

}
