package RentACar;

import java.sql.*;

public class ConnectSQL {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static String userName;
    private static String password;
    private static Connection connection = null;

    SQLstatements sql = new SQLstatements();

    CarDetails carDetails = new CarDetails();
    public static void setCredentials(String user, String pass) {
        userName = user;
        password = pass;
    }

    public static void establishConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Registered!");

            connection = DriverManager.getConnection(JDBC_URL, userName, password);
            System.out.println("Connection successfully!");

            var sql = new SQLstatements();

            Statement statement = connection.createStatement();

            // Creating the database if it doesn't exist
            statement.executeUpdate(sql.setCreateDatabaseSQL());
            System.out.println("Database 'CarAgencyDB' checked/created.");

            // Connect to Rent a car database
            String jdbcURLWithDatabase = "jdbc:mysql://localhost:3306/CarAgencyDB";
            connection = DriverManager.getConnection(jdbcURLWithDatabase, userName, password);
            System.out.println("Connection to 'carRent' database established successfully!");

            ConnectSQL connectSQL = new ConnectSQL();
            connectSQL.checkAndRun();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkAndRun () throws SQLException {
        if (checkCarDetailTable()) {
            System.out.println("Checked/Car details Table Already Exist in the database!");
        } else {
            carDetails.saveExistingCars();
        }
    }

    private boolean checkCarDetailTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql.checkTableCarDetail());
        preparedStatement.setString(1,"CarAgencyDB");
        preparedStatement.setString(2,"CarDetails");

         ResultSet resultSet = preparedStatement.executeQuery();

       return resultSet.next();

         }




    public static Connection getConnection() {
        return connection;
    }


}

