package ProgrammingAssigment_2;

import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/hangman_game";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void updateScore(String username, int newScore) {
        String sql = "UPDATE users SET score = ? WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newScore);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
