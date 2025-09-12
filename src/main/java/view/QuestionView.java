package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class 
QuestionView extends JPanel {
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private Color defaultButtonBackground;

    private static final Color CORRECT_BG = new Color(76, 175, 80);
    private static final Color INCORRECT_BG = new Color(244, 67, 54);
    private static final Color CORRECT_FG = Color.WHITE;
    private static final Color INCORRECT_FG = Color.WHITE;
    private static final Color DEFAULT_FG = new Color(33, 33, 33);

    private String baseQuestionText = "";

    public QuestionView() {
        setLayout(new BorderLayout());
        questionLabel = new JLabel("Question");
        add(questionLabel, BorderLayout.NORTH);
    }

    public void showQuestion(String questionText) {
        baseQuestionText = questionText;
        questionLabel.setText(baseQuestionText);
    }

    public void displayOptions(String[] options) {
        Component oldOptionsPanel = getComponentCount() > 1 ? getComponent(1) : null;
        if (oldOptionsPanel != null) {
            remove(oldOptionsPanel);
        }

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(options.length, 1, 0, 10));
        optionButtons = new JButton[options.length];
        for (int i = 0; i < options.length; i++) {
            optionButtons[i] = new JButton(options[i]);
            if (defaultButtonBackground == null) {
                defaultButtonBackground = optionButtons[i].getBackground();
            }
            optionButtons[i].setBackground(defaultButtonBackground);
            optionButtons[i].setForeground(DEFAULT_FG);
            optionButtons[i].setFont(new Font("Segoe UI", Font.BOLD, 18));
            optionButtons[i].setOpaque(true);
            optionButtons[i].setContentAreaFilled(true);
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setBorderPainted(false);
            optionButtons[i].setUI(new BasicButtonUI());
            optionButtons[i].setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
            optionsPanel.add(optionButtons[i]);
        }
        optionsPanel.setBackground(new Color(245, 245, 245));
        add(optionsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public JButton[] getOptionButtons() {
        return optionButtons;
    }

    public void setOptionsEnabled(boolean enabled) {
        if (optionButtons != null) {
            for (JButton btn : optionButtons) {
                btn.setEnabled(enabled);
            }
        }
    }

    public void showResultIcon(boolean correct) {
        String icon = correct ? " ✔️" : " ❌";
        questionLabel.setText(questionLabel.getText().replace(" ✔️", "").replace(" ❌", "") + icon);
    }

    public void clearResultIcon() {
        String text = questionLabel.getText().replace(" ✔️", "").replace(" ❌", "");
        questionLabel.setText(text);
    }

    public void showAnswerFeedback(String selected, String correct) {
        for (JButton btn : optionButtons) {
            btn.setEnabled(false);
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setUI(new BasicButtonUI());
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            if (btn.getText().equals(selected) && !selected.equals(correct)) {
                btn.setBackground(INCORRECT_BG);
                btn.setForeground(INCORRECT_FG);
            } else if (btn.getText().equals(correct)) {
                btn.setBackground(CORRECT_BG);
                btn.setForeground(CORRECT_FG);
            } else {
                btn.setBackground(defaultButtonBackground);
                btn.setForeground(DEFAULT_FG);
            }
        }
    }
    //Html para mejor visualización

    public void showFinalCorrectAnswer(String answer) {
        questionLabel.setText("<html>Respuesta correcta: <span style='color:green; font-weight:bold;'>" +
                answer + "</span></html>");
    }

    public void showResultIcons(Boolean playerCorrect, Boolean computerCorrect) {
    StringBuilder sb = new StringBuilder(baseQuestionText);
    sb.append("  |  Jugador: ");
    sb.append(playerCorrect == null ? "?" : (playerCorrect ? "✔️" : "❌"));
    sb.append("  |  Computadora: ");
    sb.append(computerCorrect == null ? "?" : (computerCorrect ? "✔️" : "❌"));
    questionLabel.setText(sb.toString());
}
}