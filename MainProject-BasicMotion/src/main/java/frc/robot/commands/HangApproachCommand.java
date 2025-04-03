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
        System.out.println("Hang Approach Command Running...");
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);

        //System.out.println("Moving to Hang Approach");

        addCommands(
            Commands.runOnce(() -> m_clawElevator.moveClimbingApproachPos()),
            Commands.runOnce(() -> m_clawArm.moveClimbingApproachPos()),
            Commands.runOnce(() -> m_lifter.moveClimbingApproachPos())
        );
        System.out.println("Hang Approach Command Complete!");
    }

}



