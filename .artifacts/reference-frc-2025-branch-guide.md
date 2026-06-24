---
id: frc-2025-branch-guide
artifact_kind: reference
schema_version: 2
title: FRC-2025 Branch Guide
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
tags: [frc, branch, git, motor-api, build]
---

# FRC-2025 Branch Guide

> FRC-2025 has THREE branches in different states. `main` has current motor APIs (SparkMax/SparkMaxConfig, TalonFX/Phoenix6) but only partially-refactored architecture; `2025_Archive` and `2025_Offseason_archive` are deprecated (old CANSparkMax API). If you see old `CANSparkMax` API, you're on a deprecated branch.

## Context

The FRC-2025 repository follows FRC-2026 standards (the complete coding-standards and API reference lives in `FRC-2026\.standards.md`); this artifact captures the repository-specific branch and current-state context from `.standards.md`. The repo holds two sub-projects: `2025Robot` (competition code) and `2025Robot-SimplifiedMotion` (simplified drivetrain variant). Season context: Team 3843, REEFSCAPE (2025 FRC game), swerve drive (YAGSL), multi-mechanism coordination, vision-based autonomous alignment, PathPlanner autonomous paths, complex scoring sequences. As of 2026-01-26 the repo status is PARTIALLY UPDATED — APIs current, architecture needs refactoring.

## Observations

### The three branches

- [registry] **`main`** (MOTOR APIs CURRENT): motor APIs updated to 2026 standards, architecture partially refactored. Motor APIs = `SparkMax` with `SparkMaxConfig`, `TalonFX` with Phoenix6 (CURRENT). Architecture is command-based but not fully utilizing command factories. Known issues: architecture needs refactoring to fully use the command-factory pattern; some deprecated patterns may remain. Use for: YAGSL swerve configuration, PathPlanner patterns, game strategy, multi-mechanism coordination. #branch #main
- [registry] **`2025_Archive`** (DEPRECATED): original 2025 competition code. Motor APIs = old `CANSparkMax` (removed in 2026). Command-based but with old APIs. Use for: historical reference only. #branch #deprecated
- [registry] **`2025_Offseason_archive`** (DEPRECATED): offseason experimentation code. Motor APIs = old `CANSparkMax` (removed in 2026). Use for: historical reference only. #branch #deprecated
- [constraint] If you see old `CANSparkMax` API in code, you are on a deprecated branch — switch to `main`. #branch-detection

### Current state (main branch)

- [registry] What's updated — Motor APIs (CURRENT): `SparkMax` with `SparkMaxConfig` (REVLib 2025+); `TalonFX` with Phoenix6 control requests; configuration objects instead of direct setters. #current-state #motors
- [registry] What's updated — Vendor libraries (CURRENT): REVLib, Phoenix6, YAGSL, PathplannerLib, PhotonLib. #current-state #vendordeps
- [registry] What needs refactoring — Architecture (PARTIALLY UPDATED): command-based framework used but not fully utilizing command factories; some deprecated patterns may remain; dependency injection could be improved; RobotContainer could be better structured. #current-state #architecture

### Build commands

- [registry] From `FRC-2025/2025Robot/`: `./gradlew build` (build), `./gradlew deploy` (deploy to robot), `./gradlew test` (unit tests), `./gradlew simulateJava` (robot simulation). Team number 3843. Requires WPILib JDK 17 via `JAVA_HOME` (see [[wpilib-build-env]] and [[frc-2025-critical-rules]]). #build

## Relations

- relates-to [[frc-2025-what-to-reference]] (which patterns are safe to copy from `main`)
- relates-to [[frc-2025-critical-rules]] (reference `main` only; build rule)
- relates-to [[frc-2025-reefscape-hardware]] (the motor/vendor stack on `main`)
- relates-to [[frc-2025-source]] (repo orientation)
- relates-to [[frc-2026]] [[frc-team-3843]] [[wpilib-build-env]]
