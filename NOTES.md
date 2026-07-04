# Working Notes - FRC-2025

> **Documentation Guide:**
> - **This file (NOTES):** Setup, tuning, troubleshooting, TODOs
> - **README.md:** Project overview and quick start
> - **STANDARDS.md:** Coding standards (references FRC-2026\STANDARDS.md)

## CAN Bus Assignments

### Drive System
| Device | CAN ID | Description |
|--------|--------|-------------|
| Swerve Modules | 1-12 | Drive motors, steer motors, CANCoders |

### Mechanisms (all TalonSRX + quad encoders as of 2026-07 demo rebuild)
| Device | CAN ID | Description |
|--------|--------|-------------|
| Right Lifter Motor | 31 | Lifter right side |
| Left Lifter Motor | 32 | Lifter left side |
| Claw Intake Motor | 33 | Intake/outtake control |
| Claw Arm Motor | 34 | Arm positioning |
| Lifter Intake Motor | 35 | Secondary intake |
| Claw Elevator Motor | 36 | Vertical extension (re-ID'd from 50) |

## Key Positions (2026-07 TalonSRX rebuild — encoders zero at boot; pre-position at stow before power-on)

Measured ranges: Lifter 0-6733 ticks = 180 deg; Claw Arm 0-795000 ticks = 128 deg; Claw Elevator 0-3214 ticks = 23.25 in.

### Lifter Positions (degrees; remapped from old NEO rotations via (rot-10)*180/188 — TO-VERIFY on bench)
- Stowed / Hang: 0
- Coral Score: 87.1
- Clearance / Algae Intake: 114.9
- Algae Score: 113.0
- Coral Intake: 175.2
- Climbing Approach: 180.0

### Claw Arm Positions (degrees; carried 1:1 from old TalonFX rotations — TO-VERIFY on bench)
- Stowed: 0.77
- L1 Coral Scoring: 23.26
- L2 Coral Scoring: 44
- L1 Algae Intake: 26.55
- L2 Algae Intake: 38
- Algae Score: 53

### Claw Elevator Positions (inches from bottom; up = positive — TO-VERIFY on bench)
- Stowed: 0
- L2 Coral Scoring / L2 Algae Intake / Algae Score: 14.0
- Top: 23.0

### Old-bot values (pre-rebuild, for reference only)
- Lifter (NEO rotations): stowed/hang 10, coral score 101, clearance/algae intake 130, algae score 128, coral intake 193, climbing approach 198
- Claw Elevator (TalonFX rotations, up = negative): stowed -0.75, L2 -28, top -46

## Bench Bring-Up Checklist (2026-07 demo — BENCH_TEST_MODE in Constants.java)

1. **Tuner X audit (robot on blocks, before deploying):** confirm TalonSRX at 31/32/34/36, set Brake on all four, check firmware.
2. **Blip test (Tuner, ~3% output, fractions of a second, mechanism mid-range):** per motor, record (a) does positive output move toward deploy/up, (b) does the sensor count UP. Fix `*_MOTOR_INVERT` / `*_SENSOR_PHASE` in Constants until positive = deploy AND sensor counts up, on every motor. Lifter: verify BOTH sides agree before any closed-loop move. `setInverted` does NOT flip the quad sensor on Phoenix 5.
3. **Measure kF (per mechanism):** steady duty in Tuner → read velocity (ticks/100ms) → `kF = duty * 1023 / velocity`. Enter in Constants.
4. **Arm homing (arm is backdriven — can't be moved by hand):** POV up/down = arm jog, soft limits bypassed while held. Drive it to physical stow, then **Start = re-zero arm encoder in place** (no power cycle needed). Only press Start with the arm at stow.
5. **First closed-loop moves (rider on disable):** operator A/B = lifter 10 deg/0; X/Y = elevator 2 in/0; LB/RB = arm 5 deg/stow. Sticks = open-loop jog (LY lifter, RY elevator). Back = panic stop. Watch `Lifter/DeltaDeg` on dashboard — watchdog latches + neutrals at 3 deg split (needs code restart).
6. **Extend range gradually,** verify each named setpoint, then raise MOTOR_MAX_VELOCITY/ACCELERATION toward demo speed.
7. **For the demo:** set `Constants.BENCH_TEST_MODE = false` to restore competition bindings.
8. **Every power-on:** mechanisms at stow/bottom first — encoders zero at boot (arm can instead be homed via step 4).

## Robot Dimensions
- Pod Length: 25.25 in (0.6413 m)
- Pod Width: 19.25 in (0.4889 m)

## Encoder Positions (Old Bot Reference)
- Back Left: 169.37
- Back Right: 89.47
- Front Left: 331.215
- Front Right: 222.845

## Target Positions
- 61.5 Target Position = 1 rotation
- 3.5 rotations = [calculated value]

## IMPORTANT REMINDERS

### TODOs
- [ ] Tune the bot
- [ ] Fix the bumpiness with the swerve drive
- [ ] Resolve error code (see below)

## Error Log / Troubleshooting

### Error - IllegalStateException
**Time:** 5:34:42.857 PM
**Symptom:** Robot program quit unexpectedly with IllegalStateException
**Stack Trace:**
```
java.lang.IllegalStateException: End size 0 is less than fixed size 1
at java.util.stream.Nodes$FixedNodeBuilder.end(Unknown Source)
at edu.wpi.first.wpilibj.Alert$SendableAlerts.getStrings(Alert.java:202)
at edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl.update
```
**Root Cause:** SmartDashboard/Alert system attempting to send empty array when fixed size expected
**Fix:** [Pending investigation]

## PathPlanner Configuration

**HARRISON - PLEASE FILL OUT:**
- Robot Mass (kg): [TBD]
- Robot MOI (kg*m^2): [TBD]
- Bumper Width (m): [TBD]
- Bumper Length (m): [TBD]
- Bumper Offset X (m): [TBD]
- Bumper Offset Y (m): [TBD]
- Wheel Radius (m): [TBD]
- Drive Gearing: [TBD]
- True Max Drive Speed (m/s): [TBD]
- Wheel COF: [TBD]
- Drive Motor: Neo
- Drive Current Limit (A): [TBD]

### Module Positions
- Front Left X (m): [TBD]
- Front Left Y (m): [TBD]
- Front Right X (m): [TBD]
- Front Right Y (m): [TBD]
- Back Left X (m): [TBD]
- Back Left Y (m): [TBD]
- Back Right X (m): [TBD]
- Back Right Y (m): [TBD]

## Command Ideas (16 Buttons Total)

| Button | Command | Description |
|--------|---------|-------------|
| A | CoralLifterIntakeCommand | Intake coral with lifter |
| B | CoralLifterScoreCommand | Score coral with lifter |
| X | AlgaeGroundIntakeCommand | Ground intake algae |
| Y | AlgaeL1IntakeCommand | L1 algae intake |
| Left Bumper + B | CoralClawL1ScoreCommand | L1 coral scoring |
| Left Bumper + X | CoralClawL2ScoreCommand | L2 coral scoring |
| Left Bumper + A | AlgaeL2IntakeCommand | L2 algae intake |
| Left Bumper + Y | AlgaeScoreNetCommand | Score algae in net |
| Right Bumper + X | CoralClawIntakeCommand | Intake coral with claw |
| Right Bumper + Y | StowedCommand | Stow all mechanisms |
| Back | HangCommand | Execute hang sequence |
| [TBD] | ApproachHangCommand | Approach hang position |
| [TBD] | AlgaeScoreProcessorCommand | Score algae in processor |
| [TBD] | DefenseCommand | Defense mode |

## Auto Ideas
1. ScoreCoralTrough
2. StraightLeave
3. AlgaeGrab
4. ScoreCoralTrough (repeat)

## Lessons Learned
- Swerve tuning requires patience and systematic approach
- PathPlanner configuration is critical for auto success
- Multi-mechanism coordination needs careful sequencing
- Error logging helps track intermittent issues
