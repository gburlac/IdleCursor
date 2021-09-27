import java.awt.*;
import java.util.Random;

public class IdleCursor {

    public static void main(String[] args) throws AWTException {
        Robot hal = new Robot();
        Random random = new Random();
        while (true){
           hal.delay(1000 * 60);
           int x = random.nextInt() % 800;
           int y = random.nextInt()% 800;
           hal.mouseMove(x,y);
        }
    }
}
