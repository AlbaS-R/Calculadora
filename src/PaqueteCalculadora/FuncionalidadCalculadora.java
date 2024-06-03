package PaqueteCalculadora;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FuncionalidadCalculadora implements ActionListener{
    private JTextField cajaTexto;
    private boolean canAddDecimal = true;
    
    public FuncionalidadCalculadora(JTextField cajaTexto) {
        this.cajaTexto = cajaTexto;
    }

    @Override
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

}
