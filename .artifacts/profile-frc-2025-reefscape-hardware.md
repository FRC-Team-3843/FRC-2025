---
id: frc-2025-reefscape-hardware
title: FRC-2025 Reefscape Robot Hardware Configuration
schema_version: 2
created: 2026-06-14T12:35:00Z
updated: 2026-06-14T12:35:00Z
valid_until: null
author: claude
session: null
tags: [frc, reefscape, hardware, can-bus, motors, swerve, 2025]
aliases: [frc 2025 hardware, reefscape hardware, 2025 robot hardware, reefscape can bus, 2025 mechanisms]
status: active
supersedes: null
confidence: 55
source_basis: document
human_edited: false
sensitivity: normal
decisions: []
model: claude-sonnet-4-6
model_basis: confirmed
provenance:
  harvest: deterministic
  recall-extract: claude-sonnet-4-6
  find-missing: claude-sonnet-4-6
  precision-judge: claude-sonnet-4-6
lifecycle: active
artifact_kind: memory
memory_class: semantic
semantic_kind: entity_profile
scope: FRC-2025
---

# FRC-2025 Reefscape Robot Hardware Configuration

> The 2025 REEFSCAPE robot's specific motor controllers, CAN bus assignments, and mechanism hardware — useful as a cross-reference when comparing to FRC-2026 hardware design decisions.

## Context

Team 3843's 2025 REEFSCAPE robot uses a mixed motor controller stack: REV SparkMax (brushless, PID via RevLib 2025+ API) for the Lifter, and CTRE TalonFX (Phoenix 6) for the Claw subsystems. Swerve drivetrain runs YAGSL. Physical dimensions: 25.25" length × 19.25" width pod. Robot mass approximately 58 lbs (128 kg less bumper estimate). Source: `2025Robot/src/main/java/frc/robot/`, `Constants.java`, `NOTES.md`.

## Observations

### CAN Bus Map

- [registry] Swerve modules: CAN IDs 1–12 (drive motors, steer motors, CANCoders, 3 per module × 4 modules) — YAGSL-managed #can-bus #drivetrain
- [registry] Lifter (dual motor): CAN ID 31 (right, not inverted) + CAN ID 32 (left, inverted) — REV SparkMax brushless #can-bus #mechanisms
- [registry] ClawIntake motor: CAN ID 33 — CTRE TalonFX (Phoenix 6), Clockwise_Positive invert #can-bus #mechanisms
- [registry] ClawArm motor: CAN ID 34 — CTRE TalonFX (Phoenix 6), MotionMagic position control #can-bus #mechanisms
- [registry] LifterIntake motor: CAN ID 35 — CTRE TalonFX (Phoenix 6), Clockwise_Positive invert #can-bus #mechanisms
- [registry] ClawElevator motor: CAN ID 50 — CTRE TalonFX (Phoenix 6), position-controlled #can-bus #mechanisms

### Motor Controller Stack

- [registry] Lifter uses REV SparkMax with SparkMaxConfig (2025+ API), SmartCurrentLimit 20/30/120A; closed-loop position via SparkClosedLoopController; PID: P=0.1, I=0, D=0.01 #motors #rev
- [registry] Claw mechanisms (ClawArm, ClawIntake, ClawElevator, LifterIntake) use CTRE TalonFX (Phoenix 6); ClawArm uses MotionMagicVoltage for smooth position control #motors #ctre
- [registry] Mixed stack consequence: two vendor libraries required (REVLib + CTRE Phoenix 6); note the absence of Phoenix 5 (no CANSparkMax old API on main branch) #dependency

### Servo Hardware

- [registry] Servo PWM channel 0: `motorBreak` — parking brake on Lifter, locks mechanism when powered off to prevent backdrive #servo #lifter
- [registry] Servo PWM channel 1: `lineUp` — secondary servo on Lifter for alignment (cage hang lineup) #servo #lifter
- [lore] Servo parking brakes are the correct solution for any lift/hang mechanism that must hold position when motor power is lost; the 2025 Lifter uses PWM servos (not smart servo) for simplicity #design-pattern

### Vision

- [registry] PhotonVision with `PhotonCamera` + `PhotonPoseEstimator` (MULTI_TAG_PNP_ON_COPROCESSOR strategy); AprilTag field layout `AprilTagFields.k2025ReefScape`; simulation support via `VisionSystemSim` #vision #photonvision

### Build Config

- [registry] GradleRIO version: `2026.2.1` (updated post-season to stay current); WPILib 2025/2026 transition-state; vendordeps: `WPILibNewCommands.json` only (YAGSL + CTRE + REV pulled via vendor JSON via GradleRIO, not tracked in this list) #build

## Open Questions

- Specific YAGSL swerve module hardware type (SDS MK4? MK4i?) not confirmed from this corpus alone — compare with FRC-2026 notes; the 2026 repo resolved to SDS MK4 L1.
- CANCoder positions (encoder offsets) not present in main branch — likely in YAGSL deploy JSON which was absent from this harvest (directory may be unpopulated in main branch).

## Relations

- relates-to [[frc-2025-source-202606120638]] (this hardware lives in that repo)
- relates-to [[frc-2025-reefscape-game]] (hardware designed for this game's constraints)
- relates-to [[frc-2026]] (compare hardware choices; 2026 moved to different swerve hardware)
- relates-to [[yagsl]] (drivetrain control library, see FRC-2026 note)
- relates-to [[phoenix6-ctre]] (claw subsystem motor controllers, see FRC-2026 note)
- relates-to [[revlib-sparkmax]] (lifter motor controllers, see FRC-2026 note)
