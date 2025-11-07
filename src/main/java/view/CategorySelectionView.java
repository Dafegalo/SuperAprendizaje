package view;

import model.Category;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.util.function.Consumer;

public class CategorySelectionView extends JPanel {
    private Consumer<Category> onSelected;

    public CategorySelectionView() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setOpaque(false);

        JButton estadistica = createCard("📊 Estadística", new Color(52, 152, 219));
        estadistica.addActionListener(e -> notifySelected(Category.ESTADISTICA));

        JButton geografia = createCard("🗺️ Geografía", new Color(46, 204, 113));
        geografia.addActionListener(e -> notifySelected(Category.GEOGRAFIA));

        JButton deportes = createCard("🏆 Deportes", new Color(241, 196, 15));
        deportes.addActionListener(e -> notifySelected(Category.DEPORTES));

        JButton ingles = createCard("🇬🇧 Inglés", new Color(155, 89, 182));
        ingles.addActionListener(e -> notifySelected(Category.INGLES));

        row.add(estadistica);
        row.add(geografia);
        row.add(deportes);
        row.add(ingles);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0; gbc.gridy = 0;
        add(row, gbc);

        JLabel hint = new JLabel("Elige una categoría para empezar");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hint.setForeground(new Color(70, 80, 95));
        gbc.gridy = 1;
        add(hint, gbc);
    }

    private JButton createCard(String text, Color base) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setUI(new BasicButtonUI());
        b.setOpaque(true);
        b.setBackground(base.darker());
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(base.darker().darker(), 2, true),
                BorderFactory.createEmptyBorder(24, 18, 24, 18)
        ));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(base);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(base.darker());
            }
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                b.setBackground(base.darker().darker());
            }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {
                b.setBackground(base);
            }
        });
        return b;
    }

    private void notifySelected(Category category) {
        if (onSelected != null) onSelected.accept(category);
    }

    public void setOnSelected(Consumer<Category> onSelected) {
        this.onSelected = onSelected;
    }
}