-- Sample Data for Hotel Management System
-- This script populates the database with realistic sample data
-- Run this after the schema is created

-- ============================================
-- ROOMS (20 rooms)
-- ============================================
INSERT INTO rooms (number, type, rate_per_night, status) VALUES
('101', 'SINGLE', 89.99, 'AVAILABLE'),
('102', 'SINGLE', 89.99, 'OCCUPIED'),
('103', 'SINGLE', 89.99, 'AVAILABLE'),
('201', 'DOUBLE', 129.99, 'OCCUPIED'),
('202', 'DOUBLE', 129.99, 'AVAILABLE'),
('203', 'DOUBLE', 129.99, 'MAINTENANCE'),
('204', 'DOUBLE', 129.99, 'AVAILABLE'),
('205', 'DOUBLE', 129.99, 'OCCUPIED'),
('301', 'SUITE', 249.99, 'AVAILABLE'),
('302', 'SUITE', 249.99, 'OCCUPIED'),
('303', 'SUITE', 249.99, 'AVAILABLE'),
('304', 'SUITE', 249.99, 'AVAILABLE'),
('401', 'DOUBLE', 149.99, 'AVAILABLE'),
('402', 'DOUBLE', 149.99, 'OCCUPIED'),
('403', 'SUITE', 299.99, 'AVAILABLE'),
('501', 'SINGLE', 99.99, 'AVAILABLE'),
('502', 'SINGLE', 99.99, 'OCCUPIED'),
('601', 'SUITE', 349.99, 'AVAILABLE'),
('602', 'SUITE', 349.99, 'AVAILABLE'),
('701', 'SUITE', 399.99, 'OCCUPIED');

-- ============================================
-- GUESTS (30 guests)
-- ============================================
INSERT INTO guests (full_name, phone, email) VALUES
('John Smith', '555-0101', 'john.smith@email.com'),
('Emily Johnson', '555-0102', 'emily.j@email.com'),
('Michael Brown', '555-0103', 'm.brown@email.com'),
('Sarah Davis', '555-0104', 'sarah.davis@email.com'),
('David Wilson', '555-0105', 'd.wilson@email.com'),
('Jessica Martinez', '555-0106', 'j.martinez@email.com'),
('Christopher Anderson', '555-0107', 'chris.anderson@email.com'),
('Amanda Taylor', '555-0108', 'amanda.t@email.com'),
('Matthew Thomas', '555-0109', 'matt.thomas@email.com'),
('Ashley Jackson', '555-0110', 'ashley.j@email.com'),
('Daniel White', '555-0111', 'dan.white@email.com'),
('Stephanie Harris', '555-0112', 'stephanie.h@email.com'),
('James Martin', '555-0113', 'james.martin@email.com'),
('Nicole Thompson', '555-0114', 'nicole.t@email.com'),
('Robert Garcia', '555-0115', 'rob.garcia@email.com'),
('Michelle Lewis', '555-0116', 'michelle.l@email.com'),
('William Walker', '555-0117', 'will.walker@email.com'),
('Laura Hall', '555-0118', 'laura.hall@email.com'),
('Joseph Allen', '555-0119', 'joe.allen@email.com'),
('Kimberly Young', '555-0120', 'kim.young@email.com'),
('Thomas King', '555-0121', 'thomas.king@email.com'),
('Angela Wright', '555-0122', 'angela.w@email.com'),
('Charles Lopez', '555-0123', 'charles.l@email.com'),
('Melissa Hill', '555-0124', 'melissa.hill@email.com'),
('Andrew Scott', '555-0125', 'andrew.scott@email.com'),
('Rebecca Green', '555-0126', 'rebecca.g@email.com'),
('Mark Adams', '555-0127', 'mark.adams@email.com'),
('Stephanie Baker', '555-0128', 'stephanie.b@email.com'),
('Kevin Gonzalez', '555-0129', 'kevin.g@email.com'),
('Rachel Nelson', '555-0130', 'rachel.nelson@email.com');

-- ============================================
-- CUSTOMERS (15 customers - some overlap with guests)
-- ============================================
INSERT INTO customers (full_name, phone, email) VALUES
('John Smith', '555-0101', 'john.smith@email.com'),
('Emily Johnson', '555-0102', 'emily.j@email.com'),
('Michael Brown', '555-0103', 'm.brown@email.com'),
('Sarah Davis', '555-0104', 'sarah.davis@email.com'),
('David Wilson', '555-0105', 'd.wilson@email.com'),
('Corporate Travel Inc', '555-1001', 'corporate@travel.com'),
('Business Solutions Ltd', '555-1002', 'bookings@bizsolutions.com'),
('Travel Agency Pro', '555-1003', 'info@travelpro.com'),
('Event Planning Co', '555-1004', 'events@planningco.com'),
('Tour Group International', '555-1005', 'tours@tgi.com'),
('Conference Organizers', '555-1006', 'conferences@org.com'),
('Wedding Planners Plus', '555-1007', 'weddings@planners.com'),
('Family Vacation Services', '555-1008', 'family@vacation.com'),
('Luxury Travel Group', '555-1009', 'luxury@travelgroup.com'),
('Adventure Tours Co', '555-1010', 'adventure@tours.com');

-- ============================================
-- EMPLOYEES (12 employees)
-- ============================================
INSERT INTO employees (full_name, role, phone, email, status) VALUES
('Alice Manager', 'MANAGER', '555-2001', 'alice.manager@hotel.com', 'ACTIVE'),
('Bob Receptionist', 'RECEPTIONIST', '555-2002', 'bob.reception@hotel.com', 'ACTIVE'),
('Carol Housekeeping', 'HOUSEKEEPING', '555-2003', 'carol.hk@hotel.com', 'ACTIVE'),
('David Maintenance', 'MAINTENANCE', '555-2004', 'david.maint@hotel.com', 'ACTIVE'),
('Eve Housekeeping', 'HOUSEKEEPING', '555-2005', 'eve.hk@hotel.com', 'ACTIVE'),
('Frank Concierge', 'CONCIERGE', '555-2006', 'frank.concierge@hotel.com', 'ACTIVE'),
('Grace Housekeeping', 'HOUSEKEEPING', '555-2007', 'grace.hk@hotel.com', 'ACTIVE'),
('Henry Maintenance', 'MAINTENANCE', '555-2008', 'henry.maint@hotel.com', 'ACTIVE'),
('Iris Receptionist', 'RECEPTIONIST', '555-2009', 'iris.reception@hotel.com', 'ACTIVE'),
('Jack Security', 'SECURITY', '555-2010', 'jack.security@hotel.com', 'ACTIVE'),
('Karen Housekeeping', 'HOUSEKEEPING', '555-2011', 'karen.hk@hotel.com', 'ACTIVE'),
('Larry Maintenance', 'MAINTENANCE', '555-2012', 'larry.maint@hotel.com', 'ACTIVE');

-- ============================================
-- BOOKINGS (25 bookings)
-- ============================================
INSERT INTO bookings (room_id, guest_id, check_in, check_out, total_amount, status) VALUES
(1, 1, '2024-01-15', '2024-01-18', 269.97, 'CHECKED_OUT'),
(2, 2, '2024-01-20', '2024-01-22', 179.98, 'CHECKED_OUT'),
(4, 3, '2024-01-10', '2024-01-13', 389.97, 'CHECKED_OUT'),
(5, 4, '2024-01-25', '2024-01-28', 389.97, 'BOOKED'),
(9, 5, '2024-01-12', '2024-01-15', 749.97, 'CHECKED_OUT'),
(10, 6, '2024-01-18', '2024-01-21', 749.97, 'CHECKED_IN'),
(11, 7, '2024-01-22', '2024-01-25', 749.97, 'BOOKED'),
(13, 8, '2024-01-14', '2024-01-17', 449.97, 'CHECKED_OUT'),
(14, 9, '2024-01-19', '2024-01-22', 449.97, 'CHECKED_IN'),
(15, 10, '2024-01-16', '2024-01-19', 899.97, 'CHECKED_OUT'),
(16, 11, '2024-01-21', '2024-01-24', 299.97, 'BOOKED'),
(17, 12, '2024-01-13', '2024-01-16', 299.97, 'CHECKED_OUT'),
(18, 13, '2024-01-17', '2024-01-20', 1049.97, 'BOOKED'),
(19, 14, '2024-01-11', '2024-01-14', 1049.97, 'CHECKED_OUT'),
(20, 15, '2024-01-23', '2024-01-26', 1199.97, 'CHECKED_IN'),
(1, 16, '2024-01-28', '2024-01-31', 269.97, 'BOOKED'),
(3, 17, '2024-01-26', '2024-01-29', 269.97, 'BOOKED'),
(6, 18, '2024-01-24', '2024-01-27', 389.97, 'BOOKED'),
(7, 19, '2024-01-27', '2024-01-30', 389.97, 'BOOKED'),
(8, 20, '2024-01-29', '2024-02-01', 389.97, 'BOOKED'),
(12, 21, '2024-01-30', '2024-02-02', 749.97, 'BOOKED'),
(1, 22, '2024-02-03', '2024-02-06', 269.97, 'BOOKED'),
(4, 23, '2024-02-01', '2024-02-04', 389.97, 'BOOKED'),
(9, 24, '2024-02-05', '2024-02-08', 749.97, 'BOOKED'),
(13, 25, '2024-02-02', '2024-02-05', 449.97, 'BOOKED'),
(15, 26, '2024-02-04', '2024-02-07', 899.97, 'BOOKED');

-- ============================================
-- SERVICES (15 services)
-- ============================================
INSERT INTO services (name, description, unit_price) VALUES
('Room Service Breakfast', 'Full breakfast delivered to room', 25.00),
('Room Service Lunch', 'Lunch menu delivered to room', 35.00),
('Room Service Dinner', 'Dinner menu delivered to room', 45.00),
('Laundry Service', 'Professional laundry and dry cleaning', 15.00),
('Spa Massage', '60-minute full body massage', 120.00),
('Spa Facial', 'Deep cleansing facial treatment', 80.00),
('Airport Shuttle', 'Round trip airport transportation', 50.00),
('Valet Parking', 'Daily valet parking service', 30.00),
('Mini Bar Refill', 'Refill of in-room mini bar', 40.00),
('Late Checkout', 'Extended checkout until 2 PM', 25.00),
('WiFi Premium', 'High-speed premium WiFi access', 10.00),
('Pet Fee', 'Additional fee for pet accommodation', 50.00),
('Extra Bed', 'Additional bed in room', 35.00),
('Business Center Access', 'Access to business center facilities', 20.00),
('Gym Access', 'Access to fitness center', 15.00);

-- ============================================
-- BOOKING_SERVICES (40 service line items)
-- ============================================
INSERT INTO booking_services (booking_id, service_id, quantity, unit_price, line_total) VALUES
(1, 1, 2, 25.00, 50.00),
(1, 4, 1, 15.00, 15.00),
(2, 1, 1, 25.00, 25.00),
(2, 11, 2, 10.00, 20.00),
(3, 2, 1, 35.00, 35.00),
(3, 3, 1, 45.00, 45.00),
(4, 5, 1, 120.00, 120.00),
(5, 1, 3, 25.00, 75.00),
(5, 3, 2, 45.00, 90.00),
(5, 5, 1, 120.00, 120.00),
(6, 4, 2, 15.00, 30.00),
(6, 7, 1, 50.00, 50.00),
(7, 1, 2, 25.00, 50.00),
(7, 11, 3, 10.00, 30.00),
(8, 2, 1, 35.00, 35.00),
(8, 4, 1, 15.00, 15.00),
(9, 3, 1, 45.00, 45.00),
(9, 8, 3, 30.00, 90.00),
(10, 5, 2, 120.00, 240.00),
(10, 6, 1, 80.00, 80.00),
(11, 1, 1, 25.00, 25.00),
(11, 4, 1, 15.00, 15.00),
(12, 2, 2, 35.00, 70.00),
(12, 7, 1, 50.00, 50.00),
(13, 1, 3, 25.00, 75.00),
(13, 3, 2, 45.00, 90.00),
(13, 5, 1, 120.00, 120.00),
(14, 4, 2, 15.00, 30.00),
(14, 11, 3, 10.00, 30.00),
(15, 1, 2, 25.00, 50.00),
(15, 8, 4, 30.00, 120.00),
(15, 5, 1, 120.00, 120.00),
(16, 2, 1, 35.00, 35.00),
(16, 4, 1, 15.00, 15.00),
(17, 1, 2, 25.00, 50.00),
(17, 11, 2, 10.00, 20.00),
(18, 3, 1, 45.00, 45.00),
(18, 7, 1, 50.00, 50.00),
(19, 1, 1, 25.00, 25.00),
(20, 4, 2, 15.00, 30.00);

-- ============================================
-- INVOICES (20 invoices)
-- ============================================
INSERT INTO invoices (booking_id, issued_at, subtotal, tax_total, discount_total, total, status) VALUES
(1, '2024-01-18 10:30:00', 334.97, 26.80, 0.00, 361.77, 'PAID'),
(2, '2024-01-22 11:15:00', 224.98, 18.00, 0.00, 242.98, 'PAID'),
(3, '2024-01-13 14:20:00', 469.97, 37.60, 0.00, 507.57, 'PAID'),
(4, '2024-01-25 09:45:00', 509.97, 40.80, 0.00, 550.77, 'ISSUED'),
(5, '2024-01-15 16:00:00', 1014.97, 81.20, 0.00, 1096.17, 'PAID'),
(6, '2024-01-18 12:30:00', 829.97, 66.40, 0.00, 896.37, 'ISSUED'),
(7, '2024-01-22 10:00:00', 829.97, 66.40, 0.00, 896.37, 'ISSUED'),
(8, '2024-01-17 15:45:00', 499.97, 40.00, 0.00, 539.97, 'PAID'),
(9, '2024-01-19 11:20:00', 584.97, 46.80, 0.00, 631.77, 'ISSUED'),
(10, '2024-01-19 13:15:00', 1219.97, 97.60, 0.00, 1317.57, 'PAID'),
(11, '2024-01-21 09:30:00', 339.97, 27.20, 0.00, 367.17, 'ISSUED'),
(12, '2024-01-16 14:00:00', 369.97, 29.60, 0.00, 399.57, 'PAID'),
(13, '2024-01-17 10:45:00', 1234.97, 98.80, 0.00, 1333.77, 'ISSUED'),
(14, '2024-01-14 16:30:00', 1079.97, 86.40, 0.00, 1166.37, 'PAID'),
(15, '2024-01-23 12:00:00', 1469.97, 117.60, 0.00, 1587.57, 'ISSUED'),
(16, '2024-01-28 09:00:00', 334.97, 26.80, 0.00, 361.77, 'ISSUED'),
(17, '2024-01-26 11:30:00', 334.97, 26.80, 0.00, 361.77, 'ISSUED'),
(18, '2024-01-24 13:45:00', 484.97, 38.80, 0.00, 523.77, 'ISSUED'),
(19, '2024-01-27 10:15:00', 484.97, 38.80, 0.00, 523.77, 'ISSUED'),
(20, '2024-01-29 14:20:00', 419.97, 33.60, 0.00, 453.57, 'ISSUED');

-- ============================================
-- PAYMENTS (30 payments)
-- ============================================
INSERT INTO payments (invoice_id, booking_id, paid_at, method, amount, reference) VALUES
(1, 1, '2024-01-18 10:35:00', 'CARD', 361.77, 'CARD-2024-001'),
(2, 2, '2024-01-22 11:20:00', 'CARD', 242.98, 'CARD-2024-002'),
(3, 3, '2024-01-13 14:25:00', 'ONLINE', 507.57, 'ONLINE-2024-003'),
(5, 5, '2024-01-15 16:05:00', 'CARD', 1096.17, 'CARD-2024-005'),
(8, 8, '2024-01-17 15:50:00', 'CASH', 539.97, 'CASH-2024-008'),
(10, 10, '2024-01-19 13:20:00', 'CARD', 1317.57, 'CARD-2024-010'),
(12, 12, '2024-01-16 14:05:00', 'ONLINE', 399.57, 'ONLINE-2024-012'),
(14, 14, '2024-01-14 16:35:00', 'CARD', 1166.37, 'CARD-2024-014'),
(4, 4, '2024-01-25 09:50:00', 'CARD', 300.00, 'CARD-2024-004-PARTIAL'),
(6, 6, '2024-01-18 12:35:00', 'CARD', 500.00, 'CARD-2024-006-PARTIAL'),
(7, 7, '2024-01-22 10:05:00', 'ONLINE', 896.37, 'ONLINE-2024-007'),
(9, 9, '2024-01-19 11:25:00', 'CARD', 631.77, 'CARD-2024-009'),
(11, 11, '2024-01-21 09:35:00', 'CASH', 200.00, 'CASH-2024-011-PARTIAL'),
(13, 13, '2024-01-17 10:50:00', 'CARD', 1000.00, 'CARD-2024-013-PARTIAL'),
(15, 15, '2024-01-23 12:05:00', 'CARD', 800.00, 'CARD-2024-015-PARTIAL'),
(16, 16, '2024-01-28 09:05:00', 'ONLINE', 361.77, 'ONLINE-2024-016'),
(17, 17, '2024-01-26 11:35:00', 'CARD', 361.77, 'CARD-2024-017'),
(18, 18, '2024-01-24 13:50:00', 'CARD', 523.77, 'CARD-2024-018'),
(19, 19, '2024-01-27 10:20:00', 'ONLINE', 523.77, 'ONLINE-2024-019'),
(20, 20, '2024-01-29 14:25:00', 'CARD', 453.57, 'CARD-2024-020'),
(NULL, 1, '2024-01-15 10:00:00', 'CARD', 100.00, 'DEPOSIT-001'),
(NULL, 5, '2024-01-12 09:00:00', 'ONLINE', 200.00, 'DEPOSIT-005'),
(NULL, 10, '2024-01-16 08:00:00', 'CARD', 300.00, 'DEPOSIT-010'),
(NULL, 15, '2024-01-23 11:00:00', 'CARD', 500.00, 'DEPOSIT-015'),
(NULL, 18, '2024-01-24 12:00:00', 'ONLINE', 150.00, 'DEPOSIT-018'),
(NULL, 19, '2024-01-27 09:00:00', 'CARD', 200.00, 'DEPOSIT-019'),
(NULL, 20, '2024-01-29 13:00:00', 'CASH', 100.00, 'DEPOSIT-020'),
(NULL, 21, '2024-01-30 10:00:00', 'CARD', 250.00, 'DEPOSIT-021'),
(NULL, 22, '2024-02-03 08:00:00', 'ONLINE', 150.00, 'DEPOSIT-022'),
(NULL, 23, '2024-02-01 09:00:00', 'CARD', 200.00, 'DEPOSIT-023');

-- ============================================
-- ROOM_TASKS (25 tasks)
-- ============================================
INSERT INTO room_tasks (room_id, employee_id, task_type, created_at, notes, priority, status) VALUES
(3, 3, 'HOUSEKEEPING', '2024-01-20 08:00:00', 'Deep clean after checkout', 'HIGH', 'COMPLETED'),
(3, 4, 'MAINTENANCE', '2024-01-20 09:00:00', 'Fix air conditioning unit', 'HIGH', 'COMPLETED'),
(6, 5, 'HOUSEKEEPING', '2024-01-22 07:30:00', 'Standard cleaning', 'MEDIUM', 'COMPLETED'),
(7, 6, 'HOUSEKEEPING', '2024-01-23 08:00:00', 'Prepare for new guest', 'HIGH', 'IN_PROGRESS'),
(8, 3, 'HOUSEKEEPING', '2024-01-24 07:00:00', 'Full room service', 'MEDIUM', 'OPEN'),
(11, 4, 'MAINTENANCE', '2024-01-21 10:00:00', 'Replace light bulbs', 'LOW', 'COMPLETED'),
(12, 5, 'HOUSEKEEPING', '2024-01-25 08:30:00', 'Clean and restock', 'MEDIUM', 'OPEN'),
(13, 3, 'HOUSEKEEPING', '2024-01-26 07:00:00', 'Standard maintenance clean', 'MEDIUM', 'OPEN'),
(15, 4, 'MAINTENANCE', '2024-01-22 11:00:00', 'Check plumbing', 'MEDIUM', 'COMPLETED'),
(16, 5, 'HOUSEKEEPING', '2024-01-27 08:00:00', 'Deep clean suite', 'HIGH', 'IN_PROGRESS'),
(17, 3, 'HOUSEKEEPING', '2024-01-28 07:30:00', 'Prepare for checkout', 'HIGH', 'OPEN'),
(18, 4, 'MAINTENANCE', '2024-01-24 14:00:00', 'Fix TV remote', 'LOW', 'COMPLETED'),
(19, 5, 'HOUSEKEEPING', '2024-01-29 08:00:00', 'Full suite cleaning', 'HIGH', 'OPEN'),
(1, 3, 'HOUSEKEEPING', '2024-01-31 07:00:00', 'Standard clean', 'MEDIUM', 'OPEN'),
(2, 4, 'MAINTENANCE', '2024-01-30 09:00:00', 'Replace batteries in smoke detector', 'HIGH', 'COMPLETED'),
(4, 5, 'HOUSEKEEPING', '2024-02-01 08:00:00', 'Prepare for booking', 'HIGH', 'OPEN'),
(5, 3, 'HOUSEKEEPING', '2024-02-02 07:30:00', 'Clean after checkout', 'MEDIUM', 'OPEN'),
(9, 4, 'MAINTENANCE', '2024-02-03 10:00:00', 'Inspect minibar', 'LOW', 'OPEN'),
(10, 5, 'HOUSEKEEPING', '2024-02-04 08:00:00', 'Full service', 'HIGH', 'OPEN'),
(14, 3, 'HOUSEKEEPING', '2024-02-05 07:00:00', 'Standard cleaning', 'MEDIUM', 'OPEN'),
(20, 4, 'MAINTENANCE', '2024-01-25 15:00:00', 'Fix door lock mechanism', 'HIGH', 'COMPLETED'),
(1, 5, 'HOUSEKEEPING', '2024-02-06 08:00:00', 'Prepare for new guest', 'HIGH', 'OPEN'),
(3, 3, 'HOUSEKEEPING', '2024-02-07 07:30:00', 'Deep clean', 'MEDIUM', 'OPEN'),
(6, 4, 'MAINTENANCE', '2024-02-08 09:00:00', 'Check heating system', 'MEDIUM', 'OPEN'),
(7, 5, 'HOUSEKEEPING', '2024-02-09 08:00:00', 'Full room service', 'HIGH', 'OPEN');

-- ============================================
-- Summary Statistics
-- ============================================
-- Total Records:
--   Rooms: 20
--   Guests: 30
--   Customers: 15
--   Employees: 12
--   Bookings: 25
--   Services: 15
--   Booking Services: 40
--   Invoices: 20
--   Payments: 30
--   Room Tasks: 25
-- 
-- Total: 232 records

