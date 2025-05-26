package ProgrammingAssigment_2;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Hangman - Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginButton);
        add(registerButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (LoginService.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new GameWindow(username).setVisible(true);;
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        registerButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (LoginService.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            }
        });

        setVisible(true);
    }
}
