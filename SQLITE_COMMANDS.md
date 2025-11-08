# SQLite Command-Line Quick Reference

## Basic Usage

### Open Database
```bash
sqlite3 hotel.db
```

### Exit
```sql
.quit
```
or press `Ctrl+D`

## Essential Commands

### List All Tables
```sql
.tables
```

### View Table Schema
```sql
.schema
.schema rooms          # Specific table
```

### View All Data from a Table
```sql
SELECT * FROM rooms;
SELECT * FROM guests;
SELECT * FROM bookings;
SELECT * FROM services;
SELECT * FROM invoices;
SELECT * FROM payments;
```

### Count Records
```sql
SELECT COUNT(*) FROM rooms;
SELECT COUNT(*) FROM bookings;
```

## Useful Queries

### View All Bookings with Details
```sql
SELECT 
    b.id AS booking_id,
    g.full_name AS guest,
    r.number AS room,
    b.check_in,
    b.check_out,
    b.total_amount,
    b.status
FROM bookings b
JOIN guests g ON b.guest_id = g.id
JOIN rooms r ON b.room_id = r.id
ORDER BY b.check_in DESC;
```

### View Available Rooms
```sql
SELECT * FROM rooms WHERE status = 'AVAILABLE';
```

### View Active Bookings
```sql
SELECT * FROM bookings WHERE status IN ('BOOKED', 'CHECKED_IN');
```

### View Invoices with Payments
```sql
SELECT 
    i.id,
    i.booking_id,
    i.total,
    i.status,
    COALESCE(SUM(p.amount), 0) AS paid
FROM invoices i
LEFT JOIN payments p ON i.id = p.invoice_id
GROUP BY i.id;
```

### View Services Used in Bookings
```sql
SELECT 
    bs.booking_id,
    s.name AS service_name,
    bs.quantity,
    bs.line_total
FROM booking_services bs
JOIN services s ON bs.service_id = s.id;
```

## One-Line Commands (Without Opening SQLite)

### View All Rooms
```bash
sqlite3 hotel.db "SELECT * FROM rooms;"
```

### View All Bookings
```bash
sqlite3 hotel.db "SELECT * FROM bookings;"
```

### View Bookings with Guest Names
```bash
sqlite3 hotel.db "SELECT b.id, g.full_name, r.number, b.check_in, b.check_out FROM bookings b JOIN guests g ON b.guest_id = g.id JOIN rooms r ON b.room_id = r.id;"
```

### Count Records in Each Table
```bash
sqlite3 hotel.db "SELECT 'rooms' as table_name, COUNT(*) as count FROM rooms UNION SELECT 'guests', COUNT(*) FROM guests UNION SELECT 'bookings', COUNT(*) FROM bookings;"
```

## Export Data

### Export to CSV
```bash
sqlite3 hotel.db <<EOF
.headers on
.mode csv
.output rooms.csv
SELECT * FROM rooms;
.quit
EOF
```

### Export to JSON
```bash
sqlite3 hotel.db <<EOF
.mode json
.output data.json
SELECT * FROM rooms;
SELECT * FROM bookings;
.quit
EOF
```

## Formatting Output

### Column Mode (Better for Wide Tables)
```sql
.mode column
.headers on
.width 5 15 10 10
SELECT * FROM rooms;
```

### List Mode (One Column Per Line)
```sql
.mode list
SELECT * FROM rooms LIMIT 5;
```

### Table Mode (Default)
```sql
.mode table
SELECT * FROM rooms LIMIT 5;
```

## Quick Tips

1. **Enable Headers**: Always use `.headers on` to see column names
2. **Limit Results**: Use `LIMIT 10` to see first 10 rows
3. **Pretty Print**: Use `.mode column` for better formatting
4. **Save Output**: Use `.output filename.txt` to save results

## Example Session

```bash
$ sqlite3 hotel.db
SQLite version 3.x.x
Enter ".help" for usage hints.

sqlite> .headers on
sqlite> .mode column
sqlite> SELECT * FROM rooms;
id     number  type    rate_per_night  status
-----  ------  ------  --------------  ---------
1      101     SINGLE  50.0            AVAILABLE
2      102     DOUBLE  75.0            OCCUPIED

sqlite> SELECT COUNT(*) FROM bookings;
COUNT(*)
--------
5

sqlite> .quit
```

