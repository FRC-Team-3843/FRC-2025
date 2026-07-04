---
id: frc2025-talonsrx-bench-hardening
title: FRC-2025 TalonSRX conversion hardened for 2026-07-04 demo bench bring-up
schema_version: 2
created: 2026-07-04T00:22:46Z
updated: 2026-07-04T00:22:46Z
valid_until: null
author: claude
model: claude-fable-5
model_basis: confirmed
session: 58fac07f-7bc8-41da-95a6-4bdb25ac688f
derived_from: C:\Users\dover\AppData\Local\acc\transcripts\PersonalContext\claude-58fac07f-7bc8-41da-95a6-4bdb25ac688f.jsonl
entities: [frc-2025-reefscape-hardware, frc-2025-critical-rules, frc-2025-archive-and-api-update]
tags: [frc, reefscape, talonsrx, phoenix5, motion-magic, bench-test, demo, safety]
aliases: [frc-2025-demo-bringup, lifter claw arm elevator talonsrx conversion, gemini phoenix5 review]
related: []
status: active
supersedes: session-stub-58fac07f-202607040022
confidence: 70
source_basis: transcript
human_edited: false
sensitivity: normal
decisions: []
artifact_kind: memory
memory_class: episodic
scope: FRC-2025
---

# FRC-2025 TalonSRX conversion hardened for 2026-07-04 demo bench bring-up

> With a demo the next day, a Gemini-authored (uncommitted) conversion of the Lifter/ClawArm/ClawElevator subsystems to Phoenix5 TalonSRX was reviewed, found to have several bench-unsafe defects (unit transplants, over-range setpoints, unvalidated invert/phase, no safety limits), hardened for safe bench testing, and committed (`f95f43e`).

## Context

User goal: get the 2025 REEFSCAPE robot (`FRC-2025`, archived repo, `main` branch — see [[frc-2025-critical-rules]]) working for a demo on 2026-07-04, focusing on the Lifter (dual motor, mechanically linked — unequal motion breaks the mechanism), ClawArm, and ClawElevator. A prior Gemini session had left an uncommitted working-tree conversion of all three mechanisms from their HEAD controllers (Lifter: REV SparkMax/NEO; ClawArm/ClawElevator: CTRE Phoenix6 TalonFX — see [[frc-2025-reefscape-hardware]]) to CTRE Phoenix5 TalonSRX + quadrature encoders, plus a CAN re-ID of the elevator (50→36) and eight leftover Python bulk-edit scripts in `2025Robot/`. User explicitly wanted a conservative, disable-button-ready bench-test strategy before touching the physical mechanism, using Motion Magic trapezoidal profiles at low output.

## Discussion

**Review (dispatched `general-purpose` subagent, model `claude-fable-5`, read-only, 28 tool uses, ~540s):** confirmed the conversion direction was the opposite of the initial framing — HEAD had mixed SparkMax/Phoenix6, the working tree moved everything to Phoenix5 TalonSRX. Ranked bug list (full text in the subagent's report, condensed here):

- **C1 [CRITICAL, Lifter]** Motion Magic cruise/accel (7000/28000 ticks-per-100ms) were the old SparkMax MAXMotion RPM values dropped raw into Phoenix5 tick units — effectively a full-throttle step command on a 6733-tick-range mechanism, no current limit.
- **C2 [CRITICAL, Lifter]** Setpoints (193°, 198°) exceed the measured 180° hard stop — root cause: NOTES.md's old NEO-rotation values (10–198) were relabeled 1:1 as degrees without rebasing.
- **C3 [CRITICAL, Lifter]** Sign/sensor-phase values (`RIGHT_MOTOR_INVERT=false`, `LEFT_MOTOR_INVERT=true`, asymmetric `setSensorPhase`) were Gemini guesses, never validated on hardware — highest bench risk given the two sides are mechanically linked and independent (no follower, no cross-side check).
- **C4 [CRITICAL, Lifter]** No cross-side divergence protection; `getPosition()` averages both encoders so a diverging pair reads as "on target."
- **C5 [CRITICAL, ClawElevator]** CAN ID changed 50→36 with no documented reason in the scripts — required live user confirmation.
- **C6 [CRITICAL, ClawElevator]** Sign flipped positive with factory-default invert/phase (HEAD was negative-up); TOP (−46) and L2 (−28) setpoints were collapsed into one positive value (23 in), so L2 commands now drive to full travel.
- **C7 [ClawArm]** Motion Magic cruise/accel (60/120) were Phoenix6 rot/s values frozen into Phoenix5 ticks/100ms — arm would move at ~0.1°/s (~4 min stow→L1), stalling every `WaitUntilCommand` sequence that references it.
- **C8–C14**: same unit-transplant risk on the arm's position constants, no current/soft limits or neutral-mode configuration anywhere, no homing/zeroing (quad encoders zero at boot), invented kF values (Phoenix6 kS volts reused as Phoenix5 kF), vendordep/toolchain year mismatch (GradleRIO 2026.2.1 vs 2025-season vendordeps), and a clean Vision.java deletion. Compile inspection found no syntax errors; the 8 Python scripts' transformations were fully applied (one earlier silent partial-failure in `invert_lifter.py` was caught and retried by `fix_constants.py`).

**User confirmation (`AskUserQuestion`):** all three mechanisms are now physically TalonSRX (brushed motors + quad encoders); the elevator's CAN 36 is correct (re-ID'd from 50, NOTES.md was stale); go ahead and write the Phase-2 safe-test build now, lifter first. See [[frc-2025-talonsrx-conversion-confirmed]].

**Hardening (main agent, same model):** rewrote Motion Magic cruise/accel in each mechanism's correct native units at bench speeds (lifter ~27°/s, arm ~5°/s, elevator ~7 in/s — all far below the transplanted values); remapped lifter positions from NEO rotations to degrees via `(rot−10)*180/188` (climbing-approach corrected 198°→180.0°, no longer past the hard stop); restored the elevator's distinct L2 (14.0 in) vs TOP (23.0 in) setpoints; added peak-output clamps (0.2 lifter/arm, 0.25 elevator), supply-current limits (10 A continuous / 20 A peak), soft limits just past each measured range, brake neutral mode, and boot encoder zeroing on all three; added a **lifter divergence watchdog** in `periodic()` — compares both encoders, latches a fault and forces both motors to neutral every loop once the split exceeds 3°, cleared only by a code restart; added SmartDashboard telemetry (both lifter encoders, delta, outputs, fault flag) and open-loop jog methods; added `Constants.BENCH_TEST_MODE = true`, which replaces competition bindings with bring-up controls (sticks = open-loop jog; A/B/X/Y/LB/RB = small closed-loop test moves per mechanism; Back = panic-stop everything) — flip to `false` to restore competition bindings for the demo. Updated `NOTES.md` with the new CAN table, position-unit tables (new values + old-bot reference values), and the bench bring-up checklist. `compileJava` passed (WPILib JDK17). Committed `f95f43e` on `main` with a co-author trailer; `FRC-2025/.changelog.md` entry added.

Left deliberately untouched: Gemini's 8 leftover `update_*.py`/`fix*.py`/`invert_lifter.py` scripts (untracked in `2025Robot/`, harmless, delete once confident); `2025Robot-Offseason`'s deploy files sit staged-then-worktree-deleted from an earlier, unrelated in-flight move — not this session's to resolve either way.

## Observations

- [fact] Working-tree conversion moved ALL THREE mechanisms (Lifter, ClawArm, ClawElevator) to Phoenix5 TalonSRX + quad encoders, reversing HEAD's mixed SparkMax(lifter)/Phoenix6(claw) stack — user-confirmed as the actual physical hardware state. #hardware
- [fact] ClawElevator CAN ID is 36, not 50 (NOTES.md and prior hardware profile were stale) — user-confirmed re-ID. #can-bus
- [gotcha] Phoenix5 native units (encoder ticks, ticks/100ms) do not carry over from other vendor APIs — SparkMax RPM/MAXMotion values and Phoenix6 rotations/rot-per-s values dropped in raw produced a near-full-throttle step (lifter) and a functionally-frozen mechanism (arm), respectively. #motion-magic #gotcha
- [gotcha] `setInverted` on a Phoenix5 TalonSRX does NOT flip the quadrature sensor — `setSensorPhase` is the only thing that determines sensor sign vs. motor output; wrong phase on a mechanically-linked pair can drive the two sides in opposite directions. #phoenix5 #gotcha
- [decision] Added a lifter left/right divergence watchdog (latch + neutral both sides at 3° split, code-restart-only clear) as the software backstop for the linked-mechanism risk the user named at session start. #safety
- [action] Bench verification (invert/phase blip test, kF measurement, setpoint verification, then `BENCH_TEST_MODE=false` for the demo) is tracked in [[frc-2025-bench-verification-checklist]] — not yet done as of session end. #task

## Notes for Future Sessions

The code is bench-testable, not demo-ready: every `*_MOTOR_INVERT`/`*_SENSOR_PHASE` constant is an unvalidated guess until the Tuner blip test (positive output → deploy/up AND sensor counts up, on every motor; lifter both sides must agree before any closed-loop move). kF is 0 until measured. Mechanisms must be manually pre-positioned at stow/bottom before every power-on (encoders zero at boot, no absolute sensor). Full step-by-step checklist lives in `FRC-2025/NOTES.md` and [[frc-2025-bench-verification-checklist]]. Gemini's 8 leftover Python scripts in `2025Robot/` are cleanup-when-convenient, not urgent.

## Relations

- relates_to [[frc-2025-talonsrx-conversion-confirmed]] (the user-confirmed hardware decision this session's fixes depend on)
- relates_to [[frc-2025-bench-verification-checklist]] (the open bench-test task this session left pending)
- relates_to [[frc-2025-reefscape-hardware]] (superseded hardware facts updated in this scope's semantic profile)
- relates_to [[frc-2025-archive-and-api-update]] (motor-stack drift noted against this decision)
- relates_to [[frc-2025-critical-rules]] (archived-repo guard; this session's edits were explicit user instruction)
- derived_from subagent a1116f0da6a401440 (same conversation — read-only code review)
