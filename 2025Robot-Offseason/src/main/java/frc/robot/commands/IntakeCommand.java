package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Wrist;

public class IntakeCommand extends SequentialCommandGroup {

    public IntakeCommand(Arm m_arm, Wrist m_wrist, Hand m_hand) {
        addRequirements(m_arm, m_wrist, m_hand);

        addCommands(
            Commands.runOnce(() -> m_arm.moveIntakePos()),
            new WaitUntilCommand(() -> m_arm.isAtIntakePos()),
            Commands.runOnce(() -> m_wrist.moveIntakePos())
        );

    }

}