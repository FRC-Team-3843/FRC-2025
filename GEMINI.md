# FRC-2025 Repository - Gemini Configuration

## How to Use This Configuration

This file is for Gemini-specific behavior when working in the FRC-2025 repository.

**For technical standards:** Read `STANDARDS.md` in this directory.

---

## Technical Standards

**READ THIS FIRST:** `FRC-2025\STANDARDS.md`

That file contains ALL technical rules for FRC-2025:
- 2026 Motor APIs (SparkMax, TalonFX with Phoenix6) - CURRENT
- YAGSL swerve drive integration
- PathPlanner autonomous patterns
- Multi-mechanism coordination (Arm, Hand, Wrist)
- Command-based architecture (partially refactored)
- Branch guide (main vs archived branches)
- Known limitations and refactoring needs

---

## Gemini-Specific Behavior

### When Working in FRC-2025

1. **Before starting work:**
   - Check `.agent-log\changelog.md` for recent activity
   - Read `STANDARDS.md` for all technical rules and current status
   - Note that architecture is partially refactored (APIs current, patterns need work)

2. **During work:**
   - Follow all standards in STANDARDS.md strictly
   - Use 2026 APIs exclusively (SparkMax, Phoenix6)
   - Reference YAGSL, PathPlanner, and vision patterns as needed
   - Be aware that command factories may not be fully implemented
   - Maintain command-based architecture with proper dependency injection

3. **After completing work:**
   - Log changes to `.agent-log\changelog.md`
   - Use this format:
     ```
     ### [YYYY-MM-DD HH:MM] GEMINI [ACTION_TYPE]
     - Description of changes
     - Files: <paths from repo root>
     - Notes: Important context for other agents
     - PENDING: (optional) What needs follow-up
     ```

### Gemini Workflow Tips

- **Code Generation:** Always follow the patterns in STANDARDS.md when generating new code.
- **Refactoring:** When refactoring, consider modernizing to command factory patterns.
- **Testing:** Suggest running `./gradlew test` after making changes to subsystems or commands.
- **Explanations:** When explaining patterns, reference specific sections of STANDARDS.md.
- **Architecture Notes:** This repo's architecture is partially refactored. Consider modernizing patterns when making changes.

---

## Cross-Agent Protocol

### Activity Logging

**Location:** `.agent-log\changelog.md`

**Before work:** Check changelog for recent changes by other agents (Claude, Codex).
**After work:** Log all significant changes with `[GEMINI]` tag.

### Handoff Tracking

**Location:** `.agent-log\handoffs.md`

If you leave work incomplete or encounter blockers:
1. Update handoffs.md with task status
2. Note what was completed and what's pending
3. Document any blockers or issues
4. Suggest which agent should continue (or mark as `ANY`)

---

## Repository Structure

```
FRC-2025/
├── STANDARDS.md               ← READ THIS for all technical rules
├── CLAUDE.md                  ← Claude behavior for repo
├── GEMINI.md (this file)      ← Gemini behavior for repo
├── AGENTS.md                  ← Codex behavior for repo
├── .agent-log/
│   ├── changelog.md           ← All activity for this repo
│   └── handoffs.md            ← Task handoffs
│
├── 2025Robot/                 ← Main project folder
│   ├── CLAUDE.md              ← Project-level redirect
│   ├── GEMINI.md              ← Project-level redirect
│   ├── AGENTS.md              ← Project-level redirect
│   ├── src/main/java/...      ← Source code
│   └── src/test/java/...      ← Tests
│
└── 2025Robot-SimplifiedMotion/ ← Experimental project
    ├── CLAUDE.md              ← Project-level redirect
    ├── GEMINI.md              ← Project-level redirect
    ├── AGENTS.md              ← Project-level redirect
    ├── src/main/java/...      ← Source code
    └── src/test/java/...      ← Tests
```

---

## Key Reminders

- **Always read STANDARDS.md** before making code changes
- **Use 2026 APIs exclusively** in this repo (main branch)
- **Architecture is partially refactored** - consider modernizing patterns
- **YAGSL and PathPlanner** - Reference these for swerve and autonomous patterns
- **Log all significant changes** to help other agents coordinate
- **Check changelog regularly** to avoid duplicate work

---

For cross-agent coordination protocol, see: `C:\github\GEMINI.md`
