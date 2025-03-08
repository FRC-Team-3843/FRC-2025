package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawIntake extends SubsystemBase {
    TalonSRX clawIntakeMotor = new TalonSRX(Constants.ClawConstants.CLAW_INTAKE_MOTOR_ID);

    public ClawIntake() {
        clawIntakeMotor.setInverted(Constants.ClawConstants.CLAW_INTAKE_MOTOR_INVERT);
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    public void setIntakeMotor(double speed, boolean direction) {
        if (direction) clawIntakeMotor.set(TalonSRXControlMode.PercentOutput, -speed);
        else clawIntakeMotor.set(TalonSRXControlMode.PercentOutput, speed);
    }

    public void intake(double speed) {
        setIntakeMotor(speed, false);
    }
    public void outtake(double speed) {
        setIntakeMotor(speed, true);
    }
    public void stop() {
        setIntakeMotor(0, false);
    }

}
