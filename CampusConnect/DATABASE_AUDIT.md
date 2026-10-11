# Database/Java field audit

Audited the supplied Java source and `mysql.sql`.

## Confirmed mismatches fixed
1. Registration previously inserted `full_name`, `college_id`, `department`, `graduation_year`, and `verification_status` into `users`. Those fields do not exist in `users`. Registration now inserts only account fields into `users` and stores profile fields in `student_profiles` or `alumni_profiles`.
2. The original registration screen used SHA-256 while login verification expected PBKDF2-SHA256. Both now use `PasswordUtil`.
3. `UserDAO.getAllUsers()` referenced `users.created_at`, absent from schema. It now selects existing fields and orders by `user_id`.
4. `AlumniDAO.getPendingAlumni()` referenced `users.created_at`, absent from schema. It now orders by `alumni_profiles.user_id`.
5. User status enum is `ACTIVE`, `PENDING`, `BLOCKED`; `REJECTED` is only an alumni verification status. Admin rejection now sets profile verification to `REJECTED` and account status to `BLOCKED`.
6. Login UI was a placeholder and is now connected to `AuthService` and `UserDAO.login()`.
7. `main.Main` was empty and now starts the login UI.
8. `.classpath` pointed to a Windows-specific absolute connector JAR path. It now uses `lib/mysql-connector-j-9.7.0.jar`.
9. Embedded database credentials were removed from source.

## Schema alignment reviewed
- `users`: `user_id`, `name`, `email`, `password_hash`, `role`, `status`, `verification_notes`
- `student_profiles`: `user_id`, `department`, `semester`, `skills`, `career_goal`, `college_id_number`
- `alumni_profiles`: `user_id`, `graduation_year`, `company`, `designation`, `industry`, `expertise`, `verification_status`, `college_id_number`, `evidence_reference`, `registration_number`, `department`, `degree`
- DAO queries for mentorship requests/sessions, opportunities/saved opportunities, notifications, career roadmaps, and experience posts use columns present in the supplied schema.

## Not a schema mismatch, but still incomplete
Several pre-existing UI frame classes are empty placeholders. This ZIP adds a role dashboard for core views, but it does not claim every planned Campus Connect feature is fully implemented. Runtime integration with the user's local MySQL credentials still needs to be tested on their machine.
