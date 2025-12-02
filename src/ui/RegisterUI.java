package ui;

import javax.swing.*;
import java.awt.*;
import backend.UserManager;

public class RegisterUI extends JFrame {

    private UserManager userManager;

    public RegisterUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Register");
        setSize(380, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(null);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBounds(110, 20, 200, 30);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 90, 100, 25);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(140, 90, 180, 25);
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 150, 100, 25);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 150, 180, 25);
        panel.add(passField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(50, 230, 120, 35);
        registerBtn.setBackground(new Color(0, 150, 80));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        panel.add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200, 230, 120, 35);
        backBtn.setBackground(new Color(180, 20, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        panel.add(backBtn);

        registerBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields.");
                return;
            }

            if (userManager.register(u, p)) {
                JOptionPane.showMessageDialog(this, "Account created.");
                dispose();
                new LoginUI(userManager);
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginUI(userManager);
        });

        add(panel);
        setVisible(true);
    }
}
