package ui;

import backend.UserManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ForgotPasswordUI extends JFrame {

    private UserManager userManager;

    public ForgotPasswordUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Reset Password");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Reset Password");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(99, 102, 241));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel(
                "<html><div style='text-align:center;'>Enter your username and choose a new password</div></html>"
        );
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoLabel.setForeground(new Color(156, 163, 175));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setMaximumSize(new Dimension(320, 40));

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        userLabel.setForeground(new Color(229, 231, 235));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        styleField(userField);

        // Password
        JLabel passLabel = new JLabel("New Password");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        passLabel.setForeground(new Color(229, 231, 235));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPasswordField passField = new JPasswordField();
        styleField(passField);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetBtn.setPreferredSize(new Dimension(140, 40));
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setBackground(new Color(99, 102, 241));
        resetBtn.setFocusPainted(false);
        resetBtn.setBorderPainted(false);
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        resetBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String newPass = new String(passField.getPassword());

            if (username.isEmpty() || newPass.isEmpty()) {
                showStyledDialog(this, "Fields cannot be empty","error", new Color(239, 68, 68));
                return;
            }

            if (userManager.resetPassword(username, newPass)) {
                showStyledDialog(this, "✓ Success", "Password reset successful", new Color(16, 185, 129));
                dispose();
                new LoginUI(userManager);
            } else {
                showStyledDialog(this,  "User not found", "Error", new Color(239, 68, 68));
            }
        });

        // Layout
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(infoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        panel.add(userLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(userField);

        panel.add(passLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(passField);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(resetBtn);

        add(panel);
        setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(31, 41, 55));
        field.setForeground(new Color(229, 231, 235));
        field.setCaretColor(new Color(229, 231, 235));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    // Same styled dialog as LoginUI
    private void showStyledDialog(Component parent, String title, String message, Color accentColor) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 18));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(18, 18, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(229, 231, 235));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(new EmptyBorder(15, 0, 0, 0));

        contentPanel.add(titleLabel);
        contentPanel.add(messageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(18, 18, 18));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(120, 40));
        okButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(accentColor);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(okButton);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
