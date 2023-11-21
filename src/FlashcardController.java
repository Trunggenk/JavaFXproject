import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class FlashcardController {
    @FXML
    private ListView<String> ListOfCard;
    @FXML
    private Button addAllfromhistory;
    @FXML
    private Button addButton;
    @FXML
    private Button start;
    private int currentCardIndex = 0;


    private List<Flashcard> cards = new ArrayList<>();

    /**
     * start.
     */
    public void initialize() {
        addAllfromhistory.setOnAction(event -> addAllFromHistory());
        addButton.setOnAction(event -> addCard());
        start.setOnAction(event -> startLearning()); // Add action for start button

    }

    /**
     * add all words from history.
     */
    private void addAllFromHistory() {
        HashMap<String, String> dataMap = GlobalData.getDataMap();
        List<String> history = readHistoryFromFile();

        for (String front : history) {
            String back = dataMap.get(front);
            if (back != null) {
                cards.add(new Flashcard(front, back));
            }
        }

        updateListView();
    }

    /**
     * add card.
     */
    private void addCard() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Card");
        dialog.setHeaderText("Enter the front and back of the card:");
        dialog.setContentText("Front:");
        Optional<String> front = dialog.showAndWait();

        dialog.setContentText("Back:");
        Optional<String> back = dialog.showAndWait();

        if (front.isPresent() && back.isPresent()) {
            cards.add(new Flashcard(front.get(), back.get()));
            updateListView();
        }
    }

    /**
     * update the list.
     */
    private void updateListView() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Flashcard card : cards) {
            observableList.add(card.getFront());
        }
        ListOfCard.setItems(observableList);
    }

    /**
     * read history from file.
     * @return
     */
    private List<String> readHistoryFromFile() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    /**
     * learn.
     */
    private void startLearning() {
        if (cards.isEmpty()) {
            return;
        }
        if (currentCardIndex >= cards.size()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Completion Message");
            alert.setHeaderText(null);
            alert.setContentText("You have completed all the flashcards!");

            alert.showAndWait();
            return; // Exit the method if all flashcards have been viewed
        }

        Flashcard currentCard = cards.get(currentCardIndex);
        Stage learningStage = new Stage();
        learningStage.setHeight(400); // Set the height of the Stage
        learningStage.setWidth(600); // Set the width of the Stage


        VBox layout = new VBox();
        Label frontLabel = new Label(currentCard.getFront());
        frontLabel.setTextAlignment(TextAlignment.CENTER); // Center the text within the label
        frontLabel.setAlignment(Pos.CENTER);
        WebView backWebView = new WebView();
        WebEngine webEngine = backWebView.getEngine();
        webEngine.loadContent(currentCard.getBack());
        backWebView.setVisible(false);
        frontLabel.getStyleClass().add("label");

        frontLabel.setOnMouseClicked(event -> {
            backWebView.setVisible(true);
        });

        backWebView.setOnMouseClicked(event -> {
            learningStage.close();
            startLearning();
        });

        layout.getChildren().addAll(frontLabel, backWebView);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("flashcardview.css").toExternalForm());
        learningStage.setScene(scene);
        learningStage.show();

        playSoundUS(currentCard.getFront());
        currentCardIndex++;
    }

    /**
     * soundUS.
     * @param selectedWord word
     */
    private void playSoundUS(String selectedWord) {

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
                e.printStackTrace();
            }
        }
    }
}