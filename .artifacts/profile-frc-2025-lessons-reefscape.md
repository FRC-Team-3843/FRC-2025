---
id: frc-2025-lessons-reefscape
title: FRC-2025 Reefscape — durable lessons for FRC-2026
schema_version: 2
created: 2026-06-12T06:38:00Z
updated: 2026-07-04T00:22:46Z
valid_until: null
author: claude
session: phase7-onboarding-20260612
tags: [frc, robotics, swerve, controls, lessons-learned, decision, motor-api]
aliases: [frc-2025-lessons-reefscape-202606120638, frc 2025 lessons, reefscape lessons, alliance control pitfall, competition control preferences, motor conversion unit transplant]
status: active
supersedes: null
confidence: 55
source_basis: conversation
human_edited: false
sensitivity: normal
decisions: []
artifact_kind: memory
memory_class: semantic
model: claude-sonnet-5
model_basis: confirmed
scope: FRC-2025
---

# FRC-2025 Reefscape — durable lessons for FRC-2026

> Season-proven control patterns and a hard pitfall from the 2025 REEFSCAPE robot; these transcend the season and should be found by FRC-2026 sessions via `/recall frc-2025 lessons`.

## Context

FRC Team 3843's 2025 REEFSCAPE robot (`C:\GitHub\FRC-2025`, `main` branch) competed with a YAGSL swerve drive, PhotonVision, and a multi-mechanism superstructure (Lifter, ClawArm, ClawElevator, ClawIntake, LifterIntake). Season complete; repo archived. These lessons are extracted during Phase 7 ACC onboarding from the live codebase and docs; confidence is conservative (document-sourced, unproofread).

## Discussion

### Alliance-control pitfall (CONFIRMED IN CODE)

`allianceRelativeControl(true)` is present in the main `SwerveInputStream` for field-oriented drive (source: `2025Robot/src/main/java/frc/robot/RobotContainer.java` line 87). The driver's Start button is bound to `drivebase::zeroGyro` (bare zero, no alliance flip) in both the normal and test-mode paths (lines 246, 252). `SwerveSubsystem` also provides a separate `zeroGyroWithAlliance()` method that applies a 180° odometry reset for red alliance (source: `2025Robot/.../subsystems/swervedrive/SwerveSubsystem.java` lines 586–597).

**The pitfall:** if `allianceRelativeControl(true)` AND `zeroGyroWithAlliance()` are both active, the alliance flip is applied twice — once by YAGSL internally via `allianceRelativeControl`, and once by the driver's explicit reset. The result is double-compensation: a Red alliance driver's inputs are rotated 180° by YAGSL, and then the odometry is also flipped 180° by the gyro-zero call, causing wildly incorrect field orientation. The 2025 robot avoids this because it uses bare `zeroGyro()` (no alliance flip) on the reset binding — but the safe pattern is to choose one compensation path and delete the other.

### Competition control preferences (CONFIRMED IN CODE)

Active operator binding layout from `2025Robot/src/main/java/frc/robot/RobotContainer.java` lines 154–221:

**Operator controller (mechanism):**
- Face buttons (A/B/X/Y): full mechanism-sequence commands (AlgaeGroundIntake, AlgaeL1Intake, AlgaeL2Intake, AlgaeScoreNet) — one-press sequences, not individual motor commands.
- Left/Right Bumper: mechanism-swap (SwapToLifter / SwapToClaw) — bumper as mode-switch rather than expanding button count.
- POV/D-pad: discrete position presets (up=clearance, down=stow, left=coral-score, right=coral-intake) — ergonomically clean for 4-preset navigation.

**Driver controller (drivetrain + roller control):**
- A/B: lifter-intake run/stop (onTrue/onFalse pattern — hold-to-run).
- X/Y: claw-intake run/stop (X holds at algae-hold speed on release; Y stops).
- Left/Right Bumper: hang approach / hang execute.
- Start: zeroGyro (bare, see pitfall above).
- POV Up: wheel lock (hold, simulation/test mode).

**Dual intake binding:** both A (lifter intake) and X (claw intake) are bound to separate intake paths on the driver, matching the physical mechanism split. This lets the driver manage roller state directly without mode-switching.

### Motor-controller-conversion unit-transplant pitfall (CONFIRMED IN CODE, 2026-07-03)

Converting a mechanism between motor-controller vendor APIs (REV SparkMax MAXMotion RPM/RPM-per-s ↔ CTRE Phoenix6 rotations/rotations-per-s ↔ CTRE Phoenix5 encoder ticks/ticks-per-100ms) is NOT a like-for-like numeric copy — each API's Motion Magic/MAXMotion cruise-velocity, acceleration, and feedforward constants are in that API's own native units. A 2026-07-03 conversion of the FRC-2025 Lifter/ClawArm/ClawElevator from SparkMax/Phoenix6 to Phoenix5 TalonSRX dropped the old numeric values in raw: the lifter's SparkMax RPM values became a near-full-throttle step command in Phoenix5 ticks/100ms (mechanism range 6733 ticks, "cruise" alone implied 70,000 ticks/s), and the arm's Phoenix6 rotations-per-second value became ~0.1°/s (functionally frozen) in Phoenix5 ticks/100ms. Position setpoints have the same risk: old NEO-rotation values (10–198) were relabeled 1:1 as degrees without rebasing, putting two setpoints ~10% past the mechanism's actual 180° hard stop. Full incident: [[frc2025-talonsrx-bench-hardening]].

## Observations

- [constraint] When converting a mechanism between motor-controller vendor APIs (SparkMax/Phoenix6/Phoenix5, or any future replacement), recompute Motion Magic/MAXMotion cruise, acceleration, and feedforward constants in the TARGET API's native units — never transplant the source API's numeric values directly. #motor-api #gotcha (see [[frc-2026-motor-safety-standards]] for the FRC-2026-side safety-limit half of this)
- [constraint] After any motor-API conversion, verify every position setpoint against the mechanism's actual measured range before enabling closed-loop control — a relabeled/rebased unit (e.g. rotations → degrees) can silently put a setpoint past a hard stop. #motor-api #gotcha
- [constraint] After any motor-API conversion, validate invert + sensor-phase per motor with a low-power blip test (confirm positive output = intended direction AND sensor counts the same way) before any closed-loop move — especially on mechanically-linked/paired mechanisms, where a wrong phase on one side can drive the pair in opposite directions. #motor-api #safety
- [decision] Use `allianceRelativeControl(true)` OR `zeroGyroWithAlliance()` — never both. The 2025 robot uses `allianceRelativeControl(true)` + bare `zeroGyro()` reset; this is the safer default. #swerve #controls (double-compensation causes incorrect field orientation; source: RobotContainer.java line 87 + SwerveSubsystem.java lines 586–597)
- [constraint] When porting swerve config to FRC-2026: verify the gyro-zero binding does NOT call `zeroGyroWithAlliance()` if `allianceRelativeControl(true)` is set. #swerve (audit both places; they are independent API calls)
- [decision] POV/D-pad for discrete position presets is competition-proven and ergonomically preferable to adding more face-button chords. #controls (used for lifter stow/intake/score/clear; source: RobotContainer.java lines 168–171)
- [decision] Bumper-as-mode-switch (left=lifter context, right=claw context) reduces total button count vs separate bank per mechanism. #controls (confirmed working in competition; source: RobotContainer.java lines 163–166)
- [decision] Hold-to-run (onTrue start / onFalse stop) with a safe hold-speed on release (algae-hold constant on claw X release) is better than toggle for roller mechanisms. #controls (prevents unintended stalling; source: RobotContainer.java lines 216–221)

## Open Questions

- [ ] Did the alliance-control double-compensation actually cause field problems in competition, or was it never triggered (bare zeroGyro used, so double-flip never activated)? Source docs do not record a match incident — confidence cap applies.

## Actions

- [ ] FRC-2026 sessions: before binding zeroGyro, grep `allianceRelativeControl` + `zeroGyroWithAlliance` in the new codebase and confirm only one is active.

## Notes for Future Sessions

This note is intended to be found by FRC-2026 development sessions via `/recall frc-2025` or `/recall alliance control`. The alliance-control pitfall is the highest-priority item — it is easy to introduce when copying swerve patterns and produces confusing field-orientation behavior that is hard to diagnose during a match.

The control layout documented here is a working baseline confirmed in 2025 competition; FRC-2026 may diverge (different mechanisms, different game) but the ergonomic principles (POV presets, bumper mode-switch, hold-to-run rollers) are reusable.

## Relations

[[frc-2026]] [[frc-team-3843]] [[frc-2025-source]] [[wpilib-build-env]] [[frc2025-talonsrx-bench-hardening]] [[frc-2026-motor-safety-standards]] [[phoenix5-6-coexistence]]

<!-- @claude 2026-07-04T00:22:46Z — appended motor-controller-conversion unit-transplant lesson from session 58fac07f (TalonSRX bench-hardening); prior model: unattributed -->

