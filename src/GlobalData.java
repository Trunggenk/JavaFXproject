import javafx.collections.ObservableList;

import java.util.HashMap;

public class GlobalData {
    private static HashMap<String, String> dataMap;
    private static ObservableList<String> historyList;

    public static void setDataMap(HashMap<String, String> map) {
        dataMap = map;
    }

    public static HashMap<String, String> getDataMap() {
        return dataMap;
    }

    public static void setHistoryList(ObservableList<String> list) {
        historyList = list;
    }

    public static ObservableList<String> getHistoryList() {
        return historyList;
    }
}