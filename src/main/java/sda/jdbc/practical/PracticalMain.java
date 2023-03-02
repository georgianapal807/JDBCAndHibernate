package sda.jdbc.practical;

import java.sql.*;

public class PracticalMain {

    private static final String databaseName = "sda_database";
    private static final String urlDB = "jdbc:mysql://localhost:3306/" + databaseName;
    private static final String userDB = "root";
    private static final String passwordDB = "Alisa290112";

    /*
        Task 1
        Using the JDBC API and any relational database (e.g. H2) make the following queries:

       -  create a table MOVIES with columns: id of type INTEGER AUTO INCREMENT,title of type VARCHAR (255),
        genre of type VARCHAR (255),yearOfRelease of type INTEGER. Note that a table named MOVIE may already exist.
        In that case, delete it.
       - add any three records to the MOVIES table
       - update one selected record (use the PreparedStatement)
       - delete selected record with specified id
       - display all other records in the database
     */

    public static void createMovieTable() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            try (Statement statement = con.createStatement()) {
                String tableSql = "CREATE TABLE IF NOT EXISTS movies" +
                        "(" +
                        "id int PRIMARY KEY AUTO_INCREMENT, " +
                        "title varchar(255)," +
                        "genre varchar(255), " +
                        "yearOfRelease INTEGER" +
                        ")";
                System.out.println("Execute sql query " + tableSql);
                statement.execute(tableSql);
                System.out.println("Table successfully created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovieTable() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            try (Statement statement = con.createStatement()) {
                String tableSql = "DROP TABLE IF EXISTS MOVIES";
                System.out.println("Execute sql query " + tableSql);
                statement.execute(tableSql);
                System.out.println("Table successfully deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMovie(String title, String genre, Integer yearOfRelease) {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            String sql = "INSERT INTO movies(title, genre, yearOfRelease) Values (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, genre);
                ps.setDouble(3, yearOfRelease);

                int affectedRows = ps.executeUpdate();
                System.out.println("Command successfully executed. Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMovieGenre(String title, String genre) {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            String sql = "UPDATE movies SET genre = ? WHERE title = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(2, title);
                ps.setString(1, genre);

                System.out.println("Execute update: " + sql);
                int affectedRows = ps.executeUpdate();

                System.out.println("Updated successfully executed. Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(int id) {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            String sql = "DELETE FROM movies WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                int affectedRows = ps.executeUpdate();
                System.out.println("Emp with empId = " + id + " was deleted.Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayAllMovies() {
        try (Connection con = DriverManager.getConnection(urlDB, userDB, passwordDB)) {
            try (Statement s = con.createStatement()) {
                String sql = "SELECT * FROM movies";
                try (ResultSet resultSet = s.executeQuery(sql)) {
                    while (resultSet.next()) {
                        int Id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String genre = resultSet.getString("genre");
                        Integer yearOfRelease = resultSet.getInt("yearOfRelease");

                        System.out.println("Movie -> id: " + Id + ", title: " + title + ", position: " + genre + ", salary: " + yearOfRelease);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void task1() {
        deleteMovieTable();
        createMovieTable();
        insertMovie("Lord of the Rings", "Fantasy", 2003);
        insertMovie("The Shawshank Redemption", "Drama", 1994);
        insertMovie("The Dark Knight", "Action", 2008);
        updateMovieGenre("The Dark Knight", "Crime");
        deleteMovie(2);
        displayAllMovies();

    }

    public static void main(String[] args) {
        task1();
    }
}
