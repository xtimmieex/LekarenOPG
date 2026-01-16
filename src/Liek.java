import java.time.LocalDate;

public class Liek implements Comparable<Liek> {
    // Atribúty
    private String nazov;
    private String ucinnaLatka;
    private LocalDate datumExpiracie;
    private double cena;

    // Konštruktor
    public Liek(String nazov, String ucinnaLatka, LocalDate datumExpiracie, double cena) {
        this.nazov = nazov;
        this.ucinnaLatka = ucinnaLatka;
        this.datumExpiracie = datumExpiracie;
        this.cena = cena;
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

    // Settery
    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setUcinnaLatka(String ucinnaLatka) {
        this.ucinnaLatka = ucinnaLatka;
    }

    public void setDatumExpiracie(LocalDate datumExpiracie) {
        this.datumExpiracie = datumExpiracie;
    }

    public void setCena(double cena) {
        this.cena = cena;
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
        return String.format("Názov: %s | Účinná látka: %s | Expirácia: %s | Cena: %.2f€",
                nazov, ucinnaLatka, datumExpiracie, cena);
    }
}
