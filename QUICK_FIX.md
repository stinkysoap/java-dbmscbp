# Quick Fix for MySQL Access Denied

## Step 1: Connect to MySQL as root
```bash
mysql -u root -p
```

## Step 2: Run these SQL commands
```sql
-- Create database
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user (replace 'your_password' with your actual password)
CREATE USER IF NOT EXISTS 'hotel_user'@'localhost' IDENTIFIED BY 'your_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;

-- Verify
SELECT User, Host FROM mysql.user WHERE User = 'hotel_user';
EXIT;
```

## Step 3: Update application.properties
Edit `src/main/resources/application.properties` and set:
```properties
db.password=your_password
```
(Use the same password you used in Step 2)

## Step 4: Run the application again
```bash
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

---

## Alternative: Use root user temporarily

If you want to use root user instead:

1. Update `src/main/resources/application.properties`:
```properties
db.username=root
db.password=your_root_password
```

2. Run the application again.

