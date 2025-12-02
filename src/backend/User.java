package backend;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private int wins;
    private int losses;
    private int draws;
    private int points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.points = 0;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public int getPoints() { return points; }

    public void addWin(int pointsToAdd) {
        wins++;
        points += pointsToAdd;
    }

    public void addLoss() {
        losses++;
        points -= 1;
    }

    public void addDraw(int pointsToAdd) {
        draws++;
        points += pointsToAdd;
    }

    public void setStats(int wins, int losses, int draws, int points) {
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.points = points;
    }
}
