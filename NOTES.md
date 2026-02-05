# Working Notes - FRC-2025

> **Documentation Guide:**
> - **This file (NOTES):** Setup, tuning, troubleshooting, TODOs
> - **README.md:** Project overview and quick start
> - **STANDARDS.md:** Coding standards (references FRC-2026\STANDARDS.md)

## CAN Bus Assignments

### Drive System
| Device | CAN ID | Description |
|--------|--------|-------------|
| Swerve Modules | 1-12 | Drive motors, steer motors, CANCoders |

### Mechanisms
| Device | CAN ID | Description |
|--------|--------|-------------|
| Right Lifter Motor | 31 | Lifter right side |
| Left Lifter Motor | 32 | Lifter left side |
| Claw Intake Motor | 33 | Intake/outtake control |
| Claw Arm Motor | 34 | Arm positioning |
| Lifter Intake Motor | 35 | Secondary intake |
| Claw Elevator Motor | 50 | Vertical extension |

## Key Positions

### Lifter Positions (encoder rotations)
- Stowed: 10
- Hang: 10
- Coral Score: 101
- Clearance: 130
- Algae Intake: 130
- Algae Score: 128
- Coral Intake: 193
- Climbing Approach: 198

### Claw Arm Positions (rotations)
- Stowed: 0.77
- L1 Coral Scoring: 23.26
- L2 Coral Scoring: 44
- L1 Algae Intake: 26.55
- L2 Algae Intake: 38
- Algae Score: 53

### Claw Elevator Positions (rotations)
- Stowed: -0.75
- L2 Coral Scoring: -28
- L2 Algae Intake: -28
- Algae Score: -28
- Top: -46

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
