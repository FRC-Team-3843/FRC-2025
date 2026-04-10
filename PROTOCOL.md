# FRC-2025 - Shared Agent Protocol

**Root coordination protocol:** `C:\GitHub\PROTOCOL.md`
**Technical standards:** `STANDARDS.md` (in this directory)
**Project context:** `.agent-context.md`

## How to Use This Configuration

This file contains the shared cross-agent protocol for the FRC-2025 repository. For technical rules (APIs, architecture, build commands), read `STANDARDS.md`. For agent-specific logging tags, read your agent config file (`CLAUDE.md`, `GEMINI.md`, or `AGENTS.md`).

## Before Starting Work

1. **Read `.agent-log\changelog.md`** by direct path -- review recent activity from all agents
2. **Read `.agent-log\handoffs.md`** by direct path -- check for pending tasks or blockers
3. **Read `STANDARDS.md`** -- all technical rules for this repo
4. **Read `.agent-context.md`** -- current project status and phase

## During Work

- Follow all standards in `STANDARDS.md` strictly
- Check for duplicate work before implementing new features
- Maintain consistency with existing code patterns

## After Completing Work

1. **Log to `.agent-log\changelog.md`** with this format:
   ```
   ### [YYYY-MM-DD HH:MM] <TAG> [ACTION_TYPE]
   - Description of changes
   - Files: <paths from repo root>
   - Notes: Important context for other agents
   - PENDING: (optional) What needs follow-up
   ```
2. **Action types:** `[IMPLEMENT]`, `[REFACTOR]`, `[FIX]`, `[TEST]`, `[CONFIG]`, `[DOCS]`, `[REVIEW]`
3. **If handing off incomplete work:** Update `.agent-log\handoffs.md` with status, blockers, and next steps

## Cross-Agent Protocol

- **Agent log files are append-only** -- never overwrite existing content. Always Read first, then append.
- **No Duplication** -- check changelog before implementing new features
- **Clear Handoffs** -- use `handoffs.md` when leaving incomplete work
- **Log Everything** -- better to over-communicate than under-communicate
- **Respect Standards** -- follow `STANDARDS.md` strictly
- **Three agents:** Claude (`CLAUDE`), Gemini (`GEMINI`), Codex (`CODEX`)

## Repository Structure

```
FRC-2025/
├── PROTOCOL.md (this file)        <- Shared agent protocol
├── STANDARDS.md                   <- Technical rules (READ THIS)
├── .agent-context.md              <- Project status and phase
├── CLAUDE.md                      <- Claude-specific config
├── GEMINI.md                      <- Gemini-specific config
├── AGENTS.md                      <- Codex-specific config
├── .agent-log/
│   ├── changelog.md               <- All activity for this repo
│   └── handoffs.md                <- Task handoffs
│
├── 2025Robot/                     <- Main competition project
│   ├── CLAUDE.md                  <- IDE redirect
│   ├── GEMINI.md                  <- IDE redirect
│   ├── AGENTS.md                  <- IDE redirect
│   ├── src/main/java/...         <- Source code
│   └── src/test/java/...         <- Tests
│
└── 2025Robot-SimplifiedMotion/    <- Simplified drivetrain variant
    ├── CLAUDE.md                  <- IDE redirect
    ├── GEMINI.md                  <- IDE redirect
    ├── AGENTS.md                  <- IDE redirect
    ├── src/main/java/...         <- Source code
    └── src/test/java/...         <- Tests
```

## Key Reminders

- **Always read `STANDARDS.md`** before making code changes
- **This is an archived repo** -- see `.agent-context.md` for current status
- **Log all significant changes** to help other agents coordinate
- **Check changelog regularly** to avoid duplicate work
- **Never edit protocol files directly** without human approval -- use `.agent-proposals/`
