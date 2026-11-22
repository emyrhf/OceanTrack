package boundary;

import javafx.application.Application;
import javafx.stage.Stage;
public class Principal extends Application {
    @Override 
    public void start(Stage stage) {
        new EspecieMarinhaView(stage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}