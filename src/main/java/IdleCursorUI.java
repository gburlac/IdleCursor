import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdleCursorUI {
    private static volatile boolean shouldStop = false;
    private static Thread moverThread = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Idle Cursor UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(10, 2, 5, 5));

        JLabel delayLabel = new JLabel("Delay (seconds):");
        JTextField delayField = new JTextField("60");
        JLabel maxXLabel = new JLabel("Max X:");
        JTextField maxXField = new JTextField("800");
        JLabel maxYLabel = new JLabel("Max Y:");
        JTextField maxYField = new JTextField("800");
        JLabel minutesLabel = new JLabel("Total Run (minutes):");
        JTextField minutesField = new JTextField("300");
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        JLabel statusLabel = new JLabel(" ");
        JLabel timeLabel = new JLabel("Time left: --:--:--");
        JLabel positionLabel = new JLabel("Last position: (---, ---)");
        JLabel nextMoveLabel = new JLabel("Next move in: -- seconds");
        JLabel currentTimeLabel = new JLabel("Current time: --:--:--");
        JLabel endTimeLabel = new JLabel("Actual end time: --:--:--");

        frame.add(delayLabel);
        frame.add(delayField);
        frame.add(maxXLabel);
        frame.add(maxXField);
        frame.add(maxYLabel);
        frame.add(maxYField);
        frame.add(minutesLabel);
        frame.add(minutesField);
        frame.add(startButton);
        frame.add(stopButton);
        frame.add(timeLabel);
        frame.add(positionLabel);
        frame.add(nextMoveLabel);
        frame.add(currentTimeLabel);
        frame.add(endTimeLabel);
        frame.add(new JLabel());
        frame.add(statusLabel);

        // Update current time every second
        Timer clockTimer = new Timer(1000, evt -> {
            String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
            currentTimeLabel.setText("Current time: " + now);
        });
        clockTimer.start();

        startButton.addActionListener(e -> {
            try {
                int delaySeconds = Integer.parseInt(delayField.getText());
                int maxX = Integer.parseInt(maxXField.getText());
                int maxY = Integer.parseInt(maxYField.getText());
                int totalRunMinutes = Integer.parseInt(minutesField.getText());
                statusLabel.setText("Program started...");
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                shouldStop = false;
                long expectedEndMillis = System.currentTimeMillis() + totalRunMinutes * 60 * 1000L;
                String expectedEnd = new SimpleDateFormat("HH:mm:ss").format(new Date(expectedEndMillis));
                SwingUtilities.invokeLater(() -> endTimeLabel.setText("Actual end time: " + expectedEnd));
                moverThread = new Thread(() -> {
                    try {
                        int totalRunSeconds = totalRunMinutes * 60;
                        Robot hal = new Robot();
                        java.util.Random random = new java.util.Random();
                        long startTime = System.currentTimeMillis();
                        long endTime = startTime + totalRunSeconds * 1000L;
                        while (System.currentTimeMillis() < endTime && !shouldStop) {
                            for (int i = delaySeconds; i > 0; i--) {
                                if (shouldStop) break;
                                long timeLeftSeconds = (endTime - System.currentTimeMillis()) / 1000;
                                long hours = timeLeftSeconds / 3600;
                                long minutes = (timeLeftSeconds % 3600) / 60;
                                long seconds = timeLeftSeconds % 60;
                                if (timeLeftSeconds <= 0) break;
                                String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                                int nextMove = i;
                                SwingUtilities.invokeLater(() -> {
                                    timeLabel.setText("Time left: " + timeFormatted);
                                    nextMoveLabel.setText("Next move in: " + nextMove + " seconds");
                                });
                                hal.delay(1000);
                            }
                            if (System.currentTimeMillis() >= endTime || shouldStop) break;
                            int x = random.nextInt(maxX);
                            int y = random.nextInt(maxY);
                            hal.mouseMove(x, y);
                            SwingUtilities.invokeLater(() -> positionLabel.setText("Last position: (" + x + ", " + y + ")"));
                        }
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText(shouldStop ? "Program stopped." : "Program finished.");
                            startButton.setEnabled(true);
                            stopButton.setEnabled(false);
                            timeLabel.setText("Time left: --:--:--");
                            nextMoveLabel.setText("Next move in: -- seconds");
                            endTimeLabel.setText("Actual end time: --:--:--");
                        });
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText("Error: " + ex.getMessage());
                            startButton.setEnabled(true);
                            stopButton.setEnabled(false);
                            timeLabel.setText("Time left: --:--:--");
                            nextMoveLabel.setText("Next move in: -- seconds");
                            endTimeLabel.setText("Actual end time: --:--:--");
                        });
                    }
                });
                moverThread.start();
            } catch (NumberFormatException ex) {
                statusLabel.setText("Invalid input!");
            }
        });

        stopButton.addActionListener(e -> {
            shouldStop = true;
            statusLabel.setText("Stopping...");
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
            endTimeLabel.setText("Actual end time: --:--:--");
        });

        frame.setVisible(true);
    }
}
