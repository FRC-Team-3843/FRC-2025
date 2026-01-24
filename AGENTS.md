# FRC-2025 Repository - Codex Configuration

## How to Use This Configuration

This file is for Codex-specific behavior when working in the FRC-2025 repository.

**For technical standards:** Read `STANDARDS.md` in this directory.

---

## Technical Standards

**READ THIS FIRST:** `FRC-2025\STANDARDS.md`

That file contains technical rules for FRC-2025:
- Branch guide (main, 2025_Archive, 2025_Offseason_archive)
- Motor API status (updated on main branch)
- Architecture status (needs command factory refactor)
- Build commands
- Copying guidelines (verify API compatibility before porting)

---

## Codex-Specific Behavior

### When Working in FRC-2025

1. **Before starting work:**
   - Check `.agent-log\changelog.md` for recent activity
   - Read `STANDARDS.md` for all technical rules and branch status
   - Verify which branch you're on (main has current APIs)

2. **During work:**
   - Follow all standards in STANDARDS.md
   - Main branch: Motor APIs are current, but architecture needs command factory refactor
   - Do not copy code to newer projects without verifying API compatibility
   - Check STANDARDS.md for known issues and branch differences

3. **After completing work:**
   - Log changes to `.agent-log\changelog.md`
   - Use this format:
     ```
     ### [YYYY-MM-DD HH:MM] CODEX [ACTION_TYPE]
     - Description of changes
     - Files: <paths from repo root>
     - Notes: Important context for other agents
     - PENDING: (optional) What needs follow-up
     ```

### Codex Workflow Tips

- **Branch Awareness:** Always verify which branch you're on before making changes
- **API Status:** Main branch has updated motor APIs but architecture needs work
- **Refactoring:** Consider applying command factory patterns when making changes
- **Testing:** Run `./gradlew build` and `./gradlew test` from project directory

---

## Cross-Agent Protocol

### Activity Logging

**Location:** `.agent-log\changelog.md`

**Before work:** Check changelog for recent changes by other agents (Claude, Gemini).
**After work:** Log all significant changes with `[CODEX]` tag.

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
├── STANDARDS.md               ← READ THIS for technical rules and branch guide
├── AGENTS.md (this file)      ← Codex behavior for repo
├── .agent-log/
│   ├── changelog.md           ← All activity for this repo
│   └── handoffs.md            ← Task handoffs
│
├── 2025Robot/                 ← Main robot project (main branch)
│   ├── src/main/java/...      ← Source code
│   ├── src/test/java/...      ← Tests
│   ├── src/main/deploy/       ← PathPlanner, configs
│   └── vendordeps/            ← Vendor dependencies
│
└── 2025Robot-Offseason/       ← Offseason project (deprecated branch)
```

---

## Build Commands

Run from project directory (`2025Robot/` or `2025Robot-Offseason/`):
```bash
./gradlew build          # Compile and package
./gradlew test           # Run JUnit 5 tests
./gradlew deploy         # Deploy to RoboRIO (team 3843)
./gradlew simulateJava   # Run simulation GUI
./gradlew clean          # Clear build outputs
```

---

## Key Reminders

- **Check branch first** - main has current APIs, other branches are deprecated
- **Read STANDARDS.md** for API status and known issues
- **APIs change season to season** - verify compatibility before copying to newer projects
- **Log all significant changes** to help other agents coordinate
- **Check changelog regularly** to avoid duplicate work

---

For cross-agent coordination protocol, see: `C:\github\AGENTS.md`
