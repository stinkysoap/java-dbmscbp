# Hotel Management System (Java + JDBC)

A CLI-based hotel management system built with Java 17, JDBC, and SQLite. It supports operations for rooms, guests, bookings, billing, and more.

## Features
- Create/list rooms and guests
- Create/list bookings with date overlap checks
- Billing system (services, invoices, payments)
- SQLite database with auto-initialized schema

## Tech
- Java 17
- Maven
- JDBC with SQLite JDBC Driver

## Getting Started

### Prerequisites
- Java 17+
- Maven

### Setup SQLite
**No installation required!** SQLite is embedded - the database file will be created automatically.

The database file (`hotel.db`) will be created in the project root directory when you first run the application.

### Configure Application
Edit `src/main/resources/application.properties` (default configuration works out of the box):
```properties
db.url=jdbc:sqlite:hotel.db
db.initSchema=true
```

**Note**: SQLite doesn't require username/password. The database file is stored locally.

### Build and Run
```bash
# Build
mvn clean package

# Run
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

The application will automatically create all tables on first run.

### Load Sample Data (Optional)

To populate the database with sample data for testing:

```bash
# Load sample data (keeps existing data)
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader

# Clear existing data and load sample data
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader --clear
```

See [LOAD_SAMPLE_DATA.md](LOAD_SAMPLE_DATA.md) for details. The sample data includes:
- 20 rooms, 30 guests, 25 bookings
- 15 services, 20 invoices, 30 payments
- 12 employees, 25 room tasks
- **Total: 232 sample records**

## Project Structure
- `src/main/java/com/hotel/App.java` – CLI entrypoint
- `src/main/java/com/hotel/model/*` – domain models
- `src/main/java/com/hotel/dao/*` – DAO interfaces
- `src/main/java/com/hotel/dao/jdbc/*` – JDBC implementations
- `src/main/java/com/hotel/service/HotelService.java` – business logic
- `src/main/java/com/hotel/util/ConnectionManager.java` – JDBC connection + schema init
- `src/main/resources/schema.sql` – tables DDL

## ER Diagram (10 tables)
See `docs/er.md` or preview below:

```mermaid
erDiagram
    ROOMS {
        INTEGER id PK
        TEXT number UK
        TEXT type
        REAL rate_per_night
        TEXT status
    }

    GUESTS {
        INTEGER id PK
        TEXT full_name
        TEXT phone
        TEXT email
    }

    CUSTOMERS {
        INTEGER id PK
        TEXT full_name
        TEXT phone
        TEXT email
    }

    BOOKINGS {
        INTEGER id PK
        INTEGER room_id FK
        INTEGER guest_id FK
        DATE check_in
        DATE check_out
        REAL total_amount
        TEXT status
    }

    ROOMS ||--o{ BOOKINGS : "has"
    GUESTS ||--o{ BOOKINGS : "makes"

    EMPLOYEES {
        INTEGER id PK
        TEXT full_name
        TEXT role
        TEXT phone
        TEXT email
        TEXT status
    }

    ROOM_TASKS {
        INTEGER id PK
        INTEGER room_id FK
        INTEGER employee_id FK
        TEXT task_type
        DATETIME created_at
        TEXT notes
        TEXT priority
        TEXT status
    }

    SERVICES {
        INTEGER id PK
        TEXT name UK
        TEXT description
        REAL unit_price
    }

    BOOKING_SERVICES {
        INTEGER id PK
        INTEGER booking_id FK
        INTEGER service_id FK
        INTEGER quantity
        REAL unit_price
        REAL line_total
    }

    INVOICES {
        INTEGER id PK
        INTEGER booking_id FK
        DATETIME issued_at
        REAL subtotal
        REAL tax_total
        REAL discount_total
        REAL total
        TEXT status
    }

    PAYMENTS {
        INTEGER id PK
        INTEGER invoice_id FK
        INTEGER booking_id FK
        DATETIME paid_at
        TEXT method
        REAL amount
        TEXT reference
    }


    EMPLOYEES ||--o{ ROOM_TASKS : "performs"
    ROOMS ||--o{ ROOM_TASKS : "tasks"
    BOOKINGS ||--o{ BOOKING_SERVICES : "includes"
    SERVICES ||--o{ BOOKING_SERVICES : "used"
    BOOKINGS ||--o{ INVOICES : "billed"
    INVOICES ||--o{ PAYMENTS : "paid_by"
    INVOICES ||--o{ INVOICE_TAXES : "taxes"
    TAXES ||--o{ INVOICE_TAXES : "applied"
    INVOICES ||--o{ INVOICE_DISCOUNTS : "discounts"
    DISCOUNTS ||--o{ INVOICE_DISCOUNTS : "applied"
    USERS ||--o{ USER_ROLES : "has"
    ROLES ||--o{ USER_ROLES : "granted"
```

### Notes
- Now exactly 10 tables: rooms, guests, customers, bookings, services, booking_services, invoices, payments, employees, room_tasks.
- `customers` added; `room_tasks` consolidates housekeeping and maintenance.
- Removed advanced billing (taxes/discounts/rate plans) and auth to simplify.

## Notes
- The application is configured for SQLite. See [SQLITE_SETUP.md](SQLITE_SETUP.md) for setup instructions.
- Database schema is automatically created on first run if `db.initSchema=true`.
- The database file (`hotel.db`) is created in the project root directory.
- All monetary values use `REAL` type in SQLite for floating-point precision.

