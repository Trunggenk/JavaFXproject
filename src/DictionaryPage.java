import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class DictionaryPage {
    @FXML
    protected ListView<String> ListWords;
    @FXML
    protected TextField searchField;
    @FXML
    protected WebView webdif;
    @FXML
    protected Button soundUK;
    @FXML
    protected Button soundUS;
    @FXML
    protected Button editButton;
    @FXML
    protected Button saveButton;
    @FXML
    protected ListView<String> viewHistory;
    @FXML
    protected Button removeButton;
    @FXML
    protected Button addButton;

    protected ObservableList<String> dataList;
    protected ObservableList<String> historyList;
    protected HashMap<String, String> dataMap;

    public void initialize() {
        // Khởi tạo trang
    }

    public void searchFieldAction(KeyEvent event) {
        // Xử lý sự kiện tìm kiếm
    }
}

