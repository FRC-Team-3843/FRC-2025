package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawIntake extends SubsystemBase {
    TalonSRX clawIntakeMotor;

    public ClawIntake() {
        if (!Constants.SubsystemEnables.CLAW_INTAKE_ENABLED) return;
        clawIntakeMotor = new TalonSRX(Constants.ClawIntakeConstants.MOTOR_ID);
        clawIntakeMotor.setInverted(Constants.ClawIntakeConstants.MOTOR_INVERT);
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    private void setMotor(double speed, boolean direction) {
        if (!Constants.SubsystemEnables.CLAW_INTAKE_ENABLED) return;
        if (direction) clawIntakeMotor.set(ControlMode.PercentOutput, speed);
        else clawIntakeMotor.set(ControlMode.PercentOutput, -speed);
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
