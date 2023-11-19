import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class page1 {
    HashMap<String, String> dic = new HashMap<>();



    public void LoadFileDictionary{
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("src/eng_vie.txt"));
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split("<html>");
                String word = parts[0];
                String definition = "<html>" + parts[1];

                dic.put(word, definition);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void EditDictionary{
        try {
            FileWriter fw = new FileWriter("src/eng_vie.txt");
            for (String key : dic.keySet()) {
                fw.write(key + dic.get(key) + "\n");
            }
            fw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
