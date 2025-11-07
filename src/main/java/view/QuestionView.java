package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class 
QuestionView extends JPanel {
    private JLabel questionLabel;
    private JPanel questionCard;
    private JPanel optionsWrapper;
    private JButton[] optionButtons;
    private Color defaultButtonBackground;
    private JLabel timerLabel;

    private static final Color CORRECT_BG = new Color(56, 142, 60);      // verde más profundo (mejor contraste)
    private static final Color INCORRECT_BG = new Color(211, 47, 47);    // rojo más profundo (mejor contraste)
    private static final Color CORRECT_FG = Color.WHITE;
    private static final Color INCORRECT_FG = Color.WHITE;
    private static final Color DEFAULT_FG = new Color(25, 25, 25);

    // Colores nuevos con mayor contraste
    private static final Color BUTTON_BASE   = new Color(30, 90, 200);
    private static final Color BUTTON_HOVER  = new Color(25, 110, 230);
    private static final Color BUTTON_PRESS  = new Color(20, 70, 160);
    private static final Color BUTTON_BORDER = new Color(18, 50, 120);
    private static final Color BUTTON_SHADOW = new Color(10, 30, 80);

    private String baseQuestionText = "";

    public QuestionView() {
        setLayout(new BorderLayout(0, 14));
        setOpaque(true);
        setBorder(new EmptyBorder(16, 16, 16, 16));

        // Tarjeta (card) para la pregunta
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setForeground(new Color(33, 42, 62));
        questionLabel.setBorder(new EmptyBorder(10, 12, 10, 12));
        questionLabel.setVerticalAlignment(SwingConstants.TOP);

        questionCard = new JPanel(new BorderLayout());
        questionCard.setOpaque(true);
        questionCard.setBackground(Color.WHITE);
        questionCard.setBorder(new CompoundBorder(
                new LineBorder(new Color(210, 220, 240), 2, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        questionCard.add(questionLabel, BorderLayout.CENTER);
        
        timerLabel = new JLabel("Tiempo: 15 s");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(new Color(180, 40, 40));
        questionCard.add(timerLabel, BorderLayout.SOUTH);
        
        add(questionCard, BorderLayout.NORTH);

        // Contenedor de opciones (se reemplaza en displayOptions)
        optionsWrapper = new JPanel();
        optionsWrapper.setOpaque(false);
        add(optionsWrapper, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fondo degradado suave
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(250, 252, 255),
                0, getHeight(), new Color(230, 238, 255)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    public void showQuestion(String questionText) {
        baseQuestionText = questionText;
        questionLabel.setText("<html>" + baseQuestionText + "</html>");
    }

    private void applyInteractiveStyling(JButton btn) {
        btn.setBackground(BUTTON_BASE);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setUI(new BasicButtonUI());

        // Borde + padding
        btn.setBorder(new CompoundBorder(
                new LineBorder(BUTTON_BORDER, 2, true),
                new EmptyBorder(10, 16, 10, 16)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                animateColor(btn, btn.getBackground(), BUTTON_HOVER, 160);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                animateColor(btn, btn.getBackground(), BUTTON_BASE, 160);
            }
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                btn.setBackground(BUTTON_PRESS);
            }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {
                if (btn.contains(e.getPoint())) {
                    animateColor(btn, btn.getBackground(), BUTTON_HOVER, 120);
                } else {
                    animateColor(btn, btn.getBackground(), BUTTON_BASE, 120);
                }
            }
        });
    }

    private void animateColor(JButton btn, Color start, Color end, int durationMs) {
        final int steps = 16;
        final int interval = durationMs / steps;
        final int[] step = {0};
        Timer timer = new Timer(interval, null);
        timer.addActionListener(e -> {
            float t = (float) step[0] / (float) steps;
            int r = (int)(start.getRed() + t*(end.getRed() - start.getRed()));
            int g = (int)(start.getGreen() + t*(end.getGreen() - start.getGreen()));
            int b = (int)(start.getBlue() + t*(end.getBlue() - start.getBlue()));
            btn.setBackground(new Color(r,g,b));
            step[0]++;
            if (step[0] > steps) timer.stop();
        });
        timer.start();
    }

    public void displayOptions(String[] options) {
        // Simplemente remueve y muestra las nuevas opciones con fade-in
        if (optionsWrapper != null) {
            remove(optionsWrapper);
        }
        showNewOptions(options);
    }

    private void showNewOptions(String[] options) {
        optionsWrapper = new JPanel(new GridLayout(options.length, 1, 0, 12));
        optionsWrapper.setOpaque(false);

        optionButtons = new JButton[options.length];
        for (int i = 0; i < options.length; i++) {
            JButton b = new JButton(options[i]);
            if (defaultButtonBackground == null) defaultButtonBackground = b.getBackground();
            applyInteractiveStyling(b);
            optionButtons[i] = b;
            optionsWrapper.add(b);
            
            // Solo fade-in individual de cada botón
            b.setOpaque(false);
            fadeIn(b, i * 80);
        }
        add(optionsWrapper, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }

    public JButton[] getOptionButtons() {
        return optionButtons;
    }

    public void setOptionsEnabled(boolean enabled) {
        if (optionButtons != null) {
            for (JButton btn : optionButtons) {
                btn.setEnabled(enabled);
            }
        }
    }

    public void showResultIcon(boolean correct) {
        String icon = correct ? " ✔️" : " ❌";
        // Mantiene limpia la pregunta y solo agrega un icono
        String base = questionLabel.getText()
                .replace(" ✔️", "")
                .replace(" ❌", "");
        questionLabel.setText(base + icon);
    }

    public void clearResultIcon() {
        String text = questionLabel.getText().replace(" ✔️", "").replace(" ❌", "");
        questionLabel.setText(text);
    }

    public void showAnswerFeedback(String selected, String correct) {
        for (JButton btn : optionButtons) {
            btn.setEnabled(false);
            if (btn.getText().equals(selected) && !selected.equals(correct)) {
                animateColor(btn, btn.getBackground(), INCORRECT_BG, 180);
                btn.setForeground(INCORRECT_FG);
            } else if (btn.getText().equals(correct)) {
                animateColor(btn, btn.getBackground(), CORRECT_BG, 180);
                btn.setForeground(CORRECT_FG);
            } else {
                animateColor(btn, btn.getBackground(), BUTTON_BASE, 180);
                btn.setForeground(Color.WHITE);
            }
        }
    }

    public void showFinalCorrectAnswer(String answer) {
        questionLabel.setText(
                "<html>Respuesta correcta: <span style='color:green; font-weight:bold;'>" +
                answer + "</span></html>"
        );
    }

    private void fadeIn(JButton btn, int delayMs) {
        Timer delayTimer = new Timer(delayMs, e -> {
            final float[] alpha = {0.0f};
            Timer fadeTimer = new Timer(15, null);
            fadeTimer.addActionListener(ev -> {
                alpha[0] += 0.05f;
                if (alpha[0] >= 1.0f) {
                    alpha[0] = 1.0f;
                    btn.setOpaque(true);
                    fadeTimer.stop();
                } else {
                    btn.setOpaque(false);
                }
                // Ajusta el alpha del color de fondo
                Color base = BUTTON_BASE;
                btn.setBackground(new Color(
                    base.getRed(),
                    base.getGreen(),
                    base.getBlue(),
                    (int)(alpha[0] * 255)
                ));
                btn.repaint();
            });
            fadeTimer.start();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    @Deprecated
    public void showResultIcons(Boolean playerCorrect, Boolean computerCorrect) {
        // Ya no se usa; lógica trasladada a GameView.
    }

    public void updateTimer(int seconds) {
        timerLabel.setText("Tiempo: " + seconds + " s");
        if (seconds <= 5) {
            timerLabel.setForeground(new Color(200, 30, 30));
        } else {
            timerLabel.setForeground(new Color(40, 80, 140));
        }
    }
}