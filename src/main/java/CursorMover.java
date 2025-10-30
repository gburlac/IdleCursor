public interface CursorMover {
    /**
     * Starts the cursor movement program.
     * @param delaySeconds Delay between moves in seconds
     * @param maxX Maximum X coordinate
     * @param maxY Maximum Y coordinate
     * @param totalRunMinutes Total run time in minutes
     */
    void start(int delaySeconds, int maxX, int maxY, int totalRunMinutes) throws Exception;
}

