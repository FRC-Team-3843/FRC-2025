---
id: frc-2025-archive-and-api-update
artifact_kind: decision
schema_version: 2
title: FRC-2025 Archive Status + main-branch API Update
created: 2026-06-23T19:00:00Z
updated: 2026-07-04T03:20:41Z
author: claude
model: claude-sonnet-5
model_basis: confirmed
status: chosen
load_profile: scope_entry
scope: FRC-2025
source_rel: FRC-2025\.context.md
tags: [frc, decision, archive, motor-api, branch]
question: "What is the lifecycle status of FRC-2025, and what is the motor-API/architecture state of its main branch?"
position: "Repo is archived (no further active development, 2026-04-10); main-branch motor APIs were updated to 2026 standards (SparkMax + Phoenix6) on 2026-01-23 but architecture was not fully refactored — old CANSparkMax API survives only on the deprecated branches."
---

# FRC-2025 Archive Status + main-branch API Update

> Two ratified choices for FRC-2025: (1) the repo is archived with no further active development (2026-04-10); (2) the `main` branch's motor APIs were updated to 2026 standards on 2026-01-23, but the architecture was not fully refactored, and the old CANSparkMax API remains only on the deprecated branches.

## Context

Recent decisions for the FRC-2025 repository, extracted from `.context.md` Recent Decisions (cross-referencing `.project-context.md` and `.standards.md`). Both are settled positions that govern how the repo is treated and referenced.

## Observations

### Chosen — Archive status (2026-04-10)

- [decision] Repo declared archived; no further active development expected. The 2025 REEFSCAPE season is complete. `2025_Archive` and `2025_Offseason_archive` branches are deprecated; `main` has updated APIs but partially-refactored architecture. See FRC-2026 for current standards and active development. (source: `.project-context.md`, `.context.md`) #archive

### Chosen — main-branch API update (2026-01-23)

- [decision] The `main` branch's motor APIs were updated to 2026 standards — `SparkMax` (with `SparkMaxConfig`) + Phoenix6 (`TalonFX`). The architecture was NOT fully refactored: command factories are incomplete and some deprecated patterns may remain. The old `CANSparkMax` API exists only on the deprecated branches (`2025_Archive`, `2025_Offseason_archive`). (source: `.standards.md` 2026-01-23 / Current State; `.context.md`) #motor-api #architecture — **NOTE (2026-07-04): superseded again, see below.**

### Update (2026-07-04) — archived-status exception + further motor-API drift

- [fact] The "archived, no further active development" position had a one-off, user-instructed exception: a 2026-07-04 team demo required bench-testing the Lifter/ClawArm/ClawElevator, so `main` received real code changes (commit `f95f43e`). This does not reopen the repo generally — treat it as archived again unless the user says otherwise. #archive
- [fact] `main`'s mechanism motor API drifted a second time, away from the 2026-01-23 SparkMax+Phoenix6 update: as of `f95f43e` (2026-07-03), the Lifter (previously SparkMax), ClawArm, and ClawElevator (both previously Phoenix6 TalonFX) are now uniformly **Phoenix5 TalonSRX**. Full current CAN table + motor stack: [[frc-2025-reefscape-hardware]] "2026-07-04 update" section. #motor-api #architecture
- [decision] This is a real API downgrade path (Phoenix6 → Phoenix5) driven by a physical hardware swap, not a code-only refactor — future lesson-porting to FRC-2026 should treat the 2026-01-23 SparkMax/Phoenix6 state as no longer current on `main`. See [[frc2025-talonsrx-bench-hardening]] for the full session. #lesson-port

### Update (2026-07-04, later same session) — vendordeps fully resolved to genuine 2026 registry

- [fact] The exception's blast radius grew further than the mechanism-conversion alone: getting the Driver Station to connect at all required replacing every 2025-dated vendordep with the real 2026 entries from `wpilibsuite/vendor-json-repo` — Phoenix5 5.36.0, Phoenix6 26.3.0, REVLib 2026.0.5, YAGSL 2026.4.1, Studica 2026.0.2, ReduxLib 2026.1.2, ThriftyLib 2026.1.2 (commit `a46e55b`). The prior state had only the `frcYear` field hand-edited on the 2025 JSONs — that lets Gradle build, but the underlying native binaries still crash-loop against the 2026 roboRIO image at runtime. `main` is now genuinely on the 2026 vendordep set, not just a spoofed year field. #vendordep #archive
- [decision] Treat this vendordep resolution as part of the same one-off demo exception, not a reopening of active development — the repo returns to archived status once the parade demo is done, per the position above. #archive

## Relations

- relates-to [[frc-2025-branch-guide]] (per-branch state detail)
- relates-to [[frc-2025-critical-rules]] (the archive guard + reference-main-only rule these decisions justify)
- relates-to [[frc-2025-source]] (repo orientation)
- relates-to [[frc-2026]] [[frc-team-3843]]
- relates-to [[frc2025-talonsrx-bench-hardening]] (the 2026-07-04 demo bring-up session that produced this update)
- relates-to [[frc2025-parade-demo-bench-verification]] (later same session: vendordep resolution + bench verification)
- relates-to [[frc-2025-reefscape-hardware]] (current CAN table + motor stack)

<!-- @claude 2026-07-04T00:22:46Z — appended 2026-07-04 update noting archived-status exception + second motor-API drift (Phoenix6->Phoenix5 TalonSRX); prior model: claude-opus-4-8 -->
<!-- @claude 2026-07-04T03:20:41Z — appended vendordep-resolution update (genuine 2026 registry, commit a46e55b) from session 58fac07f's parade-demo arc; prior model: claude-sonnet-5 -->

