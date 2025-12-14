package backend;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private int wins;
    private int losses;
    private int draws;
    private int points;
    private int l;

    public User(String username, String password) {
        this.username = username;
        this.password = "";
        l = password.length();
        for (int i = 0; i < l; i++) {
            char p = password.charAt(i);
            char u = username.charAt(i % username.length());
            this.password += (char)((p + u)*u);
        }
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.points = 0;
    }

    public String getUsername() { return username; }
public String getPassword() { return password; }    
    public String getdecoded() {  
        String decoded = "";
        for (int i = 0; i < l; i++) {
            char p = password.charAt(i);
            char u = username.charAt(i % username.length());
            decoded += (char)((p/u) - u);
        }
        return decoded;
    }
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