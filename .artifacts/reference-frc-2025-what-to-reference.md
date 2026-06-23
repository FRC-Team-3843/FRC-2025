---
id: frc-2025-what-to-reference
artifact_kind: reference
schema_version: 2
title: FRC-2025 What to Reference From This Repo
created: 2026-06-23T19:00:00Z
updated: 2026-06-23T19:00:00Z
author: claude
model: claude-opus-4-8
model_basis: confirmed
status: active
load_profile: scope_entry
scope: FRC-2025
source: FRC-2025\.standards.md
source_rel: FRC-2025\.standards.md
tags: [frc, reference, reuse, swerve, pathplanner, vision]
---

# FRC-2025 What to Reference From This Repo

> What is safe to copy from FRC-2025 into FRC-2026, what to use with caution, and what must NOT be copied without verification. Primary reference value: YAGSL swerve config, PathPlanner patterns, multi-mechanism sequencing, and vision/AprilTag pose estimation.

## Context

Guidance — from `.context.md` and `.standards.md` §What to Reference — on which FRC-2025 patterns carry forward to FRC-2026 development. The repo is archived; its lasting value is as a reference corpus. Reference the `main` branch only (see [[frc-2025-branch-guide]]).

## Observations

### Safe to reference

- [registry] YAGSL swerve configuration: module configuration and integration patterns (4 modules, CAN IDs 1–12). #safe #swerve
- [registry] PathPlanner integration: autonomous path-following and named commands; auto routine structure (e.g. `ScoreCoralTrough`, `StraightLeave`, `AlgaeGrab`). #safe #pathplanner #auto
- [registry] Multi-mechanism sequencing / game strategy (2025 REEFSCAPE): coordinated mechanism sequences (Arm + Elevator + Lifter + Claw; also described as Arm + Hand + Wrist) and scoring sequences. #safe #mechanisms
- [registry] Vision integration: PhotonVision setup and AprilTag pose estimation. #safe #vision

### Use with caution

- [constraint] Architecture patterns: command-based is correct, but the structure could be improved — command factories are not complete on `main`. Reference the shape, not the structure verbatim. #caution #architecture

### Do NOT copy without verification

- [constraint] Specific motor configurations — verify APIs are current before copying. #do-not-copy #motors
- [constraint] Controller binding patterns / details — check for the latest trigger API usage. #do-not-copy #controls
- [constraint] Any patterns that don't follow the command-factory approach. #do-not-copy #architecture

## Relations

- relates-to [[frc-2025-branch-guide]] (reference `main` only; current-state detail)
- relates-to [[frc-2025-lessons-reefscape-202606120638]] (the durable control lessons — alliance-control pitfall, POV presets, bumper-chord swap)
- relates-to [[frc-2025-reefscape-hardware]] (the specific motor configs to verify before copying)
- relates-to [[frc-2025-source-202606120638]] (repo orientation)
- relates-to [[frc-2026]] [[frc-team-3843]]
