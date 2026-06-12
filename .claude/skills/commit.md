---
name: commit
description: Stage changed files and create a git commit. Use when the user says "commit", "save my changes", or at the end of a work session.
---

Stage and commit all changes with a concise commit message.

## Instructions

1. Check recent commits to match the repo's commit message style:
   ```
   git log --oneline -5
   ```

2. Stage all relevant changed files (prefer specific file names over `git add -A`):
   ```
   git add {file1} {file2} ...
   ```

3. Commit using a concise description of what was done:
   ```
   {concise description of what was done}
   ```
   Examples:
   - `Add booking confirmation flow to iOS and Android`
   - `Implement availability slot validation in backend`
   - `Fix Angular service filter dropdown not resetting on category change`

   Do NOT add a `Co-Authored-By` trailer or any other trailers.

4. Confirm the commit was created and show the commit hash and message.
