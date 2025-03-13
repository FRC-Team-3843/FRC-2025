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

public class AlgaeL2IntakeCommand extends SequentialCommandGroup {

    public AlgaeL2IntakeCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake){
        
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_clawIntake.stop()),
            new MoveElevator(m_clawArm, m_clawElevator, m_lifter, Constants.ClawElevatorConstants.L2_ALGAE_INTAKE_POS),
            new WaitUntilCommand(() -> m_clawElevator.isAtL2AlgaeIntakePos()),
            new MoveArm(m_clawArm, m_clawElevator, m_lifter, Constants.ClawArmConstants.L2_ALGAE_INTAKE_POS),
            new WaitUntilCommand(() -> m_clawArm.isAtL2AlgaeIntakePos()),
            Commands.runOnce(() -> m_clawIntake.intake(Constants.ClawIntakeConstants.ALAGE_INTAKE_SPEED))
        );
    }


}
