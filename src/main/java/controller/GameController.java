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

        // Inicializa con dificultad por defecto (se ajusta antes de cada partida)
        this.errorConfig = new ErrorProbabilityConfig(0.5);
    }

    public void startGame() {
        selectDifficultyAndStart();
    }

    private void selectDifficultyAndStart() {
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
        //Funciona con >= porque Math.random() puede dar 0 pero no 1, segun entendi yo
        switch (seleccion) {
            case 0: // Fácil
                errorConfig.setErrorRate(0.5); // 50% de error (50% de acierto)
                break;
            case 1: // Normal
                errorConfig.setErrorRate(0.3); // 30% de error (70% de acierto)
                break;
            case 2: // Difícil
                errorConfig.setErrorRate(0.1); // 10% de error (90% de acierto)
                break;
            default: // Si cierra el diálogo, salir
                gameView.dispose();
                return;
        }
//P.P

//println("Tasa de error configurada: " + errorConfig.getErrorRate());
        gameView.displayWelcomeMessage();
        game.initializeGame();
        scoreView.updateScoreDisplay(game.getPlayer().getScore(), game.getComputer().getScore());
        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        Question question = game.getCurrentQuestion();
        if (question != null) {
            questionView.clearResultIcon();
            questionView.showQuestion(question.getQuestionText());
            questionView.displayOptions(question.getOptions());
            questionView.setOptionsEnabled(true);

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
                        processPlayerTurn(selectedAnswer);
                    }
                });
            }
        }
    }

    private void processPlayerTurn(String playerAnswer) {
        questionView.setOptionsEnabled(false);
        Question question = game.getCurrentQuestion();
        boolean correct = question.checkAnswer(playerAnswer);

        // Colorea los botones y deja el color hasta la siguiente pregunta
        questionView.showAnswerFeedback(playerAnswer, question.getCorrectAnswer());
        questionView.showResultIcon(correct); // Muestra el ✔️ o ❌ en el label

        if (correct) {
            game.getPlayer().updateScore(10);
            gameView.displayGameStatus("¡Correcto!");
        } else {
            gameView.displayGameStatus("Incorrecto. La respuesta correcta era: " + question.getCorrectAnswer());
        }
        scoreView.updateScoreDisplay(game.getPlayer().getScore(), game.getComputer().getScore());

        // Mostrar que la computadora está pensando (sin cambiar botones ni iconos)
        questionView.showQuestion(question.getQuestionText() + " (La computadora está pensando...)");

        // Turno de la computadora después de un pequeño delay
        Timer timer = new Timer(1500, e -> processComputerTurn(question));
        timer.setRepeats(false);
        timer.start();
    }

    private void processComputerTurn(Question question) {
        boolean computerCorrect = Math.random() >= errorConfig.getErrorRate();

        // Solo actualiza el label para mostrar el resultado de la computadora
        if (computerCorrect) {
            game.getComputer().updateScore(10);
            gameView.displayGameStatus("La computadora respondió correctamente: " + question.getCorrectAnswer());
            questionView.showResultIcon(true); // Puedes agregar un ✔️ al label
        } else {
            gameView.displayGameStatus("La computadora falló la respuesta.");
            questionView.showResultIcon(false); // Puedes agregar un ❌ al label
        }
        scoreView.updateScoreDisplay(game.getPlayer().getScore(), game.getComputer().getScore());

        // Espera antes de pasar a la siguiente pregunta y restablecer botones/colores
        Timer timer = new Timer(1500, e -> {
            if (game.hasMoreQuestions()) {
                questionView.clearResultIcon(); // Limpia el icono del label
                game.setCurrentQuestion(game.getRandomQuestion());
                showCurrentQuestion(); // Aquí sí se restablecen los botones
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

        int opcion = JOptionPane.showOptionDialog(
                gameView,
                mensajeFinal + "\n¿Quieres volver a jugar?",
                "Fin del juego",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Volver a jugar", "Salir"},
                "Volver a jugar"
        );

        if (opcion == JOptionPane.YES_OPTION) {
            game.resetGame();
            selectDifficultyAndStart();
        } else {
            gameView.dispose();
        }
    }
}