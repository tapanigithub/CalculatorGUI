import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class Calculator extends JFrame {
    JButton buttonAdd, buttonSubtract, buttonMultiply,
            buttonDivide, buttonToThePower, buttonEquals, buttonComma, buttonClear, buttonDelete;
    JButton[] nums;
    JTextField outputScreen;
    String current = "", previous = "", operator;

    public void delete() {
        if (!current.isEmpty()) {
            current = current.substring(0, current.length() - 1);
        }
    }
    public void clear() {
        current = "";
        previous = "";
        operator = null;
    }
    public void updateOutput() {
        outputScreen.setText(current);
    }
    public void appendToOutput(String number) {
        if (number.equals(",") && current.contains(",")) return;
        current += number;
    }
    public void selectOperator(String operator) {
        if (current.isEmpty()) {
            this.operator = operator;
            return;
        }
        if (!previous.isEmpty()) {
            calculate();
        }
        this.operator = operator;
        previous = current;
        current = "";
    }
    public void calculate() {
        if (previous.isEmpty() || current.isEmpty()) return;
        double result = 0.0;
        double num1 = Double.parseDouble(previous);
        double num2 = Double.parseDouble(current);
        switch (operator) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "**" -> result = Math.pow(num1, num2);
            case "/" -> {
                if (num2 == 0.0) {
                    current = "NaN";
                    operator = null;
                    previous = "";
                    return;
                }
                result = num1 / num2;
            }
            default -> {
            }
        }
        current = String.valueOf(result);
        operator = null;
        previous = "";
        processOutput();
    }

    public void processOutput() {
        if (!current.isEmpty()) {
            String beforeComma = current.split("\\.")[0];
            String afterComma = current.split("\\.")[1];
            if (afterComma.equals("0")) current = beforeComma;
        }
    }

    private class NumberButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            for (JButton button : nums) {
                if (selectedButton == button) {
                    appendToOutput(button.getText());
                    updateOutput();
                }
            }
        }
    }

    private class OperatorButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            if (selectedButton == buttonAdd) selectOperator(buttonAdd.getText());
            if (selectedButton == buttonSubtract) selectOperator(buttonSubtract.getText());
            if (selectedButton == buttonMultiply) selectOperator(buttonMultiply.getText());
            if (selectedButton == buttonToThePower) selectOperator(buttonToThePower.getText());
            if (selectedButton == buttonDivide) selectOperator(buttonDivide.getText());
            updateOutput();
        }
    }

    private class DelClrEquButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            if (selectedButton == buttonDelete) delete();
            if (selectedButton == buttonClear) clear();
            if (selectedButton == buttonEquals) calculate();
            updateOutput();
        }
    }

    public Calculator(){
        super("Calculator");

        JPanel mainPanel = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel row5 = new JPanel();


        outputScreen = new JTextField(10);
        buttonAdd = new JButton("+");
        buttonSubtract = new JButton("-");
        buttonMultiply = new JButton("*");
        buttonDivide = new JButton("/");
        buttonToThePower = new JButton(("**"));
        buttonEquals = new JButton("=");
        buttonComma = new JButton(",");
        buttonClear = new JButton("Clr");
        buttonDelete = new JButton("Del");
        nums = new JButton[11];
        nums[10] = buttonComma;

        NumberButtonHandler nbh = new NumberButtonHandler();
        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = new JButton(String.valueOf(i));
            nums[i].setFont(new Font("SansSerif", Font.BOLD, 22));
            nums[i].addActionListener(nbh);
        }

        buttonAdd.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonSubtract.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonMultiply.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonToThePower.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonDivide.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonEquals.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonComma.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonClear.setFont(new Font("SansSerif", Font.BOLD, 20));
        buttonDelete.setFont(new Font("SansSerif", Font.BOLD, 20));


        outputScreen.setMaximumSize(new Dimension(180,40));
        outputScreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        outputScreen.setDisabledTextColor(new Color(0,0,0));
        outputScreen.setMargin(new Insets(0,5,0,0));
        outputScreen.setText("0");

        DelClrEquButtonHandler dcebh = new DelClrEquButtonHandler();
        OperatorButtonHandler obh = new OperatorButtonHandler();

        buttonClear.addActionListener(dcebh);
        buttonDelete.addActionListener(dcebh);
        buttonEquals.addActionListener(dcebh);
        buttonComma.addActionListener(nbh);
        buttonAdd.addActionListener(obh);
        buttonSubtract.addActionListener(obh);
        buttonMultiply.addActionListener(obh);
        buttonDivide.addActionListener(obh);
        buttonToThePower.addActionListener(obh);

        row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
        row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
        row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
        row4.setLayout(new BoxLayout(row4, BoxLayout.LINE_AXIS));
        row5.setLayout(new BoxLayout(row5, BoxLayout.LINE_AXIS));


        row1.add(Box.createHorizontalGlue()); row1.add(buttonDelete);
        row1.add(buttonClear); row1.add(buttonToThePower);
        row2.add(nums[7]); row2.add(nums[8]); row2.add(nums[9]); row2.add(buttonMultiply);
        row3.add(nums[4]); row3.add(nums[5]); row3.add(nums[6]); row3.add(buttonAdd);
        row4.add(nums[1]); row4.add(nums[2]); row4.add(nums[3]); row4.add(buttonSubtract);
        row5.add(buttonComma); row5.add(nums[0]); row5.add(buttonEquals); row5.add(buttonDivide);


        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        mainPanel.add(outputScreen);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(205, 280);
    }

    public static void main(String[] args){
        new Calculator();
    }
}
