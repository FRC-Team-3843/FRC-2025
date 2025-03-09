package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;

public class AlgaeL1IntakeCommand extends Command {
    public ClawArm clawArm;
    public ClawIntake clawIntake;
    public ClawElevator clawElevator;

public void algaeL1IntakeCommand (ClawArm clawArm, ClawIntake clawIntake, ClawElevator clawElevator, double speed) {
    this.clawArm = clawArm;
    this.clawIntake = clawIntake;
    this.clawElevator = clawElevator;
    clawElevator.L1AlgaeIntakePos();
    clawArm.straightPos();
    clawIntake.intake(speed);
    }
}

