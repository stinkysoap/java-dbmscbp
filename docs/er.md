### Entity–Relationship Diagram

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


    ROOMS ||--o{ ROOM_TASKS : "has"
    EMPLOYEES ||--o{ ROOM_TASKS : "performs"
    BOOKINGS ||--o{ BOOKING_SERVICES : "includes"
    SERVICES ||--o{ BOOKING_SERVICES : "used"
    BOOKINGS ||--o{ INVOICES : "billed"
    INVOICES ||--o{ PAYMENTS : "paid_by"
    BOOKINGS ||--o{ PAYMENTS : "paid_for"
```

Notes:
- **10 tables total**: rooms, guests, customers, bookings, employees, room_tasks, services, booking_services, invoices, payments
- `ROOMS.number` is unique.
- `BOOKINGS.status` in { BOOKED, CHECKED_IN, CHECKED_OUT, CANCELED }.
- `ROOM_TASKS.task_type` in { HOUSEKEEPING, MAINTENANCE }.
- `PAYMENTS` can link to either `INVOICES` or `BOOKINGS` (or both).
- Overlap validation is enforced in application logic.

