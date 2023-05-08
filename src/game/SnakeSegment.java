package src.game;
import javafx.scene.shape.Rectangle;

public class SnakeSegment {
    public Integer deltaX;
    public Integer deltaY;
    public Rectangle rectangle;

    public SnakeSegment (double initialX, double initialY, Integer deltaX, Integer deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.rectangle = new Rectangle(20, 20);
        this.rectangle.relocate(initialX, initialY);
    }
}
