package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import backend.UserManager;
import backend.User;

public class DashboardUI extends JFrame {

    private UserManager userManager;
    private User currentUser;

    public DashboardUI(UserManager userManager, User currentUser) {
        this.userManager = userManager;
        this.currentUser = currentUser;

        setTitle("Tic Tac Toe - Dashboard");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 18));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(18, 18, 18));
        headerPanel.setBorder(new EmptyBorder(30, 40, 20, 40));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(99, 102, 241));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(156, 163, 175));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 2, 15, 15));
        statsPanel.setBackground(new Color(18, 18, 18));
        statsPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        statsPanel.add(createStatCard("Wins", String.valueOf(currentUser.getWins()), new Color(16, 185, 129)));
        statsPanel.add(createStatCard("Losses", String.valueOf(currentUser.getLosses()), new Color(239, 68, 68)));
        statsPanel.add(createStatCard("Draws", String.valueOf(currentUser.getDraws()), new Color(251, 191, 36)));
        statsPanel.add(createStatCard("Points", String.valueOf(currentUser.getPoints()), new Color(99, 102, 241)));

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(18, 18, 18));
        buttonsPanel.setBorder(new EmptyBorder(10, 40, 40, 40));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        JButton playBtn = createStyledButton("Play Game", new Color(99, 102, 241), new Color(79, 70, 229));
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton rankBtn = createStyledButton("View Ranking", new Color(139, 92, 246), new Color(124, 58, 237));
        rankBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logoutBtn = createStyledButton("Logout", new Color(239, 68, 68), new Color(220, 38, 38));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // UPDATED: Now opens difficulty selection instead of going directly to game
        playBtn.addActionListener(e -> {
            dispose();
            new DifficultySelectionUI(userManager, currentUser);
        });

        rankBtn.addActionListener(e -> new RankingUI(userManager));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI(userManager);
        });

        buttonsPanel.add(playBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        buttonsPanel.add(rankBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        buttonsPanel.add(logoutBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createStatCard(String label, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(31, 41, 55));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelText.setForeground(new Color(156, 163, 175));

        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("SansSerif", Font.BOLD, 32));
        valueText.setForeground(accentColor);

        card.add(labelText, BorderLayout.NORTH);
        card.add(valueText, BorderLayout.CENTER);

        return card;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(350, 50));
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}