package ru.javapp.avalo.devj120;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;


public class MainFrame extends JFrame {
    private static final String DIV_BY_ZERO_MSG = "Div by zero!";
    private JLabel screen;
    private BigDecimal register;
    private String prevOp;
    private boolean nextInputClearScreen;

    public MainFrame() {
        // заголовок
        super("Calculator demo");
        setBounds(300,200,600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        screen = new JLabel("0", SwingConstants.RIGHT);
        screen.setFont(screen.getFont().deriveFont(48.0f));
        screen.setBorder(BorderFactory.createEtchedBorder());
        add(screen, BorderLayout.NORTH);

        JPanel p = new JPanel(new GridLayout(4, 4));
        p.add(createButton("7", e -> processDigit(7)));
        p.add(createButton("8", e -> processDigit(8)));
        p.add(createButton("9", e -> processDigit(9)));
        p.add(createButton("+", e -> processOp("+")));
        p.add(createButton("4", e -> processDigit(4)));
        p.add(createButton("5", e -> processDigit(5)));
        p.add(createButton("6", e -> processDigit(6)));
        p.add(createButton("-", e -> processOp("-")));
        p.add(createButton("1", e -> processDigit(1)));
        p.add(createButton("2", e -> processDigit(2)));
        p.add(createButton("3", e -> processDigit(3)));
        p.add(createButton("*", e -> processOp("*")));
        p.add(createButton("0", e -> processDigit(0)));
        p.add(createButton(".", e -> processDot()));
        p.add(createButton("C", e -> screen.setText("0")));
        p.add(createButton("/", e -> processOp("/")));
        add(p, BorderLayout.CENTER);

        add(createButton("=", e -> processOp(null)), BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener al) {
        JButton btn = new JButton(text);
        btn.setFont(btn.getFont().deriveFont(24.f));
        btn.addActionListener(al);
        return btn;
    }

    private void processDigit(int digit) {
        if (nextInputClearScreen) {
            screen.setText(Integer.toString(digit));
            nextInputClearScreen = false;
        } else {

            if (screen.getText().equals("0")) {
                screen.setText(Integer.toString(digit));
            } else {
                screen.setText(screen.getText() + digit);
            }
        }
    }

    private void processDot() {
        if (nextInputClearScreen) {
            screen.setText("0.");
            nextInputClearScreen = false;
        }
        {
            String s = screen.getText();
            if (!s.contains("."))
                screen.setText(screen.getText() + ".");
        }
    }

    private void processOp (String op) {
        String screenText = screen.getText();
        if (screenText == DIV_BY_ZERO_MSG)
            return;
        BigDecimal screenNum = new BigDecimal(screen.getText());
        if (prevOp != null)
        {
            boolean divByZiro = false;
            switch (prevOp) {
                case  "+": register = register.add(screenNum); break;
                case  "-": register = register.subtract(screenNum); break;
                case  "*": register = register.multiply(screenNum); break;
                case  "/":
                    divByZiro = screenNum.compareTo(BigDecimal.ZERO) == 0;
                    if (!divByZiro)
                        register = register.divide(screenNum);
                    break;
            }
            if (!divByZiro)
                screen.setText(register.toString());
            else
                screen.setText("Div by zero!");
            // убрать нули в конце значения double, с int работает плохо
            //register = register.stripTrailingZeros();
        } else
            register = screenNum;
        prevOp = op;
        nextInputClearScreen=true;
    }



    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
