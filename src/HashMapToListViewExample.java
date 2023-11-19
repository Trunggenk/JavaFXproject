import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HashMapToListViewExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();

        // Load a HTML string
        String htmlContent = "<html><body><h2>Hello, world!</h2></body></html>";
        webView.getEngine().loadContent(htmlContent, "text/html");

        Scene scene = new Scene(webView, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}