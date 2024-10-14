package RentACar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Report {
    //car name , car model , customer name, payment amount
    Connection connection = ConnectSQL.getConnection();
    SQLstatements sql = new SQLstatements();

    Scanner scanner = new Scanner(System.in);

    private String name;
    private double payment;
    private String number;
    private String dueDate;
    private String brand;
    private String model;

    public void generateReport() throws SQLException {

        System.out.println("Do you want to check Transaction Report: (yes/no)");
        String userInput = scanner.next();

        if (userInput.equalsIgnoreCase("yes")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.generateReport());

            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.println("------------------------------------------------ Car Rental Records------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-20s | %-20s | %-20s | %-15s | %-10s | %-10s%n",
                    "Car Brand", "Car Model", "Customer Name", "Number", "Payment Amount", "Due Date");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                brand = resultSet.getString("Car_Brand");

                model = resultSet.getString("Car_Model");

                name = resultSet.getString("Customer_Name");

                payment = resultSet.getDouble("Payment_Amount");
                number = resultSet.getString("number");
                dueDate = resultSet.getString("Due_date");

                // Print data rows (Make sure variables are properly assigned from result set or source)
                System.out.printf("%-20s | %-20s | %-20s | %-15s | %-10.2f | %-10s%n",
                        brand, model, name, number, payment, dueDate);

            }



        }




        }
    }






