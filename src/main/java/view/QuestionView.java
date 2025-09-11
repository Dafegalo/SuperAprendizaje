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
        // Elimina el panel de opciones anterior si existe
        Component oldOptionsPanel = getComponentCount() > 1 ? getComponent(1) : null;
        if (oldOptionsPanel != null) {
            remove(oldOptionsPanel);
        }

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

    public void setOptionsEnabled(boolean enabled) {
        if (optionButtons != null) {
            for (JButton btn : optionButtons) {
                btn.setEnabled(enabled);
            }
        }
    }

    public void showResultIcon(boolean correct) {
        // Puedes mostrar un JOptionPane o cambiar el texto del label temporalmente
        String icon = correct ? " ✔️" : " ❌";
        questionLabel.setText(questionLabel.getText() + icon);
    }

    public void clearResultIcon() {
        // Elimina cualquier icono del label
        String text = questionLabel.getText().replace(" ✔️", "").replace(" ❌", "");
        questionLabel.setText(text);
    }
}