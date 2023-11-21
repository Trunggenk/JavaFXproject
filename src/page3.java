import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class page3 {
    @FXML
    protected ListView<String> ListHistoryInPage3;
    @FXML
    protected Button crossword;
    @FXML
    protected Button flashcard;

    public void initialize() {
        HashMap<String, String> dataMap = GlobalData.getDataMap();
        ListHistoryInPage3.setItems(GlobalData.getHistoryList());
        flashcard.setOnAction(event -> openFlashcard());

        ContextMenu contextMenu = new ContextMenu();

        // Create a menu item
        MenuItem deleteItem = new MenuItem("Delete word");
        deleteItem.setOnAction(event -> removeWordFromHistory());

        // Add menu item to context menu
        contextMenu.getItems().add(deleteItem);

        // Set context menu to the history list
        ListHistoryInPage3.setContextMenu(contextMenu);

    }


    private void removeWordFromHistory() {
        String selectedWord = ListHistoryInPage3.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            GlobalData.getHistoryList().remove(selectedWord);
            saveHistoryToFile();
        }
    }
    private void saveHistoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/history.txt"))) {
            for (String word : GlobalData.getHistoryList()) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openFlashcard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("flashcard.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}