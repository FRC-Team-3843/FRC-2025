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

public class CoralLifterIntakeCommand extends SequentialCommandGroup {

    public CoralLifterIntakeCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_clawIntake);

        System.out.println("Coral LifterIntake");

        addCommands(
            new MotionManager(m_clawArm, m_clawElevator, m_lifter, Constants.LifterConstants.CORAL_INTAKE_POS),
            new WaitUntilCommand(() -> m_lifter.isAtCoralIntakePos()),
            Commands.runOnce(() -> m_lifterIntake.intake(Constants.LifterIntakeConstants.CORAL_INTAKE_SPEED))
        );
    }

}
