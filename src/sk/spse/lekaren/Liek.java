package sk.spse.lekaren;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({"nazov", "ucinnaLatka", "datumExpiracie", "cena", "mnozstvo"})
public class Liek implements Comparable<Liek> {
    // Atribúty
    private final String nazov;
    private final String ucinnaLatka;
    private final LocalDate datumExpiracie;
    private final double cena;
    private final int mnozstvo;

    @JsonCreator
    public Liek(
            @JsonProperty("nazov") String nazov,
            @JsonProperty("ucinnaLatka") String ucinnaLatka,
            @JsonProperty("datumExpiracie") LocalDate datumExpiracie,
            @JsonProperty("cena") double cena,
            @JsonProperty("mnozstvo") int mnozstvo
    ) {
        this.nazov = nazov;
        this.ucinnaLatka = ucinnaLatka;
        this.datumExpiracie = datumExpiracie;
        this.cena = cena;
        this.mnozstvo = mnozstvo;
    }

    // Gettery
    public String getNazov() { return nazov; }
    public String getUcinnaLatka() { return ucinnaLatka; }
    public LocalDate getDatumExpiracie() { return datumExpiracie; }
    public double getCena() { return cena; }
    public int getMnozstvo() { return mnozstvo; }

    // Opravená metóda compareTo pre porovnávanie podľa účinnej látky
    @Override
    public int compareTo(Liek other) {
        return this.ucinnaLatka.compareTo(other.ucinnaLatka);
    }

    // Metóda na kontrolu expirácie
    public boolean jeExpirrovany() {
        return LocalDate.now().isAfter(datumExpiracie);
    }

    // Opravený toString pre konzistentný výpis
    @Override
    public String toString() {
        String stav = jeExpirrovany() ? " [EXpirovaný]" : "";
        return String.format("Názov: %s | Účinná látka: %s | Expirácia: %s | Cena: %.2f€ | Množstvo: %d%s",
                nazov, ucinnaLatka, datumExpiracie, cena, mnozstvo, stav);
    }
}
