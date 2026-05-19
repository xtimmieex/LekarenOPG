package sk.spse.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import sk.spse.lekaren.Json;
import sk.spse.lekaren.Lekaren;
import sk.spse.lekaren.Liek;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML private TableView<Liek> tabulkaLiekov;
    @FXML private TableColumn<Liek, String> colNazov;
    @FXML private TableColumn<Liek, String> colLatka;
    @FXML private TableColumn<Liek, Integer> colKusy;
    @FXML private TableColumn<Liek, Double> colCena;
    @FXML private TableColumn<Liek, LocalDate> colExpiracia;

    @FXML private Label infoLabel;

    private Lekaren lekaren = new Lekaren();

    @FXML
    public void initialize() {
        colNazov.setCellValueFactory(new PropertyValueFactory<>("nazov"));
        colLatka.setCellValueFactory(new PropertyValueFactory<>("ucinnaLatka"));
        colKusy.setCellValueFactory(new PropertyValueFactory<>("mnozstvo"));
        colCena.setCellValueFactory(new PropertyValueFactory<>("cena"));

        // OPRAVENÝ RIADOK: Nastavené na "datumExpiracie", aby to sedelo s getDatumExpiracie() v triede Liek
        colExpiracia.setCellValueFactory(new PropertyValueFactory<>("datumExpiracie"));

        try {
            List<Liek> lieky = Json.nacitajLieky();

            if (lieky != null) {
                for (Liek l : lieky) {
                    if (l != null) {
                        lekaren.pridatLiek(l);
                    }
                }
                infoLabel.setText("Dáta úspešne načítané zo súboru.");
            } else {
                infoLabel.setText("Súbor neobsahuje žiadne dáta.");
            }
        } catch (Exception e) {
            infoLabel.setText("Program pokračuje s prázdnym skladom.");
        }

        ukazVsetky();
    }

    @FXML
    public void ukazPodlaLatky() {
        List<Liek> lieky = lekaren.ziskatLiekyPodlaUcinnejLatky();
        tabulkaLiekov.getItems().setAll(lieky);

        if (lieky.isEmpty()) {
            infoLabel.setText("Sklad je prázdny.");
        } else {
            infoLabel.setText("Zobrazené lieky zoradené podľa účinnej látky.");
        }
    }

    @FXML
    public void ukazVsetky() {
        List<Liek> lieky = lekaren.ziskatVsetkyLieky();
        tabulkaLiekov.getItems().setAll(lieky);

        if (lieky.isEmpty()) {
            infoLabel.setText("Sklad je prázdny.");
        } else {
            infoLabel.setText("Zobrazené všetky lieky v sklade.");
        }
    }

    @FXML
    public void ukazExpirovane() {
        List<Liek> expirovane = lekaren.ziskatExpirovanyLieky();
        tabulkaLiekov.getItems().setAll(expirovane);

        if (expirovane.isEmpty()) {
            infoLabel.setText("V sklade nie sú žiadne expirované lieky.");
        } else {
            infoLabel.setText("Zobrazené expirované lieky.");
        }
    }

    @FXML
    public void vyhladatLiek() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vyhľadávanie");
        dialog.setHeaderText("Zadajte názov hľadaného lieku:");

        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) {
            infoLabel.setText("Vyhľadávanie bolo zrušené.");
            return;
        }

        String nazov = result.get().trim();

        if (nazov.isEmpty()) {
            infoLabel.setText("Chyba: Názov lieku nemôže byť prázdny.");
            return;
        }

        List<Liek> najdene = lekaren.vyhladatPodlaNazvu(nazov);
        tabulkaLiekov.getItems().setAll(najdene);

        if (najdene.isEmpty()) {
            infoLabel.setText("Nenašiel sa žiadny liek s názvom: " + nazov);
        } else {
            infoLabel.setText("Výsledky hľadania pre: " + nazov);
        }
    }

    @FXML
    public void pridatLiek() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Pridať liek");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField fNazov = new TextField();
        TextField fLatka = new TextField();
        DatePicker fDatum = new DatePicker(LocalDate.now().plusYears(1));
        TextField fCena = new TextField();
        TextField fKusy = new TextField();

        fNazov.setPromptText("napr. Paralen");
        fLatka.setPromptText("napr. Paracetamol");
        fCena.setPromptText("napr. 4.50");
        fKusy.setPromptText("napr. 10");

        grid.add(new Label("Názov:"), 0, 0);
        grid.add(fNazov, 1, 0);
        grid.add(new Label("Látka:"), 0, 1);
        grid.add(fLatka, 1, 1);
        grid.add(new Label("Expirácia:"), 0, 2);
        grid.add(fDatum, 1, 2);
        grid.add(new Label("Cena (€):"), 0, 3);
        grid.add(fCena, 1, 3);
        grid.add(new Label("Množstvo:"), 0, 4);
        grid.add(fKusy, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isEmpty() || result.get() != ButtonType.OK) {
            infoLabel.setText("Pridanie lieku bolo zrušené.");
            return;
        }

        String nazov = fNazov.getText().trim();
        String latka = fLatka.getText().trim();
        LocalDate datum = fDatum.getValue();
        Double cena = nacitajCenu(fCena.getText());
        Integer kusy = nacitajKusy(fKusy.getText());

        if (nazov.isEmpty()) {
            infoLabel.setText("Chyba: Názov lieku nemôže byť prázdny.");
            return;
        }

        if (latka.isEmpty()) {
            infoLabel.setText("Chyba: Účinná látka nemôže byť prázdna.");
            return;
        }

        if (datum == null) {
            infoLabel.setText("Chyba: Dátum expirácie musí byť vyplnený.");
            return;
        }

        if (datum.isBefore(LocalDate.now())) {
            infoLabel.setText("Chyba: Nemožno pridať liek s expiráciou v minulosti.");
            return;
        }

        if (cena == null) {
            infoLabel.setText("Chyba: Cena musí byť platné číslo väčšie ako 0.");
            return;
        }

        if (kusy == null) {
            infoLabel.setText("Chyba: Množstvo musí byť celé číslo väčšie ako 0.");
            return;
        }

        lekaren.naskladnitLiek(nazov, latka, datum, cena, kusy);
        ukazVsetky();
        infoLabel.setText("Liek bol úspešne pridaný.");
    }

    @FXML
    public void predatLiek() {
        if (lekaren.ziskatVsetkyLieky().isEmpty()) {
            infoLabel.setText("Sklad je prázdny. Nie je možné predať liek.");
            return;
        }

        Liek vybrany = tabulkaLiekov.getSelectionModel().getSelectedItem();

        if (vybrany == null) {
            infoLabel.setText("Najprv musíte kliknúť na liek v zozname.");
            return;
        }

        if (vybrany.jeExpirrovany()) {
            infoLabel.setText("Chyba: Expirovaný liek nie je možné predať.");
            return;
        }

        if (vybrany.getMnozstvo() <= 0) {
            infoLabel.setText("Chyba: Tento liek nie je dostupný na sklade.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Predaj lieku");
        dialog.setHeaderText("Koľko kusov lieku " + vybrany.getNazov() + " chcete predať?");
        dialog.setContentText("Dostupné množstvo: " + vybrany.getMnozstvo() + " ks");

        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) {
            infoLabel.setText("Predaj bol zrušený.");
            return;
        }

        Integer kusy = nacitajKusy(result.get());

        if (kusy == null) {
            infoLabel.setText("Chyba: Množstvo musí byť celé číslo väčšie ako 0.");
            return;
        }

        if (kusy > vybrany.getMnozstvo()) {
            infoLabel.setText("Chyba: Nie je možné predať viac kusov, ako je na sklade.");
            return;
        }

        Alert potvrdenie = new Alert(Alert.AlertType.CONFIRMATION);
        potvrdenie.setTitle("Potvrdenie predaja");
        potvrdenie.setHeaderText("Naozaj chcete predať " + kusy + " ks lieku " + vybrany.getNazov() + "?");
        potvrdenie.setContentText("Celková cena: " + String.format("%.2f €", vybrany.getCena() * kusy));

        Optional<ButtonType> potvrdene = potvrdenie.showAndWait();

        if (potvrdene.isEmpty() || potvrdene.get() != ButtonType.OK) {
            infoLabel.setText("Predaj bol zrušený.");
            return;
        }

        if (lekaren.predatLiek(vybrany, kusy)) {
            ukazVsetky();
            infoLabel.setText("Úspešne predané " + kusy + " ks lieku " + vybrany.getNazov() + ".");
        } else {
            infoLabel.setText("Chyba: Predaj nebolo možné vykonať.");
        }
    }

    @FXML
    public void vyraditLiek() {
        if (lekaren.ziskatVsetkyLieky().isEmpty()) {
            infoLabel.setText("Sklad je prázdny. Nie je možné vyradiť liek.");
            return;
        }

        Liek vybrany = tabulkaLiekov.getSelectionModel().getSelectedItem();

        if (vybrany == null) {
            infoLabel.setText("Najprv musíte kliknúť na liek v zozname.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Vyradenie lieku");
        alert.setHeaderText("Naozaj chcete vyradiť liek " + vybrany.getNazov() + "?");

        if (vybrany.jeExpirrovany()) {
            alert.setContentText("Upozornenie: Tento liek je expirovaný.");
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            lekaren.odstranLiek(vybrany);
            ukazVsetky();
            infoLabel.setText("Liek bol vyradený zo skladu.");
        } else {
            infoLabel.setText("Vyradenie lieku bolo zrušené.");
        }
    }

    @FXML
    public void vyraditExpirovane() {
        List<Liek> expirovane = lekaren.ziskatExpirovanyLieky();

        if (expirovane.isEmpty()) {
            infoLabel.setText("V sklade nie sú žiadne expirované lieky.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Vyradenie expirovaných liekov");
        alert.setHeaderText("Naozaj chcete vyradiť všetky expirované lieky?");
        alert.setContentText("Počet expirovaných liekov: " + expirovane.size());

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            lekaren.vyraditExpirovanyLieky();
            ukazVsetky();
            infoLabel.setText("Všetky expirované lieky boli vyradené.");
        } else {
            infoLabel.setText("Vyradenie expirovaných liekov bolo zrušené.");
        }
    }

    private Double nacitajCenu(String vstup) {
        if (vstup == null) return null;
        vstup = vstup.trim().replace(',', '.');
        if (vstup.isEmpty()) return null;
        try {
            double cena = Double.parseDouble(vstup);
            if (cena <= 0 || Double.isNaN(cena) || Double.isInfinite(cena)) return null;
            return cena;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer nacitajKusy(String vstup) {
        if (vstup == null) return null;
        vstup = vstup.trim();
        if (vstup.isEmpty()) return null;
        try {
            int kusy = Integer.parseInt(vstup);
            if (kusy <= 0) return null;
            return kusy;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}