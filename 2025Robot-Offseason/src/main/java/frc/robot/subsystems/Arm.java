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

public class Arm extends SubsystemBase{
    private final TalonFX armMotor = new TalonFX(Constants.ArmConstants.MOTOR_ID);
    private final MotionMagicVoltage armMM = new MotionMagicVoltage(0);
    public final TalonFXConfiguration armConfig = new TalonFXConfiguration();
    
    public Arm () {
        FeedbackConfigs fdb = armConfig.Feedback;
        fdb.SensorToMechanismRatio = 1;

        MotionMagicConfigs armMMConfig = armConfig.MotionMagic;
        armMMConfig.MotionMagicAcceleration = 100;
        armMMConfig.MotionMagicCruiseVelocity = 500;

        Slot0Configs armSlot0Configs = armConfig.Slot0;
        armSlot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
        armSlot0Configs.kV = 0.012; // A velocity target of 1 rps results in 0.12 V output
        armSlot0Configs.kA = 0.001; // An acceleration of 1 rps/s requires 0.01 V output
        armSlot0Configs.kP = 5; // A position error of 0.2 rotations results in 12 V output
        armSlot0Configs.kI = 0; // No output for integrated error
        armSlot0Configs.kD = 0; // A velocity error of 1 rps results in 0.5 V output

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = armMotor.getConfigurator().apply(armConfig);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }

    }

    public void setPos(double targetPosition) {
         armMM.withPosition(targetPosition);
         armMotor.setControl(armMM);
    }

    public double getPosition() {
        return armMotor.getPosition().getValue().magnitude();
    }

    public boolean isAtPosition(double position){
        if(Math.abs(getPosition() - position) < 4)
            return true;
        return false;
    }

    public void moveStowedPos() { 
        setPos(Constants.ArmConstants.STOWED_POS);
    }

    public void moveL1Pos() {
        setPos(Constants.ArmConstants.L1_POS);
    }

    public void moveL2Pos() {
        setPos(Constants.ArmConstants.L2_POS);
    }

    public void moveL3Pos() {
        setPos(Constants.ArmConstants.L3_POS);
    }

    public void moveL4Pos() {
        setPos(Constants.ArmConstants.L4_POS);
    }

    public void moveIntakePos() {
        setPos(Constants.ArmConstants.INTAKE_POS);
    }

////////////////////////////////////////////////////////////////////////
////            //////        /////////////     //////              ////
////////    ///////     /////////////////        //////////     ////////
////////    ///////    /////////////////    //    /////////     ////////
////////    ////////     ///////////////    //    /////////     ////////
////////    //////////    //////////////          /////////     ////////
////////    /////////     //////////////    //    /////////     ////////
////            //      ////////////////    //    /////////     ////////
////////////////////////////////////////////////////////////////////////

    public boolean isAtL1Pos() {
        return isAtPosition(Constants.ArmConstants.L1_POS);
    }

    public boolean isAtL2Pos() {
        return isAtPosition(Constants.ArmConstants.L2_POS);
    }

    public boolean isAtL3Pos() {
        return isAtPosition(Constants.ArmConstants.L3_POS);
    }

    public boolean isAtL4Pos() {
        return isAtPosition(Constants.ArmConstants.L4_POS);
    }

    public boolean isAtIntakePos() {
        return isAtPosition(Constants.ArmConstants.INTAKE_POS);
    }

    public boolean isAtStowedPos() {
        return isAtPosition(Constants.ArmConstants.STOWED_POS);
    }
}
