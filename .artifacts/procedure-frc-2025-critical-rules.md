---
id: frc-2025-critical-rules
artifact_kind: memory
memory_class: procedural
enforceability: mandatory
schema_version: 2
title: FRC-2025 Critical Rules
created: 2026-06-23T19:00:00Z
updated: 2026-06-23T19:00:00Z
author: claude
model: claude-opus-4-8
model_basis: confirmed
status: active
load_profile: scope_entry
scope: FRC-2025
source_rel: FRC-2025\.protocol.md
tags: [frc, critical-rules, archive, build, branch]
---

# FRC-2025 Critical Rules

> The non-negotiable guardrails for FRC-2025: archived/dormant repo (no new code without explicit instruction), JDK17 build-from-`2025Robot/` rule, reference `main` branch only, and the verification gates when porting lessons forward to FRC-2026.

## Context

The @always critical rules for the FRC-2025 repository, extracted from `.protocol.md`. This repo is FRC Team 3843's 2025 REEFSCAPE season robot, archived after the season completed. These rules gate every action in the scope and must be honored before any edit, build, or lesson-port.

## Observations

- [constraint] This repo is **archived / dormant** (2025 season complete). Do NOT write new code or modify existing subsystems without explicit user instruction. #archive-guard
- [constraint] To build, set `JAVA_HOME="C:/Users/Public/wpilib/2026/jdk"` (WPILib JDK 17) — system Java is Java 8 and will fail. Build from the `FRC-2025/2025Robot/` sub-directory. #build (see [[wpilib-build-env]])
- [constraint] Reference code from the `main` branch only; `2025_Archive` and `2025_Offseason_archive` branches are deprecated (old `CANSparkMax` API, not valid for 2026+). #branch
- [constraint] When pulling lessons forward to FRC-2026: verify motor API currency — architecture patterns (command factories) are incomplete on `main`. #lesson-port
- [constraint] Read the lessons note [[frc-2025-lessons-reefscape-202606120638]] before making any FRC-2026 control/swerve decision. #lesson-port

## Relations

- relates-to [[frc-2025-lessons-reefscape-202606120638]] (lessons to consult before FRC-2026 control/swerve work — do not duplicate; read there)
- relates-to [[frc-2025-source-202606120638]] (repo orientation; main-vs-deprecated branch summary)
- relates-to [[frc-2025-branch-guide]] (per-branch detail)
- relates-to [[frc-2025-local-workflow]] (the local workflow these rules gate)
- relates-to [[frc-2026]] [[frc-team-3843]] [[wpilib-build-env]]
