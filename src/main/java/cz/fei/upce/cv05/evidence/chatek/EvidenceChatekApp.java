package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {

    // Konstanty pro definovani jednotlivych operaci (pouze pro cisty kod)
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
                    ----------
                    MENU:

                    1 - vypsani vsech chatek
                    2 - vypsani konkretni chatky
                    3 - Pridani navstevniku
                    4 - Odebrani navstevniku
                    5 - Celkova obsazenost kempu
                    6 - Vypis prazdne chatky
                    0 - Konec programu
                    """;
    static int[] chatky = new int[VELIKOST_KEMPU];
    static int cisloChatky;
    static int indexChatky;

    public static void main(String[] args) {

        int operace;

        do {
            System.out.println(menu);
            System.out.print("Zadej volbu: ");
            operace = scanner.nextInt();

            switch (operace) {
                case VYPIS_CHATEK ->
                    vypisChatek();
                case VYPIS_KONKRETNI_CHATKU ->
                    vypisKonkretniChatky();
                case PRIDANI_NAVSTEVNIKU ->
                    pridaniNavstevniku();
                case ODEBRANI_NAVSTEVNIKU ->
                    odebraniNavstevniku();
                case CELKOVA_OBSAZENOST ->
                    celkovaObsazenost();
                case VYPIS_PRAZDNE_CHATKY ->
                    vypisPrazdneChatky();
                case KONEC_PROGRAMU ->
                    System.out.println("Konec programu");
                default ->
                    System.err.println("Neplatna volba");
            }
        } while (operace != 0);
    }

    private static void pridaniNavstevniku() {
        if (!(zadejCisloChatky())) {
            return;
        }
        int pocetNavstevniku = zadejPocetNavstevniku();
        if (kontrolaMaxPoctuNavstevniku(pocetNavstevniku) == false) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
            return;
        }

        if (vlozNavstevnikyDoChatky(pocetNavstevniku) == false) {
            System.err.println("Prekrocen maximalni pocet navstevniku chatky");
        } else {
            System.out.println("Navstevnici uspesne pridany");
        }

    }

    private static boolean vlozNavstevnikyDoChatky(int pocetNavstevniku) {
        if ((chatky[indexChatky] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {

            return false;
        } else {
            // Pridej nove ubytovane do chatky k tem co uz tam jsou
            chatky[indexChatky] += pocetNavstevniku;
            return true;
        }
    }

    private static boolean kontrolaMaxPoctuNavstevniku(int pocetNavstevniku) {
        // Zaporne cislo nebo prilis velky nevalidni vstup

        return !(pocetNavstevniku <= 0
                || pocetNavstevniku > MAX_VELIKOST_CHATKY);
    }

    private static int zadejPocetNavstevniku() {
        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = scanner.nextInt();
        return pocetNavstevniku;
    }

    private static void vypisKonkretniChatky() {
        if (!(zadejCisloChatky())) {
            return;
        }
        System.out.format("Chatka [%1d] =%2d\n",
                cisloChatky, chatky[indexChatky]);

    }

    private static int prevedeniCislaChatkyNaIndex(int cisloChatky) {
        return cisloChatky - 1;
    }

    private static void vypisChatek() {
        // Projdi cele pole od <0, VELIKOST) a vypis kazdy index
        for (int i = 0; i < chatky.length; i++) {
            System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
        }
    }

    private static boolean zadejCisloChatky() {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        int zadaneCisloChatky = scanner.nextInt();
        if (kontrolaCislaChatky(zadaneCisloChatky)) {
            cisloChatky = zadaneCisloChatky;
            indexChatky = prevedeniCislaChatkyNaIndex(cisloChatky);
            return true;
        }
        System.err.println("Tato chatka neexistuje");
        return false;
    }

    private static boolean kontrolaCislaChatky(int zadaneCisloChatky) {
        int indexZadaneChatky = zadaneCisloChatky - 1;
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        return (indexZadaneChatky > 0 && indexZadaneChatky <= chatky.length);

    }

    private static void odebraniNavstevniku() {
        if (!(zadejCisloChatky())) {
            return;
        }
        int pocetNavstevniku = zadejPocetNavstevniku();

        if (odebraniNavstevnikuZChatky(pocetNavstevniku) == false) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
        } else {
            System.out.println("Navstevnici uspesne odebrany");
        }

    }

    private static boolean odebraniNavstevnikuZChatky(int pocetNavstevniku) {
        if ((chatky[indexChatky] - pocetNavstevniku) < 0) {
            return false;

        }
        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[indexChatky] -= pocetNavstevniku;
        return true;
    }

    private static void celkovaObsazenost() {
        int celkovaObsazenost = 0;

        for (int chatka : chatky) {
            celkovaObsazenost += chatka;
        }
        System.out.println("Celkova obsazenost chatek je " + celkovaObsazenost);

    }

    private static void vypisPrazdneChatky() {
        int pocetPrazdnychChatek = 0;
        for (int i : chatky) {
            if (i == 0) {
                pocetPrazdnychChatek++;
            }
        }
        System.out.println("Pocet prazdnych chatek je " + pocetPrazdnychChatek);
    }

}
