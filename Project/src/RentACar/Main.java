package RentACar;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("---------ATTENTION---------\nThis programme is Work On MySql Database Please carefully review and enter correct username and password of your local MySql Server.\n");

        // Prompt user for database credentials
        System.out.print("Enter username: ");
        String user = scanner.nextLine();

        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        // Set credentials and establish connection
        ConnectSQL.setCredentials(user, pass);
        ConnectSQL.establishConnection();

        try {
            System.out.println("\n\n---------------------------WELCOME to DriveAway Rentals---------------------------\n\n");
            System.out.println("---------------------------Enter 1 to go to Owner Access---------------------------");
            System.out.println("---------------------------Enter 2 if You are a Customer---------------------------\n");

            System.out.print("Enter > ");
            int input = scanner.nextInt();

            // To gain owner access
            switch (input) {
                case 1:
                   
        Owner owner = new Owner();
        owner.ownerAccess();
                    break;
                case 2:
                    Booking booking = new Booking();
                    Payments payments = new Payments();

                    // Set the dependencies
                    booking.setPayments(payments);
                    payments.setBooking(booking);

                    // Calculate the total rent
                    payments.updatePaymentsDetails();
                    break;
                default:
                    System.out.println("Wrong Input! Please check your Input");
                    break;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
