import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateAPI {

    public static String translate(String fromLanguage, String toLanguage, String inputText) throws IOException {
        String googleScriptUrl = "https://script.google.com/macros/s/AKfycbw1qSfs1Hvfnoi3FzGuoDWijwQW69eGcMM_iGDF7p5vu1oN_CaFqIDFmCGzBuuGCk_N/exec";

        googleScriptUrl += "?q=" + URLEncoder.encode(inputText, "UTF-8");
        googleScriptUrl += "&target=" + toLanguage;
        googleScriptUrl += "&source=" + fromLanguage;

        URL url = new URL(googleScriptUrl);
        StringBuilder translatedText = new StringBuilder();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            translatedText.append(line);
        }
        reader.close();

        return translatedText.toString();
    }
}