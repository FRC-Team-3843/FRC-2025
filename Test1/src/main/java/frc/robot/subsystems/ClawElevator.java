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

public class ClawElevator extends SubsystemBase{
    private final TalonFX elevatorMotor = new TalonFX(Constants.ClawElevatorConstants.MOTOR_ID);
    private final MotionMagicVoltage elevatorMM = new MotionMagicVoltage(0);
    public final TalonFXConfiguration elevatorConfig = new TalonFXConfiguration();
    
    public ClawElevator () {
        FeedbackConfigs fdb = elevatorConfig.Feedback;
        fdb.SensorToMechanismRatio = 1;

        MotionMagicConfigs elevatorMMConfig = elevatorConfig.MotionMagic;
        elevatorMMConfig.MotionMagicAcceleration = 100;
        elevatorMMConfig.MotionMagicCruiseVelocity = 500;

        Slot0Configs elevatorSlot0Configs = elevatorConfig.Slot0;
        elevatorSlot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
        elevatorSlot0Configs.kV = 0.012; // A velocity target of 1 rps results in 0.12 V output
        elevatorSlot0Configs.kA = 0.001; // An acceleration of 1 rps/s requires 0.01 V output
        elevatorSlot0Configs.kP = 5; // A position error of 0.2 rotations results in 12 V output
        elevatorSlot0Configs.kI = 0; // No output for integrated error
        elevatorSlot0Configs.kD = 0; // A velocity error of 1 rps results in 0.5 V output

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = elevatorMotor.getConfigurator().apply(elevatorConfig);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }

    }

    private void setPos(double targetPosition) {
         elevatorMM.withPosition(targetPosition);
         elevatorMotor.setControl(elevatorMM);
    }

    public double getPosition() {
        return elevatorMotor.getPosition().getValue().magnitude();
    }

    private boolean isAtPosition(double position){
        if(Math.abs(getPosition() - position) < 2)
            return true;
        return false;
    }

    public void moveStowedPos() { 
        setPos(Constants.ClawElevatorConstants.STOWED_POS);
    }

    public void moveL1CoralScoringPos() {
        setPos(Constants.ClawElevatorConstants.L1_CORAL_SCORING_POS);
    }

    public void moveL1AlgaeIntakePos() {
        setPos(Constants.ClawElevatorConstants.L1_ALGAE_INTAKE_POS);
    }

    public void moveCoralHumanPos() {
        setPos(Constants.ClawElevatorConstants.CORAL_HUMAN_POS);
    }

    public void moveAlgaeTransferPos() {
        setPos(Constants.ClawElevatorConstants.ALGAE_TRANSFER_POS);
    }

    public void moveL2CoralScoringPos() {
        setPos(Constants.ClawElevatorConstants.L2_CORAL_SCORING_POS);
    }

    public void moveL2AlgaeIntakePos() {
        setPos(Constants.ClawElevatorConstants.L2_ALGAE_INTAKE_POS);
    }

    public void moveClimbingApproachPos() {
        setPos(Constants.ClawElevatorConstants.CLIMBING_APPROACH_POS);
    }
    
    public void moveAlgaeScorePos() {
        setPos(Constants.ClawElevatorConstants.ALGAE_SCORE_POS);
    }   

    public boolean isAtStowedPos() {
        return isAtPosition(Constants.ClawElevatorConstants.STOWED_POS);
    }

    public boolean isAtL1CoralScoringPos() {
        return isAtPosition(Constants.ClawElevatorConstants.L1_CORAL_SCORING_POS);
    }   

    public boolean isAtL1AlgaeIntakePos() {
        return isAtPosition(Constants.ClawElevatorConstants.L1_ALGAE_INTAKE_POS);
    }   

    public boolean isAtCoralHumanPos() {
        return isAtPosition(Constants.ClawElevatorConstants.CORAL_HUMAN_POS);
    }

    public boolean isAtAlgaeTransferPos() {
        return isAtPosition(Constants.ClawElevatorConstants.ALGAE_TRANSFER_POS);
    }

    public boolean isAtL2CoralScoringPos() {
        return isAtPosition(Constants.ClawElevatorConstants.L2_CORAL_SCORING_POS);
    }

    public boolean isAtL2AlgaeIntakePos() {
        return isAtPosition(Constants.ClawElevatorConstants.L2_ALGAE_INTAKE_POS);
    }

    public boolean isAtClimbingApproachPos() {
        return isAtPosition(Constants.ClawElevatorConstants.CLIMBING_APPROACH_POS);
    }

    public boolean isAtAlgaeScorePos() {
        return isAtPosition(Constants.ClawElevatorConstants.ALGAE_SCORE_POS);
    }

    




}
