package main;

import controller.GameController;

import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, se usa el look and feel por defecto
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameController gameController = new GameController();
            gameController.startGame();
        });
    }
}
