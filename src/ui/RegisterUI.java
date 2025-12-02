package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import backend.UserManager;

public class RegisterUI extends JFrame {

    private UserManager userManager;

    public RegisterUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Tic Tac Toe - Register");
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

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(99, 102, 241));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Join the game!");
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

        JButton registerBtn = createStyledButton("Create Account", new Color(16, 185, 129), new Color(5, 150, 105));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backBtn = createStyledButton("Back to Login", new Color(31, 41, 55), new Color(55, 65, 81));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());

            if (u.isEmpty() || p.isEmpty()) {
                showStyledMessage(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (u.length() < 3) {
                showStyledMessage(this, "Username must be at least 3 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (p.length() < 4) {
                showStyledMessage(this, "Password must be at least 4 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userManager.register(u, p)) {
                showStyledMessage(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginUI(userManager);
            } else {
                showStyledMessage(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginUI(userManager);
        });

        formPanel.add(userLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(passField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(registerBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        formPanel.add(backBtn);

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