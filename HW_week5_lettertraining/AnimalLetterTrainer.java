package HW_week5_lettertraining;

import javax.swing.*;

public class AnimalLetterTrainer {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Preschool Letter Training");
        frame.setSize(420, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        String[] animalNames = {"Cat", "Duck", "Tiger"};
        String[] imagePaths = {
                "src/HW_week5_lettertraining/images/cat.jpg",
                "src/HW_week5_lettertraining/images/duck.jpg",
                "src/HW_week5_lettertraining/images/tiger.jpg"
        };
        int[] currentIndex = {0};

        JLabel questionLabel = new JLabel("What is the first letter of this animal?");
        questionLabel.setBounds(50, 30, 300, 30);
        frame.add(questionLabel);

        JTextField inputField = new JTextField();
        inputField.setBounds(50, 70, 100, 30);
        frame.add(inputField);

        JButton checkButton = new JButton("Check");
        checkButton.setBounds(160, 70, 80, 30);
        frame.add(checkButton);

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(250, 70, 80, 30);
        frame.add(nextButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(50, 120, 300, 30);
        frame.add(resultLabel);

        ImageIcon icon = new ImageIcon(imagePaths[currentIndex[0]]);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(100, 160, 200, 150);
        frame.add(imageLabel);

        checkButton.addActionListener(e -> {
            String input = inputField.getText().trim().toUpperCase();
            String answer = animalNames[currentIndex[0]].substring(0, 1).toUpperCase();
            if (input.equals(answer)) {
                resultLabel.setText("✅ Correct for " + animalNames[currentIndex[0]] + "!");
            } else {
                resultLabel.setText("❌ Try again!");
            }
        });

        nextButton.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % animalNames.length;
            inputField.setText("");
            resultLabel.setText("");
            questionLabel.setText("What is the first letter of this animal?");
            icon.setImage(new ImageIcon(imagePaths[currentIndex[0]]).getImage());
            imageLabel.repaint();
        });

        frame.setVisible(true);
    }
}
