package RentACar;

import java.sql.*;
import java.util.Scanner;

public class Customer {
    Connection connection = ConnectSQL.getConnection();

    Scanner scanner = new Scanner(System.in);
    SQLstatements sql = new SQLstatements();
    CarDetails carDetails = new CarDetails();

    private String NIC;

    private int customer_id;


    public void updateCustomers() throws StopProgrammeException {

        try {


            Statement statement = connection.createStatement();
            statement.executeUpdate(sql.createCustomerDetails());
            System.out.println("Customer Table Created Successfully!");

            PreparedStatement preparedStatement = connection.prepareStatement(sql.setCustomerDetails());


                System.out.println("Do you want to check what cars are available (yes/no): ");
                String inputSelection = scanner.next();

                // view available cars
                if (inputSelection.equalsIgnoreCase("yes")) {
                    carDetails.getCarDetails();

                } else {

                    throw new StopProgrammeException("User choosed to exit!");
                }



                //  add customer details
                System.out.print("Do you want to rent out a car ? (yes/no)");
                  inputSelection = scanner.next();

                if (inputSelection.equalsIgnoreCase("yes")) {
                    collectCustomerDetails(scanner, preparedStatement);
                } else {
                    System.out.println("BYE! COME AGAIN");
                    throw new StopProgrammeException("User choosed to exit!");
                }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // data collection
        private void collectCustomerDetails(Scanner scanner, PreparedStatement preparedStatement) throws SQLException,StopProgrammeException {

        // clear input buffer
        scanner.nextLine();
        System.out.println("Enter your following details if you want to rent a car");
            System.out.print("Your first name: ");
                 String name = scanner.nextLine();
            System.out.println("Your current address: ");
                String address = scanner.nextLine();
            System.out.println("Your phone no: ");
                String phone = null;

                //Ensure user enters an integer as phone number
                    while (phone == null) {
                        try {
                            phone = scanner.nextLine();
                            Integer.parseInt(phone); // Check if phone is a valid integer
                        } catch (NumberFormatException e) {
                            throw new StopProgrammeException("Invalid phone number.");

                        }
                    }

            System.out.println("Your NIC: ");
                 NIC = scanner.nextLine();

                if (customerDetailsValidation(name,address,phone,NIC)) {
                    setCustomerDetailsTable(preparedStatement, name, address, phone,NIC);

                } else {
                    System.out.println("Invalid Inputs! Check again !!!");
                }

    }

    // validate user data
    private boolean customerDetailsValidation(String name, String address, String phone, String NIC) {
        return !name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !NIC.isEmpty();
    }

    private void setCustomerDetailsTable(PreparedStatement preparedStatement, String name, String address, String phone, String NIC) {

        while (true) {
            try {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, NIC);
                preparedStatement.executeUpdate();
                // System.out.println("Customer details inserted successfully!"); // used for debugging purposes
                break;
            } catch (SQLException e) {
                // Check for unique constraint violation by MySQL error code
                if (e.getErrorCode() == 1062) { // Error code 1062 is for a duplicate entry
                    System.out.println("Error: The NIC '" + NIC + "' already exists. Please enter a unique NIC.");
                    System.exit(1);
                } else {
                    e.printStackTrace();
                    break; // Exit loop
                }
            }
        }
    }

    public int getLastCustomerId () throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(sql.getCustomerIdQuery());
        preparedStatement.setString(1, NIC);


        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            customer_id = resultSet.getInt("Customer_id");
        } else {
            System.out.println("No customer ID found" + NIC);
        }
        // Close resources
        resultSet.close();
        preparedStatement.close();

        return customer_id;
    }




}

