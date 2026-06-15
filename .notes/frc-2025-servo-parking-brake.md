---
id: frc-2025-servo-parking-brake
title: FRC-2025 Servo Parking Brake Pattern
schema_version: 2
created: 2026-06-14T12:35:00Z
updated: 2026-06-14T12:35:00Z
valid_until: null
author: claude
session: null
tags: [frc, servo, lifter, mechanism, design-pattern, parking-brake, backdrive]
aliases: [servo parking brake, lifter parking brake, backdrive prevention, pwm brake servo, lifter servo brake]
status: active
supersedes: null
confidence: 50
source_basis: document
human_edited: false
sensitivity: normal
decisions: []
model: claude-sonnet-4-6
model_basis: confirmed
provenance:
  harvest: deterministic
  recall-extract: claude-sonnet-4-6
  find-missing: claude-sonnet-4-6
  precision-judge: claude-sonnet-4-6
artifact_kind: memory
memory_class: procedural
enforceability: preferred
---

# FRC-2025 Servo Parking Brake Pattern

> The 2025 robot uses standard PWM servos as mechanical parking brakes on the Lifter to prevent backdrive when motor power is removed — a design pattern worth carrying forward to any FRC mechanism that must hold position under load.

## Context

The FRC-2025 REEFSCAPE robot's Lifter mechanism uses two standard WPILib `edu.wpi.first.wpilibj.Servo` objects (not smart servos) as parking brakes. Source: `2025Robot/src/main/java/frc/robot/subsystems/Lifter.java`. The lifter holds the robot's weight during end-game hang and must not backdrive when power is cut (e.g., during disabled state or brownout).

## Observations

- [registry] PWM channel 0 (`motorBreak`): primary parking brake servo on Lifter, physically engages a mechanical stop to prevent backdrive when Lifter motors are disabled #servo #mechanism
- [registry] PWM channel 1 (`lineUp`): secondary servo on Lifter, used for cage-hang line-up alignment (positions robot for consistent cage grab approach) #servo #mechanism
- [lore] Standard PWM servos (not smart CAN servos) are sufficient for parking brakes when the load is predictable and the engagement position is static — simpler and cheaper than smart servo alternatives #design-pattern
- [lore] Parking brakes are initialized in the Lifter constructor alongside motor config, so they default to a known position at robot enable — sequence matters: set brake before enabling closed-loop position hold #initialization-order
- [lore] The README describes this as "servo-driven parking brakes to prevent backdriving when powered off" — the design intent is explicit; it is not a tune-and-forget constant, it requires deliberate servo position management in command logic #operational-note

## Open Questions

- Exact servo positions (engaged vs released angle) not confirmed from static analysis alone — the implementation likely sets these in command logic (`StowedCommand`, `HangCommand`); review those commands before replicating this pattern.
- Whether the parking brake was reliable in competition or caused latency issues (servo travel time) is not captured in the corpus.

## Relations

- relates-to [[frc-2025-reefscape-hardware]] (servo PWM assignments are part of hardware map)
- relates-to [[frc-2025-source-202606120638]] (implementation in Lifter.java)
- relates-to [[frc-2025-lessons-reefscape-202606120638]] (competition-proven mechanism patterns)
