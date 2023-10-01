import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleCalculator extends JFrame {
    private JTextField jtfResult;
    private double opr1;
    private double opr2;
    private char operation;

    private String[] buttonLabels = {
            "CE", "C", "<--", "/",
            "1", "2", "3", "*",
            "4", "5", "6", "+",
            "7", "8", "9", "-",
            "0", ".", "="
    };

    private JButton[] buttons;

    public SimpleCalculator() {
        super("CALCULATOR");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        jtfResult = new JTextField(20);
        jtfResult.setHorizontalAlignment(JTextField.RIGHT);
        panel1.add(jtfResult);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(5, 4));

        buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            panel2.add(buttons[i]);
            buttons[i].addActionListener(new ButtonClickListener());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel2, BorderLayout.SOUTH);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();

            if (isNumeric(buttonText)) {
                jtfResult.setText(jtfResult.getText() + buttonText);
            } else if (buttonText.equals(".")) {
                if (!jtfResult.getText().contains(".")) {
                    jtfResult.setText(jtfResult.getText() + buttonText);
                }
            } else if (buttonText.equals("CE")) {
                jtfResult.setText("");
            } else if (buttonText.equals("C")) {
                opr1 = 0;
                opr2 = 0;
                jtfResult.setText("");
            } else if (buttonText.equals("<--")) {
                String currentText = jtfResult.getText();
                if (!currentText.isEmpty()) {
                    jtfResult.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (isOperator(buttonText)) {
                opr1 = Double.parseDouble(jtfResult.getText());
                operation = buttonText.charAt(0);
                jtfResult.setText("");
            } else if (buttonText.equals("=")) {
                opr2 = Double.parseDouble(jtfResult.getText());
                calculateResult();
            }
        }
    }

    private void calculateResult() {
        switch (operation) {
            case '+' -> opr1 += opr2;
            case '-' -> opr1 -= opr2;
            case '*' -> opr1 *= opr2;
            case '/' -> opr1 /= opr2;
        }
        jtfResult.setText(String.valueOf(opr1));
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperator(String str) {
        return str.matches("[+\\-*/]");
    }

    public static void main(String[] args) {
        new SimpleCalculator();
    }
}
