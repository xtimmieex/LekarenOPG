package sk.spse.lekaren;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class UcinnaLatkaComparator implements Comparator<Liek> {
    private Collator collator;

    public UcinnaLatkaComparator() {
        collator = Collator.getInstance(new Locale("sk", "SK"));
        collator.setStrength(Collator.SECONDARY);
    }

    @Override
    public int compare(Liek a, Liek b) {
        return collator.compare(a.getUcinnaLatka(), b.getUcinnaLatka());
    }
}