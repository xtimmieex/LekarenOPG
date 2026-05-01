package sk.spse.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller pre FXML súbor – obsahuje logiku aplikácie
 */
public class Controller {

    private int counter = 0;

    @FXML
    private TextField counterField;

    @FXML
    private void incrementCounter() {
        counter++;
        counterField.setText(String.valueOf(counter));
    }
}
