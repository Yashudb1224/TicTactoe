package backend;

import java.util.*;
import backend.User;

public class UserManager {

    private ArrayList<User> users = new ArrayList<>();

    public boolean register(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; // already exists
            }
        }
        users.add(new User(username, password));
        return true;
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void updateWin(User user) {
        user.addWin();
    }

    public void updateLoss(User user) {
        user.addLoss();
    }

    public void updateDraw(User user) {
        user.addDraw();
    }

    public ArrayList<User> getRanking() {
        ArrayList<User> ranking = new ArrayList<>(users);

        ranking.sort((a, b) -> {
            if (b.getPoints() != a.getPoints())
                return b.getPoints() - a.getPoints(); // higher points first

            if (b.getWins() != a.getWins())
                return b.getWins() - a.getWins(); // more wins first

            if (a.getLosses() != b.getLosses())
                return a.getLosses() - b.getLosses(); // fewer losses first

            return a.getUsername().compareToIgnoreCase(b.getUsername());
        });

        return ranking;
    }
}
