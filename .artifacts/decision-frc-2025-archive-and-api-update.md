---
id: frc-2025-archive-and-api-update
artifact_kind: decision
schema_version: 2
title: FRC-2025 Archive Status + main-branch API Update
created: 2026-06-23T19:00:00Z
updated: 2026-06-23T19:00:00Z
author: claude
model: claude-opus-4-8
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

- [decision] The `main` branch's motor APIs were updated to 2026 standards — `SparkMax` (with `SparkMaxConfig`) + Phoenix6 (`TalonFX`). The architecture was NOT fully refactored: command factories are incomplete and some deprecated patterns may remain. The old `CANSparkMax` API exists only on the deprecated branches (`2025_Archive`, `2025_Offseason_archive`). (source: `.standards.md` 2026-01-23 / Current State; `.context.md`) #motor-api #architecture

## Relations

- relates-to [[frc-2025-branch-guide]] (per-branch state detail)
- relates-to [[frc-2025-critical-rules]] (the archive guard + reference-main-only rule these decisions justify)
- relates-to [[frc-2025-source-202606120638]] (repo orientation)
- relates-to [[frc-2026]] [[frc-team-3843]]
