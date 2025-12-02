package backend;

import java.util.*;
import java.io.*;

public class UserManager {

    private ArrayList<User> users = new ArrayList<>();
    private final String FILE_PATH = "users.txt"; // file to save data

    public UserManager() {
        loadUsers(); // load users at startup
    }

    // Register new user
    public boolean register(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username))
                return false;
        }
        users.add(new User(username, password));
        saveUsers();
        return true;
    }

    // Login
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    // Stats updates
    public void updateWin(User user) {
        user.addWin(3); // +3 points
        saveUsers();
    }

    public void updateLoss(User user) {
        user.addLoss(); // -1 point, can go negative
        saveUsers();
    }

    public void updateDraw(User user) {
        user.addDraw(1); // +1 point
        saveUsers();
    }

    // Ranking: Points → Wins → Draws
    public ArrayList<User> getRanking() {
        ArrayList<User> ranking = new ArrayList<>(users);

        ranking.sort((a, b) -> {
            if (b.getPoints() != a.getPoints()) return b.getPoints() - a.getPoints();
            if (b.getWins() != a.getWins()) return b.getWins() - a.getWins();
            return b.getDraws() - a.getDraws();
        });

        return ranking;
    }

    // --- Save all users to file ---
    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.println(u.getUsername() + "," +
                               u.getPassword() + "," +
                               u.getWins() + "," +
                               u.getLosses() + "," +
                               u.getDraws() + "," +
                               u.getPoints());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Load users from file ---
    private void loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 6) continue;

                User user = new User(parts[0], parts[1]);
                user.setStats(
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]),
                    Integer.parseInt(parts[5])
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
