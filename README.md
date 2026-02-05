# FRC-2025 - Reefscape

FRC Team 3843 - 2025 Season

> **Documentation Guide:**
> - **This file (README):** Project overview and quick start
> - **NOTES.md:** Setup procedures, tuning values, troubleshooting
> - **STANDARDS.md:** Coding standards and architecture rules (references FRC-2026)

## Competition Season
The 2025 Reefscape game requires manipulation of two game piece types (Algae and Coral) with scoring at multiple levels. This robot was designed for versatile multi-level scoring with fast cycling and reliable autonomous routines.

## Overview
Team 3843's robot for the 2025 Reefscape game. Features a swerve drive system powered by YAGSL (Yet Another Generic Swerve Library) with advanced autonomous capabilities via PathPlanner. The robot includes a complex superstructure with multiple mechanisms for manipulating both Algae and Coral game pieces.

## Hardware

### Drivetrain
- **Type:** Swerve Drive (4-wheel independent steering)
- **Library:** YAGSL (Yet Another Generic Swerve Library)
- **Pod Dimensions:** 25.25" length x 19.25" width
- **Control:** Field-centric with gyro-assisted navigation

### Mechanisms
- **Lifter:** Multi-purpose dual-motor lift system with three functions:
  - Algae intake (works with claw arm, elevator, and claw intake to pull ball into claw for barge scoring)
  - Coral intake and scoring into bottom tray of reef
  - Climbing by hooking onto cage and lifting robot
  - Can also hold algae for low goal scoring
  - Includes servo-driven parking brakes to prevent backdriving when powered off
- **Lifter Intake:** Roller mechanism on lifter for pulling in algae and coral.
- **Claw Intake:** Primary algae intake system
  - Forward: pulls algae from lifter or directly off reef
  - Reverse: pushes algae out to score in barge
  - Can pass ball back to lifter for low goal scoring
- **Claw Arm:** Multi-position articulated arm for positioning mechanisms at different scoring/intake heights
- **Claw Elevator:** Vertical extension mechanism providing additional height needed for barge scoring

## Software Stack
- WPILib 2025
- YAGSL (Swerve Drive Library)
- PathPlanner (Autonomous)
- REVLib (SparkMax/NEO)
- Phoenix6 (TalonFX)
- PhotonVision (Vision processing)

*See NOTES.md for CAN bus assignments and mechanism positions*

## Building and Deploying
```bash
cd 2025Robot
./gradlew build
./gradlew deploy
```

## Autonomous
PathPlanner-based autonomous routines:
- **ScoreCoralTrough:** Score pre-loaded coral in trough and exit starting zone
- **StraightLeave:** Simple mobility auto - drive straight out of starting zone
- **AlgaeGrab:** Acquire algae game piece from field during auto

*See NOTES.md for PathPlanner configuration and tuning details*


