# Reset Database (If Schema Creation Fails)

If you get errors like "Table doesn't exist" during schema initialization, the database might be in a partial state. Reset it:

## Option 1: Drop and Recreate Database

```bash
sudo mysql
```

Then:
```sql
DROP DATABASE IF EXISTS hotel_db;
CREATE DATABASE hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

Then rebuild and run:
```bash
mvn clean package
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

## Option 2: Drop All Tables Manually

```bash
sudo mysql hotel_db
```

Then:
```sql
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS booking_services;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS room_tasks;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS guests;
DROP TABLE IF EXISTS rooms;
EXIT;
```

Then run the app again - it will recreate all tables.

