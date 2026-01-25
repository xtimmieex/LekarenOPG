package sk.spse.lekaren;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SkladLiekov {
    private List<Liek> lieky;

    public SkladLiekov() {
        this.lieky = new ArrayList<>();
    }

    public List<Liek> getLieky() {
        return lieky;
    }

    public void naskladniLiek(Liek liek) {
        lieky.add(liek);
    }

    public void odstranLiek(Liek liek) {
        lieky.remove(liek);
    }

    public void vyradLiekyPoExpiracii() {
        lieky.removeIf(liek -> !liek.getDatumExpiracie().isAfter(LocalDate.now()));
    }

    public int getPocetLiekov() {
        return lieky.size();
    }

    public boolean jeSkladPrazdny() {
        return lieky.isEmpty();
    }
}
