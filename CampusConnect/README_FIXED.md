# Campus Connect — Corrected Java + MySQL project

## What was fixed
- Registration SQL now uses the real `users` columns (`name`, `email`, `password_hash`, `role`, `status`).
- Registration inserts the matching `student_profiles` or `alumni_profiles` row using the supplied schema fields. Both writes are in one transaction.
- Passwords are hashed using the existing `PasswordUtil` PBKDF2-SHA256 format, consistent with login verification.
- Login now authenticates through `AuthService`, checks the selected role, rejects pending/blocked accounts, and opens a role-aware dashboard.
- Removed invalid `users.created_at` references. The supplied `users` table does not have that column.
- User account status now uses only `ACTIVE`, `PENDING`, and `BLOCKED`; alumni verification separately uses `PENDING`, `VERIFIED`, and `REJECTED`. Admin verification updates both records transactionally.
- `Main.java` launches the login screen.
- Removed hard-coded database credentials from source; connection settings are read from environment variables.
- Replaced placeholder demo password hashes in this SQL copy with valid local demo hashes.

## Before running
1. Install MySQL 8 and create/import the database using `mysql.sql`. **This dump contains DROP TABLE statements and will replace tables with the same names. Back up any existing data before importing.**
2. Add MySQL Connector/J 9.7.0 to `CampusConnect/lib/mysql-connector-j-9.7.0.jar` (the connector JAR is not bundled in this ZIP).
3. Set environment variables for your own MySQL connection: `CAMPUS_CONNECT_DB_USER`, `CAMPUS_CONNECT_DB_PASSWORD`. Optionally set `CAMPUS_CONNECT_DB_URL`. Defaults are user `root`, empty password, and local `campus_connect` database.
4. Import `CampusConnect` as an Eclipse Java project (Java 21). If you use another IDE, add `lib/mysql-connector-j-9.7.0.jar` to the project dependencies.
5. Run `main.Main`.

## Demo accounts
The included SQL seeds six sample users with the password `CampusDemo123!` for local demonstration only:
- `ananya@example.com` — Student
- `arjun@example.com` — Alumni
- `meera@example.com` — Alumni
- `admin@example.com` — Admin
- `rahul@example.com` — Student
- `neha@example.com` — Alumni

Select the matching role on the login screen. These are sample credentials only; change them before any real deployment.

## Important notes
- Alumni who register through the UI are created as `PENDING` and cannot log in until an admin verifies them. Student accounts are `ACTIVE` immediately.
- The role dashboard supports core database-backed views and alumni verification. Several older individual feature-frame classes in `src/ui` remain empty placeholders from the original project and have not been implemented as full screens. Their underlying DAO/service classes were reviewed for SQL column references, but not every feature workflow has been manually runtime-tested against a live MySQL server.
- The supplied schema's `users` table intentionally has no `created_at` column. Do not add code references to it unless you also change the schema.
