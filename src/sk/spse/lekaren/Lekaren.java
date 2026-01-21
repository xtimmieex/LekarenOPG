package sk.spse.lekaren;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lekaren {
    private SkladLiekov sklad;

    public Lekaren() {
        this.sklad = new SkladLiekov();
    }

    // Zoradenie a výpis liekov podľa účinnej látky
    public void vypisatPodlaUcinnejLatky() {
        List<Liek> zoradene = new ArrayList<>(sklad.getLieky());
        Collections.sort(zoradene, new UcinnaLatkaComparator());

        System.out.println("Lieky zoradené podľa účinnej látky:");
        for (Liek liek : zoradene) {
            System.out.println(liek);
        }
        System.out.println();
    }
}
