import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class HashMapToListViewExample  extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HashMap<String, String> dataMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/eng_vie.txt"));
            String line;
            while ( (line = br.readLine()) != null){
                String[] str = line.split("<html>");
                dataMap.put(str[0], str[1]);
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }

        ObservableList<String> dataList = FXCollections.observableArrayList(dataMap.keySet());

        ListView<String> listView = new ListView<>(dataList);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> filteredData = dataList.filtered(item -> item.toLowerCase().contains(newValue.toLowerCase()));
            listView.setItems(filteredData);
        });

        VBox root = new VBox(searchField, listView);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Search Bar Example");
        primaryStage.show();
    }

}