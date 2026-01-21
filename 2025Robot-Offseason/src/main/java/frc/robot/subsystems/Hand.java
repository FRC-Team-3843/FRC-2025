package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hand extends SubsystemBase{
    TalonFX lifterIntakeMotor = new TalonFX(Constants.HandConstants.MOTOR_ID);
    TalonFXConfiguration lifterIntakeConfig = new TalonFXConfiguration();
    
    public Hand() {
        lifterIntakeConfig.MotorOutput.Inverted = Constants.HandConstants.HAND_MOTOR_INVERT;
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    public void setMotor(double speed, boolean direction) {
        if (direction) lifterIntakeMotor.set(speed);
        else lifterIntakeMotor.set( -speed);
    }

    public void intake(double speed) {
        setMotor(speed, false);
    }
    public void outtake(double speed) {
        setMotor(speed, true); // Or vise-versa
    }
    public void stop() {
        setMotor(0, false);
    }
}
