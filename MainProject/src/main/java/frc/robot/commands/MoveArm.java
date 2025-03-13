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

        SequentialCommandGroup commandGroup = new SequentialCommandGroup();

        if ((liftCurrentPosition > Constants.LifterConstants.CLEARANCE_POS) ||
            (clawArmCurrentPosition > Constants.ClawArmConstants.CLEARANCE_POS && position > Constants.ClawArmConstants.CLEARANCE_POS)) {
            // Free motion
            commandGroup.addCommands(
                new WarningLogCommand("Moving Arm - Free Motion"),
                Commands.runOnce(() -> m_clawArm.setPos(position))
            );
        }
        else if (clawArmCurrentPosition < (Constants.ClawArmConstants.ALGAE_TRANSFER_POS + positionVariation)) {
            // Low position handling
            if (position <= (Constants.ClawArmConstants.ALGAE_TRANSFER_POS)) {
                if (liftCurrentPosition > (Constants.LifterConstants.ALGAE_INTAKE_POS - positionVariation)) {
                    commandGroup.addCommands(
                        new WarningLogCommand("Moving Arm - Low - Free Movement"),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
                else if (position == Constants.ClawArmConstants.STOWED_POS) {
                    commandGroup.addCommands(
                        new WarningLogCommand("Moving Arm - Low - Stowed Position"),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
                else {
                    commandGroup.addCommands(
                        new WarningLogCommand("Moving Arm - Low - Lifter too close, moving out"),
                        Commands.runOnce(() -> m_lifter.moveAlgaeIntakePos()),
                        new WaitUntilCommand(() -> m_lifter.isAtAlgaeIntakePos()),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
            }
            else {
                // Higher position handling
                if (position >= Constants.ClawArmConstants.CLEARANCE_POS) {
                    commandGroup.addCommands(
                        new WarningLogCommand("Moving Arm - High - Lifter too close moving out"),
                        new WarningLogCommand(String.valueOf(liftCurrentPosition)),
                        Commands.runOnce(() -> m_lifter.moveClear()),
                        new WaitUntilCommand(() -> m_lifter.isClear()),
                        Commands.runOnce(() -> m_clawArm.setPos(position))
                    );
                }
                else {
                    commandGroup.addCommands(
                        new WarningLogCommand("Moving Arm - Med - Lifter too close moving out"),
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
        else {
            // Default (Med) handling
            commandGroup.addCommands(
                new WarningLogCommand("Moving Arm - Med - Lifter too close moving in"),
                Commands.runOnce(() -> m_clawArm.moveClear()),
                new WaitUntilCommand(() -> m_clawArm.isClear()),
                Commands.runOnce(() -> m_lifter.moveStowedPos()),
                new WaitUntilCommand(() -> m_lifter.isAtStowedPos()),
                Commands.runOnce(() -> m_clawArm.setPos(position))
            );
        }

        // Schedule the command group during execute
        commandGroup.schedule();
    }

}
