package ui;

import backend.User;
import backend.UserManager;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DifficultySelectionUI extends JFrame {

    private UserManager userManager;
    private User currentUser;

    public DifficultySelectionUI(UserManager userManager, User currentUser) {
        this.userManager = userManager;
        this.currentUser = currentUser;

        setTitle("Tic Tac Toe - Select Difficulty");
        setSize(500, 600);
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

        JLabel titleLabel = new JLabel("Select Difficulty");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(99, 102, 241));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose your challenge level");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(156, 163, 175));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Difficulty Buttons Panel with ScrollPane
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(18, 18, 18));
        buttonsPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        // Easy Button
        JPanel easyCard = createDifficultyCard(
            "Easy",
            "",
            "",
            new Color(16, 185, 129),
            new Color(5, 150, 105)
        );

        // Medium Button
        JPanel mediumCard = createDifficultyCard(
            "Medium",
            "",
            "",
            new Color(251, 191, 36),
            new Color(245, 158, 11)
        );

        // Hard Button
        JPanel hardCard = createDifficultyCard(
            "Hard",
            "",
            "",
            new Color(239, 68, 68),
            new Color(220, 38, 38)
        );

        easyCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new GameUI(userManager, currentUser, "Easy");
            }
        });

        mediumCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new GameUI(userManager, currentUser, "Medium");
            }
        });

        hardCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new GameUI(userManager, currentUser, "Hard");
            }
        });

        buttonsPanel.add(easyCard);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(mediumCard);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(hardCard);

        // Wrap buttons panel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setBackground(new Color(18, 18, 18));
        scrollPane.getViewport().setBackground(new Color(18, 18, 18));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Bottom Panel with Back Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(18, 18, 18));
        bottomPanel.setBorder(new EmptyBorder(10, 40, 40, 40));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JButton backBtn = createStyledButton("Back to Dashboard", new Color(55, 65, 81), new Color(75, 85, 99));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        backBtn.addActionListener(e -> {
            dispose();
            new DashboardUI(userManager, currentUser);
        });

        bottomPanel.add(backBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createDifficultyCard(String difficulty, String description, String reward, Color bgColor, Color hoverColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.brighter(), 2),
            new EmptyBorder(15, 18, 15, 18)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(bgColor);

        JLabel titleLabel = new JLabel(difficulty);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setForeground(new Color(255, 255, 255, 200));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descLabel.setBorder(new EmptyBorder(4, 0, 4, 0));

        JLabel rewardLabel = new JLabel(reward);
        rewardLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        rewardLabel.setForeground(Color.WHITE);
        rewardLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(descLabel);
        textPanel.add(rewardLabel);

        card.add(textPanel, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(hoverColor);
                textPanel.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(bgColor);
                textPanel.setBackground(bgColor);
            }
        });

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