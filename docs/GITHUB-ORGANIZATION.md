# GitHub Organization Guide

This project is easier to maintain when the repository shows a clear learning flow instead of looking like a temporary local experiment.

## Recommended Repository Identity

Use a simple public name such as:

- `spring-security-learning`
- `spring-security-notes-and-code`
- `learn-spring-security`

Recommended GitHub repository description:

`Step-by-step Spring Security learning project with lessons, tests, and incremental security features.`

## Recommended Folder Rules

Keep the repository organized with these responsibilities:

- `src/main/java`: application code
- `src/test/java`: tests that prove each lesson
- `src/main/resources`: runtime configuration
- `docs`: lesson notes, roadmap, and setup guides

Avoid storing these in the repository:

- IDE-generated files
- compiled output such as `target/`
- local-only secrets
- random screenshots unless they explain something important

## Recommended Learning Workflow

Use a simple branch strategy:

- `main`: stable learning history
- `lesson/01-foundations`
- `lesson/02-role-based-authorization`
- `lesson/03-database-authentication`

This makes your GitHub history easier to read because each branch can represent one learning milestone.

## Recommended Commit Style

Keep commit messages short and specific:

- `docs: rewrite README for GitHub onboarding`
- `security: add role-based endpoint rules`
- `test: cover admin vs user access behavior`
- `lesson-03: add user entity and repository`

## What To Update Every Lesson

For each new lesson, update these items together:

1. application code
2. tests
3. lesson document in `docs/`
4. `README.md` current progress section

That rule keeps GitHub aligned with the actual code.

## Suggested Git Commands

Initialize the repository:

```bash
git init
git add .
git commit -m "chore: initialize Spring Security learning project"
```

Connect to GitHub after creating the remote repository:

```bash
git remote add origin <your-github-repo-url>
git branch -M main
git push -u origin main
```

## Optional Improvements

You can add these later if the repository grows:

- `LICENSE`
- `CHANGELOG.md`
- `.github/pull_request_template.md`
- `.github/ISSUE_TEMPLATE/`

For a solo learning project, these are optional. Clear lessons, tests, and a strong README matter more.
