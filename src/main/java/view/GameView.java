package view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JTextArea textArea;

    public GameView() {
        setTitle("SuperAprendizaje");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayGameStatus(String status) {
        appendText("Game Status: " + status);
    }

    public void promptUserInput(String prompt) {
        appendText(prompt);
    }

    public void displayError(String errorMessage) {
        appendText("Error: " + errorMessage);
    }

    public void displayWinner(String winner) {
        appendText("El ganador es: " + winner);
    }

    public void displayWelcomeMessage() {
        appendText("¡Bienvenido a SuperAprendizaje!");
    }

    private void appendText(String text) {
        textArea.append(text + "\n");
    }
}