---
id: frc-2025-local-workflow
artifact_kind: memory
memory_class: procedural
enforceability: preferred
schema_version: 2
title: FRC-2025 Local Workflow
created: 2026-06-23T19:00:00Z
updated: 2026-06-23T21:30:00Z
author: claude
model: claude-opus-4-8
model_basis: confirmed
status: active
load_profile: scope_entry
scope: FRC-2025
source_rel: FRC-2025\.protocol.md
tags: [frc, workflow, protocol, changelog, scratch]
---

# FRC-2025 Local Workflow

> Repository-local workflow for FRC-2025: read-order, during-work discipline, changelog logging format, working-file conventions, and incomplete-work tracking. This repo must remain usable on its own, without requiring `C:\GitHub` to exist.

## Context

The local workflow for the FRC-2025 repository, extracted from `.protocol.md` (non-@always sections). The repo holds two sub-projects: `2025Robot` (competition code) and `2025Robot-SimplifiedMotion` (simplified drivetrain variant). The repo is self-contained: if broader coordination context is available, `C:\GitHub\.protocol.md` is the preferred top-level entry point, but it is optional — this repo does not require the `C:\GitHub` root to function.

## Observations

### Read order (session start)

- [constraint] Read scope files at session start in this order: (1) `.changelog.md`, (2) `.project-context.md`, (3) `.standards.md`, (4) `.protocol.md`. #read-order (post-decomposition these are the `.artifacts\` scope_entry artifacts loaded via `load_profile: scope_entry`)

### During work

- [constraint] Follow FRC-2026 coding standards — the repo follows them (the standalone `.standards.md` was decomposed; see [[frc-2025-branch-guide]] and the FRC-2026 standards reference in `FRC-2026\.standards.md`). #during-work
- [constraint] Check `.changelog.md` before overlapping work to avoid conflicting edits. #during-work
- [constraint] Repo is archived (no active development), so project-state tracking is historical; current scope state lives in the `.artifacts\` scope_entry artifacts, not a `.project-context.md` (that monolith was decomposed). #during-work

### Changelog logging format

- [constraint] Log substantive work to `.changelog.md` (append-only) using this format: #logging

```text
### [YYYY-MM-DD HH:MM] AGENT_NAME [ACTION_TYPE]
- Description of changes
- Repo: FRC-2025
- Files modified: <paths from repo root>
- Notes: Important context for other agents
- Why: Why this change was made
- PENDING: (optional) Follow-up work
```

### Working files

- [constraint] Use `.scratch/` for temporary scripts, debug artifacts, and working files. #working-files
- [constraint] Use `.research/` for reference materials, external tools, and research. #working-files
- [constraint] Both `.scratch/` and `.research/` are gitignored — do not commit their contents. #working-files

### Incomplete work tracking

- [constraint] Incomplete-work tracking (historical): the original `.project-context.md` held `TODO` for actionable follow-up and `Pending Decisions` for unresolved choices; that monolith was decomposed and the repo is archived, so any residual scope state now lives in the `.artifacts\` scope_entry artifacts. #incomplete-work
- [registry] Sub-projects in this repo: `2025Robot` (competition code, `main` branch) and `2025Robot-SimplifiedMotion` (simplified drivetrain variant) — both archived, no active development. #sub-projects

## Relations

- relates-to [[frc-2025-critical-rules]] (the @always guardrails that gate any work here)
- relates-to [[frc-2025-branch-guide]] (which branch to reference; build commands)
- relates-to [[frc-2025-source-202606120638]] (repo orientation)
- relates-to [[frc-team-3843]] [[wpilib-build-env]]
