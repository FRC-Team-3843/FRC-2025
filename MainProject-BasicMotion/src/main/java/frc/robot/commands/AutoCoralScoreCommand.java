package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class AutoCoralScoreCommand extends SequentialCommandGroup {

    public AutoCoralScoreCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        //System.out.println("Coral Lifter Outtake");

        addCommands(
            
           // new MoveLift(m_clawArm, m_clawElevator, m_lifter, Constants.LifterConstants.CORAL_SCORE_POS),
           Commands.runOnce(() -> m_lifter.moveCoralScorePos()),
            new WaitUntilCommand(() -> m_lifter.isAtCoralScorePos()),
            Commands.run(() -> m_lifterIntake.outtake(Constants.LifterIntakeConstants.CORAL_OUTTAKE_SPEED)),
            new WaitCommand(3),
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_lifter.moveStowedPos())
        );
    }

}