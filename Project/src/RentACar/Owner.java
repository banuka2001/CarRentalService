package RentACar;

import java.sql.SQLException;
import java.util.Scanner;

public class Owner {
    CarDetails carDetails = new CarDetails();
    Report report = new Report();
    Scanner scanner = new Scanner(System.in);

    private final String password = "Hello";

    public void ownerAccess() throws SQLException {
        System.out.print("To Add new cars to the system enter the Owner Access Password: ");

        String input = scanner.nextLine();

        if (input.equals(password)) {
            System.out.println("Do you want to add Extra class to the database (Type: Add)\n ------ Or Do you want to check transaction records (Type: Check)");
            String execute = scanner.nextLine();
            if (execute.equalsIgnoreCase("add")) {
                carDetails.addExtraCars();
            } else if (execute.equalsIgnoreCase("check")) {
                report.generateReport();
            } else {
                System.out.println("Access Denied !!!");
            }
        }

    }
}
