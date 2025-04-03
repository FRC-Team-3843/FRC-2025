package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class AlgaeGroundIntakeCommand extends SequentialCommandGroup {

    public AlgaeGroundIntakeCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        System.out.println("Algae Ground Intake Command Running...");
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            Commands.runOnce(() -> m_lifter.moveAlgaeIntakePos()),
            new WaitUntilCommand(() -> m_lifter.isAtAlgaeIntakePos()),
            Commands.runOnce(() -> m_clawArm.moveAlgaeTransferPos()),
            new WaitUntilCommand(() -> m_clawArm.isAtAlgaeTransferPos()),
            Commands.runOnce(() -> m_clawElevator.moveStowedPos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtStowedPos())
        );
        System.out.println("Algae Ground Intake Command Complete!");

    }

}
