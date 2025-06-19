import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
    private int[][] currentBoard;
    private int[][] nextBoard;
    private final int boardWidth;
    private final int boardHeight;
    private final int threadCount;
    private final ExecutorService executorService;

    public GameOfLife(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.currentBoard = new int[boardHeight][boardWidth];
        this.nextBoard = new int[boardHeight][boardWidth];
        this.threadCount = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
        this.executorService = Executors.newFixedThreadPool(threadCount);
        fillBoardWithRandomValues();
    }

    public int[][] getBoard() {
        return currentBoard;
    }

    public void resetBoard() {
        fillBoardWithRandomValues();
    }

    private void fillBoardWithRandomValues() {
        Random random = new Random();
        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardWidth; c++) {
                currentBoard[r][c] = random.nextInt(2);
                nextBoard[r][c] = 0;
            }
        }
    }

    public void makeTurn() {
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            int finalThreadId = i;
            tasks.add(() -> {
                int cellNumber = finalThreadId;
                while (true) {
                    int row = cellNumber / boardWidth;
                    int col = cellNumber % boardWidth;

                    if (row >= boardHeight) {
                        break;
                    }

                    processCell(row, col);
                    cellNumber += threadCount;
                }
                return null;
            });
        }

        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Game turn interrupted: " + e.getMessage());
        }

        int[][] temp = currentBoard;
        currentBoard = nextBoard;
        nextBoard = temp;
    }

    private int getCellValue(int row, int col) {
        int wrappedRow = (row % boardHeight + boardHeight) % boardHeight;
        int wrappedCol = (col % boardWidth + boardWidth) % boardWidth;
        return currentBoard[wrappedRow][wrappedCol];
    }

    private void processCell(int row, int col) {
        int neighboursCount = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                neighboursCount += getCellValue(row + i, col + j);
            }
        }

        if (currentBoard[row][col] == 1) {
            if (neighboursCount < 2 || neighboursCount > 3) {
                nextBoard[row][col] = 0;
            } else {
                nextBoard[row][col] = 1;
            }
        } else {
            if (neighboursCount == 3) {
                nextBoard[row][col] = 1;
            } else {
                nextBoard[row][col] = 0;
            }
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args) {
        final int BOARD_WIDTH = 80;
        final int BOARD_HEIGHT = 60;
        final int CELL_SIZE = 10;
        final int MIN_UPDATE_INTERVAL_MS = 0;
        final int INTERVAL_STEP_MS = 25;

        int initialUpdateIntervalMs = 150;

        GameOfLife gameOfLife = new GameOfLife(BOARD_WIDTH, BOARD_HEIGHT);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Conway's Game of Life");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            JPanel gamePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    int[][] board = gameOfLife.getBoard();

                    for (int r = 0; r < board.length; r++) {
                        for (int c = 0; c < board[r].length; c++) {
                            if (board[r][c] == 1) {
                                g.setColor(Color.BLACK);
                            } else {
                                g.setColor(Color.WHITE);
                            }
                            g.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                            g.setColor(Color.LIGHT_GRAY);
                            g.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }
                    }
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);
                }
            };

            Timer timer = new Timer(initialUpdateIntervalMs, null);

            JLabel updateIntervalLabel = new JLabel("Update Interval: " + timer.getDelay() + " ms");

            ActionListener timerActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameOfLife.makeTurn();
                    gamePanel.repaint();
                }
            };
            timer.addActionListener(timerActionListener);
            timer.start();

            JButton decreaseIntervalButton = new JButton("-");
            decreaseIntervalButton.addActionListener(e -> {
                timer.stop();
                int currentDelay = timer.getDelay();
                int newDelay = Math.max(MIN_UPDATE_INTERVAL_MS, currentDelay - INTERVAL_STEP_MS);
                timer.setDelay(newDelay);
                updateIntervalLabel.setText("Update Interval: " + newDelay + " ms");
                timer.start();
            });

            JButton restartButton = new JButton("Restart Game");
            restartButton.addActionListener(e -> {
                timer.stop();
                gameOfLife.resetBoard();
                gamePanel.repaint();
                timer.start();
            });


            JButton increaseIntervalButton = new JButton("+");
            increaseIntervalButton.addActionListener(e -> {
                timer.stop();
                int currentDelay = timer.getDelay();
                int newDelay = currentDelay + INTERVAL_STEP_MS;
                timer.setDelay(newDelay);
                updateIntervalLabel.setText("Update Interval: " + newDelay + " ms");
                timer.start();
            });


            JPanel controlPanel = new JPanel();
            controlPanel.add(restartButton);
            controlPanel.add(decreaseIntervalButton);
            controlPanel.add(updateIntervalLabel);
            controlPanel.add(increaseIntervalButton);

            frame.add(gamePanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    timer.stop();
                    gameOfLife.shutdown();
                    System.out.println("Game of Life window closed, executor service shut down.");
                }
            });
        });
    }
}