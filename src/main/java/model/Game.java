package model;

import java.util.Arrays;


public class Game {
    private Player player;
    private Player computer;
    private Question currentQuestion;

    public Game(Player player, Player computer) {
        this.player = player;
        this.computer = computer;
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
    }

    public void initializeGame() {
        // Ejemplo simple: crea una pregunta y la asigna como actual
        String[] opciones = {"Azul", "Rojo", "Verde", "Amarillo"};
        Question pregunta = new Question("¿De qué color es el cielo?", opciones, "Azul");
        setCurrentQuestion(pregunta);
    }
}