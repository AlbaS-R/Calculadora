package PaqueteCalculadora;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaCalculadora extends JFrame {
    private JPanel panel;
    private JTextField cajaTexto;
    private boolean canAddDecimal = true;

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
            JButton button = new RoundedButton(label);
            button.setBounds(x, y, width, height);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.PINK);
            button.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
            button.addActionListener(new funcionalidadCalculadora());

            // Cambiar la fuente de los botones de números
            if (label.matches("[0-9]|00")) {
                button.setFont(new Font("HGPMinchoB", Font.BOLD, 20));
            }

            // Añadir MouseListener para cambiar el color al pasar el mouse por encima
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

    private class funcionalidadCalculadora implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "C":
                    if (cajaTexto.getText().length() > 0) {
                        String currentText = cajaTexto.getText();
                        if (currentText.endsWith(",")) {
                            canAddDecimal = true;
                        }
                        cajaTexto.setText(currentText.substring(0, currentText.length() - 1));
                    }
                    break;
                case "CE":
                cajaTexto.setText("");
                    canAddDecimal = true;
                    break;
                case "=":
                    try {
                        String expression = cajaTexto.getText().replaceAll(",", ".");
                        double result = equacion(expression);
                        if (Double.isInfinite(result)) {
                            cajaTexto.setText("Infinito.");
                        } else {
                            cajaTexto.setText(String.valueOf(result).replaceAll("\\.", ","));
                        }
                        canAddDecimal = !cajaTexto.getText().contains(",");
                    } catch (ArithmeticException ex) {
                        cajaTexto.setText("Infinito.");
                    } catch (Exception ex) {
                        cajaTexto.setText("Error");
                    }
                    break;
                case ",":
                    if (canAddDecimal) {
                        cajaTexto.setText(cajaTexto.getText() + ",");
                        canAddDecimal = false;
                    }
                    break;
                case "+":
                case "-":
                case "x":
                case "/":
                cajaTexto.setText(cajaTexto.getText() + " " + command + " ");
                    canAddDecimal = true;
                    break;
                default:
                cajaTexto.setText(cajaTexto.getText() + command);
            }
        }
    }

    private double equacion(String expression) {
        String[] tokens = expression.split(" ");
        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double nextNumber = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += nextNumber;
                    break;
                case "-":
                    result -= nextNumber;
                    break;
                case "x":
                    result *= nextNumber;
                    break;
                case "/":
                    result /= nextNumber;
                    break;
            }
        }
        return result;
    }

    class RoundedButton extends JButton {
        public RoundedButton(String label) {
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
}
