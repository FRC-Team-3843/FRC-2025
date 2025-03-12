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

public class HangApproachCommand extends SequentialCommandGroup{
    public HangApproachCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        addRequirements(m_lifter);
        addRequirements(m_lifterIntake);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_clawIntake);

        System.out.println("Moving to Hange Approach");

        addCommands(
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_clawIntake.stop()),
            new MoveLift(m_clawArm, m_clawElevator, m_lifter, Constants.LifterConstants.CLIMBING_APPROACH_POS),
            new MoveElevator(m_clawArm, m_clawElevator, m_lifter, Constants.ClawElevatorConstants.CLIMBING_APPROACH_POS),
            new MoveArm(m_clawArm, m_clawElevator, m_lifter, Constants.ClawArmConstants.CLIMBING_APPROACH_POS),
            new WaitUntilCommand(() -> m_clawArm.isAtClimbingApproachPos()),
            new WaitUntilCommand(() -> m_clawElevator.isAtClimbingApproachPos()),
            new WaitUntilCommand(() -> m_lifter.isAtClimbingApproachPos())
        );
        
    }

}



