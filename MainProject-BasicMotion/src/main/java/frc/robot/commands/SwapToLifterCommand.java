package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class SwapToLifterCommand extends SequentialCommandGroup{

        public SwapToLifterCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake){

        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            Commands.runOnce(() -> m_clawArm.moveClear()),
            new WaitUntilCommand(() -> m_clawArm.isClear()),
            Commands.runOnce(() -> m_clawElevator.moveStowedPos()),
            Commands.runOnce(() -> m_lifter.moveClear()),
            new WaitUntilCommand(() -> m_lifter.isClear()),
            Commands.runOnce(() -> m_clawArm.moveStowedPos())
        );
    }
}
