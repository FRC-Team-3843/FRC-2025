package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;

public class AlgaeScoreNetCommand extends Command {
    public ClawArm clawArm;
    public ClawIntake clawIntake;
    public ClawElevator clawElevator;

    public void algaeL2IntakeCommand (ClawArm clawArm, ClawIntake clawIntake, ClawElevator clawElevator, double speed) {
        this.clawArm = clawArm;
        this.clawIntake = clawIntake;
        this.clawElevator = clawElevator;
        clawElevator.scoringPos();
        clawArm.algaescorePos();
        clawIntake.intake(-speed);
    }

}
