package sk.spse.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sk.spse.lekaren.Lekaren;
import sk.spse.lekaren.Liek;
import sk.spse.lekaren.Json;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML private ListView<Liek> zoznamLiekov;
    @FXML private Label infoLabel;

    private Lekaren lekaren = new Lekaren();

    @FXML
    public void initialize() {
        try {
            List<Liek> lieky = Json.nacitajLieky();
            for (Liek l : lieky) lekaren.pridatLiek(l);
            infoLabel.setText("Dáta úspešne načítané zo súboru.");
        } catch (Exception e) {
            infoLabel.setText("Program pokračuje s prázdnym skladom.");
        }
        ukazVsetky();
    }

    // --- 1. Zobraziť podľa látky ---
    @FXML public void ukazPodlaLatky() {
        zoznamLiekov.getItems().setAll(lekaren.ziskatLiekyPodlaUcinnejLatky());
        infoLabel.setText("Zobrazené lieky zoradené podľa účinnej látky.");
    }

    // --- 2. Zobraziť všetky ---
    @FXML public void ukazVsetky() {
        zoznamLiekov.getItems().setAll(lekaren.ziskatVsetkyLieky());
        infoLabel.setText("Zobrazené všetky lieky v sklade.");
    }

    // --- 3. Zobraziť expirované ---
    @FXML public void ukazExpirovane() {
        zoznamLiekov.getItems().setAll(lekaren.ziskatExpirovanyLieky());
        infoLabel.setText("Zobrazené expirované lieky.");
    }

    // --- 4. Vyhľadať liek ---
    @FXML public void vyhladatLiek() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vyhľadávanie");
        dialog.setHeaderText("Zadajte názov hľadaného lieku:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nazov -> {
            List<Liek> najdene = lekaren.vyhladatPodlaNazvu(nazov);
            zoznamLiekov.getItems().setAll(najdene);
            infoLabel.setText("Výsledky hľadania pre: " + nazov);
        });
    }

    // --- 5. Pridať liek ---
    @FXML public void pridatLiek() {
        // Jednoduchý formulár cez predpripravený Dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Pridať liek");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        TextField fNazov = new TextField();
        TextField fLatka = new TextField();
        DatePicker fDatum = new DatePicker(LocalDate.now().plusYears(1));
        TextField fCena = new TextField("0.0");
        TextField fKusy = new TextField("1");

        grid.add(new Label("Názov:"), 0, 0); grid.add(fNazov, 1, 0);
        grid.add(new Label("Látka:"), 0, 1); grid.add(fLatka, 1, 1);
        grid.add(new Label("Expirácia:"), 0, 2); grid.add(fDatum, 1, 2);
        grid.add(new Label("Cena (€):"), 0, 3); grid.add(fCena, 1, 3);
        grid.add(new Label("Množstvo:"), 0, 4); grid.add(fKusy, 1, 4);
        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                double cena = Double.parseDouble(fCena.getText());
                int kusy = Integer.parseInt(fKusy.getText());
                lekaren.naskladnitLiek(fNazov.getText(), fLatka.getText(), fDatum.getValue(), cena, kusy);
                ukazVsetky();
                infoLabel.setText("Liek úspešne pridaný.");
            } catch (Exception e) {
                infoLabel.setText("Chyba: Zlý formát čísla!");
            }
        }
    }

    // --- 6. Predaj lieku ---
    @FXML public void predatLiek() {
        Liek vybrany = zoznamLiekov.getSelectionModel().getSelectedItem();
        if (vybrany == null) {
            infoLabel.setText("Najprv musíte kliknúť na liek v zozname!");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setHeaderText("Koľko kusov lieku " + vybrany.getNazov() + " chcete predať?");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(kusyStr -> {
            try {
                int kusy = Integer.parseInt(kusyStr);
                if (lekaren.predatLiek(vybrany, kusy)) {
                    infoLabel.setText("Úspešne predané " + kusy + " ks lieku " + vybrany.getNazov());
                    zoznamLiekov.refresh(); // Aktualizuje zobrazenie zoznamu
                } else {
                    infoLabel.setText("Chyba: Nedostatok kusov na sklade!");
                }
            } catch (Exception e) {
                infoLabel.setText("Chyba: Zadajte platné číslo!");
            }
        });
    }

    // --- 7. Vyradiť liek ---
    @FXML public void vyraditLiek() {
        Liek vybrany = zoznamLiekov.getSelectionModel().getSelectedItem();
        if (vybrany == null) {
            infoLabel.setText("Najprv musíte kliknúť na liek v zozname!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Naozaj vyradiť " + vybrany.getNazov() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            lekaren.odstranLiek(vybrany);
            ukazVsetky();
            infoLabel.setText("Liek vyradený.");
        }
    }

    // --- 8. Vyradiť expirované ---
    @FXML public void vyraditExpirovane() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Naozaj chcete vyradiť VŠETKY expirované lieky?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            lekaren.vyraditExpirovanyLieky();
            ukazVsetky();
            infoLabel.setText("Všetky expirované lieky boli vyradené.");
        }
    }
}