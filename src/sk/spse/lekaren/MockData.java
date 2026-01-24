package sk.spse.lekaren;

import java.time.LocalDate;

public class MockData {

    /**
     * Naplní lekáreň testovacími dátami
     */
    public static void naplnitTestovacimiDatami(Lekaren lekaren) {
        // Lieky s platnou expiráciou
        lekaren.naskladnitLiek(
                "Paralen",
                "Paracetamol",
                LocalDate.of(2026, 12, 31),
                4.50,
                50
        );

        lekaren.naskladnitLiek(
                "Ibalgin",
                "Ibuprofen",
                LocalDate.of(2027, 3, 15),
                6.80,
                30
        );

        lekaren.naskladnitLiek(
                "Aspirin",
                "Kyselina acetylsalicylová",
                LocalDate.of(2026, 8, 20),
                3.20,
                75
        );

        lekaren.naskladnitLiek(
                "Stopangín",
                "Hexetidín",
                LocalDate.of(2027, 1, 10),
                7.90,
                25
        );

        lekaren.naskladnitLiek(
                "Claritine",
                "Loratadín",
                LocalDate.of(2026, 11, 5),
                8.50,
                40
        );

        lekaren.naskladnitLiek(
                "Modafen",
                "Ibuprofen",
                LocalDate.of(2026, 9, 30),
                5.20,
                60
        );

        lekaren.naskladnitLiek(
                "Nurofen",
                "Ibuprofen",
                LocalDate.of(2027, 2, 28),
                9.30,
                35
        );

        lekaren.naskladnitLiek(
                "Voltaren",
                "Diklofenak",
                LocalDate.of(2026, 10, 15),
                12.40,
                20
        );

        lekaren.naskladnitLiek(
                "Acc",
                "Acetylcysteín",
                LocalDate.of(2027, 4, 22),
                6.70,
                45
        );

        lekaren.naskladnitLiek(
                "Strepsils",
                "Amylmetakrezol",
                LocalDate.of(2026, 7, 18),
                5.60,
                80
        );

        // Expirované lieky (pre testovanie)
        lekaren.naskladnitLiek(
                "Coldrex Starý",
                "Paracetamol",
                LocalDate.of(2024, 6, 15),
                4.20,
                15
        );

        lekaren.naskladnitLiek(
                "Septolete Expirovaný",
                "Benzalkónium-chlorid",
                LocalDate.of(2023, 12, 31),
                3.80,
                10
        );

        lekaren.naskladnitLiek(
                "Vitamin C Starý",
                "Kyselina askorbová",
                LocalDate.of(2024, 3, 10),
                2.50,
                20
        );

        // Lieky blízko expirácie
        lekaren.naskladnitLiek(
                "Mucosolvan",
                "Ambroxol",
                LocalDate.of(2026, 2, 28),
                8.90,
                18
        );

        lekaren.naskladnitLiek(
                "Fenistil",
                "Dimetindén",
                LocalDate.of(2026, 3, 15),
                11.20,
                12
        );
    }

    /**
     * Pomocná metóda pre rýchle vytvorenie lekárne s dátami
     */
    public static Lekaren vytvorLekarenSDatami() {
        Lekaren lekaren = new Lekaren();
        naplnitTestovacimiDatami(lekaren);
        return lekaren;
    }

    /**
     * Hlavná metóda pre otestovanie
     */
    public static void main(String[] args) {
        Lekaren lekaren = vytvorLekarenSDatami();

        System.out.println("=== MOCK DATA ÚSPEŠNE NAČÍTANÉ ===");
        System.out.println("Celkový počet liekov: " + lekaren.getPocetLiekov());
        System.out.println("Počet expirovaných: " + lekaren.getPocetExpirovanychLiekov());
        System.out.println();

        System.out.println("=== VŠETKY LIEKY ===");
        for (Liek liek : lekaren.ziskatVsetkyLieky()) {
            System.out.println(liek);
        }
    }
}