package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;


public class HangCommand extends SequentialCommandGroup{
    
    public HangCommand(LifterIntake m_lifterIntake, ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, ClawIntake m_clawIntake) {
        
        addRequirements(m_lifter, m_lifterIntake, m_clawArm, m_clawElevator, m_clawIntake);
        
        addCommands(
            Commands.runOnce(() -> m_lifterIntake.stop()),
            Commands.runOnce(() -> m_clawIntake.stop()),
            Commands.runOnce(() -> m_lifter.moveHangPos()),
            new WaitUntilCommand(() -> m_lifter.isAtHangPos()),
            Commands.runOnce(() -> m_lifter.setBreak()),
            new WaitCommand(1),
            Commands.runOnce(() -> m_lifter.stopMotor())
        );
        
    }
}
