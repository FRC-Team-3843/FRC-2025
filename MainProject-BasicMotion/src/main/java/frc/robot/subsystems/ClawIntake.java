package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawIntake extends SubsystemBase {
    TalonSRX clawIntakeMotor = new TalonSRX(Constants.ClawIntakeConstants.MOTOR_ID);

    public ClawIntake() {
        clawIntakeMotor.setInverted(Constants.ClawIntakeConstants.MOTOR_INVERT);
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    private void setMotor(double speed, boolean direction) {
        if (direction) clawIntakeMotor.set(TalonSRXControlMode.PercentOutput, -speed);
        else clawIntakeMotor.set(TalonSRXControlMode.PercentOutput, speed);
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
