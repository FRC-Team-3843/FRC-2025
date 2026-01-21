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
        Double clawArmCurrentPosition = m_clawArm.getPosition();
        Double liftCurrentPosition = m_lifter.getPosition();

        // Command group variable
        var commandGroup = new SequentialCommandGroup();

        // Check for the arm and lifter positions and set the command group accordingly
        if (clawArmCurrentPosition > Constants.ClawArmConstants.CLEARANCE_POS ||
            (liftCurrentPosition > Constants.LifterConstants.CLEARANCE_POS && position > Constants.LifterConstants.CLEARANCE_POS) ||
            m_clawArm.isAtStowedPos()) {
            
            // If the claw arm is at a safe position, move the lifter
            commandGroup.addCommands(
                Commands.runOnce(() -> m_lifter.setPos(position))
            );
        }
        else if (clawArmCurrentPosition > (Constants.ClawArmConstants.ALGAE_TRANSFER_POS + positionVariation)) {
            
            // If the claw arm is high enough, move it out of the way
            commandGroup.addCommands(
                Commands.runOnce(() -> m_clawArm.moveClear()), // Move arm to clear position
                new WaitUntilCommand(() -> m_lifter.isClear()), // Wait for lifter to clear
                Commands.runOnce(() -> m_lifter.setPos(position)) // Move lifter to target position
            );
        }
        else {
            
            // If the claw arm is not high enough, move it to stowed position
            commandGroup.addCommands(
                Commands.runOnce(() -> m_clawArm.moveStowedPos()), // Move arm to stowed position
                new WaitUntilCommand(() -> m_clawArm.isAtStowedPos()), // Wait for the arm to be stowed
                Commands.runOnce(() -> m_lifter.setPos(position)) // Move lifter to target position
            );
        }

        // Schedule the command group
        commandGroup.schedule();
    }


}
