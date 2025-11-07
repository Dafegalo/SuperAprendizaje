package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CelebrationPanel extends JPanel {
    private List<Particle> particles;
    private Timer animationTimer;
    private Random random;
    private static final Color[] COLORS = {
        new Color(255, 87, 34),   // Naranja
        new Color(76, 175, 80),   // Verde
        new Color(33, 150, 243),  // Azul
        new Color(255, 193, 7),   // Amarillo
        new Color(156, 39, 176),  // Púrpura
        new Color(244, 67, 54)    // Rojo
    };

    public CelebrationPanel() {
        setOpaque(false);
        setLayout(null);
        particles = new ArrayList<>();
        random = new Random();
    }

    public void startCelebration() {
        particles.clear();
        // Crear múltiples partículas
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle(
                random.nextInt(getWidth()),
                -random.nextInt(50),
                COLORS[random.nextInt(COLORS.length)],
                random
            ));
        }

        animationTimer = new Timer(16, e -> {
            for (Particle p : particles) {
                p.update();
            }
            repaint();
        });
        animationTimer.start();

        // Detener después de 5 segundos
        Timer stopTimer = new Timer(5000, e -> {
            animationTimer.stop();
            setVisible(false);
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Particle p : particles) {
            p.draw(g2);
        }
    }

    private static class Particle {
        private float x, y;
        private float vx, vy;
        private Color color;
        private int size;
        private float rotation;
        private float rotationSpeed;

        public Particle(int x, int y, Color color, Random random) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.size = 8 + random.nextInt(8);
            this.vx = -3 + random.nextFloat() * 6;
            this.vy = 2 + random.nextFloat() * 3;
            this.rotation = random.nextFloat() * 360;
            this.rotationSpeed = -5 + random.nextFloat() * 10;
        }

        public void update() {
            x += vx;
            y += vy;
            vy += 0.2; // gravedad
            rotation += rotationSpeed;
        }

        public void draw(Graphics2D g2) {
            g2.setColor(color);
            g2.rotate(Math.toRadians(rotation), x, y);
            g2.fillRect((int)x - size/2, (int)y - size/2, size, size);
            g2.rotate(-Math.toRadians(rotation), x, y);
        }
    }
}