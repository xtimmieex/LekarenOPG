package sk.spse.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

///
/// Trieda pre deklaratívne vytvorené GUI
///
/// Upravujte hlavne FXML súbor a Controller, nie túto triedu!
///

public class Application extends javafx.application.Application {

    private int counter = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("primary.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Declarative Application 5");
        stage.setScene(scene);
        stage.show();
    }
}