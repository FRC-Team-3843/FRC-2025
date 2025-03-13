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

public class AlgaeGroundIntakeCommand extends SequentialCommandGroup {

    public AlgaeGroundIntakeCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            
            new WaitUntilCommand(() -> m_clawArm.isAtAlgaeTransferPos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtAlgaeTransferPos()),
            new WaitUntilCommand(() -> m_lifter.isAtAlgaeIntakePos()),
            Commands.runOnce(() -> m_lifterIntake.intake(Constants.LifterIntakeConstants.ALAGE_INTAKE_SPEED)),
            Commands.runOnce(() -> m_clawIntake.intake(Constants.ClawIntakeConstants.ALAGE_INTAKE_SPEED))
        );

    }

}
