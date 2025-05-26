package ProgrammingAssigment_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class GameWindow extends JFrame {
    private final String username;
    private final String word;
    private final Set<Character> guessed = new HashSet<>();
    private int mistakes = 0;
    private final int maxMistakes = 6;

    private JLabel wordLabel;
    private JTextField inputField;
    private JLabel statusLabel;

    public GameWindow(String username) {
        this.username = username;
        this.word = getRandomWord().toLowerCase();

        setTitle("Hangman Game");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        wordLabel = new JLabel(getMaskedWord(), SwingConstants.CENTER);
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 24));

        inputField = new JTextField();
        JButton guessButton = new JButton("Guess");

        statusLabel = new JLabel("Wrong attempts: 0/" + maxMistakes, SwingConstants.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(guessButton, BorderLayout.EAST);

        add(wordLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        guessButton.addActionListener(e -> handleGuess());
        inputField.addActionListener(e -> handleGuess());

        setVisible(true);
    }

    private void handleGuess() {
        String input = inputField.getText().toLowerCase();
        inputField.setText("");

        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) return;

        char letter = input.charAt(0);
        if (guessed.contains(letter)) return;

        guessed.add(letter);
        if (word.indexOf(letter) == -1) {
            mistakes++;
        }

        wordLabel.setText(getMaskedWord());
        statusLabel.setText("Wrong attempts: " + mistakes + "/" + maxMistakes);

        if (getMaskedWord().equals(word)) {
            JOptionPane.showMessageDialog(this, "You won!");
            saveScoreAndExit();
        } else if (mistakes >= maxMistakes) {
            JOptionPane.showMessageDialog(this, "You lost! Word was: " + word);
            saveScoreAndExit();
        }
    }

    private void saveScoreAndExit() {
        int score = (maxMistakes - mistakes) * 10;
        DBManager.updateScore(username, score);
        showEndOptions();
    }

    private void showEndOptions() {
        int choice = JOptionPane.showOptionDialog(this, "What would you like to do?", "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Play Again", "Show Leaderboard", "Logout"}, null);

        if (choice == 0) {
            dispose();
            new GameWindow(username);
        } else if (choice == 1) {
            showLeaderboard();
            showEndOptions();
        } else {
            dispose();
            new LoginFrame();
        }
    }

    private void showLeaderboard() {
        try (Connection conn = DBManager.connect()) {
            String sql = "SELECT username, score FROM users ORDER BY score DESC LIMIT 5";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder("Top 5 Users:\n");
            while (rs.next()) {
                sb.append(rs.getString("username")).append(" - ").append(rs.getInt("score")).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving leaderboard.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (i == 0 || i == word.length() - 1 || guessed.contains(c)) {
                sb.append(c);
            } else {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    private String getRandomWord() {
        String[] words = {"hangman", "elephant", "banana", "developer", "holiday"};
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }
}
