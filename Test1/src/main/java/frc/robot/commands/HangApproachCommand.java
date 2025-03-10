package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class HangApproachCommand extends SequentialCommandGroup{
    public HangApproachCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_clawIntake);
        addCommands(
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_clawIntake.stop()),
            Commands.runOnce(() -> m_lifter.moveClimbingApproachPos()),
            new WaitUntilCommand(() -> m_lifter.isClear()),
            Commands.runOnce(() -> m_clawArm.moveClimbingApproachPos()),
            Commands.runOnce(() -> m_clawElevator.moveClimbingApproachPos())
        );
        
    }

}



