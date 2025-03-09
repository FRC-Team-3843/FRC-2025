package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class AlgaeGroundIntakeCommand extends Command {
    public Lifter lifter;
    public ClawArm clawArm;
    public ClawIntake clawIntake;
    public LifterIntake lifterIntake;

    public void coralIntakeCommand(Lifter lifter, LifterIntake lifterIntake, ClawIntake clawIntake, double speed) {
    this.lifter = lifter;
    this.lifterIntake = lifterIntake;
    this.clawIntake = clawIntake;
    lifter.moveAlgaeIntakePos();
    lifterIntake.intake(speed);
    clawIntake.intake(speed);
}
}
