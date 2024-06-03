package PaqueteCalculadora;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaCalculadora extends JFrame {
    private JPanel panel;
    private JTextField cajaTexto;

    public VentanaCalculadora() {
        this.setSize(350, 600);
        setTitle("Calculadora");
        setLocationRelativeTo(null);
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void iniciarComponentes() {
        iniciarPanel();
        iniciarCajaTexto();
        iniciarBotones();
    }

    private void iniciarPanel() {
        ImageIcon backgroundImage = new ImageIcon(
                "D:\\m03\\UF5Visual\\Calculadora\\Calculadora\\src\\PaqueteCalculadora\\imagenes\\kuromi1.jpg");

        panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        setContentPane(panel);
    }

    private void iniciarCajaTexto() {
        cajaTexto = new JTextField();
        cajaTexto.setBounds(20, 50, 295, 50);
        cajaTexto.setEditable(false);
        cajaTexto.setBackground(Color.WHITE);
        cajaTexto.setForeground(Color.BLACK);
        cajaTexto.setFont(new Font("HGPMinchoB", Font.BOLD, 24));
        cajaTexto.setBorder(new LineBorder(Color.PINK, 2));
        panel.add(cajaTexto);
    }

    private void iniciarBotones() {
        String[] buttonLabels = {
                "CE", "C", "/", "x",
                "7", "8", "9", "-",
                "4", "5", "6", "+",
                "1", "2", "3", "=",
                "0", "00", ","
        };

        int x = 20, y = 125;
        int width = 70, height = 70;

        for (String label : buttonLabels) {
            JButton button = new EstilosCalculadora(label);
            button.setBounds(x, y, width, height);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.PINK);
            button.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
            button.addActionListener(new FuncionalidadCalculadora(cajaTexto));

            if (label.matches("[0-9]|00")) {
                button.setFont(new Font("HGPMinchoB", Font.BOLD, 20));
            }

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(Color.PINK);
                    button.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.PINK);
                }
            });

            panel.add(button);

            x += 75;
            if (x > 245) {
                x = 20;
                y += 75;
            }
        }
    }
}
