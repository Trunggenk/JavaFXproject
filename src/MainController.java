import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable
{
    @FXML
    private BorderPane Bpmain;

    @FXML
    private Button page1;

    @FXML
    private Label exit;

    @FXML
    void page1(MouseEvent event) {
    loadPage("page1");
    }

    @FXML
    void page2(MouseEvent event) {
    loadPage("page2");
    }

    @FXML
    void page3(MouseEvent event) {

    }

    //load page
    @FXML
    private void loadPage(String page)
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (Exception e) {

        }

        Bpmain.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //exit button
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

    }
}
