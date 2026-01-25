package sk.spse.lekaren;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({"nazov", "ucinnaLatka", "datumExpiracie", "cena", "mnozstvo"})
public class Liek {
    private final String nazov;
    private final String ucinnaLatka;
    private final LocalDate datumExpiracie;
    private final double cena;
    private int mnozstvo;

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

    /**
     * Zníži množstvo lieku o zadaný počet kusov
     */
    public void zmensitMnozstvo(int pocet) {
        this.mnozstvo -= pocet;
    }

    /**
     * Kontroluje, či je liek expirovaný
     */
    public boolean jeExpirrovany() {
        return LocalDate.now().isAfter(datumExpiracie);
    }

    @Override
    public String toString() {
        String stav = jeExpirrovany() ? " [Expirovaný]" : "";
        return String.format("Názov: %s | Účinná látka: %s | Expirácia: %s | Cena: %.2f€ | Množstvo: %d ks%s",
                nazov, ucinnaLatka, datumExpiracie, cena, mnozstvo, stav);
    }
}