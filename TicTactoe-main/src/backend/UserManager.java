package backend;

import java.util.*;
import java.sql.*;

public class UserManager {

    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {
        loadUsers();
    }

    // Register new user
    public boolean register(String username, String password) {
        // check in memory first
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username))
                return false;
        }

        User newUser = new User(username, password);

        String sql = """
            INSERT INTO users (username, password, wins, losses, draws, points)
            VALUES (?, ?, 0, 0, 0, 0)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getPassword());
            ps.executeUpdate();

            users.add(newUser);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login
    public User login(String username, String decoded) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            // encrypt entered password using your existing logic
            User temp = new User(username, decoded);

            if (!rs.getString("password").equals(temp.getPassword()))
                return null;

            User u = new User(username, decoded);
            u.setStats(
                rs.getInt("wins"),
                rs.getInt("losses"),
                rs.getInt("draws"),
                rs.getInt("points")
            );

            // keep memory list in sync
            users.add(u);
            return u;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Stats updates
    public void updateWin(User user, int points) {
        user.addWin(points);

        String sql = "UPDATE users SET wins = wins + 1, points = points + ? WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, points);
            ps.setString(2, user.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLoss(User user) {
        user.addLoss();

        String sql = "UPDATE users SET losses = losses + 1, points = points - 1 WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDraw(User user, int points) {
        user.addDraw(points);

        String sql = "UPDATE users SET draws = draws + 1, points = points + ? WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, points);
            ps.setString(2, user.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ranking: Points → Wins → Draws
    public ArrayList<User> getRanking() {
        ArrayList<User> ranking = new ArrayList<>();

        String sql = """
            SELECT * FROM users
            ORDER BY points DESC, wins DESC, draws DESC
        """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("password"));
                u.setStats(
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("points")
                );
                ranking.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ranking;
    }

    // Load users at startup
    private void loadUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User(
                    rs.getString("username"),
                    rs.getString("password")
                );
                u.setStats(
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("points")
                );
                users.add(u);
            }

        } catch (SQLException e) {
            // database might not exist yet — that's okay on first run
        }
    }

    // Reset password
    public boolean resetPassword(String username, String newPassword) {

    String sql = "UPDATE users SET password = ? WHERE username = ?";

    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        User temp = new User(username, newPassword);

        ps.setString(1, temp.getPassword());
        ps.setString(2, username);

        int updated = ps.executeUpdate();
        return updated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
