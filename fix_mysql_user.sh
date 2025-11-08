#!/bin/bash
# Script to fix MySQL user setup

echo "=== MySQL User Setup Fix ==="
echo ""
echo "Choose an option:"
echo "1. Reset password for existing hotel_user"
echo "2. Create new hotel_user (will drop existing if present)"
echo "3. Use root user instead"
echo ""
read -p "Enter choice (1-3): " choice

case $choice in
    1)
        echo ""
        echo "Enter new password for hotel_user:"
        read -s NEW_PASSWORD
        sudo mysql <<EOF
ALTER USER IF EXISTS 'hotel_user'@'localhost' IDENTIFIED BY '$NEW_PASSWORD';
FLUSH PRIVILEGES;
SELECT 'Password updated!' AS Status;
EOF
        echo ""
        echo "Update application.properties with:"
        echo "  db.password=$NEW_PASSWORD"
        ;;
    2)
        echo ""
        echo "Enter password for new hotel_user:"
        read -s NEW_PASSWORD
        sudo mysql <<EOF
DROP USER IF EXISTS 'hotel_user'@'localhost';
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'hotel_user'@'localhost' IDENTIFIED BY '$NEW_PASSWORD';
GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel_user'@'localhost';
FLUSH PRIVILEGES;
SELECT 'User created!' AS Status;
EOF
        echo ""
        echo "Update application.properties with:"
        echo "  db.password=$NEW_PASSWORD"
        ;;
    3)
        echo ""
        echo "Enter root password (or press Enter if using sudo mysql):"
        read -s ROOT_PASS
        if [ -z "$ROOT_PASS" ]; then
            echo "Using root without password (via sudo)"
            echo ""
            echo "Update application.properties with:"
            echo "  db.username=root"
            echo "  db.password="
        else
            echo ""
            echo "Update application.properties with:"
            echo "  db.username=root"
            echo "  db.password=$ROOT_PASS"
        fi
        ;;
    *)
        echo "Invalid choice"
        exit 1
        ;;
esac

