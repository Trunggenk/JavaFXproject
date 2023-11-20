import javafx.application.Platform;
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
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.stream.Stream;

public class page1 extends DictionaryPage {

    @FXML
    private HTMLEditor htmledit;
    private String currentWord;
    public void initialize() {
        htmledit.setVisible(false);
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
        historyList = FXCollections.observableArrayList();
        loadHistoryFromFile();
        viewHistory.setItems(historyList);


        ListWords.setOnMouseClicked(event -> {
            String selectedWord = ListWords.getSelectionModel().getSelectedItem();
            if (!historyList.contains(selectedWord)) {
                // Thêm từ vào đầu lịch sử
                historyList.add(0, selectedWord);
                saveHistoryToFile();
            }
            String htmlContent = dataMap.get(selectedWord);
            webdif.getEngine().loadContent(htmlContent, "text/html");
        });
        viewHistory.setOnMouseClicked(event -> {
            String selectedWord = viewHistory.getSelectionModel().getSelectedItem();
            String htmlContent = dataMap.get(selectedWord);
            webdif.getEngine().loadContent(htmlContent, "text/html");
        });

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String word = searchField.getText();
                if (dataMap.containsKey(word)) {
                    currentWord = searchField.getText();
                    String htmlContent = dataMap.get(word);
                    webdif.getEngine().loadContent(htmlContent, "text/html");
                    if (!historyList.contains(word)) {
                        // Thêm từ vào đầu lịch sử
                        historyList.add(0, word);
                        saveHistoryToFile();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy từ");
                    alert.showAndWait();
                }
            }
        });
        soundUS.setOnAction(event -> playSoundUS());
        soundUK.setOnAction(event -> playSoundUK());
        editButton.setOnAction(event -> editWord());
        saveButton.setOnAction(event -> saveWord());
        removeButton.setOnAction(event -> removeWord());
        addButton.setOnAction(event -> addWord());
        GlobalData.setDataMap(dataMap);
        GlobalData.setHistoryList(historyList);


        Platform.runLater(() -> {

            editButton.getScene().setOnKeyPressed(event -> {
                if (event.isControlDown() && event.getCode() == KeyCode.E) {
                    editButton.fire();
                    htmledit.requestFocus();
                    event.consume(); // tiêu thụ sự kiện để nó không lan truyền thêm
                }
            });
            saveButton.getScene().setOnKeyPressed(event -> {
                if (event.isControlDown() && event.getCode() == KeyCode.S) {
                    saveButton.fire();
                    event.consume(); // tiêu thụ sự kiện để nó không lan truyền thêm
                }
            });
        });

    }

    private void playSoundUS() {
        // Code for playing US sound...
                String encodedWord = ListWords.getSelectionModel().getSelectedItem().replace(" ", "_");
                String selectedWord = ListWords.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {

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
                        } else {
                            throw new Exception("File not found");

                        }
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText(null);
                        if (e.getMessage().equals("File not found")) {
                            alert.setContentText("File âm thanh không tồn tại");
                        } else {
                            alert.setContentText("Âm thanh hiện không khả dụng");
                        }
                        alert.showAndWait();
                    }
                }
    }




    private void playSoundUK()
        {
            String encodedWord = ListWords.getSelectionModel().getSelectedItem().replace(" ", "_");
            String selectedWord = ListWords.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {

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
        }
    private void saveWord() {
        String selectedWord = ListWords.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            selectedWord = currentWord;
        }
        if (selectedWord != null) {
            String newMeaning = htmledit.getHtmlText();
            dataMap.put(selectedWord, newMeaning);
            webdif.getEngine().loadContent(newMeaning, "text/html");
            htmledit.setVisible(false);

            // Call writeToFile() after saving the word
            writeToFile();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Sửa thành công");
            alert.showAndWait();
        }
    }
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/eng_vie.txt"))) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                writer.write(entry.getKey() + "<html>" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editWord() {
        String selectedWord = ListWords.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            selectedWord = currentWord;
        }
        if (selectedWord != null) {
            String htmlContent = dataMap.get(selectedWord);
            htmledit.setHtmlText(htmlContent);
            htmledit.setVisible(true);
        }
    }



    private void removeWord() {
        String selectedWord = ListWords.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            selectedWord = currentWord;
        }
        if (selectedWord != null) {
            dataMap.remove(selectedWord);
            dataList.remove(selectedWord);
            webdif.getEngine().loadContent("", "text/html");

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Xoá thành công");
            alert.showAndWait();
        }
    }

    private void addWord() {
        Stage newWordStage = new Stage();

        Label titleLabel = new Label("Thêm từ mới"); // Tiêu đề

        TextField newWordField = new TextField();
        newWordField.setPromptText("Nhập từ mới");

        HTMLEditor newWordMeaningEditor = new HTMLEditor();

        Button saveNewWordButton = new Button("Lưu");
        saveNewWordButton.setOnAction(saveEvent -> {
            String newWord = newWordField.getText();
            String newWordMeaning = newWordMeaningEditor.getHtmlText();

            if (newWord != null && !newWord.isEmpty() && newWordMeaning != null && !newWordMeaning.isEmpty()) {
                if (dataMap.containsKey(newWord)) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận");
                    alert.setHeaderText(null);
                    alert.setContentText("Từ đã tồn tại. Bạn có muốn ghi đè không?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        // Người dùng chọn ghi đè
                        dataMap.put(newWord, newWordMeaning);
                        dataList.remove(newWord);
                        dataList.add(newWord);
                        FXCollections.sort(dataList);
                        newWordStage.close();
                        if (!historyList.contains(newWord)) {
                            // Thêm từ vào đầu lịch sử
                            historyList.add(0, newWord);
                        }
                    } else {
                        // Người dùng chọn không lưu
                        // Không làm gì cả
                    }
                } else {
                    // Từ mới không tồn tại, lưu như bình thường
                    dataMap.put(newWord, newWordMeaning);
                    dataList.add(newWord);
                    FXCollections.sort(dataList);
                    newWordStage.close();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập từ và nghĩa của từ");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, newWordField, newWordMeaningEditor, saveNewWordButton); // Thêm tiêu đề và nút lưu vào layout

        Scene scene = new Scene(layout, 731, 453);
        newWordStage.setScene(scene);
        newWordStage.show();
    }

    private void saveHistoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/history.txt"))) {
            for (String word : historyList) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistoryFromFile() {
        try (Stream<String> stream = Files.lines(Paths.get("src/history.txt"))) {
            stream.forEach(word -> historyList.add(word));
        } catch (IOException e) {
            e.printStackTrace();
        }
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