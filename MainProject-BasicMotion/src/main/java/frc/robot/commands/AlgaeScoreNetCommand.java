package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class AlgaeScoreNetCommand extends SequentialCommandGroup{

    public AlgaeScoreNetCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake){
        
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            new MotionManager(m_clawArm, Constants.ClawArmConstants.ALGAE_SCORE_POS, m_clawElevator, Constants.ClawArmConstants.ALGAE_SCORE_POS, m_lifter),
            new WaitUntilCommand(() -> m_clawArm.isAtAlgaeScorePos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtAlgaeScorePos())
        );
    }


}
