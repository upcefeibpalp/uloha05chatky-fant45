package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {

    public static void main(String[] args) {
        // Konstanty pro definovani jednotlivych operaci (pouze pro cisty kod)
        final int KONEC_PROGRAMU = 0;
        final int VYPIS_CHATEK = 1;
        final int VYPIS_KONKRETNI_CHATKU = 2;
        final int PRIDANI_NAVSTEVNIKU = 3;
        final int ODEBRANI_NAVSTEVNIKU = 4;
        final int CELKOVA_OBSAZENOST = 5;
        final int VYPIS_PRAZDNE_CHATKY = 6;

        final int VELIKOST_KEMPU = 5;
        final int MAX_VELIKOST_CHATKY = 10;

        Scanner scanner = new Scanner(System.in);

        // Definovani pole podle velikosti kempu (poctu chatek)
        int[] chatky = new int[VELIKOST_KEMPU];
        int operace;

        do {
            System.out.println("""
                    MENU:
                                        
                    1 - vypsani vsech chatek
                    2 - vypsani konkretni chatky
                    3 - Pridani navstevniku
                    4 - Odebrani navstevniku
                    5 - Celkova obsazenost kempu
                    6 - Vypis prazdne chatky
                    0 - Konec programu
                    """);

            // Ziskani operace od uzivatele
            System.out.print("Zadej volbu: ");
            operace = scanner.nextInt();

            switch (operace) {
                case VYPIS_CHATEK -> {

                    vypisChatek(chatky);
                }

                case VYPIS_KONKRETNI_CHATKU -> {

                    if (vypisJedneChatky(scanner, chatky)) continue;
                }

                case PRIDANI_NAVSTEVNIKU -> {

                    if (pridaniNavstevniku(scanner, chatky, MAX_VELIKOST_CHATKY)) continue;
                }

                case ODEBRANI_NAVSTEVNIKU -> {
                    if (odebraniNavstevniku(scanner, chatky)) continue;
                }

                case CELKOVA_OBSAZENOST -> {
                    celkovaObsazenost(VELIKOST_KEMPU, chatky);
                    
                }

                case VYPIS_PRAZDNE_CHATKY -> {
                    
                    vypisPrazdneChatky(VELIKOST_KEMPU, chatky);
                    
                }

                case KONEC_PROGRAMU -> {
                    System.out.println("Konec programu");
                }

                default -> {
                    System.err.println("Neplatna volba");
                }
            }
        } while (operace != 0);
    }

    private static void celkovaObsazenost(final int VELIKOST_KEMPU, int[] chatky) {
        int suma = 0;
        
        for (int i = 0; i < VELIKOST_KEMPU; i++) {
            suma += chatky[i];
        }
        System.out.println("Celkový počet návštěvníků kempu: " + suma);
    }

    private static void vypisPrazdneChatky(final int VELIKOST_KEMPU, int[] chatky) {
        for (int i = 0; i < VELIKOST_KEMPU; i++) {
            if (chatky[i] == 0) {
                System.out.println("Chatka[" + (i + 1) +"]: " + chatky[i]);
            }
        }
    }

    private static boolean odebraniNavstevniku(Scanner scanner, int[] chatky) {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        // Odecteni 1 protoze uzivatel cisluje chatky o 1, ale program od 0
        int cisloChatky = scanner.nextInt() - 1;
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        if (cisloChatky < 0 || cisloChatky >= chatky.length) {
            System.err.println("Tato chatka neexistuje");
            return true; // Zacni novou iteraci cyklu
        }
        //Vypsání počtu návštěvníků v chatce
        System.out.println("Chatka [" + (cisloChatky + 1) + "] = " + chatky[cisloChatky]);
        //Získání počtu návšěvníků, které chcem odebrat. Ověření hodnot
        System.out.print("Zadej pocet navstevniku, které chcete odebrat: ");
        int pocetNavstevnikuOdebrat = scanner.nextInt();
        // Zaporne cislo nebo prilis velky nevalidni vstup
        if (pocetNavstevnikuOdebrat <= 0) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
            return true; // Zacni novou iteraci cyklu
        }
        // Pokud je pocet uz ubytovanych mínus ty co chceme odebrat menší než nula je to nevalidni vstup
        if (pocetNavstevnikuOdebrat > chatky[cisloChatky]) {
            System.err.println("Prekrocen maximalního počtu ubytovaných");
            return true; // Zacni novou iteraci cyklu
        }
        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[cisloChatky] = chatky[cisloChatky] - pocetNavstevnikuOdebrat;
        return false;
    }

    private static boolean pridaniNavstevniku(Scanner scanner, int[] chatky, final int MAX_VELIKOST_CHATKY) {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        // Odecteni 1 protoze uzivatel cisluje chatky o 1, ale program od 0
        int cisloChatky = scanner.nextInt() - 1;
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        if (cisloChatky < 0 || cisloChatky >= chatky.length) {
            System.err.println("Tato chatka neexistuje");
            return true; // Zacni novou iteraci cyklu
        }
        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = scanner.nextInt();
        // Zaporne cislo nebo prilis velky nevalidni vstup
        if (pocetNavstevniku <= 0 || pocetNavstevniku > MAX_VELIKOST_CHATKY) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
            return true; // Zacni novou iteraci cyklu
        }
        // Pokud je pocet uz ubytovanych plus ty co se chteji ubytovat vetsi nez kapacita chatky je to nevalidni vstup
        if ((chatky[cisloChatky] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {
            System.err.println("Prekrocen maximalni pocet navstevniku chatky");
            return true; // Zacni novou iteraci cyklu
        }
        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[cisloChatky] = pocetNavstevniku + chatky[cisloChatky];
        return false;
    }

    private static boolean vypisJedneChatky(Scanner scanner, int[] chatky) {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        // Odecteni 1 protoze uzivatel cisluje chatky o 1, ale program od 0
        int cisloChatky = scanner.nextInt() - 1;
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        if (cisloChatky < 0 || cisloChatky >= chatky.length) {
            System.err.println("Tato chatka neexistuje");
            return true; // Zacni novou iteraci cyklu
        }
        System.out.println("Chatka [" + (cisloChatky + 1) + "] = " + chatky[cisloChatky]);
        return false;
    }

    private static void vypisChatek(int[] chatky) {
        // Projdi cele pole od <0, VELIKOST) a vypis kazdy index
        for (int i = 0; i < chatky.length; i++) {
            System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
        }
    }
}
