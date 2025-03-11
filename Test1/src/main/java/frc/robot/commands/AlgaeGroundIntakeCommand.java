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
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_clawIntake);

        addCommands(
            new MotionManager(m_clawArm, Constants.ClawArmConstants.ALGAE_TRANSFER_POS, m_clawElevator, Constants.ClawElevatorConstants.ALGAE_TRANSFER_POS, m_lifter, Constants.LifterConstants.ALGAE_INTAKE_POS),
            new WaitUntilCommand(() -> m_clawArm.isAtAlgaeTransferPos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtAlgaeTransferPos()),
            new WaitUntilCommand(() -> m_lifter.isAtAlgaeIntakePos()),
            Commands.runOnce(() -> m_lifterIntake.intake(Constants.LifterIntakeConstants.ALAGE_INTAKE_SPEED)),
            Commands.runOnce(() -> m_clawIntake.intake(Constants.ClawIntakeConstants.ALAGE_INTAKE_SPEED))
        );

    }

}
