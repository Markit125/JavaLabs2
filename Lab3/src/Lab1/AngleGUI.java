package Lab1;

import javax.swing.*;
import java.awt.*;

public class AngleGUI extends JFrame {
    private final JFrame frame;
    private final JTextField deg1Field, min1Field;
    private final JTextField deg2Field, min2Field;
    private final JTextArea outputArea;

    public AngleGUI() {
        frame = new JFrame("Angle Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Angles"));

        inputPanel.add(new JLabel("Angle 1"));

        JPanel angle1Fields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        angle1Fields.add(new JLabel("Degrees:"));
        deg1Field = new JTextField("0", 5);
        angle1Fields.add(deg1Field);

        angle1Fields.add(new JLabel("Minutes:"));
        min1Field = new JTextField("0", 5);
        angle1Fields.add(min1Field);

        inputPanel.add(angle1Fields);

        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("Angle 2"));

        JPanel angle2Fields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        angle2Fields.add(new JLabel("Degrees:"));
        deg2Field = new JTextField("0", 5);
        angle2Fields.add(deg2Field);

        angle2Fields.add(new JLabel("Minutes:"));
        min2Field = new JTextField("0", 5);
        angle2Fields.add(min2Field);

        inputPanel.add(angle2Fields);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));

        JButton addBtn = new JButton("Add");
        JButton subBtn = new JButton("Subtract");
        JButton trigBtn = new JButton("Trig");
        JButton incBtn = new JButton("Increase");
        JButton decBtn = new JButton("Decrease");
        JButton clearBtn = new JButton("Clear");

        buttonPanel.add(addBtn);
        buttonPanel.add(subBtn);
        buttonPanel.add(trigBtn);
        buttonPanel.add(incBtn);
        buttonPanel.add(decBtn);
        buttonPanel.add(clearBtn);

        frame.add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        frame.add(scrollPane, BorderLayout.SOUTH);

        addBtn.addActionListener(_ -> performAdd());
        subBtn.addActionListener(_ -> performSub());
        trigBtn.addActionListener(_ -> performTrig());
        incBtn.addActionListener(_ -> performInc());
        decBtn.addActionListener(_ -> performDec());
        clearBtn.addActionListener(_ -> outputArea.setText(""));

        frame.setVisible(true);
    }

    private Angle getAngle1() {
        int d = Integer.parseInt(deg1Field.getText().trim());
        int m = Integer.parseInt(min1Field.getText().trim());
        return new Angle(d, m);
    }

    private Angle getAngle2() {
        int d = Integer.parseInt(deg2Field.getText().trim());
        int m = Integer.parseInt(min2Field.getText().trim());
        return new Angle(d, m);
    }

    private void performAdd() {
        try {
            Angle a1 = getAngle1();
            Angle a2 = getAngle2();
            Angle sum = a1.add(a2);
            outputArea.append("Sum: " + sum + "\n");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void performSub() {
        try {
            Angle a1 = getAngle1();
            Angle a2 = getAngle2();
            Angle diff = a1.subtract(a2);
            outputArea.append("Difference: " + diff + "\n");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void performTrig() {
        try {
            Angle a1 = getAngle1();
            outputArea.append(String.format("Angle 1: %s\n", a1));
            outputArea.append(String.format("sin: %.4f\n", a1.sin()));
            outputArea.append(String.format("cos: %.4f\n", a1.cos()));
            try {
                outputArea.append(String.format("tan: %.4f\n", a1.tan()));
            } catch (ArithmeticException e) {
                outputArea.append("tan: Undefined\n");
            }
            try {
                outputArea.append(String.format("cot: %.4f\n", a1.cot()));
            } catch (ArithmeticException e) {
                outputArea.append("cot: Undefined\n");
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void performInc() {
        try {
            Angle a1 = getAngle1();
            Angle a2 = getAngle2();
            Angle result = a1.add(a2);
            updateAngle1Fields(result);
            outputArea.append("Angle 1 after increase: " + result + "\n");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void performDec() {
        try {
            Angle a1 = getAngle1();
            Angle a2 = getAngle2();
            Angle result = a1.subtract(a2);
            updateAngle1Fields(result);
            outputArea.append("Angle 1 after decrease: " + result + "\n");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateAngle1Fields(Angle angle) {
        deg1Field.setText(String.valueOf(angle.getDegrees()));
        min1Field.setText(String.valueOf(angle.getMinutes()));
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
