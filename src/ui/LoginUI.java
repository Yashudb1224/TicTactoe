package ui;

import javax.swing.*;
import java.awt.*;
import backend.UserManager;
import backend.User;

public class LoginUI extends JFrame {

    private UserManager userManager;

    public LoginUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Login");
        setSize(380, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(null);

        JLabel title = new JLabel("Welcome Back!");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBounds(110, 20, 200, 30);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 80, 100, 25);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(140, 80, 180, 25);
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 130, 100, 25);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 130, 180, 25);
        panel.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 200, 120, 35);
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        panel.add(loginBtn);

        JButton regBtn = new JButton("Register");
        regBtn.setBounds(200, 200, 120, 35);
        regBtn.setBackground(new Color(60, 60, 60));
        regBtn.setForeground(Color.WHITE);
        regBtn.setFocusPainted(false);
        panel.add(regBtn);

        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            User user = userManager.login(u, p);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new DashboardUI(userManager, user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        regBtn.addActionListener(e -> {
            dispose();
            new RegisterUI(userManager);
        });

        add(panel);
        setVisible(true);
    }
}
