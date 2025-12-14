package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import backend.UserManager;
import backend.User;
import backend.GameManager;

public class GameUI extends JFrame {

    private JButton[] buttons = new JButton[9];
    private boolean playerTurn = true;

    private UserManager userManager;
    private User currentUser;
    private GameManager gameManager;
    private JLabel statusLabel;
    private String difficulty;

    public GameUI(UserManager userManager, User currentUser, String difficulty) {
        this.userManager = userManager;
        this.currentUser = currentUser;
        this.difficulty = difficulty;
        this.gameManager = new GameManager(difficulty);
        
        setTitle("Tic Tac Toe - " + difficulty + " Mode");
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
        headerPanel.setBorder(new EmptyBorder(20, 30, 10, 30));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(99, 102, 241));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel difficultyLabel = new JLabel(difficulty + " Mode • " + getPointsInfo());
        difficultyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        difficultyLabel.setForeground(getDifficultyAccentColor());
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyLabel.setBorder(new EmptyBorder(5, 0, 5, 0));

        statusLabel = new JLabel("Your turn - Place X");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        statusLabel.setForeground(new Color(156, 163, 175));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(difficultyLabel);
        headerPanel.add(statusLabel);

        // Game Grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3, 10, 10));
        gridPanel.setBackground(new Color(18, 18, 18));
        gridPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton("");
            btn.setFont(new Font("SansSerif", Font.BOLD, 60));
            btn.setFocusPainted(false);
            btn.setBackground(new Color(31, 41, 55));
            btn.setForeground(new Color(229, 231, 235));
            btn.setBorder(BorderFactory.createLineBorder(new Color(55, 65, 81), 2));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            int index = i;

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (btn.getText().equals("") && playerTurn) {
                        btn.setBackground(new Color(45, 55, 72));
                    }
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (btn.getText().equals("")) {
                        btn.setBackground(new Color(31, 41, 55));
                    }
                }
            });

            btn.addActionListener(e -> {
                if (btn.getText().equals("") && playerTurn) {
                    btn.setText("X");
                    btn.setForeground(new Color(99, 102, 241));
                    btn.setBackground(new Color(31, 41, 55));
                    playerTurn = false;
                    statusLabel.setText("Computer is thinking...");

                    gameManager.playerMove(index / 3, index % 3);

                    if (checkGame()) return;

                    Timer timer = new Timer(500, evt -> {
                        computerMove();
                        statusLabel.setText("Your turn - Place X");
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            });

            buttons[i] = btn;
            gridPanel.add(btn);
        }

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(18, 18, 18));
        bottomPanel.setBorder(new EmptyBorder(10, 30, 30, 30));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton resetBtn = createStyledButton("New Game", new Color(139, 92, 246), new Color(124, 58, 237));
        JButton backBtn = createStyledButton("Back", new Color(239, 68, 68), new Color(220, 38, 38));

        resetBtn.addActionListener(e -> resetGame());

        backBtn.addActionListener(e -> {
            dispose();
            new DifficultySelectionUI(userManager, currentUser);
        });

        bottomPanel.add(resetBtn);
        bottomPanel.add(backBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void computerMove() {
        int[] move = gameManager.computerMove();
        int index = move[0] * 3 + move[1];

        buttons[index].setText("O");
        buttons[index].setForeground(new Color(239, 68, 68));
        buttons[index].setBackground(new Color(31, 41, 55));
        playerTurn = true;

        checkGame();
    }

    private boolean checkGame() {
        char winner = gameManager.checkWinner();

        if (winner != ' ') {
            highlightWinner();
            if (winner == 'X') {
                int points = getWinPoints();
                userManager.updateWin(currentUser, points);
                showStyledDialog("🎉 Victory!", "Congratulations! You won!", "+" + points + " point" + (points > 1 ? "s" : ""), new Color(16, 185, 129));
            } else {
                userManager.updateLoss(currentUser);
                showStyledDialog("😢 Defeat", "Better luck next time!", "-1 point", new Color(239, 68, 68));
            }
            return true;
        }

        if (gameManager.isDraw()) {
            int points = getDrawPoints();
            userManager.updateDraw(currentUser, points);
            showStyledDialog("🤝 Draw!", "Well played!", points > 0 ? ("+" + points + " point" + (points > 1 ? "s" : "")) : "0 points", new Color(251, 191, 36));
            return true;
        }

        return false;
    }

    private int getWinPoints() {
        switch (difficulty) {
            case "Easy": return 1;
            case "Medium": return 3;
            case "Hard": return 5;
            default: return 3;
        }
    }

    private int getDrawPoints() {
        return 0; // No points for draw
    }

    private String getPointsInfo() {
        switch (difficulty) {
            case "Easy": return "Win: +1";
            case "Medium": return "Win: +3";
            case "Hard": return "Win: +5";
            default: return "";
        }
    }

    private Color getDifficultyAccentColor() {
        switch (difficulty) {
            case "Easy": return new Color(16, 185, 129);
            case "Medium": return new Color(251, 191, 36);
            case "Hard": return new Color(239, 68, 68);
            default: return new Color(99, 102, 241);
        }
    }

    private void highlightWinner() {
        Timer timer = new Timer(100, null);
        timer.addActionListener(e -> {
            for (JButton btn : buttons) {
                if (!btn.getText().equals("")) {
                    Color current = btn.getBackground();
                    if (current.equals(new Color(31, 41, 55))) {
                        btn.setBackground(new Color(55, 65, 81));
                    } else {
                        btn.setBackground(new Color(31, 41, 55));
                    }
                }
            }
        });
        timer.setRepeats(true);
        timer.start();

        Timer stopTimer = new Timer(1000, e -> timer.stop());
        stopTimer.setRepeats(false);
        stopTimer.start();
    }

    private void resetGame() {
        gameManager.resetBoard();
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setBackground(new Color(31, 41, 55));
            btn.setEnabled(true);
        }
        playerTurn = true;
        statusLabel.setText("Your turn - Place X");
    }

    private void showStyledDialog(String title, String message, String points, Color accentColor) {
        Timer timer = new Timer(1200, e -> {
            JDialog dialog = new JDialog(this, title, true);
            dialog.setSize(400, 280);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(false);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(new Color(18, 18, 18));
            mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

            // Content Panel
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(new Color(18, 18, 18));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
            titleLabel.setForeground(accentColor);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            messageLabel.setForeground(new Color(156, 163, 175));
            messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            messageLabel.setBorder(new EmptyBorder(15, 0, 10, 0));

            JLabel pointsLabel = new JLabel(points);
            pointsLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            pointsLabel.setForeground(accentColor);
            pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            contentPanel.add(titleLabel);
            contentPanel.add(messageLabel);
            contentPanel.add(pointsLabel);

            // Buttons Panel
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBackground(new Color(18, 18, 18));
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

            JButton playAgainBtn = createDialogButton("Play Again", new Color(99, 102, 241), new Color(79, 70, 229));
            JButton dashboardBtn = createDialogButton("Dashboard", new Color(55, 65, 81), new Color(75, 85, 99));

            playAgainBtn.addActionListener(ev -> {
                dialog.dispose();
                resetGame();
            });

            dashboardBtn.addActionListener(ev -> {
                dialog.dispose();
                dispose();
                new DashboardUI(userManager, currentUser);
            });

            buttonsPanel.add(playAgainBtn);
            buttonsPanel.add(dashboardBtn);

            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

            dialog.add(mainPanel);
            dialog.setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private JButton createDialogButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 45));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
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

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 45));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
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