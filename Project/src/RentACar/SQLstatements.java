package RentACar;

public class SQLstatements {

    public String setCreateDatabaseSQL() {
        return "CREATE DATABASE IF NOT EXISTS CarAgencyDB";
    }

    // Create Table queries
    public String createCarDetails () {
        return "CREATE TABLE IF NOT EXISTS CarDetails (" +
                "    Car_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "    Brand_Name VARCHAR(50), " +
                "    Model_Name VARCHAR(50)," +
                "    Model_Year INT, " +
                "    Cost DECIMAL(10, 2), " +
                "    isAvailable BOOLEAN DEFAULT TRUE " +
                ") ";
    }


    public String createCustomerDetails  () {
        return "CREATE TABLE IF NOT EXISTS CustomerDetails ( " +
                "    Customer_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "    Name VARCHAR(100), " +
                "    Address VARCHAR(255), " +
                "    Phone_number CHAR(10), " +
                "    NIC VARCHAR(15) UNIQUE" +
                ") ";
    }

    public String createBookingDetails  () {
        return "CREATE TABLE IF NOT EXISTS BookingDetails (" +
                "    Booking_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "    Booking_date DATE, " +
                "    Due_date DATE, " +
                "    Car_id INT, " +
                "    Customer_id INT, " +
                "    FOREIGN KEY (Car_id) REFERENCES CarDetails(Car_id), " +
                "    FOREIGN KEY (Customer_id) REFERENCES CustomerDetails(Customer_id) " +
                ") ";
    }

    public String createPaymentDetails   () {
        return "CREATE TABLE IF NOT EXISTS PaymentDetails ( " +
                "    Payment_id INT PRIMARY KEY AUTO_INCREMENT , " +
                "    Booking_id INT , " +
                "    Total_Payment DECIMAL(10, 2), " +
                "    FOREIGN KEY (Booking_id) REFERENCES BookingDetails(Booking_id) " +
                ") ";
    }


    //Add table details statements
        public String setCarDetails() {
            return "INSERT INTO CarDetails (Brand_Name, Model_Name, Model_Year, Cost) VALUES (?, ?, ?, ?)";
    }

    public String setCustomerDetails () {

        return "INSERT INTO CustomerDetails (Name, Address, Phone_number, NIC) VALUES (?, ?, ?, ?)";
    }
    public String setBookingDetails () {
        return "INSERT INTO BookingDetails (Booking_date, Due_date, Car_id, Customer_id) VALUES (?, ?, ?, ?)";
    }

    public String setPaymentDetails () {
        return "INSERT INTO PaymentDetails (Booking_id, Total_Payment) VALUES (?, ?)";
    }


    // Show Table data queries
        public String showCarDetails() {
            return "SELECT * FROM CarDetails WHERE isAvailable = 1";
    }
        public String showCustomerDetails() {
            return "SELECT * FROM CustomerDetails";
        }
        public String showBookingDetails() {
            return "SELECT * FROM BookingDetails";
        }

        public String showPaymentDetails() {
            return "SELECT * FROM PaymentDetails";
        }

        //Check if CarDetail table already exist or not
    public String checkTableCarDetail() {
        return "SELECT 1 FROM information_schema.tables WHERE table_schema = ? AND table_name = ? LIMIT 1";
    }

        //Table clear queries.
        public String clearCarDetails() {
            return "DELETE FROM CarDetails";
        }

        public String clearBookingDetails() {
            return "DELETE FROM BookingDetails";
        }

        public String clearCustomerDetails() {
            return "DELETE FROM CustomerDetails";
        }

        //update cars

        public String updateCars() {

            return "UPDATE CarDetails SET isAvailable = 0 WHERE Car_id = ? AND isAvailable = 1";
        }


    public String generateReport() {
        return "SELECT " +
                "    CarDetails.Brand_Name AS Car_Brand, " +
                "    CarDetails.Model_Name AS Car_Model, " +
                "    CustomerDetails.Name AS Customer_Name, " +
                "    PaymentDetails.Total_Payment AS Payment_Amount, " +
                "    BookingDetails.Due_date AS Due_Date, " +
                " CustomerDetails.Phone_number AS number " +
                "FROM " +
                "BookingDetails " +
                "JOIN " +
                "CarDetails ON BookingDetails.Car_id = CarDetails.Car_id " +
                "JOIN " +
                "CustomerDetails ON BookingDetails.Customer_id = CustomerDetails.Customer_id " +
                "JOIN " +
                "PaymentDetails ON BookingDetails.Booking_id = PaymentDetails.Booking_id;";
    }

    // getters

    public String getCustomerIdQuery() {

        return "SELECT Customer_id FROM CustomerDetails WHERE NIC = ?";
    }

    public String getCarRent() {
        return "SELECT Cost FROM CarDetails WHERE Car_id = ?";
    }

    public String getBookingId() {
        return "SELECT Booking_id FROM BookingDetails WHERE Car_id = ?";
    }

}
