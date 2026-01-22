package sk.spse.lekaren;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Lekaren {
    private SkladLiekov sklad;

    public Lekaren() {
        this.sklad = new SkladLiekov();
    }

    // OPRAVENÉ - používa Collections.sort() s Comparable namiesto chýbajúceho Comparator
    public void vypisatPodlaUcinnejLatky() {
        List<Liek> zoradene = new ArrayList<>(sklad.getLieky());
        Collections.sort(zoradene);  // Používa Liek.compareTo() namiesto chýbajúceho UcinnaLatkaComparator

        System.out.println("Lieky zoradené podľa účinnej látky:");
        for (Liek liek : zoradene) {
            System.out.println(liek);
        }
        System.out.println();
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
        System.out.println("2. Vyhľadať liek");
        System.out.println("3. Pridať nový liek");
        System.out.println("4. Predaj lieku");
        System.out.println("5. Koniec");
        System.out.print("Vyberte možnosť (1-5): ");
    }

    public static void main(String[] args) {
        Lekaren lekaren = new Lekaren();
        Scanner scanner = new Scanner(System.in);

        lekaren.uvitanie();

        while (true) {
            lekaren.vypisanieMenu();
            int volba = scanner.nextInt();
            scanner.nextLine();

            switch (volba) {
                case 1:
                    lekaren.vypisatPodlaUcinnejLatky();
                    break;
                case 2:
                    System.out.println("Funkcia vyhľadávanie nie je implementovaná.");
                    break;
                case 3:
                    System.out.println("Funkcia pridanie lieku nie je implementovaná.");
                    break;
                case 4:
                    System.out.println("Funkcia predaj lieku nie je implementovaná.");
                    break;
                case 5:
                    System.out.println("Ďakujeme za návštevu! Dobre sa máte.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Neplatná voľba! Skúste znovu.\n");
            }
        }
    }
}
