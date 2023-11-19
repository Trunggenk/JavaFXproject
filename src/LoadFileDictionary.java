import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class LoadFileDictionary{
    HashMap<String, String> dic = new HashMap<>();
    public void LoadFileDictionary () {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/dictionaries.txt"));
            String line;
            while ( (line = br.readLine()) != null){
                String[] str = line.split("<html>");
                dic.put(str[0], str[1]);
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }
    public void searchPrefix(String prefix) {
        int i = 1;
        for (String key : this.dic.keySet()) {
            if (key.startsWith(prefix)) {
                System.out.println(i + "\t|" + key + "\t\t|" + this.dic.get(key));
                i++;
            }
        }
    }


}
