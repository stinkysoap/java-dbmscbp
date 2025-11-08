# MySQL Setup Guide

This guide will help you set up MySQL for the Hotel Management System.

## Prerequisites

- MySQL Server 8.0 or higher installed
- Java 17 or higher
- Maven installed

## Step 1: Install MySQL

### On Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

### On macOS:
```bash
brew install mysql
brew services start mysql
```

### On Windows:
Download and install from: https://dev.mysql.com/downloads/mysql/

## Step 2: Secure MySQL Installation (First Time Setup)

```bash
sudo mysql_secure_installation
```

Follow the prompts to:
- Set root password (or keep blank if you prefer)
- Remove anonymous users
- Disallow root login remotely (optional)
- Remove test database
- Reload privilege tables

## Step 3: Create Database and User

Login to MySQL:
```bash
mysql -u root -p
```

Then run these SQL commands:
```sql
-- Create the database
CREATE DATABASE hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create a dedicated user (recommended for security)
CREATE USER 'hotel_user'@'localhost' IDENTIFIED BY 'your_secure_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```

**Note:** Replace `your_secure_password` with a strong password.

## Step 4: Configure Application

Edit `src/main/resources/application.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/hotel_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.username=hotel_user
db.password=your_secure_password
db.initSchema=true
```

**Important:** Replace:
- `hotel_user` with your MySQL username (or `root` if using root)
- `your_secure_password` with your MySQL password

## Step 5: Build and Run

```bash
# Build the project (downloads MySQL JDBC driver)
mvn clean package

# Run the application
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

## Step 6: Verify Database

The application will automatically create all tables on first run (if `db.initSchema=true`).

To verify, connect to MySQL:
```bash
mysql -u hotel_user -p hotel_db
```

Then check tables:
```sql
SHOW TABLES;
SELECT COUNT(*) FROM rooms;
```

## Troubleshooting

### Connection Refused
- Ensure MySQL is running: `sudo systemctl status mysql` (Linux) or `brew services list` (macOS)
- Check MySQL is listening on port 3306: `netstat -an | grep 3306`

### Access Denied
- Verify username and password in `application.properties`
- Check user has privileges: `SHOW GRANTS FOR 'hotel_user'@'localhost';`

### Timezone Issues
- The connection string includes `serverTimezone=UTC` - adjust if needed
- For your timezone: `serverTimezone=America/New_York` (example)

### SSL Warnings
- `useSSL=false` is set for local development
- For production, enable SSL and use proper certificates

### Schema Not Creating
- Check MySQL user has CREATE privileges
- Manually create database if needed: `CREATE DATABASE hotel_db;`
- Set `db.initSchema=true` in `application.properties`

## Production Considerations

1. **Use Connection Pooling**: Consider HikariCP for production
2. **Enable SSL**: Change `useSSL=false` to `useSSL=true` with certificates
3. **Backup Strategy**: Set up regular MySQL backups
4. **Monitoring**: Use MySQL Workbench or similar tools
5. **User Permissions**: Use least-privilege principle (don't use root)

## Alternative: Using Root User (Not Recommended for Production)

If you want to use root user temporarily:

```properties
db.url=jdbc:mysql://localhost:3306/hotel_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.username=root
db.password=your_root_password
db.initSchema=true
```

## Testing Connection

You can test the MySQL connection before running the app:

```bash
mysql -u hotel_user -p -e "USE hotel_db; SHOW TABLES;"
```

If this works, your credentials are correct.

