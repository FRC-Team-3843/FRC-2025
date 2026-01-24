# Working Notes - FRC-2025

## Robot Dimensions
- Pod Length: 25.25 in (0.6413 m)
- Pod Width: 19.25 in (0.4889 m)

## Encoder Positions (Old Bot Reference)
- Back Left: 169.37
- Back Right: 89.47
- Front Left: 331.215
- Front Right: 222.845

## Target Positions
- 61.5 Target Position = 1 rotation
- 3.5 rotations = [calculated value]

## IMPORTANT REMINDERS

### TODOs
- [ ] Tune the bot
- [ ] Fix the bumpiness with the swerve drive
- [ ] Resolve error code (see below)

## Error Log / Troubleshooting

### Error - IllegalStateException
**Time:** 5:34:42.857 PM
**Symptom:** Robot program quit unexpectedly with IllegalStateException
**Stack Trace:**
```
java.lang.IllegalStateException: End size 0 is less than fixed size 1
at java.util.stream.Nodes$FixedNodeBuilder.end(Unknown Source)
at edu.wpi.first.wpilibj.Alert$SendableAlerts.getStrings(Alert.java:202)
at edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl.update
```
**Root Cause:** SmartDashboard/Alert system attempting to send empty array when fixed size expected
**Fix:** [Pending investigation]

## PathPlanner Configuration

**HARRISON - PLEASE FILL OUT:**
- Robot Mass (kg): [TBD]
- Robot MOI (kg*m^2): [TBD]
- Bumper Width (m): [TBD]
- Bumper Length (m): [TBD]
- Bumper Offset X (m): [TBD]
- Bumper Offset Y (m): [TBD]
- Wheel Radius (m): [TBD]
- Drive Gearing: [TBD]
- True Max Drive Speed (m/s): [TBD]
- Wheel COF: [TBD]
- Drive Motor: Neo
- Drive Current Limit (A): [TBD]

### Module Positions
- Front Left X (m): [TBD]
- Front Left Y (m): [TBD]
- Front Right X (m): [TBD]
- Front Right Y (m): [TBD]
- Back Left X (m): [TBD]
- Back Left Y (m): [TBD]
- Back Right X (m): [TBD]
- Back Right Y (m): [TBD]

## Command Ideas (16 Buttons Total)

| Button | Command | Description |
|--------|---------|-------------|
| A | CoralLifterIntakeCommand | Intake coral with lifter |
| B | CoralLifterScoreCommand | Score coral with lifter |
| X | AlgaeGroundIntakeCommand | Ground intake algae |
| Y | AlgaeL1IntakeCommand | L1 algae intake |
| Left Bumper + B | CoralClawL1ScoreCommand | L1 coral scoring |
| Left Bumper + X | CoralClawL2ScoreCommand | L2 coral scoring |
| Left Bumper + A | AlgaeL2IntakeCommand | L2 algae intake |
| Left Bumper + Y | AlgaeScoreNetCommand | Score algae in net |
| Right Bumper + X | CoralClawIntakeCommand | Intake coral with claw |
| Right Bumper + Y | StowedCommand | Stow all mechanisms |
| Back | HangCommand | Execute hang sequence |
| [TBD] | ApproachHangCommand | Approach hang position |
| [TBD] | AlgaeScoreProcessorCommand | Score algae in processor |
| [TBD] | DefenseCommand | Defense mode |

## Auto Ideas
1. ScoreCoralTrough
2. StraightLeave
3. AlgaeGrab
4. ScoreCoralTrough (repeat)

## Lessons Learned
- Swerve tuning requires patience and systematic approach
- PathPlanner configuration is critical for auto success
- Multi-mechanism coordination needs careful sequencing
- Error logging helps track intermittent issues
