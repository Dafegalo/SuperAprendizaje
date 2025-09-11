package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Player player;
    private Player computer;
    private Question currentQuestion;
    private List<Question> questions;
    private Random random;

    public Game(Player player, Player computer) {
        this.player = player;
        this.computer = computer;
        this.questions = new ArrayList<>();
        this.random = new Random();
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void resetGame() {
        player.resetScore();
        computer.resetScore();
        currentQuestion = null;
        questions.clear();
        initializeGame();
    }

    public void initializeGame() {
        questions.clear();
        questions.add(new Question("¿De qué color es el cielo?", new String[]{"Azul", "Rojo", "Verde", "Amarillo"}, "Azul"));
        questions.add(new Question("¿Cuál es la capital de Francia?", new String[]{"Madrid", "París", "Roma", "Berlín"}, "París"));
        questions.add(new Question("¿Cuánto es 2 + 2?", new String[]{"3", "4", "5", "6"}, "4"));
        questions.add(new Question("¿Cuál es el animal más grande del mundo?", new String[]{"Elefante", "Ballena azul", "Jirafa", "Tiburón"}, "Ballena azul"));
        questions.add(new Question("¿En qué continente está Brasil?", new String[]{"Asia", "África", "América", "Europa"}, "América"));
        setCurrentQuestion(getRandomQuestion());
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        int index = random.nextInt(questions.size());
        return questions.remove(index); // Así no se repiten preguntas
    }

    public boolean hasMoreQuestions() {
        return !questions.isEmpty();
    }
}