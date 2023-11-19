import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class page1 {

    @FXML
    private ListView<String> ListWords;

    @FXML
    private TextField searchField;
    @FXML
    private WebView webdif;
    @FXML
    private Button soundUK;

    @FXML
    private Button soundUS;


    private ObservableList<String> dataList;
    private HashMap<String, String> dataMap;

    public void initialize() {
        dataMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/eng_vie.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("<html>");
                dataMap.put(str[0], str[1]);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        dataList = FXCollections.observableArrayList(dataMap.keySet());
        FXCollections.sort(dataList);
        ListWords.setItems(dataList);


        ListWords.setOnMouseClicked(event -> {
            String selectedWord = ListWords.getSelectionModel().getSelectedItem();
            String htmlContent = dataMap.get(selectedWord);
            webdif.getEngine().loadContent(htmlContent,"text/html");
        });




        soundUS.setOnAction(event -> {
            String selectedWord = ListWords.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                String encodedWord = selectedWord.replace(" ", "%20");
                String urlStr = "https://ssl.gstatic.com/dictionary/static/sounds/oxford/" + selectedWord + "--_us_1.mp3";
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("HEAD");
                    int responseCode = huc.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Media sound = new Media(urlStr);
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play();
                    } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                        throw new Exception("File not found");
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Âm thanh hiện không khả dụng");
                    alert.showAndWait();
                }
            }
        });
        soundUK.setOnAction(event -> {
            String selectedWord = ListWords.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                String encodedWord = selectedWord.replace(" ", "%20");
                String urlStr = "https://ssl.gstatic.com/dictionary/static/sounds/oxford/" + encodedWord + "--_gb_1.mp3";
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("HEAD");
                    int responseCode = huc.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Media sound = new Media(urlStr);
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play();
                    } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                        throw new Exception("File not found");
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Âm thanh hiện không khả dụng");
                    alert.showAndWait();
                }
            }
        });
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String word = searchField.getText();
                if (dataMap.containsKey(word)) {
                    String htmlContent = dataMap.get(word);
                    webdif.getEngine().loadContent(htmlContent, "text/html");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy từ");
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    void searchFieldAction(KeyEvent event) {
        String newValue = searchField.getText();
        if (newValue != null) {
            ObservableList<String> filteredData = FXCollections.observableArrayList(dataList.filtered(item -> item != null && item.toLowerCase().startsWith(newValue.toLowerCase())));
            FXCollections.sort(filteredData);
            ListWords.setItems(filteredData);
        }
    }
}