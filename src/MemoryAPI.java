import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MemoryAPI {
    private static final String BASE_URL = "https://api.mymemory.translated.net";

    public String get(String query, String langpair) throws Exception {
        String url = BASE_URL + "/get?q=" + query + "&langpair=" + langpair;
        return sendGetRequest(url);
    }

    public String keygen(String user, String pass) throws Exception {
        String url = BASE_URL + "/keygen?user=" + user + "&pass=" + pass;
        return sendGetRequest(url);
    }

    public String set(String seg, String tra, String langpair) throws Exception {
        String url = BASE_URL + "/set?seg=" + seg + "&tra=" + tra + "&langpair=" + langpair;
        return sendGetRequest(url);
    }

    // The import method is not included because it requires a multipart/form-data request, which is more complex to implement

    public String status(String uuid) throws Exception {
        String url = BASE_URL + "/v2/import/status?uuid=" + uuid;
        return sendGetRequest(url);
    }

    private String sendGetRequest(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}