package controller;

import model.Game;
import model.Player;
import model.Question;
import view.GameView;
import view.QuestionView;
import view.ScoreView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private Game game;
    private GameView gameView;
    private QuestionView questionView;
    private ScoreView scoreView;

    public GameController() {
        Player player = new Player("Jugador");
        Player computer = new Player("Computadora");
        this.game = new Game(player, computer);

        this.gameView = new GameView();
        this.questionView = new QuestionView();
        this.scoreView = new ScoreView();

        // Configura la ventana principal
        gameView.setLayout(new BorderLayout());
        gameView.add(scoreView, BorderLayout.NORTH);
        gameView.add(questionView, BorderLayout.CENTER);
        gameView.setVisible(true);
    }

    public void startGame() {
        gameView.displayWelcomeMessage();
        game.initializeGame();
        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        Question question = game.getCurrentQuestion();
        if (question != null) {
            questionView.showQuestion(question.getQuestionText());
            questionView.displayOptions(question.getOptions());

            // Quita listeners anteriores
            for (JButton btn : questionView.getOptionButtons()) {
                for (ActionListener al : btn.getActionListeners()) {
                    btn.removeActionListener(al);
                }
            }

            // Agrega listeners a los botones de opciones
            JButton[] optionButtons = questionView.getOptionButtons();
            String[] options = question.getOptions();
            for (int i = 0; i < optionButtons.length; i++) {
                final String selectedAnswer = options[i];
                optionButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        processAnswer(selectedAnswer);
                    }
                });
            }
        }
    }

    private void processAnswer(String playerAnswer) {
        Question question = game.getCurrentQuestion();
        boolean correct = question.checkAnswer(playerAnswer);
        if (correct) {
            game.getPlayer().updateScore(10);
            gameView.displayGameStatus("¡Correcto!");
        } else {
            gameView.displayGameStatus("Incorrecto. La respuesta correcta era: " + question.getCorrectAnswer());
        }
        scoreView.updateScoreDisplay(game.getPlayer().getScore(), game.getComputer().getScore());
        endGame();
    }

    public void endGame() {
        gameView.displayGameStatus("Juego terminado. Puntaje final: " + game.getPlayer().getScore());
        JOptionPane.showMessageDialog(gameView, "¡Gracias por jugar!\nPuntaje final: " + game.getPlayer().getScore());
        gameView.dispose();
    }
}