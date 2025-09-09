package main;

import controller.GameController;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido a SuperAprendizaje - Juego de Preguntas y Respuestas");
        SwingUtilities.invokeLater(() -> {
            GameController gameController = new GameController();
            gameController.startGame();
        });
    }
}
