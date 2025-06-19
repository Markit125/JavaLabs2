import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NumberWriter extends JFrame {

    private final JTextField textField1;
    private final JTextField textField2;

    private NumberGeneratorThread currentGeneratorThread1;
    private NumberGeneratorThread currentGeneratorThread2;

    public NumberWriter() {
        setTitle("Number Generator");
        setSize(450, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton button1 = new JButton("1");
        JButton button2 = new JButton("2");

        textField1 = new JTextField(15);
        textField2 = new JTextField(15);

        textField1.setEditable(false);
        textField2.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        textFieldPanel.add(textField1);
        textFieldPanel.add(textField2);

        setLayout(new BorderLayout(10, 10));

        add(buttonPanel, BorderLayout.NORTH);
        add(textFieldPanel, BorderLayout.CENTER);

        button1.addActionListener(e -> {
            if (currentGeneratorThread1 == null || !currentGeneratorThread1.isAlive()) {
                textField1.setText("");
                currentGeneratorThread1 = new NumberGeneratorThread(textField1);
                currentGeneratorThread1.start();
            } else {
                currentGeneratorThread1.stopRunning();
                currentGeneratorThread1 = null;
            }
        });

        button2.addActionListener(e -> {
            if (currentGeneratorThread2 == null || !currentGeneratorThread2.isAlive()) {
                textField2.setText("");
                currentGeneratorThread2 = new NumberGeneratorThread(textField2);
                currentGeneratorThread2.start();
            } else {
                currentGeneratorThread2.stopRunning();
                currentGeneratorThread2 = null;
            }
        });
    }

    private static class NumberGeneratorThread extends Thread {
        private final JTextField targetTextField;
        private final Random random = new Random();
        private boolean running = true;

        public NumberGeneratorThread(JTextField targetTextField) {
            this.targetTextField = targetTextField;
        }

        public void stopRunning() {
            running = false;
            this.interrupt();
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    int randomNumber = random.nextInt(10);

                    SwingUtilities.invokeLater(() -> {
                        String currentText = targetTextField.getText();
                        if (currentText.length() > 50) {
                            currentText = currentText.substring(currentText.length() - 49);
                        }
                        targetTextField.setText(currentText + i);
                    });

                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    if (!running) {
                        break;
                    }

                    Thread.currentThread().interrupt();
                    System.err.println("Number generation thread interrupted unexpectedly: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberWriter().setVisible(true));
    }
}