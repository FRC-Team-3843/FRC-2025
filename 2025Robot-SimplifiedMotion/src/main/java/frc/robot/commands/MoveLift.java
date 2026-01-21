package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class MoveLift extends Command {

    private final ClawArm m_clawArm;
    private final ClawElevator m_clawElevator;
    private final Lifter m_lifter;
    private final double position;
    private final double positionVariation = 4.0;

    public MoveLift(ClawArm m_clawArm, ClawElevator m_clawElevator, Lifter m_lifter, double position) {
        this.m_clawArm = m_clawArm;
        this.m_clawElevator = m_clawElevator;
        this.m_lifter = m_lifter;
        this.position = position;
        

        // Add subsystem requirements
        addRequirements(m_clawArm, m_clawElevator, m_lifter);
    }

    @Override
    public void initialize() {
        // Initialization logic if needed (empty in this case)
    }

    @Override
    public void execute() {
        // Get the current positions of the claw arm and lifter
        double clawArmCurrentPosition = m_clawArm.getPosition();
        double liftCurrentPosition = m_lifter.getPosition();


        // Check for the arm and lifter positions and set the command group accordingly
        if(position >=  Constants.LifterConstants.CLEARANCE_POS){
            if(m_lifter.isClear() || m_clawArm.isClear() || m_clawArm.isAtStowedPos()){
                Commands.runOnce(() -> m_lifter.setPos(position));
            }
            else if((clawArmCurrentPosition + 2) > Constants.ClawArmConstants.ALGAE_TRANSFER_POS){
                Commands.runOnce(() -> m_clawArm.moveClear());
            }
            else{
                Commands.runOnce(() -> m_lifter.setPos(position));
            }

        }
        else{
            if(m_clawArm.isClear() || m_clawArm.isAtStowedPos()){
                Commands.runOnce(() -> m_lifter.setPos(position));
            }
            else if((clawArmCurrentPosition + 2) > Constants.ClawArmConstants.ALGAE_TRANSFER_POS){
                Commands.runOnce(() -> m_clawArm.moveClear());
            }
            else{
                Commands.runOnce(() ->  m_clawArm.moveStowedPos());
            }
        }

    }

    @Override
    public boolean isFinished() {
        // Check if the command is finished
        return m_lifter.isAtPosition(position);
    }


}
