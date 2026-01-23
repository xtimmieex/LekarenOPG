package sk.spse.lekaren;

import java.util.List;
import java.util.Scanner;

public class LekarenUI {
    private Lekaren lekaren;
    private Scanner scanner;

    public LekarenUI() {
        this.lekaren = new Lekaren();
        this.scanner = new Scanner(System.in);
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
        System.out.println("7. Koniec");
        System.out.print("Vyberte možnosť (1-7): ");
    }

    public void vypisatLieky(List<Liek> lieky, String nadpis) {
        System.out.println("=== " + nadpis + " ===");

        if (lieky.isEmpty()) {
            System.out.println("Žiadne lieky na zobrazenie.");
        } else {
            System.out.println("Počet liekov: " + lieky.size());
            System.out.println();

            for (Liek liek : lieky) {
                System.out.println(liek);
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
                    System.out.println("Funkcia vyhľadávanie nie je implementovaná.");
                    break;
                case 5:
                    System.out.println("Funkcia pridanie lieku nie je implementovaná.");
                    break;
                case 6:
                    System.out.println("Funkcia predaj lieku nie je implementovaná.");
                    break;
                case 7:
                    System.out.println("Ďakujeme za návštevu! Dobre sa máte.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Neplatná voľba! Skúste znovu.\n");
            }
        }
    }

}