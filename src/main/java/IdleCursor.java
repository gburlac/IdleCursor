import java.awt.*;
import java.util.Random;

public class IdleCursor {
    public static void main(String[] args) throws AWTException {
        // All time values below are in SECONDS except totalRunMinutes, which is in MINUTES
        // You can edit the default delay (seconds between moves), maxX, maxY, and total run time (minutes) here:
        int delaySeconds = args.length > 0 ? Integer.parseInt(args[0]) : 60; // Delay between moves (seconds)
        int maxX = args.length > 1 ? Integer.parseInt(args[1]) : 800;        // Max X coordinate
        int maxY = args.length > 2 ? Integer.parseInt(args[2]) : 800;        // Max Y coordinate
        int totalRunMinutes = args.length > 3 ? Integer.parseInt(args[3]) : 360; // Total run time (minutes)
        int totalRunSeconds = totalRunMinutes * 60; // Convert minutes to seconds

        Robot hal = new Robot();
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + totalRunSeconds * 1000L;
        while (System.currentTimeMillis() < endTime) {
            for (int i = delaySeconds; i > 0; i--) {
                long timeLeftSeconds = (endTime - System.currentTimeMillis()) / 1000;
                long hours = timeLeftSeconds / 3600;
                long minutes = (timeLeftSeconds % 3600) / 60;
                long seconds = timeLeftSeconds % 60;
                if (timeLeftSeconds <= 0) break;
                String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                System.out.print("Time remaining to next move: " + i + " seconds | Program ends in: " + timeFormatted + "\r");
                hal.delay(1000);
            }
            if (System.currentTimeMillis() >= endTime) break;
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            hal.mouseMove(x, y);
            System.out.println("Moved cursor to: (" + x + ", " + y + ")");
        }
        System.out.println("Program finished after " + totalRunMinutes + " minutes.");
    }
}
