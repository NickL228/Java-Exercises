package ProgrammingAssigment_2;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    public LoginWindow() {
        setTitle("Hangman - Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (LoginService.login(user, pass)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Login failed");
            }
        });

        registerBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (LoginService.register(user, pass)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}
