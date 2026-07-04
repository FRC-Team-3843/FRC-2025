---
id: frc-2025-talonsrx-conversion-confirmed
artifact_kind: decision
schema_version: 2
title: FRC-2025 hardware confirmed all-TalonSRX; elevator CAN re-ID'd 50 to 36
created: 2026-07-04T00:22:46Z
updated: 2026-07-04T00:22:46Z
author: claude
model: claude-fable-5
model_basis: confirmed
status: chosen
question: "Is Gemini's uncommitted conversion's premise correct — are the Lifter (CAN 31/32), ClawArm (CAN 34), and ClawElevator physically TalonSRX + quadrature encoders now, and is the elevator's CAN ID 36 (not the NOTES.md-documented 50)?"
position: "Yes to both: all three mechanisms are physically TalonSRX now, and the ClawElevator's CAN ID is 36, re-ID'd from 50 — confirmed directly by the user via AskUserQuestion before any bench-test code was written."
reasoning: |
  P1 [transcript]: user directly answered "Yes, all TalonSRX now" — the lifter, arm, and elevator motor controllers were physically converted; this is a fact about the user's own robot hardware that neither the code nor a review can independently establish.
  P2 [transcript]: user directly answered "36 (hardware was re-ID'd)" over the NOTES.md-documented 50.
  P3 [offered_by: agent, vetted: false]: the working-tree code is internally consistent with the all-TalonSRX premise (Phoenix5 imports and TalonSRX construction uniform across Lifter/ClawArm/ClawElevator; no leftover Phoenix6/SparkMax calls) — corroborating, not independently establishing. Excluded from chain strength.
  Warrant: the user is the sole authoritative source on the robot's actual physical wiring; a code review can flag internal inconsistency but cannot confirm real-world CAN topology, so a direct question was the correct and sufficient resolution method before writing safety-relevant code.
  defeaters:
    - Not yet verified live in Phoenix Tuner (device-type + CAN-ID readback) — the user's answer reflects knowledge of what was physically installed, not a bench observation from this session.
    - If Tuner disagrees (e.g. CAN 31/32 still enumerate as SparkMax, or 36 doesn't respond), this decision must be revisited before any further bench work.
    - If a different device is later found occupying CAN 36, the elevator's Motion Magic commands would silently target the wrong hardware.
  confidence: 80
tags: [frc, decision, hardware, can-bus, talonsrx]
aliases: [elevator can 36 confirmed, all mechanisms talonsrx, lifter arm elevator hardware confirmation]
entities: [frc-2025-reefscape-hardware]
load_profile: on_demand
source_basis: transcript
confidence: 80
human_edited: false
sensitivity: normal
session: 58fac07f-7bc8-41da-95a6-4bdb25ac688f
derived_from: C:\Users\dover\AppData\Local\acc\transcripts\PersonalContext\claude-58fac07f-7bc8-41da-95a6-4bdb25ac688f.jsonl
supersedes: null
scope: FRC-2025
---

# FRC-2025 hardware confirmed all-TalonSRX; elevator CAN re-ID'd 50 to 36

> User confirmed via AskUserQuestion (2026-07-04): the Lifter (CAN 31/32), ClawArm (CAN 34), and ClawElevator are all now physically CTRE TalonSRX + quadrature encoders (not the HEAD mixed SparkMax/Phoenix6 stack), and the ClawElevator's CAN ID is 36, re-ID'd from the NOTES.md-documented 50.

## Context

A review subagent found Gemini's uncommitted working-tree code assumed all three mechanisms run Phoenix5 TalonSRX and that the elevator is on CAN 36 — both premises the code has no way to verify itself (a TalonSRX cannot drive a NEO; a wrong CAN ID just means silent config failure). Before writing any bench-test hardening, the orchestrator asked the user directly rather than guessing, alongside whether to proceed with the Phase-2 safe-test build (yes).

## Observations

- [decision] All three mechanisms (Lifter CAN 31/32, ClawArm CAN 34, ClawElevator CAN 36) are physically TalonSRX + quadrature encoders as of 2026-07-04. #hardware #can-bus
- [decision] ClawElevator CAN ID is 36 (re-ID'd from 50); NOTES.md's prior "50" entry and the pre-existing [[frc-2025-reefscape-hardware]] profile were stale and updated in this session. #can-bus

## Notes for Future Sessions

Treat "all TalonSRX, elevator=36" as settled unless the Tuner-first step of [[frc-2025-bench-verification-checklist]] contradicts it — in that case stop and revisit this decision before proceeding.

## Relations

- relates_to [[frc2025-talonsrx-bench-hardening]] (the session and hardening work that depended on this confirmation)
- relates_to [[frc-2025-reefscape-hardware]] (superseded CAN table + motor-stack facts)
- relates_to [[frc-2025-bench-verification-checklist]] (the Tuner-first verification this decision's defeater requires)
