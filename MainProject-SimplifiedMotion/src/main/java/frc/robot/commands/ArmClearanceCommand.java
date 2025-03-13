package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class ArmClearanceCommand extends SequentialCommandGroup{


        public ArmClearanceCommand(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter) {
        addRequirements(m_lifter);
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);

        System.out.println("Moving Arm to Clearance");
        
        addCommands(
            new MoveArm(m_clawArm, m_clawElevator, m_lifter, Constants.ClawArmConstants.CLEARANCE_POS),
            new WaitUntilCommand(() -> m_clawArm.isClear())
        );
        
    }

}

