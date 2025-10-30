public class Main {
    public static void main(String[] args) throws Exception {
        // You can edit the default values below or pass them as command line arguments
        int delaySeconds = args.length > 0 ? Integer.parseInt(args[0]) : 60; // Delay between moves (seconds)
        int maxX = args.length > 1 ? Integer.parseInt(args[1]) : 800;        // Max X coordinate
        int maxY = args.length > 2 ? Integer.parseInt(args[2]) : 800;        // Max Y coordinate
        int totalRunMinutes = args.length > 3 ? Integer.parseInt(args[3]) : 300; // Total run time (minutes)

        CursorMover mover = new IdleCursor();
        mover.start(delaySeconds, maxX, maxY, totalRunMinutes);
    }
}
