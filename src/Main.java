

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

public class Main extends Application{
    Button button;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mainview.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            root.getScene().getWindow().setX(event.getScreenX() - root.getScene().getWindow().getX());
            root.getScene().getWindow().setY(event.getScreenY() - root.getScene().getWindow().getY());
        });

        primaryStage.setScene(new Scene(root,873,648));

        primaryStage.setTitle("Dictionary");
        primaryStage.show();



    }

}