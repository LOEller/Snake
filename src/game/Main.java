package src.game;
import javafx.application.Application; 
import javafx.stage.Stage;
import javafx.scene.Group; 
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;


public class Main extends Application {  
    @Override     
    public void start(Stage primaryStage) throws Exception { 
      Integer sceneHeight = 600;
      Integer sceneWidth = 600;

      Group root = new Group();
      Snake snake = new Snake(sceneHeight, sceneWidth, root);
      Scene scene = new Scene(root, sceneHeight, sceneWidth);

      scene.setOnKeyPressed((KeyEvent event) -> {
         snake.keyPressed(event.getCode());
      });

      primaryStage.setTitle("Snake!"); 
      primaryStage.setScene(scene); 
      primaryStage.show();
    }         
    public static void main(String args[]){           
       launch(args);      
    } 
 } 