import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class page2 {

    @FXML
    private Button LangFromEng;

    @FXML
    private Button LangFromViet;

    @FXML
    private Button LangToEng;

    @FXML
    private Button LangToViet;

    @FXML
    private Button autodectectLanguage;
    @FXML
    private TextField ToText;
    @FXML
    private TextField FromText;

    @FXML
    private ChoiceBox<?> selectAPI;

    // Biến để theo dõi nút hiện tại đã được chọn
    private Button currentSelectedButton;
    private Button currentSelectedButtonFrom;

    public void initialize() {
        // List of all buttonsfrom
        Button[] buttonsfrom = {LangFromEng, LangFromViet, autodectectLanguage};

        for (Button button : buttonsfrom) {
            button.setOnAction(event -> {
                // Nếu có nút "from" đã được chọn trước
                if (currentSelectedButtonFrom != null ) {
                    currentSelectedButtonFrom.getStyleClass().remove("button-lang-selected");
                }

                // Thêm lớp CSS vào nút mới và cập nhật nút "from" hiện tại đã được chọn
                button.getStyleClass().add("button-lang-selected");
                currentSelectedButtonFrom = button;


                if (button == autodectectLanguage || button == LangFromEng) {
                    LangToViet.fire();

                }

                // Nếu nút LangFromViet được nhấn, tự động "nhấn" nút LangToEng
                if (button == LangFromViet) {
                    LangToEng.fire();

                }
            });
        }

        // List of all buttonsto
        Button[] buttonsto = {LangToEng, LangToViet};

        for (Button button : buttonsto) {
            button.setOnAction(event -> {
                // Nếu có nút đã được chọn trước đó, loại bỏ lớp CSS từ nút đó
                if (currentSelectedButton != null) {
                    currentSelectedButton.getStyleClass().remove("button-lang-selected");
                }

                // Thêm lớp CSS vào nút mới và cập nhật nút hiện tại đã được chọn
                button.getStyleClass().add("button-lang-selected");
                currentSelectedButton = button;
            });
        }
    }
}