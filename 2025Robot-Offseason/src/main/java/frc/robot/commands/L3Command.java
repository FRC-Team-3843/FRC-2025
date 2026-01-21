package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Wrist;

public class L3Command extends SequentialCommandGroup {

    public L3Command(Arm m_arm, Wrist m_wrist, Hand m_hand) {
        addRequirements(m_arm, m_wrist, m_hand);

        addCommands(
            Commands.runOnce(() -> m_arm.moveL3Pos()),
            new WaitUntilCommand(() -> m_arm.isAtL3Pos()),
            Commands.runOnce(() -> m_wrist.moveL3Pos())
        );

    }

}