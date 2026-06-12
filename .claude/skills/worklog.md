---
name: worklog
description: Generate a worklog summary from git history. Use when the user asks for a worklog, daily summary, or what was done over a period.
---

Generate a worklog summary from the git log for the BookEase project.

## Instructions

1. Get the git log for the requested period (default: last 7 days):
   ```
   git -C /Users/lakshmipriya/learning/BookEase/BookEase log --reverse --format="%ai %s" --since="7 days ago"
   ```
   Adjust `--since` based on what the user asks for (e.g. `--since="2026-06-01"` for a specific start date, or omit for all commits).

2. Group commits by day.

3. For each active day, write one paragraph summarising what was built or changed across all platforms (iOS, Android, Angular, backend) — no bullet points, no dates, no "implemented/added" phrasing. Write in plain present-tense descriptive style (e.g. "The booking flow gains confirmation screens on both mobile platforms while the backend validates slot availability against concurrent reservations...").

4. Skip days with no commits entirely.

5. Output the paragraphs one per day, separated by a blank line. No headings, no dates, no labels.
