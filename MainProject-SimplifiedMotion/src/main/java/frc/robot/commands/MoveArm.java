package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.Lifter;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ClawElevator;


public class MoveArm extends Command {

    private final ClawArm m_clawArm;
    private final ClawElevator m_clawElevator;
    private final Lifter m_lifter;
    private final double position;
    private final double positionVariation = 4.0;

    public MoveArm(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, double position) {
        this.m_clawArm = m_clawArm;
        this.m_clawElevator = m_clawElevator;
        this.m_lifter = m_lifter;
        this.position = position;

        addRequirements(m_clawArm, m_clawElevator, m_lifter);
    }

    @Override
    public void initialize() {
        // Initialize any variables or states if needed (this method can be empty if not required)
    }

    @Override
    public void execute() {
        // Get the current positions of the arm and lifter during the execution
        double clawArmCurrentPosition = m_clawArm.getPosition();
        double liftCurrentPosition = m_lifter.getPosition();

        if(position >= Constants.ClawArmConstants.CORAL_HUMAN_POS){            
            if (m_lifter.isClear()){
                new WarningLogCommand("Moving Arm - High - Lift Clear");
                Commands.runOnce(() -> m_clawArm.setPos(position));
            }
            else if((liftCurrentPosition - 8) < (Constants.LifterConstants.ALGAE_INTAKE_POS)){
                new WarningLogCommand("Moving Arm - High - Lifter too close, moving out");
                Commands.runOnce(() -> m_lifter.moveClear());
            }
            else{
                new WarningLogCommand("Moving Arm - High - Arm Clear");
                Commands.runOnce(() -> m_clawArm.setPos(position));
            }
        }
        else if(position > Constants.ClawArmConstants.ALGAE_TRANSFER_POS && position < Constants.ClawArmConstants.CORAL_HUMAN_POS){
            if((clawArmCurrentPosition - 2) < Constants.ClawArmConstants.ALGAE_TRANSFER_POS){   
                if(!m_lifter.isClear()){
                    new WarningLogCommand("Moving Arm - Med - Lift too close, moving out");
                    Commands.runOnce(() -> m_lifter.moveClear());
                }
                else {
                    new WarningLogCommand("Moving Arm - Med - Arm in, moving clear for lifter");
                    Commands.runOnce(() -> m_clawArm.moveClear());
                }

            }
            else if(!m_lifter.isAtStowedPos()){
                if(!m_clawArm.isClear()){
                    new WarningLogCommand("Moving Arm - Med - Arm not clear, moving out");
                    Commands.runOnce(() -> m_clawArm.moveClear());
                }
                else{
                    new WarningLogCommand("Moving Arm - Med - Lifter out, moving in");
                    Commands.runOnce(() -> m_lifter.moveStowedPos());
                }
            }
            else{
                new WarningLogCommand("Moving Arm - Med - Arm Clear - Lift stowed");
                Commands.runOnce(() -> m_clawArm.setPos(position));
            }
        }    
        else {
            // Low position handling
            
            if(m_lifter.isClear()){
                new WarningLogCommand("Moving Arm - Low - lift clear");
                Commands.runOnce(() -> m_clawArm.setPos(position));
            }
            else if((clawArmCurrentPosition + 2) > (Constants.ClawArmConstants.ALGAE_TRANSFER_POS)){
                if(m_clawArm.isClear()){
                    new WarningLogCommand("Moving Arm - Low - lift in, moving out");
                    Commands.runOnce(() -> m_lifter.moveClear());
                }else{
                    new WarningLogCommand("Moving Arm - Low - lift in, arm too low, moving out");
                    Commands.runOnce(() -> m_clawArm.moveClear());
                }
            }
            else{
                if(position < Constants.ClawArmConstants.ALGAE_TRANSFER_POS){
                    new WarningLogCommand("Moving Arm - Low - Arm in - lift clear");
                    Commands.runOnce(() -> m_clawArm.setPos(position));
                }
                else if((liftCurrentPosition - 8) < Constants.LifterConstants.ALGAE_INTAKE_POS){
                    new WarningLogCommand("Moving Arm - Low - Arm in, lift too close, moving out");
                    Commands.runOnce(() -> m_lifter.moveAlgaeIntakePos());
                }
                else{
                    new WarningLogCommand("Moving Arm - Low - Out - lift clear");
                    Commands.runOnce(() -> m_clawArm.setPos(position));
                }
            }
        }

    }

    @Override
    public boolean isFinished() {
        if((position > Constants.ClawArmConstants.ALGAE_TRANSFER_POS && position < Constants.ClawArmConstants.CORAL_HUMAN_POS) && m_clawArm.isAtPosition(position))
            return true;
        else if(m_lifter.isAtStowedPos() && m_clawArm.isAtPosition(position))
            return true;
        return true;
    }


}
