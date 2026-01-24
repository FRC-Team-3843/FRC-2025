# FRC-2025 Technical Standards

**Standards Version:** Motor APIs updated to 2026 standards; architecture partially refactored

**⚠️ IMPORTANT:** APIs change season to season. Always verify compatibility before copying to newer projects.

---

## Branch Guide

This repository has **three branches** in different states:

### `main` Branch (MOTOR APIs CURRENT)
- **Status:** Motor APIs updated to 2026 standards; architecture partially refactored
- **Motor APIs:** `SparkMax` with `SparkMaxConfig`, `TalonFX` with Phoenix6 (CURRENT)
- **Architecture:** Command-based but not fully utilizing command factories
- **Known Issues:** Architecture needs refactoring to fully utilize command factory pattern; some deprecated patterns may remain
- **Use For:** YAGSL swerve configuration, PathPlanner patterns, game strategy, multi-mechanism coordination

### `2025_Archive` Branch (DEPRECATED)
- **Status:** Original 2025 competition code (deprecated)
- **Motor APIs:** Old `CANSparkMax` API (removed in 2026)
- **Architecture:** Command-based but with old APIs
- **Use For:** Historical reference only

### `2025_Offseason_archive` Branch (DEPRECATED)
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
- ✅ REVLib - Modern API
- ✅ Phoenix6 - Current version
- ✅ YAGSL - Swerve drive library
- ✅ PathplannerLib - Autonomous path following
- ✅ PhotonLib - Vision processing

### What Needs Refactoring

**Architecture (PARTIALLY UPDATED):**
- ⚠️ Command-based framework used, but not fully utilizing command factories
- ⚠️ Some deprecated patterns may remain
- ⚠️ Dependency injection could be improved
- ⚠️ RobotContainer could be better structured

**Known Gaps:**
- Command factories not fully implemented in subsystems
- Some subsystems may have commands defined externally instead of as factory methods
- Controller bindings may not fully use trigger API patterns

---

## What to Reference from This Repo

### ✅ Safe to Reference:

**YAGSL Swerve Configuration:**
- Swerve module configuration patterns
- YAGSL integration with command-based
- Swerve kinematics and control

**PathPlanner Integration:**
- Autonomous path following
- Named commands for path events
- Auto builder configuration

**Game Strategy (2025 REEFSCAPE):**
- Multi-mechanism coordination (Arm + Hand + Wrist)
- Scoring sequences (L1, L2, L3, L4 positions)
- Game piece handling patterns

**Vision Integration:**
- PhotonVision setup
- AprilTag pose estimation
- Vision-based autonomous alignment

**Multi-Subsystem Coordination:**
- Coordinating arm, wrist, and hand mechanisms
- Complex command compositions
- State management for multi-step actions

### ⚠️ Use with Caution:

**Architecture Patterns:**
- Command-based framework is correct, but structure could be improved
- Check for command factory usage before copying
- Verify dependency injection patterns

### ❌ DO NOT Copy Without Verification:

- Specific motor configurations (verify APIs are current)
- Controller binding patterns (check for latest trigger API usage)
- Any patterns that don't follow command factory approach

---

## Subsystems (main branch)

### SwerveSubsystem
- **Implementation:** YAGSL library
- **Motors:** 4x drive (TalonFX), 4x steer (TalonFX)
- **Sensors:** 4x CANcoder for absolute position
- **Features:** Field-centric drive, path following, pose estimation

### Arm
- **Motor:** TalonFX (CAN ID 33)
- **Control:** Position control with Motion Magic
- **Positions:** L1, L2, L3, L4 scoring positions

### Hand
- **Motor:** SparkMax (CAN ID 34)
- **Function:** Intake/outtake mechanism
- **Control:** Velocity control for game piece handling

### Wrist
- **Motor:** TalonFX or SparkMax (check Constants)
- **Control:** Position control for game piece orientation

### Vision
- **Implementation:** PhotonVision
- **Features:** AprilTag detection, pose estimation
- **Integration:** Fuses with odometry for accurate positioning

---

## CAN Bus Layout (2025)

```
0       - Power Distribution Panel
1-4     - Drive Motors (TalonFX) - FL, FR, RL, RR
5-8     - Steer Motors (TalonFX) - FL, FR, RL, RR
9-12    - Angle Sensors (CANcoder) - FL, FR, RL, RR
33      - Arm Motor (TalonFX)
34      - Hand Motor (SparkMax)
35+     - Additional mechanism motors (see Constants.java)
```

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

**Why This Matters:**
- Shows how to coordinate multiple mechanisms
- Demonstrates YAGSL swerve integration
- PathPlanner patterns are reusable
- Vision integration patterns are valuable

---

## Copying to Newer Projects

**CRITICAL:** APIs change season to season. Before copying ANY code to a newer project:

1. **Verify motor APIs** - Although updated, APIs may change again in future seasons
2. **Check vendor library versions** - YAGSL, PathPlanner, PhotonLib may update
3. **Review architecture** - The partial refactoring means some patterns may not be ideal
4. **Verify command structure** - Check if command factories are used before copying
5. **Test thoroughly** - Don't assume code will work unchanged

**What's Safe to Reference:**
- YAGSL configuration patterns (verify library version)
- PathPlanner integration approach (verify library version)
- Multi-mechanism coordination logic (adapt to newer patterns)
- Game-specific strategy (adapt to new APIs)

**What Requires Careful Review:**
- Architecture patterns (not fully refactored)
- Command definitions (may not be using factory pattern)
- Controller bindings (may not be using latest trigger API)

---

## Repository Status

**Last Updated:** 2026-01-23
**Status:** PARTIALLY UPDATED - APIs current, architecture needs refactoring
**Use For:** YAGSL examples, PathPlanner integration, multi-mechanism coordination, game strategy
**Known Limitations:** Architecture not fully modernized; some patterns may be deprecated
**Recommended Action:** Refactor architecture to fully utilize command factories and modern dependency injection

---

**For the latest standards:** See the newest season's `STANDARDS.md` file (currently `FRC-2026\STANDARDS.md`)
