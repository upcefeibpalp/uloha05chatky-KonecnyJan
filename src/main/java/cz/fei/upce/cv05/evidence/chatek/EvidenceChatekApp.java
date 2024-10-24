package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {

    static final int KONEC_PROGRAMU = 0;
    static final int VYPIS_CHATEK = 1;
    static final int VYPIS_KONKRETNI_CHATKU = 2;
    static final int PRIDANI_NAVSTEVNIKU = 3;
    static final int ODEBRANI_NAVSTEVNIKU = 4;
    static final int CELKOVA_OBSAZENOST = 5;
    static final int VYPIS_PRAZDNE_CHATKY = 6;

    static final int VELIKOST_KEMPU = 10;
    static final int MAX_VELIKOST_CHATKY = 4;
    static Scanner scanner = new Scanner(System.in);
    static String menu = """
                    MENU:

                    1 - vypsani vsech chatek
                    2 - vypsani konkretni chatky
                    3 - Pridani navstevniku
                    4 - Odebrani navstevniku
                    5 - Celkova obsazenost kempu
                    6 - Vypis prazdne chatky
                    0 - Konec programu
                    """;

    public static void main(String[] args) {
        // Konstanty pro definovani jednotlivych operaci (pouze pro cisty kod)

        // Definovani pole podle velikosti kempu (poctu chatek)
        int[] chatky = new int[VELIKOST_KEMPU];
        int operace;

        do {
            System.out.println(menu);
            System.out.print("Zadej volbu: ");
            operace = scanner.nextInt();

            switch (operace) {
                case VYPIS_CHATEK -> {

                    vypisChatek(chatky);
                }

                case VYPIS_KONKRETNI_CHATKU -> {

                    vypisKonkretniChatky(chatky);

                }

                case PRIDANI_NAVSTEVNIKU -> {

                    pridaniNavstevniku(chatky);
                }

                case ODEBRANI_NAVSTEVNIKU -> {
                    // TODO
                    odebraniNavstevniku(chatky);
                }

                case CELKOVA_OBSAZENOST -> {
                    // TODO
                    celkovaObsazenost(chatky);
                }

                case VYPIS_PRAZDNE_CHATKY -> {
                    // TODO
                    vypisPrazdneChatky(chatky);
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

    private static void pridaniNavstevniku(int[] chatky) {
        int cisloChatky = zadejCisloChatky();
        if (kontrolaCislaChatky(cisloChatky, chatky) == false) {
            System.err.println("Tato chatka neexistuje");
            return;
        }
        int pocetNavstevniku = zadejPocetNavstevniku();
        if (kontrolaMaxPoctuNavstevnikuVChatce(pocetNavstevniku) == false) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
            return;
        }

        if (vlozNavstevnikyDoChatky(chatky, cisloChatky, pocetNavstevniku) == false) {
            System.err.println("Prekrocen maximalni pocet navstevniku chatky");
        } else {
            System.out.println("Navstevnici uspesne pridany");
        }

    }

    private static boolean vlozNavstevnikyDoChatky(int[] chatky, int cisloChatky, int pocetNavstevniku) {
        int indexChatky = prevedeniCislaChatkyNaIndex(cisloChatky);
        if ((chatky[indexChatky] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {

            return false;
        }
        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[indexChatky] = pocetNavstevniku + chatky[indexChatky];
        return true;
    }

    private static boolean kontrolaMaxPoctuNavstevnikuVChatce(int pocetNavstevniku) {
        // Zaporne cislo nebo prilis velky nevalidni vstup

        return !(pocetNavstevniku <= 0 || pocetNavstevniku > MAX_VELIKOST_CHATKY);
    }

    private static int zadejPocetNavstevniku() {
        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = scanner.nextInt();
        return pocetNavstevniku;
    }

    private static void vypisKonkretniChatky(int[] chatky) {
        int cisloChatky = zadejCisloChatky();
        if (kontrolaCislaChatky(cisloChatky, chatky) == false) {
            System.err.println("Tato chatka neexistuje");
            return;
        }

        System.out.println("Chatka [" + cisloChatky + "] = " + chatky[prevedeniCislaChatkyNaIndex(cisloChatky)]);
    }

    private static int prevedeniCislaChatkyNaIndex(int cisloChatky) {
        return cisloChatky - 1;
    }

    private static void vypisChatek(int[] chatky) {
        // Projdi cele pole od <0, VELIKOST) a vypis kazdy index
        for (int i = 0; i < chatky.length; i++) {
            System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
        }
    }

    private static int zadejCisloChatky() {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        // Odecteni 1 protoze uzivatel cisluje chatky o 1, ale program od 0
        int cisloChatky = scanner.nextInt();
        return cisloChatky;
    }

    private static boolean kontrolaCislaChatky(int cisloChatky, int[] chatky) {
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        return !(prevedeniCislaChatkyNaIndex(cisloChatky) < 0
                || prevedeniCislaChatkyNaIndex(cisloChatky) >= chatky.length);
    }

    private static void odebraniNavstevniku(int[] chatky) {
        int cisloChatky = zadejCisloChatky();
        int indexChatky = prevedeniCislaChatkyNaIndex(cisloChatky);
        kontrolaCislaChatky(cisloChatky, chatky);
        int pocetNavstevniku = zadejPocetNavstevniku();

        if (odebraniNavstevnikuZChatky(chatky, indexChatky, pocetNavstevniku) == false) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
        } else {
            System.out.println("Navstevnici uspesne odebrany");
        }

    }

    private static boolean odebraniNavstevnikuZChatky(int[] chatky, int indexChatky, int pocetNavstevniku) {
        if ((chatky[indexChatky] - pocetNavstevniku) < 0) {
            return false;

        }
        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[indexChatky] = chatky[indexChatky] - pocetNavstevniku;
        return true;
    }

    private static void celkovaObsazenost(int[] chatky) {
        int celkovaObsazenost = 0;

        for (int chatka : chatky) {
            celkovaObsazenost += chatka;
        }
        System.out.println("Celková obsazenost chatek je " + celkovaObsazenost);

    }

    private static void vypisPrazdneChatky(int[] chatky) {
        int pocetPrazdnychChatek = 0;
        for (int i : chatky) {
            if (i == 0) {
                pocetPrazdnychChatek++;
            }
        }
        System.out.println("Pocet prazdnych chatek je " + pocetPrazdnychChatek);
    }
}
