Car Rental Service System

This is a Java-based Car Rental Management System that uses JDBC to connect to a MySQL database. The system provides both Owner and Customer access. It allows Owner  (Admins) to manage cars (add, delete), and Customers to rent cars with real-time price calculation based on rental duration. The system automatically creates the database if it doesn't exist on the first run.

Features
- User Access: Admins can securely log in and perform car management operations such as adding, updating, or deleting cars from the database.
- Customer Access: Customers can browse available cars, rent a car, and calculate the total cost based on the number of rental days.
- Automatic Database Creation: On first run, if no database exists, the system will automatically create a MySQL database and populate it.
- Pricing Logic: Calculates rental cost dynamically based on the car type and rental duration.
- Persistence: The data is stored in a MySQL database and is persisted between runs.

Technologies Used
-  Java
- JDBC API
- **MySQL**

How to Run
1. Clone this repository:
   	git clone https://github.com/banuka2001/CarRentalService.git



Notes:

- I used IntelliJ IDEA for this project.
-I have used JBCD API to connect this java application to Local MySQL server.
- In Order to run the application you need to enter your local MySQL database server username and password in the beginning of the program.
- For owner access password in section 1 use : "Hello" as password.

For more details refer Report file.

- I am planning to enhance this project by using Springboot,docker,Kubernates in near future.

@By Banuka Dilshan
