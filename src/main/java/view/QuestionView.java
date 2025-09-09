package view;

import javax.swing.*;
import java.awt.*;

public class QuestionView extends JPanel {
    private JLabel questionLabel;
    private JButton[] optionButtons;

    public QuestionView() {
        setLayout(new BorderLayout());
        questionLabel = new JLabel("Question");
        add(questionLabel, BorderLayout.NORTH);
    }

    public void showQuestion(String questionText) {
        questionLabel.setText(questionText);
    }

    public void displayOptions(String[] options) {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(options.length, 1));
        optionButtons = new JButton[options.length];
        for (int i = 0; i < options.length; i++) {
            optionButtons[i] = new JButton(options[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public JButton[] getOptionButtons() {
        return optionButtons;
    }
}