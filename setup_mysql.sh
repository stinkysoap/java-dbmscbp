#!/bin/bash
# MySQL Setup Script for Hotel Management System

echo "MySQL Setup for Hotel Management System"
echo "======================================"
echo ""

# Check if MySQL is running
if ! systemctl is-active --quiet mysql 2>/dev/null && ! pgrep -x mysqld > /dev/null; then
    echo "⚠️  MySQL doesn't appear to be running."
    echo "Please start MySQL first:"
    echo "  sudo systemctl start mysql    (Linux)"
    echo "  brew services start mysql     (macOS)"
    exit 1
fi

echo "Enter MySQL root password:"
read -s ROOT_PASSWORD

echo ""
echo "Enter password for 'hotel_user' (or press Enter to use 'hotel123'):"
read -s USER_PASSWORD
if [ -z "$USER_PASSWORD" ]; then
    USER_PASSWORD="hotel123"
fi

echo ""
echo "Creating database and user..."

mysql -u root -p"$ROOT_PASSWORD" <<EOF
-- Create database
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user
CREATE USER IF NOT EXISTS 'hotel_user'@'localhost' IDENTIFIED BY '$USER_PASSWORD';

-- Grant privileges
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;

-- Show success
SELECT 'Database and user created successfully!' AS Status;
SHOW DATABASES LIKE 'hotel_db';
EOF

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ MySQL setup complete!"
    echo ""
    echo "Update your application.properties with:"
    echo "  db.password=$USER_PASSWORD"
    echo ""
    echo "Or run this command to update it automatically:"
    echo "  sed -i 's/db.password=.*/db.password=$USER_PASSWORD/' src/main/resources/application.properties"
else
    echo ""
    echo "❌ Setup failed. Please check your MySQL root password and try again."
fi

