package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.Lifter;

public class MotionManager extends SequentialCommandGroup {

    MotionManager(ClawArm m_clawArm, double clawArmSetPosition, ClawElevator m_clawElevator, double clawElevatorSetPosition, Lifter m_lifter, double liftSetPosition) {
        addRequirements(m_clawArm);
        addRequirements(m_clawElevator);
        addRequirements(m_lifter);

        double clawArmCurrentPosition = m_clawArm.getPosition();
        double clawElevatorCurrentPosition = m_clawElevator.getPosition();
        double liftCurrentPosition = m_lifter.getPosition();

        if(clawArmCurrentPosition > Constants.ClawArmConstants.MIN_ARM_POS && clawArmSetPosition > Constants.ClawArmConstants.MIN_ARM_POS) {        
            addCommands(
                Commands.runOnce(() -> m_clawArm.setPos(clawArmSetPosition)),
                Commands.runOnce(() -> m_clawElevator.setPos(clawElevatorSetPosition)),
                Commands.runOnce(() -> m_lifter.setPos(liftSetPosition))
            );
        }

    }

}
