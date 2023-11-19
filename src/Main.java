

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

public class Main extends Application{
    Button button;
    double x,y;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mainview.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);


        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX()-x);
            primaryStage.setY(event.getScreenY()-y);
        });

        primaryStage.setScene(new Scene(root,866,638));

        primaryStage.setTitle("Dictionary");
        primaryStage.show();


    }

}