package backend;

public class User {
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

    public void addWin() {
        wins++;
        points += 3;
    }

    public void addLoss() {
        losses++;
        points -= 1;
        if (points < 0) points = 0;
    }

    public void addDraw() {
        draws++;
        points += 1;
    }
}
