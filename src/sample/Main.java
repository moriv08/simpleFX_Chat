package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Controller c;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("sample.fxml"));
        root.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
        c = loader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 350, 375));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e->{
            c.Dispose();
            Platform.exit();
            System.exit(0);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
