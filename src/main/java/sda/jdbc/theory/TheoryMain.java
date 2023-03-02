package sda.jdbc.theory;

import java.sql.*;

public class TheoryMain {
    private static final String databaseName = "sda_database";
    private static final String urlDB = "jdbc:mysql://localhost:3306/" + databaseName;
    private static final String userDB = "root";
    private static final String passwordDB = "Alisa290112";

    /*
     * CREATE DATABASE sda_database;
     * CREATE USER 'sda_user'@'localhost' IDENTIFIED BY 'sda_pass';
     * GRANT ALL on sda_database.* TO 'sda_user';
     */

    // Connectivity
    public static void connectToDB() {
        //Folosim DriverManager sa obtinem conexiunea
        //Include si inchiderea conexiunii

        try (Connection con = DriverManager.getConnection(urlDB,
                userDB, passwordDB)) {
            System.out.println("Successfully connected to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Execute SQL command - Create table
    public static void executeStatement() {
        try (Connection con = DriverManager.getConnection(urlDB,
                userDB, passwordDB)) {
            try (Statement statement = con.createStatement()) {
                String tableSql = "CREATE TABLE IF NOT EXISTS employees" +
                        "(" +
                        "emp_id int PRIMARY KEY AUTO_INCREMENT, " +
                        "name varchar(30)," +
                        "position varchar(30), " +
                        "salary double" +
                        ")";

                System.out.println("Execute sql query: " + tableSql);

                statement.execute(tableSql);

                System.out.println("Successfully table created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertToDB() {
        try (Connection con = DriverManager.getConnection(urlDB,
                userDB, passwordDB)) {
            try (Statement statement = con.createStatement()) {
                String tableSql = "INSERT INTO employees (name, position, salary) VALUES ('Riza Ioana', 'Jr. Accountant', 1550)";
                String tableSql1 = "INSERT INTO employees (name, salary) VALUES ('Giurge Nicoleta', 4000)";
                String tableSql2 = "INSERT INTO employees (name, position) VALUES ('Pop Andrei', 'Manager Account')";

                System.out.println("Execute sql query: " + tableSql);
                System.out.println("Execute sql query: " + tableSql1);
                System.out.println("Execute sql query: " + tableSql2);

                statement.executeUpdate(tableSql);
                statement.executeUpdate(tableSql1);
                statement.executeUpdate(tableSql2);

                System.out.println("Successfully values inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertToDBWithPARAMS(String name, String position, double salary) {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            String sql = "INSERT INTO employees(name, position, salary) Values (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, position);
                ps.setDouble(3, salary);

                int affectedRows = ps.executeUpdate();
                System.out.println("Command successfully executed. Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSalaryForEmp(int empId, double salary) {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            String sql = "UPDATE employees SET salary = ? WHERE emp_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDouble(1, salary);
                ps.setInt(2, empId);
                System.out.println("Execute update: " + sql);
                int affectedRows = ps.executeUpdate();
                System.out.println("Updated successfully executed. Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromDB() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            try (Statement statement = con.createStatement()) {
                String tableSql = "DELETE FROM employees WHERE salary=3000";

                System.out.println("Execute sql query: " + tableSql);
                int rows = statement.executeUpdate(tableSql); //nr de linii afectate
                System.out.println("Successfully " + rows + " values deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(int empId) {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            String sql = "DELETE FROM employees WHERE emp_id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, empId);
                int affectedRows = ps.executeUpdate();
                System.out.println("Emp with empId = " + empId + " was deleted.Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectAllFromTable() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) { //cream obj con - unde stabilim conexiunea cu mysql
            try (Statement s = con.createStatement()) { //s obj nou de tip statement
                String sql = "SELECT * FROM employees"; //instructiune sql
                try (ResultSet resultSet = s.executeQuery(sql)) { //folosesc statement sa execut instructiunea sql la DB si s.executeQuesry o sa intoarca un obiect de tip ResultSet
                    while (resultSet.next()) {
                        int empId = resultSet.getInt("emp_id");
                        String name = resultSet.getString("name");
                        String position = resultSet.getString("position");
                        Double salary = resultSet.getDouble("salary");

                        System.out.println("Emp -> emp_id: " + empId + ", name: " + name + ", position: " + position + ", salary: " + salary);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeTransactionsCommit() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            con.setAutoCommit(false);
            String firstUpdate = "UPDATE employees SET salary = 0 WHERE emp_id = 3";
            String secondUpdate = "UPDATE employees SET salary = 10 WHERE emp_id = 4";

            try (Statement statement = con.createStatement()) {
                System.out.println("Execute first update: " + firstUpdate);
                statement.executeUpdate(firstUpdate);

                System.out.println("Execute second update: " + secondUpdate);
                statement.executeUpdate(secondUpdate);
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executTransactionRollBack() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            con.setAutoCommit(false);
            String firstUpdate = "UPDATE employees SET salary = 0 WHERE emp_id = 6";
            String secondUpdate = "UPDATE employees SET salary = 10 WHERE emp_id = 7";
            try (Statement statement = con.createStatement()) {
                System.out.println("Execute first update: " + firstUpdate);
                int firstUpdateAffectedRows = statement.executeUpdate(firstUpdate);
                System.out.println("First update affected " + firstUpdateAffectedRows + " rows.");
                System.out.println("Execute second update: " + secondUpdate);
                int secondUpdateAffectedRow = statement.executeUpdate(secondUpdate);
                System.out.println("Second update affected " + secondUpdateAffectedRow + " rows.");
            }
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//  Here we have one function which return the value of the given employee salary
    public static double selectSalaryOfEmpFromTable(int emp_Id) {
        Double salary = 0d;
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) { //cream obj con - unde stabilim conexiunea cu mysql
            try (Statement s = con.createStatement()) { //s obj nou de tip statement
                String sql = "SELECT * FROM employees WHERE emp_id =" + emp_Id; //instructiune sql
                try (ResultSet resultSet = s.executeQuery(sql)) { //folosesc statement sa execut instructiunea sql la DB si s.executeQuesry o sa intoarca un obiect de tip ResultSet
                    while (resultSet.next()) {
                        salary = resultSet.getDouble("salary");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salary;
    }
// Here we update the salary of two given employees with the specified amount. Rules of updated were given by trainer.
    public static void updateSalaryTransaction(int emp1, int emp2, int amount) {
        if (selectSalaryOfEmpFromTable(emp1) - amount >= 0) {
            updateSalaryForEmp(emp1, selectSalaryOfEmpFromTable(emp1) - amount);
            updateSalaryForEmp(emp2, selectSalaryOfEmpFromTable(emp2) + amount);
        } else {
            System.out.println("The amount exceeds the employee's available salary");
        }
    }

    public static void main(String[] args) {

//        connectToDB();
//        executeStatement();
//        insertToDB();
//        deleteFromDB();
//        insertToDBWithPARAMS("Sorin", "Dev", 8000);
//        System.out.println("Before");
//        selectAllFromTable();
//        System.out.println("After");
//        updateSalaryForEmp(1,6000);
//        deleteEmployee(1);
//        selectAllFromTable();
//        selectAllFromTable();
//        executeTransactionsCommit();
//        executTransactionRollBack();
//        selectAllFromTable();
        System.out.println("Salar initial emp 7: " + selectSalaryOfEmpFromTable(7));
        System.out.println("Salar initial emp 6: " + selectSalaryOfEmpFromTable(6));
        updateSalaryTransaction(7, 6, 500);
        System.out.println("Salar final emp 7: " + selectSalaryOfEmpFromTable(7));
        System.out.println("Salar final emp 6: " + selectSalaryOfEmpFromTable(6));
    }
}
