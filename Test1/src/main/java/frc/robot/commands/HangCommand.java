package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Lifter;


public class HangCommand extends SequentialCommandGroup{
    public HangCommand(Lifter m_lifter) {
        addRequirements(m_lifter);
        addCommands(
            Commands.runOnce(() -> m_lifter.moveHangPos()),
            new WaitUntilCommand(() -> m_lifter.isAtHangPos())
        );
        
    }
}
