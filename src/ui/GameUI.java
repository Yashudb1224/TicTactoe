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

    public GameUI(UserManager userManager, User currentUser) {
        this.userManager = userManager;
        this.currentUser = currentUser;
        this.gameManager = new GameManager();

        setTitle("Tic Tac Toe - Unbeatable AI");
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

        statusLabel = new JLabel("Your turn - Place X");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        statusLabel.setForeground(new Color(156, 163, 175));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headerPanel.add(titleLabel);
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
            new DashboardUI(userManager, currentUser);
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
                userManager.updateWin(currentUser);
                showStyledEnd("🎉 You Win!", "Congratulations! +3 points");
            } else {
                userManager.updateLoss(currentUser);
                showStyledEnd("😔 You Lose!", "Better luck next time! -1 point");
            }
            return true;
        }

        if (gameManager.isDraw()) {
            userManager.updateDraw(currentUser);
            showStyledEnd("🤝 Draw!", "Well played! +1 point");
            return true;
        }

        return false;
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

    private void showStyledEnd(String title, String message) {
        Timer timer = new Timer(1200, e -> {
            UIManager.put("OptionPane.background", new Color(31, 41, 55));
            UIManager.put("Panel.background", new Color(31, 41, 55));
            UIManager.put("OptionPane.messageForeground", new Color(229, 231, 235));
            
            int choice = JOptionPane.showOptionDialog(
                this,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Play Again", "Dashboard"},
                "Play Again"
            );

            if (choice == 0) {
                resetGame();
            } else {
                dispose();
                new DashboardUI(userManager, currentUser);
            }
        });
        timer.setRepeats(false);
        timer.start();
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