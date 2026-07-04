package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lifter extends SubsystemBase {
    private TalonSRX lifterRightMotor;
    private TalonSRX lifterLeftMotor;

    // Latched when the two sides diverge past DIVERGENCE_FAULT_DEGREES. The sides are
    // mechanically linked — divergence means one side is stalled, slipping, or fighting the
    // other, and continuing to drive will break the mechanism. Cleared only by code restart.
    private boolean divergenceFault = false;

    public Lifter(){
        if (!Constants.SubsystemEnables.LIFTER_ENABLED) return;
        lifterRightMotor = new TalonSRX(Constants.LifterConstants.RIGHT_MOTOR_ID);
        lifterLeftMotor = new TalonSRX(Constants.LifterConstants.LEFT_MOTOR_ID);
        lifterRightMotor.configFactoryDefault();
        lifterLeftMotor.configFactoryDefault();

        lifterRightMotor.setNeutralMode(NeutralMode.Brake);
        lifterLeftMotor.setNeutralMode(NeutralMode.Brake);

        lifterRightMotor.setInverted(Constants.LifterConstants.RIGHT_MOTOR_INVERT);
        lifterLeftMotor.setInverted(Constants.LifterConstants.LEFT_MOTOR_INVERT);

        lifterRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        lifterRightMotor.setSensorPhase(Constants.LifterConstants.RIGHT_SENSOR_PHASE);

        lifterLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        lifterLeftMotor.setSensorPhase(Constants.LifterConstants.LEFT_SENSOR_PHASE);

        // Boot position defines zero — mechanism must be at stow before power-on.
        lifterRightMotor.setSelectedSensorPosition(0, 0, 30);
        lifterLeftMotor.setSelectedSensorPosition(0, 0, 30);

        configureOutputLimits(lifterRightMotor);
        configureOutputLimits(lifterLeftMotor);

        configureGainsAndMotion(lifterRightMotor);
        configureGainsAndMotion(lifterLeftMotor);
    }

    private void configureOutputLimits(TalonSRX motor) {
        motor.configNominalOutputForward(0, 30);
        motor.configNominalOutputReverse(0, 30);
        motor.configPeakOutputForward(Constants.LifterConstants.PEAK_OUTPUT, 30);
        motor.configPeakOutputReverse(-Constants.LifterConstants.PEAK_OUTPUT, 30);

        motor.configContinuousCurrentLimit(Constants.LifterConstants.CONTINUOUS_CURRENT_LIMIT, 30);
        motor.configPeakCurrentLimit(Constants.LifterConstants.PEAK_CURRENT_LIMIT, 30);
        motor.configPeakCurrentDuration(Constants.LifterConstants.PEAK_CURRENT_DURATION_MS, 30);
        motor.enableCurrentLimit(true);

        motor.configForwardSoftLimitThreshold(Constants.LifterConstants.FORWARD_SOFT_LIMIT_TICKS, 30);
        motor.configReverseSoftLimitThreshold(Constants.LifterConstants.REVERSE_SOFT_LIMIT_TICKS, 30);
        motor.configForwardSoftLimitEnable(true, 30);
        motor.configReverseSoftLimitEnable(true, 30);
    }

    private void configureGainsAndMotion(TalonSRX motor) {
        motor.selectProfileSlot(0, 0);
        motor.config_kF(0, Constants.LifterConstants.MOTOR_F, 30);
        motor.config_kP(0, Constants.LifterConstants.MOTOR_P, 30);
        motor.config_kI(0, Constants.LifterConstants.MOTOR_I, 30);
        motor.config_kD(0, Constants.LifterConstants.MOTOR_D, 30);

        motor.configMotionCruiseVelocity((int)Constants.LifterConstants.MOTOR_MAX_VELOCITY, 30);
        motor.configMotionAcceleration((int)Constants.LifterConstants.MOTOR_MAX_ACCELERATION, 30);
    }

    @Override
    public void periodic() {
        if (!Constants.SubsystemEnables.LIFTER_ENABLED) return;

        double rightTicks = lifterRightMotor.getSelectedSensorPosition();
        double leftTicks = lifterLeftMotor.getSelectedSensorPosition();
        double deltaDegrees = (rightTicks - leftTicks) / Constants.LifterConstants.TICKS_PER_DEGREE;

        if (!divergenceFault && Math.abs(deltaDegrees) > Constants.LifterConstants.DIVERGENCE_FAULT_DEGREES) {
            divergenceFault = true;
            DriverStation.reportError(
                "LIFTER DIVERGENCE FAULT: sides split " + deltaDegrees
                    + " deg (right " + rightTicks + " / left " + leftTicks
                    + " ticks). Motors neutralized until code restart.", false);
        }
        if (divergenceFault) {
            // Re-assert neutral every loop so a latched Motion Magic setpoint can't re-engage.
            lifterRightMotor.set(ControlMode.PercentOutput, 0);
            lifterLeftMotor.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("Lifter/RightTicks", rightTicks);
        SmartDashboard.putNumber("Lifter/LeftTicks", leftTicks);
        SmartDashboard.putNumber("Lifter/RightDeg", rightTicks / Constants.LifterConstants.TICKS_PER_DEGREE);
        SmartDashboard.putNumber("Lifter/LeftDeg", leftTicks / Constants.LifterConstants.TICKS_PER_DEGREE);
        SmartDashboard.putNumber("Lifter/DeltaDeg", deltaDegrees);
        SmartDashboard.putNumber("Lifter/RightOutput", lifterRightMotor.getMotorOutputPercent());
        SmartDashboard.putNumber("Lifter/LeftOutput", lifterLeftMotor.getMotorOutputPercent());
        SmartDashboard.putBoolean("Lifter/DivergenceFault", divergenceFault);
    }

    public void setPos(double targetPositionDegrees) {
        if (!Constants.SubsystemEnables.LIFTER_ENABLED) return;
        if (divergenceFault) return;
        double clampedDegrees = MathUtil.clamp(targetPositionDegrees, 0, Constants.LifterConstants.MAX_POS_DEGREES);
        double targetTicks = clampedDegrees * Constants.LifterConstants.TICKS_PER_DEGREE;
        lifterLeftMotor.set(ControlMode.MotionMagic, targetTicks);
        lifterRightMotor.set(ControlMode.MotionMagic, targetTicks);
    }

    /** Open-loop bench jog; both sides get the same duty. Watchdog in periodic() still applies. */
    public void jog(double percentOutput) {
        if (!Constants.SubsystemEnables.LIFTER_ENABLED) return;
        if (divergenceFault) return;
        double clamped = MathUtil.clamp(percentOutput,
            -Constants.LifterConstants.JOG_MAX_OUTPUT, Constants.LifterConstants.JOG_MAX_OUTPUT);
        lifterLeftMotor.set(ControlMode.PercentOutput, clamped);
        lifterRightMotor.set(ControlMode.PercentOutput, clamped);
    }

    public void stopMotor(){
        if (!Constants.SubsystemEnables.LIFTER_ENABLED) return;
        lifterLeftMotor.set(ControlMode.PercentOutput, 0);
        lifterRightMotor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isFaulted() {
        return divergenceFault;
    }

    public double getPosition() {
        if (!Constants.SubsystemEnables.LIFTER_ENABLED) return 0;
        double rawTicks = (lifterRightMotor.getSelectedSensorPosition() + lifterLeftMotor.getSelectedSensorPosition()) / 2.0;
        return rawTicks / Constants.LifterConstants.TICKS_PER_DEGREE;
    }

    public boolean isAtPosition(double position){
        if(Math.abs(getPosition() - position) < 2.0)
            return true;
        return false;
    }

    public void moveStowedPos() {
        setPos(Constants.LifterConstants.STOWED_POS);
    }

    public void moveClimbingApproachPos() {
        setPos(Constants.LifterConstants.CLIMBING_APPROACH_POS);
    }

    public void moveCoralIntakePos() {
        setPos(Constants.LifterConstants.CORAL_INTAKE_POS);
    }

    public void moveAlgaeIntakePos() {
        setPos(Constants.LifterConstants.ALGAE_INTAKE_POS);
    }

    public void moveClear() {
        setPos(Constants.LifterConstants.CLEARANCE_POS);
    }

    public void moveCoralScorePos() {
        setPos(Constants.LifterConstants.CORAL_SCORE_POS);
    }

    public void moveHangPos() {
        setPos(Constants.LifterConstants.HANG_POS);
    }

    public boolean isAtStowedPos() {
        return isAtPosition(Constants.LifterConstants.STOWED_POS);
    }

    public boolean isAtClimbingApproachPos() {
        return isAtPosition(Constants.LifterConstants.CLIMBING_APPROACH_POS);
    }

    public boolean isAtCoralIntakePos() {
        return isAtPosition(Constants.LifterConstants.CORAL_INTAKE_POS);
    }

    public boolean isAtAlgaeIntakePos() {
        return isAtPosition(Constants.LifterConstants.ALGAE_INTAKE_POS);
    }

    public boolean isAtCoralScorePos() {
        return isAtPosition(Constants.LifterConstants.CORAL_SCORE_POS);
    }

    public boolean isAtHangPos() {
        return isAtPosition(Constants.LifterConstants.HANG_POS);
    }

    public boolean isClear(){
        if(getPosition() > Constants.LifterConstants.CLEARANCE_POS)
            return true;
        return false;
    }

}
