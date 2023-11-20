import javafx.fxml.FXML;
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
import java.util.HashMap;

public class page3 {
    @FXML
    private ListView<String> ListHistoryInPage3;
    @FXML
    private Button crossword;
    @FXML
    private Button flashcard;

    public void initialize() {
        HashMap<String, String> dataMap = GlobalData.getDataMap();
        ListHistoryInPage3.setItems(GlobalData.getHistoryList());
        flashcard.setOnAction(event -> createFlashcard());
        ContextMenu contextMenu = new ContextMenu();

        // Create a menu item
        MenuItem deleteItem = new MenuItem("Delete word");
        deleteItem.setOnAction(event -> removeWordFromHistory());

        // Add menu item to context menu
        contextMenu.getItems().add(deleteItem);

        // Set context menu to the history list
        ListHistoryInPage3.setContextMenu(contextMenu);
        ListHistoryInPage3.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            // If this word is the selected word, set the text color to red
                            if (item.equals(ListHistoryInPage3.getSelectionModel().getSelectedItem())) {
                                setTextFill(Color.RED);
                            } else {
                                setTextFill(Color.BLACK);
                            }
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void createFlashcard() {
        String selectedWord = ListHistoryInPage3.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một từ để tạo flashcard");
            alert.showAndWait();
            return;
        }

        Stage flashcardStage = new Stage();
        VBox layout = new VBox();
        Text wordText = new Text(selectedWord);
        Button flipButton = new Button("Lật flashcard");
        flipButton.setOnAction(event -> wordText.setText(GlobalData.getDataMap().get(selectedWord)));
        Button closeButton = new Button("Đóng flashcard");
        closeButton.setOnAction(event -> flashcardStage.close());
        layout.getChildren().addAll(wordText, flipButton, closeButton);
        flashcardStage.setScene(new Scene(layout));
        flashcardStage.show();
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

}