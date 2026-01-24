package sk.spse.lekaren;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lekaren {
    private SkladLiekov sklad;

    public Lekaren() {
        this.sklad = new SkladLiekov();
    }

    /**
     * Vráti zoznam všetkých liekov v sklade
     */
    public List<Liek> ziskatVsetkyLieky() {
        return new ArrayList<>(sklad.getLieky());
    }

    /**
     * Vráti lieky zoradené podľa účinnej látky
     */
    public List<Liek> ziskatLiekyPodlaUcinnejLatky() {
        List<Liek> zoradene = new ArrayList<>(sklad.getLieky());
        Collections.sort(zoradene);
        return zoradene;
    }

    /**
     * Vráti zoznam expirovaných liekov
     */
    public List<Liek> ziskatExpirovanyLieky() {
        List<Liek> expirovane = new ArrayList<>();
        for (Liek liek : sklad.getLieky()) {
            if (liek.jeExpirrovany()) {
                expirovane.add(liek);
            }
        }
        return expirovane;
    }

    /**
     * Vráti počet všetkých liekov v sklade
     */
    public int getPocetLiekov() {
        return sklad.getPocetLiekov();
    }

    /**
     * Vráti počet expirovaných liekov
     */
    public int getPocetExpirovanychLiekov() {
        return ziskatExpirovanyLieky().size();
    }

    /**
     * Pridá nový liek do skladu
     */
    public void pridatLiek(Liek liek) {
        sklad.naskladniLiek(liek);
    }

    /**
     * Odstráni liek zo skladu
     */
    public void odstranLiek(Liek liek) {
        sklad.odstranLiek(liek);
    }

    /**
     * Vyradí všetky expirované lieky zo skladu
     */
    public void vyraditExpirovanyLieky() {
        sklad.vyradLiekyPoExpiracii();
    }

    /**
     * Vyhľadá lieky podľa názvu (čiastočné zhodovanie)
     */
    public List<Liek> vyhladatPodlaNazvu(String nazov) {
        List<Liek> vysledky = new ArrayList<>();
        String hladanyNazov = nazov.toLowerCase();

        for (Liek liek : sklad.getLieky()) {
            if (liek.getNazov().toLowerCase().contains(hladanyNazov)) {
                vysledky.add(liek);
            }
        }
        return vysledky;
    }

    /**
     * Vyhľadá lieky podľa účinnej látky (čiastočné zhodovanie)
     */
    public List<Liek> vyhladatPodlaUcinnejLatky(String ucinnaLatka) {
        List<Liek> vysledky = new ArrayList<>();
        String hladanaLatka = ucinnaLatka.toLowerCase();

        for (Liek liek : sklad.getLieky()) {
            if (liek.getUcinnaLatka().toLowerCase().contains(hladanaLatka)) {
                vysledky.add(liek);
            }
        }
        return vysledky;
    }

    /**
     * Predá liek pacientovi (odstráni ho zo skladu)
     * @param liek Liek, ktorý sa má predať
     * @return true ak bol liek úspešne predaný, false ak liek nie je v sklade
     */
    public boolean predatLiek(Liek liek) {
        if (sklad.getLieky().contains(liek)) {
            if (liek.jeExpirrovany()) {
                System.out.println("POZOR: Tento liek je expirovaný a nemôže byť predaný!");
                return false;
            }
            sklad.odstranLiek(liek);
            return true;
        }
        return false;
    }

    /**
     * Naskladní nový liek do skladu
     * @param nazov Názov lieku
     * @param ucinnaLatka Účinná látka
     * @param datumExpiracie Dátum expirácie
     * @param cena Cena lieku
     * @param mnozstvo Množstvo kusov
     */
    public void naskladnitLiek(String nazov, String ucinnaLatka, LocalDate datumExpiracie, double cena, int mnozstvo) {
        Liek novyLiek = new Liek(nazov, ucinnaLatka, datumExpiracie, cena, mnozstvo);
        sklad.naskladniLiek(novyLiek);
    }

    /**
     * Kontroluje, či je sklad prázdny
     */
    public boolean jeSkladPrazdny() {
        return sklad.jeSkladPrazdny();
    }

    /**
     * Získa priamy prístup k skladu (pre špeciálne účely)
     */
    public SkladLiekov getSklad() {
        return sklad;
    }

    /**
     * Hlavná metóda - spustí aplikáciu
     */
    public static void main(String[] args) {
        LekarenUI ui = new LekarenUI();
        ui.spustitMenu();
    }
}