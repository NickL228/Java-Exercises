package ProgrammingAssigment_2;

import java.sql.*;

public class LoginService {

    public static boolean register(String username, String password) {
        try (Connection conn = DBManager.connect()) {
            String checkSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            String insertSql = "INSERT INTO users (username, password, score) VALUES (?, ?, 0)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public static boolean login(String username, String password) {
        try (Connection conn = DBManager.connect()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    public static void updateScore(String username, int change) {
        try (Connection conn = DBManager.connect()) {
            String sql = "UPDATE users SET score = score + ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, change);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Score update error: " + e.getMessage());
        }
    }

    public static int getScore(String username) {
        try (Connection conn = DBManager.connect()) {
            String sql = "SELECT score FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
        } catch (SQLException e) {
            System.out.println("Get score error: " + e.getMessage());
        }
        return 0;
    }
}
