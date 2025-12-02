package ui;

import javax.swing.*;
import java.awt.*;
import backend.UserManager;
import backend.User;

public class DashboardUI extends JFrame {

    private UserManager userManager;
    private User currentUser;

    public DashboardUI(UserManager userManager, User currentUser) {
        this.userManager = userManager;
        this.currentUser = currentUser;

        setTitle("Dashboard - " + currentUser.getUsername());
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setBounds(130, 20, 300, 40);
        panel.add(title);

        JLabel welcome = new JLabel("Logged in as: " + currentUser.getUsername());
        welcome.setBounds(120, 70, 300, 20);
        panel.add(welcome);

        JButton playBtn = new JButton("Play Game");
        playBtn.setBounds(120, 130, 180, 40);
        playBtn.setBackground(new Color(0, 120, 215));
        playBtn.setForeground(Color.WHITE);
        playBtn.setFocusPainted(false);
        panel.add(playBtn);

        JButton rankBtn = new JButton("View Ranking");
        rankBtn.setBounds(120, 200, 180, 40);
        rankBtn.setBackground(new Color(0, 100, 160));
        rankBtn.setForeground(Color.WHITE);
        rankBtn.setFocusPainted(false);
        panel.add(rankBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 270, 180, 40);
        logoutBtn.setBackground(new Color(160, 20, 20));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        panel.add(logoutBtn);

        playBtn.addActionListener(e -> {
            dispose();
            new GameUI(userManager, currentUser);
        });

        rankBtn.addActionListener(e -> new RankingUI(userManager));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI(userManager);
        });

        add(panel);
        setVisible(true);
    }
}
