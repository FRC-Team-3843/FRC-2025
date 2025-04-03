package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class AlgaeL2IntakeCommand extends SequentialCommandGroup {

    public AlgaeL2IntakeCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake){
        System.out.println("Algae L2 Intake Command Running...");
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        addCommands(
            Commands.runOnce(() -> m_clawElevator.moveStowedPos()),
            Commands.runOnce(() -> m_clawArm.moveL2AlgaeIntakePos())
        );
        System.out.println("Algae L2 Intake Command Complete!");
    }


}
