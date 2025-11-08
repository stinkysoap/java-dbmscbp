# Fix: MySQL Root Access on Linux

## Problem
`ERROR 1698 (28000): Access denied for user 'root'@'localhost'`

This happens because MySQL 8.0+ uses `auth_socket` plugin for root by default on Linux.

## Solution 1: Use sudo (Easiest)
```bash
sudo mysql
```

Then run:
```sql
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'hotel_user'@'localhost' IDENTIFIED BY 'hotel123';
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

## Solution 2: Enable password authentication for root
If you want to use `mysql -u root -p`:

```bash
sudo mysql
```

Then:
```sql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'your_new_password';
FLUSH PRIVILEGES;
EXIT;
```

Now you can use: `mysql -u root -p`

## Solution 3: Use root without password (if auth_socket works)
If `sudo mysql` works, you can use root directly in the app:

Update `application.properties`:
```properties
db.username=root
db.password=
```

But you'll need to modify ConnectionManager to handle empty password, OR better: create hotel_user as shown in Solution 1.

