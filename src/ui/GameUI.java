package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import backend.UserManager;
import backend.User;

public class GameUI extends JFrame {

    private JButton[] buttons = new JButton[9];
    private boolean playerTurn = true; // player = X, computer = O

    private UserManager userManager;
    private User currentUser;

    private Random random = new Random();

    public GameUI(UserManager userManager, User currentUser) {
        this.userManager = userManager;
        this.currentUser = currentUser;

        setTitle("Tic Tac Toe");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBackground(Color.WHITE);

        JLabel label = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        main.add(label, BorderLayout.NORTH);

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(3, 3));
        grid.setBackground(Color.WHITE);

        Font font = new Font("SansSerif", Font.BOLD, 50);

        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton("");
            btn.setFont(font);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(230, 230, 230));

            int index = i;

            btn.addActionListener(e -> {
                if (btn.getText().equals("") && playerTurn) {
                    btn.setText("X");
                    playerTurn = false;

                    if (checkGame()) return;

                    computerMove();
                }
            });

            buttons[i] = btn;
            grid.add(btn);
        }

        main.add(grid, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(new Color(160, 20, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);

        backBtn.addActionListener(e -> {
            dispose();
            new DashboardUI(userManager, currentUser);
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(backBtn);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);
        setVisible(true);
    }

    private void computerMove() {
        int index;

        do {
            index = random.nextInt(9);
        } while (!buttons[index].getText().equals(""));

        buttons[index].setText("O");

        playerTurn = true;

        checkGame();
    }

    private boolean checkGame() {
        String winner = getWinner();

        if (winner != null) {
            if (winner.equals("X")) {
                userManager.updateWin(currentUser);
                showEnd("You Win! +3 points");
            } else {
                userManager.updateLoss(currentUser);
                showEnd("You Lose! -1 point");
            }
            return true;
        }

        // Draw condition
        if (isBoardFull()) {
            userManager.updateDraw(currentUser);
            showEnd("Draw! +1 point");
            return true;
        }

        return false;
    }

    private void showEnd(String message) {
        JOptionPane.showMessageDialog(this, message);

        dispose();
        new DashboardUI(userManager, currentUser);
    }

    private boolean isBoardFull() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    private String getWinner() {
        int[][] winConditions = {
                {0,1,2},{3,4,5},{6,7,8}, // rows
                {0,3,6},{1,4,7},{2,5,8}, // cols
                {0,4,8},{2,4,6}          // diagonals
        };

        for (int[] wc : winConditions) {
            String a = buttons[wc[0]].getText();
            String b = buttons[wc[1]].getText();
            String c = buttons[wc[2]].getText();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                return a;
            }
        }
        return null;
    }
}
