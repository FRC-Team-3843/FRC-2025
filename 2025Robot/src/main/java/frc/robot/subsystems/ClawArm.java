package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawArm extends SubsystemBase{
    private TalonSRX clawArmMotor;

    public ClawArm () {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        clawArmMotor = new TalonSRX(Constants.ClawArmConstants.MOTOR_ID);
        clawArmMotor.configFactoryDefault();
        clawArmMotor.setNeutralMode(NeutralMode.Brake);
        clawArmMotor.setInverted(Constants.ClawArmConstants.MOTOR_INVERT);
        clawArmMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        clawArmMotor.setSensorPhase(Constants.ClawArmConstants.SENSOR_PHASE);

        // Boot position defines zero — arm must be at stow before power-on.
        clawArmMotor.setSelectedSensorPosition(0, 0, 30);

        clawArmMotor.configNominalOutputForward(0, 30);
        clawArmMotor.configNominalOutputReverse(0, 30);
        clawArmMotor.configPeakOutputForward(Constants.ClawArmConstants.PEAK_OUTPUT, 30);
        clawArmMotor.configPeakOutputReverse(-Constants.ClawArmConstants.PEAK_OUTPUT, 30);

        clawArmMotor.configContinuousCurrentLimit(Constants.ClawArmConstants.CONTINUOUS_CURRENT_LIMIT, 30);
        clawArmMotor.configPeakCurrentLimit(Constants.ClawArmConstants.PEAK_CURRENT_LIMIT, 30);
        clawArmMotor.configPeakCurrentDuration(Constants.ClawArmConstants.PEAK_CURRENT_DURATION_MS, 30);
        clawArmMotor.enableCurrentLimit(true);

        clawArmMotor.configForwardSoftLimitThreshold(Constants.ClawArmConstants.FORWARD_SOFT_LIMIT_TICKS, 30);
        clawArmMotor.configReverseSoftLimitThreshold(Constants.ClawArmConstants.REVERSE_SOFT_LIMIT_TICKS, 30);
        clawArmMotor.configForwardSoftLimitEnable(true, 30);
        clawArmMotor.configReverseSoftLimitEnable(true, 30);

        clawArmMotor.selectProfileSlot(0, 0);
        clawArmMotor.config_kF(0, Constants.ClawArmConstants.MOTOR_F, 30);
        clawArmMotor.config_kP(0, Constants.ClawArmConstants.MOTOR_P, 30);
        clawArmMotor.config_kI(0, Constants.ClawArmConstants.MOTOR_I, 30);
        clawArmMotor.config_kD(0, Constants.ClawArmConstants.MOTOR_D, 30);

        clawArmMotor.configMotionCruiseVelocity((int)Constants.ClawArmConstants.MOTOR_MAX_VELOCITY, 30);
        clawArmMotor.configMotionAcceleration((int)Constants.ClawArmConstants.MOTOR_MAX_ACCELERATION, 30);
    }

    @Override
    public void periodic() {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        SmartDashboard.putNumber("ClawArm/Ticks", clawArmMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("ClawArm/Degrees", getPosition());
        SmartDashboard.putNumber("ClawArm/Output", clawArmMotor.getMotorOutputPercent());
    }

    public void setPos(double targetPositionDegrees) {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        double clampedDegrees = MathUtil.clamp(targetPositionDegrees, 0, Constants.ClawArmConstants.MAX_POS_DEGREES);
        double targetTicks = clampedDegrees * Constants.ClawArmConstants.TICKS_PER_DEGREE;
        clawArmMotor.set(ControlMode.MotionMagic, targetTicks);
    }

    /** Open-loop bench jog. */
    public void jog(double percentOutput) {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        double clamped = MathUtil.clamp(percentOutput,
            -Constants.ClawArmConstants.JOG_MAX_OUTPUT, Constants.ClawArmConstants.JOG_MAX_OUTPUT);
        clawArmMotor.set(ControlMode.PercentOutput, clamped);
    }

    public void stop() {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        clawArmMotor.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Soft limits are relative to boot zero — if the arm powered on away from stow they block
     * homing, so the bench homing jog disables them while held and re-enables on release.
     */
    public void setSoftLimitsEnabled(boolean enabled) {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        clawArmMotor.configForwardSoftLimitEnable(enabled, 30);
        clawArmMotor.configReverseSoftLimitEnable(enabled, 30);
    }

    /** Re-zero at the current position (arm physically at stow) — no power cycle needed. */
    public void zeroEncoder() {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return;
        clawArmMotor.setSelectedSensorPosition(0, 0, 30);
    }

    public double getPosition() {
        if (!Constants.SubsystemEnables.CLAW_ARM_ENABLED) return 0;
        return clawArmMotor.getSelectedSensorPosition() / Constants.ClawArmConstants.TICKS_PER_DEGREE;
    }

    public boolean isAtPosition(double position){
        if(Math.abs(getPosition() - position) < 2.0)
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

    public void moveDeployLineUpPos() {
        setPos(Constants.ClawArmConstants.DEPLOY_LINE_UP_POS);
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

    public boolean isAtDeployLineUpPos() {
        return isAtPosition(Constants.ClawArmConstants.DEPLOY_LINE_UP_POS);
    }

    public void moveClear(){
        setPos(Constants.ClawArmConstants.CLEARANCE_POS);
    }

    public boolean isClear(){
        if(getPosition() > Constants.ClawArmConstants.CLEARANCE_POS - 2)
            return true;
        return false;
    }
    



}
