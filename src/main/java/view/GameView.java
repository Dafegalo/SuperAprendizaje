package view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JTextArea textArea;
    private JLabel scoreLabel;
    private JPanel centerPanel; // Panel central jerárquico

    public GameView() {
        setTitle("SuperAprendizaje");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scoreLabel = new JLabel("Jugador: 0   |   Computadora: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        scoreLabel.setForeground(new Color(44, 62, 80));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(scoreLabel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 10));
        add(centerPanel, BorderLayout.CENTER);

        textArea = new JTextArea(4, 30);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(textArea);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);
    }

    // Permite agregar el QuestionView al centro del panel central
    public void setQuestionView(QuestionView questionView) {
        centerPanel.add(questionView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void displayGameStatus(String status) {
        appendText("Estado: " + status);
    }

    public void promptUserInput(String prompt) {
        appendText(prompt);
    }

    public void displayError(String errorMessage) {
        appendText("Error: " + errorMessage);
    }

    public void displayWinner(String winner) {
        appendText("\n🏆 " + winner + " 🏆\n");
    }

    public void displayWelcomeMessage() {
        appendText("¡Bienvenido a SuperAprendizaje!\n");
    }

    public void updateScore(int playerScore, int computerScore) {
        scoreLabel.setText("Jugador: " + playerScore + "   |   Computadora: " + computerScore);
    }

    public void clearMessages() {
        textArea.setText("");
    }

    private void appendText(String text) {
        textArea.append(text + "\n");
    }
}