package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import backend.UserManager;
import backend.User;

public class LoginUI extends JFrame {

    private UserManager userManager;

    public LoginUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Tic Tac Toe - Login");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 18));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(18, 18, 18));
        headerPanel.setBorder(new EmptyBorder(40, 40, 20, 40));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(99, 102, 241));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Welcome back!");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(156, 163, 175));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(18, 18, 18));
        formPanel.setBorder(new EmptyBorder(20, 40, 40, 40));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        userLabel.setForeground(new Color(229, 231, 235));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField userField = createStyledTextField();
        
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        passLabel.setForeground(new Color(229, 231, 235));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passLabel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPasswordField passField = createStyledPasswordField();

        JButton loginBtn = createStyledButton("Login", new Color(99, 102, 241), new Color(79, 70, 229));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton regBtn = createStyledButton("Create Account", new Color(31, 41, 55), new Color(55, 65, 81));
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());

            if (u.isEmpty() || p.isEmpty()) {
                showStyledMessage(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = userManager.login(u, p);

            if (user != null) {
                showStyledMessage(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new DashboardUI(userManager, user);
            } else {
                showStyledMessage(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        regBtn.addActionListener(e -> {
            dispose();
            new RegisterUI(userManager);
        });

        formPanel.add(userLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(passField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(loginBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        formPanel.add(regBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setPreferredSize(new Dimension(300, 45));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(31, 41, 55));
        field.setForeground(new Color(229, 231, 235));
        field.setCaretColor(new Color(229, 231, 235));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setPreferredSize(new Dimension(300, 45));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(31, 41, 55));
        field.setForeground(new Color(229, 231, 235));
        field.setCaretColor(new Color(229, 231, 235));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(300, 45));
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

    private void showStyledMessage(Component parent, String message, String title, int messageType) {
        UIManager.put("OptionPane.background", new Color(31, 41, 55));
        UIManager.put("Panel.background", new Color(31, 41, 55));
        UIManager.put("OptionPane.messageForeground", new Color(229, 231, 235));
        JOptionPane.showMessageDialog(parent, message, title, messageType);
    }
}