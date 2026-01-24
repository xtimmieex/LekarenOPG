package sk.spse.lekaren;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class LekarenUI {
    private Lekaren lekaren;
    private Scanner scanner;

    public LekarenUI() {
        this.lekaren = new Lekaren();
        this.scanner = new Scanner(System.in);
        // Načítanie testovacích dát
        MockData.naplnitTestovacimiDatami(lekaren);
    }

    public void uvitanie() {
        System.out.println("=========================================");
        System.out.println("            VITAJTE V LEKÁRNI            ");
        System.out.println("=========================================");
        System.out.println("Dobrý deň! Vitajte v našej lekárni.");
        System.out.println("Sme tu pre Vás 24/7 s kompletnou ponukou liekov.");
        System.out.println("=========================================\n");
    }

    public void vypisanieMenu() {
        System.out.println("=== HLAVNÉ MENU LEKÁRNE ===");
        System.out.println("1. Zobraziť lieky podľa účinnej látky");
        System.out.println("2. Zobraziť všetky lieky");
        System.out.println("3. Zobraziť expirované lieky");
        System.out.println("4. Vyhľadať liek");
        System.out.println("5. Pridať nový liek");
        System.out.println("6. Predaj lieku");
        System.out.println("7. Vyradiť liek");
        System.out.println("8. Vyradiť všetky expirované lieky");
        System.out.println("9. Koniec");
        System.out.print("Vyberte možnosť (1-9): ");
    }

    public void vypisatLieky(List<Liek> lieky, String nadpis) {
        System.out.println("=== " + nadpis + " ===");

        if (lieky.isEmpty()) {
            System.out.println("Žiadne lieky na zobrazenie.");
        } else {
            System.out.println("Počet liekov: " + lieky.size());
            System.out.println();

            for (int i = 0; i < lieky.size(); i++) {
                System.out.println((i + 1) + ". " + lieky.get(i));
            }
        }
        System.out.println();
    }

    public void vypisatLiekyPoExpiracii() {
        List<Liek> expirovane = lekaren.ziskatExpirovanyLieky();

        System.out.println("=== EXPIROVANÉ LIEKY ===");

        if (expirovane.isEmpty()) {
            System.out.println("Žiadne expirované lieky v sklade.");
        } else {
            System.out.println("Počet expirovaných liekov: " + expirovane.size());
            System.out.println("UPOZORNENIE: Tieto lieky by mali byť vyradené!");
            System.out.println();

            for (Liek liek : expirovane) {
                System.out.println(liek);
            }
        }
        System.out.println();
    }

    public void vyhladatLiek() {
        System.out.println("\n=== VYHĽADÁVANIE LIEKU ===");
        System.out.println("1. Vyhľadať podľa názvu");
        System.out.println("2. Vyhľadať podľa účinnej látky");
        System.out.print("Vyberte možnosť (1-2): ");

        int volba = scanner.nextInt();
        scanner.nextLine();

        switch (volba) {
            case 1:
                System.out.print("Zadajte názov lieku: ");
                String nazov = scanner.nextLine();
                List<Liek> podlaNazvu = lekaren.vyhladatPodlaNazvu(nazov);
                vypisatLieky(podlaNazvu, "VÝSLEDKY VYHĽADÁVANIA PODĽA NÁZVU: " + nazov);
                break;
            case 2:
                System.out.print("Zadajte účinnú látku: ");
                String latka = scanner.nextLine();
                List<Liek> podlaLatky = lekaren.vyhladatPodlaUcinnejLatky(latka);
                vypisatLieky(podlaLatky, "VÝSLEDKY VYHĽADÁVANIA PODĽA ÚČINNEJ LÁTKY: " + latka);
                break;
            default:
                System.out.println("Neplatná voľba!\n");
        }
    }

    public void pridatLiek() {
        System.out.println("\n=== PRIDANIE NOVÉHO LIEKU ===");

        try {
            System.out.print("Názov lieku: ");
            String nazov = scanner.nextLine();

            System.out.print("Účinná látka: ");
            String ucinnaLatka = scanner.nextLine();

            System.out.print("Dátum expirácie (RRRR-MM-DD): ");
            String datumStr = scanner.nextLine();
            LocalDate datumExpiracie = LocalDate.parse(datumStr, DateTimeFormatter.ISO_LOCAL_DATE);

            System.out.print("Cena (€): ");
            double cena = scanner.nextDouble();

            System.out.print("Množstvo (ks): ");
            int mnozstvo = scanner.nextInt();
            scanner.nextLine(); // Spotrebuje newline

            lekaren.naskladnitLiek(nazov, ucinnaLatka, datumExpiracie, cena, mnozstvo);
            System.out.println("\n✓ Liek bol úspešne naskladnený!");

        } catch (DateTimeParseException e) {
            System.out.println("✗ Chyba: Nesprávny formát dátumu! Použite formát RRRR-MM-DD");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("✗ Chyba pri pridávaní lieku: " + e.getMessage());
            scanner.nextLine();
        }
        System.out.println();
    }

    public void predatLiek() {
        System.out.println("\n=== PREDAJ LIEKU ===");

        if (lekaren.jeSkladPrazdny()) {
            System.out.println("Sklad je prázdny. Nie je možné predať liek.\n");
            return;
        }

        List<Liek> lieky = lekaren.ziskatVsetkyLieky();
        vypisatLieky(lieky, "DOSTUPNÉ LIEKY");

        System.out.print("Vyberte číslo lieku na predaj (0 pre zrušenie): ");
        int volba = scanner.nextInt();
        scanner.nextLine();

        if (volba == 0) {
            System.out.println("Predaj zrušený.\n");
            return;
        }

        if (volba < 1 || volba > lieky.size()) {
            System.out.println("✗ Neplatný výber!\n");
            return;
        }

        Liek vybranyLiek = lieky.get(volba - 1);

        System.out.println("\nPredávaný liek: " + vybranyLiek);
        System.out.print("Potvrďte predaj (a/n): ");
        String potvrdenie = scanner.nextLine().toLowerCase();

        if (potvrdenie.equals("a") || potvrdenie.equals("ano")) {
            if (lekaren.predatLiek(vybranyLiek)) {
                System.out.println("✓ Liek bol úspešne predaný!");
                System.out.printf("Celková suma: %.2f€\n", vybranyLiek.getCena());
            } else {
                System.out.println("✗ Predaj nebol možné vykonať!");
            }
        } else {
            System.out.println("Predaj zrušený.");
        }
        System.out.println();
    }

    public void vyraditLiek() {
        System.out.println("\n=== VYRADENIE LIEKU ===");

        if (lekaren.jeSkladPrazdny()) {
            System.out.println("Sklad je prázdny. Nie je možné vyradiť liek.\n");
            return;
        }

        List<Liek> lieky = lekaren.ziskatVsetkyLieky();
        vypisatLieky(lieky, "DOSTUPNÉ LIEKY");

        System.out.print("Vyberte číslo lieku na vyradenie (0 pre zrušenie): ");
        int volba = scanner.nextInt();
        scanner.nextLine();

        if (volba == 0) {
            System.out.println("Vyradenie zrušené.\n");
            return;
        }

        if (volba < 1 || volba > lieky.size()) {
            System.out.println("✗ Neplatný výber!\n");
            return;
        }

        Liek vybranyLiek = lieky.get(volba - 1);

        System.out.println("\nVyraďovaný liek: " + vybranyLiek);
        if (vybranyLiek.jeExpirrovany()) {
            System.out.println("⚠ UPOZORNENIE: Tento liek je expirovaný!");
        }
        System.out.print("Potvrďte vyradenie (a/n): ");
        String potvrdenie = scanner.nextLine().toLowerCase();

        if (potvrdenie.equals("a") || potvrdenie.equals("ano")) {
            lekaren.odstranLiek(vybranyLiek);
            System.out.println("✓ Liek bol úspešne vyradený zo skladu!");
        } else {
            System.out.println("Vyradenie zrušené.");
        }
        System.out.println();
    }

    public void vyraditVsetkyExpirovane() {
        System.out.println("\n=== VYRADENIE VŠETKÝCH EXPIROVANÝCH LIEKOV ===");

        int pocetExpirovanychPred = lekaren.getPocetExpirovanychLiekov();

        if (pocetExpirovanychPred == 0) {
            System.out.println("✓ V sklade nie sú žiadne expirované lieky.\n");
            return;
        }

        System.out.println("Počet expirovaných liekov: " + pocetExpirovanychPred);
        System.out.print("Naozaj chcete vyradiť všetky expirované lieky? (a/n): ");
        String potvrdenie = scanner.nextLine().toLowerCase();

        if (potvrdenie.equals("a") || potvrdenie.equals("ano")) {
            lekaren.vyraditExpirovanyLieky();
            System.out.println("✓ Bolo vyradených " + pocetExpirovanychPred + " expirovaných liekov!");
        } else {
            System.out.println("Vyradenie zrušené.");
        }
        System.out.println();
    }

    public void spustitMenu() {
        uvitanie();

        while (true) {
            vypisanieMenu();
            int volba = scanner.nextInt();
            scanner.nextLine(); // Spotrebuje newline

            switch (volba) {
                case 1:
                    List<Liek> podlaLatky = lekaren.ziskatLiekyPodlaUcinnejLatky();
                    vypisatLieky(podlaLatky, "LIEKY ZORADENÉ PODĽA ÚČINNEJ LÁTKY");
                    break;
                case 2:
                    List<Liek> vsetky = lekaren.ziskatVsetkyLieky();
                    vypisatLieky(vsetky, "VŠETKY LIEKY V SKLADE");
                    break;
                case 3:
                    vypisatLiekyPoExpiracii();
                    break;
                case 4:
                    vyhladatLiek();
                    break;
                case 5:
                    pridatLiek();
                    break;
                case 6:
                    predatLiek();
                    break;
                case 7:
                    vyraditLiek();
                    break;
                case 8:
                    vyraditVsetkyExpirovane();
                    break;
                case 9:
                    System.out.println("Ďakujeme za návštevu! Dobre sa máte.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Neplatná voľba! Skúste znovu.\n");
            }
        }
    }
}