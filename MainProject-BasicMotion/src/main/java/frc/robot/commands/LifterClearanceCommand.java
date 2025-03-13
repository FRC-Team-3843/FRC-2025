package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class LifterClearanceCommand extends SequentialCommandGroup{

        public LifterClearanceCommand(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter) {
        addRequirements(m_lifter);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        
        //System.out.println("Moving Lifter to Clearance");

        addCommands(
            new MoveLift(m_clawArm, m_clawElevator, m_lifter, Constants.LifterConstants.CLEARANCE_POS),
            new WaitUntilCommand(() -> m_lifter.isClear())
        );
        
    }

}
