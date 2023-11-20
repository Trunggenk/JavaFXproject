import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.HashMap;

public class page3 {
    @FXML
    private ListView<String> ListHistoryInPage3;

    public void initialize() {
        HashMap<String, String> dataMap = GlobalData.getDataMap();
        ListHistoryInPage3.setItems(GlobalData.getHistoryList());

    }
}