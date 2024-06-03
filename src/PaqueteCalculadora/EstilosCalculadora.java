package PaqueteCalculadora;

import javax.swing.*;
import java.awt.*;

class EstilosCalculadora extends JButton {
    public EstilosCalculadora(String label) {
        super(label);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = getBounds();
        int x = (r.width - fm.stringWidth(getText())) / 2;
        int y = (r.height + fm.getAscent()) / 2 - 2;
        g2.drawString(getText(), x, y);
        g2.dispose();
        super.paintComponent(g);
    }
}
