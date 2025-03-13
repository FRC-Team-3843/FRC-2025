package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class CoralL2ScoreCommand extends SequentialCommandGroup{

    public CoralL2ScoreCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake){
        
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_clawIntake.stop()),
            new MoveElevator(m_clawArm, m_clawElevator, m_lifter, Constants.ClawElevatorConstants.L2_CORAL_SCORING_POS),
            new WaitUntilCommand(() -> m_clawElevator.isAtL2CoralScoringPos()),
            new MoveArm(m_clawArm, m_clawElevator, m_lifter, Constants.ClawArmConstants.L2_CORAL_SCORING_POS),
            new WaitUntilCommand(() -> m_clawArm.isAtL2CoralScoringPos())
        );
    }

}
