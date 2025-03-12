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
    private final TalonFX clawArmMotor = new TalonFX(Constants.ClawArmConstants.MOTOR_ID);
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
    public void setPos(double targetPosition) {
        clawArmMM.withPosition(targetPosition);
        clawArmMotor.setControl(clawArmMM);
    }

    public double getPosition() {
        return clawArmMotor.getPosition().getValue().magnitude();
    }

    public boolean isAtPosition(double position){
        if(Math.abs(getPosition() - position) < 2)
            return true;
        return false;
    }
    
    public void moveStowedPos() {
        setPos(Constants.ClawArmConstants.STOWED_POS);
    }

    public void moveClimbingApproachPos() {
        setPos(Constants.ClawArmConstants.CLIMBING_APPROACH_POS);
    }

    public void moveAlgaeScorePos() {
        setPos(Constants.ClawArmConstants.ALGAE_SCORE_POS);
    }

    public void moveCoralHumanPos() {
        setPos(Constants.ClawArmConstants.CORAL_HUMAN_POS);
    }

    public void moveL1CoralScoringPos() {
        setPos(Constants.ClawArmConstants.L1_CORAL_SCORING_POS);
    }

    public void moveL2CoralScoringPos() {
        setPos(Constants.ClawArmConstants.L2_CORAL_SCORING_POS);
    }

    public void moveL1AlgaeIntakePos() {
        setPos(Constants.ClawArmConstants.L1_ALGAE_INTAKE_POS);
    }

    public void moveL2AlgaeIntakePos() {
        setPos(Constants.ClawArmConstants.L2_ALGAE_INTAKE_POS);
    }

    public void moveAlgaeTransferPos() {
        setPos(Constants.ClawArmConstants.ALGAE_TRANSFER_POS);
    }

    public boolean isAtStowedPos() {
        return isAtPosition(Constants.ClawArmConstants.STOWED_POS);
    }

    public boolean isAtClimbingApproachPos() {
        return isAtPosition(Constants.ClawArmConstants.CLIMBING_APPROACH_POS);
    }

    public boolean isAtAlgaeScorePos() {
        return isAtPosition(Constants.ClawArmConstants.ALGAE_SCORE_POS);
    }

    public boolean isAtCoralHumanPos() {
        return isAtPosition(Constants.ClawArmConstants.CORAL_HUMAN_POS);
    }

    public boolean isAtL1CoralScoringPos() {
        return isAtPosition(Constants.ClawArmConstants.L1_CORAL_SCORING_POS);
    }

    public boolean isAtL2CoralScoringPos() {
        return isAtPosition(Constants.ClawArmConstants.L2_CORAL_SCORING_POS);
    }

    public boolean isAtL1AlgaeIntakePos() {
        return isAtPosition(Constants.ClawArmConstants.L1_ALGAE_INTAKE_POS);
    }

    public boolean isAtL2AlgaeIntakePos() {
        return isAtPosition(Constants.ClawArmConstants.L2_ALGAE_INTAKE_POS);
    }

    public boolean isAtAlgaeTransferPos() {
        return isAtPosition(Constants.ClawArmConstants.ALGAE_TRANSFER_POS);
    }

    public void moveClear(){
        setPos(Constants.ClawArmConstants.CLEARANCE_POS);
    }

    public boolean isClear(){
        if(getPosition() > Constants.ClawArmConstants.CLEARANCE_POS)
            return true;
        return false;
    }
    



}
