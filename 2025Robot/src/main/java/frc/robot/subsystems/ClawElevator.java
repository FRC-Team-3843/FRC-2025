package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawElevator extends SubsystemBase{
    private TalonSRX elevatorMotor;

    public ClawElevator () {
        if (!Constants.SubsystemEnables.CLAW_ELEVATOR_ENABLED) return;
        elevatorMotor = new TalonSRX(Constants.ClawElevatorConstants.MOTOR_ID);
        elevatorMotor.configFactoryDefault();
        elevatorMotor.setNeutralMode(NeutralMode.Brake);
        elevatorMotor.setInverted(Constants.ClawElevatorConstants.MOTOR_INVERT);
        elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        elevatorMotor.setSensorPhase(Constants.ClawElevatorConstants.SENSOR_PHASE);

        // Boot position defines zero — elevator must be at the bottom before power-on.
        elevatorMotor.setSelectedSensorPosition(0, 0, 30);

        elevatorMotor.configNominalOutputForward(0, 30);
        elevatorMotor.configNominalOutputReverse(0, 30);
        elevatorMotor.configPeakOutputForward(Constants.ClawElevatorConstants.PEAK_OUTPUT, 30);
        elevatorMotor.configPeakOutputReverse(-Constants.ClawElevatorConstants.PEAK_OUTPUT, 30);

        elevatorMotor.configContinuousCurrentLimit(Constants.ClawElevatorConstants.CONTINUOUS_CURRENT_LIMIT, 30);
        elevatorMotor.configPeakCurrentLimit(Constants.ClawElevatorConstants.PEAK_CURRENT_LIMIT, 30);
        elevatorMotor.configPeakCurrentDuration(Constants.ClawElevatorConstants.PEAK_CURRENT_DURATION_MS, 30);
        elevatorMotor.enableCurrentLimit(true);

        elevatorMotor.configForwardSoftLimitThreshold(Constants.ClawElevatorConstants.FORWARD_SOFT_LIMIT_TICKS, 30);
        elevatorMotor.configReverseSoftLimitThreshold(Constants.ClawElevatorConstants.REVERSE_SOFT_LIMIT_TICKS, 30);
        elevatorMotor.configForwardSoftLimitEnable(true, 30);
        elevatorMotor.configReverseSoftLimitEnable(true, 30);

        elevatorMotor.selectProfileSlot(0, 0);
        elevatorMotor.config_kF(0, Constants.ClawElevatorConstants.MOTOR_F, 30);
        elevatorMotor.config_kP(0, Constants.ClawElevatorConstants.MOTOR_P, 30);
        elevatorMotor.config_kI(0, Constants.ClawElevatorConstants.MOTOR_I, 30);
        elevatorMotor.config_kD(0, Constants.ClawElevatorConstants.MOTOR_D, 30);

        elevatorMotor.configMotionCruiseVelocity((int)Constants.ClawElevatorConstants.MOTOR_MAX_VELOCITY, 30);
        elevatorMotor.configMotionAcceleration((int)Constants.ClawElevatorConstants.MOTOR_MAX_ACCELERATION, 30);
    }

    @Override
    public void periodic() {
        if (!Constants.SubsystemEnables.CLAW_ELEVATOR_ENABLED) return;
        SmartDashboard.putNumber("ClawElevator/Ticks", elevatorMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("ClawElevator/Inches", getPosition());
        SmartDashboard.putNumber("ClawElevator/Output", elevatorMotor.getMotorOutputPercent());
    }

    public void setPos(double targetPositionInches) {
        if (!Constants.SubsystemEnables.CLAW_ELEVATOR_ENABLED) return;
        double clampedInches = MathUtil.clamp(targetPositionInches, 0, Constants.ClawElevatorConstants.MAX_POS_INCHES);
        double targetTicks = clampedInches * Constants.ClawElevatorConstants.TICKS_PER_INCH;
        elevatorMotor.set(ControlMode.MotionMagic, targetTicks);
    }

    /** Open-loop bench jog. */
    public void jog(double percentOutput) {
        if (!Constants.SubsystemEnables.CLAW_ELEVATOR_ENABLED) return;
        double clamped = MathUtil.clamp(percentOutput,
            -Constants.ClawElevatorConstants.JOG_MAX_OUTPUT, Constants.ClawElevatorConstants.JOG_MAX_OUTPUT);
        elevatorMotor.set(ControlMode.PercentOutput, clamped);
    }

    public void stop() {
        if (!Constants.SubsystemEnables.CLAW_ELEVATOR_ENABLED) return;
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public double getPosition() {
        if (!Constants.SubsystemEnables.CLAW_ELEVATOR_ENABLED) return 0;
        return elevatorMotor.getSelectedSensorPosition() / Constants.ClawElevatorConstants.TICKS_PER_INCH;
    }

    public boolean isAtPosition(double position){
        if(Math.abs(getPosition() - position) < 0.5)
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

    public void moveTopPos() {
        setPos(Constants.ClawElevatorConstants.TOP_POS);
    }

    public boolean isAtTopPos() {
        return isAtPosition(Constants.ClawElevatorConstants.TOP_POS);
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
