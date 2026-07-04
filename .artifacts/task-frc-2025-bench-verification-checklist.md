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
status: resolved
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

> RESOLVED 2026-07-04, later the same session: all invert/sensor-phase values bench-verified via blip test + live NetworkTables telemetry, arm kF measured, elevator scale recalibrated, and closed-loop moves confirmed on all three mechanisms. Full account: [[frc2025-parade-demo-bench-verification]]. Two residuals noted below, neither blocking.

## Context

Left open at the end of the 2026-07-03 hardening session (see [[frc2025-talonsrx-bench-hardening]]), ahead of the 2026-07-04 demo. Resolved later the same session (post-compaction) once Driver Station connectivity was restored — see [[frc2025-parade-demo-bench-verification]] for the DS no-comms diagnosis that had to be solved first.

## Actions

- [x] Tuner X audit — done at the app level: operator-pad blip test (not literal Tuner X) plus live NT telemetry confirmed all four TalonSRX (CAN 31/32/34/36) respond correctly.
- [x] Blip test each motor — done. Lifter: `LEFT_SENSOR_PHASE` was the wrong constant (both motor directions were already correct), flipped `false→true`, confirmed via two independent divergence-fault log captures. Arm: `SENSOR_PHASE` flipped `false→true` (second independent reconfirmation that `setInverted` does not flip the Phoenix5 quad sensor). Elevator: both `MOTOR_INVERT` and `SENSOR_PHASE` flipped `false→true`.
- [x] Measure kF — arm: **0.056**, measured directly from a live runaway's telemetry. Elevator: no velocity-based kF measured, but a gravity feedforward (0.2) was added empirically (mechanism free-falls at 0 output). **Lifter kF remains 0 (unmeasured)** — demo speed was reached via kP=1.0 + cruise/accel/peak tuning alone; residual, not blocking, flagged in [[frc2025-parade-demo-bench-verification]].
- [x] First closed-loop moves with rider on disable — done for lifter, arm, and elevator; all confirmed via telemetry.
- [x] Extend range gradually; verify named setpoints — lifter 120°, arm 123° (5° shy of true max), elevator 21.25 in (2 in shy of true max) all reached and held during parade-cycle testing.
- [x] Raise `MOTOR_MAX_VELOCITY`/`ACCELERATION` toward demo speed — lifter 2x (~53°/s), arm 1.5x (~12°/s), elevator unchanged; divergence threshold raised 3°→5° after a benign same-sign graze at the new lifter speed.
- [~] Set `Constants.BENCH_TEST_MODE = false` — **superseded, not done.** The parade demo intentionally runs in bench mode (POV-right toggle in teleop, or autonomous) rather than flipping to competition bindings — this was an explicit scope decision, not a skipped step. See [[frc2025-parade-demo-bench-verification]].
- [x] Every power-on: confirm mechanisms at stow/bottom first — followed as standing operating procedure throughout the bench session; remains true going forward (encoders still zero at boot, no absolute sensor/homing added except the arm's Start-button re-zero).

## Relations

- relates_to [[frc2025-talonsrx-bench-hardening]] (the session that produced the code this checklist verifies)
- relates_to [[frc-2025-talonsrx-conversion-confirmed]] (the hardware decision this checklist's Tuner-first step verifies)
- relates_to [[frc2025-parade-demo-bench-verification]] (the session arc that resolved this checklist)

<!-- @claude 2026-07-04T03:20:41Z — resolved: all bench-verification actions completed or explicitly superseded per session 58fac07f's parade-demo arc; prior model: claude-fable-5 -->

