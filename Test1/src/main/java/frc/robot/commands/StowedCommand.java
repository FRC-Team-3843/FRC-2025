package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class StowedCommand extends Command{
 public ClawIntake clawIntake;
    public Lifter lifter;
    public LifterIntake lifterIntake;

    public void stowedAfterCommand (ClawIntake clawIntake, Lifter lifter, LifterIntake lifterIntake) {
        this.clawIntake = clawIntake;
        this.lifter = lifter;
        this.lifterIntake = lifterIntake;
        
        lifterIntake.stop();
        clawIntake.stop();
        lifter.moveStowedPos();
    }

}
