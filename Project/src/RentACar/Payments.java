package RentACar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Payments {


        private int days;
        private double totalCost;

        private Booking booking;

        Connection connection = ConnectSQL.getConnection();
        SQLstatements sql = new SQLstatements();


    // Object is pass through a setter for decouple from Booking class
        public void setBooking (Booking booking) {
            this.booking = booking;
        }

        private void calculateTotalRent () throws SQLException {
            // make sure the addbooking method run before calculation part.
            booking.addBooking();
                double cost = booking.getCost();
                days = booking.getDays();

                double costPerDay = cost / 30;

            int months = calculateMonths();
            int remainingDays = calculateDays();

            if (months >= 2) {
                double discount = 12.5;
                double rentCost = (cost * months) + (costPerDay * remainingDays) ;
                totalCost = Math.round(rentCost * (100 - discount)/100);
                 System.out.println("Your payment amount is: "+ totalCost);
            } else {
                double rentCost = (cost * months) + (costPerDay * remainingDays);
                System.out.println("Your payment amount is: " + rentCost);
            }


            }

            private int calculateMonths() {
                    int temporary = days;
                    int months = 0;
                while (temporary >= 30) {
                        temporary -= 30;
                        months += 1;
            }
                return months;
            }
        private int calculateDays() {
        int remainingDays = days - (calculateMonths()*30);
        return remainingDays;
        }


        public void updatePaymentsDetails() throws SQLException {
            calculateTotalRent();

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql.createPaymentDetails());

            int bookingId = booking.getLastBookingID();

            PreparedStatement preparedStatement = connection.prepareStatement(sql.setPaymentDetails());
            setPaymentDetailsTable(preparedStatement,bookingId, totalCost);
        }

        private void setPaymentDetailsTable(PreparedStatement preparedStatement, int bookingId, double totalCost) throws SQLException {
            preparedStatement.setInt(1,bookingId);
            preparedStatement.setDouble(2,totalCost);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }



        public double getTotalCost () {
            return totalCost;
        }
}

