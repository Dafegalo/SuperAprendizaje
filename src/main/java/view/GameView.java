package view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JTextArea textArea;
    private JPanel centerPanel;
    private CelebrationPanel celebrationPanel;

    // Nuevo panel de estado
    private JLabel playerNameLabel;
    private JLabel playerScoreValueLabel;
    private JLabel playerStateIconLabel;

    private JLabel computerNameLabel;
    private JLabel computerScoreValueLabel;
    private JLabel computerStateIconLabel;

    public GameView() {
        setTitle("SuperAprendizaje");
        setPreferredSize(new Dimension(900, 600));
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Usar JLayeredPane para permitir overlay de confetti
        setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        
        // Panel principal (contenido normal)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBounds(0, 0, 900, 600);

        // Header compuesto
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        header.setBackground(new Color(235, 238, 245));

        JLabel title = new JLabel("SuperAprendizaje", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(40, 55, 80));
        header.add(title, BorderLayout.NORTH);

        JPanel scorePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        scorePanel.setOpaque(false);

        // Player box
        JPanel playerBox = new JPanel();
        playerBox.setLayout(new BoxLayout(playerBox, BoxLayout.Y_AXIS));
        playerBox.setOpaque(false);
        playerNameLabel = new JLabel("Jugador");
        playerNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playerScoreValueLabel = new JLabel("Puntaje: 0");
        playerScoreValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playerStateIconLabel = new JLabel("Estado: ?");
        playerStateIconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        playerBox.add(playerNameLabel);
        playerBox.add(Box.createVerticalStrut(2));
        playerBox.add(playerScoreValueLabel);
        playerBox.add(Box.createVerticalStrut(2));
        playerBox.add(playerStateIconLabel);

        // Computer box
        JPanel computerBox = new JPanel();
        computerBox.setLayout(new BoxLayout(computerBox, BoxLayout.Y_AXIS));
        computerBox.setOpaque(false);
        computerNameLabel = new JLabel("Computadora");
        computerNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        computerScoreValueLabel = new JLabel("Puntaje: 0");
        computerScoreValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        computerStateIconLabel = new JLabel("Estado: ?");
        computerStateIconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        computerBox.add(computerNameLabel);
        computerBox.add(Box.createVerticalStrut(2));
        computerBox.add(computerScoreValueLabel);
        computerBox.add(Box.createVerticalStrut(2));
        computerBox.add(computerStateIconLabel);

        scorePanel.add(playerBox);
        scorePanel.add(computerBox);
        header.add(scorePanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);

        centerPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textArea.setBackground(new Color(245, 247, 250));
        textArea.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        JScrollPane scrollPane = new JScrollPane(textArea);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        // Panel de celebración (overlay)
        celebrationPanel = new CelebrationPanel();
        celebrationPanel.setBounds(0, 0, 900, 600);
        celebrationPanel.setVisible(false);

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(celebrationPanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
        
        pack();
        
        // Ajustar tamaños después de pack
        Dimension size = getSize();
        mainPanel.setBounds(0, 0, size.width, size.height);
        celebrationPanel.setBounds(0, 0, size.width, size.height);
    }

    // Permite agregar el QuestionView al centro del panel central
    public void setQuestionView(QuestionView questionView) {
        centerPanel.add(questionView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Reemplaza el contenido central (encima del área de mensajes)
    public void setCenterContent(JComponent content) {
        Component currentCenter = ((BorderLayout) centerPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (currentCenter != null) {
            centerPanel.remove(currentCenter);
        }
        centerPanel.add(content, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void celebrate() {
        celebrationPanel.setVisible(true);
        celebrationPanel.startCelebration();
    }

    public void displayGameStatus(String status) {
        appendText("Estado: " + status);
    }

    public void displayWinner(String winner) {
        appendText("\n🏆 " + winner + " 🏆");
    }

    public void displayWelcomeMessage() {
        appendText("¡Bienvenido a SuperAprendizaje!");
    }

    public void updateScores(int playerScore, int computerScore) {
        playerScoreValueLabel.setText("Puntaje: " + playerScore);
        computerScoreValueLabel.setText("Puntaje: " + computerScore);
    }

    public void updatePlayerScoreWithPulse(int newScore) {
        playerScoreValueLabel.setText("Puntaje: " + newScore);
        pulseLabel(playerScoreValueLabel);
    }

    public void updateComputerScoreWithPulse(int newScore) {
        computerScoreValueLabel.setText("Puntaje: " + newScore);
        pulseLabel(computerScoreValueLabel);
    }

    private void pulseLabel(JLabel label) {
        Font originalFont = label.getFont();
        Color originalColor = label.getForeground();
        final int[] step = {0};
        final int maxSteps = 20;

        Timer timer = new Timer(20, null);
        timer.addActionListener(e -> {
            float progress = (float) step[0] / maxSteps;
            
            if (step[0] < maxSteps / 2) {
                // Creciendo
                float scale = 1.0f + (progress * 0.5f);
                label.setFont(originalFont.deriveFont(originalFont.getSize() * scale));
                // Color verde brillante
                label.setForeground(new Color(76, 175, 80));
            } else {
                // Reduciendo
                float scale = 1.5f - ((progress - 0.5f) * 0.5f);
                label.setFont(originalFont.deriveFont(originalFont.getSize() * scale));
                // Volviendo al color original
                int r = (int) (76 + (originalColor.getRed() - 76) * (progress - 0.5f) * 2);
                int g = (int) (175 + (originalColor.getGreen() - 175) * (progress - 0.5f) * 2);
                int b = (int) (80 + (originalColor.getBlue() - 80) * (progress - 0.5f) * 2);
                label.setForeground(new Color(r, g, b));
            }

            step[0]++;
            if (step[0] > maxSteps) {
                label.setFont(originalFont);
                label.setForeground(originalColor);
                timer.stop();
            }
            label.getParent().revalidate();
            label.getParent().repaint();
        });
        timer.start();
    }

    // Actualiza iconos de estado (✔️, ❌, ?)
    public void updatePlayerState(Boolean correct) {
        playerStateIconLabel.setText("Estado: " + (correct == null ? "?" : (correct ? "✔️" : "❌")));
    }

    public void updateComputerState(Boolean correct) {
        computerStateIconLabel.setText("Estado: " + (correct == null ? "?" : (correct ? "✔️" : "❌")));
    }

    public void clearMessages() {
        textArea.setText("");
    }

    private void appendText(String text) {
        textArea.append(text + "\n");
    }

    public void shakeWindow() {
        final Point origin = getLocation();
        final int amplitude = 8;     // pixeles
        final int steps = 12;        // ciclos
        final int delay = 15;        // ms

        final int[] step = {0};
        Timer t = new Timer(delay, null);
        t.addActionListener(e -> {
            int dx = (step[0] % 2 == 0 ? amplitude : -amplitude) / Math.max(1, (step[0] / 3) + 1);
            int dy = ((step[0] % 4) == 0 ? amplitude/2 : -amplitude/2) / Math.max(1, (step[0] / 3) + 1);
            setLocation(origin.x + dx, origin.y + dy);
            step[0]++;
            if (step[0] >= steps) {
                setLocation(origin);
                ((Timer)e.getSource()).stop();
            }
        });
        t.start();
    }
}