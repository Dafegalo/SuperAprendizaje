package controller;

import config.ErrorProbabilityConfig;
import model.Game;
import model.Player;
import model.Question;
import view.GameView;
import view.QuestionView;
import view.ScoreView;
import view.CategorySelectionView;
import util.SoundPlayer;
import model.Category;
import util.CategoryQuestionProvider;
import model.QuestionBank;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameController {
    private Game game;
    private GameView gameView;
    private QuestionView questionView;
    private ScoreView scoreView;
    private ErrorProbabilityConfig errorConfig;
    private CategorySelectionView categorySelectionView;

    private Boolean lastPlayerCorrect = null;
    private Boolean lastComputerCorrect = null;
    private Category selectedCategory;

    private Timer questionTimer;
    private int timeLeft;

    public GameController() {
        Player player = new Player("Jugador");
        Player computer = new Player("Computadora");
        this.game = new Game(player, computer);

        this.gameView = new GameView();
        this.questionView = new QuestionView();
        this.categorySelectionView = new CategorySelectionView();

        // Callback al elegir tarjeta
        categorySelectionView.setOnSelected(category -> {
            selectedCategory = category;
            java.util.List<model.Question> list = util.CategoryQuestionProvider.getQuestions(selectedCategory);
            game.initializeGameWith(list, 8); // 8 preguntas de 10
            gameView.displayGameStatus("Categoría: " + humanCategoryName(category));

            // Cambiar el centro a las preguntas
            gameView.setCenterContent(questionView);
            updateScoreView();
            showCurrentQuestion();
        });

        // Puedes mantener esto; luego setCenterContent reemplaza el CENTER
        gameView.setQuestionView(questionView);
        gameView.setVisible(true);

        this.errorConfig = new ErrorProbabilityConfig(0.5);
    }

    public void startGame() {
        SoundPlayer.playBackground(true);
        gameView.clearMessages();
        gameView.displayWelcomeMessage();
        selectDifficultyAndStart();
    }

    private void selectDifficultyAndStart() {
        gameView.displayGameStatus("Selecciona dificultad.");
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
            case 0: errorConfig.setErrorRate(0.5); break;
            case 1: errorConfig.setErrorRate(0.3); break;
            case 2: errorConfig.setErrorRate(0.1); break;
            default: gameView.dispose(); return;
        }
        // Mostrar tarjetas de categoría en el centro
        showCategorySelection();
    }

    private void showCategorySelection() {
        gameView.displayGameStatus("Selecciona una categoría.");
        gameView.setCenterContent(categorySelectionView);
    }

    private String humanCategoryName(model.Category c) {
        switch (c) {
            case ESTADISTICA: return "Estadística";
            case GEOGRAFIA: return "Geografía";
            case DEPORTES: return "Deportes";
            default: return c.name();
        }
    }

    private void updateScoreView() {
        gameView.updateScores(game.getPlayer().getScore(), game.getComputer().getScore());
    }

    private void startQuestionTimer() {
        stopQuestionTimer();
        timeLeft = 15;
        questionView.updateTimer(timeLeft);
        questionTimer = new Timer(1000, e -> {
            timeLeft--;
            questionView.updateTimer(timeLeft);
            if (timeLeft <= 0) {
                stopQuestionTimer();
                handlePlayerTimeout();
            }
        });
        questionTimer.start();
    }

    private void stopQuestionTimer() {
        if (questionTimer != null) {
            questionTimer.stop();
            questionTimer = null;
        }
    }

    private void handlePlayerTimeout() {
        Question question = game.getCurrentQuestion();
        if (question == null) return;
        gameView.displayGameStatus("Tiempo agotado. Se cuenta como incorrecto.");
        lastPlayerCorrect = false;
        gameView.updatePlayerState(false);
        // Mostrar solo correcta
        questionView.showAnswerFeedback("", question.getCorrectAnswer());
        // Continuar flujo computadora
        Timer t = new Timer(1200, e -> processComputerTurn(question));
        t.setRepeats(false);
        t.start();
    }

    private void showCurrentQuestion() {
        Question question = game.getCurrentQuestion();
        if (question != null) {
            questionView.clearResultIcon();
            lastPlayerCorrect = null;
            lastComputerCorrect = null;
            gameView.updatePlayerState(null);
            gameView.updateComputerState(null);
            questionView.showQuestion(question.getQuestionText());
            questionView.displayOptions(question.getOptions());
            questionView.setOptionsEnabled(true);
            startQuestionTimer(); // iniciar temporizador

            for (JButton btn : questionView.getOptionButtons()) {
                for (ActionListener al : btn.getActionListeners()) {
                    btn.removeActionListener(al);
                }
            }
            String[] options = question.getOptions();
            JButton[] optionButtons = questionView.getOptionButtons();
            for (int i = 0; i < optionButtons.length; i++) {
                final String selectedAnswer = options[i];
                optionButtons[i].addActionListener(e -> {
                    stopQuestionTimer(); // detener al responder
                    processPlayerTurn(selectedAnswer);
                });
            }
        }
    }

    private void processPlayerTurn(String playerAnswer) {
        // stopQuestionTimer() ya llamado antes
        questionView.setOptionsEnabled(false);
        Question question = game.getCurrentQuestion();
        boolean correct = question.checkAnswer(playerAnswer);
        lastPlayerCorrect = correct;

        questionView.showAnswerFeedback(playerAnswer, question.getCorrectAnswer());
        gameView.updatePlayerState(lastPlayerCorrect);

        if (correct) {
            game.getPlayer().updateScore(10);
            gameView.displayGameStatus("¡Correcto!");
            gameView.updatePlayerScoreWithPulse(game.getPlayer().getScore());
        } else {
            gameView.displayGameStatus("Incorrecto. La respuesta correcta era: " + question.getCorrectAnswer());
            gameView.updateScores(game.getPlayer().getScore(), game.getComputer().getScore());
            gameView.shakeWindow(); // efecto shake al fallar
        }

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
            gameView.updateComputerScoreWithPulse(game.getComputer().getScore());
        } else {
            gameView.displayGameStatus("La computadora falló la respuesta.");
            gameView.updateScores(game.getPlayer().getScore(), game.getComputer().getScore());
            gameView.shakeWindow(); // shake también en fallo de la computadora
        }
        gameView.updateComputerState(lastComputerCorrect);

        Timer timer = new Timer(1500, e -> {
            if (game.hasMoreQuestions()) {
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
        stopQuestionTimer();
        String mensajeFinal = "Juego terminado.\nPuntaje final:\n" +
                game.getPlayer().getName() + ": " + game.getPlayer().getScore() + "\n" +
                game.getComputer().getName() + ": " + game.getComputer().getScore();

        String ganador;
        boolean playerWon = false;
        if (game.getPlayer().getScore() > game.getComputer().getScore()) {
            ganador = "¡" + game.getPlayer().getName() + " es el ganador!";
            playerWon = true;
        } else if (game.getPlayer().getScore() < game.getComputer().getScore()) {
            ganador = "¡" + game.getComputer().getName() + " es el ganador!";
        } else {
            ganador = "¡Empate!";
        }

        gameView.displayWinner(ganador);

        // Si el jugador ganó, mostrar confetti
        if (playerWon) {
            gameView.celebrate();
        }

        Question lastQuestion = game.getCurrentQuestion();
        if (lastQuestion != null) {
            questionView.showFinalCorrectAnswer(lastQuestion.getCorrectAnswer());
        }

        // Delay para que se vea la celebración antes del diálogo
        Timer dialogTimer = new Timer(1000, e -> {
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
                startGame();
            } else {
                SoundPlayer.stopBackground();
                gameView.dispose();
            }
        });
        dialogTimer.setRepeats(false);
        dialogTimer.start();
    }
}