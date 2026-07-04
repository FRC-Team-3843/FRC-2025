package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LifterIntake extends SubsystemBase{
    TalonSRX lifterIntakeMotor;
    
    public LifterIntake() {
        if (!Constants.SubsystemEnables.LIFTER_INTAKE_ENABLED) return;
        lifterIntakeMotor = new TalonSRX(Constants.LifterIntakeConstants.MOTOR_ID);
        lifterIntakeMotor.setInverted(Constants.LifterIntakeConstants.LIFTER_MOTOR_INVERT);
    }

    public void periodic() {
        // Code here gets executed perodically
    }

    public void setMotor(double speed, boolean direction) {
        if (!Constants.SubsystemEnables.LIFTER_INTAKE_ENABLED) return;
        if (direction) lifterIntakeMotor.set(ControlMode.PercentOutput, speed);
        else lifterIntakeMotor.set(ControlMode.PercentOutput, -speed);
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
