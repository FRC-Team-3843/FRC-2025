package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class CoralLifterOuttakeCommand extends SequentialCommandGroup {
public Lifter lifter;
public LifterIntake lifterIntake;
public double speed;

public CoralLifterOuttakeCommand(Lifter m_lifter, LifterIntake m_lifterIntake, double speed) {
   
    addRequirements(m_lifter);
    addRequirements(m_lifterIntake);
    addCommands(
        Commands.runOnce(() -> m_lifter.moveCoralScorePos()),
        new WaitUntilCommand(() -> m_lifter.isAtCoralScorePos()),
        Commands.runOnce(() -> m_lifterIntake.outtake(speed))
    );
}

}