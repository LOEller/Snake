package src.game;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.Group; 
import javafx.geometry.Bounds;

import java.util.ArrayList;
import java.util.List;


public class Snake {
    private List<SnakeSegment> body = new ArrayList<SnakeSegment>();  
    private Integer sceneHeight;
    private Integer sceneWidth;
    private Group root;
    private Integer counter = 0;
    
    public Snake(Integer sceneWidth, Integer sceneHeight, Group root) {
        this.sceneHeight = sceneHeight;
        this.sceneWidth = sceneWidth;
        this.root = root;
        this.restart();
        this.snakeTimer.start();
    }

    private void restart() {
        this.body.clear();
        this.root.getChildren().clear();
        
        // create head segment in the middle of the game, moving right
        SnakeSegment head = new SnakeSegment(sceneWidth / 2, sceneHeight / 2, 1, 0);
        this.body.add(head);
        root.getChildren().add(head.rectangle);
    }

    private void addTailSegment() {
        // create a new segment with the same direction as the tail of the snake
        // place it one segment-length behind the current tail in the opposite
        // direction that the tail is currently moving
        SnakeSegment tail = this.body.get(this.body.size()-1);
        SnakeSegment seg = new SnakeSegment(
            tail.rectangle.getLayoutX() - 20*tail.deltaX, 
            tail.rectangle.getLayoutY() - 20*tail.deltaY, 
            tail.deltaX, 
            tail.deltaY
        );
        this.body.add(seg);
        root.getChildren().add(seg.rectangle);
    }


    private AnimationTimer snakeTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            SnakeSegment head = body.get(0);

            Boolean bottomCollision = head.rectangle.getLayoutY() + head.rectangle.getHeight() >= sceneHeight;
            Boolean topCollision = head.rectangle.getLayoutY() <= 0;
            Boolean leftCollision = head.rectangle.getLayoutX() <= 0;
            Boolean rightCollision = head.rectangle.getLayoutX() + head.rectangle.getWidth() >= sceneWidth;

            // detect collision between head of snake and game boundaries
            if (bottomCollision || topCollision || leftCollision || rightCollision) {
                restart();
                return;
            }

            // detect collision between head of the snake and any other segment
            Bounds headBounds = head.rectangle.getBoundsInParent();
            for (int i = 3; i < body.size(); i++) {
                Bounds segmentBounds = body.get(i).rectangle.getBoundsInParent();
                if (headBounds.intersects(segmentBounds)) {
                    restart();
                    return;
                }
            }

            // animate each segment in the snake
            for (SnakeSegment segment:body) {
                segment.rectangle.setLayoutY(segment.rectangle.getLayoutY() + 2*segment.deltaY);
                segment.rectangle.setLayoutX(segment.rectangle.getLayoutX() + 2*segment.deltaX);
            }

            // propogate directions from the head backward along the snake, waiting until each segment
            // moves in line with the one in front of it to get the cool "snake" effect
            for (int i = 0; i < body.size()-1; i++) {
                SnakeSegment ahead = body.get(i);
                SnakeSegment behind = body.get(i+1);

                if (!(ahead.deltaX == behind.deltaX && ahead.deltaY == behind.deltaY)) {
                    if (ahead.deltaX == 0 && ahead.rectangle.getLayoutX() == behind.rectangle.getLayoutX()) {
                        behind.deltaX = ahead.deltaX;
                        behind.deltaY = ahead.deltaY;
                    } else if (ahead.deltaY == 0 && ahead.rectangle.getLayoutY() == behind.rectangle.getLayoutY()) {
                        behind.deltaX = ahead.deltaX;
                        behind.deltaY = ahead.deltaY;
                    }
                }
            }
        }
    };

    public void keyPressed(KeyCode keyCode) {
        // temporary: use space to simulate eating a food and adding a segment
        if (keyCode == KeyCode.SPACE) {
            this.addTailSegment();
        } 

        SnakeSegment head = this.body.get(0);
        if (keyCode == KeyCode.UP) {
            if (head.deltaX != 0) {
                head.deltaX = 0;
                head.deltaY = -1;
            }
        } else if (keyCode == KeyCode.DOWN) {
            if (head.deltaX != 0) {
                head.deltaX = 0;
                head.deltaY = 1;
            }
        } else if (keyCode == KeyCode.RIGHT) {
            if (head.deltaY != 0) {
                head.deltaX = 1;
                head.deltaY = 0;
            }
        } else if (keyCode == KeyCode.LEFT) {
            if (head.deltaY != 0) {
                head.deltaX = -1;
                head.deltaY = 0;
            }
        } 
    }
}
