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
    public void addWord(String word, String meaning){
        dic.put(word, meaning);
    }
    public void editWord (){

    }
}
