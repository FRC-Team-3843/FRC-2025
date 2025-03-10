package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lifter extends SubsystemBase {
    SparkMax lifterRightMotor = new SparkMax(Constants.LifterConstants.LIFTER_RIGHT_MOTOR_ID, MotorType.kBrushless);
    SparkMax lifterLeftMotor = new SparkMax(Constants.LifterConstants.LIFTER_LEFT_MOTOR_ID, MotorType.kBrushless);
    SparkClosedLoopController lifterRightClosedLoopController = lifterRightMotor.getClosedLoopController();
    SparkClosedLoopController lifterLeftClosedLoopController = lifterLeftMotor.getClosedLoopController();
    RelativeEncoder lifterRightEncoder = lifterRightMotor.getEncoder();
    RelativeEncoder lifterLeftEncoder = lifterLeftMotor.getEncoder();
    SparkMaxConfig lifterRightConfig = new SparkMaxConfig();
    SparkMaxConfig lifterLeftConfig = new SparkMaxConfig();

    public Lifter(){
        lifterRightConfig.inverted(Constants.LifterConstants.LIFTER_RIGHT_MOTOR_INVERT);
        lifterLeftConfig.inverted(Constants.LifterConstants.LIFTER_LEFT_MOTOR_INVERT);
        lifterRightMotor.configure(lifterRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        lifterLeftMotor.configure(lifterLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        lifterRightConfig.encoder
            .positionConversionFactor(1)
            .velocityConversionFactor(1);
            
        lifterLeftConfig.encoder
            .positionConversionFactor(1)
            .velocityConversionFactor(1);

        /*
        * Configure the closed loop controller. We want to make sure we set the
        * feedback sensor as the primary encoder.
        */


        lifterRightConfig.closedLoop
            .minOutput(Constants.LifterConstants.DRIVE_MOTOR_MIN_OUTPUT)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // Set PID values for position control. We don't need to pass a closed loop
            // slot, as it will default to slot 0.
            .p(Constants.LifterConstants.LIFTER_RIGHT_P)
            .i(Constants.LifterConstants.LIFTER_RIGHT_I)
            .d(Constants.LifterConstants.LIFTER_RIGHT_D)
            .outputRange(-1, 1)

            // Set PID values for velocity control in slot 1
            .p(0.0001, ClosedLoopSlot.kSlot1)
            .i(0, ClosedLoopSlot.kSlot1)
            .d(0, ClosedLoopSlot.kSlot1)
            .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
            .outputRange(-1, 1, ClosedLoopSlot.kSlot1)

            .iZone(Constants.LifterConstants.DRIVE_MOTOR_IZ)
            .maxMotion.allowedClosedLoopError(Constants.LifterConstants.DRIVE_MOTOR_ALLOWED_ERROR)
            .maxVelocity(Constants.LifterConstants.DRIVE_MOTOR_MAX_VELOCITY)
            .maxAcceleration(Constants.LifterConstants.DRIVE_MOTOR_MAX_ACCELERATION);
    

        lifterLeftConfig.closedLoop
            .minOutput(Constants.LifterConstants.DRIVE_MOTOR_MIN_OUTPUT)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // Set PID values for position control. We don't need to pass a closed loop
            // slot, as it will default to slot 0.
            .p(Constants.LifterConstants.LIFTER_LEFT_P)
            .i(Constants.LifterConstants.LIFTER_LEFT_I)
            .d(Constants.LifterConstants.LIFTER_LEFT_D)
            .outputRange(-1, 1)

            // Set PID values for velocity control in slot 1
            .p(0.0001, ClosedLoopSlot.kSlot1)
            .i(0, ClosedLoopSlot.kSlot1)
            .d(0, ClosedLoopSlot.kSlot1)
            .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
            .outputRange(-1, 1, ClosedLoopSlot.kSlot1)

            .iZone(Constants.LifterConstants.DRIVE_MOTOR_IZ)
            .maxMotion.allowedClosedLoopError(Constants.LifterConstants.DRIVE_MOTOR_ALLOWED_ERROR)
            .maxVelocity(Constants.LifterConstants.DRIVE_MOTOR_MAX_VELOCITY)
            .maxAcceleration(Constants.LifterConstants.DRIVE_MOTOR_MAX_ACCELERATION);

        lifterRightMotor.configure(lifterRightConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        lifterLeftMotor.configure(lifterLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    private void setPos(double targetPosition) {
        lifterLeftClosedLoopController.setReference(targetPosition, ControlType.kMAXMotionPositionControl, ClosedLoopSlot.kSlot0);
        lifterRightClosedLoopController.setReference(targetPosition, ControlType.kMAXMotionPositionControl, ClosedLoopSlot.kSlot0);
        
     // System.out.println(targetPosition);
        
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

    public void moveClearancePos() {
        setPos(Constants.LifterConstants.ARM_CLEARANCE_POS);
    }

    public void moveCoralScorePos() {
        setPos(Constants.LifterConstants.CORAL_SCORE_POS);
    }

    public void moveHangPos() {
        setPos(Constants.LifterConstants.HANG_POS);
    }

    public double getPosition() {
        return lifterLeftEncoder.getPosition();
    }

    private double getMotorPosition() {
        // Get the average position of the motor in revolutions
        return ((lifterRightEncoder.getPosition() + lifterLeftEncoder.getPosition()) / 2);
    }

    private boolean isAtPosition(double position){
        if(Math.abs(getMotorPosition() - position) < 2)
            return true;
        return false;
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
        if(getMotorPosition() > Constants.LifterConstants.ARM_CLEARANCE_POS)
            return true;
        return false;
    }

    public void moveClear(){
        if (isClear())
            return;
        moveClearancePos();
    }

}