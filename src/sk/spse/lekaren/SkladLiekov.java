package sk.spse.lekaren;

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

    public void setLieky(List<Liek> lieky) {
        this.lieky = lieky;
    }

    public void naskladniLiek(Liek liek) {
        lieky.add(liek);
    }

    public void odstranLiek(Liek liek) {
        lieky.remove(liek);
    }

    public int getPocetLiekov() {
        return lieky.size();
    }
}
