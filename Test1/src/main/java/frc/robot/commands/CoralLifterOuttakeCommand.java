package frc.robot.commands;
import java.io.Console;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class CoralLifterOuttakeCommand extends Command {
public Lifter lifter;
public LifterIntake lifterIntake;
//public static int positionVariation = 2;
public double speed;

public void coralOuttakeCommand(Lifter lifter, LifterIntake lifterIntake, double speed) {
    this.lifter = lifter;
    this.speed = speed;
    this.lifterIntake = lifterIntake;

    lifter.moveCoralScorePos();
}

/*public void execute() {
    System.out.println("Execute is running here");
    lifter.moveCoralScorePos();
}

public boolean isFinished() {
    System.out.println("Hey! This is working!");
    if (lifter.getPosition() > Constants.LifterConstants.CORAL_SCORE_POS - positionVariation && lifter.getPosition() < Constants.LifterConstants.CORAL_SCORE_POS + positionVariation)
        return true;
        System.out.println("Look here");
        
    return false;
}

public void end(boolean interrupted) {
    if (interrupted)
    return;
    lifterIntake.outtake(speed);
    try {
        wait(2000);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    lifterIntake.stop();*/



    public void reset(Lifter lifter, LifterIntake lifterIntake) {
        this.lifter = lifter;
        this.lifterIntake = lifterIntake;
        lifterIntake.outtake(1);
    }

}