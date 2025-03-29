package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawIntake extends SubsystemBase {
    TalonFX clawIntakeMotor = new TalonFX(Constants.ClawIntakeConstants.MOTOR_ID);
     TalonFXConfiguration clawIntakeConfig = new TalonFXConfiguration();



    public ClawIntake() {
        clawIntakeConfig.MotorOutput.Inverted = Constants.ClawIntakeConstants.MOTOR_INVERT;
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    private void setMotor(double speed, boolean direction) {
        if (direction) clawIntakeMotor.set(speed);
        else clawIntakeMotor.set( -speed);
    }

    public void intake(double speed) {
        setMotor(speed, false);
    }
    public void outtake(double speed) {
        setMotor(speed, true);
    }
    public void stop() {
        setMotor(0, false);
    }

}
