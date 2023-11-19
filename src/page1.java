import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
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