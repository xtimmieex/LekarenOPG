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

    public List<Liek> ziskatVsetkyLieky() {
        return new ArrayList<>(sklad.getLieky());
    }

    public List<Liek> ziskatLiekyPodlaUcinnejLatky() {
        List<Liek> zoradene = new ArrayList<>(sklad.getLieky());
        Collections.sort(zoradene, new UcinnaLatkaComparator());
        return zoradene;
    }

    public List<Liek> ziskatExpirovanyLieky() {
        List<Liek> expirovane = new ArrayList<>();
        for (Liek liek : sklad.getLieky()) {
            if (liek.jeExpirrovany()) {
                expirovane.add(liek);
            }
        }
        return expirovane;
    }

    public int getPocetLiekov() {
        return sklad.getPocetLiekov();
    }

    public int getPocetExpirovanychLiekov() {
        return ziskatExpirovanyLieky().size();
    }

    public void pridatLiek(Liek liek) {
        sklad.naskladniLiek(liek);
    }

    public void odstranLiek(Liek liek) {
        sklad.odstranLiek(liek);
    }

    public void vyraditExpirovanyLieky() {
        sklad.vyradLiekyPoExpiracii();
    }

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

    public boolean predatLiek(Liek liek, int pocet) {
        if (!sklad.getLieky().contains(liek)) {
            System.out.println("✗ Tento liek nie je v sklade!");
            return false;
        }

        if (liek.jeExpirrovany()) {
            System.out.println("✗ POZOR: Tento liek je expirovaný a nemôže byť predaný!");
            return false;
        }

        if (liek.getMnozstvo() < pocet) {
            System.out.println("✗ Na sklade nie je dostatok kusov! Dostupné: " + liek.getMnozstvo() + " ks");
            return false;
        }

        liek.zmensitMnozstvo(pocet);

        if (liek.getMnozstvo() <= 0) {
            sklad.odstranLiek(liek);
            System.out.println("ℹ Liek bol odstránený zo skladu (vypredané).");
        }

        return true;
    }

    public void naskladnitLiek(String nazov, String ucinnaLatka, LocalDate datumExpiracie, double cena, int mnozstvo) {
        Liek novyLiek = new Liek(nazov, ucinnaLatka, datumExpiracie, cena, mnozstvo);
        sklad.naskladniLiek(novyLiek);
    }

    public boolean jeSkladPrazdny() {
        return sklad.jeSkladPrazdny();
    }

    public static void main(String[] args) {
        LekarenUI ui = new LekarenUI();
        ui.spustitMenu();
    }
}