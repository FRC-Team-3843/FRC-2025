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
    private final TalonFX elevatorMotor = new TalonFX(Constants.ElevatorConstants.ELEVATOR_MOTOR_ID);
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
        System.out.println("hey?");
         elevatorMM.withPosition(targetPosition);
         elevatorMotor.setControl(elevatorMM);
    }
    public void bottomPos() {
        setPos(Constants.ElevatorConstants.BOTTOM_POS);
    }

    public void scoringPos() {
        setPos(Constants.ElevatorConstants.SCORING_POS);
    }

    public void L1CoralScoringPos() {
        setPos(Constants.ElevatorConstants.L1_CORAL_SCORING_POS);
    }

    public void L2CoralScoringPos() {
        setPos(Constants.ElevatorConstants.L2_CORAL_SCORING_POS);
    }
    public void L1AlgaeIntakePos() {
        setPos(Constants.ElevatorConstants.L1_ALGAE_INTAKE_POS);
    }
    public void L2AlgaeIntakePos() {
        setPos(Constants.ElevatorConstants.L2_ALGAE_INTAKE_POS);
    }

}
