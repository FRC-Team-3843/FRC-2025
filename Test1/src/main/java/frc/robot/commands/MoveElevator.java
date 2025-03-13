package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class MoveElevator extends SequentialCommandGroup{

    public MoveElevator(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, double position){
        
        addRequirements(m_clawArm, m_clawElevator, m_lifter);
        
        //Double clawArmCurrentPosition = m_clawArm.getPosition();
        Double clawElevatorCurrentPosition = m_clawElevator.getPosition();
        //Double liftCurrentPosition = m_lifter.getPosition();

        if((clawElevatorCurrentPosition < 18 && position > 18) || (clawElevatorCurrentPosition > 18 && position < 18)){
            if(!m_lifter.isClear()){
                addCommands(
                    new MoveLift(m_clawArm, m_clawElevator, m_lifter, Constants.LifterConstants.CLEARANCE_POS),
                    Commands.runOnce(() -> m_clawElevator.setPos(position))
                );
            }
            else{
                addCommands(
                    Commands.runOnce(() -> m_clawElevator.setPos(position))
                );
            }
        }
        else{
            addCommands(
                Commands.runOnce(() -> m_clawElevator.setPos(position))
            );
        }
    }
    

}
