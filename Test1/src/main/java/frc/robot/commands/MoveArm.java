package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
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

        DriverStation.reportError("Staring Arm Movement", false);

        if((liftCurrentPosition > Constants.LifterConstants.CLEARANCE_POS) || (clawArmCurrentPosition > Constants.ClawArmConstants.CLEARANCE_POS && position > Constants.ClawArmConstants.CLEARANCE_POS)){
            DriverStation.reportError("Moving Arm - Free Motion", false);
            addCommands(
                Commands.runOnce(() -> m_clawArm.setPos(position))
            );
        }
        else if(clawArmCurrentPosition < (Constants.ClawArmConstants.ALGAE_TRANSFER_POS + 2)){
            DriverStation.reportError("Arm Low", false);
            if(position <= (Constants.ClawArmConstants.ALGAE_TRANSFER_POS)){
                DriverStation.reportError("Position Low", false);
                if(liftCurrentPosition > (Constants.LifterConstants.ALGAE_INTAKE_POS - 2)){
                    DriverStation.reportError("Free Arm Movement", false);
                    addCommands(
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
                if(position == Constants.ClawArmConstants.STOWED_POS){
                    DriverStation.reportError("Moving Arm to Stowed Position", false);
                    addCommands(
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
                else{
                    DriverStation.reportError("Lifter too close, Moving out, Then Low movement", false);
                    addCommands(
                        Commands.runOnce(() -> m_lifter.moveAlgaeIntakePos()),
                        new WaitUntilCommand(() -> m_lifter.isAtAlgaeIntakePos()),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
            }
            else{
                DriverStation.reportError("Position High", false);
                if(position >= Constants.ClawArmConstants.CLEARANCE_POS){
                    DriverStation.reportError("Moving Lifter then going high", false);
                    addCommands(
                        Commands.runOnce(() -> m_lifter.moveClear()),
                        new WaitUntilCommand(() -> m_lifter.isClear()),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }else{
                    DriverStation.reportError("Moving Lifter out, then moving clear, then stowing lifter, then moving", false);
                    addCommands(
                        Commands.runOnce(() -> m_lifter.moveClear()),
                        new WaitUntilCommand(() -> m_lifter.isClear()),
                        Commands.runOnce(() -> m_clawArm.moveClear()),
                        new WaitUntilCommand(() -> m_clawArm.isClear()),
                        Commands.runOnce(() -> m_lifter.moveStowedPos()),
                        new WaitUntilCommand(() -> m_lifter.isAtStowedPos()),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }

            }
        }
        else{
            DriverStation.reportError("Stowing lifter, then moving", false);
            addCommands(
                Commands.runOnce(() -> m_clawArm.moveClear()),
                new WaitUntilCommand(() -> m_clawArm.isClear()),
                Commands.runOnce(() -> m_lifter.moveStowedPos()),
                new WaitUntilCommand(() -> m_lifter.isAtStowedPos()),
                Commands.runOnce(() -> m_clawArm.setPos(position))
            );
        }
    }



}
