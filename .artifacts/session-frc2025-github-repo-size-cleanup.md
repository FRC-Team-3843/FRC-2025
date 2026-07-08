---
id: frc2025-github-repo-size-cleanup
artifact_kind: memory
schema_version: 2
title: FRC-2025 GitHub Repo Size Investigation + PathPlanner Bloat Cleanup
created: 2026-07-07T00:00:00Z
updated: 2026-07-07T00:00:00Z
author: claude
model: claude-sonnet-5
model_basis: confirmed
status: active
memory_class: episodic
session: web-claude-web-20d9f262-c217-4f58-b237-08ace8a48da8
surface: web
platform: claude-web
source_url: https://claude.ai/chat/20d9f262-c217-4f58-b237-08ace8a48da8
derived_from: C:\Users\dover\AppData\Local\acc\transcripts\web\claude-web\20d9f262-c217-4f58-b237-08ace8a48da8.jsonl
entities: [frc-2025, frc-team-3843]
tags: [frc, github, git, pathplanner, gitignore, repo-hygiene]
aliases: [organizing frc github repositories, frc github cleanup, pathplanner bloat]
source_basis: transcript
confidence: 65
human_edited: false
sensitivity: normal
---

# FRC-2025 GitHub Repo Size Investigation + PathPlanner Bloat Cleanup

> Investigated a suspected FRC GitHub repo size-limit problem (turned out to be a Claude upload-limit message, not a GitHub limit), then cleaned up FRC-2025's `.gitignore` and stopped tracking PathPlanner-generated trajectory JSON.

## Context

Dated 2026-01-20 (imported later from a web-chat mirror). At the time, `FRC-Team-3843/FRC-2025` on GitHub held six sub-projects: `MainProject`, `MainProject-BasicMotion`, `MainProject-SimplifiedMotion`, `ServoTest`, `SparkMaxPIDTest`, `TallonPIDTest` — a different structure than the repo's current single-project layout (`2025Robot`, `2025Robot-SimplifiedMotion`), so this session's specifics are historical/pre-restructure. The user reported a "409% of limit" error and worried the repo (or org) had hit a GitHub storage cap, plus recalled prior-season trouble committing after adopting PathPlanner.

## Discussion

- The "409% of limit" turned out to be **Claude's own file-upload limit** when the user tried to share the whole repo directory with the assistant — not a GitHub error at all (user confirmed this mid-conversation).
- Diagnosis walked local repo size (`du -sh .git` / `du -sh .`), full Git history (`git rev-list --objects --all | git cat-file --batch-check…`), and the GitHub API `size` field for both `FRC-2024` (121 KB) and `FRC-2025` (438 KB) — all tiny and healthy. No history bloat, no oversized tracked files.
- Root cause of the *prior season's* commit trouble was PathPlanner: it writes both durable path definitions (`.path`/`.auto` files, `navgrid.json`, `settings.json` — small, and needed to sync between the user's multiple computers) and a re-derivable `generatedJSON/` folder of trajectory `.wpilib.json` files (~230-240 KB each, regenerated on every build/deploy) — the latter was being committed and inflating the repo needlessly.
- Also spotted: a duplicate nested `deploy/pathplanner/deploy/pathplanner/` folder structure inside each project (looked like an accidental copy-into-itself or a PathPlanner UI confusion) — flagged as messy but not urgent; user was given a `git rm -r` snippet to remove it but no confirmation it was ever run.
- Assistant has no GitHub connector / cannot act directly on the user's repos — every fix was a copy/paste shell snippet for the user (Git Bash) to run themselves; this is a repeated limitation in the conversation.
- Applied to FRC-2025: added a root `.gitignore` (build/bin/out, `.gradle/`, IDE dirs, OS files, WPILib sim files, `**/generatedJSON/`), then `git rm -r --cached` on the six `generatedJSON/` directories (54,012 lines removed from tracking) and committed (`af97f26`, "Add .gitignore and stop tracking PathPlanner generated files"). Same script was drafted for FRC-2024 too but deprioritized (season over, repo only 121 KB).
- **Push failed** after the commit: `remote: Invalid username or token. Password authentication is not supported for Git operations.` — GitHub Desktop was already installed and the user was told to push from there instead, since Git Bash HTTPS push needs a Personal Access Token (password auth is disabled). No confirmation in-transcript that the push via GitHub Desktop actually completed, though the user's final messages ("everything is within a size you can handle now") imply it did.
- Final assistant review of the (partially re-synced) tree found the `.gitignore` change had taken effect (generatedJSON gone) and reiterated the leftover nested `deploy/pathplanner/deploy/` duplicate as the one remaining cleanup item, plus optional additions (`ctre_sim/`, `logs/`) to `.gitignore`.

## Notes for Future Sessions

- This conversation predates the repo's current `2025Robot` / `2025Robot-SimplifiedMotion` layout and the WPILib-standard `.gitignore` now in place — treat the specific file paths here as historical, but the underlying PathPlanner git-hygiene lesson (gitignore `generatedJSON/`, keep `.path`/`.auto`/`navgrid.json`/`settings.json` tracked so paths sync across machines) is durable and reusable for any FRC repo using PathPlanner, including FRC-2026 (see `profile-pathplanner.md` there).
- Open/unconfirmed: whether the nested duplicate `deploy/pathplanner/deploy/pathplanner/` folders were ever actually removed, and whether the GitHub Desktop push succeeded (transcript ends without an explicit confirmation).
- Git Bash pushes to `github.com` over HTTPS require a Personal Access Token (password auth is disabled); GitHub Desktop sidesteps this by handling its own auth.

## Relations

- relates_to [[frc-2025]] (repo this session cleaned up)
- relates_to [[frc-team-3843]] (team context)
