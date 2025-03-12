package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class MoveLift extends SequentialCommandGroup{
    
    public MoveLift(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, Double position){
        
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_lifter);

        Double clawArmCurrentPosition = m_clawArm.getPosition();
        //Double clawElevatorCurrentPosition = m_clawElevator.getPosition();
        Double liftCurrentPosition = m_lifter.getPosition();

        if(clawArmCurrentPosition > Constants.ClawArmConstants.CLEARANCE_POS || (liftCurrentPosition > Constants.LifterConstants.CLEARANCE_POS && position > Constants.LifterConstants.CLEARANCE_POS) || m_clawArm.isAtStowedPos()){
            addCommands(
                Commands.runOnce(() -> m_lifter.setPos(position))
            );
        }
        else if (clawArmCurrentPosition > (Constants.ClawArmConstants.ALGAE_TRANSFER_POS + 2)){
            addCommands(
                Commands.runOnce(() -> m_clawArm.moveClear()),
                new WaitUntilCommand(() -> m_lifter.isClear()),
                Commands.runOnce(() -> m_lifter.setPos(position))
            );
        }
        else{
            addCommands(
                Commands.runOnce(() -> m_clawArm.moveStowedPos()),
                new WaitUntilCommand(() -> m_clawArm.isAtStowedPos()),
                Commands.runOnce(() -> m_lifter.setPos(position))
            );
        }
    }
    
}
