package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LifterIntake extends SubsystemBase{
    TalonFX lifterIntakeMotor = new TalonFX(Constants.LifterIntakeConstants.MOTOR_ID);
    TalonFXConfiguration lifterIntakeConfig = new TalonFXConfiguration();
    
    public LifterIntake() {
        lifterIntakeConfig.MotorOutput.Inverted = Constants.LifterIntakeConstants.LIFTER_MOTOR_INVERT;
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
        setMotor(speed, true);
    }
    public void stop() {
        setMotor(0, false);
    }
}
