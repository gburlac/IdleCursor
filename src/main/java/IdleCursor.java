import java.awt.*;
import java.util.Random;

public class IdleCursor implements CursorMover {
    /**
     * Starts the cursor movement program.
     * @param delaySeconds Delay between moves in seconds
     * @param maxX Maximum X coordinate
     * @param maxY Maximum Y coordinate
     * @param totalRunMinutes Total run time in minutes
     */
    @Override
    public void start(int delaySeconds, int maxX, int maxY, int totalRunMinutes) throws Exception {
        System.out.println("Program started. Moving cursor every " + delaySeconds + " seconds for " + totalRunMinutes + " minutes. Max coordinates: (" + maxX + ", " + maxY + ")");
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
                System.out.print("Next move in... " + i + " seconds | Program ends in: " + timeFormatted + "\r");
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

    // Optional: Keep main for quick testing, but Main.java is now the recommended entry point
    public static void main(String[] args) throws Exception {
        int delaySeconds = args.length > 0 ? Integer.parseInt(args[0]) : 60;
        int maxX = args.length > 1 ? Integer.parseInt(args[1]) : 800;
        int maxY = args.length > 2 ? Integer.parseInt(args[2]) : 800;
        int totalRunMinutes = args.length > 3 ? Integer.parseInt(args[3]) : 300;
        new IdleCursor().start(delaySeconds, maxX, maxY, totalRunMinutes);
    }
}
