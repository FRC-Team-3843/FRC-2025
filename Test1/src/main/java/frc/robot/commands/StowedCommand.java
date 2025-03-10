package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawArm;
import frc.robot.subsystems.ClawElevator;
import frc.robot.subsystems.ClawIntake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.LifterIntake;

public class StowedCommand extends Command{

    public StowedCommand (ClawIntake m_clawIntake, ClawArm m_ClawArm, ClawElevator m_clawElevator, Lifter m_lifter, LifterIntake m_lifterIntake) {
        m_lifterIntake.stop();
        m_clawIntake.stop();
        m_lifter.moveStowedPos();
    }

}
