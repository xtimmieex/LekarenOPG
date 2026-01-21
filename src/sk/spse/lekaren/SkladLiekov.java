package sk.spse.lekaren;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SkladLiekov {

    private List<Liek> lieky;

    // Konštruktor
    public SkladLiekov() {
        this.lieky = new ArrayList<>();
    }

    // Getter
    public List<Liek> getLieky() {
        return lieky;
    }

    // Setter
    public void setLieky(List<Liek> lieky) {
        this.lieky = lieky;
    }

    // Naskladnenie lieku
    public void naskladniLiek(Liek liek) {
        lieky.add(liek);
    }

    // Odstránenie konkrétneho lieku
    public void odstranLiek(Liek liek) {
        lieky.remove(liek);
    }

    // Vyradenie liekov po expirácii
    public void vyradLiekyPoExpiracii() {
        lieky.removeIf(liek ->
                liek.getDatumExpiracie().isBefore(LocalDate.now())
        );
    }

    // Počet liekov v sklade
    public int getPocetLiekov() {
        return lieky.size();
    }
}
