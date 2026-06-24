---
id: frc-2025-reefscape-game
title: FRC 2025 REEFSCAPE Game
schema_version: 2
created: 2026-06-14T12:35:00Z
updated: 2026-06-14T12:35:00Z
valid_until: null
author: claude
session: null
tags: [frc, reefscape, game, first-robotics, 2025]
aliases: [reefscape, frc 2025 game, reefscape game, coral algae game, 2025 first game]
status: active
supersedes: null
confidence: 55
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
lifecycle: active
artifact_kind: memory
memory_class: semantic
semantic_kind: entity_profile
scope: FRC-2025
---

# FRC 2025 REEFSCAPE Game

> The 2025 FIRST Robotics Competition game; understanding the game is required context for every design decision in the FRC-2025 robot.

## Context

REEFSCAPE is the 2025 FRC game. Two types of game pieces are manipulated: **Coral** (hollow ring) and **Algae** (rubber ball). The field features a central **Reef** structure with four levels of scoring branches (L1–L4), a **Barge** (elevated net for algae scoring), a **Processor** (lower algae scoring zone), and **Cages** for end-game climbing/hang. Two alliances of three robots each compete on a field with both autonomous and teleoperated periods.

Team 3843's 2025 robot was designed for multi-level scoring of both game pieces: Coral scored into the reef branches (trough/L1 via lifter, L1–L2 via claw+elevator+arm); Algae scored into the Barge (requires elevation via elevator); end-game hang onto Cage via lifter hook.

## Observations

- [registry] Two game piece types: Coral (hollow ring, reef branch scoring) and Algae (rubber ball, barge/processor scoring) #game-pieces
- [registry] Scoring zones: Reef (4 branch levels L1–L4), Barge (elevated net, algae only), Processor (floor-level, algae only), Cage (hang zone, end-game) #field
- [registry] End-game: robots hang on Cage by hooking and lifting themselves — Team 3843 implemented via Lifter mechanism with climbing-approach and hang command sequence #endgame
- [registry] Autonomous period (first 15s): alliance-specific field orientation matters; alliance-relative control and gyro-zero binding are safety-critical (see alliance-control pitfall in frc-2025-lessons-reefscape) #autonomous
- [lore] The dual-game-piece requirement drove the multi-context mechanism architecture: separate intake paths (ClawIntake for algae, LifterIntake for coral), operator bumper mode-switch, and one-press sequence commands per game-piece + level combination #design-rationale

## Open Questions

- Exact L3/L4 coral scoring: the 2025 robot documents L1 (lifter trough) and L2 (claw+elevator) scoring confirmed in code; L3/L4 capable hardware unclear from corpus alone.

## Relations

- relates-to [[frc-2025-source]] (season robot built for this game)
- relates-to [[frc-2025-lessons-reefscape]] (control lessons derive from game constraints)
- relates-to [[frc-team-3843]] (Team 3843 competed in this season)
