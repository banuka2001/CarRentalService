package RentACar;

import java.sql.*;
import java.util.Scanner;

public class CarDetails {
    private Scanner scanner = new Scanner(System.in);
    Connection connection = ConnectSQL.getConnection();
    SQLstatements sql = new SQLstatements();

    Report report = new Report();

    public void saveExistingCars() {

        try {
            Statement statement = connection.createStatement();

            // Creating the table if it doesn't exist
            statement.executeUpdate(sql.createCarDetails());
            System.out.println("Table 'Car Owner' checked/created.");



            // Insert data into the table
            PreparedStatement preparedStatement = connection.prepareStatement(sql.setCarDetails());
// data set to update car details
            setCarDetails(preparedStatement, "Toyota", "Camry", 2022, 30000.00);
            setCarDetails(preparedStatement, "Honda", "Civic", 2021, 25000.00);
            setCarDetails(preparedStatement, "Ford", "Focus", 2020, 28000.00);
            setCarDetails(preparedStatement, "Chevrolet", "Malibu", 2019, 27000.00);
            setCarDetails(preparedStatement, "BMW", "3 Series", 2018, 45000.00);
            setCarDetails(preparedStatement, "Mercedes", "C-Class", 2022, 48000.00);
            setCarDetails(preparedStatement, "Audi", "A4", 2021, 47000.00);
            setCarDetails(preparedStatement, "Volkswagen", "Passat", 2020, 35000.00);
            setCarDetails(preparedStatement, "Nissan", "Altima", 2019, 22000.00);
            setCarDetails(preparedStatement, "Hyundai", "Elantra", 2018, 21000.00);
            setCarDetails(preparedStatement, "Kia", "Optima", 2022, 23000.00);
            setCarDetails(preparedStatement, "Mazda", "3", 2021, 24000.00);
            setCarDetails(preparedStatement, "Subaru", "Impreza", 2020, 26000.00);
            setCarDetails(preparedStatement, "Lexus", "ES", 2019, 40000.00);
            setCarDetails(preparedStatement, "Acura", "TLX", 2018, 38000.00);
            setCarDetails(preparedStatement, "Infiniti", "Q50", 2022, 42000.00);
            setCarDetails(preparedStatement, "Jaguar", "XE", 2021, 46000.00);
            setCarDetails(preparedStatement, "Porsche", "Macan", 2020, 49000.00);
            setCarDetails(preparedStatement, "Volvo", "S60", 2018, 47000.00);
            setCarDetails(preparedStatement, "Mitsubishi", "Lancer", 2022, 20000.00);
            setCarDetails(preparedStatement, "Chrysler", "300", 2021, 31000.00);
            setCarDetails(preparedStatement, "Jeep", "Cherokee", 2020, 34000.00);
            setCarDetails(preparedStatement, "Dodge", "Charger", 2019, 29000.00);
            setCarDetails(preparedStatement, "Ram", "1500", 2018, 33000.00);
            setCarDetails(preparedStatement, "Peugeot", "308", 2022, 28000.00);
            setCarDetails(preparedStatement, "Renault", "Megane", 2021, 27000.00);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExtraCars() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(sql.setCarDetails());

        while (true) {
            scanner.nextLine();

            System.out.print("Enter Brand Name (or type 'exit' to leave this section): ");
            String brandName = scanner.nextLine();

            if (brandName.equalsIgnoreCase("exit")) {

                report.generateReport();
                break;
            }

            System.out.print("Enter Model Name: ");
            String modelName = scanner.nextLine();

            System.out.print("Enter Model Year: ");
            int modelYear = scanner.nextInt();

            System.out.print("Enter Rent Cost per month: ");
            double cost = scanner.nextDouble();


            preparedStatement.setString(1, brandName);
            preparedStatement.setString(2, modelName);
            preparedStatement.setInt(3, modelYear);
            preparedStatement.setDouble(4, cost);

            preparedStatement.executeUpdate();
        }

    }

    private void setCarDetails (PreparedStatement preparedStatement, String brandName, String modelName, int modelYear, double cost) throws SQLException {
            preparedStatement.setString(1, brandName);
            preparedStatement.setString(2, modelName);
            preparedStatement.setInt(3, modelYear);
            preparedStatement.setDouble(4, cost);
        preparedStatement.executeUpdate();
    }

    public void getCarDetails() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.showCarDetails());

            System.out.println("Available Cars are: ");
// Refer this
            System.out.printf("%-20s | %-20s | %-10s | %-10s%n",
                    "Brand Name", "Model Name", "Model Year", "Rent Per Month");



            while (resultSet.next()) {
                    //Brand_Name, Model_Name, Model_Year, Cost
                int Car_id = resultSet.getInt("car_id");
                String brandName = resultSet.getString("Brand_Name");
                String modelName = resultSet.getString("Model_Name");
                int modelYear = resultSet.getInt("Model_Year");
                double cost = resultSet.getDouble("Cost");
                boolean Availability = resultSet.getBoolean("isAvailable");

                System.out.printf("%-10d | %-20s | %-20s | %-10d | $%-10.2f | %-10b%n",
                        Car_id, brandName, modelName, modelYear, cost, Availability);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
