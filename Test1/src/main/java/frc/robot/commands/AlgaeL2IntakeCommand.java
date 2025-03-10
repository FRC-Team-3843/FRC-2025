package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;

public class AlgaeL2IntakeCommand extends Command {
    public ClawIntake clawIntake;
    public ClawArm clawArm;
    public ClawElevator clawElevator;

    public void algaeL2IntakeCommand (ClawArm clawArm, ClawIntake clawIntake, ClawElevator clawElevator, double speed) {
        this.clawArm = clawArm;
        this.clawIntake = clawIntake;
        this.clawElevator = clawElevator;
        //clawElevator.L2AlgaeIntakePos();
        //clawArm.straightPos();
        clawIntake.intake(speed);
    }
}
