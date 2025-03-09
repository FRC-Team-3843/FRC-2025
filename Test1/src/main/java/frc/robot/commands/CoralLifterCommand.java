package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class CoralLifterCommand extends Command {
public Lifter lifter;
public LifterIntake lifterIntake;
public double speed;



public void coralIntakeCommand(Lifter lifter, LifterIntake lifterIntake, double speed) {
    this.lifter = lifter;
    this.lifterIntake = lifterIntake;
    System.out.println("Sending regards!");
    lifter.moveCoralIntakePos();
    lifterIntake.intake(speed);
}




/*public void coralOutakeCommand(Lifter lifter, LifterIntake lifterIntake) {
    this.lifter = lifter;
    this.lifterIntake = lifterIntake;

    lifter.moveCoralScorePos();
    double lifterPos = lifter.getMotorPosition();
    System.out.println(lifterPos);
    if (lifterPos > 80 & lifterPos < 90) {
        lifterIntake.outtake(.2);
    }
    
} */

public void reset(Lifter lifter, LifterIntake lifterIntake) {
    this.lifter = lifter;
    this.lifterIntake = lifterIntake;

    lifter.moveStowedPos();
    lifterIntake.stop();
}
}
