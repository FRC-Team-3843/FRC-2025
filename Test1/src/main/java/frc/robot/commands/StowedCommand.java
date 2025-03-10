package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class StowedCommand extends SequentialCommandGroup{

    public StowedCommand (ClawIntake m_clawIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, LifterIntake m_lifterIntake) {
        addRequirements(m_clawArm);
        addRequirements(m_clawIntake);
        addRequirements(m_clawElevator);
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addCommands(
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_clawIntake.stop()),
            Commands.runOnce(() -> m_lifter.moveStowedPos()));
    }

}
