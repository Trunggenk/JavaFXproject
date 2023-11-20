import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

public class page2 extends DictionaryPage {

    @FXML
    private Button LangFromEng;

    @FXML
    private Button LangFromViet;

    @FXML
    private Button LangToEng;

    @FXML
    private Button LangToViet;

    @FXML
    private TextArea ToText;
    @FXML
    private TextArea FromText;

    @FXML
    private ChoiceBox<String> selectAPI;

    // Biến để theo dõi nút "from" và "to" hiện tại đã được chọn
    private Button currentSelectedButtonFrom;
    private Button currentSelectedButtonTo;

    @Override
    public void initialize() {
        super.initialize();
        setupButtons();
        setupChoiceBox();
        setupTextArea();
    }

    private void setupButtons() {
        // Đặt FromButton mặc định là LangFromEng và ToButton mặc định là LangToViet
        currentSelectedButtonFrom = LangFromEng;
        currentSelectedButtonTo = LangToViet;
        LangFromEng.getStyleClass().add("button-lang-selected");
        LangToViet.getStyleClass().add("button-lang-selected");
        // Thêm sự kiện cho các nút
        LangFromEng.setOnAction(event -> {
            handleButtonFromClick(LangFromEng);
            LangToViet.fire();
        });

        LangFromViet.setOnAction(event -> {
            handleButtonFromClick(LangFromViet);
            LangToEng.fire();
        });

        LangToEng.setOnAction(event -> handleButtonToClick(LangToEng));
        LangToViet.setOnAction(event -> handleButtonToClick(LangToViet));
    }

    private void setupChoiceBox() {
        selectAPI.getItems().addAll("Google Translate", "MemoryAPI");
        selectAPI.setValue("Google Translate"); // Tự động chọn "Google Translate"
    }

    private void setupTextArea() {
        FromText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleTextAreaChange(newValue);
            }
        });
    }

    private void handleTextAreaChange(String newValue) {
        // Nếu TextField FromText trống, xóa nội dung của TextField ToText
        if (newValue == null || newValue.isEmpty()) {
            ToText.setText("");
            return;
        }

        // Xác định ngôn ngữ nguồn và đích dựa trên nút đã được chọn
        String langFrom = currentSelectedButtonFrom == LangFromEng ? "en" : "vi";
        String langTo = currentSelectedButtonTo == LangToEng ? "en" : "vi";

        // Tạo một Task để thực hiện việc dịch văn bản trong một thread riêng
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                // Sử dụng TranslateAPI để dịch văn bản
                return TranslateAPI.googleTranslate(langFrom, langTo, newValue);
            }
        };

        // Khi Task hoàn thành, đặt kết quả dịch vào TextField ToText
        task.setOnSucceeded(event -> {
            // Kiểm tra nếu FromText trống sau khi tất cả các task đã hoàn thành
            if (FromText.getText() == null || FromText.getText().isEmpty()) {
                ToText.setText("");
            } else {
                ToText.setText(task.getValue());
            }
        });

        // Bắt đầu thực hiện Task
        new Thread(task).start();
    }

    private void handleButtonFromClick(Button clickedButton) {
        if (currentSelectedButtonFrom != null) {
            currentSelectedButtonFrom.getStyleClass().remove("button-lang-selected");
        }
        clickedButton.getStyleClass().add("button-lang-selected");
        currentSelectedButtonFrom = clickedButton;
    }

    private void handleButtonToClick(Button clickedButton) {
        if (currentSelectedButtonTo != null) {
            currentSelectedButtonTo.getStyleClass().remove("button-lang-selected");
        }
        clickedButton.getStyleClass().add("button-lang-selected");
        currentSelectedButtonTo = clickedButton;
    }
}