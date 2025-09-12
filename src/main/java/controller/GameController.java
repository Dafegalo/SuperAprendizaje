package controller;

import config.ErrorProbabilityConfig;
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
    private ErrorProbabilityConfig errorConfig;

    private Boolean lastPlayerCorrect = null;
    private Boolean lastComputerCorrect = null;

    public GameController() {
        Player player = new Player("Jugador");
        Player computer = new Player("Computadora");
        this.game = new Game(player, computer);

        this.gameView = new GameView();
        this.questionView = new QuestionView();

        // Usa el nuevo método para agregar el QuestionView al centro
        gameView.setQuestionView(questionView);

        gameView.setVisible(true);

        this.errorConfig = new ErrorProbabilityConfig(0.5);
    }

    public void startGame() {
        gameView.clearMessages();
        gameView.displayWelcomeMessage();
        selectDifficultyAndStart();
    }

    private void selectDifficultyAndStart() {
        gameView.displayGameStatus("Por favor, selecciona la dificultad para comenzar.");
        String[] opciones = {"Fácil", "Normal", "Difícil"};
        int seleccion = JOptionPane.showOptionDialog(
                gameView,
                "Selecciona la dificultad:",
                "Dificultad",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        switch (seleccion) {
            case 0: errorConfig.setErrorRate(0.5); break; //Falla 50% de las veces
            case 1: errorConfig.setErrorRate(0.3); break; //Falla 30% de las veces
            case 2: errorConfig.setErrorRate(0.1); break; //Falla 10% de las veces
            default: gameView.dispose(); return;
        }
        gameView.clearMessages();
        gameView.displayWelcomeMessage();
        game.initializeGame();
        updateScoreView();
        showCurrentQuestion();
    }

    private void updateScoreView() {
        gameView.updateScore(game.getPlayer().getScore(), game.getComputer().getScore());
    }

    private void showCurrentQuestion() {
        Question question = game.getCurrentQuestion();
        if (question != null) {
            questionView.clearResultIcon();
            questionView.showQuestion(question.getQuestionText());
            questionView.displayOptions(question.getOptions());
            questionView.setOptionsEnabled(true);
            questionView.showResultIcons(null, null); // Limpia iconos

            for (JButton btn : questionView.getOptionButtons()) {
                for (ActionListener al : btn.getActionListeners()) {
                    btn.removeActionListener(al);
                }
            }
            JButton[] optionButtons = questionView.getOptionButtons();
            String[] options = question.getOptions();
            for (int i = 0; i < optionButtons.length; i++) {
                final String selectedAnswer = options[i];
                optionButtons[i].addActionListener(e -> processPlayerTurn(selectedAnswer));
            }
        }
    }

    private void processPlayerTurn(String playerAnswer) {
        questionView.setOptionsEnabled(false);
        Question question = game.getCurrentQuestion();
        boolean correct = question.checkAnswer(playerAnswer);
        lastPlayerCorrect = correct;

        questionView.showAnswerFeedback(playerAnswer, question.getCorrectAnswer());
        questionView.showResultIcons(lastPlayerCorrect, lastComputerCorrect);

        if (correct) {
            game.getPlayer().updateScore(10);
            gameView.displayGameStatus("¡Correcto!");
        } else {
            gameView.displayGameStatus("Incorrecto. La respuesta correcta era: " + question.getCorrectAnswer());
        }
        updateScoreView();

        if (game.hasMoreQuestions()) {
            gameView.displayGameStatus("La computadora está pensando...");
        }

        Timer timer = new Timer(1500, e -> processComputerTurn(question));
        timer.setRepeats(false);
        timer.start();
    }

    private void processComputerTurn(Question question) {
        boolean computerCorrect = Math.random() >= errorConfig.getErrorRate();
        lastComputerCorrect = computerCorrect;

        if (computerCorrect) {
            game.getComputer().updateScore(10);
            gameView.displayGameStatus("La computadora respondió correctamente: " + question.getCorrectAnswer());
        } else {
            gameView.displayGameStatus("La computadora falló la respuesta.");
        }
        questionView.showResultIcons(lastPlayerCorrect, lastComputerCorrect);
        updateScoreView();

        Timer timer = new Timer(1500, e -> {
            if (game.hasMoreQuestions()) {
                lastPlayerCorrect = null;
                lastComputerCorrect = null;
                questionView.clearResultIcon();
                game.setCurrentQuestion(game.getRandomQuestion());
                showCurrentQuestion();
            } else {
                endGame();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void endGame() {
        String mensajeFinal = "Juego terminado.\nPuntaje final:\n" +
                game.getPlayer().getName() + ": " + game.getPlayer().getScore() + "\n" +
                game.getComputer().getName() + ": " + game.getComputer().getScore();

        String ganador;
        if (game.getPlayer().getScore() > game.getComputer().getScore()) {
            ganador = "¡" + game.getPlayer().getName() + " es el ganador!";
        } else if (game.getPlayer().getScore() < game.getComputer().getScore()) {
            ganador = "¡" + game.getComputer().getName() + " es el ganador!";
        } else {
            ganador = "¡Empate!";
        }

        gameView.displayWinner(ganador);

        // Mostrar la respuesta correcta en el label de la pregunta (si hay una pregunta actual)
        Question lastQuestion = game.getCurrentQuestion();
        if (lastQuestion != null) {
            questionView.showFinalCorrectAnswer(lastQuestion.getCorrectAnswer());
        }

        int opcion = JOptionPane.showOptionDialog(
                gameView,
                mensajeFinal + "\n" + ganador + "\n¿Quieres volver a jugar?",
                "Fin del juego",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Volver a jugar", "Salir"},
                "Volver a jugar"
        );

        if (opcion == JOptionPane.YES_OPTION) {
            game.resetGame();
            startGame();
        } else {
            gameView.dispose();
        }
    }
}