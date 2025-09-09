
package controller;

public class ScoreController {
    private int score;

    public ScoreController() {
        this.score = 0;
    }

    public void calculateScore(boolean isCorrect) {
        if (isCorrect) {
            updateScore(10); // Increment score by 10 for a correct answer
        } else {
            updateScore(-5); // Decrement score by 5 for an incorrect answer
        }
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return this.score;
    }
}