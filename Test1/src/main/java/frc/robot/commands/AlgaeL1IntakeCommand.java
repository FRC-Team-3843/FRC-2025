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

public class AlgaeL1IntakeCommand extends SequentialCommandGroup {

    public AlgaeL1IntakeCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake){
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_clawIntake);

        addCommands(
            new MotionManager(m_clawArm, Constants.ClawArmConstants.L1_ALGAE_INTAKE_POS, m_clawElevator, Constants.ClawElevatorConstants.L1_ALGAE_INTAKE_POS, m_lifter),
            new WaitUntilCommand(() -> m_clawArm.isAtL1AlgaeIntakePos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtL1AlgaeIntakePos()),
            Commands.runOnce(() -> m_clawIntake.intake(Constants.ClawIntakeConstants.ALAGE_INTAKE_SPEED))
        );
    }

}

