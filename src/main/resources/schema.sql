-- Rooms
CREATE TABLE IF NOT EXISTS rooms (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    number TEXT NOT NULL UNIQUE,
    type TEXT NOT NULL,
    rate_per_night REAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'AVAILABLE'
);

-- Guests (kept for existing app flows)
CREATE TABLE IF NOT EXISTS guests (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name TEXT NOT NULL,
    phone TEXT,
    email TEXT
);

-- Customers (new consolidated customer table)
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name TEXT NOT NULL,
    phone TEXT,
    email TEXT
);

-- Bookings
CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    room_id INTEGER NOT NULL,
    guest_id INTEGER NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    total_amount REAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'BOOKED',
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (guest_id) REFERENCES guests(id)
);

-- Simple indexes
CREATE INDEX IF NOT EXISTS idx_bookings_room ON bookings(room_id);
CREATE INDEX IF NOT EXISTS idx_bookings_guest ON bookings(guest_id);

-- Employees
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name TEXT NOT NULL,
    role TEXT NOT NULL,
    phone TEXT,
    email TEXT,
    status TEXT NOT NULL DEFAULT 'ACTIVE'
);

-- Consolidated room tasks (housekeeping + maintenance)
CREATE TABLE IF NOT EXISTS room_tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    room_id INTEGER NOT NULL,
    employee_id INTEGER,
    task_type TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT (datetime('now')),
    notes TEXT,
    priority TEXT NOT NULL DEFAULT 'MEDIUM',
    status TEXT NOT NULL DEFAULT 'OPEN',
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Services catalog (e.g., spa, laundry)
CREATE TABLE IF NOT EXISTS services (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT,
    unit_price REAL NOT NULL
);

-- Booking line-items for services
CREATE TABLE IF NOT EXISTS booking_services (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INTEGER NOT NULL,
    service_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price REAL NOT NULL,
    line_total REAL NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (service_id) REFERENCES services(id)
);

-- Invoices for bookings
CREATE TABLE IF NOT EXISTS invoices (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INTEGER NOT NULL,
    issued_at DATETIME NOT NULL DEFAULT (datetime('now')),
    subtotal REAL NOT NULL,
    tax_total REAL NOT NULL,
    discount_total REAL NOT NULL DEFAULT 0,
    total REAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'ISSUED',
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- Payments linked to invoices or bookings
CREATE TABLE IF NOT EXISTS payments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    invoice_id INTEGER,
    booking_id INTEGER,
    paid_at DATETIME NOT NULL DEFAULT (datetime('now')),
    method TEXT NOT NULL,
    amount REAL NOT NULL,
    reference TEXT,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE INDEX IF NOT EXISTS idx_room_tasks_room ON room_tasks(room_id);
CREATE INDEX IF NOT EXISTS idx_booking_services_booking ON booking_services(booking_id);
CREATE INDEX IF NOT EXISTS idx_invoices_booking ON invoices(booking_id);
CREATE INDEX IF NOT EXISTS idx_payments_invoice ON payments(invoice_id);
