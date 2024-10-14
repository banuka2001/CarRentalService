package RentACar;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Booking {
   SQLstatements sql = new SQLstatements();
   Connection connection = ConnectSQL.getConnection();

   Customer customer = new Customer();



   private int days;
   private int carID;

   //TO GET THE CURRENT DATE
    LocalDate currentDate = LocalDate.now();

    Scanner scanner = new Scanner(System.in);




   private Payments payments;

    // Object is passthrough a setter for decoupe from Payment class
   public void setPayments(Payments payments) {
       this.payments = payments;
   }
    public void addBooking ()  {

       try {
           // executing update customer method from Customer class
           customer.updateCustomers();

           Statement statement = connection.createStatement();
           statement.executeUpdate(sql.createBookingDetails());

           statement.close();

           int ID = customer.getLastCustomerId(); // getter to access last customerId
           System.out.println(ID);

           System.out.print("Select a car no for booking: ");
           carID = scanner.nextInt();

           updateCarTable();

           // get days from user
           System.out.print("How many days do you like to rent the car you selected: ");
           days = scanner.nextInt();

           PreparedStatement preparedStatement = connection.prepareStatement(sql.setBookingDetails());
           // update booking table
           setBookingDetailsTable(preparedStatement,currentDate, String.valueOf(getDueDate()), carID, ID);

           preparedStatement.close();
       } catch (SQLException | StopProgrammeException e) {
           throw new RuntimeException(e);
       }

    }

   private void updateCarTable() throws StopProgrammeException {

       try {
       // update cars list when a customer booked a car
           PreparedStatement prst = connection.prepareStatement(sql.updateCars());
            prst.setInt(1, carID);
       // Execute update query
       int rowsAffected = prst.executeUpdate();
       // Check if the update was successful
       if (rowsAffected > 0) {
           System.out.println("Car available");
       } else {
           System.out.println();
            throw new StopProgrammeException("Check the Car Id again and input correct input!");
       }
       prst.close();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

   //will get monthly rental cost of the selected car
    public double getCost() throws SQLException {

        double cost = 0;
       //get cost for selected car
       PreparedStatement prsta = connection.prepareStatement(sql.getCarRent());
       prsta.setInt(1, carID);
       prsta.executeQuery();
           ResultSet resultSet = prsta.executeQuery();
           while (resultSet.next()) {
                 cost = resultSet.getDouble("Cost");
           }

           prsta.close();
           resultSet.close();
       return cost;

   }


   private void setBookingDetailsTable (PreparedStatement preparedStatement,LocalDate currentDate,String dueDate, int carId, int customerId) throws SQLException {
       preparedStatement.setString(1, String.valueOf(currentDate));
       preparedStatement.setString(2,dueDate);

       preparedStatement.setInt(3,carId);
       preparedStatement.setInt(4,customerId);

       preparedStatement.executeUpdate();

   }
   private LocalDate getDueDate () {
        LocalDate dueDate = currentDate.plusDays(days);
        return dueDate;
   }



   public int getDays() {
        return days;
   }

   // will get last booking Id to help update payment table.

    public int getLastBookingID () throws SQLException {

        int bookingId = 0;
       PreparedStatement preparedStatement = connection.prepareStatement(sql.getBookingId());
       preparedStatement.setInt(1,carID);
       preparedStatement.executeQuery();

       ResultSet resultSet = preparedStatement.executeQuery();
       while (resultSet.next()) {
          bookingId = resultSet.getInt("Booking_id");
       }
       return bookingId;

   }

}
