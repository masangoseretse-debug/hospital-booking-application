# Testing Evidence

## Automated unit tests

Run `mvn test`. The current JUnit suite verifies email validation, password length and appointment-date validation.

## Integration test matrix

| ID | Test | Expected result |
|---|---|---|
| IT-01 | Start application while MySQL is available | Login page opens without a connection error |
| IT-02 | Sign in with the patient demo account | Patient dashboard displays only that patient's appointments |
| IT-03 | Create an appointment | Record is inserted and immediately appears in the table |
| IT-04 | Edit an appointment date and status | Database and UI display the updated values |
| IT-05 | Delete an appointment and confirm | Record disappears from the UI and database |
| IT-06 | Search for a patient as administrator | Matching database records are displayed |
| IT-07 | Stop MySQL and attempt login | Application reports a server/database failure instead of showing hardcoded data |

## User acceptance tests

| ID | User action | Expected result | Actual result | Pass |
|---|---|---|---|---|
| UAT-01 | Register a new patient | Registration succeeds and patient can sign in | To be recorded | ☐ |
| UAT-02 | Patient books an appointment | Success message and new table row appear | To be recorded | ☐ |
| UAT-03 | Doctor filters pending appointments | Only pending assigned records appear | To be recorded | ☐ |
| UAT-04 | Administrator updates an appointment | Updated values appear immediately | To be recorded | ☐ |
| UAT-05 | Administrator deactivates a user | User becomes inactive and cannot sign in | To be recorded | ☐ |
| UAT-06 | Use site on a phone-sized screen | Navigation and tables remain usable | To be recorded | ☐ |

## Bug log

| Bug | Cause | Resolution | Status |
|---|---|---|---|
| Original ZIP exceeded GitHub browser limit | `node_modules` added 169 MB | Rebuilt without Node dependencies and added a correct `.gitignore` | Resolved |
| Original project required a configured Supabase instance | Environment keys and remote schema were required | Replaced Supabase access with JDBC and a reproducible MySQL schema | Resolved |
| Duplicate doctor bookings were possible | No local database uniqueness rule | Added a unique key for doctor, date and time | Resolved |

Add screenshots of tests and completed actual results before the individual MP4 submission.
