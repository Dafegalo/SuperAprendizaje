package model;

public class Score {
    private int scoreValue;

    public Score() {
        this.scoreValue = 0;
    }

    public void updateScore(int points) {
        this.scoreValue += points;
    }

    public int getScore() {
        return this.scoreValue;
    }

    public void resetScore() {
        this.scoreValue = 0;
    }
}