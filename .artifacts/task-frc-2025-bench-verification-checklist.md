---
id: frc-2025-bench-verification-checklist
artifact_kind: task
schema_version: 2
title: Bench-verify FRC-2025 TalonSRX invert/phase, kF, and setpoints before the 2026-07-04 demo
created: 2026-07-04T00:22:46Z
updated: 2026-07-04T00:22:46Z
author: claude
model: claude-fable-5
model_basis: confirmed
status: open
tags: [frc, bench-test, talonsrx, safety, demo]
aliases: [frc-2025 bench checklist, tuner blip test, kf measurement, bench_test_mode verification]
entities: [frc-2025-reefscape-hardware]
load_profile: on_demand
sensitivity: normal
source_basis: transcript
confidence: 70
human_edited: false
session: 58fac07f-7bc8-41da-95a6-4bdb25ac688f
derived_from: C:\Users\dover\AppData\Local\acc\transcripts\PersonalContext\claude-58fac07f-7bc8-41da-95a6-4bdb25ac688f.jsonl
scope: FRC-2025
---

# Bench-verify FRC-2025 TalonSRX invert/phase, kF, and setpoints before the 2026-07-04 demo

> Commit `f95f43e` made the TalonSRX conversion bench-testable but NOT demo-ready — every invert/sensor-phase value is an unvalidated guess, kF is 0, and setpoints are unverified. Full step-by-step checklist lives in `FRC-2025/NOTES.md` "Bench Bring-Up Checklist"; this task tracks it as an open item for the ACC corpus.

## Context

Left open at the end of the 2026-07-03 hardening session (see [[frc2025-talonsrx-bench-hardening]]), ahead of the 2026-07-04 demo.

## Actions

- [ ] Tuner X audit (robot on blocks, before deploying): confirm TalonSRX at CAN 31/32/34/36, set Brake on all four, check firmware.
- [ ] Blip test each motor (~3% output in Tuner, fractions of a second, mechanism mid-range): confirm positive output moves toward deploy/up AND the sensor counts up. Fix `*_MOTOR_INVERT`/`*_SENSOR_PHASE` constants until true on every motor. Lifter: both sides must agree before any closed-loop move — `setInverted` does NOT flip the Phoenix5 quad sensor.
- [ ] Measure kF per mechanism: steady duty in Tuner → read velocity (ticks/100ms) → `kF = duty * 1023 / velocity`; enter in `Constants.java` (currently 0 on all three).
- [ ] First closed-loop moves with rider on disable: operator A/B = lifter 10°/0°, X/Y = elevator 2 in/0, LB/RB = arm 5°/stow. Watch `Lifter/DeltaDeg` on SmartDashboard — the divergence watchdog latches + neutrals at 3° split (clears only via code restart).
- [ ] Extend range gradually; verify each named setpoint (lifter degrees via `(rot-10)*180/188`; elevator L2 14.0 in vs TOP 23.0 in; arm degrees carried 1:1 from old TalonFX rotations — all marked TO-VERIFY in NOTES.md).
- [ ] Raise `MOTOR_MAX_VELOCITY`/`ACCELERATION` toward demo speed once verified.
- [ ] Set `Constants.BENCH_TEST_MODE = false` to restore competition bindings for the demo.
- [ ] Every power-on: confirm mechanisms are at stow/bottom first (encoders zero at boot, no absolute sensor/homing).

## Relations

- relates_to [[frc2025-talonsrx-bench-hardening]] (the session that produced the code this checklist verifies)
- relates_to [[frc-2025-talonsrx-conversion-confirmed]] (the hardware decision this checklist's Tuner-first step verifies)
