package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class WarningLogCommand extends InstantCommand {
    public WarningLogCommand(String message) {
        super(() -> DriverStation.reportWarning(message, false));
    }
}