package com.hotel;

import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Room;
import com.hotel.service.HotelService;
import com.hotel.service.BillingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        HotelService service = new HotelService();
        BillingService billing = new BillingService();
        boolean running = true;
        while (running) {
            System.out.println("\nHotel Management System");
            System.out.println("1) Add Room");
            System.out.println("2) List Rooms");
            System.out.println("3) Add Guest");
            System.out.println("4) List Guests");
            System.out.println("5) Create Booking");
            System.out.println("6) List Bookings");
            System.out.println("7) Add Service");
            System.out.println("8) List Services");
            System.out.println("9) Add Service to Booking");
            System.out.println("10) Generate Invoice for Booking");
            System.out.println("11) Record Payment");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            if (!SCANNER.hasNextLine()) {
                System.out.println("\nNo input detected. Exiting.");
                break;
            }
            String choice = SCANNER.nextLine();
            try {
                switch (choice) {
                    case "1":
                        addRoom(service);
                        break;
                    case "2":
                        listRooms(service);
                        break;
                    case "3":
                        addGuest(service);
                        break;
                    case "4":
                        listGuests(service);
                        break;
                    case "5":
                        createBooking(service);
                        break;
                    case "6":
                        listBookings(service);
                        break;
                    case "7":
                        addService(billing);
                        break;
                    case "8":
                        listServices(billing);
                        break;
                    case "9":
                        addServiceToBooking(billing);
                        break;
                    case "10":
                        generateInvoice(billing);
                        break;
                    case "11":
                        recordPayment(billing);
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private static void addRoom(HotelService service) {
        System.out.print("Room number: ");
        String number = SCANNER.nextLine();
        System.out.print("Type (SINGLE/DOUBLE/SUITE): ");
        String type = SCANNER.nextLine();
        System.out.print("Rate per night: ");
        double rate = Double.parseDouble(SCANNER.nextLine());
        Room room = service.addRoom(number, type, rate);
        System.out.println("Created room id=" + room.getId());
    }

    private static void listRooms(HotelService service) {
        List<Room> rooms = service.listRooms();
        rooms.forEach(r -> System.out.println(r.getId() + ": " + r.getNumber() + " - " + r.getType() + " - $" + r.getRatePerNight() + " - " + r.getStatus()));
    }

    private static void addGuest(HotelService service) {
        System.out.print("Full name: ");
        String name = SCANNER.nextLine();
        System.out.print("Phone: ");
        String phone = SCANNER.nextLine();
        System.out.print("Email: ");
        String email = SCANNER.nextLine();
        Guest guest = service.addGuest(name, phone, email);
        System.out.println("Created guest id=" + guest.getId());
    }

    private static void listGuests(HotelService service) {
        List<Guest> guests = service.listGuests();
        guests.forEach(g -> System.out.println(g.getId() + ": " + g.getFullName() + " - " + g.getPhone() + " - " + g.getEmail()));
    }

    private static void createBooking(HotelService service) {
        System.out.print("Room id: ");
        long roomId = Long.parseLong(SCANNER.nextLine());
        System.out.print("Guest id: ");
        long guestId = Long.parseLong(SCANNER.nextLine());
        System.out.print("Check-in (YYYY-MM-DD): ");
        LocalDate in = LocalDate.parse(SCANNER.nextLine());
        System.out.print("Check-out (YYYY-MM-DD): ");
        LocalDate out = LocalDate.parse(SCANNER.nextLine());
        Booking booking = service.createBooking(roomId, guestId, in, out);
        System.out.println("Created booking id=" + booking.getId() + ", total=$" + booking.getTotalAmount());
    }

    private static void listBookings(HotelService service) {
        List<Booking> bookings = service.listBookings();
        bookings.forEach(b -> System.out.println(b.getId() + ": room=" + b.getRoomId() + ", guest=" + b.getGuestId() + ", " + b.getCheckIn() + " -> " + b.getCheckOut() + ", $" + b.getTotalAmount() + ", " + b.getStatus()));
    }

    private static void addService(BillingService billing) {
        System.out.print("Service name: ");
        String name = SCANNER.nextLine();
        System.out.print("Description: ");
        String desc = SCANNER.nextLine();
        System.out.print("Unit price: ");
        double price = Double.parseDouble(SCANNER.nextLine());
        var s = billing.addService(name, desc, price);
        System.out.println("Created service id=" + s.getId());
    }

    private static void listServices(BillingService billing) {
        var list = billing.listServices();
        list.forEach(s -> System.out.println(s.getId() + ": " + s.getName() + " - $" + s.getUnitPrice()));
    }

    private static void addServiceToBooking(BillingService billing) {
        System.out.print("Booking id: ");
        long bookingId = Long.parseLong(SCANNER.nextLine());
        System.out.print("Service id: ");
        long serviceId = Long.parseLong(SCANNER.nextLine());
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(SCANNER.nextLine());
        var line = billing.addServiceToBooking(bookingId, serviceId, qty);
        System.out.println("Added line id=" + line.getId() + ", total=$" + line.getLineTotal());
    }

    private static void generateInvoice(BillingService billing) {
        System.out.print("Booking id: ");
        long bookingId = Long.parseLong(SCANNER.nextLine());
        var invoice = billing.generateInvoice(bookingId);
        System.out.println("Invoice id=" + invoice.getId() + ", total=$" + invoice.getTotal());
    }

    private static void recordPayment(BillingService billing) {
        System.out.print("Invoice id (optional, blank to skip): ");
        String invStr = SCANNER.nextLine().trim();
        Long invoiceId = invStr.isEmpty() ? null : Long.parseLong(invStr);
        System.out.print("Booking id (optional if invoice provided): ");
        String bkStr = SCANNER.nextLine().trim();
        Long bookingId = bkStr.isEmpty() ? null : Long.parseLong(bkStr);
        System.out.print("Method (CASH/CARD/ONLINE): ");
        String method = SCANNER.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(SCANNER.nextLine());
        System.out.print("Reference: ");
        String ref = SCANNER.nextLine();
        var p = billing.recordPayment(invoiceId, bookingId, method, amount, ref);
        System.out.println("Payment id=" + p.getId());
    }
}


