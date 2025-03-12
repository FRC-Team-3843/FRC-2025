package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class MoveArm extends SequentialCommandGroup{
    
    public MoveArm(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, Double position){
        
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_lifter);

        Double clawArmCurrentPosition = m_clawArm.getPosition();
        //Double clawElevatorCurrentPosition = m_clawElevator.getPosition();
        Double liftCurrentPosition = m_lifter.getPosition();

        if((liftCurrentPosition > Constants.LifterConstants.CLEARANCE_POS) || (clawArmCurrentPosition > Constants.ClawArmConstants.CLEARANCE_POS && position > Constants.ClawArmConstants.CLEARANCE_POS)){
            addCommands(
                Commands.runOnce(() -> m_clawArm.setPos(position))
            );
        }
        else if(clawArmCurrentPosition < (Constants.ClawArmConstants.ALGAE_TRANSFER_POS + 2)){
            if(position < (Constants.ClawArmConstants.ALGAE_TRANSFER_POS + 2)){
                if(liftCurrentPosition > (Constants.LifterConstants.CORAL_SCORE_POS - 2)){
                    addCommands(
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
                else{
                    addCommands(
                        Commands.runOnce(() -> m_lifter.moveCoralIntakePos()),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
            }
            else{
                addCommands(
                    Commands.runOnce(() -> m_lifter.moveClear()),
                    Commands.runOnce(() -> m_clawArm.setPos(position))
                );
            }
        }
        else{
            addCommands(
                Commands.runOnce(() -> m_clawArm.moveClear()),
                Commands.runOnce(() -> m_lifter.moveStowedPos()),
                Commands.runOnce(() -> m_clawArm.setPos(position))
            );
        }
    }



}
