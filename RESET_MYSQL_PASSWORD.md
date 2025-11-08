# Reset MySQL Password

## Step 1: Access MySQL without password (Linux)
```bash
sudo mysql
```

## Step 2: Reset root password (choose one)

### Option A: Reset root password
```sql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'new_password_here';
FLUSH PRIVILEGES;
EXIT;
```

Now you can use: `mysql -u root -p` (with your new password)

### Option B: Create hotel_user (Recommended - no need to reset root)
```sql
-- Create database
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user with password
CREATE USER IF NOT EXISTS 'hotel_user'@'localhost' IDENTIFIED BY 'hotel123';

-- Grant privileges
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;

-- Verify
SELECT User, Host FROM mysql.user WHERE User = 'hotel_user';
EXIT;
```

## Step 3: Update application.properties
Edit `src/main/resources/application.properties`:

If using hotel_user:
```properties
db.username=hotel_user
db.password=hotel123
```

If using root:
```properties
db.username=root
db.password=new_password_here
```

## Step 4: Test connection
```bash
# Test with hotel_user
mysql -u hotel_user -p hotel_db
# Enter password: hotel123

# Or test with root
mysql -u root -p
# Enter password: new_password_here
```

## Step 5: Run the application
```bash
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

