---
id: frc-2025-reefscape-hardware
title: FRC-2025 Reefscape Robot Hardware Configuration
schema_version: 2
created: 2026-06-14T12:35:00Z
updated: 2026-07-04T03:20:41Z
valid_until: null
author: claude
session: null
tags: [frc, reefscape, hardware, can-bus, motors, swerve, 2025, talonsrx]
aliases: [frc 2025 hardware, reefscape hardware, 2025 robot hardware, reefscape can bus, 2025 mechanisms]
status: active
supersedes: null
confidence: 55
source_basis: document
human_edited: false
sensitivity: normal
decisions: []
model: claude-sonnet-5
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

### 2026-07-04 update, later same session — invert/phase/kF/scale bench-verified (supersedes the "UNVALIDATED" note below)

- [registry] **Lifter:** `RIGHT_MOTOR_INVERT=false` / `RIGHT_SENSOR_PHASE=true` unchanged (verified correct); `LEFT_MOTOR_INVERT=true` unchanged; `LEFT_SENSOR_PHASE: false→true` — the wrong constant was the phase, not the invert, confirmed by two independent divergence-fault log captures both showing equal-and-opposite tick signs after both motor directions blip-tested correct. `kF` remains **0, unmeasured** (demo speed reached via kP=1.0 + cruise/accel/peak tuning alone — residual gap, not blocking). #talonsrx #bench-verified
- [registry] **ClawArm:** `MOTOR_INVERT=false` unchanged (correct); `SENSOR_PHASE: false→true` — a second independent reconfirmation that Phoenix5 `setInverted` does NOT flip the quadrature sensor (found via a live runaway: +0.2 duty sustained while ticks ran to −260k over ~7s). `kF = 0.056`, measured directly from that runaway's recorded velocity. #talonsrx #bench-verified
- [registry] **ClawElevator:** `MOTOR_INVERT: false→true` AND `SENSOR_PHASE: false→true` (both wrong — blip showed positive output moving down; Phoenix5 invert also flips the sensor reading, so both had to move together). Scale recalibrated from measured full-travel data: **3178 ticks = 23.25 in** (~136.9 ticks/in; the assumed 3214-tick figure was within 1%, now replaced). Gravity feedforward **0.2** added to Motion Magic — the mechanism free-falls at 0 output and 0.25 duty alone could not lift it unassisted. Peak clamps made asymmetric: **0.55 up / 0.25 down**. Jog-release now holds closed-loop instead of free-falling. #talonsrx #bench-verified
- [registry] **Demo-speed constants (parade cycle, `9ff9684`):** lifter cruise 100→200 ticks/100ms (~27→53°/s), accel→300, peak 0.35→0.5, kP→1.0; arm cruise 5000→7500 ticks/100ms (~8→12°/s), peak 0.4→0.55; lifter divergence-fault threshold raised **3°→5°** after a benign same-sign graze at the new speed (equal-and-opposite would NOT have been raised — see [[frc-2025-lessons-reefscape]] for the signature-interpretation rule). #motion-magic
- [registry] **Vendordeps, genuine 2026 registry (commit `a46e55b`):** Phoenix5 5.36.0, Phoenix6 26.3.0, REVLib 2026.0.5, YAGSL 2026.4.1, Studica 2026.0.2, ReduxLib 2026.1.2, ThriftyLib 2026.1.2 — replaces the prior 2025-dated files that had only their `frcYear` field hand-edited (built fine, crash-looped at runtime against the 2026 rio image). #vendordep #build
- [registry] Full diagnosis + bench-verification narrative: [[frc2025-parade-demo-bench-verification]]. The checklist this update resolves: [[frc-2025-bench-verification-checklist]] (status: resolved).

### 2026-07-04 update — all mechanisms converted to TalonSRX (current state; supersedes the CAN table + motor stack below)

- [registry] **CURRENT CAN table (main, as of commit `f95f43e`):** Lifter right=31 / left=32, ClawIntake=33, ClawArm=34, LifterIntake=35, **ClawElevator=36 (re-ID'd from 50)** — ALL SIX now CTRE **Phoenix5 TalonSRX** + quadrature encoders (brushed motors), not the mixed SparkMax/Phoenix6 stack recorded below. User-confirmed physical hardware change; see [[frc-2025-talonsrx-conversion-confirmed]]. #can-bus #mechanisms #talonsrx
- [registry] Motion Magic is now driven in Phoenix5 native units (encoder ticks, ticks/100ms) — NOT the RPM/rotations-per-second units the SparkMax/Phoenix6 stack used; a straight numeric transplant between these unit systems is a confirmed real bug pattern, see [[frc-2025-lessons-reefscape]]. #motion-magic
- [registry] All three mechanisms (Lifter, ClawArm, ClawElevator) now have: current limits (10A cont/20A peak), soft limits, brake neutral mode, boot encoder zeroing, and (Lifter only) a left/right divergence watchdog — added in the 2026-07-03 bench-hardening pass, full detail in [[frc2025-talonsrx-bench-hardening]]. #safety
- [registry] Invert/sensor-phase values for all six TalonSRX are UNVALIDATED as of this update — pending the Tuner blip test in [[frc-2025-bench-verification-checklist]]. #gotcha

### CAN Bus Map (historical — HEAD/pre-2026-07 state; superseded above for Lifter/ClawArm/ClawElevator)

- [registry] Swerve modules: CAN IDs 1–12 (drive motors, steer motors, CANCoders, 3 per module × 4 modules) — YAGSL-managed #can-bus #drivetrain
- [registry] Lifter (dual motor): CAN ID 31 (right, not inverted) + CAN ID 32 (left, inverted) — REV SparkMax brushless #can-bus #mechanisms
- [registry] ClawIntake motor: CAN ID 33 — CTRE TalonFX (Phoenix 6), Clockwise_Positive invert #can-bus #mechanisms
- [registry] ClawArm motor: CAN ID 34 — CTRE TalonFX (Phoenix 6), MotionMagic position control #can-bus #mechanisms
- [registry] LifterIntake motor: CAN ID 35 — CTRE TalonFX (Phoenix 6), Clockwise_Positive invert #can-bus #mechanisms
- [registry] ClawElevator motor: CAN ID 50 — CTRE TalonFX (Phoenix 6), position-controlled #can-bus #mechanisms — **superseded, now CAN 36, see the 2026-07-04 update above**

### Motor Controller Stack (historical — HEAD/pre-2026-07 state; superseded above)

- [registry] Lifter uses REV SparkMax with SparkMaxConfig (2025+ API), SmartCurrentLimit 20/30/120A; closed-loop position via SparkClosedLoopController; PID: P=0.1, I=0, D=0.01 #motors #rev — **superseded, Lifter is now TalonSRX, see the 2026-07-04 update above**
- [registry] Claw mechanisms (ClawArm, ClawIntake, ClawElevator, LifterIntake) use CTRE TalonFX (Phoenix 6); ClawArm uses MotionMagicVoltage for smooth position control #motors #ctre — **superseded, all now TalonSRX (Phoenix5), see the 2026-07-04 update above**
- [registry] Mixed stack consequence: two vendor libraries required (REVLib + CTRE Phoenix 6); note the absence of Phoenix 5 (no CANSparkMax old API on main branch) #dependency — **superseded: Phoenix5 is now present and is the primary mechanism API; see the 2026-07-04 update above**

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

- relates-to [[frc-2025-source]] (this hardware lives in that repo)
- relates-to [[frc-2025-reefscape-game]] (hardware designed for this game's constraints)
- relates-to [[frc-2026]] (compare hardware choices; 2026 moved to different swerve hardware)
- relates-to [[yagsl]] (drivetrain control library, see FRC-2026 note)
- relates-to [[phoenix6-ctre]] (claw subsystem motor controllers, see FRC-2026 note)
- relates-to [[revlib-sparkmax]] (lifter motor controllers historically; superseded, see 2026-07-04 update)
- relates-to [[frc2025-talonsrx-bench-hardening]] (session that converted + hardened all mechanisms to TalonSRX)
- relates-to [[frc-2025-talonsrx-conversion-confirmed]] (user confirmation this update is based on)
- relates-to [[frc-2025-bench-verification-checklist]] (verification of the invert/phase/kF/scale values now resolved)
- relates-to [[frc2025-parade-demo-bench-verification]] (bench-verification session that finalized the invert/phase/kF/scale values above)

<!-- @claude 2026-07-04T00:22:46Z — appended 2026-07-04 update section (CAN table, motor stack, unit-transplant note) reflecting the TalonSRX conversion confirmed + hardened in session 58fac07f; prior model: claude-sonnet-4-6 -->
<!-- @claude 2026-07-04T03:20:41Z — appended bench-verified invert/phase/kF/scale + vendordep-resolution update from session 58fac07f's parade-demo arc; prior model: claude-sonnet-4-6 -->

