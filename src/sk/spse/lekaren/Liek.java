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

    @JsonCreator  // Jackson tento konštruktor použije pri vytváraní objektu z JSON
    public Liek(
            // mapovanie JSON atribútov na atribúty triedy
            @JsonProperty("nazov") String nazov,
            @JsonProperty("ucinnaLatka") String ucinnaLatka,
            // Jackson automaticky skonvertuje na LocalDate
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
    public String getNazov() {
        return nazov;
    }

    public String getUcinnaLatka() {
        return ucinnaLatka;
    }

    public LocalDate getDatumExpiracie() {
        return datumExpiracie;
    }

    public double getCena() {
        return cena;
    }

    public int getMnozstvo() {
        return mnozstvo;
    }

    // Metóda na kontrolu expirácie
    public boolean jeExpirrovany() {
        return LocalDate.now().isAfter(datumExpiracie);
    }

    // Implementácia compareTo pre triedenie podľa účinnej látky
    @Override
    public int compareTo(Liek inyLiek) {
        return this.ucinnaLatka.compareTo(inyLiek.ucinnaLatka);
    }

    // ToString pre výpis informácií
    @Override
    public String toString() {
        return String.format("Názov: %s | Účinná látka: %s | Expirácia: %s | Cena: %.2f€ | Množstvo: %d ks",
                nazov, ucinnaLatka, datumExpiracie, cena, mnozstvo);
    }
}

