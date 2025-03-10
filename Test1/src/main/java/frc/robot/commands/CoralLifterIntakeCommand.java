package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class CoralLifterIntakeCommand extends SequentialCommandGroup {

    public CoralLifterIntakeCommand(Lifter m_lifter, LifterIntake m_lifterIntake, double speed) {
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addCommands(
            Commands.runOnce(() -> m_lifter.moveCoralIntakePos()),
            new WaitUntilCommand(() -> m_lifter.isAtCoralIntakePos()),
            Commands.runOnce(() -> m_lifterIntake.intake(speed))
        );
    }

}
