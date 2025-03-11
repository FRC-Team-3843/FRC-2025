package frc.robot.commands;

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
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_clawIntake);

        addCommands(
            new MotionManager(m_clawArm, Constants.ClawArmConstants.L2_CORAL_SCORING_POS, m_clawElevator, Constants.ClawElevatorConstants.L2_CORAL_SCORING_POS, m_lifter),
            new WaitUntilCommand(() -> m_clawArm.isAtL2CoralScoringPos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtL2CoralScoringPos())
        );
    }

}
