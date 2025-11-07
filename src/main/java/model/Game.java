package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Player player;
    private Player computer;
    private Question currentQuestion;
    private Random random;
    private QuestionBank questionBank;

    public Game(Player player, Player computer) {
        this.player = player;
        this.computer = computer;
        this.random = new Random();
    }

    public Player getPlayer() { return player; }
    public Player getComputer() { return computer; }
    public Question getCurrentQuestion() { return currentQuestion; }
    public void setCurrentQuestion(Question currentQuestion) { this.currentQuestion = currentQuestion; }

    public void resetGame() {
        player.resetScore();
        computer.resetScore();
        currentQuestion = null;
        if (questionBank != null) questionBank.clear();
    }

    public void setQuestionBank(QuestionBank qb) {
        this.questionBank = qb;
    }

    public void initializeGameWith(List<Question> source, int limit) {
        questionBank = new QuestionBank();
        java.util.Collections.shuffle(source);
        int n = Math.min(limit, source.size());
        for (int i = 0; i < n; i++) {
            questionBank.addQuestion(source.get(i));
        }
        // Primera pregunta (se remueve del banco)
        setCurrentQuestion(questionBank.getRandomQuestion());
    }

    public Question getRandomQuestion() {
        return questionBank != null ? questionBank.getRandomQuestion() : null;
    }

    public boolean hasMoreQuestions() {
        return questionBank != null && !questionBank.isEmpty();
    }
}