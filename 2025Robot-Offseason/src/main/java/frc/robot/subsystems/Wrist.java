package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Wrist extends SubsystemBase {
    private final Servo wristServo;

    public Wrist() {
        wristServo = new Servo(Constants.WristConstants.SERVO_CHANNEL);
        wristServo.setAngle(Constants.WristConstants.WRIST_MIN_ANGLE); // home at construction
    }

    /* Set wrist to an absolute angle (0–200). */
    public void setAngle(double angle) {
        double clamped = Math.max(Constants.WristConstants.WRIST_MIN_ANGLE,
                           Math.min(Constants.WristConstants.WRIST_MAX_ANGLE, angle));
        double scaled = clamped / Constants.WristConstants.WRIST_MAX_ANGLE; // 0.0–1.0 scale
        wristServo.set(scaled);
    }

    /* Return current wrist position as angle. */
    public double getAngle() {
        return wristServo.get() * Constants.WristConstants.WRIST_MAX_ANGLE;
    }

    public void moveIntakePos() {
        setAngle(0);
    }

    public void moveStowedPos() {
        setAngle(0);
    }

    public void moveL1Pos() {
        setAngle(30);
    }

    public void moveL2Pos() {
        setAngle(35);
    }

    public void moveL3Pos() {
        setAngle(40);
    }

    public void moveL4Pos() {
        setAngle(120);
    }
    
}
