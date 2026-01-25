# Lekáreň OPG

Konzolová aplikácia pre správu skladu liekov v lekárni.

## O projekte

Program načíta lieky zo súboru `assets/lieky.json` a umožňuje ich spravovať cez textové menu.

## Funkcie

- Pridávanie a vyraďovanie liekov
- Predaj liekov s kontrolou expirácie a množstva
- Vyhľadávanie podľa názvu alebo účinnej látky
- Zobrazenie všetkých liekov (aj zoradených podľa účinnej látky)
- Kontrola a vyraďovanie expirovaných liekov

## Hlavné triedy

- **Liek** - obsahuje názov, účinnú látku, dátum expirácie, cenu a množstvo
- **SkladLiekov** - spravuje zoznam liekov
- **Lekaren** - biznis logika (predaj, vyhľadávanie, validácia)
- **LekarenUI** - používateľské rozhranie s menu
- **UcinnaLatkaComparator** - zoraďovanie liekov podľa účinnej látky
- **Json** - načítanie a zápis JSON súborov

## Menu

```
1. Zobraziť lieky podľa účinnej látky
2. Zobraziť všetky lieky
3. Zobraziť expirované lieky
4. Vyhľadať liek
5. Pridať nový liek
6. Predaj lieku
7. Vyradiť liek
8. Vyradiť všetky expirované lieky
9. Koniec
```

## Autori

**Timea** - LekarenUI, výpisy, predaj a naskladnenie  
**Elizabeth** - Liek, UcinnaLatkaComparator, testovacie dáta  
**Dano** - SkladLiekov, vyraďovanie liekov, UML diagram  
**Timea & Elizabeth** - Prezentácia