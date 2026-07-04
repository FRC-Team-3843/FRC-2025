---
id: frc2025-talonsrx-conversion-origin
artifact_kind: memory
schema_version: 2
title: FRC-2025 TalonSRX conversion + unit-scaling first pass (origin of the 2026-07-04 demo work)
created: 2026-07-04T04:30:00Z
updated: 2026-07-04T04:30:00Z
valid_until: null
author: antigravity
model: claude-opus-4-8
model_basis: confirmed
status: active
memory_class: episodic
session: fa4471c4-94cd-4494-85fa-2a28de950128
derived_from: C:\Users\dover\AppData\Local\acc\transcripts\PersonalContext\antigravity-fa4471c4-94cd-4494-85fa-2a28de950128.jsonl
entities: [frc-2025-reefscape-hardware, frc-2025-critical-rules, frc-2025-archive-and-api-update, frc-2025-branch-guide]
tags: [frc, reefscape, talonsrx, phoenix5, motion-magic, can-bus, swerve, demo]
aliases: [gemini talonsrx first pass, frc 2025 unit conversion origin, 2025 robot on 2026 base code, subsystem enable flags, elevator lifter arm tick conversion]
scope: FRC-2025
source_basis: transcript
confidence: 70
human_edited: false
sensitivity: normal
supersedes: session-stub-fa4471c4-202607032032
load_profile: on_demand
---

# FRC-2025 TalonSRX conversion + unit-scaling first pass (origin of the 2026-07-04 demo work)

> The Gemini (antigravity) session that first converted the FRC-2025 REEFSCAPE robot's non-drive mechanisms to Phoenix5 TalonSRX on the 2026 base code, established the user-provided tick↔real-unit conversion factors, added per-subsystem boolean disable flags, restored the missing swerve deploy config from an archive branch, and stripped auto/PathPlanner/PhotonVision — the uncommitted working tree that the next session ([[frc2025-talonsrx-bench-hardening]]) reviewed, hardened, and bench-verified.

## Context

User goal: get the 2025 REEFSCAPE robot ([[frc-2025-critical-rules]] — archived repo, `main` branch, `2025Robot/`) running on the 2026 WPILib base code for a demo. The physical robot is the 2025 machine but the code target is 2026 (GradleRIO `2026.2.1`, `projectYear: 2026`). All non-drive/steer mechanisms were being physically re-motored to **brushed CTRE TalonSRX** controllers (Phoenix5), replacing the HEAD mixed stack (Lifter: REV SparkMax/NEO; ClawArm/ClawElevator/intakes: Phoenix6 TalonFX — see [[frc-2025-reefscape-hardware]]). This session produced an **uncommitted working tree**; the immediately-following claude session ([[frc2025-talonsrx-bench-hardening]], `58fac07f`) reviewed it, found several bench-unsafe defects, hardened it, and committed `f95f43e`.

## Discussion

**Motor-controller conversion.** Converted ClawIntake, LifterIntake (both `PercentOutput`), ClawArm, ClawElevator, and the dual Lifter motors from SparkMax/Phoenix6 to Phoenix5 `TalonSRX`. The three position mechanisms (Lifter, ClawArm, ClawElevator) use CTRE `ControlMode.MotionMagic`; intakes are open-loop. Phoenix6 `InvertedValue` was replaced with native boolean inversion. Copied the Phoenix5 (and Phoenix6) vendordeps into the project and **hand-bumped their internal `frcYear` tags to 2026** so the 2026 build system would accept the 2025-season libraries — a bridge workaround the user accepted as temporary. (Note: that frcYear-bump hack later crash-looped at runtime against the 2026 rio image and was replaced with genuine 2026-registry vendordeps in `a46e55b` — see [[frc-2025-reefscape-hardware]].)

**User-provided mechanism travel ranges → unit scaling.** The user supplied measured raw-encoder ranges and their real-world equivalents; the subsystems were rewritten to accept inches/degrees and convert to ticks internally:
- **ClawElevator:** 0–3214 ticks = 23.25 in → `TICKS_PER_INCH = 3214/23.25 ≈ 138.24`. Setpoints reworked to 0 in (stow/low) and 23 in (top), replacing old out-of-range negative tick setpoints (−28/−46). *(Bench-refined later to 3178 ticks = 23.25 in.)*
- **Lifter (right):** 0 (stowed) → −6733 ticks = 180°. Left is symmetric but counts **positive**. Resolved by making `TICKS_PER_DEGREE = 6733/180 ≈ 37.4` positive and adding sensor-phase flags — `RIGHT_SENSOR_PHASE=true`, `LEFT_SENSOR_PHASE=false` — so the right encoder counts up as it deploys to match the left; `RIGHT_MOTOR_INVERT` left false.
- **ClawArm:** 0–795000 ticks = 128° → `TICKS_PER_DEGREE = 795000/128 ≈ 6210.9`. Existing arm setpoints (0.77 … 53) were already degree-sized and left unchanged.

**Per-subsystem disable flags.** Added a `Constants.SubsystemEnables` nested class with a boolean per non-drive subsystem; a false flag skips motor-controller instantiation (saving CAN bandwidth) and makes run-calls cleanly no-op. Requested states: `BRAKES_ENABLED=false`, `CLAW_INTAKE_ENABLED=false`, `LIFTER_INTAKE_ENABLED=false`; `CLAW_ELEVATOR_ENABLED=true`, `CLAW_ARM_ENABLED=true`, `LIFTER_ENABLED=true`. The PWM parking-brake/lineup servos were physically removed, so their code was disabled.

**Swerve config recovered from archive branch.** The `deploy/swerve/*.json` YAGSL configs were missing from `main`. After ruling out the FRC-2026 repo (see revert episode below), they were found on the **`2025_Offseason_archive`** branch (branches: `main`, `2025_Archive`, `2025_Offseason_archive` — see [[frc-2025-branch-guide]]), checked out into `2025Robot/src/main/deploy/swerve/`, and re-ID'd. Also added `PowerDistribution(1, kCTRE)` to `RobotContainer.java`.

**Auto / PathPlanner / PhotonVision removed.** Deleted `PathplannerLib` + `photonlib` vendordeps and `Vision.java`; stripped AutoBuilder/NamedCommands/SendableChooser and photonvision aim-at-target/pathfinding code from `SwerveSubsystem.java` and `RobotContainer.java`; autonomous reduced to `Commands.none()`.

**Wrong-repo edit + revert (process note).** Looking for the missing swerve configs, the agent briefly edited the **FRC-2026** repo's swerve JSONs and PDP instantiation, believing the configs lived there. The user corrected it ("I made a mistake and looked in the 2026 folder too… very thoroughly undo your actions"); the agent fully reverted the FRC-2026 changes via git, leaving that repo clean.

## Observations

- [fact] User-measured mechanism travel ranges (raw ticks → real units): ClawElevator 3214 tk = 23.25 in; Lifter 6733 tk = 180° (right counts negative, left positive); ClawArm 795000 tk = 128°. These are physical robot facts, source of the Motion Magic conversion constants. #hardware #motion-magic
- [fact] Swerve CAN reassignment applied this session: Drive 10/11/12/13, Steer 14/15/16/17, CANCoders 18/19/20/21 (FL/FR/BR/BL), PDP=1 (CTRE) — restored from the `2025_Offseason_archive` branch's deploy/swerve, which was absent from `main`. #can-bus #swerve
- [fact] Final non-drive CAN map after elevator re-ID: Lifter right=31/left=32, ClawIntake=33, ClawArm=34, LifterIntake=35, ClawElevator=36 (re-ID'd 50→36) — all TalonSRX. #can-bus
- [decision] Per-subsystem boolean disable via `Constants.SubsystemEnables` — a false flag skips instantiation and no-ops run calls; brakes/claw-intake/lifter-intake disabled, elevator/arm/lifter enabled. #design-pattern
- [gotcha] The `frcYear`-hand-bump on 2025-season vendordeps was introduced here as a 2026-build bridge; it built but later crash-looped at runtime and was replaced with genuine 2026-registry vendordeps. #vendordep #build
- [action] The FRC-2026 repo was briefly and mistakenly edited (swerve JSONs + PDP), then fully git-reverted after user correction — 2025's deploy/swerve lives on 2025's archive branch, not in the 2026 repo. #process
- [action] Session ended with the user asking that all provided numbers be left as code comments above their constants; the agent did so and logged a changelog entry (its own, `AGENT/gemini-2.5-pro [REFACTOR]`). Left uncommitted; hardened+committed by the next session. #handoff

## Notes for Future Sessions

This is the **origin narrative**; the current committed state and all bench-verified invert/phase/kF/scale values live in [[frc2025-talonsrx-bench-hardening]], [[frc2025-parade-demo-bench-verification]], and the [[frc-2025-reefscape-hardware]] profile — prefer those for "what is true now." Two facts from this session are **not yet reflected in the hardware profile's CAN table** and should be verified against committed `main`: (1) the **swerve CAN reassignment to 10–21** (the profile's swerve section still shows the historical 1–12) — confirm whether this working-tree change was committed in `f95f43e` or superseded; (2) the **auto/PathPlanner/PhotonVision removal** (the profile's Vision/PhotonVision entries may be stale). The elevator scale 3214 and the guessed invert/phase values from this pass were later corrected on the bench — do not treat this session's numeric constants as final.

## Relations

- relates_to [[frc2025-talonsrx-bench-hardening]] (the next session that reviewed + hardened + committed this working tree)
- relates_to [[frc2025-parade-demo-bench-verification]] (bench arc that finalized the invert/phase/kF/scale values first written here)
- relates_to [[frc-2025-talonsrx-conversion-confirmed]] (user hardware confirmation the hardening depended on)
- relates_to [[frc-2025-reefscape-hardware]] (semantic profile; this session's user-measured travel ranges appended there)
- relates_to [[frc-2025-branch-guide]] (the archive branch the swerve configs were recovered from)
- relates_to [[frc-2025-critical-rules]] (archived-repo guard; edits here were explicit user instruction)
