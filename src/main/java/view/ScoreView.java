package view;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JPanel {
    private JLabel playerScoreLabel;
    private JLabel computerScoreLabel;

    public ScoreView() {
        setLayout(new GridLayout(2, 1));
        playerScoreLabel = new JLabel("Player Score: 0");
        computerScoreLabel = new JLabel("Computer Score: 0");
        add(playerScoreLabel);
        add(computerScoreLabel);
    }

    public void updateScoreDisplay(int playerScore, int computerScore) {
        playerScoreLabel.setText("Player Score: " + playerScore);
        computerScoreLabel.setText("Computer Score: " + computerScore);
    }
}