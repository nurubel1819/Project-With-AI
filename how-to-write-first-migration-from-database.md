# How to Write First Migration from Database

A quick reference for creating the initial Flyway migration (`V1`) from an existing database that was built using `ddl-auto: update`.

---

## 1. Build the schema with Hibernate first

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

Run the app once so Hibernate creates all tables from your `@Entity` classes.

---

## 2. Export the schema (structure only, no data)

Run from the migration folder:

```powershell
cd src\main\resources\db\migration
mysqldump -u root -p1234 --no-data --skip-comments ai_project | Out-File -Encoding utf8 V1__init_schema.sql
```

**Key flags:**
| Flag | Purpose |
|---|---|
| `--no-data` | Export table structure only, no rows |
| `--skip-comments` | Remove `mysqldump` version/host comment lines |
| ❌ Don't use `--compact` | It strips the `FOREIGN_KEY_CHECKS` statements, which breaks table creation order |
| `Out-File -Encoding utf8` | Forces UTF-8 output. **Never use `>` in PowerShell** — it saves as UTF-16, which Flyway cannot read (`MalformedInputException`) |

---

## 3. Verify the generated file

```powershell
# Check encoding (first bytes should NOT be 255/254 — that means UTF-16)
Get-Content -Encoding Byte -TotalCount 4 V1__init_schema.sql

# Check FOREIGN_KEY_CHECKS lines exist (protects table creation order)
Get-Content V1__init_schema.sql | Select-String "FOREIGN_KEY_CHECKS"

# Check ENUM columns have actual values, not just "enum"
Get-Content V1__init_schema.sql | Select-String "enum"
```

**Remove environment-specific noise:**
```powershell
(Get-Content V1__init_schema.sql -Raw) -replace ' AUTO_INCREMENT=\d+', '' | Set-Content -Encoding utf8 V1__init_schema.sql
```

---

## 4. Update `application.yaml`

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate    # switch from update → validate
  flyway:
    enabled: true
    locations: classpath:db/migration
    validate-on-migrate: true
```

> No `baseline-on-migrate` / `baseline-version` needed if starting from an empty (dropped) database.

---

## 5. Drop the database and re-run

```sql
DROP DATABASE ai_project;
```

Then start the app. Flyway will:
1. Create the database (via `createDatabaseIfNotExist=true` in the JDBC URL)
2. Create `flyway_schema_history` table
3. Execute `V1__init_schema.sql`
4. Hibernate validates entities against the new schema

Expect to see in logs:
```
Successfully applied 1 migration to schema `ai_project`
```

---

## 6. Going forward — never edit `V1` again

Once `V1__init_schema.sql` is applied, it's locked. Any schema change becomes a new file:

```
db/migration/
  V1__init_schema.sql
  V2__add_refresh_tokens_table.sql
  V3__add_index_on_users_email.sql
```

Naming rule: `V<version>__<description>.sql` (double underscore, version numbers strictly increasing).

---

## Common pitfalls checklist

- [ ] Used `Out-File -Encoding utf8`, not `>` (avoids UTF-16 checksum error)
- [ ] Did **not** use `--compact` (avoids foreign key / table order errors)
- [ ] ENUM columns have their value lists (`enum('ADMIN','USER')`), not bare `enum`
- [ ] Removed `AUTO_INCREMENT=n` values (environment-specific)
- [ ] `ddl-auto` switched from `update` to `validate` before running Flyway
- [ ] Database dropped/empty before first Flyway run (or use `baseline-on-migrate: true` if keeping existing data)
- [ ] Never edit an already-applied migration file — always add a new version
