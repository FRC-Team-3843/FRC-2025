# FRC-2025 - Reefscape

FRC Team 3843 - 2025 Season

## Overview
Team 3843's robot for the 2025 Reefscape game. Features a swerve drive system powered by YAGSL (Yet Another Generic Swerve Library) with advanced autonomous capabilities via PathPlanner. The robot includes a complex superstructure with multiple mechanisms for manipulating both Algae and Coral game pieces.

## Hardware

### Drivetrain
- **Type:** Swerve Drive (4-wheel independent steering)
- **Library:** YAGSL (Yet Another Generic Swerve Library)
- **Pod Dimensions:** 25.25" length x 19.25" width
- **Control:** Field-centric with gyro-assisted navigation

### Mechanisms
- **Lifter:** Dual-motor vertical lift system (SparkMax motors)
- **Claw Intake:** Game piece manipulation (TalonFX)
- **Claw Arm:** Multi-position articulated arm (position-controlled)
- **Claw Elevator:** Extension mechanism for reach
- **Lifter Intake:** Secondary intake system (TalonFX)

## Software Stack
- WPILib 2025
- YAGSL (Swerve Drive Library)
- PathPlanner (Autonomous)
- REVLib (SparkMax/NEO)
- Phoenix6 (TalonFX)
- PhotonVision (Vision processing)

## CAN ID Assignments

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

## Subsystems
- **Swerve Drive:** YAGSL-powered swerve with odometry and vision fusion
- **Lifter:** Dual-motor lift with MAXMotion position control
- **Claw Intake:** Game piece intake/outtake for Algae and Coral
- **Claw Arm:** Multi-position arm for scoring and intake
- **Claw Elevator:** Extension mechanism for reach
- **Lifter Intake:** Secondary intake system

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

## Building and Deploying
```bash
cd 2025Robot
./gradlew build
./gradlew deploy
```

## Autonomous
PathPlanner-based autonomous with pre-planned trajectories and on-the-fly pathfinding. Requires proper configuration of:
- Robot mass and MOI
- Bumper dimensions
- Wheel radius and gearing
- Drive motor current limits
- Module positions

## Competition Season
The 2025 Reefscape game requires manipulation of two game piece types (Algae and Coral) with scoring at multiple levels. This robot was designed for versatile multi-level scoring with fast cycling and reliable autonomous routines.
