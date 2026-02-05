# FRC-2025 Technical Standards

> **This repository follows FRC-2026 standards.**
> See `C:\GitHub\FRC-2026\STANDARDS.md` for the complete technical reference.
>
> **Documentation Guide:**
> - **This file (STANDARDS):** Repository-specific context and status
> - **README.md:** Project overview and quick start
> - **NOTES.md:** Setup procedures, tuning values, troubleshooting
> - **FRC-2026\STANDARDS.md:** Complete coding standards and API reference

---

## Repository-Specific Context

### Branch Guide

This repository has **three branches** in different states:

#### `main` Branch (MOTOR APIs CURRENT)
- **Status:** Motor APIs updated to 2026 standards; architecture partially refactored
- **Motor APIs:** `SparkMax` with `SparkMaxConfig`, `TalonFX` with Phoenix6 (CURRENT)
- **Architecture:** Command-based but not fully utilizing command factories
- **Known Issues:** Architecture needs refactoring to fully utilize command factory pattern; some deprecated patterns may remain
- **Use For:** YAGSL swerve configuration, PathPlanner patterns, game strategy, multi-mechanism coordination

#### `2025_Archive` Branch (DEPRECATED)
- **Status:** Original 2025 competition code (deprecated)
- **Motor APIs:** Old `CANSparkMax` API (removed in 2026)
- **Architecture:** Command-based but with old APIs
- **Use For:** Historical reference only

#### `2025_Offseason_archive` Branch (DEPRECATED)
- **Status:** Offseason experimentation code (deprecated)
- **Motor APIs:** Old `CANSparkMax` API (removed in 2026)
- **Use For:** Historical reference only

**If you see old `CANSparkMax` API, you're on a deprecated branch!**

---

## Current State (main branch)

### What's Updated

**Motor APIs (CURRENT):**
- ✅ `SparkMax` with `SparkMaxConfig` (REVLib 2025+)
- ✅ `TalonFX` with Phoenix6 control requests
- ✅ Configuration objects instead of direct setters

**Vendor Libraries (CURRENT):**
- ✅ REVLib, Phoenix6, YAGSL, PathplannerLib, PhotonLib

### What Needs Refactoring

**Architecture (PARTIALLY UPDATED):**
- ⚠️ Command-based framework used, but not fully utilizing command factories
- ⚠️ Some deprecated patterns may remain
- ⚠️ Dependency injection could be improved
- ⚠️ RobotContainer could be better structured

---

## What to Reference from This Repo

### ✅ Safe to Reference:

- **YAGSL Swerve Configuration:** Module configuration, integration patterns
- **PathPlanner Integration:** Autonomous path following, named commands
- **Game Strategy (2025 REEFSCAPE):** Multi-mechanism coordination (Arm + Hand + Wrist), scoring sequences
- **Vision Integration:** PhotonVision setup, AprilTag pose estimation

### ⚠️ Use with Caution:

- **Architecture Patterns:** Command-based is correct, but structure could be improved

### ❌ DO NOT Copy Without Verification:

- Specific motor configurations (verify APIs are current)
- Controller binding patterns (check for latest trigger API usage)
- Any patterns that don't follow command factory approach

---

## 2025 Season Context

**Team:** FRC 3843
**Robot:** 2025Robot (swerve drive)
**Game:** REEFSCAPE (2025 FRC game)

**Key Features:**
- Swerve drive (YAGSL-based)
- Multi-mechanism coordination (Arm, Hand, Wrist)
- Vision-based autonomous alignment
- PathPlanner autonomous paths
- Complex scoring sequences

---

## Build Commands

From `FRC-2025/2025Robot/`:
```bash
./gradlew build          # Build the project
./gradlew deploy         # Deploy to robot
./gradlew test           # Run unit tests
./gradlew simulateJava   # Run robot simulation
```

**Team Number:** 3843

---

**Last Updated:** 2026-01-26
**Status:** PARTIALLY UPDATED - APIs current, architecture needs refactoring
